package com.gcs.suban.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.listener.OnPhoneListener;
import com.gcs.suban.model.PersonModeImpl;
import com.gcs.suban.model.PersonModel;
import com.gcs.suban.tools.TimerCountTool;
import com.gcs.suban.tools.ToastTool;
/**
 * 忘记密码   设置新密码
 */
public class ForgetPwdActivity extends BaseActivity implements OnPhoneListener {

	private TextView Tv_title;

	private EditText Et_phone;
	private EditText Et_code;
	private EditText Et_npaypwd1;
	private EditText Et_npaypwd2;

	private ImageButton IBtn_back;

	private Button Btn_send;
	private Button Btn_confirm;

	private String url;
	private String mobile;
	private String code;
	private String npaypwd1;
	private String npaypwd2;

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
			setPassword();
			break;
		default:
			break;
		}
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_forgetpwd);
		Tv_title = (TextView) findViewById(R.id.title);
		Tv_title.setText("忘记密码");

		Et_phone = (EditText) context.findViewById(R.id.et_phone);
		Et_code = (EditText) context.findViewById(R.id.et_code);
		Et_npaypwd1 = (EditText) context.findViewById(R.id.et_npaypwd1);
		Et_npaypwd2 = (EditText) context.findViewById(R.id.et_npaypwd2);

		Btn_send = (Button) context.findViewById(R.id.btn_send);
		Btn_send.setOnClickListener(this);

		Btn_confirm = (Button) context.findViewById(R.id.btn_confirm);
		Btn_confirm.setOnClickListener(this);

		IBtn_back = (ImageButton) context.findViewById(R.id.back);
		IBtn_back.setOnClickListener(this);

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
	private void setPassword() {
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
		npaypwd1 = Et_npaypwd1.getText().toString();
		npaypwd2 = Et_npaypwd2.getText().toString();
		if (npaypwd1.length() != 6 || npaypwd2.length() != 6) {
			ToastTool.showToast(context, "支付密码必须为6位");
			return;
		}
		if (!npaypwd1.equals(npaypwd2)) {
			ToastTool.showToast(context, "两次密码输入必须一致");
			return;
		}
		model.setPwd(Url.forgetpwd, mobile, code, npaypwd1, this);
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
	 * 绑定成功
	 */
	@Override
	public void onSuccess(String result) {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, result);
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
