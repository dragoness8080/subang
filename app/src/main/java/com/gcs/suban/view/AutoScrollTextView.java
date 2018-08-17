package com.gcs.suban.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;
import android.view.View;

public class AutoScrollTextView extends TextView implements View.OnClickListener {

    public final static String TAG = AutoScrollTextView.class.getSimpleName();

    private float textLength = 0f;//文本长度
    private float viewWidth = 0f;
    private float step = 0f;//文字横坐标
    private float y = 0f;//文字纵坐标
    private float temp_view_plug_text_length = 0.0f;//用于计算的临时变量
    private float temp_view_plug_two_text_length = 0.0f;//用于计算的临时变量
    public boolean isStarting = false;//是否开始滚动
    private Paint paint = null;//绘图样式
    private String text = "";//文字内容

    public AutoScrollTextView(Context context) {
        super(context);
        initView();
    }

    public AutoScrollTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AutoScrollTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    public void onClick(View v) {
        if(isStarting){
            stopScroll();
        }else{
            startScroll();
        }
    }

    public void init(WindowManager windowManager){
        paint = getPaint();
        text = getText().toString();
        Log.i("AutoScrollTextView",text);
        textLength = paint.measureText(text);   //获取字符串长度
        viewWidth = getWidth();
        if(viewWidth == 0){
            if(windowManager != null){
                Display display = windowManager.getDefaultDisplay();
                viewWidth = display.getWidth();
            }
        }
        step = textLength;
        temp_view_plug_text_length = viewWidth + textLength;
        temp_view_plug_two_text_length = viewWidth + textLength * 2;
        y = getTextSize() + getPaddingTop();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.step = step;
        ss.isStarting = isStarting;
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if(!(state instanceof SavedState)){
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState)state;
        super.onRestoreInstanceState(ss.getSuperState());
        step = ss.step;
        isStarting = ss.isStarting;
    }

    public static class SavedState extends BaseSavedState{
        public boolean isStarting = false;
        public float step = 0.0f;

        SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeBooleanArray(new boolean[]{isStarting});
            out.writeFloat(step);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        private SavedState(Parcel in){
            super(in);
            boolean[] b = null;
            in.readBooleanArray(b);
            if(b != null && b.length > 0){
                isStarting = b[0];
            }
            step = in.readFloat();
        }
    }

    public void startScroll(){
        isStarting = true;
        invalidate();
    }

    public void stopScroll(){
        isStarting = false;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawText(text,temp_view_plug_text_length - step,y,paint);
        if(!isStarting){    return;}
        step += 0.7;//文字滚动速度
        if(step > temp_view_plug_two_text_length){  step = textLength;}
        invalidate();
    }

    private void initView(){
        setOnClickListener(this);
    }
}
