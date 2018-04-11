package com.gcs.suban.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.adapter.CommentAdapter;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.CommentBean;
import com.gcs.suban.listener.OnCommentListener;
import com.gcs.suban.model.CommentModel;
import com.gcs.suban.model.CommentModelImpl;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.view.LoadListView;
import com.gcs.suban.view.LoadListView.onLoadListViewListener;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends BaseActivity implements OnCommentListener,
		onLoadListViewListener, OnRefreshListener {

	private TextView Tv_title;

	private ImageButton IBtn_back;

	private LoadListView listView;

	private CommentAdapter adapter; // �Զ���������

	private List<CommentBean> mListType = new ArrayList<CommentBean>(); // ���ﳵ���ݼ���

	private CommentModel model;

	private String page = "0";
	private String goodsid;

	private Boolean isRefresh = true;

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
		Intent intent = getIntent();
		goodsid = intent.getStringExtra("goodsid");
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_comment);
		Tv_title = (TextView) findViewById(R.id.title);
		Tv_title.setText("�û�����");

		IBtn_back = (ImageButton) context.findViewById(R.id.back);
		IBtn_back.setOnClickListener(this);

		listView = (LoadListView) findViewById(R.id.list_comment);
		listView.setOnLoadListViewListener(this);

		adapter = new CommentAdapter(this, mListType);
		listView.setAdapter(adapter);

		swipeRefreshLayout = (SwipeRefreshLayout) context
				.findViewById(R.id.swipeRefreshLayout_comment);
		swipeRefreshLayout.setOnRefreshListener(this);
		InitSwipeRefreshLayout();

		model = new CommentModelImpl();
		swipeRefreshLayout.post(new Runnable() {
			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(true);
			}
		});
		this.onRefresh();
	}

	/**
	 * ����������
	 */
	private void setData(List<CommentBean> ListType) {
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
	 * �������� ������سɹ�
	 */
	@Override
	public void onSuccess(List<CommentBean> ListType, String page) {
		// TODO Auto-generated method stub
		swipeRefreshLayout.setRefreshing(false);
		this.page = page;
		if (ListType == null) {
			// ��������ȫ�����
			if (isRefresh != true) {
				listView.setComplete(true);
				listView.loadComplete();
				return;
			}
			// �б�Ϊ��
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

	/**
	 * �������� �������ʧ��
	 */
	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, error);
		swipeRefreshLayout.setRefreshing(false);
		listView.loadComplete();
	}

	/**
	 * ����ˢ��
	 */
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onRefresh");
		page = "0";
		isRefresh = true;
		model.onInfo(Url.getcomments, goodsid, page, this);
	}

	/**
	 * ��������
	 */
	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onLoad");
		model.onInfo(Url.getcomments, goodsid, page, this);
	}
}
