package com.gcs.suban.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import com.gcs.suban.R;
import com.gcs.suban.view.LoadListView;

/**
 * 全部订单列表
 */
public class OrderAllFragment extends OrderBaseFragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_order_all, container,
				false);
		return view;
	}
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		status="10";
		listView = (LoadListView) context.findViewById(R.id.list_all);
		
		layout = (LinearLayout) context.findViewById(R.id.layout_null_orderall);
		
		Btn_confirm = (Button) context.findViewById(R.id.layout_orderall)
				.findViewById(R.id.btn_confirm);
		
		swipeRefreshLayout = (SwipeRefreshLayout) context
				.findViewById(R.id.swipeRefreshLayout_all);
		super.init();
	}
	

}
