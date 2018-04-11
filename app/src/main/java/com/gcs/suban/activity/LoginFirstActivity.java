package com.gcs.suban.activity;

import java.util.ArrayList;
import com.gcs.suban.R;
import com.gcs.suban.adapter.LoginFirstViewPagerAdapter;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.tools.SharedPreference;
import com.nostra13.universalimageloader.core.ImageLoader;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;

/**
 * 描述：第一次安装app 进入导航界面
 */
public class LoginFirstActivity extends BaseActivity implements
		OnClickListener, OnPageChangeListener {
	// 定义ViewPager对象
	private ViewPager viewPager;
	// 定义ViewPager适配器
	private LoginFirstViewPagerAdapter vpAdapter;
	// 定义一个ArrayList来存放View
	private ArrayList<View> views;
	// 引导图片资源
	private static final String[] pics = { "", "", "", "" };
	
	// 底部小点的图片
	private ImageView[] points;
	// 记录当前选中位置
	private int currentIndex;
	
	private Boolean misScrolled;

	/**
	 * 初始化组件
	 */
	@Override
	protected void init() {
		SharedPreference.setParam(context,"first","1");
		InitImageLoader();
		setContentView(R.layout.activity_first);
		Intent intent=getIntent();
		pics[0]=intent.getStringExtra("pic1");
		pics[1]=intent.getStringExtra("pic2");
		pics[2]=intent.getStringExtra("pic3");
		pics[3]=intent.getStringExtra("pic4");

		initData();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// 实例化ArrayList对象
		views = new ArrayList<View>();
		// 实例化ViewPager
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		// 实例化ViewPager适配器
		vpAdapter = new LoginFirstViewPagerAdapter(views);
		// 定义一个布局并设置参数
		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);

		// 初始化引导图片列表
		for (int i = 0; i < pics.length; i++) {
			ImageView iv = new ImageView(this);
			iv.setLayoutParams(mParams);
			// 防止图片不能填满屏幕
			iv.setScaleType(ScaleType.FIT_XY);
			// 加载图片资源
			ImageLoader.getInstance().displayImage(pics[i], iv, options);
			views.add(iv);
		}

		// 设置数据
		viewPager.setAdapter(vpAdapter);
		// 设置监听
		viewPager.addOnPageChangeListener(this);

		// 初始化底部小点
		initPoint();
	}

	/**
	 * 初始化底部小点
	 */
	private void initPoint() {
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll);

		points = new ImageView[pics.length];

		// 循环取得小点图片
		for (int i = 0; i < pics.length; i++) {
			// 得到一个LinearLayout下面的每一个子元素
			points[i] = (ImageView) linearLayout.getChildAt(i);
			// 默认都设为灰色
			points[i].setEnabled(true);
			// 给每个小点设置监听
			points[i].setOnClickListener(this);
			// 设置位置tag，方便取出与当前位置对应
			points[i].setTag(i);
		}

		// 设置当面默认的位置
		currentIndex = 0;
		// 设置为白色，即选中状态
		points[currentIndex].setEnabled(false);
	}

	/**
	 * 滑动状态改变时调用
	 */
	@Override
	public void onPageScrollStateChanged(int state) {
		 switch (state) {
		    case ViewPager.SCROLL_STATE_DRAGGING:
		        misScrolled = false;
		        break;
		    case ViewPager.SCROLL_STATE_SETTLING:
		        misScrolled = true;
		        break;
		    case ViewPager.SCROLL_STATE_IDLE:
		        if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !misScrolled) {
		            intent();
		        }
		        misScrolled = true;
		        break;
		 }
	}

	/**
	 * 当前页面滑动时调用
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}
	
	

	/**
	 * 新的页面被选中时调用
	 */
	@Override
	public void onPageSelected(int arg0) {
		// 设置底部小点选中状态
		setCurDot(arg0);
	}

	@Override
	public void onClick(View v) {
		int position = (Integer) v.getTag();
		setCurView(position);
		setCurDot(position);
	}

	/**
	 * 设置当前页面的位置
	 */
	private void setCurView(int position) {
		if (position < 0 || position >= pics.length) {
			return;
		}
		viewPager.setCurrentItem(position);
	}

	/**
	 * 设置当前的小点的位置
	 */
	private void setCurDot(int positon) {
		if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {
			return;
		}
		points[positon].setEnabled(false);
		points[currentIndex].setEnabled(true);

		currentIndex = positon;
	}

	public void intent() {
		String vid = (String) SharedPreference.getParam(context, "vid", "");
		if (vid.equals("")) {
			Intent intent_enter = new Intent(context, LoginActivity.class);
			startActivity(intent_enter);
			context.finish();
		} else {
			Intent intent_enter2 = new Intent(context, MainActivity.class);
			startActivity(intent_enter2);
			context.finish();
		}
	}
}
