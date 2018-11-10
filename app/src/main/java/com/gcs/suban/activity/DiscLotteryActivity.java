package com.gcs.suban.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.LotteryBean;
import com.gcs.suban.listener.OnLotteryListener;
import com.gcs.suban.model.LotteryModel;
import com.gcs.suban.model.LotteryModelImpl;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.view.DiscLotterView;

import java.util.ArrayList;
import java.util.List;


public class DiscLotteryActivity extends BaseActivity implements OnLotteryListener {

    private List<LotteryBean> mList = new ArrayList<>();
    private LotteryModel model;
    private DiscLotterView lotterView;
    private RelativeLayout start;
    private TextView tv_times;
    private ImageButton back;
    private TextView title;
    private Button reward;
    private boolean mIsClickStart;
    private int times;
    private String mReward;
    private String mTitle;
    private String mThumb;
    private int mWinIndex;
    private DownLoadBitmapThread thread;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                dialog.cancel();
                lotterView.setList(mList);
            }
        }
    };

    @Override
    protected void init() {
        setContentView(R.layout.activity_disclottery);
        title = (TextView)findViewById(R.id.title_stock);
        title.setText("抽奖");
        back = (ImageButton)findViewById(R.id.back_stock);
        back.setOnClickListener(this);
        reward = (Button)findViewById(R.id.wallet_btn);
        reward.setText("获奖明细");
        reward.setOnClickListener(this);

        model = new LotteryModelImpl();
        model.getPrizeList(Url.lottery_list,this);
        thread = new DownLoadBitmapThread();

        dialog.show();

        lotterView = (DiscLotterView)findViewById(R.id.ls_lucky);
        start = (RelativeLayout)findViewById(R.id.ls_start);
        tv_times = (TextView)findViewById(R.id.lottery_times);
        start.setOnClickListener(this);
        lotterView.setOnDiscRollListener(new DiscLotterView.DiscRollListener() {
            @Override
            public void onDiscRollListener(double speed) {
                if(speed == 0){
                    start.setEnabled(true);
                    if(mIsClickStart){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mIsClickStart = false;
                                showDialog();
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_stock:
                finish();
                break;
            case R.id.ls_start:
                mWinIndex = -1;
                if(times <= 0){
                    ToastTool.showToast(context,"您已没有抽奖次数");
                }else{
                    start.setEnabled(false);
                    model.getReward(Url.lottery_reware,this);
                }
                break;
            case R.id.wallet_btn:
                Intent intent = new Intent(context, ExchangeActivity.class);
                intent.putExtra("postion", "0");
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onGetPrizeSuccess(List<LotteryBean> mList, int times) {
        this.mList.clear();
        this.mList.addAll(mList);
        this.times = times;
        tv_times.setText("剩余 " + this.times + " 次");
        thread.start();
    }

    @Override
    public void onError(String error) {
        ToastTool.showToast(context,error);
    }

    @Override
    public void onGetRewardSuccess(int index, String reward, String title, String thumb, int times) {
        mWinIndex = index;
        this.times = times;
        mReward = reward;
        mTitle = title;
        mThumb = thumb;
        mIsClickStart = true;
        tv_times.setText("剩余 " + this.times + " 次");
        lotterView.luckStart(index);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(5000);
                lotterView.luckStop();
            }
        }).start();
    }

    class DownLoadBitmapThread extends Thread{
        public void run(){
            for (int i = 0; i < mList.size(); i++){
                mList.get(i).setPrize();
                //String thumb =  mList.get(i).getThumb();
                //mList.get(i).setPrize(imageLoader.loadImageSync(thumb));
            }
            handler.sendEmptyMessage(1);
        }
    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(context,R.layout.popwindow_lottery,null);
        final ImageView lottery_win_img = (ImageView)dialogView.findViewById(R.id.lottery_win_img);
        final TextView lottery_win_title = (TextView)dialogView.findViewById(R.id.lottery_win_title);
        final TextView lottery_win_name = (TextView)dialogView.findViewById(R.id.lottery_win_name);
        LotteryBean bean = mList.get(mWinIndex);
        lottery_win_img.setImageBitmap(bean.getPrize());
        lottery_win_title.setText("恭喜您获得" + mReward);
        lottery_win_name.setText(bean.getTitle());
        //dialog.setTitle("获奖提醒");
        dialog.setView(dialogView);
        dialog.show();
    }
}
