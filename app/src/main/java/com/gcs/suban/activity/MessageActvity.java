package com.gcs.suban.activity;

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
import com.gcs.suban.adapter.MessageAdapter;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.MessageBean;
import com.gcs.suban.eventbus.PersonEvent;
import com.gcs.suban.listener.OnMessageListener;
import com.gcs.suban.model.MessageModel;
import com.gcs.suban.model.MessageModelImpl;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.view.LoadListView;
import com.gcs.suban.view.LoadListView.onLoadListViewListener;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;


public class MessageActvity extends BaseActivity implements OnMessageListener,
		onLoadListViewListener, OnRefreshListener {
	
	private TextView Tv_title;
	
	private ImageButton IBtn_back;

	private LoadListView listView;

	private MessageAdapter adapter;

	private List<MessageBean> mListType = new ArrayList<MessageBean>();

	private LinearLayout layout;

	private Button Btn_confirm;

	private MessageModel model;

	private String page = "0";

	private Boolean isRefresh = false;

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
		default:
			break;
		}
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_news);
		Tv_title = (TextView) findViewById(R.id.title);
		Tv_title.setText("我的消息");
		
		IBtn_back = (ImageButton) context.findViewById(R.id.back);
		IBtn_back.setOnClickListener(this);

		listView = (LoadListView) context.findViewById(R.id.list_news);
		listView.setLayoutAnimation(controller);
		listView.setOnLoadListViewListener(this);

		adapter = new MessageAdapter(context, mListType);
		listView.setAdapter(adapter);

		layout = (LinearLayout) context.findViewById(R.id.layout_null_news);

		Btn_confirm = (Button) context.findViewById(R.id.layout_news)
				.findViewById(R.id.btn_confirm);
		Btn_confirm.setOnClickListener(this);

		swipeRefreshLayout = (SwipeRefreshLayout) context
				.findViewById(R.id.swipeRefreshLayout_news);
		swipeRefreshLayout.setOnRefreshListener(this);
		InitSwipeRefreshLayout();

		model = new MessageModelImpl();
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
	private void setData(List<MessageBean> ListType) {
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
	public void onSuccess(List<MessageBean> ListType, String page) {
		// TODO Auto-generated method stub
		EventBus.getDefault().post(new PersonEvent("type","msg"));
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
		Log.i(TAG, "onRefresh");
		page = "0";
		isRefresh = true;
		model.getInfo(Url.messagelist, page, this);
	}

	/**
	 * 上拉加载
	 */
	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		model.getInfo(Url.messagelist, page, this);
	}

}
