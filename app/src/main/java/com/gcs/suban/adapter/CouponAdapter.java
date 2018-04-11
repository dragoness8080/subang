package com.gcs.suban.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.CouponBean;
import com.gcs.suban.bean.ShopDataBean;

public class CouponAdapter  extends BaseListAdapter<CouponBean> {
	public List<ShopDataBean> lists = new ArrayList<ShopDataBean>();
	
	class ViewHolder { // 自定义控件集合
		public TextView Tv_name;
		public TextView Tv_price;
		public TextView Tv_time;
	}

	public CouponAdapter(Context context, List<CouponBean> listItems) {
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
			convertView = mInflater.inflate(R.layout.list_item_coupon, null);
			// 获取控件对象
			holder.Tv_name = (TextView) convertView.findViewById(R.id.tv_couponname);
			holder.Tv_price = (TextView) convertView
					.findViewById(R.id.tv_deduct);
			holder.Tv_time = (TextView) convertView
					.findViewById(R.id.tv_time);
			// 设置控件集到convertView
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			convertView.setTag(holder);
		}

		// 获取地址id和是否为默认地址
		holder.Tv_name.setText(listItems.get(position).couponname);
		holder.Tv_price.setText(listItems.get(position).deduct);
		holder.Tv_time.setText("有效期至"
				+ listItems.get(position).useday);
		return convertView;
	}
}
