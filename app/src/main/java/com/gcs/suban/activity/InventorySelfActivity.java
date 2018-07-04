package com.gcs.suban.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.adapter.ViewpagerAdapter;
import com.gcs.suban.fragment.InventorySelfApplyFragment;
import com.gcs.suban.fragment.InventorySelfDeliverFragment;
import com.gcs.suban.fragment.InventorySelfFinishFragment;
import com.gcs.suban.fragment.InventorySelfPayFragment;

import java.util.ArrayList;

public class InventorySelfActivity extends FragmentActivity implements OnClickListener {

    protected ImageButton Ib_back;
    protected TextView Tv_title;
    protected Button Ib_add;
    protected TextView mTab1,mTab2,mTab3,mTab4;
    protected ViewPager viewPager;
    protected ImageView ivBottomLine;

    protected int bottomLineWidth;
    protected int offset = 0;
    protected int position_old,position_now,position_zero,position_one,position_two,position_three;
    protected Fragment fragment1,fragment2,fragment3,fragment4;
    protected ArrayList<Fragment> fragments;
    public final static int num = 4;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_inventoryself);
        init();
        initWidth();
        initViewPager();
    }

    protected void init(){
        Ib_back = (ImageButton)findViewById(R.id.back_stock);
        Tv_title = (TextView)findViewById(R.id.title_stock);
        Ib_add = (Button) findViewById(R.id.wallet_btn);
        mTab1 = (TextView)findViewById(R.id.tv_tab_1);
        mTab2 = (TextView)findViewById(R.id.tv_tab_2);
        mTab3 = (TextView)findViewById(R.id.tv_tab_3);
        mTab4 = (TextView)findViewById(R.id.tv_tab_4);

        Tv_title.setText("自提订单");

        Ib_back.setOnClickListener(this);
        Ib_add.setText("自提");
        Ib_add.setOnClickListener(this);

        mTab1.setOnClickListener(new MyOnClickListener(0));
        mTab2.setOnClickListener(new MyOnClickListener(1));
        mTab3.setOnClickListener(new MyOnClickListener(2));
        mTab4.setOnClickListener(new MyOnClickListener(3));

    }

    protected void initWidth(){
        ivBottomLine = (ImageView)findViewById(R.id.iv_bottom_line);
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

    protected void initViewPager(){
        viewPager = (ViewPager)findViewById(R.id.vp_fragment);
        viewPager.setOffscreenPageLimit(4);
        fragments = new ArrayList<Fragment>();

        fragment1 = new InventorySelfApplyFragment();
        fragment2 = new InventorySelfPayFragment();
        fragment3 = new InventorySelfDeliverFragment();
        fragment4 = new InventorySelfFinishFragment();

        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);

        viewPager.setAdapter(new ViewpagerAdapter(getSupportFragmentManager(),fragments));
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());

        int i = 0;
        if(i == 0){
            TranslateAnimation animation = new TranslateAnimation(position_zero,position_zero,0,0);
            animation.setFillAfter(true);
            animation.setDuration(300);
            ivBottomLine.startAnimation(animation);
            position_old = position_zero;
        }else if(i == 1){
            TranslateAnimation animation = new TranslateAnimation(position_one,position_one,0,0);
            animation.setFillAfter(true);
            animation.setDuration(300);
            ivBottomLine.startAnimation(animation);
            position_old = position_one;
        }else if(i == 2){
            TranslateAnimation animation = new TranslateAnimation(position_two,position_two,0,0);
            animation.setFillAfter(true);
            animation.setDuration(300);
            ivBottomLine.startAnimation(animation);
            position_old = position_two;
        }else if(i == 3){
            TranslateAnimation animation = new TranslateAnimation(position_three,position_three,0,0);
            animation.setFillAfter(true);
            animation.setDuration(300);
            ivBottomLine.startAnimation(animation);
            position_old = position_three;
        }
        viewPager.setCurrentItem(i);
        ivBottomLine.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_stock:
                finish();
                break;
            case R.id.wallet_btn:
                Intent intent = new Intent(this,InventorySelfAddActivity.class);
                startActivity(intent);
            default:
                break;
        }
    }

    public void restTxts(){
        mTab1.setTextColor(getResources().getColor(R.color.black));
        mTab2.setTextColor(getResources().getColor(R.color.black));
        mTab3.setTextColor(getResources().getColor(R.color.black));
        mTab4.setTextColor(getResources().getColor(R.color.black));
    }

    class MyOnClickListener implements OnClickListener{
        protected int index = 0;

        public MyOnClickListener(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            viewPager.setCurrentItem(index);
        }
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Animation animation = null;
            restTxts();
            switch (position){
                case 0:
                    position_now = position_zero;
                    animation = new TranslateAnimation(position_old,position_now,0,0);
                    position_old = position_now;
                    mTab1.setTextColor(getResources().getColor(R.color.themeOrange));
                    break;
                case 1:
                    position_now = position_one;
                    animation = new TranslateAnimation(position_old,position_now,0,0);
                    position_old = position_now;
                    mTab2.setTextColor(getResources().getColor(R.color.themeOrange));
                    break;
                case 2:
                    position_now = position_two;
                    animation = new TranslateAnimation(position_old,position_now,0,0);
                    position_old = position_now;
                    mTab3.setTextColor(getResources().getColor(R.color.themeOrange));
                    break;
                case 3:
                    position_now = position_three;
                    animation = new TranslateAnimation(position_old,position_now,0,0);
                    position_old = position_now;
                    mTab4.setTextColor(getResources().getColor(R.color.themeOrange));
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
}
