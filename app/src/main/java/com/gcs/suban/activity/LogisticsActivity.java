package com.gcs.suban.activity;

import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.adapter.LogisticsAdapter;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.LogisticsBean;
import com.gcs.suban.listener.OnLogisticsListener;
import com.gcs.suban.model.LogistModel;
import com.gcs.suban.model.LogisticsModelImpl;
import com.gcs.suban.tools.ToastTool;

public class LogisticsActivity extends BaseActivity implements OnLogisticsListener{
	
	private ImageButton IBtn_back;
	private TextView Tv_title;
	
	private String orderid;
	
	private ListView listView;
	private LogisticsAdapter adapter; // 自定义适配器
	
	private LogistModel model;
	
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
		orderid = intent.getStringExtra("orderid");
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_logistics);
		Tv_title = (TextView) findViewById(R.id.title);
		Tv_title.setText("物流详情");

		IBtn_back = (ImageButton) context.findViewById(R.id.back);
		IBtn_back.setOnClickListener(this);
		
		listView=(ListView)context.findViewById(R.id.listView_logistics);
		
		model=new LogisticsModelImpl();
		model.getInfo(Url.express, orderid, this);
	}

	/**
	 * 网络请求 结果返回成功
	 */
	@Override
	public void onSuccess(List<LogisticsBean> mListType) {
		// TODO Auto-generated method stub
		adapter = new LogisticsAdapter(this, mListType);
		listView.setAdapter(adapter);
	}

	/**
	 * 网络请求 结果返回失败
	 */
	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, error);
	}

}
