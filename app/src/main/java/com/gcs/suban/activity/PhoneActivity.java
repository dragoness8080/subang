package com.gcs.suban.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.eventbus.PersonEvent;
import com.gcs.suban.listener.OnPhoneListener;
import com.gcs.suban.model.PersonModeImpl;
import com.gcs.suban.model.PersonModel;
import com.gcs.suban.tools.TimerCountTool;
import com.gcs.suban.tools.ToastTool;

import io.rong.eventbus.EventBus;


/**
 * �����ֻ�����
 */
public class PhoneActivity extends BaseActivity implements OnPhoneListener {

	private TextView Tv_title;

	private EditText Et_phone;
	private EditText Et_code;

	private ImageButton IBtn_back;

	private Button Btn_send;
	private Button Btn_confirm;

	private String url;
	private String mobile;
	private String code;

	private PersonModel model;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.btn_send:
			getCode();
			break;
		case R.id.btn_confirm:
			setPhone();
			break;
		default:
			break;
		}
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_phone);
		Tv_title = (TextView) findViewById(R.id.title);
		Tv_title.setText("�����ֻ�");
		Et_phone = (EditText) context.findViewById(R.id.et_phone);
		Et_code = (EditText) context.findViewById(R.id.et_code);
		Btn_send = (Button) context.findViewById(R.id.btn_send);
		Btn_confirm = (Button) context.findViewById(R.id.btn_confirm);
		IBtn_back = (ImageButton) context.findViewById(R.id.back);
		Btn_send.setOnClickListener(this);
		Btn_confirm.setOnClickListener(this);
		IBtn_back.setOnClickListener(this);

		model = new PersonModeImpl();
	}

	/**
	 * ��ȡ��֤��
	 */
	private void getCode() {
		mobile = Et_phone.getText().toString();
		if (mobile.length() == 11) {
			url = Url.getcode + "?phone=" + mobile;
			model.getCode(url, this);
		} else {
			ToastTool.showToast(context, "�ֻ������ʽ����ȷ");
		}
	}

	/**
	 * �����ֻ�����
	 */
	private void setPhone() {
		mobile = Et_phone.getText().toString();
		if (mobile.length() != 11) {
			ToastTool.showToast(context, "�ֻ������ʽ����ȷ");
			return;
		}
		code = Et_code.getText().toString();
		if (code.length() != 6) {
			ToastTool.showToast(context, "��������ȷ����֤��");
			return;
		}
		model.setPhone(Url.phonemod, mobile, code, this);
	}

	/**
	 * ��ȡ��֤��ɹ�
	 */
	@Override
	public void onCode(String result) {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, result);
		TimerCountTool timer = new TimerCountTool(60000, 1000, Btn_send);
		timer.start();
	}

	/**
	 * ���ֻ��ųɹ�
	 */
	@Override
	public void onSuccess(String result) {
		// TODO Auto-generated method stub
		EventBus.getDefault().post(new PersonEvent("phone",mobile));
		ToastTool.showToast(context, result);
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
}
