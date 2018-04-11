package com.gcs.suban.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseFragment;
import com.gcs.suban.eventbus.MainEvent;

import io.rong.eventbus.EventBus;


public class CenterNullFragment extends BaseFragment{
	
	private Button Btn_confirm;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_center_null, container, false);
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_confirm:
			EventBus.getDefault().post(new MainEvent("select", "msg"));
			break;
		default:
			break;
		}
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		Btn_confirm = (Button) context.findViewById(R.id.btn_confirm);
		Btn_confirm.setOnClickListener(this);
	}

}
