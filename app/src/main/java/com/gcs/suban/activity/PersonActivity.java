package com.gcs.suban.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.BankBean;
import com.gcs.suban.bean.MemberBean;
import com.gcs.suban.eventbus.PersonEvent;
import com.gcs.suban.listener.OnPersonListener;
import com.gcs.suban.model.PersonModeImpl;
import com.gcs.suban.model.PersonModel;
import com.gcs.suban.tools.MapChangeTool;
import com.gcs.suban.tools.ToastTool;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.rong.eventbus.EventBus;


/**
 * ������Ϣ
 */
public class PersonActivity extends BaseActivity implements OnPersonListener {
	private TextView Tv_title;
	private TextView Tv_username;
	private TextView Tv_phone;
	private TextView Tv_sex;
	private TextView Tv_bank;

	private ImageButton IBtn_back;

	private ImageView Img_logo;

	private CardView Cv_logo;
	private CardView Cv_username;
	private CardView Cv_sex;
	private CardView Cv_phone;
	private CardView Cv_bank;

	private String picfile;
	private String sex;
	private int isbankcard;

	private Bitmap photo;

	private PersonModel model;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.card_bank:
			if(isbankcard==0)
			{
			Intent intent_bank = new Intent(context, BankBundingActivity.class);
			startActivity(intent_bank);
			}
			else
			{
				Intent intent_bank = new Intent(context, BankActivity.class);
				startActivity(intent_bank);
			}
			break;
		case R.id.card_logo:
			// �����û�ͷ��
			ShowPickDialog();
			break;
		case R.id.card_username:
			Intent intent_username = new Intent(context, UsernameActivity.class);
			startActivityForResult(intent_username, 4);
			break;
		case R.id.card_sex:
			SexPickDialog();
			break;
		case R.id.card_phone:
			Intent intent_phone = new Intent(context, PhoneActivity.class);
			startActivity(intent_phone);
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
		setContentView(R.layout.activity_person);
		
		Tv_title = (TextView) findViewById(R.id.title);
		Tv_title.setText("��������");
		Tv_username = (TextView) context.findViewById(R.id.tv_username);
		Tv_phone = (TextView) context.findViewById(R.id.tv_phone);
		Tv_sex = (TextView) context.findViewById(R.id.tv_sex);
		Tv_bank=(TextView) context.findViewById(R.id.tv_bank);
		
		Img_logo = (ImageView) context.findViewById(R.id.img_logo);
		
		Cv_logo = (CardView) context.findViewById(R.id.card_logo);
		Cv_username = (CardView) context.findViewById(R.id.card_username);
		Cv_sex = (CardView) context.findViewById(R.id.card_sex);
		Cv_phone = (CardView) context.findViewById(R.id.card_phone);
		Cv_bank=(CardView)findViewById(R.id.card_bank);
		
		IBtn_back = (ImageButton) context.findViewById(R.id.back);
		
		Cv_logo.setOnClickListener(this);
		Cv_username.setOnClickListener(this);
		Cv_sex.setOnClickListener(this);
		Cv_phone.setOnClickListener(this);
		Cv_bank.setOnClickListener(this);
		IBtn_back.setOnClickListener(this);

		model = new PersonModeImpl();
		model.getInfo(Url.person, this);
	}

	/**
	 * �������� ������سɹ�
	 */
	@Override
	public void onSuccess(MemberBean bean,BankBean bean2) {
		// TODO Auto-generated method stub
		imageLoader.displayImage(bean.avatar, Img_logo, options3);
		Tv_username.setText(bean.nickname);
		Tv_phone.setText(bean.mobile);
		sex = bean.gender;
		isSex();
		
		if(bean2.isbankcard.equals("1"))
		{
			Tv_bank.setText(bean2.bankname+"  "+bean2.weihao);
			isbankcard=1;
		}
		else
		{
			Tv_bank.setText("�����п�");
			isbankcard=0;
		}
	}

	/**
	 * ����ͷ�� ������سɹ�
	 */
	@Override
	public void onLogo(String result, String avatar) {
		// TODO Auto-generated method stub
		EventBus.getDefault().post(new PersonEvent("logo", avatar));
		imageLoader.displayImage(avatar, Img_logo, options3);
		ToastTool.showToast(context, result);
	}

	/**
	 * �����Ա� ������سɹ�
	 */
	@Override
	public void onSex(String result) {
		// TODO Auto-generated method stub
		isSex();
		ToastTool.showToast(context, result);
	}

	/**
	 * �������� �������ʧ��
	 */
	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, error);
	}	

	/** �Ա�ѡ�񵯴� */
	private void SexPickDialog() {
		final SweetAlertDialog sd2 = new SweetAlertDialog(this,
				SweetAlertDialog.PAY_TYPE);
		sd2.setTitleText("�����Ա�");
		sd2.setCancelable(true);
		sd2.setCanceledOnTouchOutside(true);
		sd2.setFirstBtnText("��");
		sd2.setSecondBtnText("Ů");
		sd2.setAliPayClickListener(new SweetAlertDialog.OnSweetClickListener() {

			@Override
			public void onClick(SweetAlertDialog sweetAlertDialog) {
				sd2.dismiss();
				sex = "1";
				Tv_sex.setText("��");
				changeSex();
			}
		});
		sd2.setWxPayClickListener(new SweetAlertDialog.OnSweetClickListener() {

			@Override
			public void onClick(SweetAlertDialog sweetAlertDialog) {
				sd2.dismiss();
				sex = "2";
				Tv_sex.setText("Ů");
				changeSex();
			}
		});
		sd2.show();
	}

	/**
	 * �Ա���������
	 */
	public void changeSex() {
		// TODO Auto-generated method stub
		model.setSex(Url.gendermod, sex, this);
	}

	/**
	 * �Ա���ʾ
	 */
	public void isSex() {
		// TODO Auto-generated method stub
		if (sex.equals("1")) {
			Tv_sex.setText("��");
		} else {
			Tv_sex.setText("Ů");
		}
	}

	/** ����ѡ��� ����ͷ�񷽷�ѡ�� */
	private void ShowPickDialog() {
		final SweetAlertDialog sd = new SweetAlertDialog(this,
				SweetAlertDialog.PAY_TYPE);
		sd.setTitleText("����ͷ��");
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
			model.setLogo(Url.avatarmod, picfile, this);
		}
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

	/**
	 * �㲥�¼�
	 */
	public void onEventMainThread(PersonEvent event) {
		String msg = event.getMsg();
		if (event.getType().equals("phone")) {
			Tv_phone.setText(msg);
		} else if (event.getType().equals("nickname")) {
			Tv_username.setText(msg);
		}
		else if (event.getType().equals("bank")) {
			model.getInfo(Url.person, this);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

}
