package com.gcs.suban.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.OrderBean;
import com.gcs.suban.bean.ShopDataBean;

public class TeamOrderAdapter extends BaseListAdapter<OrderBean>{
	public List<OrderBean> lists = new ArrayList<OrderBean>();
	public List<ShopDataBean> mListType;

	class ViewHolder { // 自定义控件集合
		public TextView Tv_ordersn;
		public TextView Tv_time;
		public TextView Tv_status;
		public TextView Tv_price;
		public TextView Tv_nickname;
		public ImageView Img_vip;
	}

	public TeamOrderAdapter(Context context, List<OrderBean> listItems) {
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
			convertView = mInflater.inflate(R.layout.list_item_teamorder, null);
			// 获取控件对象
			holder.Tv_nickname = (TextView) convertView
					.findViewById(R.id.tv_nickname);
			holder.Tv_ordersn = (TextView) convertView
					.findViewById(R.id.tv_ordersn);
			holder.Tv_time= (TextView) convertView
					.findViewById(R.id.tv_time);
			holder.Tv_status = (TextView) convertView
					.findViewById(R.id.tv_status);
			holder.Tv_price = (TextView) convertView
					.findViewById(R.id.tv_price);
			holder.Img_vip = (ImageView) convertView
					.findViewById(R.id.img_vip);
			// 设置控件集到convertView
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			convertView.setTag(holder);
		}

		holder.Tv_nickname.setText(listItems.get(position).nickname);
		holder.Tv_time.setText(listItems.get(position).createtime);
		holder.Tv_ordersn.setText(listItems.get(position).ordersn);
		holder.Tv_status.setText(listItems.get(position).statusname);
		holder.Tv_price.setText("￥" + listItems.get(position).price);
		String level=listItems.get(position).level;
		if(level.equals("1"))
		{
			holder.Img_vip.setBackgroundResource(R.drawable.icon_vip1);
		}
		else if(level.equals("2"))
		{
			holder.Img_vip.setBackgroundResource(R.drawable.icon_vip2);
		}
		else {
			holder.Img_vip.setBackgroundResource(R.drawable.icon_vip3);
		}
		

		return convertView;
	}
}
