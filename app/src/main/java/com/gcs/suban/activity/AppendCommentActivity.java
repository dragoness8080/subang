package com.gcs.suban.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.CommentBean;
import com.gcs.suban.eventbus.OrderEvent;
import com.gcs.suban.listener.OnBaseListener;
import com.gcs.suban.listener.OnImgUpListener;
import com.gcs.suban.model.CommentModel;
import com.gcs.suban.model.CommentModelImpl;
import com.gcs.suban.tools.MapChangeTool;
import com.gcs.suban.tools.ToastTool;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.rong.eventbus.EventBus;


public class AppendCommentActivity extends BaseActivity implements
		OnImgUpListener, OnBaseListener {
	private TextView Tv_title;
	private TextView Tv_num;

	private ImageButton IBtn_back;

	private Button Btn_confirm;

	private EditText Et_comment;

	private ImageView img1;
	private ImageView img2;
	private ImageView img3;
	private ImageView img4;
	private ImageView img5;
	private ImageView cancel1;
	private ImageView cancel2;
	private ImageView cancel3;
	private ImageView cancel4;
	private ImageView cancel5;
	
	private String url1="";
	private String url2="";
	private String url3="";
	private String url4="";
	private String url5="";
	
	private String picfile;
	
	private String orderid;
	private String goodsid;

	private Bitmap photo;

	private RatingBar ratingBar;

	private int i;

	private CommentModel model;

	private CommentBean bean = new CommentBean();

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.btn_confirm:
			commentsup();
			break;
		case R.id.img1:
			i = 1;
			ShowPickDialog();
			break;
		case R.id.img2:
			i = 2;
			ShowPickDialog();
			break;
		case R.id.img3:
			i = 3;
			ShowPickDialog();
			break;
		case R.id.img4:
			i = 4;
			ShowPickDialog();
			break;
		case R.id.img5:
			i = 5;
			ShowPickDialog();
			break;
		case R.id.cancel1:
			url1="";
			img1.setImageResource(R.drawable.bg_add);
			showView();
			break;
		case R.id.cancel2:
			url2="";
			img2.setImageResource(R.drawable.bg_add);
			showView();
			break;
		case R.id.cancel3:
			url3="";
			img3.setImageResource(R.drawable.bg_add);
			showView();
			break;
		case R.id.cancel4:
			url4="";
			img4.setImageResource(R.drawable.bg_add);
			showView();
			break;
		case R.id.cancel5:
			url5="";
			img5.setImageResource(R.drawable.bg_add);
			showView();
			break;
		default:
			break;
		}
	}

	@Override
	protected void init() {
		Intent intent = getIntent();
		orderid = intent.getStringExtra("orderid");
		goodsid = intent.getStringExtra("goodsid");
		Log.e(orderid, goodsid);
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_addcomment);

		Tv_title = (TextView) findViewById(R.id.title);
		Tv_title.setText("追加评价");
		Tv_num = (TextView) findViewById(R.id.tv_num);

		Et_comment = (EditText) findViewById(R.id.et_comment);
		Et_comment.addTextChangedListener(watcher);
		
		//Rlyt_ratingbar=(RelativeLayout)findViewById(R.id.ratingBar);
		//Rlyt_ratingbar.setVisibility(View.INVISIBLE);

		IBtn_back = (ImageButton) context.findViewById(R.id.back);
		IBtn_back.setOnClickListener(this);

		Btn_confirm = (Button) context.findViewById(R.id.btn_confirm);
		Btn_confirm.setOnClickListener(this);

		ratingBar = (RatingBar) findViewById(R.id.ratingBar1);

		img1 = (ImageView) findViewById(R.id.img1);
		img1.setOnClickListener(this);
		img2 = (ImageView) findViewById(R.id.img2);
		img2.setOnClickListener(this);
		img3 = (ImageView) findViewById(R.id.img3);
		img3.setOnClickListener(this);
		img4 = (ImageView) findViewById(R.id.img4);
		img4.setOnClickListener(this);
		img5 = (ImageView) findViewById(R.id.img5);
		img5.setOnClickListener(this);
		
		cancel1 = (ImageView) findViewById(R.id.cancel1);
		cancel1.setOnClickListener(this);
		cancel2 = (ImageView) findViewById(R.id.cancel2);
		cancel2.setOnClickListener(this);
		cancel3 = (ImageView) findViewById(R.id.cancel3);
		cancel3.setOnClickListener(this);
		cancel4 = (ImageView) findViewById(R.id.cancel4);
		cancel4.setOnClickListener(this);
		cancel5 = (ImageView) findViewById(R.id.cancel5);
		cancel5.setOnClickListener(this);
		
		showView();

		model = new CommentModelImpl();
	}

	/** 弹出选项框 上传图片 */
	private void ShowPickDialog() {
		final SweetAlertDialog sd = new SweetAlertDialog(this,
				SweetAlertDialog.PAY_TYPE);
		sd.setTitleText("上传图片");
		sd.setCancelable(true);
		sd.setCanceledOnTouchOutside(true);
		sd.setFirstBtnText("相册");
		sd.setSecondBtnText("拍照");

		sd.setAliPayClickListener(new SweetAlertDialog.OnSweetClickListener() {

			@Override
			public void onClick(SweetAlertDialog sweetAlertDialog) {
				sd.dismiss();
				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(intent, 1);
			}
		});
		sd.setWxPayClickListener(new SweetAlertDialog.OnSweetClickListener() {

			@Override
			public void onClick(SweetAlertDialog sweetAlertDialog) {
				sd.dismiss();
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				// 下面这句指定调用相机拍照后的照片存储的路径
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
						Environment.getExternalStorageDirectory(), "logo.jpg")));
				startActivityForResult(intent, 2);
			}
		});
		sd.show();
	}

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			photo = extras.getParcelable("data");
			photo = MapChangeTool.changeMapSize(photo);
			picfile = MapChangeTool.bitmapToBase64(photo);
			model.UpImg(Url.commentimgup, picfile, this);
		}
	}

	/** 选取相片后返回自动调用 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// 如果是直接从相册获取
		case 1:
			// 非空判断，否则不选图片直接返回 date为空 出现空指针错误
			if (data != null) {
				startPhotoZoom(data.getData());
			}
			break;
		// 如果是调用相机拍照时
		case 2:
			if (Environment.getExternalStorageDirectory() + "/logo.jpg" != null) {
				File temp = new File(Environment.getExternalStorageDirectory()
						+ "/logo.jpg");
				startPhotoZoom(Uri.fromFile(temp));
			}
			break;
		// 取得裁剪后的图片
		case 3:
			if (data != null) {
				setPicToView(data);
			}
			break;
		default:
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	// 输入框的监听
	private TextWatcher watcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			int num = Et_comment.length();
			Tv_num.setText(num + "/360");
		}
	};

	private void commentsup() {
		bean.orderid = orderid;
		bean.goodsid = goodsid;
		String url = url1 + url2 + url3;
		if (url.length() != 0) {
			url = url.substring(0, url.length() - 1);
		}
		bean.images = url;
		bean.content = Et_comment.getText().toString();
		bean.level = ratingBar.getRating();
		model.appendCommentsUp(Url.appendcommentsup, bean, this);
	}

	private void showView() {
		cancel5.setVisibility(View.INVISIBLE);
		cancel4.setVisibility(View.INVISIBLE);
		cancel3.setVisibility(View.INVISIBLE);
		cancel2.setVisibility(View.INVISIBLE);
		cancel1.setVisibility(View.INVISIBLE);
		img5.setVisibility(View.INVISIBLE);
		img4.setVisibility(View.INVISIBLE);
		img3.setVisibility(View.INVISIBLE);
		img2.setVisibility(View.INVISIBLE);
		img1.setVisibility(View.INVISIBLE);
		
		if (!url5.equals("")) {
			cancel5.setVisibility(View.VISIBLE);
			img5.setVisibility(View.VISIBLE);
			img4.setVisibility(View.VISIBLE);
			img3.setVisibility(View.VISIBLE);
			img2.setVisibility(View.VISIBLE);
			img1.setVisibility(View.VISIBLE);
		} else if(!url4.equals("")){
			cancel4.setVisibility(View.VISIBLE);
			img5.setVisibility(View.VISIBLE);
			img4.setVisibility(View.VISIBLE);
			img3.setVisibility(View.VISIBLE);
			img2.setVisibility(View.VISIBLE);
			img1.setVisibility(View.VISIBLE);
		}
		else if(!url3.equals("")){
			cancel3.setVisibility(View.VISIBLE);
			img4.setVisibility(View.VISIBLE);
			img3.setVisibility(View.VISIBLE);
			img2.setVisibility(View.VISIBLE);
			img1.setVisibility(View.VISIBLE);
		}
		else if(!url2.equals("")){
			cancel2.setVisibility(View.VISIBLE);
			img3.setVisibility(View.VISIBLE);
			img2.setVisibility(View.VISIBLE);
			img1.setVisibility(View.VISIBLE);
		}
		else if(!url1.equals("")){
			cancel1.setVisibility(View.VISIBLE);
			img2.setVisibility(View.VISIBLE);
			img1.setVisibility(View.VISIBLE);
		}
		else if(url1.equals("")){
			img1.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onSuccess(String resulttext) {
		// TODO Auto-generated method stub
		EventBus.getDefault().post(
				new OrderEvent("update","msg"));
		finish();
	}

	@Override
	public void onUpImg(String resulttext, String url) {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, resulttext);
		if(i==1)
		{
			url1 = url+",";
			img1.setImageBitmap(photo);
		}
		if(i==2)
		{
			url2 = url+",";
			img2.setImageBitmap(photo);
		}
		if(i==3)
		{
			url3 = url+",";
			img3.setImageBitmap(photo);
		}
		
		if(i==4)
		{
			url4 = url+",";
			img4.setImageBitmap(photo);
		}
		
		if(i==5)
		{
			url5 = url+",";
			img5.setImageBitmap(photo);
		}
		showView();
	}

	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, error);
	}

}
