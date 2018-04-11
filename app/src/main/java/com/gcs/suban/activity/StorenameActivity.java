package com.gcs.suban.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.eventbus.StoreEvent;
import com.gcs.suban.listener.OnStorenameListener;
import com.gcs.suban.model.StoreModel;
import com.gcs.suban.model.StoreModelImpl;
import com.gcs.suban.tools.ToastTool;

import io.rong.eventbus.EventBus;


public class StorenameActivity extends BaseActivity implements
		OnStorenameListener {
	private EditText Et_storename;

	private ImageButton IBtn_back;

	private Button Btn_confirm;

	private StoreModel model;

	private String shopid;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.btn_confirm:
			if (Et_storename.getText().toString() != null) {
				model.setName(Url.shopnamemod, shopid, Et_storename.getText()
						.toString(), this);
			} else {
				ToastTool.showToast(context, "昵称不能为空");
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		shopid = intent.getStringExtra("shopid");
		setContentView(R.layout.activity_storename);
		Et_storename = (EditText) context.findViewById(R.id.et_storename);
		Btn_confirm = (Button) context.findViewById(R.id.btn_confirm);
		IBtn_back = (ImageButton) context.findViewById(R.id.back);
		Btn_confirm.setOnClickListener(this);
		IBtn_back.setOnClickListener(this);

		model = new StoreModelImpl();
	}

	/**
	 * 网络请求 结果返回成功
	 */
	@Override
	public void onSuccess() {
		// TODO Auto-generated method stub
		EventBus.getDefault().post(
				new StoreEvent("storename", Et_storename.getText().toString()));
		ToastTool.showToast(context, "小店名称设置成功");
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

}
