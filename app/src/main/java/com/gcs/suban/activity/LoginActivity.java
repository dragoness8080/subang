package com.gcs.suban.activity;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.gcs.suban.MyDate;
import com.gcs.suban.R;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.eventbus.MainEvent;
import com.gcs.suban.wxapi.WXHelper;

import io.rong.eventbus.EventBus;


public class LoginActivity extends BaseActivity {
	private Button Btn_login;
	private WXHelper wxHelper;
	private Message message = new Message();

	private boolean first=false;
	private int i;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_login:
			i=2;
			if(first==false)
			{
			message.what = 1;
			mHandler.sendMessage(message);
			}
			first=true;
			Btn_login.setClickable(false);
			wxHelper.login();
			break;
		default:
			break;
		}
	}

	@Override
	protected void init() {
		EventBus.getDefault().register(this);
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_login);
		Btn_login = (Button) findViewById(R.id.btn_login);
		Btn_login.setOnClickListener(this);

		wxHelper = new WXHelper(context);
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (i == 1) {
					Btn_login.setText("正在获取授权...");
				} else if (i == 2) {
					Btn_login.setText("正在获取授权.");
				} else if (i == 3) {
					Btn_login.setText("正在获取授权..");
				}
				i = i + 1;
				if (i == 4) {
					i = 1;
				}
				break;
			}
			message = mHandler.obtainMessage(1);
			mHandler.sendMessageDelayed(message, 500);
			super.handleMessage(msg);
		};
	};
	
	/**
	 * 广播事件
	 */
	public void onEventMainThread(MainEvent event) {
		if(event.getType().equals("login"))
		{
		finish();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (MyDate.wxresp == 1) {
			Btn_login.setClickable(false);
		} else {
			i=5;
			Btn_login.setText("微信登录");
			Btn_login.setClickable(true);
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

}
