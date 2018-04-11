package com.gcs.suban.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.activity.CommissionRecordActivity;
import com.gcs.suban.activity.CommissionwithdrawalActivity;
import com.gcs.suban.activity.QrCodeActivity;
import com.gcs.suban.activity.StoreActivity;
import com.gcs.suban.activity.TeamActivity;
import com.gcs.suban.activity.TeamOrderActivity;
import com.gcs.suban.base.BaseFragment;
import com.gcs.suban.bean.CenterBean;
import com.gcs.suban.eventbus.CenterEvent;
import com.gcs.suban.eventbus.StoreEvent;
import com.gcs.suban.listener.OnCenterListener;
import com.gcs.suban.model.CenterModel;
import com.gcs.suban.model.CenterModelImpl;
import com.gcs.suban.tools.DateTool;
import com.gcs.suban.tools.ToastTool;

import io.rong.eventbus.EventBus;

/**
 * 分销中心界面
 */
public class CenterFragment extends BaseFragment implements OnRefreshListener,
		OnCenterListener {

	private TextView Tv_commission;
	private TextView Tv_commissionsum;
	private TextView Tv_cashsum;
	private TextView Tv_uncom;
	private TextView Tv_putincome;
	private TextView Tv_income;
	private TextView Tv_saleorder;
	private TextView Tv_myteam;
	private TextView Tv_shopname;
	private TextView Tv_time;
	
	private Button Btn_recharge;

	private CardView Card_center_order;
	private CardView Card_center_detail;
	private CardView Card_center_team;
	private CardView Card_person_store;
	private CardView Card_center_qrcode;

	private CenterModel centerModel;

	private String commission;
	private String income;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_center, container, false);
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.cv_center_order:
			Intent intent_order = new Intent(context,
					TeamOrderActivity.class);
			intent_order.putExtra("income", income);
			startActivity(intent_order);
			break;
		case R.id.cv_center_detail:
			Intent intent_detail = new Intent(context, CommissionRecordActivity.class);
			startActivity(intent_detail);
			break;
		case R.id.cv_center_team:
			Intent intent_team = new Intent(context, TeamActivity.class);
			startActivity(intent_team);
			break;
		case R.id.cv_person_store:
			Intent intent_store = new Intent(context, StoreActivity.class);
			startActivity(intent_store);
			break;
		case R.id.cv_center_qrcode:
			Intent intent_qrcode = new Intent(context, QrCodeActivity.class);
			startActivity(intent_qrcode);
			break;
		case R.id.btn_center_with:
			Intent intent_with = new Intent(context, CommissionwithdrawalActivity.class);
			intent_with.putExtra("money", commission);
			startActivity(intent_with);
			break;
		default:
			break;
		}
	}

	@Override
	protected void init() {
		EventBus.getDefault().register(this);
		// TODO Auto-generated method stub
		Tv_time = (TextView) context.findViewById(R.id.tv_center_time);
		Tv_commission = (TextView) context
				.findViewById(R.id.tv_center_commission);
		Tv_commissionsum = (TextView) context
				.findViewById(R.id.tv_center_commissionsum);
		Tv_cashsum = (TextView) context.findViewById(R.id.tv_center_cashsum);
		Tv_income = (TextView) context.findViewById(R.id.tv_center_income);
		Tv_myteam = (TextView) context.findViewById(R.id.tv_center_teamnum);
		Tv_saleorder = (TextView) context.findViewById(R.id.tv_center_ordernum);
		Tv_shopname = (TextView) context.findViewById(R.id.tv_center_storename);
		Tv_uncom = (TextView) context.findViewById(R.id.tv_center_uncom);
		Tv_putincome = (TextView) context
				.findViewById(R.id.tv_center_applymoney);
		
		Btn_recharge=(Button)context
				.findViewById(R.id.btn_center_with);
		Btn_recharge.setOnClickListener(this);

		Card_center_order = (CardView) context
				.findViewById(R.id.cv_center_order);
		Card_center_detail = (CardView) context
				.findViewById(R.id.cv_center_detail);
		Card_center_team = (CardView) context.findViewById(R.id.cv_center_team);
		Card_person_store = (CardView) context
				.findViewById(R.id.cv_person_store);
		Card_center_qrcode = (CardView) context
				.findViewById(R.id.cv_center_qrcode);
		Card_center_order.setOnClickListener(this);
		Card_center_detail.setOnClickListener(this);
		Card_center_team.setOnClickListener(this);
		Card_person_store.setOnClickListener(this);
		Card_center_qrcode.setOnClickListener(this);

		swipeRefreshLayout = (SwipeRefreshLayout) context
				.findViewById(R.id.swipeRefreshLayout_center);
		swipeRefreshLayout.setOnRefreshListener(this);
		InitSwipeRefreshLayout();
		
		hideView();

		centerModel = new CenterModelImpl();
		
		swipeRefreshLayout.post(new Runnable() {
			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(true);
			}
		});
		this.onRefresh();
	}

	/**
	 * 更新数据
	 */
	private void setData(CenterBean bean) {
		Tv_cashsum.setText(bean.cashsum);
		Tv_commission.setText(bean.commission + "元");
		commission=bean.commission;
		Tv_commissionsum.setText(bean.commissionsum);
		Tv_saleorder.setText(bean.saleorder + "个");
		Tv_shopname.setText(bean.shopname);
		Tv_income.setText(bean.income + "元");
		income=bean.income;
		Tv_uncom.setText(bean.uncom + "元");
		Tv_putincome.setText(bean.putincome + "元");
		Tv_myteam.setText(bean.mycustom + "/" + bean.myteam);
		Tv_time.setText(DateTool.TimeStamp2Date(bean.createtime,
				"yyyy-MM-dd"));
		ShowView();
	}
	
	/**
	 * 隐藏视图
	 */
	private void hideView() {
		Tv_cashsum.setVisibility(View.INVISIBLE);
		Tv_commission.setVisibility(View.INVISIBLE);
		Tv_commissionsum.setVisibility(View.INVISIBLE);
		Tv_saleorder.setVisibility(View.INVISIBLE);
		Tv_shopname.setVisibility(View.INVISIBLE);
		Tv_income.setVisibility(View.INVISIBLE);
		Tv_uncom.setVisibility(View.INVISIBLE);
		Tv_putincome.setVisibility(View.INVISIBLE);
		Tv_myteam.setVisibility(View.INVISIBLE);
		Tv_time.setVisibility(View.INVISIBLE);
	}
	
	/**
	 * 显示视图
	 */
	private void ShowView() {
		Tv_cashsum.setVisibility(View.VISIBLE);
		Tv_commission.setVisibility(View.VISIBLE);
		Tv_commissionsum.setVisibility(View.VISIBLE);
		Tv_saleorder.setVisibility(View.VISIBLE);
		Tv_shopname.setVisibility(View.VISIBLE);
		Tv_income.setVisibility(View.VISIBLE);
		Tv_uncom.setVisibility(View.VISIBLE);
		Tv_putincome.setVisibility(View.VISIBLE);
		Tv_myteam.setVisibility(View.VISIBLE);
		Tv_time.setVisibility(View.VISIBLE);
	}

	/**
	 * 网络请求 结果返回成功
	 */
	@Override
	public void onSuccess(CenterBean bean) {
		// TODO Auto-generated method stub
		setData(bean);
		swipeRefreshLayout.setRefreshing(false);
	}

	/**
	 * 网络请求 结果返回失败
	 */
	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, error);
		swipeRefreshLayout.setRefreshing(false);
	}

	/**
	 * 下拉刷新
	 */
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		centerModel.getInfo(Url.center, this);
	}
	
	/**
	 * 广播事件
	 */
	public void onEventMainThread(StoreEvent event) {
		if(event.getType().equals("storename"))
		{
		String msg = event.getMsg();
		Tv_shopname.setText(msg);
		}
	}
	
	public void onEventMainThread(CenterEvent event) {
		centerModel.getInfo(Url.center, this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

}
