package com.gcs.suban.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.AddressBean;
import com.gcs.suban.eventbus.AddressEvent;
import com.gcs.suban.listener.OnBaseListener;
import com.gcs.suban.model.AddressModel;
import com.gcs.suban.model.AddressModelImpl;
import com.gcs.suban.tools.ToastTool;
import com.padant.liveselect.LiveAddressSelect;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.rong.eventbus.EventBus;

/**
 * 添加收货地址
 */
public class AddressAddActivity extends BaseActivity implements
OnBaseListener {

	private TextView Tv_title;
	private TextView Tv_province;

	private ImageButton Ibtn_back;

	private Button Btn_save;

	private EditText Et_name;
	private EditText Et_tel;
	private EditText Et_address;

	private RelativeLayout Rlyt_province;

	private String province;
	private String city;
	private String area;

	private AddressModel addModel;
	private AddressBean bean = new AddressBean("", "", "", "", "", "", "", "");

	private String url;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.rlyt_province:
			selectLive();
			break;
		case R.id.btn_confirm:
			// 判断输入框是否填写完整
			if (Et_name.getText().length() != 0
				&& Et_tel.getText().length() != 0
				&& Tv_province.getText().length() != 0
				&& Et_address.getText().length() != 0) 
			{bean.realname = Et_name.getText().toString();
				bean.mobile = Et_tel.getText().toString();
				bean.address = Et_address.getText().toString();
				bean.province = province;
				bean.city = city;
				bean.area = area;
				addModel.setAddress(getUrl(), bean, this);} 
			else {
				ToastTool.showToast(getApplicationContext(), "请填写完整信息");
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_address_add);
		Tv_title = (TextView) findViewById(R.id.title);
		Tv_title.setText("添加收货地址");
		Tv_province = (TextView) findViewById(R.id.tv_province);
		Btn_save = (Button) findViewById(R.id.btn_confirm);
		Ibtn_back = (ImageButton) findViewById(R.id.back);
		Et_name = (EditText) findViewById(R.id.et_name);
		Et_tel = (EditText) findViewById(R.id.et_tel);
		Et_address = (EditText) findViewById(R.id.et_address);
		Rlyt_province = (RelativeLayout) findViewById(R.id.rlyt_province);

		Rlyt_province.setOnClickListener(this);
		Btn_save.setOnClickListener(this);
		Ibtn_back.setOnClickListener(this);

		addModel = new AddressModelImpl();
	}

	/**
	 * 获取url地址
	 */
	private String getUrl() {
		url = Url.addaddress;
		return url;
	}

	/**
	 * 网络请求 结果返回成功
	 */
	@Override
	public void onSuccess(String resulttext) {
		// TODO Auto-generated method stub
		EventBus.getDefault().post(new AddressEvent("updata", "msg"));
		context.finish();
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
	 * 省市区选择
	 */
	private void selectLive() {
		final SweetAlertDialog sd = new SweetAlertDialog(context,
				SweetAlertDialog.ADDRESS_TYPE);
		sd.setConfirmText("确定");
		sd.setCancelText("取消");
		sd.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
			@Override
			public void onClick(SweetAlertDialog sDialog) {
				province = LiveAddressSelect.mCurrentProviceName;
				city = LiveAddressSelect.mCurrentCityName;
				area = LiveAddressSelect.mCurrentDistrictName;
				Tv_province.setText(province + " " + city + " " + area);
				sd.dismiss();
			}
		});
		sd.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
			@Override
			public void onClick(SweetAlertDialog sDialog) {
				sd.dismiss();
			}
		});
		sd.show();
	}

}
