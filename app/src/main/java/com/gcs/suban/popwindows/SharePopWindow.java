package com.gcs.suban.popwindows;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.gcs.suban.Constants;
import com.gcs.suban.R;
import com.gcs.suban.app;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.wxapi.WXHelper;
import com.tencent.tauth.Tencent;

public class SharePopWindow extends BaseActivity {
	private TextView Tv_cancel;

//	private RelativeLayout Rlyt_qq;
//	private RelativeLayout Rlyt_qqzone;
	private RelativeLayout Rlyt_weixin;
	private RelativeLayout Rlyt_friend;

	private String otherurl;
	private String title;
	private String summary;
	private String pic;
	private Bitmap bitmap;

	private WXHelper wxHelper;

	private Tencent mTencent;
	private static final String SHARE_TO_QZONE_TYPE_IMAGE_TEXT = null;
	
	private Boolean isLoad=false;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.weixin:
			if(isLoad)
			{
			wxHelper.share(0, summary, title, otherurl, bitmap);
			}
			else
			{
				ToastTool.showToast(context, "分享内容载入中，请稍后");
			}
			break;
		case R.id.friend:
			if(isLoad)
			{
			wxHelper.share(1, summary, title, otherurl, bitmap);
			}
			else
			{
				ToastTool.showToast(context, "分享内容载入中，请稍后");
			}
			break;
/*		case R.id.qq:
			shareToQQ();
			break;
		case R.id.qqzone:
			shareToQzone();
			break;*/
		case R.id.tv_cancel:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		otherurl = intent.getStringExtra("otherurl");
		title = intent.getStringExtra("title");
		summary = intent.getStringExtra("summary");
		pic = intent.getStringExtra("pic");
		ImageRequest imageRequest = new ImageRequest(pic,
				new Listener<Bitmap>() {

					@Override
					public void onResponse(Bitmap arg0) {
						bitmap = arg0;
						//Rlyt_weixin.setClickable(true);
						//Rlyt_friend.setClickable(true);
						isLoad=true;
					}
				}, 200, 200, Config.RGB_565, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						ToastTool.showToast(context, "分享图片加载错误");
					}
				});
		app.getHttpQueues().add(imageRequest);

		setContentView(R.layout.popwindow_share);
		SetWindow();

		Tv_cancel = (TextView) findViewById(R.id.tv_cancel);
		Tv_cancel.setOnClickListener(this);

	/*	Rlyt_qq = (RelativeLayout) findViewById(R.id.qq);
		Rlyt_qqzone = (RelativeLayout) findViewById(R.id.qqzone);*/
		Rlyt_weixin = (RelativeLayout) findViewById(R.id.weixin);
		Rlyt_friend = (RelativeLayout) findViewById(R.id.friend);

/*		Rlyt_qq.setOnClickListener(this);
		Rlyt_qqzone.setOnClickListener(this);*/
		Rlyt_weixin.setOnClickListener(this);
		//Rlyt_weixin.setClickable(false);
		Rlyt_friend.setOnClickListener(this);
		//Rlyt_friend.setClickable(false);

		wxHelper = new WXHelper(context);
		mTencent = Tencent.createInstance(Constants.QQ_APP_ID,
				this.getApplicationContext());
	}

/*	// 分享到qq
	private void shareToQQ() {
		final Bundle params = new Bundle();
		params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
				QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
		params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
		params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
		params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, otherurl);
		params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, pic);
		params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "素邦");
		mTencent.shareToQQ(SharePopWindow.this, params, new ShareTxListener());
	}

	// 分享到qq空间
	private void shareToQzone() {
		final Bundle params = new Bundle();
		params.putString(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,
				SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
		params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);// 必填
		params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary);// 选填
		params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, otherurl);// 必填
		ArrayList<String> list = new ArrayList<String>();
		list.add(pic);
		params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, list);
		mTencent.shareToQzone(SharePopWindow.this, params,
				new ShareTxListener());
	}*/

/*
	public class ShareTxListener implements IUiListener {

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub
			Log.d("Tecent", "onCancel");
		}

		@Override
		public void onComplete(Object arg0) {
			// TODO Auto-generated method stub
			Log.d("Tecent", "onComplete");
		}

		@Override
		public void onError(UiError e) {
			Log.d("Tecent", "onError");
		}
	}
*/

}
