package com.gcs.suban.popwindows;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.activity.CouponActivity;
import com.gcs.suban.base.BaseActivity;

public class CouponPopWindow extends BaseActivity {
	private Button Btn_check;
	private ImageButton Ibtn_cancel;
	private TextView Tv_time;
	private TextView Tv_num;
	//private TextView Tv_couponname;

	private String useday;
	private String deduct;
	//private String couponname;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ibtn_cancel:
			finish();
			break;
		case R.id.btn_check:
			Intent intent=new Intent(context,CouponActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.tv_cancel:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		useday = intent.getStringExtra("useday");
		deduct = intent.getStringExtra("deduct");
		//couponname = intent.getStringExtra("couponname");

		setContentView(R.layout.popwindow_coupon);

		Btn_check = (Button) findViewById(R.id.btn_check);
		Btn_check.setOnClickListener(this);

		Ibtn_cancel = (ImageButton) findViewById(R.id.ibtn_cancel);
		Ibtn_cancel.setOnClickListener(this);

		//Tv_couponname = (TextView) findViewById(R.id.tv_couponname);
		//Tv_couponname.setText(couponname);
		Tv_time = (TextView) findViewById(R.id.tv_time);
		Tv_time.setText("请在" + useday + "前使用");
		Tv_num = (TextView) findViewById(R.id.tv_num);
		Tv_num.setText(deduct);
	}

}
