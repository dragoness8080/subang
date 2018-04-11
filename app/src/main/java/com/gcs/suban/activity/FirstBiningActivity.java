package com.gcs.suban.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gcs.suban.MyDate;
import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.listener.OnPhoneListener;
import com.gcs.suban.model.PersonModeImpl;
import com.gcs.suban.model.PersonModel;
import com.gcs.suban.tools.SharedPreference;
import com.gcs.suban.tools.TimerCountTool;
import com.gcs.suban.tools.ToastTool;

public class FirstBiningActivity extends BaseActivity implements OnPhoneListener{

	private EditText Et_phone;
	private EditText Et_code;

	private Button Btn_send;
	private Button Btn_login;

	private String url;
	private String mobile;
	private String code;

	private PersonModel model;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_send:
			getCode();
			break;
		case R.id.btn_login:
			setPhone();
			break;
		default:
			break;
		}
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_firstbinding);	
		
		Et_phone = (EditText) context.findViewById(R.id.et_phone);
		Et_code = (EditText) context.findViewById(R.id.et_code);
		
		Btn_send = (Button) context.findViewById(R.id.btn_send);
		Btn_send.setOnClickListener(this);
		
		Btn_login=(Button)findViewById(R.id.btn_login);
		Btn_login.setOnClickListener(this);
		
		model = new PersonModeImpl();
	}
	
	/**
	 * 获取验证码
	 */
	private void getCode() {
		mobile = Et_phone.getText().toString();
		if (mobile.length() == 11) {
			url = Url.getcode + "?phone=" + mobile;
			model.getCode(url, this);
		} else {
			ToastTool.showToast(context, "手机号码格式不正确");
		}
	}

	/**
	 * 设置手机号码
	 */
	private void setPhone() {
		mobile = Et_phone.getText().toString();
		if (mobile.length() != 11) {
			ToastTool.showToast(context, "手机号码格式不正确");
			return;
		}
		code = Et_code.getText().toString();
		if (code.length() != 6) {
			ToastTool.showToast(context, "请输入正确的验证码");
			return;
		}
		model.setPhone(Url.phoneset, mobile, code, this);
	}

	/**
	 * 获取验证码成功
	 */
	@Override
	public void onCode(String result) {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, result);
		TimerCountTool timer = new TimerCountTool(60000, 1000, Btn_send);
		timer.start();
	}

	/**
	 * 绑定手机号成功
	 */
	@Override
	public void onSuccess(String result) {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, result);
		SharedPreference.setParam(context, "vid", MyDate.getMyVid());
		SharedPreference.setParam(context, "jpushid", MyDate.getJpushid());
		Intent intent = new Intent(context, MainActivity.class);
		startActivity(intent);
		finish();
	}

	/**
	 * 网络请求失败
	 */
	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, error);
	}

}
