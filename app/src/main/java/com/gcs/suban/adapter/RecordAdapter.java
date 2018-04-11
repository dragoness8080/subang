package com.gcs.suban.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.RecordBean;
/**
 * ?????? ??????
 */
public class RecordAdapter extends BaseListAdapter<RecordBean> {

	public RecordAdapter(Context context, List<RecordBean> list) {
		super(context, list);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		RecordBean bean = listItems.get(position);
		
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item_record, null);
			holder = new ViewHolder();
			holder.Tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			holder.Tv_money = (TextView) convertView
					.findViewById(R.id.tv_money);
			holder.Tv_createtime = (TextView) convertView
					.findViewById(R.id.tv_createtime);
			holder.Tv_statusname = (TextView) convertView
					.findViewById(R.id.tv_statusname);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.Tv_title.setText(bean.title);
		holder.Tv_money.setText("гд" + bean.money);
		holder.Tv_createtime.setText(bean.createtime);
		holder.Tv_statusname.setText("("+bean.statusname+")");

		return convertView;
	}

	private final class ViewHolder {
		public TextView Tv_title; 
		public TextView Tv_money; 
		public TextView Tv_createtime; 
		public TextView Tv_statusname; 
	}


}
