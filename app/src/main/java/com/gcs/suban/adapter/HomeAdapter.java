package com.gcs.suban.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.ShopDataBean3;

import java.util.List;

public class HomeAdapter extends BaseListAdapter<ShopDataBean3> {
	private String id;
	private String pic;

	class ViewHolder { // 自定义控件集合
//		public TextView Tv_name;
		public ImageView Img_pic;
	}

	public HomeAdapter(Context context, List<ShopDataBean3> listItems) {
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
			convertView = mInflater.inflate(R.layout.list_item_home, null);
/*			// 获取控件对象
			holder.Tv_name = (TextView) convertView.findViewById(R.id.tv_name);*/
			holder.Img_pic = (ImageView) convertView.findViewById(R.id.img_pic);
			// 设置控件集到convertView
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			convertView.setTag(holder);
		}

		// 获取地址id和是否为默认地址
//		holder.Tv_name.setText(listItems.get(position).title);
		id = listItems.get(position).getGid();
		pic = listItems.get(position).getImgurl();

		imageLoader.displayImage(pic, holder.Img_pic, options);
		convertView.setTag(R.id.tag_first, id);
		return convertView;
	}
}
