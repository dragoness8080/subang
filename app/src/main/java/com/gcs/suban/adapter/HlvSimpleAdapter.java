package com.gcs.suban.adapter;

import java.util.HashMap;
import java.util.List;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.HlvSimpleBean;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HlvSimpleAdapter extends BaseListAdapter<HlvSimpleBean> {
	private static HashMap<Integer, Boolean> isSelected;

	class ViewHolder { // 自定义控件集合
		public TextView Tv_select;
	}

	public HlvSimpleAdapter(Context context, List<HlvSimpleBean> listItems) {
		super(context, listItems);
		InitImageLoader();
		isSelected = new HashMap<Integer, Boolean>();
		initDate();
	}

	// 初始化isSelected的数据
	public void initDate() {
		Log.e("HlvSimpleAdapter", listItems.size() + "");
		for (int i = 0; i < 20; i++) {
			getIsSelected().put(i, false);
		}
		getIsSelected().put(0, true);
	}

	public HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		HlvSimpleAdapter.isSelected = isSelected;
	}

	/**
	 * ListView Item设置
	 */
	@Override
	@SuppressWarnings("deprecation")
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.i("method", "getView" + position);

		// 自定义视图
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			// 获取list_item布局文件的视图
			convertView = mInflater.inflate(R.layout.list_item_hlvsimple, null);
			// 获取控件对象
			holder.Tv_select = (TextView) convertView
					.findViewById(R.id.tv_title);
			// 设置控件集到convertView
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			convertView.setTag(holder);
		}

		// 获取地址id和是否为默认地址
		holder.Tv_select.setText(listItems.get(position).title);
		if (getIsSelected().get(position)) {
			holder.Tv_select.setTextColor(context.getResources().getColor(
					R.color.themeOrange));
			holder.Tv_select
					.setBackgroundResource(R.drawable.button_sort_select);
		} else {
			holder.Tv_select.setTextColor(context.getResources().getColor(
					R.color.black));
			holder.Tv_select
					.setBackgroundResource(R.drawable.button_sort_unselect);
		}

		return convertView;
	}

}
