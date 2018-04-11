package com.gcs.suban.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.app;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.CodeBean;
import com.gcs.suban.listener.OnQrCodeListener;
import com.gcs.suban.model.QrCodeModel;
import com.gcs.suban.model.QrCodeModelImpl;
import com.gcs.suban.popwindows.SharePopWindow;
import com.gcs.suban.tools.CodeCreatTool;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.wxapi.WXHelper;

public class QrCodeActivity extends BaseActivity implements OnQrCodeListener{
	private TextView Tv_title;
	
	private ImageButton IBtn_back;
	
	private Button Btn_share;
	
	private ImageView Img_qrcode;
	private ImageView Img_avatar;
	
	private TextView Tv_username;
	
	private RelativeLayout Rlyt_linkshare;
	private RelativeLayout Rlyt_picshare;
	
	private Bitmap bitmap;
	private String pic;
	private String link;
	private String title;
	private String content;
	
	private QrCodeModel model;
	private boolean isLoad;
	private WXHelper wxHelper;


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
/*		case R.id.btn_share:
			Intent linkshares = new Intent(context, SharePopWindow.class);
			linkshares.putExtra("otherurl", link);
			linkshares.putExtra("title", title);
			linkshares.putExtra("summary", content); 
			linkshares.putExtra("pic", pic);
			startActivity(linkshares);
			break;*/
		case R.id.rlyt_linkshare:
			Intent linkshare = new Intent(context, SharePopWindow.class);
			linkshare.putExtra("otherurl", link);
			linkshare.putExtra("title", title);
			linkshare.putExtra("summary", content); 
			linkshare.putExtra("pic", pic);
			startActivity(linkshare);
			break;
		case R.id.rlyt_picshare:
			Intent picshare = new Intent(context, SharePopWindow.class);
			picshare.putExtra("otherurl", link);
			picshare.putExtra("title", title);
			picshare.putExtra("summary", content);
			picshare.putExtra("pic", pic);
			startActivity(picshare);
			break;
			case R.id.wx_share:
				if (isLoad) {
					wxHelper.share(0, content, title, link, bitmap);
				} else {
					ToastTool.showToast(context, "分享内容载入中，请稍后");
				}
				break;
			case R.id.friend_share:
				if (isLoad) {
					wxHelper.share(1, content, title, link, bitmap);
				} else {
					ToastTool.showToast(context, "分享内容载入中，请稍后");
				}
				break;
			case R.id.copy:
				ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
				cmb.setText(link);
				Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
				break;

		default:
			break;


		}
	}

	@Override
	protected void init() {
		InitImageLoader();
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_qrcode);
		initView();
		wxHelper = new WXHelper(context);
		string2Bitmap();
		
		model=new QrCodeModelImpl();
		model.getInfo(Url.sharecontent, this);
	}

	private void initView() {
		Tv_title = (TextView) findViewById(R.id.title);
		Tv_title.setText("分享链接");

		Img_qrcode=(ImageView)context.findViewById(R.id.img_qrcode);
		Img_avatar=(ImageView)context.findViewById(R.id.img_avatar);

		Btn_share=(Button)context.findViewById(R.id.btn_share);
		Btn_share.setOnClickListener(this);

		Tv_username= (TextView) findViewById(R.id.tv_username);

		Rlyt_linkshare=(RelativeLayout)context.findViewById(R.id.rlyt_linkshare);
		Rlyt_linkshare.setOnClickListener(this);
		Rlyt_picshare=(RelativeLayout)context.findViewById(R.id.rlyt_picshare);
		Rlyt_picshare.setOnClickListener(this);

		IBtn_back = (ImageButton) context.findViewById(R.id.back);
		IBtn_back.setOnClickListener(this);
		findViewById(R.id.wx_share).setOnClickListener(this);
		findViewById(R.id.friend_share).setOnClickListener(this);
		findViewById(R.id.copy).setOnClickListener(this);
	}

	@Override
	public void onQrCode(CodeBean bean) {
		// TODO Auto-generated method stub
		Log.e(TAG, bean.link);
		title=bean.title;
		content=bean.content;
		link=bean.link;
		pic=bean.img;
		
		bitmap=CodeCreatTool.createQRImage(bean.link, 200, 200);
		Img_qrcode.setImageBitmap(bitmap);
		
		Tv_username.setText(bean.nickname);
		imageLoader.displayImage(bean.avatar, Img_avatar, options4);
		
	}

	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, error);
	}


	private void string2Bitmap() {
		ImageRequest imageRequest = new ImageRequest(pic,
				new Response.Listener<Bitmap>() {

					@Override
					public void onResponse(Bitmap arg0) {
						bitmap = arg0;
						//Rlyt_weixin.setClickable(true);
						//Rlyt_friend.setClickable(true);
						isLoad = true;
					}
				}, 200, 200, Bitmap.Config.RGB_565, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				ToastTool.showToast(context, "分享图片加载错误");
			}
		});
		app.getHttpQueues().add(imageRequest);
	}

}