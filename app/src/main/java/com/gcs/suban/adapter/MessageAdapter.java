package com.gcs.suban.adapter;

import java.util.List;
import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.MessageBean;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MessageAdapter extends BaseListAdapter<MessageBean> {

	public MessageAdapter(Context context, List<MessageBean> listItems) {
		super(context, listItems);
	}

	class ViewHolder {
		public TextView Tv_ctime;
		public TextView Tv_title;
		public TextView Tv_content;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		MessageBean bean = listItems.get(position);

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.list_item_message, null);
			holder.Tv_ctime = (TextView) convertView.findViewById(R.id.tv_time);
			holder.Tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			holder.Tv_content = (TextView) convertView
					.findViewById(R.id.tv_content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.Tv_ctime.setText(bean.createtime);
		holder.Tv_title.setText(bean.title);
		holder.Tv_content.setText(bean.content);

		return convertView;
	}

}
