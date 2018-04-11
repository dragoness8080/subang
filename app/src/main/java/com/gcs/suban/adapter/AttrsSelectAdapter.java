package com.gcs.suban.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gcs.suban.AttrCanst;
import com.gcs.suban.R;
import com.gcs.suban.bean.AttrsBean;
import com.gcs.suban.bean.AttrsChildbean;
import com.gcs.suban.view.SelectCheckBoxs;
import com.gcs.suban.view.SelectCheckBoxs.OnSelectListener;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AttrsSelectAdapter extends BaseAdapter {
	protected static final String TAG = "AttrsSelectAdapter";
	private List<AttrsBean> listItems; // 信息集合
	private LayoutInflater listContainer; // 视图容器
	private SelectCheckBoxs check;
	private TextView Tv_attr;
	private int num;
	private Map<Integer, String> mColorData;

	private Handler mHandler;
	
	private AttrsChildbean bean;

	public AttrsSelectAdapter(Context context, List<AttrsBean> listItems,
			Handler mHandler) {
		listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = listItems;
		this.mHandler = mHandler;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/**
	 * ListView Item设置
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.i("method", "getView" + position);
		convertView = listContainer.inflate(R.layout.list_item_attr, null);

		check = (SelectCheckBoxs) convertView.findViewById(R.id.checkbox);
		check.setOnSelectListener(new OnSSChkClickEvent());
		num = listItems.get(position).num;
		mColorData = new HashMap<Integer, String>();
		for (int j = 0; j < num; j++) {
			Log.e(TAG, AttrCanst.attrs[position][j].title);
			// 控件需要的数据类型
			mColorData.put(j, AttrCanst.attrs[position][j].title);
		}
		check.setData(position, mColorData);
		Tv_attr = (TextView) convertView.findViewById(R.id.tv_attr);
		String title = listItems.get(position).title;
		Tv_attr.setText(title);

		return convertView;

	}

	class OnSSChkClickEvent implements OnSelectListener {
		// position 表示
		@Override
		public void onSelect(int i, int j, int p) { // i 代表所在listview的行数 j代表
													// 在当前行中的第几个
			String title = AttrCanst.attrs[i][j].title;
			String thumb = AttrCanst.attrs[i][j].thumb;
			String speciid = AttrCanst.attrs[i][j].speciid;
			String sort = AttrCanst.attrs[i][j].sort;
			String specid = AttrCanst.attrs[i][j].specid;
			Log.i(TAG, title + " " + thumb + " " + speciid + " " + sort
					+ " " + specid);
			if (p != -1) {
				for (int x = 0; x < listItems.get(i).num; x++) {
					AttrCanst.attrs[i][x].is = false;
				}
				AttrCanst.attrs[i][j].is = true;
			} else {
				AttrCanst.attrs[i][j].is = false;
				title="";
			}
			Message msg=Message.obtain();
			msg.what=i;
			bean=new AttrsChildbean();
			bean.speciid=specid;
			bean.title=title;
			bean.thumb=thumb;
			bean.sort=sort;
			bean.speciid=specid;
			bean.speciid=specid;
			bean.is=AttrCanst.attrs[i][j].is;
			
			msg.obj=bean;
			mHandler.sendMessage(msg);
			
		}
	}
}
