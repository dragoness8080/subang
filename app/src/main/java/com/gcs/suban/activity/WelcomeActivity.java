package com.gcs.suban.activity;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.android.api.JPushInterface;

import com.android.volley.VolleyError;
import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.app;
import com.gcs.suban.tools.SharedPreference;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

public class WelcomeActivity extends InstrumentedActivity {

	private Context context;

	private Timer timer = new Timer();
	private Timer timer2 = new Timer();

	private String pic1, pic2, pic3, pic4;

	private Boolean isLoad = false, isLoad1 = false, isLoad2 = false,
			isLoad3 = false, isLoad4 = false;

	private Message message = new Message();

	/**
	 * handler检测图片是否加载完毕
	 */
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (isLoad1 && isLoad2 && isLoad3 && isLoad4) {
					isLoad = true;
					timer2.schedule(task2, 2000);
				}
				else
				{
					message = mHandler.obtainMessage(1);
					mHandler.sendMessageDelayed(message, 500);
					super.handleMessage(msg);
				}
				break;	
			}
		};
	};

	/**
	 * 超过8s后执行页面跳转
	 */
	TimerTask task = new TimerTask() {
		@Override
		public void run() {
			intent();
		}
	};
	
	/**
	 * 加载完毕后1s后执行页面跳转
	 */
	TimerTask task2 = new TimerTask() {
		@Override
		public void run() {
			intent();
		}
	};
	
	/**
	 * 直接进入下一个界面
	 */
	TimerTask task3 = new TimerTask() {
		@Override
		public void run() {
			String vid = (String) SharedPreference.getParam(context, "vid", "");
			if (vid.equals("")) {
				Intent intent_enter = new Intent(context, LoginActivity.class);
				startActivity(intent_enter);
			} else {
				Intent intent_enter2 = new Intent(context, MainActivity.class);
				startActivity(intent_enter2);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		context = this;
		init();
	}

	private void init() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        
		String first=(String) SharedPreference.getParam(app.getContext(), "first", "0");
		if(first.equals("0"))
		{
		getInfo(Url.welcome);
		timer.schedule(task, 8000);
		message.what = 1;
		mHandler.sendMessage(message);
		}else{
			timer.schedule(task3, 3000);
		}
	}

	/**
	 * 获取网络图片地址
	 */
	public void getInfo(String url) {
		final String TAG = url;
		// TODO Auto-generated method stub
		BaseVolleyRequest.StringRequestGet(context, url, TAG,
				new BaseStrVolleyInterFace(context,
						BaseStrVolleyInterFace.mListener,
						BaseStrVolleyInterFace.mErrorListener) {
					@Override
					public void onSuccess(String response) {
						Log.i(TAG, "GET请求成功-->" + response);
						try {
							JSONObject jsonObject = new JSONObject(response);
							String result = jsonObject.getString("result");
							if (result.equals("1001")) {
								pic1 = jsonObject.getString("pic1");
								pic2 = jsonObject.getString("pic2");
								pic3 = jsonObject.getString("pic3");
								pic4 = jsonObject.getString("pic4");
								load();
							} else {
								error();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							error();
						}
					}

					@Override
					public void onError(VolleyError volleyError) {
						Log.i(TAG, "GET请求失败-->" + volleyError.toString());
						error();
					}
				});
	}

	/**
	 * 页面跳转
	 */
	public void intent() {
		task.cancel();
		task2.cancel();
		mHandler.removeMessages(1);
		if (isLoad) {
			Intent intent_enter = new Intent(WelcomeActivity.this,
					LoginFirstActivity.class);
			intent_enter.putExtra("pic1", pic1);
			intent_enter.putExtra("pic2", pic2);
			intent_enter.putExtra("pic3", pic3);
			intent_enter.putExtra("pic4", pic4);
			startActivity(intent_enter);
		} else {
			String vid = (String) SharedPreference.getParam(context, "vid", "");
			if (vid.equals("")) {
				Intent intent_enter = new Intent(context, LoginActivity.class);
				startActivity(intent_enter);
			} else {
				Intent intent_enter2 = new Intent(context, MainActivity.class);
				startActivity(intent_enter2);
			}
		}
		finish();
	}

	/**
	 * 图片加载
	 */
	public void load() {
		ImageLoader.getInstance().loadImage(pic1,
				new SimpleImageLoadingListener() {
					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						isLoad1 = true;
						Log.i("pic1", imageUri);
					};
				});

		ImageLoader.getInstance().loadImage(pic2,
				new SimpleImageLoadingListener() {
					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						isLoad2 = true;
						Log.i("pic2", "load");
					};
				});

		ImageLoader.getInstance().loadImage(pic3,
				new SimpleImageLoadingListener() {
					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						isLoad3 = true;
						Log.i("pic3", "load");
					};
				});

		ImageLoader.getInstance().loadImage(pic4,
				new SimpleImageLoadingListener() {
					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						isLoad4 = true;
						Log.i("pic4", "load");
					};
				});
	}

	/**
	 * 网络 错误
	 */
	public void error() {
		intent();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

}
