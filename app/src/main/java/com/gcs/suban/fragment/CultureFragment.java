package com.gcs.suban.fragment;

import java.util.ArrayList;

import com.gcs.suban.R;
import com.gcs.suban.adapter.ViewpagerAdapter;
import com.gcs.suban.base.BaseFragment;
import com.gcs.suban.view.NoSlideViewPager;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class CultureFragment extends BaseFragment {
	private Resources resources;
	private NoSlideViewPager mPager;
	private ArrayList<Fragment> fragmentsList;
	private ImageView ivBottomLine;
	private TextView mTab1, mTab2, mTab3, mTab4;
	private int bottomLineWidth;
	private int offset = 0;
	private int position_old;
	private int position_zero;
	private int position_now;
	private int position_one;
	private int position_two;
	private int position_three;

	public final static int num = 4;
	public String postion;

	Fragment home1;
	Fragment home2;
	Fragment home3;
	Fragment home4;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_culture, null);
		resources = getResources();
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		InitWidth();
		InitViewPager();
		
		TranslateAnimation animation = new TranslateAnimation(
				position_zero, position_zero, 0, 0);
		animation.setFillAfter(true);
		animation.setDuration(300);
		ivBottomLine.startAnimation(animation);
		position_old = position_zero;
		mPager.setCurrentItem(0);
		ivBottomLine.setVisibility(View.VISIBLE);
	}
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		mTab1 = (TextView) context.findViewById(R.id.tv_tab_1);
		mTab2 = (TextView) context.findViewById(R.id.tv_tab_2);
		mTab3 = (TextView) context.findViewById(R.id.tv_tab_3);
		mTab4 = (TextView) context.findViewById(R.id.tv_tab_4);

		mTab1.setOnClickListener(new MyOnClickListener(0));
		mTab2.setOnClickListener(new MyOnClickListener(1));
		mTab3.setOnClickListener(new MyOnClickListener(2));
		mTab4.setOnClickListener(new MyOnClickListener(3));
	}

	private void InitViewPager() {
		mPager = (NoSlideViewPager) context.findViewById(R.id.viewPager);
		fragmentsList = new ArrayList<Fragment>();
		home1 = new CultureVideoFragment();
		home2 = new CultureVoiceFragment();
		home3 = new CultureDownFragment();
		home4 = new CultureBrandFragment();
		fragmentsList.add(home1);
		fragmentsList.add(home2);
		fragmentsList.add(home3);
		fragmentsList.add(home4);
		mPager.setAdapter(new ViewpagerAdapter(getChildFragmentManager(),
				fragmentsList));
		mPager.addOnPageChangeListener(new MyOnPageChangeListener());
		mPager.setCurrentItem(4);
	}
	
	private void InitWidth() {
		ivBottomLine = (ImageView) context.findViewById(R.id.iv_bottom_line);
		bottomLineWidth = ivBottomLine.getLayoutParams().width;
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
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
				mTab1.setTextColor(resources.getColor(R.color.themeOrange));
				break;
			case 1:
				position_now = position_one;
				animation = new TranslateAnimation(position_old, position_now,
						0, 0);
				position_old = position_now;
				mTab2.setTextColor(resources.getColor(R.color.themeOrange));
				break;

			case 2:
				position_now = position_two;
				animation = new TranslateAnimation(position_old, position_now,
						0, 0);
				position_old = position_now;
				mTab3.setTextColor(resources.getColor(R.color.themeOrange));
				break;
			case 3:
				position_now = position_three;
				animation = new TranslateAnimation(position_old, position_now,
						0, 0);
				position_old = position_now;
				mTab4.setTextColor(resources.getColor(R.color.themeOrange));
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
	 * ÇÐ»»ÎÄ×Ö ÖÁ°µÉ«
	 */
	@SuppressWarnings("deprecation")
	public void resetTxts() {
		mTab1.setTextColor(resources.getColor(R.color.black));
		mTab2.setTextColor(resources.getColor(R.color.black));
		mTab3.setTextColor(resources.getColor(R.color.black));
		mTab4.setTextColor(resources.getColor(R.color.black));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
