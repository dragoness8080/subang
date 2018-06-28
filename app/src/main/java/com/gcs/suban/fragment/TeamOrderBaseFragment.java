package com.gcs.suban.fragment;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.gcs.suban.Url;
import com.gcs.suban.adapter.TeamOrderAdapter;
import com.gcs.suban.base.BaseFragment;
import com.gcs.suban.bean.OrderBean;
import com.gcs.suban.listener.OnOrderListListener;
import com.gcs.suban.model.OrderModel;
import com.gcs.suban.model.TeamOrderModelImpl;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.view.LoadListView;
import com.gcs.suban.view.LoadListView.onLoadListViewListener;

public class TeamOrderBaseFragment extends BaseFragment implements
OnOrderListListener, onLoadListViewListener,
OnRefreshListener{
	protected LoadListView listView;
	
	protected TeamOrderAdapter adapter;

	protected LinearLayout layout;

	protected String page = "0";
	protected String status;
	protected Boolean isRefresh = false;


	protected OrderModel model;

	protected List<OrderBean> mListType = new ArrayList<OrderBean>();
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		default:
			break;
		}
	}
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		listView.setOnLoadListViewListener(this);

		adapter = new TeamOrderAdapter(context, mListType);
		listView.setAdapter(adapter);

		swipeRefreshLayout.setOnRefreshListener(this);
		InitSwipeRefreshLayout();

		model = new TeamOrderModelImpl();

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
		model.getList(Url.sellorders, status, page, this);
	}

	/**
	 * 上拉加载
	 */
	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onLoad");
		model.getList(Url.sellorders, status, page, this);
	}

}
