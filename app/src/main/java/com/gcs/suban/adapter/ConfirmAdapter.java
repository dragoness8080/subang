package com.gcs.suban.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.ShopDataBean;

import java.util.List;

public class ConfirmAdapter extends BaseListAdapter<ShopDataBean> {
	private String pic;

	public ConfirmAdapter(Context context, List<ShopDataBean> list) {
		super(context, list);
		InitImageLoader();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ShopDataBean bean = listItems.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item_confirm, null);
			holder = new ViewHolder();
			holder.Img_pic = (ImageView) convertView.findViewById(R.id.img_pic);
			holder.Tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.Tv_price = (TextView) convertView
					.findViewById(R.id.tv_price);
			holder.Tv_num = (TextView) convertView
					.findViewById(R.id.tv_num);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		pic = bean.thumb;
		imageLoader.displayImage(pic, holder.Img_pic, options);

		holder.Tv_name.setText(bean.title);
		holder.Tv_price.setText("×Ü¼Û" + bean.marketprice);
		holder.Tv_num.setText("x" + bean.total);
		return convertView;
	}

	private final class ViewHolder {
		public ImageView Img_pic; // ?????
		public TextView Tv_name; // ???????
		public TextView Tv_price; // ??????
		public TextView Tv_num; // ???????
	}
}