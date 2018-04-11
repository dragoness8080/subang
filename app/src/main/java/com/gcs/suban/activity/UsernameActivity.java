package com.gcs.suban.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.eventbus.PersonEvent;
import com.gcs.suban.listener.OnNicknameListener;
import com.gcs.suban.model.NicknameModel;
import com.gcs.suban.model.NicknameModelImpl;
import com.gcs.suban.tools.ToastTool;

import io.rong.eventbus.EventBus;


/**
 * 修改昵称
 */
public class UsernameActivity extends BaseActivity implements OnNicknameListener{
	private EditText  Et_nickname;
	
	private ImageButton IBtn_back;
	
	private Button Btn_confirm;
	
	private String url;
	
	private NicknameModel model;
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.btn_confirm:
			if(Et_nickname.getText().toString()!=null)
			{
			model.getInfo(getUrl(), Et_nickname.getText().toString(), this);
			}
			else {
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
		setContentView(R.layout.activity_username);
		Et_nickname=(EditText)context.findViewById(R.id.et_nickname);
		Btn_confirm=(Button)context.findViewById(R.id.btn_confirm);
		IBtn_back = (ImageButton) context.findViewById(R.id.back);
		Btn_confirm.setOnClickListener(this);
		IBtn_back.setOnClickListener(this);
		
		model=new NicknameModelImpl();
	}
	
	/**
	 * 获取url地址
	 */
	private String getUrl() {
		url = Url.nickname;
		return url;
	}
	
	/**
	 * 网络请求 结果返回成功
	 */
	@Override
	public void onSuccess(String result) {
		// TODO Auto-generated method stub
		EventBus.getDefault().post(new PersonEvent("nickname",Et_nickname.getText().toString()));
		ToastTool.showToast(context, result);
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
