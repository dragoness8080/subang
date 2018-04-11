package com.gcs.suban.activity;

import java.util.ArrayList;
import java.util.List;

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
import com.gcs.suban.adapter.RecordAdapter;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.RecordBean;
import com.gcs.suban.listener.OnRecordListener;
import com.gcs.suban.model.RecordModel;
import com.gcs.suban.model.RecordModelImpl;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.view.LoadListView;
import com.gcs.suban.view.LoadListView.onLoadListViewListener;
/**
 * Ӷ����ϸ
 */
public class CommissionRecordActivity extends BaseActivity implements
		OnRecordListener, onLoadListViewListener, OnRefreshListener {

	private TextView Tv_title;
	
	private ImageButton IBtn_back;
	
	private Button Btn_confirm;
	
	protected LinearLayout layout;

	private LoadListView listView;
	
	private RecordAdapter adapter; // �Զ���������
	
	private List<RecordBean> mListType = new ArrayList<RecordBean>(); // ���ﳵ���ݼ���
	
	private RecordModel model;
	
	private String page = "0";
	
	private Boolean isRefresh = true;

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
		setContentView(R.layout.activity_record);
		Tv_title = (TextView) findViewById(R.id.title);
		Tv_title.setText("Ӷ����ϸ");

		IBtn_back = (ImageButton) context.findViewById(R.id.back);
		IBtn_back.setOnClickListener(this);
		
		Btn_confirm= (Button) context.findViewById(R.id.layout_commission)
				.findViewById(R.id.btn_confirm);
		Btn_confirm.setOnClickListener(this);
		
		layout = (LinearLayout) context.findViewById(R.id.layout_null_commission);

		listView = (LoadListView) findViewById(R.id.list_record);
		listView.setOnLoadListViewListener(this);

		adapter = new RecordAdapter(this, mListType);
		listView.setAdapter(adapter);

		swipeRefreshLayout = (SwipeRefreshLayout) context
				.findViewById(R.id.swipeRefreshLayout_record);
		swipeRefreshLayout.setOnRefreshListener(this);
		InitSwipeRefreshLayout();

		model = new RecordModelImpl();
		model.getInfo(Url.commission, page, this);
	}

	/**
	 * ����������
	 */
	private void setData(List<RecordBean> ListType) {
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
	public void onSuccess(List<RecordBean> ListType, String page) {
		// TODO Auto-generated method stub
		layout.setVisibility(View.GONE);
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
				layout.setVisibility(View.VISIBLE);
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
		model.getInfo(Url.commission, page, this);
	}

	/**
	 * ��������
	 */
	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onLoad");
		model.getInfo(Url.commission, page, this);
	}

}
