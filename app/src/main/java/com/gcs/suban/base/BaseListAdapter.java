package com.gcs.suban.base;

import java.util.ArrayList;
import java.util.List;

import com.gcs.suban.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

public abstract class BaseListAdapter<T extends Object> extends BaseAdapter {
	//protected RequestQueue queue;
	protected LayoutInflater mInflater = null;
	protected List<T> listItems;
	protected Context context;

	protected ImageLoader imageLoader;
	protected DisplayImageOptions options; // 设置图片显示相关参数
	protected DisplayImageOptions options2; 
	protected DisplayImageOptions options3; 
	
	//protected String vid = MyDate.getMyVid();

	public BaseListAdapter(Context context) {
		this(context, new ArrayList<T>());
	}

	public BaseListAdapter(Context context, List<T> list) {
		//queue = Volley.newRequestQueue(context);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		this.listItems = list;
	}

	public void setList(List<T> list) {
		this.listItems = list;
		this.notifyDataSetChanged();
	}

	public void clear() {
		listItems.clear();
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return listItems.size();
	}

	@Override
	public T getItem(int position) {
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	// imageLoader初始化
	protected void InitImageLoader() {
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.bg_pic) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.bg_pic) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.bg_pic) // 设置图片加载或解码过程中发生错误显示的图片
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT) // 图片缩放方式
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
				 .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				 .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
				 .displayer(new RoundedBitmapDisplayer(0)) // 设置成圆角图片
				.build(); // 构建完成
		
		options2 = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.bg_pic) // 设置图片下载期间显示的图片
			.showImageForEmptyUri(R.drawable.bg_pic) // 设置图片Uri为空或是错误的时候显示的图片
			.showImageOnFail(R.drawable.bg_pic) // 设置图片加载或解码过程中发生错误显示的图片
			.imageScaleType(ImageScaleType.IN_SAMPLE_INT) // 图片缩放方式
			.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
			 .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
			 .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
			 .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
			.build(); // 构建完成
		
		options3 = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.bg_pic) // 设置图片下载期间显示的图片
			.showImageForEmptyUri(R.drawable.bg_pic) // 设置图片Uri为空或是错误的时候显示的图片
			.showImageOnFail(R.drawable.bg_pic) // 设置图片加载或解码过程中发生错误显示的图片
			.imageScaleType(ImageScaleType.IN_SAMPLE_INT) // 图片缩放方式
			.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
			 .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
			 .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
			 .displayer(new RoundedBitmapDisplayer(90)) // 设置成圆角图片
			.build(); // 构建完成
	}
	


}
