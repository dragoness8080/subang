package com.gcs.suban.fragment;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gcs.suban.R;
import com.gcs.suban.activity.LoginActivity;
import com.gcs.suban.activity.MainActivity;
import com.gcs.suban.base.BaseFragment;
import com.gcs.suban.tools.SharedPreference;

public class WelcomeFragment extends BaseFragment{
	Timer timer = new Timer();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_welcome, container, false);
		return view;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		timer.schedule(task, 2000);
	}
	
	TimerTask task = new TimerTask() {
		@Override
		public void run() {
			String vid = (String) SharedPreference.getParam(context, "vid", "");
 	 			if (vid.equals("")) {
 	 				Intent intent_enter = new Intent(context,
 	 						LoginActivity.class);
 	 				startActivity(intent_enter);
 	 				context.finish();
 	 			} else {
 	 				Intent intent_enter2 = new Intent(context,
 	 						MainActivity.class);
 	 				startActivity(intent_enter2);
 	 				context.finish();
 	        } 
		}
	};


}
