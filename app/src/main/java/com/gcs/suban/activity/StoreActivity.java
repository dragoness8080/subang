package com.gcs.suban.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.StoreBean;
import com.gcs.suban.eventbus.StoreEvent;
import com.gcs.suban.listener.OnStoreListener;
import com.gcs.suban.model.StoreModel;
import com.gcs.suban.model.StoreModelImpl;
import com.gcs.suban.tools.MapChangeTool;
import com.gcs.suban.tools.ToastTool;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.rong.eventbus.EventBus;

/**
 * 小店设置
 */
public class StoreActivity extends BaseActivity implements OnStoreListener {
	private TextView Tv_title;
	private TextView Tv_name;

	private ImageView Img_storelogo;
	private ImageView Img_storeimg;

	private ImageButton IBtn_back;

	private Button Btn_store_confirm;

	private CardView Cv_store_logo;
	private CardView Cv_store_name;
	private CardView Cv_store_signs;
	private CardView Cv_store_introduce;

	private EditText Et_store_introduce;

	private String shopid;
	private String desc;
	private String picfile;
	private String picfile2;

	private StoreModel model;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.card_store_logo:
			ShowPickDialog();
			break;
		case R.id.card_store_name:
			Intent intent_username = new Intent(context,
					StorenameActivity.class);
			intent_username.putExtra("shopid", shopid);
			startActivityForResult(intent_username, 4);
			break;
		case R.id.card_store_signs:
			ShowPickDialog2();
			break;
		case R.id.card_store_introduce:
			Et_store_introduce.requestFocus();
			InputMethodManager imm = (InputMethodManager) Et_store_introduce
					.getContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
			break;
		case R.id.btn_store_confirm:
			desc = Et_store_introduce.getText().toString();
			model.setDesc(Url.shopdescmod, shopid, desc, this);
			break;
		case R.id.back:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void init() {
		EventBus.getDefault().register(this);
		InitImageLoader();
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_store);
		Tv_title = (TextView) findViewById(R.id.title);
		Tv_title.setText("小店设置");
		Tv_name = (TextView) findViewById(R.id.tv_store_name);

		Img_storelogo = (ImageView) findViewById(R.id.img_store_logo);
		Img_storeimg = (ImageView) findViewById(R.id.img_store_img);

		Et_store_introduce = (EditText) findViewById(R.id.et_store_introduce);
		Et_store_introduce.setVisibility(View.INVISIBLE);

		Cv_store_logo = (CardView) context.findViewById(R.id.card_store_logo);
		Cv_store_name = (CardView) context.findViewById(R.id.card_store_name);
		Cv_store_signs = (CardView) context.findViewById(R.id.card_store_signs);
		Cv_store_introduce = (CardView) context
				.findViewById(R.id.card_store_introduce);

		Btn_store_confirm = (Button) context
				.findViewById(R.id.btn_store_confirm);
		Btn_store_confirm.setOnClickListener(this);

		IBtn_back = (ImageButton) context.findViewById(R.id.back);
		IBtn_back.setOnClickListener(this);

		Cv_store_logo.setOnClickListener(this);
		Cv_store_name.setOnClickListener(this);
		Cv_store_signs.setOnClickListener(this);
		Cv_store_introduce.setOnClickListener(this);

