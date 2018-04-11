package com.gcs.suban.base;

import com.gcs.suban.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;

public abstract class BaseFragment extends Fragment implements OnClickListener {
	protected Activity context;
	protected String TAG;
	protected String vid;
	protected ImageLoader imageLoader;
	protected DisplayImageOptions options; // ����ͼƬ��ʾ��ز���
	protected DisplayImageOptions options2; 
	protected DisplayImageOptions options3; 
	protected SwipeRefreshLayout swipeRefreshLayout;
	protected Animation animation;
	protected LayoutAnimationController controller;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		context = getActivity();
		TAG = getRunningActivityName();
		animation = new AlphaAnimation(0f, 1f);
		animation.setDuration(500);
		controller = new LayoutAnimationController(animation, 1f);
		controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
		init();
	}

	//�ؼ�����
	protected abstract void init();

	// ��ȡ��ǰ����Activity����
	private String getRunningActivityName() {
		String contextString = context.toString();
		return contextString.substring(contextString.lastIndexOf(".") + 1,
				contextString.indexOf("@"));
	}
	
	// SwipeRefreshLayout��ʼ��
		protected void InitSwipeRefreshLayout() {
			swipeRefreshLayout.setColorSchemeResources(R.color.themeRed, R.color.themeRed, R.color.themeRed,
					R.color.themeRed);
		}
		
	// imageLoader��ʼ��
	protected void InitImageLoader() {
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
			//	.showImageOnLoading(R.drawable.background) // ����ͼƬ�����ڼ���ʾ��ͼƬ
			//	.showImageForEmptyUri(R.drawable.background) // ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
			//	.showImageOnFail(R.drawable.background) // ����ͼƬ���ػ��������з���������ʾ��ͼƬ
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT) // ͼƬ���ŷ�ʽ
				.bitmapConfig(Bitmap.Config.RGB_565)// ����ͼƬ�Ľ�������//
				.cacheInMemory(true) // �������ص�ͼƬ�Ƿ񻺴����ڴ���
				.cacheOnDisk(true) // �������ص�ͼƬ�Ƿ񻺴���SD����
				.displayer(new RoundedBitmapDisplayer(0)) // ���ó�Բ��ͼƬ
				.build(); // �������
		options2 = new DisplayImageOptions.Builder()
		//	.showImageOnLoading(R.drawable.background) // ����ͼƬ�����ڼ���ʾ��ͼƬ
		//	.showImageForEmptyUri(R.drawable.background) // ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
		//	.showImageOnFail(R.drawable.background) // ����ͼƬ���ػ��������з���������ʾ��ͼƬ
			.imageScaleType(ImageScaleType.IN_SAMPLE_INT) // ͼƬ���ŷ�ʽ
			.bitmapConfig(Bitmap.Config.RGB_565)// ����ͼƬ�Ľ�������//
			.cacheInMemory(true) // �������ص�ͼƬ�Ƿ񻺴����ڴ���
			.cacheOnDisk(true) // �������ص�ͼƬ�Ƿ񻺴���SD����
			.displayer(new RoundedBitmapDisplayer(20)) // ���ó�Բ��ͼƬ
			.build(); // �������
		options3 = new DisplayImageOptions.Builder()
		//	.showImageOnLoading(R.drawable.background) // ����ͼƬ�����ڼ���ʾ��ͼƬ
		//	.showImageForEmptyUri(R.drawable.background) // ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
		//	.showImageOnFail(R.drawable.background) // ����ͼƬ���ػ��������з���������ʾ��ͼƬ
			.imageScaleType(ImageScaleType.IN_SAMPLE_INT) // ͼƬ���ŷ�ʽ
			.bitmapConfig(Bitmap.Config.RGB_565)// ����ͼƬ�Ľ�������//
			.cacheInMemory(true) // �������ص�ͼƬ�Ƿ񻺴����ڴ���
			.cacheOnDisk(true) // �������ص�ͼƬ�Ƿ񻺴���SD����
			.displayer(new RoundedBitmapDisplayer(360)) // ���ó�Բ��ͼƬ
			.build(); // �������
	}

}