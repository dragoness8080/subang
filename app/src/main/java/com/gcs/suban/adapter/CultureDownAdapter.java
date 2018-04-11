package com.gcs.suban.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.HlvSimpleBean;

import java.util.List;

public class CultureDownAdapter extends  BaseListAdapter<HlvSimpleBean>{

	class ViewHolder { // ???????????
		public TextView Tv_title;
		public TextView Tv_createtime;
		public ImageView pic;
		public TextView Tv_des;
	}

	public CultureDownAdapter(Context context, List<HlvSimpleBean> listItems) {
		super(context, listItems);
		InitImageLoader();
	}

	/**
	 * ListView Item????
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.i("method", "getView" + position);


		ViewHolder holder = null;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.list_item_down, null);
			// ??????????
			holder.Tv_title = (TextView) convertView
					.findViewById(R.id.tv_title);
			holder.Tv_createtime=(TextView)convertView
					.findViewById(R.id.tv_time);
			holder.Tv_des = (TextView)convertView.findViewById(R.id.des);
			holder.pic=(ImageView)convertView
					.findViewById(R.id.pic);
			// ??????????convertView
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			convertView.setTag(holder);
		}

		
		holder.Tv_title.setText(listItems.get(position).title);
		holder.Tv_createtime.setText(listItems.get(position).createtime);
		holder.Tv_des.setText(listItems.get(position).des);
		imageLoader.displayImage(listItems.get(position).thumb, holder.pic,options);

		return convertView;
	}

}
