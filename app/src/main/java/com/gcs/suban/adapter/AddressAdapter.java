package com.gcs.suban.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.activity.AddressEditActivity;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.AddressBean;
import com.gcs.suban.eventbus.AddressEvent;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

public class AddressAdapter extends BaseListAdapter<AddressBean>{
	public List<AddressBean> lists = new ArrayList<AddressBean>();
	private String address;
	private AddressBean bean=new AddressBean("", "", "", "", "", "", "", "");
	
	public AddressAdapter(Context context, List<AddressBean> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	class ViewHolder { // 自定义控件集合
		public TextView name;
		public TextView tel;
		public TextView address;
		public TextView edit;
		public TextView Tv_delete;
		public CheckBox Ibtn_default_address;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.i("method", "getView" + position);
		// 自定义视图
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			// 获取list_item布局文件的视图
			convertView = mInflater.inflate(R.layout.list_item_address, null);
			// 获取控件对象
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.tel = (TextView) convertView.findViewById(R.id.tel);
			holder.address = (TextView) convertView.findViewById(R.id.address);
			holder.edit = (TextView) convertView.findViewById(R.id.edit);
			holder.Tv_delete = (TextView) convertView.findViewById(R.id.delete);
			holder.Ibtn_default_address = (CheckBox) convertView
					.findViewById(R.id.default_address);
			// 设置控件集到convertView
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			convertView.setTag(holder);
		}
		bean.addressid=listItems.get(position).addressid;
		bean.realname=listItems.get(position).realname;
		bean.mobile=listItems.get(position).mobile;
		bean.province=listItems.get(position).province;
		bean.city=listItems.get(position).city;
		bean.area=listItems.get(position).area;
		bean.address=listItems.get(position).address;
		bean.isdefault=listItems.get(position).isdefault;
		
		Bundle mBundle = new Bundle(); 
		mBundle.putString("addressid", bean.addressid);
		mBundle.putString("mobile", bean.mobile);
		mBundle.putString("realname", bean.realname);
		mBundle.putString("address", bean.address);
		mBundle.putString("province", bean.province);
		mBundle.putString("city", bean.city);
		mBundle.putString("area", bean.area);
		
		address=bean.province+" "+bean.city+" "+bean.area+" "+bean.address;

		holder.name.setText(bean.realname);
		holder.tel.setText(bean.mobile);
		holder.address.setText(address);
		
		holder.Tv_delete.setTag(bean.addressid);
		holder.Tv_delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 获取按钮标签上的的地址id
				String id = v.getTag().toString();
				EventBus.getDefault().post(
						new AddressEvent("delete",id));
			}
		});
		
		holder.edit.setTag(bean.addressid);
		holder.edit.setTag(R.id.tag_first, mBundle);
		holder.edit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle=(Bundle) v.getTag(R.id.tag_first);
				Intent intent = new Intent(context, AddressEditActivity.class);
				intent.putExtra("mBundle", bundle);
				context.startActivity(intent);
			}
		});
		
		if (bean.isdefault.equals("1")) {
			holder.Ibtn_default_address
					.setBackgroundResource(R.drawable.icon_all_select);
		} else {
			holder.Ibtn_default_address
					.setBackgroundResource(R.drawable.icon_all_unselect);
		}

		holder.Ibtn_default_address.setTag(bean.addressid);
		holder.Ibtn_default_address
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						String id = v.getTag().toString();
						EventBus.getDefault().post(
								new AddressEvent("default",id));
					}
				});

		return convertView;
	}
}
