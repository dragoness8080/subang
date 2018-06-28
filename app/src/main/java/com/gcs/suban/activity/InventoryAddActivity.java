package com.gcs.suban.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
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
import com.gcs.suban.fragment.AddGoodsFragment;
import com.gcs.suban.fragment.AddMoneyFragment;

import java.util.ArrayList;


public class InventoryAddActivity extends FragmentActivity implements OnClickListener {

    Resources resources;
    protected ViewPager viewPager;
    protected ImageButton Img_back;
    protected TextView Tv_title;
    protected Button Img_title;
    protected TextView mTab1,mTab2;
    protected ArrayList<Fragment> fragmentsList;
    protected ImageView ivBottomLine;
    Fragment fragment1,fragment2;
    protected int position_old,position_zero,position_new,position_one;
    protected int bottomWidth;
    protected int offset = 0;
    public final static int num = 2;
    public String position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_inventoryadd);
        resources = getResources();
        init();
        initWidth();
        initViewPager();
    }

    private void init(){
        Img_back = (ImageButton)findViewById(R.id.back_stock);
        Tv_title = (TextView)findViewById(R.id.title_stock);
        Img_title = (Button)findViewById(R.id.wallet_btn);
        Img_title.setVisibility(View.GONE);
        mTab1 = (TextView)findViewById(R.id.tv_radio_1);
        mTab2 = (TextView)findViewById(R.id.tv_radio_2);

        Tv_title.setText("¿â´æ²¹²Ö");

        Img_back.setOnClickListener(this);
        mTab1.setOnClickListener(new MyOnClickListener(0));
        mTab2.setOnClickListener(new MyOnClickListener(1));
    }

    private void initViewPager(){
        viewPager = (ViewPager)findViewById(R.id.stock_page);
        viewPager.setOffscreenPageLimit(2);
        fragmentsList = new ArrayList<Fragment>();

        fragment1 = new AddMoneyFragment();
        fragment2 = new AddGoodsFragment();

        fragmentsList.add(fragment1);
        fragmentsList.add(fragment2);

        viewPager.setAdapter(new ViewpagerAdapter(getSupportFragmentManager(),fragmentsList));
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        //Intent intent = getIntent();
        //position = intent.getStringExtra("position");

        //int i = Integer.parseInt(position);
        int i = 0;
        if(i == 0){
            TranslateAnimation animation = new TranslateAnimation(position_zero,position_zero,0,0);
            animation.setFillAfter(true);
            animation.setDuration(300);
            ivBottomLine.startAnimation(animation);
            position_old = position_zero;
        }else if(i == 1){
            TranslateAnimation animation = new TranslateAnimation(position_one, position_one,0,0);
            animation.setFillAfter(true);
            animation.setDuration(300);
            ivBottomLine.startAnimation(animation);
            position_old = position_one;
        }
        viewPager.setCurrentItem(i);
        ivBottomLine.setVisibility(View.VISIBLE);
    }

    private void initWidth(){
        ivBottomLine = (ImageView)findViewById(R.id.stock_bottom_line);
        bottomWidth = ivBottomLine.getLayoutParams().width;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = (screenW / num - bottomWidth) / 2;
        int avg = screenW /num;
        position_zero = offset;
        position_one = avg + offset;
        position_old = offset;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_stock:
                finish();
                break;
            case R.id.wallet_btn:
                break;
            default:
                break;
        }
    }

    public class MyOnClickListener implements OnClickListener{

        private int index = 0;
        public MyOnClickListener(int i){ index = i;}

        @Override
        public void onClick(View v) {
            viewPager.setCurrentItem(index);
        }
    }

    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Animation animation = null;
            resetText();
            switch (position){
                case 0:
                    position_new = position_zero;
                    animation = new TranslateAnimation(position_old,position_new,0,0);
                    position_old = position_new;
                    mTab1.setTextColor(getResources().getColor(R.color.themeOrange));
                    break;
                case 1:
                    position_new = position_one;
                    animation = new TranslateAnimation(position_old,position_new,0,0);
                    position_old = position_new;
                    mTab2.setTextColor(getResources().getColor(R.color.themeOrange));
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

    public void resetText(){
        mTab1.setTextColor(getResources().getColor(R.color.black));
        mTab2.setTextColor(getResources().getColor(R.color.black));
    }
}
