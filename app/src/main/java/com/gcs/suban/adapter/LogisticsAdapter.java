package com.gcs.suban.adapter;

import java.util.List;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.LogisticsBean;

 public class LogisticsAdapter extends BaseListAdapter<LogisticsBean> {


	public LogisticsAdapter(Context context, List<LogisticsBean> listItems) {
		super(context,listItems);
	}


	class ViewHolder {
		public TextView Tv_address;
		public TextView Tv_time;
		public ImageView Img_tick;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.list_item_logistics, null);
			holder.Tv_address = (TextView) convertView
					.findViewById(R.id.tv_address);
			holder.Tv_time = (TextView) convertView
					.findViewById(R.id.tv_time);
			holder.Img_tick = (ImageView) convertView
					.findViewById(R.id.img_tick);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			convertView.setTag(holder);
		}
		holder.Tv_address.setText(listItems.get(position).address);
		holder.Tv_time.setText(listItems.get(position).time);	
		
		if(position==0)
		{
			holder.Tv_address.setTextColor(Color.BLACK);
			holder.Tv_time.setTextColor(Color.BLACK);
			holder.Img_tick.setBackgroundResource(R.drawable.icon_tick_light);
		}
		else 
		{
			holder.Tv_address.setTextColor(Color.GRAY);
			holder.Tv_time.setTextColor(Color.GRAY);
			holder.Img_tick.setBackgroundResource(R.drawable.icon_tick);
		}
		return convertView;
	}
}

