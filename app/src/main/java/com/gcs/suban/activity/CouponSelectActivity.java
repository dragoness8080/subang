package com.gcs.suban.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.adapter.CouponAdapter;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.CouponBean;
import com.gcs.suban.listener.OnCouponListListener;
import com.gcs.suban.model.CouPonModel;
import com.gcs.suban.model.CouponModelImpl;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.view.LoadListView;
import com.gcs.suban.view.LoadListView.onLoadListViewListener;

public class CouponSelectActivity extends BaseActivity implements
		OnCouponListListener, OnItemClickListener, onLoadListViewListener,
		OnRefreshListener {
	
	private TextView Tv_title;
	
	private ImageButton IBtn_back;

	protected LoadListView listView;
	protected CouponAdapter adapter;

	protected String page = "0";
	protected String status;
	protected Boolean isRefresh = false;

	protected CouPonModel model;

	protected List<CouponBean> mListType = new ArrayList<CouponBean>();

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
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
		setContentView(R.layout.activity_coupon_select);
		
		Tv_title = (TextView) findViewById(R.id.title);
		Tv_title.setText("优惠券");
		
		IBtn_back = (ImageButton) context.findViewById(R.id.back);
		IBtn_back.setOnClickListener(this);

		status="1";

		listView = (LoadListView) context.findViewById(R.id.list_unused);
		listView.setOnLoadListViewListener(this);
		listView.setOnItemClickListener(this);

		adapter = new CouponAdapter(context, mListType);
		listView.setAdapter(adapter);

		swipeRefreshLayout = (SwipeRefreshLayout) context
				.findViewById(R.id.swipeRefreshLayout_unused);
		swipeRefreshLayout.setOnRefreshListener(this);
		InitSwipeRefreshLayout();

		model = new CouponModelImpl();

		swipeRefreshLayout.post(new Runnable() {
			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(true);
			}
		});
		this.onRefresh();
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onRefresh");
		page = "0";
		isRefresh = true;
		model.getList(Url.couponlist, status, page, this);
	}

	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onLoad");
		model.getList(Url.orderlist, status, page, this);
	}
	
	/**
	 * 更新适配器
	 */
	protected void setData(List<CouponBean> ListType) {
		if (isRefresh == true) {
			isRefresh = false;
			adapter.clear();
			mListType.clear();
		}
		listView.loadComplete();

		mListType.addAll(ListType);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onSuccess(List<CouponBean> ListType, String page) {
		// TODO Auto-generated method stub
		swipeRefreshLayout.setRefreshing(false);
		this.page = page;
		if (ListType == null) {
			// 上拉加载全部完成
			if (isRefresh != true) {
				listView.setComplete(true);
				listView.loadComplete();
				return;
			}
			// 列表为空
			else {
				adapter.clear();
				mListType.clear();
				adapter.notifyDataSetChanged();
				listView.loadComplete();
			}
		} else {
			listView.setComplete(false);
			setData(ListType);
		}
	}

	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, error);
		swipeRefreshLayout.setRefreshing(false);
		listView.loadComplete();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String coupondataid = mListType.get(position).coupondataid;
		String couponname = mListType.get(position).couponname;
		String deduct = mListType.get(position).deduct;
		Intent intent = new Intent();
		intent.putExtra("couponname", couponname);
		intent.putExtra("deduct", deduct);
		intent.putExtra("coupondataid", coupondataid);
		context.setResult(RESULT_OK, intent);
		finish();
	}

}
