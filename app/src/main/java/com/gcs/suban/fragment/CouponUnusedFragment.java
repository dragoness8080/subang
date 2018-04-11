package com.gcs.suban.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gcs.suban.R;
import com.gcs.suban.view.LoadListView;

public class CouponUnusedFragment extends CouponBaseFragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_coupon_unused, container,
				false);
		return view;
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		status="1";
		
		listView = (LoadListView) context.findViewById(R.id.list_unused);
		
		swipeRefreshLayout = (SwipeRefreshLayout) context
				.findViewById(R.id.swipeRefreshLayout_unused);
		
		super.init();
	}

}