		model = new StoreModelImpl();
		model.getInfo(Url.shopindex, this);
	}

	/** 弹出选项框 设置小店头像方法选择 */
	private void ShowPickDialog() {
		final SweetAlertDialog sd = new SweetAlertDialog(this,
				SweetAlertDialog.PAY_TYPE);
		sd.setTitleText("设置小店头像");
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
						Environment.getExternalStorageDirectory(),
						"storelogo.jpg")));
				startActivityForResult(intent, 2);
			}
		});
		sd.show();
	}

	/** 弹出选项框 设置小店 店招选择 */
	private void ShowPickDialog2() {
		final SweetAlertDialog sd2 = new SweetAlertDialog(this,
				SweetAlertDialog.PAY_TYPE);
		sd2.setTitleText("设置小店店招");
		sd2.setCancelable(true);
		sd2.setCanceledOnTouchOutside(true);
		sd2.setFirstBtnText("相册");
		sd2.setSecondBtnText("拍照");

		sd2.setAliPayClickListener(new SweetAlertDialog.OnSweetClickListener() {

			@Override
			public void onClick(SweetAlertDialog sweetAlertDialog) {
				sd2.dismiss();
				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(intent, 4);
			}
		});
		sd2.setWxPayClickListener(new SweetAlertDialog.OnSweetClickListener() {

			@Override
			public void onClick(SweetAlertDialog sweetAlertDialog) {
				sd2.dismiss();
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				// 下面这句指定调用相机拍照后的照片存储的路径
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
						Environment.getExternalStorageDirectory(),
						"storelogo.jpg")));
				startActivityForResult(intent, 5);
			}
		});
		sd2.show();
	}

	/** 剪裁小店头像 */
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

	/** 剪裁 店招 */
	public void startPhotoZoom2(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 5);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 500);
		intent.putExtra("outputY", 100);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 6);
	}

	/**
	 * 保存裁剪之后的图片数据 头像
	 * 
	 * @param picdata
	 */
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			photo = MapChangeTool.changeMapSize(photo);
			picfile = MapChangeTool.bitmapToBase64(photo);
			model.setLogo(Url.upfilelogo, shopid, picfile, this);
		}
	}

	/**
	 * 保存裁剪之后的图片数据 店招
	 * 
	 * @param picdata
	 */
	private void setPicToView2(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			photo = MapChangeTool.changeMapSize(photo,750,150);
			picfile2 = MapChangeTool.bitmapToBase64(photo);
			model.setImg(Url.upfileimg, shopid, picfile2, this);
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
			if (Environment.getExternalStorageDirectory() + "/storelogo.jpg" != null) {
				File temp = new File(Environment.getExternalStorageDirectory()
						+ "/storelogo.jpg");
				startPhotoZoom(Uri.fromFile(temp));
			}
			break;
		// 取得裁剪后的图片
		case 3:
			if (data != null) {
				setPicToView(data);
			}
			break;
		case 4:
			// 非空判断，否则不选图片直接返回 date为空 出现空指针错误
			if (data != null) {
				startPhotoZoom2(data.getData());
			}
			break;
		// 如果是调用相机拍照时
		case 5:
			if (Environment.getExternalStorageDirectory() + "/storelogo.jpg" != null) {
				File temp = new File(Environment.getExternalStorageDirectory()
						+ "/storelogo.jpg");
				startPhotoZoom2(Uri.fromFile(temp));
			}
			break;
		// 取得裁剪后的图片
		case 6:
			if (data != null) {
				setPicToView2(data);
			}
			break;
		default:
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 获取小店信息 结果返回成功
	 */
	@Override
	public void onSuccess(StoreBean bean) {
		// TODO Auto-generated method stub
		shopid = bean.shopid;
		imageLoader.displayImage(bean.logo, Img_storelogo, options3);
		imageLoader.displayImage(bean.img, Img_storeimg, options);
		Tv_name.setText(bean.name);
		Et_store_introduce.setText(bean.desc);
		Et_store_introduce.setSelection(Et_store_introduce.length());
		Et_store_introduce.setVisibility(View.VISIBLE);
	}

	/**
	 * 设置头像结果返回成功
	 */
	@Override
	public void onLogo() {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, "小店头像设置成功");
		model.getInfo(Url.shopindex, this);
	}
	
	/**
	 * 设置店招 结果返回成功
	 */
	@Override
	public void onImg() {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, "小店店招设置成功");
		model.getInfo(Url.shopindex, this);
	}

	/**
	 * 设置简介 结果返回成功
	 */
	@Override
	public void ondesc() {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, "小店信息设置成功");
		finish();
	}


	/**
	 * 网络请求 结果返回失败
	 */
	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, error);
	}

	/**
	 * 广播事件
	 */
	public void onEventMainThread(StoreEvent event) {
		if(event.getType().equals("storename"))
		{
		String msg = event.getMsg();
		Tv_name.setText(msg);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

}