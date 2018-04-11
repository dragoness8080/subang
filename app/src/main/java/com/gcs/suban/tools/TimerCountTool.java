package com.gcs.suban.tools;

import android.os.CountDownTimer;
import android.widget.Button;
/**
 * ������ ����ʱ��
 */
public class TimerCountTool extends CountDownTimer {
    private Button button;

    public TimerCountTool(long millisInFuture, long countDownInterval, Button button) {
        super(millisInFuture, countDownInterval);
        this.button = button;
    }

    public TimerCountTool(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onFinish() {
        // TODO Auto-generated method stub
    	button.setClickable(true);
    	button.setText("��ȡ��֤��");
    }

    @Override
    public void onTick(long arg0) {
        // TODO Auto-generated method stub
    	button.setClickable(false);
    	button.setText(arg0 / 1000 + "������»�ȡ");
    }
}
