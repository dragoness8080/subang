package com.gcs.suban.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.OrderBean;
import com.gcs.suban.bean.ShopDataBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TeamOrderAdapter extends BaseListAdapter<OrderBean>{
	public List<OrderBean> lists = new ArrayList<OrderBean>();
	public List<ShopDataBean> mListType;

	public TeamOrderGoodsAdapter adapter;

	class ViewHolder { // �Զ���ؼ�����
		public TextView Tv_ordersn;
		public TextView Tv_time;
		public TextView Tv_status;
		public TextView Tv_price;
		public TextView Tv_nickname;
		public ImageView Img_vip;
		public RelativeLayout v1;
		public RelativeLayout v2;
		public TextView Tv_nickname1;
		public ImageView Img_vip1;
		public ListView goods_list;
	}

	public TeamOrderAdapter(Context context, List<OrderBean> listItems) {
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
			convertView = mInflater.inflate(R.layout.list_item_teamorder, null);
			// ��ȡ�ؼ�����
			holder.Tv_nickname = (TextView) convertView
					.findViewById(R.id.tv_nickname);
			holder.Tv_ordersn = (TextView) convertView
					.findViewById(R.id.tv_ordersn);
			holder.Tv_time= (TextView) convertView
					.findViewById(R.id.tv_time);
			holder.Tv_status = (TextView) convertView
					.findViewById(R.id.tv_status);
			holder.Img_vip = (ImageView) convertView
					.findViewById(R.id.img_vip);
			holder.v1 = (RelativeLayout) convertView.findViewById(R.id.v1);
			holder.v2 = (RelativeLayout) convertView.findViewById(R.id.v2);
			holder.Tv_nickname1 = (TextView)convertView.findViewById(R.id.tv_nickname1);
			holder.Img_vip1 = (ImageView) convertView.findViewById(R.id.img_vip1);

			holder.goods_list = (ListView) convertView.findViewById(R.id.goods_list);
			// ���ÿؼ�����convertView
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			convertView.setTag(holder);
		}

		holder.Tv_nickname.setText(listItems.get(position).agentname);
		holder.Tv_nickname1.setText(listItems.get(position).nickname);
		holder.Tv_time.setText(listItems.get(position).createtime);
		holder.Tv_ordersn.setText(listItems.get(position).ordersn);
		holder.Tv_status.setText(listItems.get(position).statusname);

		String agentlevel = listItems.get(position).agentlevel;
		if(agentlevel.equals("0")){
			holder.v1.setVisibility(View.GONE);
		}else if(agentlevel.equals("1")){
			holder.Img_vip.setBackgroundResource(R.drawable.icon_vip1);
		}else if(agentlevel.equals("2")){
			holder.Img_vip.setBackgroundResource(R.drawable.icon_vip2);
		}

		String level=listItems.get(position).level;
		if(level.equals("1"))
		{
			holder.Img_vip1.setBackgroundResource(R.drawable.icon_vip1);
		}
		else if(level.equals("2"))
		{
			holder.Img_vip1.setBackgroundResource(R.drawable.icon_vip2);
		}
		else {
			holder.Img_vip1.setBackgroundResource(R.drawable.icon_vip3);
		}

		int status = listItems.get(position).status;

		mListType = new ArrayList<ShopDataBean>();
		JSONArray jsonArray;
		try{
			jsonArray = new JSONArray(listItems.get(position).goods);
			for (int i = 0; i < jsonArray.length(); i++){
				JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
				ShopDataBean dataBean = new ShopDataBean();
				dataBean.marketprice = jsonObject.getString("price");
				dataBean.thumb = jsonObject.getString("thumb");
				dataBean.title = jsonObject.getString("title");
				dataBean.commission = (float)jsonObject.getDouble("commission");
				dataBean.total = jsonObject.getInt("total");
				dataBean.status = status;
				mListType.add(dataBean);
			}
			adapter = new TeamOrderGoodsAdapter(context,mListType);
			holder.goods_list.setAdapter(adapter);
			setListViewHeightBasedOnChildren(holder.goods_list);

		}catch (JSONException e) {
			e.printStackTrace();
		}

		return convertView;
	}

	/* ��ListView�߶����� */
	public void setListViewHeightBasedOnChildren(ListView listView) {
		// ��ȡListView��Ӧ��Adapter
		ConfirmAdapter listAdapter = (ConfirmAdapter) listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			// listAdapter.getCount()�������������Ŀ
			View listItem = listAdapter.getView(i, null, listView);
			// ��������View �Ŀ��
			listItem.measure(0, 0);
			// ͳ������������ܸ߶�
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()��ȡ�����ָ���ռ�õĸ߶�
		// params.height���õ�����ListView������ʾ��Ҫ�ĸ߶�
		listView.setLayoutParams(params);
	}

}
