package com.gcs.suban.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.gcs.suban.R;
import com.gcs.suban.bean.LotteryBean;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class DiscLotterView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    private static final float CIRCLE_ANGLE = 360;
    private static final float HALF_CIRCLE_ANGLE = 180;

    RectF mRectRange;   //范围
    RectF mRectCircleRange; //圆环范围
    Paint mSpanPaint;   //绘制圆盘
    Paint mCirclePaint; //绘制圆环
    Paint mTextPaint;   //绘制文字
    DiscRollListener mDiscRollListener; //转动状态的接听
    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas;
    private Thread mThread;
    private boolean isRunning;  //控制线程的开关
    private List<LotteryBean> mList = new ArrayList<>(); //奖品列表
    private Bitmap[] mImgBitmap;
    private int[] mColor;   //背景颜色
    private Bitmap mDiscBackground = BitmapFactory.decodeResource(getResources(), R.drawable.disc_bg); //背景图片
    private int mRadius;    //转盘直径
    private int mPadding;
    private float mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,15,getResources().getDisplayMetrics());  //字体大小，设置成可配属性
    private int mSpanCount = 0; //盘的数量
    private double mSpeed;  //盘滚动的速度
    private volatile float mStartSpanAngel = 0; //开始转动角度  可能会有多个线程访问  保证线程间的可见性
    private int mCenter;    //中心
    private boolean isSpanEnd;  //是否停止




    public DiscLotterView(Context context) {
        this(context,null);
    }

    public DiscLotterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        //设置克获得焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        //设置长亮
        setKeepScreenOn(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = Math.min(getMeasuredWidth(),getMeasuredHeight());
        mPadding = getPaddingLeft();
        mRadius = width - mPadding * 2;
        mCenter = width / 2;
        setMeasuredDimension(width,width);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //初始化画笔
        mSpanPaint = new Paint();
        mSpanPaint.setAntiAlias(true);
        mSpanPaint.setDither(true);
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(0Xffa58453);
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(0xffdfc89c);

        mRectRange = new RectF(mPadding,mPadding,mPadding + mRadius,mPadding + mRadius);
        mRectCircleRange = new RectF(mPadding * 3 /2,mPadding * 3 / 2,getMeasuredWidth() - mPadding * 3 / 2,getMeasuredWidth() - mPadding * 3 / 2);

        mThread = new Thread(this);
        isRunning = true;
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning){
            long start = SystemClock.currentThreadTimeMillis();
            if(mSpanCount > 0){
                draw();
            }
            long end = SystemClock.currentThreadTimeMillis();
            if((end - start) < 50){
                SystemClock.sleep( 50 - (end - start));
            }
        }
    }

    private void draw(){
        try{
            mCanvas = mSurfaceHolder.lockCanvas();
            if(null != mCanvas){
                drawBg();
                mCanvas.drawCircle(mCenter,mCenter, mRadius / 2 + mPadding / 20,mCirclePaint);
                drawSpan();
            }
        }catch (Exception e){

        }finally {
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    private void drawBg(){
        mCanvas.drawColor(0xffffffff);
        mCanvas.drawBitmap(mDiscBackground, null, new RectF(mPadding / 2, mPadding / 2, getMeasuredWidth() - mPadding / 2, getMeasuredWidth() - mPadding / 2),mSpanPaint);
    }

    private void drawSpan(){
        float tmpAngle = mStartSpanAngel;
        float sweepAngle = CIRCLE_ANGLE / mSpanCount;
        for(int i = 0; i < mSpanCount; i++){
            LotteryBean bean = mList.get(i);
            //mSpanPaint.setColor(Integer.getInteger(bean.getBgcolor(),16));
            BigInteger b = new BigInteger(bean.getBgcolor().substring(2),16);
            mSpanPaint.setColor(b.intValue());
            mCanvas.drawArc(mRectCircleRange,tmpAngle,sweepAngle,true,mSpanPaint);
            drawText(tmpAngle,sweepAngle,bean.getTitle());
            drawPrizeIcon(tmpAngle,bean.getPrize());
            tmpAngle += sweepAngle;
        }
        //通过修改mSpeed的值让转盘有不同速度的转动
        mStartSpanAngel += mSpeed;
        if(isSpanEnd){
            mSpeed -= 1;
        }
        if(mSpeed <= 0){
            mSpeed = 0;
            isSpanEnd = false;
            mDiscRollListener.onDiscRollListener(mSpeed);
        }
    }

    private void drawText(float tmpAngle,float seeepAngle,String text){
        Path path = new Path();
        path.addArc(mRectRange,tmpAngle,seeepAngle);
        float txtWidth = mTextPaint.measureText(text);
        float hOval = (float) ((mRadius * Math.PI / mSpanCount / 2) - (txtWidth / 2));
        float vOval = mRadius / 15; //竖直偏移量可以自定义
        mCanvas.drawTextOnPath(text,path,hOval,vOval,mTextPaint);
    }

    private void drawPrizeIcon(float tmpAngle, Bitmap bitmap){
        int iconWidth = mRadius / 20;
        double angle = (tmpAngle + CIRCLE_ANGLE / mSpanCount / 2) * Math.PI / 180;
        int x = (int) (mCenter + mRadius / 4 * Math.cos(angle));
        int y = (int) (mCenter + mRadius / 4 * Math.sin(angle));
        RectF rectF = new RectF(x - iconWidth, y - iconWidth, x + iconWidth, y + iconWidth);
        mCanvas.drawBitmap(bitmap,null,rectF,null);
    }

    public void setOnDiscRollListener(DiscRollListener discRollListener){
        this.mDiscRollListener = discRollListener;
    }

    public interface DiscRollListener{
        void onDiscRollListener(double speed);
    }

    public void setList(List<LotteryBean> mList){
        this.mList.addAll(mList);
        mSpanCount = this.mList.size();
    }
    public List<LotteryBean> getAdapter(){    return this.mList;}
    public int getCount(){  return mList.size();}

    public void luckStart(int index){
        float angle = CIRCLE_ANGLE / mSpanCount;
        float from = HALF_CIRCLE_ANGLE - (index - 1) * angle;
        float end = from + angle;
        //设置需要停下来的时候转动的距离  保证每次不停留的某个index下的同一个位置
        float targentFrom = 3 * CIRCLE_ANGLE + from;
        float targentEnd = 3 * CIRCLE_ANGLE + end;

        float vForm = (float) ((Math.sqrt(1 + 8 * targentFrom) - 1) / 2);
        float vEnd = (float) ((Math.sqrt(1 + 8 * targentEnd) - 1) / 2);
        mSpeed = vForm + Math.random() * (vEnd - vForm);
        isSpanEnd = false;
    }

    public void luckStop(){
        mStartSpanAngel = 0;
        isSpanEnd = true;
    }
}
