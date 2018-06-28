package com.gcs.suban.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.adapter.ViewpagerAdapter;
import com.gcs.suban.fragment.AchievementFragment;
import com.gcs.suban.fragment.StockSettleFragment;

import java.util.ArrayList;

public class InventorySettleActivity extends FragmentActivity implements OnClickListener {

    protected TextView Tv_title;
    protected ImageButton Im_back;
    protected TextView mTab1,mTab2;
    protected ViewPager mPager;
    protected ArrayList<Fragment> fragmentList;
    protected Fragment fragment1,fragment2;
    protected int bottomLineWidth,offset = 0,position_old,position_zero,position_now,position_one;
    protected ImageView ivBottomLine;

    public final static int num = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_inventorysettle);
        init();
        InitWidth();
        initViewPager();
    }

    private void init(){
        Tv_title = (TextView)findViewById(R.id.title);
        Tv_title.setText("Ω·À„÷––ƒ");
        Im_back = (ImageButton)findViewById(R.id.back);
        Im_back.setOnClickListener(this);
        mTab1 = (TextView)findViewById(R.id.tab1);
        mTab2 = (TextView)findViewById(R.id.tab2);
        mTab1.setOnClickListener(new MyOnClickListener(0));
        mTab2.setOnClickListener(new MyOnClickListener(1));
    }

    private void initViewPager(){
        mPager = (ViewPager)findViewById(R.id.settle_page);
        mPager.setOffscreenPageLimit(2);
        fragmentList = new ArrayList<Fragment>();
        fragment1 = new StockSettleFragment();
        fragment2 = new AchievementFragment();

        fragmentList.add(fragment1);
        fragmentList.add(fragment2);

        mPager.setAdapter(new ViewpagerAdapter(getSupportFragmentManager(),fragmentList));
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
        }
        mPager.setCurrentItem(i);
        ivBottomLine.setVisibility(View.VISIBLE);
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

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
        position_old = offset;

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

    public void resetTxts() {
        mTab1.setTextColor(getResources().getColor(R.color.black));
        mTab2.setTextColor(getResources().getColor(R.color.black));
    }
}
