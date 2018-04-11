package com.gcs.suban.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.ShopDataBean;

import java.util.List;

public class OrderChildrenAdapter extends BaseListAdapter<ShopDataBean>{
	

	public OrderChildrenAdapter(Context context, List<ShopDataBean> listItems) {
		super(context, listItems);
		InitImageLoader();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.i("method", "getView" + position);

		// 自定义视图
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			// 获取list_item布局文件的视图
			convertView = mInflater.inflate(R.layout.list_item_orderchildren, null);
			// 获取控件对象
			holder.Tv_ordersn = (TextView) convertView.findViewById(R.id.tv_ordersn);
			holder.Tv_status = (TextView) convertView
					.findViewById(R.id.tv_status);
			holder.Tv_payprice = (TextView) convertView
					.findViewById(R.id.tv_payprice);
			holder.Tv_dispatchprice = (TextView) convertView.findViewById(R.id.tv_dispatchprice);
			holder.Btn_Left = (Button) convertView.findViewById(R.id.btn_right);
			holder.Btn_Right = (Button) convertView.findViewById(R.id.btn_buy);
			holder.listView=(ListView)convertView.findViewById(R.id.listView_children);
			// 设置控件集到convertView
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			convertView.setTag(holder);
		}

		

		return convertView;
	}
	
	class ViewHolder { // 自定义控件集合
		public TextView Tv_ordersn;
		public TextView Tv_status;
		public TextView Tv_dispatchprice;
		public TextView Tv_payprice;
		public Button Btn_Left;
		public Button Btn_Right;
		public ListView listView;
	}

}
