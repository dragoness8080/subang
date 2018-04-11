package com.gcs.suban.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.HlvSimpleBean;

public class CultureBrandAdapter extends BaseListAdapter<HlvSimpleBean>{

	class ViewHolder { // 自定义控件集合
		public TextView Tv_title;
		public TextView Tv_time;
		public ImageView Img_pic;
	}

	public CultureBrandAdapter(Context context, List<HlvSimpleBean> listItems) {
		super(context, listItems);
		InitImageLoader();
	}

	/**
	 * ListView Item设置
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.i("method", "getView" + position);

		// 自定义视图
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			// 获取list_item布局文件的视图
			convertView = mInflater.inflate(R.layout.list_item_brand, null);
			// 获取控件对象
			holder.Tv_title = (TextView) convertView
					.findViewById(R.id.tv_title);
			holder.Tv_time = (TextView) convertView
					.findViewById(R.id.tv_time);
			holder.Img_pic=(ImageView)convertView
					.findViewById(R.id.img_pic);
			// 设置控件集到convertView
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			convertView.setTag(holder);
		}

		
		holder.Tv_title.setText(listItems.get(position).title);
		holder.Tv_time.setText(listItems.get(position).createtime);
		imageLoader.displayImage(listItems.get(position).thumb, holder.Img_pic,options);

		return convertView;
	}

}

