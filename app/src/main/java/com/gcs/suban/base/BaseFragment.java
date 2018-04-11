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
	protected DisplayImageOptions options; // 设置图片显示相关参数
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

	//控件设置
	protected abstract void init();

	// 获取当前运行Activity名字
	private String getRunningActivityName() {
		String contextString = context.toString();
		return contextString.substring(contextString.lastIndexOf(".") + 1,
				contextString.indexOf("@"));
	}
	
	// SwipeRefreshLayout初始化
		protected void InitSwipeRefreshLayout() {
			swipeRefreshLayout.setColorSchemeResources(R.color.themeRed, R.color.themeRed, R.color.themeRed,
					R.color.themeRed);
		}
		
	// imageLoader初始化
	protected void InitImageLoader() {
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
			//	.showImageOnLoading(R.drawable.background) // 设置图片下载期间显示的图片
			//	.showImageForEmptyUri(R.drawable.background) // 设置图片Uri为空或是错误的时候显示的图片
			//	.showImageOnFail(R.drawable.background) // 设置图片加载或解码过程中发生错误显示的图片
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT) // 图片缩放方式
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
				.displayer(new RoundedBitmapDisplayer(0)) // 设置成圆角图片
				.build(); // 构建完成
		options2 = new DisplayImageOptions.Builder()
		//	.showImageOnLoading(R.drawable.background) // 设置图片下载期间显示的图片
		//	.showImageForEmptyUri(R.drawable.background) // 设置图片Uri为空或是错误的时候显示的图片
		//	.showImageOnFail(R.drawable.background) // 设置图片加载或解码过程中发生错误显示的图片
			.imageScaleType(ImageScaleType.IN_SAMPLE_INT) // 图片缩放方式
			.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
			.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
			.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
			.displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
			.build(); // 构建完成
		options3 = new DisplayImageOptions.Builder()
		//	.showImageOnLoading(R.drawable.background) // 设置图片下载期间显示的图片
		//	.showImageForEmptyUri(R.drawable.background) // 设置图片Uri为空或是错误的时候显示的图片
		//	.showImageOnFail(R.drawable.background) // 设置图片加载或解码过程中发生错误显示的图片
			.imageScaleType(ImageScaleType.IN_SAMPLE_INT) // 图片缩放方式
			.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
			.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
			.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
			.displayer(new RoundedBitmapDisplayer(360)) // 设置成圆角图片
			.build(); // 构建完成
	}

}