package com.gcs.suban.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.adapter.ViewpagerAdapter;
import com.gcs.suban.fragment.RewardAllFragment;
import com.gcs.suban.fragment.RewardExchangeFragment;
import com.gcs.suban.fragment.RewardNotFragment;

import java.util.ArrayList;

public class ExchangeActivity extends FragmentActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private ArrayList<Fragment> fragmentArrayList;
    private ImageView ivBottomLine;
    private TextView title;
    private TextView mTab1,mTab2,mTab3;
    private int bottomLineWidth;
    private int offset = 0,position_old,position_now,position_zero,position_one,position_two;
    public final static int num = 3;
    public String postion;
    Fragment fragment1,fragment2,fragment3;
    private ImageButton back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_exchange);
        init();
        InitWidth();
        InitViewPager();
    }

    private void init(){
        title = (TextView)findViewById(R.id.title);
        back = (ImageButton)findViewById(R.id.back);
        title.setText("÷–Ω±–≈œ¢");
        back.setOnClickListener(this);
        mTab1 = (TextView)findViewById(R.id.exchange_all);
        mTab2 = (TextView)findViewById(R.id.exchange_not);
        mTab3 = (TextView)findViewById(R.id.exchange_used);
        mTab1.setOnClickListener(new MyOnClickListener(0));
        mTab2.setOnClickListener(new MyOnClickListener(1));
        mTab3.setOnClickListener(new MyOnClickListener(2));
    }

    private void InitViewPager(){
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);
        fragmentArrayList = new ArrayList<>();
        fragment1 = new RewardAllFragment();
        fragment2 = new RewardNotFragment();
        fragment3 = new RewardExchangeFragment();
        fragmentArrayList.add(fragment1);
        fragmentArrayList.add(fragment2);
        fragmentArrayList.add(fragment3);
        viewPager.setAdapter(new ViewpagerAdapter(getSupportFragmentManager(),fragmentArrayList));
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        Intent intent = getIntent();
        postion = intent.getStringExtra("postion");
        int i = Integer.parseInt(postion);
        if(i == 0){
            TranslateAnimation animation = new TranslateAnimation(
                    position_zero, position_zero, 0, 0);
            animation.setFillAfter(true);
            animation.setDuration(300);
            ivBottomLine.startAnimation(animation);
            position_old = position_zero;
        }else if(i == 1){
            TranslateAnimation animation = new TranslateAnimation(position_one,
                    position_one, 0, 0);
            animation.setFillAfter(true);
            animation.setDuration(300);
            ivBottomLine.startAnimation(animation);
            position_old = position_one;
        }else if(i == 2){
            TranslateAnimation animation = new TranslateAnimation(position_two,
                    position_two, 0, 0);
            animation.setFillAfter(true);
            animation.setDuration(300);
            ivBottomLine.startAnimation(animation);
            position_old = position_two;
        }
        viewPager.setCurrentItem(i);
        ivBottomLine.setVisibility(View.VISIBLE);
    }

    private void InitWidth(){
        ivBottomLine = (ImageView)findViewById(R.id.nav_line_bottom);
        bottomLineWidth = ivBottomLine.getLayoutParams().width;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = (screenW / num - bottomLineWidth) / 2;
        int avg = screenW / num;
        position_zero = offset;
        position_one = avg + offset;
        position_two = 2 * avg + offset;
        position_old = offset;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;
        public MyOnClickListener(int i){
            index = i;
        }

        @Override
        public void onClick(View v) {
            viewPager.setCurrentItem(index);
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Animation animation = null;
            resetTxts();
            switch (position){
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
            }
            animation.setFillAfter(true);
            animation.setDuration(300);
            ivBottomLine.startAnimation(animation);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    public void resetTxts() {
        mTab1.setTextColor(getResources().getColor(R.color.black));
        mTab2.setTextColor(getResources().getColor(R.color.black));
        mTab3.setTextColor(getResources().getColor(R.color.black));
    }
}
