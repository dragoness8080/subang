package com.gcs.suban.activity;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gcs.suban.MyDate;
import com.gcs.suban.R;
import com.gcs.suban.app;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.eventbus.MainEvent;
import com.gcs.suban.tools.DataCleanManager;
import com.gcs.suban.tools.SharedPreference;
import com.gcs.suban.tools.UpdateManager;
import com.zcw.togglebutton.ToggleButton;
import com.zcw.togglebutton.ToggleButton.OnToggleChanged;

import cn.jpush.android.api.JPushInterface;
import io.rong.eventbus.EventBus;


/**
 * …Ë÷√
 */
public class SetActivity extends BaseActivity {

	private TextView Tv_title;
	private TextView Tv_cache;

	private ImageButton IBtn_back;

	private Button Btn_confirm;

	private ToggleButton pushToggleButton;

	private CardView Cv_clean;
	private CardView upadateCardView;

	private String isJpush;

	private UpdateManager updateManager;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.btn_confirm:
			finish();
			EventBus.getDefault().post(new MainEvent("exit", "msg"));
			SharedPreference.setParam(context, "vid", "");
			SharedPreference.setParam(context,"jpushid", "");
			MyDate.setWxresp(0);
			MyDate.setMyVid("");
			MyDate.setJpushid("");
			Intent intent = new Intent(context, WelcomeActivity.class);
			startActivity(intent);
			break;
		case R.id.card_clean:
			if (!Tv_cache.getText().toString().equals("0K")) {
				DataCleanManager.clearAllCache(context);
				Tv_cache.setText("0K");
			}
			break;
		case R.id.card_updata:
			updateManager.VolleyGet();
			break;
		default:
			break;
		}
	}

	@Override
	protected void init() {
		updateManager = new UpdateManager(context);
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_set);
		Tv_title = (TextView) findViewById(R.id.title);
		Tv_title.setText("…Ë÷√");
		Tv_cache = (TextView) findViewById(R.id.tv_cache);
		try {
			Tv_cache.setText(DataCleanManager.getTotalCacheSize(context));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Tv_cache.setText("0");
			e.printStackTrace();
		}

		pushToggleButton = (ToggleButton) context.findViewById(R.id.push);
		isJpush=(String) SharedPreference.getParam(context, "isJpush", "String");
		if (isJpush.equals("0")) {
			
		} else {
			pushToggleButton.toggle();
		}

		pushToggleButton.setOnToggleChanged(new OnToggleChanged() {
			@Override
			public void onToggle(boolean on) {
				if (on == true) { 
					JPushInterface.resumePush(app.getContext());
					isJpush="1";
					SharedPreference.setParam(context, "isJpush", isJpush);
				} else {
					JPushInterface.stopPush(app.getContext());
					isJpush="0";
					SharedPreference.setParam(context, "isJpush", isJpush);
				}
			}
		});

		IBtn_back = (ImageButton) context.findViewById(R.id.back);
		IBtn_back.setOnClickListener(this);
		
		upadateCardView = (CardView) context.findViewById(R.id.card_updata);
		upadateCardView.setOnClickListener(this);

		Btn_confirm = (Button) context.findViewById(R.id.btn_confirm);
		Btn_confirm.setOnClickListener(this);

		Cv_clean = (CardView) context.findViewById(R.id.card_clean);
		Cv_clean.setOnClickListener(this);
	}

}
