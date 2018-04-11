package com.gcs.suban.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.BankBean;
import com.gcs.suban.listener.OnBankInfoListener;
import com.gcs.suban.model.BankModel;
import com.gcs.suban.model.BankModelImpl;
import com.gcs.suban.tools.ToastTool;


public class BankActivity extends BaseActivity implements OnBankInfoListener{
	
	private TextView Tv_title;
	private TextView Tv_bank;
	
	private ImageButton IBtn_back;
	
	private Button Btn_confirm;
	
	private BankModel model;
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.btn_confirm:
			Intent intent =new Intent(context,BankBundingActivity.class);
			startActivity(intent);
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_bank);
		Tv_title = (TextView) findViewById(R.id.title);
		Tv_title.setText("绑定银行卡");
		Tv_bank = (TextView) findViewById(R.id.tv_bank);
		
		IBtn_back = (ImageButton) context.findViewById(R.id.back);
		IBtn_back.setOnClickListener(this);
		
		Btn_confirm= (Button) context.findViewById(R.id.btn_confirm);
		Btn_confirm.setOnClickListener(this);
		
		model=new BankModelImpl();
		model.getInfo(Url.bankcard, this);
	}

	/**
	 * 网络请求成功
	 */
	@Override
	public void onSuccess(BankBean bean) {
		// TODO Auto-generated method stub
		Tv_bank.setText(bean.bankname+"  "+bean.weihao);
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
