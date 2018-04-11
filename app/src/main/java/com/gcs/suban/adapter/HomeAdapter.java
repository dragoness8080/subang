package com.gcs.suban.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.ShopDataBean3;

import java.util.List;

public class HomeAdapter extends BaseListAdapter<ShopDataBean3> {
	private String id;
	private String pic;

	class ViewHolder { // �Զ���ؼ�����
//		public TextView Tv_name;
		public ImageView Img_pic;
	}

	public HomeAdapter(Context context, List<ShopDataBean3> listItems) {
		super(context, listItems);
		InitImageLoader();
	}

	/**
	 * ListView Item����
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.i("method", "getView" + position);

		// �Զ�����ͼ
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			// ��ȡlist_item�����ļ�����ͼ
			convertView = mInflater.inflate(R.layout.list_item_home, null);
/*			// ��ȡ�ؼ�����
			holder.Tv_name = (TextView) convertView.findViewById(R.id.tv_name);*/
			holder.Img_pic = (ImageView) convertView.findViewById(R.id.img_pic);
			// ���ÿؼ�����convertView
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			convertView.setTag(holder);
		}

		// ��ȡ��ַid���Ƿ�ΪĬ�ϵ�ַ
//		holder.Tv_name.setText(listItems.get(position).title);
		id = listItems.get(position).getGid();
		pic = listItems.get(position).getImgurl();

		imageLoader.displayImage(pic, holder.Img_pic, options);
		convertView.setTag(R.id.tag_first, id);
		return convertView;
	}
}
