package com.gcs.suban.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.CollectBean;
import com.gcs.suban.eventbus.CollectEvent;

import java.util.HashMap;
import java.util.List;

import io.rong.eventbus.EventBus;

public class CollectAdapter extends BaseListAdapter<CollectBean> {
	private String pic;
	private static HashMap<Integer, Boolean> isSelected;

	public CollectAdapter(Context context, List<CollectBean> list) {
		super(context, list);
		isSelected = new HashMap<Integer, Boolean>();
		InitImageLoader();
		initDate();
	}

	// 初始化isSelected的数据
	public void initDate() {
		Log.e("CollectAdapter", listItems.size() + "");
		for (int i = 0; i < listItems.size(); i++) {
			getIsSelected().put(i, false);
		}
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		CollectAdapter.isSelected = isSelected;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CollectBean bean = listItems.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item_collect, null);
			holder = new ViewHolder();
			holder.Img_pic = (ImageView) convertView.findViewById(R.id.img_pic);
			holder.Tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.Tv_price = (TextView) convertView
					.findViewById(R.id.tv_price);
			holder.Tv_price2 = (TextView) convertView
					.findViewById(R.id.tv_price2);
			holder.shop_check = (CheckBox) convertView
					.findViewById(R.id.shop_check);
			holder.Ibtn_cancel = (ImageButton) convertView
					.findViewById(R.id.ibtn_delete);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		pic = bean.getShopPicture();
		imageLoader.displayImage(pic, holder.Img_pic, options);

		holder.Tv_name.setText(bean.getShopName());
		holder.Tv_price.setText("￥" + bean.getShopPrice());
		holder.Tv_price2.setText("￥" + bean.getShopPrice2());
		holder.Tv_price2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		holder.shop_check.setTag(position);
		holder.shop_check.setChecked(getIsSelected().get(position));
		holder.shop_check
				.setOnCheckedChangeListener(new CheckBoxChangedListener());
		holder.Ibtn_cancel.setTag(bean.getId());
		holder.Ibtn_cancel.setOnClickListener(new CancelListener());
		convertView.setTag(R.id.tag_first, bean.getShopId() + "");
		return convertView;
	}

	private final class ViewHolder {
		public ImageView Img_pic; // 商品图片
		public TextView Tv_name; // 商品名称
		public TextView Tv_price; // 商品价格
		public TextView Tv_price2; // 商品数量
		public CheckBox shop_check; // 商品选择按钮
		public ImageButton Ibtn_cancel; // 取消收藏按钮
	}

	// CheckBox选择改变监听器
	private final class CheckBoxChangedListener implements
			OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(CompoundButton cb, boolean flag) {
			int position = (Integer) cb.getTag();
			getIsSelected().put(position, flag);
		}
	}

	private final class CancelListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			EventBus.getDefault().post(new CollectEvent("delete",v.getTag().toString()));
		}
	}

}
