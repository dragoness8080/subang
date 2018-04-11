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

	class ViewHolder { // �Զ���ؼ�����
		public TextView Tv_select;
	}

	public HlvSimpleAdapter(Context context, List<HlvSimpleBean> listItems) {
		super(context, listItems);
		InitImageLoader();
		isSelected = new HashMap<Integer, Boolean>();
		initDate();
	}

	// ��ʼ��isSelected������
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
	 * ListView Item����
	 */
	@Override
	@SuppressWarnings("deprecation")
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.i("method", "getView" + position);

		// �Զ�����ͼ
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			// ��ȡlist_item�����ļ�����ͼ
			convertView = mInflater.inflate(R.layout.list_item_hlvsimple, null);
			// ��ȡ�ؼ�����
			holder.Tv_select = (TextView) convertView
					.findViewById(R.id.tv_title);
			// ���ÿؼ�����convertView
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			convertView.setTag(holder);
		}

		// ��ȡ��ַid���Ƿ�ΪĬ�ϵ�ַ
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
