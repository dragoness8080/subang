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

public class OrderFinishFragment extends OrderBaseFragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_order_finish, container,
				false);
		return view;
	}

	@Override
	protected void init() {
		status="4";
		
		listView = (LoadListView) context.findViewById(R.id.list_finish);

		layout = (LinearLayout) context.findViewById(R.id.layout_null_orderfinish);
		
		Btn_confirm = (Button) context.findViewById(R.id.layout_orderfinish)
				.findViewById(R.id.btn_confirm);

		swipeRefreshLayout = (SwipeRefreshLayout) context
				.findViewById(R.id.swipeRefreshLayout_finish);
		
		super.init();
	}
	
}
