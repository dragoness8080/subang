package com.gcs.suban.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.adapter.CustomerAdapter;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.TeamBean;
import com.gcs.suban.listener.OnTeamListener;
import com.gcs.suban.model.CustomerModelImpl;
import com.gcs.suban.model.TeamModel;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.view.LoadListView;
import com.gcs.suban.view.LoadListView.onLoadListViewListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends BaseActivity implements OnTeamListener,
		onLoadListViewListener, OnRefreshListener {
	
	private ImageButton IBtn_back;
	private Button Btn_exchange;
	private TextView Tv_title;
	
	private LoadListView listView;

	private CustomerAdapter adapter;

	private List<TeamBean> mListType = new ArrayList<TeamBean>();

	private LinearLayout layout;

	private Button Btn_confirm;

	private TeamModel model;

	private String page = "0";

	private Boolean isRefresh = false;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_confirm:
			Intent intent = new Intent(context, QrCodeActivity.class);
			startActivity(intent);
			context.finish();
			break;
		case R.id.btn_exchange:
			Intent intent_exchange = new Intent(context, TeamActivity.class);
			startActivity(intent_exchange);
			context.finish();
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
		setContentView(R.layout.activity_customer);
		// TODO Auto-generated method stub
		TAG = TAG + "CUSTOM";
		
		Tv_title = (TextView) findViewById(R.id.title);
		Tv_title.setText("未购买会员");

		IBtn_back = (ImageButton) context.findViewById(R.id.back);
		IBtn_back.setOnClickListener(this);
		
		Btn_exchange=(Button)context.findViewById(R.id.btn_exchange);
		Btn_exchange.setOnClickListener(this);

		listView = (LoadListView) context.findViewById(R.id.list_customer);
		listView.setLayoutAnimation(controller);
		listView.setOnLoadListViewListener(this);

		adapter = new CustomerAdapter(context, mListType);
		listView.setAdapter(adapter);

		layout = (LinearLayout) context.findViewById(R.id.layout_null_custom);

		Btn_confirm = (Button) context.findViewById(R.id.layout_custom)
				.findViewById(R.id.btn_confirm);
		Btn_confirm.setOnClickListener(this);

		swipeRefreshLayout = (SwipeRefreshLayout) context
				.findViewById(R.id.swipeRefreshLayout_customer);
		swipeRefreshLayout.setOnRefreshListener(this);
		InitSwipeRefreshLayout();

		model = new CustomerModelImpl();
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
	private void setData(List<TeamBean> ListType) {
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
	 * 网络请求 结果返回失败
	 */
	@Override
	public void onSuccess(List<TeamBean> ListType, String page) {
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

	@Override
	public void onSearchSuccess(List<TeamBean> listType, String page2) {
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
		model.getInfo(Url.mycustom, page, this);
	}

	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onLoad");
		model.getInfo(Url.mycustom, page, this);
	}

}
