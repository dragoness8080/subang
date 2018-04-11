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
 * ��������һ�ΰ�װapp ���뵼������
 */
public class LoginFirstActivity extends BaseActivity implements
		OnClickListener, OnPageChangeListener {
	// ����ViewPager����
	private ViewPager viewPager;
	// ����ViewPager������
	private LoginFirstViewPagerAdapter vpAdapter;
	// ����һ��ArrayList�����View
	private ArrayList<View> views;
	// ����ͼƬ��Դ
	private static final String[] pics = { "", "", "", "" };
	
	// �ײ�С���ͼƬ
	private ImageView[] points;
	// ��¼��ǰѡ��λ��
	private int currentIndex;
	
	private Boolean misScrolled;

	/**
	 * ��ʼ�����
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
	 * ��ʼ������
	 */
	private void initData() {
		// ʵ����ArrayList����
		views = new ArrayList<View>();
		// ʵ����ViewPager
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		// ʵ����ViewPager������
		vpAdapter = new LoginFirstViewPagerAdapter(views);
		// ����һ�����ֲ����ò���
		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);

		// ��ʼ������ͼƬ�б�
		for (int i = 0; i < pics.length; i++) {
			ImageView iv = new ImageView(this);
			iv.setLayoutParams(mParams);
			// ��ֹͼƬ����������Ļ
			iv.setScaleType(ScaleType.FIT_XY);
			// ����ͼƬ��Դ
			ImageLoader.getInstance().displayImage(pics[i], iv, options);
			views.add(iv);
		}

		// ��������
		viewPager.setAdapter(vpAdapter);
		// ���ü���
		viewPager.addOnPageChangeListener(this);

		// ��ʼ���ײ�С��
		initPoint();
	}

	/**
	 * ��ʼ���ײ�С��
	 */
	private void initPoint() {
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll);

		points = new ImageView[pics.length];

		// ѭ��ȡ��С��ͼƬ
		for (int i = 0; i < pics.length; i++) {
			// �õ�һ��LinearLayout�����ÿһ����Ԫ��
			points[i] = (ImageView) linearLayout.getChildAt(i);
			// Ĭ�϶���Ϊ��ɫ
			points[i].setEnabled(true);
			// ��ÿ��С�����ü���
			points[i].setOnClickListener(this);
			// ����λ��tag������ȡ���뵱ǰλ�ö�Ӧ
			points[i].setTag(i);
		}

		// ���õ���Ĭ�ϵ�λ��
		currentIndex = 0;
		// ����Ϊ��ɫ����ѡ��״̬
		points[currentIndex].setEnabled(false);
	}

	/**
	 * ����״̬�ı�ʱ����
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
	 * ��ǰҳ�滬��ʱ����
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}
	
	

	/**
	 * �µ�ҳ�汻ѡ��ʱ����
	 */
	@Override
	public void onPageSelected(int arg0) {
		// ���õײ�С��ѡ��״̬
		setCurDot(arg0);
	}

	@Override
	public void onClick(View v) {
		int position = (Integer) v.getTag();
		setCurView(position);
		setCurDot(position);
	}

	/**
	 * ���õ�ǰҳ���λ��
	 */
	private void setCurView(int position) {
		if (position < 0 || position >= pics.length) {
			return;
		}
		viewPager.setCurrentItem(position);
	}

	/**
	 * ���õ�ǰ��С���λ��
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
