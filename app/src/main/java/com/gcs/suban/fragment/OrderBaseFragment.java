package com.gcs.suban.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.activity.OrderDetailActivity;
import com.gcs.suban.adapter.OrderAdapter;
import com.gcs.suban.base.BaseFragment;
import com.gcs.suban.bean.OrderBean;
import com.gcs.suban.eventbus.OrderEvent;
import com.gcs.suban.listener.OnOrderListListener;
import com.gcs.suban.model.OrderModel;
import com.gcs.suban.model.OrderModelImpl;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.view.LoadListView;
import com.gcs.suban.view.LoadListView.onLoadListViewListener;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;


public class OrderBaseFragment extends BaseFragment implements
		OnOrderListListener, OnItemClickListener, onLoadListViewListener,
		OnRefreshListener {

	protected LoadListView listView;
	protected OrderAdapter adapter;

	protected LinearLayout layout;

	protected Button Btn_confirm;

	protected String page = "0";
	protected String status;
	protected Boolean isRefresh = false;

	protected OrderModel model;

	protected List<OrderBean> mListType = new ArrayList<OrderBean>();

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_confirm:
			context.finish();
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void init() {
		EventBus.getDefault().register(this);
		// TODO Auto-generated method stub
		listView.setOnItemClickListener(this);
		listView.setOnLoadListViewListener(this);

		Btn_confirm.setOnClickListener(this);

		adapter = new OrderAdapter(context, mListType);
		listView.setAdapter(adapter);

		swipeRefreshLayout.setOnRefreshListener(this);
		InitSwipeRefreshLayout();

		model = new OrderModelImpl();

		swipeRefreshLayout.post(new Runnable() {
			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(true);
			}
		});
		this.onRefresh();
	}

	/**
	 * 更新适配器
	 */
	protected void setData(List<OrderBean> ListType) {
		if (isRefresh == true) {
			isRefresh = false;
			adapter.clear();
			mListType.clear();
		}
		listView.loadComplete();

		mListType.addAll(ListType);
		adapter.notifyDataSetChanged();
	}

	/**
	 * 网络请求 结果返回成功
	 */
	@Override
	public void onSuccess(List<OrderBean> ListType, String page) {
		// TODO Auto-generated method stub
		layout.setVisibility(View.GONE);
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
				layout.setVisibility(View.VISIBLE);
			}
		} else {
			listView.setComplete(false);
			setData(ListType);
		}
	}

	/**
	 * 网络请求 结果返回失败
	 */
	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, error);
		swipeRefreshLayout.setRefreshing(false);
		listView.loadComplete();
	}

	/**
	 * 下拉刷新
	 */
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onRefresh");
		page = "0";
		isRefresh = true;
		model.getList(Url.orderlist, status, page, this);
	}

	/**
	 * 上拉加载
	 */
	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onLoad");
		model.getList(Url.orderlist, status, page, this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		String orderid = mListType.get(position).orderid;
		String ordersn = mListType.get(position).ordersn;
		Intent intent = new Intent(context, OrderDetailActivity.class);
		intent.putExtra("orderid", orderid);
		intent.putExtra("ordersn", ordersn);
		startActivity(intent);
	}

	/**
	 * 广播事件
	 */
	public void onEventMainThread(OrderEvent event) {
		swipeRefreshLayout.post(new Runnable() {
			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(true);
			}
		});
		this.onRefresh();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

}
