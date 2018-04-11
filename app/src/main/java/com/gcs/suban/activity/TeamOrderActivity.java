package com.gcs.suban.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.adapter.ViewpagerAdapter;
import com.gcs.suban.fragment.TeamOrderAllFragment;
import com.gcs.suban.fragment.TeamOrderFinishFragment;
import com.gcs.suban.fragment.TeamOrderPayFragment;
import com.gcs.suban.fragment.TeamOrderReceiveFragment;
/**
 * 分销订单 
 */
public class TeamOrderActivity extends FragmentActivity implements OnClickListener{
	private TextView Tv_title;
	
	private ViewPager mPager;
	
	private ArrayList<Fragment> fragmentsList;
	
	private ImageView ivBottomLine;
	
	private TextView mTab1, mTab2, mTab3, mTab4;
	private TextView Tv_price;
	
	private ImageButton Ibtn_back;
	
	private int bottomLineWidth;
	private int offset = 0;
	private int position_old;
	private int position_zero;
	private int position_now;
	private int position_one;
	private int position_two;
	private int position_three;
	
	public final static int num = 4;
	
	
	private Fragment home1;
	private Fragment home2;
	private Fragment home3;
	private Fragment home4;
	
	private String income;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_teamorder);
		init();
		InitWidth();
		InitViewPager();	
	}

	private void init() {
		Intent intent = getIntent();
		income = intent.getStringExtra("income");
		Tv_title = (TextView) findViewById(R.id.title);
		Tv_title.setText("分销订单");
		Tv_price= (TextView) findViewById(R.id.tv_price);
		Tv_price.setText(income);
		Ibtn_back = (ImageButton) findViewById(R.id.back);
		mTab1 = (TextView) findViewById(R.id.tv_tab_1);
		mTab2 = (TextView) findViewById(R.id.tv_tab_2);
		mTab3 = (TextView) findViewById(R.id.tv_tab_3);
		mTab4 = (TextView) findViewById(R.id.tv_tab_4);
		Ibtn_back.setOnClickListener(this);
		mTab1.setOnClickListener(new MyOnClickListener(0));
		mTab2.setOnClickListener(new MyOnClickListener(1));
		mTab3.setOnClickListener(new MyOnClickListener(2));
		mTab4.setOnClickListener(new MyOnClickListener(3));
	}


	private void InitViewPager() {
		mPager = (ViewPager) findViewById(R.id.vPager);
		mPager.setOffscreenPageLimit(4);
		fragmentsList = new ArrayList<Fragment>();

		home1 = new TeamOrderAllFragment();
		home2 = new TeamOrderPayFragment();
		home3 = new TeamOrderReceiveFragment();
		home4 = new TeamOrderFinishFragment();

		fragmentsList.add(home1);
		fragmentsList.add(home2);
		fragmentsList.add(home3);
		fragmentsList.add(home4);

		mPager.setAdapter(new ViewpagerAdapter(
				getSupportFragmentManager(), fragmentsList));
		mPager.addOnPageChangeListener(new MyOnPageChangeListener());

		int i =0;
		if (i == 0) {
			TranslateAnimation animation = new TranslateAnimation(
					position_zero, position_zero, 0, 0);
			animation.setFillAfter(true);
			animation.setDuration(300);
			ivBottomLine.startAnimation(animation);
			position_old=position_zero;
		} else if (i == 1) {
			TranslateAnimation animation = new TranslateAnimation(position_one,
					position_one, 0, 0);
			animation.setFillAfter(true);
			animation.setDuration(300);
			ivBottomLine.startAnimation(animation);
			position_old=position_one;
		} else if (i == 2) {
			TranslateAnimation animation = new TranslateAnimation(position_two,
					position_two, 0, 0);
			animation.setFillAfter(true);
			animation.setDuration(300);
			ivBottomLine.startAnimation(animation);
			position_old=position_two;
		} else if (i == 3) {
			TranslateAnimation animation = new TranslateAnimation(
					position_three, position_three, 0, 0);
			animation.setFillAfter(true);
			animation.setDuration(300);
			ivBottomLine.startAnimation(animation);
			position_old=position_three;
		}
		 mPager.setCurrentItem(i);
		ivBottomLine.setVisibility(View.VISIBLE);

	}

	private void InitWidth() {
		ivBottomLine = (ImageView) findViewById(R.id.iv_bottom_line);
		bottomLineWidth = ivBottomLine.getLayoutParams().width;
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		offset = (screenW / num - bottomLineWidth) / 2;
		int avg = screenW / num;
		position_zero = offset;
		position_one = avg + offset;
		position_two = 2 * avg + offset;
		position_three = 3 * avg + offset;
		position_old = offset;

	}

	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	};

	public class MyOnPageChangeListener implements OnPageChangeListener {

		@SuppressWarnings("deprecation")
		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			resetTxts();
			switch (arg0) {
			case 0:
				position_now = position_zero;
				animation = new TranslateAnimation(position_old, position_now,
						0, 0);
				position_old = position_now;
				mTab1.setTextColor(getResources().getColor(R.color.themeOrange));
				break;
			case 1:
				position_now = position_one;
				animation = new TranslateAnimation(position_old, position_now,
						0, 0);
				position_old = position_now;
				mTab2.setTextColor(getResources().getColor(R.color.themeOrange));
				break;

			case 2:
				position_now = position_two;
				animation = new TranslateAnimation(position_old, position_now,
						0, 0);
				position_old = position_now;
				mTab3.setTextColor(getResources().getColor(R.color.themeOrange));
				break;
			case 3:
				position_now = position_three;
				animation = new TranslateAnimation(position_old, position_now,
						0, 0);
				position_old = position_now;
				mTab4.setTextColor(getResources().getColor(R.color.themeOrange));
				break;
			}
			animation.setFillAfter(true);
			animation.setDuration(300);
			ivBottomLine.startAnimation(animation);
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}
	}
	
	/**
	 * 切换文字 至暗色
	 */
	@SuppressWarnings("deprecation")
	public void resetTxts() {
		mTab1.setTextColor(getResources().getColor(R.color.black));
		mTab2.setTextColor(getResources().getColor(R.color.black));
		mTab3.setTextColor(getResources().getColor(R.color.black));
		mTab4.setTextColor(getResources().getColor(R.color.black));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		default:
			break;
		}
	}

}

