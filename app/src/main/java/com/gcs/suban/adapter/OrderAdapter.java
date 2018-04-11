package com.gcs.suban.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gcs.suban.OrderHelper;
import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.OrderBean;
import com.gcs.suban.bean.ShopDataBean;

public class OrderAdapter extends BaseListAdapter<OrderBean> {
	
	private ConfirmAdapter adapter;
	
	private List<ShopDataBean> mListType;

	private OrderHelper helper;
	

	class ViewHolder { // 自定义控件集合
		public TextView Tv_ordersn;
		public TextView Tv_status;
		public TextView Tv_dispatchprice;
		public TextView Tv_payprice;
		public RelativeLayout Rlyt_coupon;
		public TextView Tv_coupon;
		public Button Btn_Left;
		public Button Btn_Right;
		public ListView listView;
	}

	public OrderAdapter(Context context, List<OrderBean> listItems) {
		super(context, listItems);
		InitImageLoader();
		helper = new OrderHelper(context);
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
			convertView = mInflater.inflate(R.layout.list_item_order, null);
			// 获取控件对象
			holder.Tv_ordersn = (TextView) convertView
					.findViewById(R.id.tv_ordersn);
			holder.Tv_status = (TextView) convertView
					.findViewById(R.id.tv_status);
			holder.Tv_payprice = (TextView) convertView
					.findViewById(R.id.tv_payprice);
			holder.Tv_dispatchprice = (TextView) convertView
					.findViewById(R.id.tv_dispatchprice);
			holder.Tv_coupon = (TextView) convertView
					.findViewById(R.id.tv_coupon);
			holder.Rlyt_coupon = (RelativeLayout) convertView
					.findViewById(R.id.rlyt_coupon);
			holder.Btn_Left = (Button) convertView.findViewById(R.id.btn_left);
			holder.Btn_Right = (Button) convertView
					.findViewById(R.id.btn_right);
			holder.listView = (ListView) convertView
					.findViewById(R.id.listView_children);
			// 设置控件集到convertView
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			convertView.setTag(holder);
		}

		mListType = new ArrayList<ShopDataBean>();
		JSONArray jsonArray;
		String goodsid="";
		try {
			jsonArray = new JSONArray(listItems.get(position).goods);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObjectSon = (JSONObject) jsonArray.opt(i);
				ShopDataBean bean = new ShopDataBean();
				bean.thumb = jsonObjectSon.getString("thumb");
				bean.title = jsonObjectSon.getString("title");
				bean.goodsid = jsonObjectSon.getString("goodsid");
				bean.marketprice = jsonObjectSon.getString("marketprice");
				bean.total = jsonObjectSon.getInt("total");
				mListType.add(bean);
				goodsid=goodsid+bean.goodsid+",";
				Log.e("goodsid", goodsid);
			}
			goodsid = goodsid.substring(0, goodsid.length() - 1);
			listItems.get(position).goodsid=goodsid;
			adapter = new ConfirmAdapter(context, mListType);
			holder.listView.setAdapter(adapter);
			setListViewHeightBasedOnChildren(holder.listView);
			holder.listView.setFocusable(false);
			holder.listView.setClickable(false);
			holder.listView.setEnabled(false);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		holder.Tv_ordersn.setText(listItems.get(position).ordersn);
		holder.Tv_status.setText(listItems.get(position).statusname);
		holder.Tv_payprice.setText("￥" + listItems.get(position).price);
		holder.Tv_coupon.setText("￥" + listItems.get(position).couponprice);
		if(listItems.get(position).couponprice.equals("0"))
		{
			holder.Rlyt_coupon.setVisibility(View.GONE);
		}
		else {
			holder.Rlyt_coupon.setVisibility(View.VISIBLE);
		}
		holder.Tv_dispatchprice.setText("￥"
				+ listItems.get(position).dispatchprice);

		holder.Btn_Right.setTag(position);
		holder.Btn_Left.setTag(position);
		
		helper.setButton(listItems.get(position), holder.Btn_Left, holder.Btn_Right);

		holder.Btn_Right.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int i = (int) v.getTag();
				OrderBean bean = listItems.get(i);
				helper.rightClick(bean);
			}
		});

		holder.Btn_Left.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int i = (int) v.getTag();
				OrderBean bean = listItems.get(i);
				helper.leftClick(bean);
			}
		});

		return convertView;
	}

	/* 子ListView高度设置 */
	public void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ConfirmAdapter listAdapter = (ConfirmAdapter) listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			// listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			// 计算子项View 的宽高
			listItem.measure(0, 0);
			// 统计所有子项的总高度
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}

}