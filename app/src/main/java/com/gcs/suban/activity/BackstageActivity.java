package com.gcs.suban.activity;


import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.BackstageBean;
import com.gcs.suban.eventbus.CenterEvent;
import com.gcs.suban.eventbus.PersonEvent;
import com.gcs.suban.listener.OnBackstageListener;
import com.gcs.suban.listener.OnBaseListener;
import com.gcs.suban.model.BackstageModel;
import com.gcs.suban.model.BackstageModelImpl;
import com.gcs.suban.model.WithDrawModelImpl;
import com.gcs.suban.model.WithdrawModel;
import com.gcs.suban.tools.ToastTool;

import io.rong.eventbus.EventBus;


public class BackstageActivity extends BaseActivity implements OnBaseListener,OnBackstageListener, OnRefreshListener{
	
	private TextView Tv_title;
	private TextView Tv_record;
	
	private TextView Tv_commission_ok;
	private TextView Tv_commission_total;
	private TextView Tv_commission_pay;
	private TextView Tv_commission_apply;
	private TextView Tv_commission_check;
	private TextView Tv_commission_lock;
	
	private ImageButton IBtn_back;
	
	private Button Btn_balance;
	private Button Btn_weixin;
	private Button Btn_bank;

	private String type;
	
	private BackstageModel backstageModel;
	private WithdrawModel withdrawModel;

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
		case R.id.tv_record:
			Intent record = new Intent(context,
					CommissionRecordActivity.class);
			startActivity(record);
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
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_backstage);
		Tv_title = (TextView) findViewById(R.id.title);
		Tv_title.setText("我的后台");
		
		swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout_backstage);
		swipeRefreshLayout.setOnRefreshListener(this);
		InitSwipeRefreshLayout();
		Tv_commission_total= (TextView) findViewById(R.id.tv_commission_total);
		Tv_commission_ok= (TextView) findViewById(R.id.tv_commission_ok);
		Tv_commission_pay= (TextView) findViewById(R.id.tv_commission_pay);
		Tv_commission_apply= (TextView) findViewById(R.id.tv_commission_apply);
		Tv_commission_check= (TextView) findViewById(R.id.tv_commission_check);
		Tv_commission_lock= (TextView) findViewById(R.id.tv_commission_lock);
		
		Tv_record= (TextView) findViewById(R.id.tv_record);
		Tv_record.setOnClickListener(this);
		
		Btn_balance= (Button) findViewById(R.id.btn_balance);
		Btn_balance.setOnClickListener(this);
		
		Btn_weixin= (Button) findViewById(R.id.btn_weixin);
		Btn_weixin.setOnClickListener(this);
		
		Btn_bank= (Button) findViewById(R.id.btn_bank);
		Btn_bank.setOnClickListener(this);
		
		IBtn_back = (ImageButton) findViewById(R.id.back);
		IBtn_back.setOnClickListener(this);
		
		hideView();
		
		backstageModel=new BackstageModelImpl();
		swipeRefreshLayout.post(new Runnable() {
			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(true);
			}
		});
		this.onRefresh();

		withdrawModel=new WithDrawModelImpl();
	}
	
	/**
	 * 后台信息请求 结果返回成功
	 */
	@Override
	public void onBackstage(BackstageBean bean) {
		swipeRefreshLayout.setRefreshing(false);
		Tv_commission_total.setText(bean.commission_total);
		Tv_commission_ok.setText(bean.commission_ok);
		Tv_commission_pay.setText(bean.commission_pay);
		Tv_commission_apply.setText(bean.commission_apply);
		Tv_commission_check.setText(bean.commission_check);
		Tv_commission_lock.setText(bean.commission_lock);
		
		showView();
	}	
	
	public void withdraw()
	{
		dialog.show();
		withdrawModel.onCommissionWirhdraw(Url.commissionup, type, this);
	}

	/**
	 * 提现请求 结果返回成功
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
		swipeRefreshLayout.setRefreshing(false);
		ToastTool.showToast(context, error);
	}

	private void hideView() {
		Tv_commission_total.setVisibility(View.INVISIBLE);
		Tv_commission_ok.setVisibility(View.INVISIBLE);
		Tv_commission_pay.setVisibility(View.INVISIBLE);
		Tv_commission_apply.setVisibility(View.INVISIBLE);
		Tv_commission_check.setVisibility(View.INVISIBLE);
		Tv_commission_lock.setVisibility(View.INVISIBLE);

		Btn_balance.setClickable(false);
		Btn_bank.setClickable(false);
		Btn_weixin.setClickable(false);
	}

	private void showView() {
		Tv_commission_total.setVisibility(View.VISIBLE);
		Tv_commission_ok.setVisibility(View.VISIBLE);
		Tv_commission_pay.setVisibility(View.VISIBLE);
		Tv_commission_apply.setVisibility(View.VISIBLE);
		Tv_commission_check.setVisibility(View.VISIBLE);
		Tv_commission_lock.setVisibility(View.VISIBLE);

		Btn_balance.setClickable(true);
		Btn_bank.setClickable(true);
		Btn_weixin.setClickable(true);
	}
	
	/**
	 * 下拉刷新
	 */
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		backstageModel.getInfo(Url.mycommission, this);
	}

}
