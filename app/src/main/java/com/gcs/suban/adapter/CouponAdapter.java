package com.gcs.suban.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.CouponBean;
import com.gcs.suban.bean.ShopDataBean;

public class CouponAdapter  extends BaseListAdapter<CouponBean> {
	public List<ShopDataBean> lists = new ArrayList<ShopDataBean>();
	
	class ViewHolder { // �Զ���ؼ�����
		public TextView Tv_name;
		public TextView Tv_price;
		public TextView Tv_time;
	}

	public CouponAdapter(Context context, List<CouponBean> listItems) {
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
			convertView = mInflater.inflate(R.layout.list_item_coupon, null);
			// ��ȡ�ؼ�����
			holder.Tv_name = (TextView) convertView.findViewById(R.id.tv_couponname);
			holder.Tv_price = (TextView) convertView
					.findViewById(R.id.tv_deduct);
			holder.Tv_time = (TextView) convertView
					.findViewById(R.id.tv_time);
			// ���ÿؼ�����convertView
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			convertView.setTag(holder);
		}

		// ��ȡ��ַid���Ƿ�ΪĬ�ϵ�ַ
		holder.Tv_name.setText(listItems.get(position).couponname);
		holder.Tv_price.setText(listItems.get(position).deduct);
		holder.Tv_time.setText("��Ч����"
				+ listItems.get(position).useday);
		return convertView;
	}
}
