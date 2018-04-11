package com.gcs.suban.adapter;

import io.rong.imkit.RongIM;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.TeamBean;

public class CustomerAdapter extends BaseListAdapter<TeamBean> {

	class ViewHolder { // 自定义控件集合
		public TextView Tv_name;
		public TextView Tv_time;
		public ImageView Img_logo;
		public ImageView Img_notice;
	}

	public CustomerAdapter(Context context, List<TeamBean> listItems) {
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
			convertView = mInflater.inflate(R.layout.list_item_customer, null);
			// 获取控件对象
			holder.Tv_name = (TextView) convertView
					.findViewById(R.id.tv_nickname);
			holder.Tv_time = (TextView) convertView
					.findViewById(R.id.tv_time);
			holder.Img_logo = (ImageView) convertView
					.findViewById(R.id.img_logo);
			holder.Img_notice= (ImageView) convertView
					.findViewById(R.id.img_notice);
			// 设置控件集到convertView
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			convertView.setTag(holder);
		}

		// 获取地址id和是否为默认地址
		holder.Tv_name.setText(listItems.get(position).nickname);
		holder.Tv_time.setText(listItems.get(position).createtime);
		imageLoader.displayImage(listItems.get(position).avatar, holder.Img_logo, options3);
		
		holder.Img_notice.setTag(position);
		holder.Img_notice.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int position=(int) v.getTag();
				
				if(RongIM.getInstance() != null)
				{
				RongIM.getInstance().startPrivateChat(context,listItems.get(position).id, "会员聊天");
				}
			}
		});

		return convertView;
	}
}

