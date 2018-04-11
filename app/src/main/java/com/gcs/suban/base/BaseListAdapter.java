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
	protected DisplayImageOptions options; // ����ͼƬ��ʾ��ز���
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

	// imageLoader��ʼ��
	protected void InitImageLoader() {
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.bg_pic) // ����ͼƬ�����ڼ���ʾ��ͼƬ
				.showImageForEmptyUri(R.drawable.bg_pic) // ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
				.showImageOnFail(R.drawable.bg_pic) // ����ͼƬ���ػ��������з���������ʾ��ͼƬ
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT) // ͼƬ���ŷ�ʽ
				.bitmapConfig(Bitmap.Config.RGB_565)// ����ͼƬ�Ľ�������//
				 .cacheInMemory(true) // �������ص�ͼƬ�Ƿ񻺴����ڴ���
				 .cacheOnDisk(true) // �������ص�ͼƬ�Ƿ񻺴���SD����
				 .displayer(new RoundedBitmapDisplayer(0)) // ���ó�Բ��ͼƬ
				.build(); // �������
		
		options2 = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.bg_pic) // ����ͼƬ�����ڼ���ʾ��ͼƬ
			.showImageForEmptyUri(R.drawable.bg_pic) // ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
			.showImageOnFail(R.drawable.bg_pic) // ����ͼƬ���ػ��������з���������ʾ��ͼƬ
			.imageScaleType(ImageScaleType.IN_SAMPLE_INT) // ͼƬ���ŷ�ʽ
			.bitmapConfig(Bitmap.Config.RGB_565)// ����ͼƬ�Ľ�������//
			 .cacheInMemory(true) // �������ص�ͼƬ�Ƿ񻺴����ڴ���
			 .cacheOnDisk(true) // �������ص�ͼƬ�Ƿ񻺴���SD����
			 .displayer(new RoundedBitmapDisplayer(20)) // ���ó�Բ��ͼƬ
			.build(); // �������
		
		options3 = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.bg_pic) // ����ͼƬ�����ڼ���ʾ��ͼƬ
			.showImageForEmptyUri(R.drawable.bg_pic) // ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
			.showImageOnFail(R.drawable.bg_pic) // ����ͼƬ���ػ��������з���������ʾ��ͼƬ
			.imageScaleType(ImageScaleType.IN_SAMPLE_INT) // ͼƬ���ŷ�ʽ
			.bitmapConfig(Bitmap.Config.RGB_565)// ����ͼƬ�Ľ�������//
			 .cacheInMemory(true) // �������ص�ͼƬ�Ƿ񻺴����ڴ���
			 .cacheOnDisk(true) // �������ص�ͼƬ�Ƿ񻺴���SD����
			 .displayer(new RoundedBitmapDisplayer(90)) // ���ó�Բ��ͼƬ
			.build(); // �������
	}
	


}
