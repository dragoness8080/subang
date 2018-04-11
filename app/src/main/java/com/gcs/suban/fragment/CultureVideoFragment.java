package com.gcs.suban.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.activity.CultureActivity;
import com.gcs.suban.adapter.CultureDownAdapter;
import com.gcs.suban.adapter.HlvSimpleAdapter;
import com.gcs.suban.base.BaseFragment;
import com.gcs.suban.bean.HlvSimpleBean;
import com.gcs.suban.listener.OnVideoListener;
import com.gcs.suban.model.VideoModel;
import com.gcs.suban.model.VideoModelImpl;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.view.HorizontalListView;
import com.gcs.suban.view.LoadListView;
import com.gcs.suban.view.LoadListView.onLoadListViewListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CultureVideoFragment extends BaseFragment implements
		OnVideoListener, onLoadListViewListener {

	private HorizontalListView mHlvSimpleList;
	private List<HlvSimpleBean> hList = new ArrayList<HlvSimpleBean>();
	private HlvSimpleAdapter hAdapter;

	private LoadListView listView;
	private List<HlvSimpleBean> vList = new ArrayList<HlvSimpleBean>();
	private CultureDownAdapter vAdapter;
	// HlvSimpleBean bean = new HlvSimpleBean();;

	private String page = "0";
	private String type = null;

	private Boolean isRefresh = false;

	private VideoModel mVideoModel;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_video, null);
		return view;
	}

	@Override
	protected void init() {
		mVideoModel = new VideoModelImpl();
		// TODO Auto-generated method stub
		mHlvSimpleList = (HorizontalListView) context
				.findViewById(R.id.video_hlvSimpleList);
		mHlvSimpleList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				type = hList.get(position).id;
				onReset(position);
				RefreshListView();
			}
		});
		mVideoModel.getSort(Url.artcilecolum + "?type=2", this);

		listView = (LoadListView) context.findViewById(R.id.video_list);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, CultureActivity.class);
				intent.putExtra("article_id", vList.get(position).article_id);
				intent.putExtra("title", vList.get(position).title);
				intent.putExtra("content", vList.get(position).createtime);
				intent.putExtra("shareurl", vList.get(position).shareurl);
				intent.putExtra("pic", vList.get(position).thumb);
				context.startActivity(intent);
			}
		});
		listView.setOnLoadListViewListener(this);
		vAdapter = new CultureDownAdapter(context, vList);
		listView.setAdapter(vAdapter);
	}

	public void onReset(int i) {
		HashMap<Integer, Boolean> isSelected = new HashMap<Integer, Boolean>();
		for (int j = 0; j < hList.size(); j++) {
			isSelected.put(j, false);
		}
		isSelected.put(i, true);
		hAdapter.setIsSelected(isSelected);
		hAdapter.notifyDataSetChanged();
	}

	/**
	 * 刷新
	 */
	private void RefreshListView() {
		isRefresh = true;
		page = "0";
		String url = Url.articlelist + "?type=" + type + "&page=" + page;
		mVideoModel.getListInfo(url, this);
	}

	/**
	 * 加载
	 */
	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		String url = Url.articlelist + "?type=" + type + "&page=" + page;
		mVideoModel.getListInfo(url, this);
	}

	/**
	 * 更新适配器
	 */
	private void setData(List<HlvSimpleBean> ListType) {
		if (isRefresh == true) {
			isRefresh = false;
			vAdapter.clear();
			vList.clear();
		}

		listView.loadComplete();

		vList.addAll(ListType);
		vAdapter.notifyDataSetChanged();
	}

	@Override
	public void onSuccess(List<HlvSimpleBean> ListType, String page) {
		// TODO Auto-generated method stub
		this.page = page;
		if (ListType == null) {
			// 上拉加载全部完成
			if (isRefresh != true) {
				listView.setComplete(true);
				listView.loadComplete();
				return;
			}
			// 列表为空
			else {
				vAdapter.clear();
				vList.clear();
				vAdapter.notifyDataSetChanged();
			}
		} else {
			listView.setComplete(false);
			setData(ListType);
		}
	}

	@Override
	public void onSetSort(List<HlvSimpleBean> mListType) {
		// TODO Auto-generated method stub
		hList = mListType;
		hAdapter = new HlvSimpleAdapter(context, hList);
		mHlvSimpleList.setAdapter(hAdapter);
		type = hList.get(0).id;
		RefreshListView();
	}

	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, error);
		listView.loadComplete();
	}

}
