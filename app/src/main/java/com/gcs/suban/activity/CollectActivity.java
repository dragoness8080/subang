package com.gcs.suban.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.adapter.CollectAdapter;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.CollectBean;
import com.gcs.suban.eventbus.CollectEvent;
import com.gcs.suban.listener.OnCollectListener;
import com.gcs.suban.model.CollectModel;
import com.gcs.suban.model.CollectModelImpl;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.view.LoadListView;
import com.gcs.suban.view.LoadListView.onLoadListViewListener;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;


public class CollectActivity extends BaseActivity implements
		OnItemClickListener, OnCollectListener, onLoadListViewListener,
		OnRefreshListener {
	private ImageButton IBtn_back;

	private Button Btn_delete;
	
	private Button Btn_confirm;
	
	protected LinearLayout layout;

	private LoadListView listView;
	
	private CollectAdapter adapter; // 自定义适配器
	
	private List<CollectBean> mListType = new ArrayList<CollectBean>(); // 购物车数据集合
	
	private CollectModel model;

	private String page = "0";
	
	private Boolean isRefresh = true;

	private int id;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.btn_confirm:
			finish();
			break;
		case R.id.btn_delete:
			deleteShop();
			break;
		default:
			break;
		}
	}

	@Override
	protected void init() {
		EventBus.getDefault().register(this);
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_collect);

		IBtn_back = (ImageButton) context.findViewById(R.id.back);
		IBtn_back.setOnClickListener(this);

		Btn_delete = (Button) context.findViewById(R.id.btn_delete);
		Btn_delete.setOnClickListener(this);
		
		Btn_confirm= (Button) context.findViewById(R.id.layout_collect)
				.findViewById(R.id.btn_confirm);
		Btn_confirm.setOnClickListener(this);
		
		layout = (LinearLayout) context.findViewById(R.id.layout_null_collect);

		listView = (LoadListView) findViewById(R.id.list_collect);
		listView.setOnItemClickListener(this);
		listView.setOnLoadListViewListener(this);

		adapter = new CollectAdapter(this, mListType);
		listView.setAdapter(adapter);

		swipeRefreshLayout = (SwipeRefreshLayout) context
				.findViewById(R.id.swipeRefreshLayout_collect);
		swipeRefreshLayout.setOnRefreshListener(this);
		InitSwipeRefreshLayout();

		model = new CollectModelImpl();
		model.getInfo(Url.collectlist, page, this);
	}

	/**
	 * 更新适配器
	 */
	private void setData(List<CollectBean> ListType) {
		if (isRefresh == true) {
			isRefresh = false;
			adapter.clear();
			mListType.clear();
		}
		listView.loadComplete();

		mListType.addAll(ListType);
		adapter.initDate();
		adapter.notifyDataSetChanged();
	}

	/**
	 * 网络请求 结果返回成功
	 */
	@Override
	public void onSuccess(List<CollectBean> ListType, String page) {
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
	 * 取消收藏成功时回调
	 */
	@Override
	public void onCollect(String resulttext) {
		ToastTool.showToast(context, resulttext);
		page = "0";
		isRefresh = true;
		model.getInfo(Url.collectlist, page, this);
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
		model.getInfo(Url.collectlist, page, this);
	}

	/**
	 * 上拉加载
	 */
	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onLoad");
		model.getInfo(Url.collectlist, page, this);
	}

	/* 删除商品 */
	private void deleteShop() {
		StringBuffer sb = new StringBuffer();
		id=-1;
		for (int i = 0; i < mListType.size(); i++) {
			if (CollectAdapter.getIsSelected().get(i)) {
				id = mListType.get(i).getShopId();
				Log.e("delete", id + "");
				sb.append(id);
				sb.append(",");
			}
		}
		if(id!=-1)
		{
		sb.deleteCharAt(sb.length() - 1);
		model.cancel(Url.cancelcollect, sb + "", this);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		String goods_id = (String) view.getTag(R.id.tag_first);
		Intent intent = new Intent(context, ShopDetailActivity.class);
		intent.putExtra("goodsid", goods_id);
		startActivity(intent);
	}

	/**
	 * 广播事件
	 */
	public void onEventMainThread(CollectEvent event) {
		String msg = event.getMsg();
		if (event.getType().equals("delete")) {
			model.cancel(Url.cancelcollect, msg, this);
		} else if (event.getType().equals("updata")) {
			page = "0";
			isRefresh = true;
			model.getInfo(Url.collectlist, page, this);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

}
