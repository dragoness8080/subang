package com.gcs.suban.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import com.gcs.suban.R;
import com.gcs.suban.adapter.OrderAdapter;
import com.gcs.suban.view.LoadListView;

public class OrderSendFragment extends OrderBaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_order_send, container,
				false);
		return view;
	}
	
	@Override
	protected void init() {	
		status="1";
		
		listView = (LoadListView) context.findViewById(R.id.list_send);

		layout = (LinearLayout) context
				.findViewById(R.id.layout_null_ordersend);
		
		Btn_confirm = (Button) context.findViewById(R.id.layout_ordersend)
				.findViewById(R.id.btn_confirm);

		adapter = new OrderAdapter(context, mListType);
		listView.setAdapter(adapter);

		swipeRefreshLayout = (SwipeRefreshLayout) context
				.findViewById(R.id.swipeRefreshLayout_send);
		
		super.init();
	}

	
}
