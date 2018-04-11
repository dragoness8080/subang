package com.gcs.suban.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.ReturnBean;
import com.gcs.suban.eventbus.OrderEvent;
import com.gcs.suban.listener.OnImgUpListener;
import com.gcs.suban.listener.OnReturnOrderListener;
import com.gcs.suban.model.ReternModelImpl;
import com.gcs.suban.model.ReturnModel;
import com.gcs.suban.tools.MapChangeTool;
import com.gcs.suban.tools.ToastTool;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.rong.eventbus.EventBus;


public class ReturnActivity extends BaseActivity implements OnReturnOrderListener,OnImgUpListener {

	private TextView Tv_title;
	private TextView Tv_goodstitle;
	private TextView Tv_price;
	private TextView Tv_ordersn;
	private TextView Tv_time;
	private TextView Tv_refundmoney;
	
	private ImageButton IBtn_back;

	private CardView Cv_file;
	
	private EditText Et_reason;
	private EditText Et_phone;
	
	private Button Btn_confirm;

	private ReturnModel model;
	
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
	
	//private String[] url=new String[3];
	//private ImageView[] imageViews=new ImageView[3];

	private Bitmap photo;
	
	private int i;
	
	private ReturnBean bean=new ReturnBean();

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.btn_confirm:
			returnup();
			break;
		case R.id.card_file:
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
		bean.orderid = intent.getStringExtra("orderid");
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_return);
		Tv_title = (TextView) findViewById(R.id.title);
		Tv_title.setText("�˻�����");
		Tv_goodstitle= (TextView) findViewById(R.id.tv_goodstitle);
		Tv_price= (TextView) findViewById(R.id.tv_price);
		Tv_ordersn= (TextView) findViewById(R.id.tv_ordersn);
		Tv_time= (TextView) findViewById(R.id.tv_time);
		Tv_refundmoney= (TextView) findViewById(R.id.tv_refundmoney);
		
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
		
		Cv_file=(CardView)findViewById(R.id.card_file);
		Cv_file.setOnClickListener(this);
		
		Et_reason = (EditText) context.findViewById(R.id.et_reason);
		Et_reason.clearFocus();
		Et_phone= (EditText) context.findViewById(R.id.et_phone);
		Et_phone.clearFocus();
		
		Btn_confirm = (Button) context.findViewById(R.id.btn_confirm);
		Btn_confirm.setOnClickListener(this);
		
		IBtn_back = (ImageButton) context.findViewById(R.id.back);
		IBtn_back.setOnClickListener(this);
		
		showView();
		
		model=new ReternModelImpl();
		model.getInfo(Url.refundinfo, bean.orderid, this);
	}
	
	/**
	 * ��ȡ�˿����� �ɹ�
	 */
	@Override
	public void onInfo(ReturnBean bean) {
		// TODO Auto-generated method stub
		Tv_goodstitle.setText(bean.goodstitle);
		Tv_ordersn.setText(bean.ordersn);
		Tv_time.setText(bean.time);
		Tv_price.setText("��" +bean.price);
		Tv_refundmoney.setText("��" +bean.refundmoney);
	}
	
	/**
	 * �˿����� �ɹ�
	 */
	@Override
	public void onConfirm(String resulttext) {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, resulttext);
		EventBus.getDefault().post(
				new OrderEvent("update","msg"));
		finish();
	}

	/**
	 * ��������ʧ��
	 */
	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, error);
	}

	/** ѡȡ��Ƭ�󷵻��Զ����� */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// �����ֱ�Ӵ�����ȡ
		case 1:
			// �ǿ��жϣ�����ѡͼƬֱ�ӷ��� dateΪ�� ���ֿ�ָ�����
			if (data != null) {
				startPhotoZoom(data.getData());
			}
			break;
		// ����ǵ����������ʱ
		case 2:
			if (Environment.getExternalStorageDirectory() + "/logo.jpg" != null) {
				File temp = new File(Environment.getExternalStorageDirectory()
						+ "/logo.jpg");
				startPhotoZoom(Uri.fromFile(temp));
			}
			break;
		// ȡ�òü����ͼƬ
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
	
	/** ����ѡ��� �ϴ�ͼƬ */
	private void ShowPickDialog() {
		final SweetAlertDialog sd = new SweetAlertDialog(this,
				SweetAlertDialog.PAY_TYPE);
		sd.setTitleText("�ϴ�ͼƬ");
		sd.setCancelable(true);
		sd.setCanceledOnTouchOutside(true);
		sd.setFirstBtnText("���");
		sd.setSecondBtnText("����");

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
				// �������ָ������������պ����Ƭ�洢��·��
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
		// �������crop=true�������ڿ�����Intent��������ʾ��VIEW�ɲü�
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);
	}

	/**
	 * ����ü�֮���ͼƬ����
	 * 
	 * @param picdata
	 */
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			photo = extras.getParcelable("data");
			photo = MapChangeTool.changeMapSize(photo);
			picfile = MapChangeTool.bitmapToBase64(photo);
			model.UpImg(Url.refundimgup, picfile, this);
		}
	}
	
	private void returnup() {
		bean.mobile=Et_phone.getText().toString();
		bean.reason=Et_reason.getText().toString();
		String url=	url1+url2+url3;
		if(url.length()!=0)
		{
		url = url.substring(0, url.length() - 1);
		}
		bean.refund_img=url;
		model.onApply(Url.refund, bean, this);
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
	
}
