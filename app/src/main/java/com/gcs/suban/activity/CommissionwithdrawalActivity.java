package com.gcs.suban.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.eventbus.CenterEvent;
import com.gcs.suban.eventbus.PersonEvent;
import com.gcs.suban.listener.OnBaseListener;
import com.gcs.suban.model.WithDrawModelImpl;
import com.gcs.suban.model.WithdrawModel;
import com.gcs.suban.tools.ToastTool;

import io.rong.eventbus.EventBus;


/**
 * 佣金提现
 */
public class CommissionwithdrawalActivity extends BaseActivity implements OnBaseListener{
	
	private TextView Tv_title;
	private TextView Tv_money;
	
	private ImageButton IBtn_back;

	private Button Btn_balance;
	private Button Btn_weixin;
	private Button Btn_bank;
	
	private String money;
	private String type;
	
	private WithdrawModel model;
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_balance:
			type="0";
			withdraw();
			break;
		case R.id.btn_weixin:
			type="1";
			withdraw();
			break;
		case R.id.btn_bank:
			type="2";
			withdraw();
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
		Intent intent = getIntent();
		money = intent.getStringExtra("money");
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_commissionwithdrawal);
		Tv_title = (TextView) findViewById(R.id.title);
		Tv_title.setText("佣金提现");
		Tv_money= (TextView) findViewById(R.id.tv_moeny);
		Tv_money.setText(money+"元");
		
		Btn_balance= (Button) findViewById(R.id.btn_balance);
		Btn_balance.setOnClickListener(this);
		
		Btn_weixin= (Button) findViewById(R.id.btn_weixin);
		Btn_weixin.setOnClickListener(this);
		
		Btn_bank= (Button) findViewById(R.id.btn_bank);
		Btn_bank.setOnClickListener(this);
		
		IBtn_back = (ImageButton) findViewById(R.id.back);
		IBtn_back.setOnClickListener(this);
		
		model=new WithDrawModelImpl();
	}
	
	public void withdraw()
	{
		dialog.show();
		model.onCommissionWirhdraw(Url.commissionup, type, this);
	}
	
	/**
	 * 网络请求 结果返回成功
	 */
	@Override
	public void onSuccess(String resulttext) {
		// TODO Auto-generated method stub
		dialog.dismiss();
		ToastTool.showToast(context, resulttext);
		EventBus.getDefault().post(new CenterEvent("what", "msg"));
		EventBus.getDefault().post(new PersonEvent("what", "msg"));
		finish();
	}
	/**
	 * 网络请求 结果返回失败
	 */
	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		dialog.dismiss();
		ToastTool.showToast(context, error);
	}

}
