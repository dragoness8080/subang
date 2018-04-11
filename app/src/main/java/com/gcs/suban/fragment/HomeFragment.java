package com.gcs.suban.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.activity.ShopDetailActivity;
import com.gcs.suban.adapter.HomeAdapter;
import com.gcs.suban.base.BaseFragment;
import com.gcs.suban.bean.ShopDataBean3;
import com.gcs.suban.listener.OnShopListener;
import com.gcs.suban.model.ShopModel;
import com.gcs.suban.model.ShopModelImpl;
import com.gcs.suban.tools.ACache;
import com.gcs.suban.tools.GlideImageLoader;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.view.MyScrollview;
import com.gcs.suban.view.NoSlideGridView;
import com.gcs.suban.view.VpSwipeRefreshLayout;
import com.google.gson.Gson;
import com.meiqia.meiqiasdk.util.MQIntentBuilder;
import com.youth.banner.Banner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * ???
 */
public class HomeFragment extends BaseFragment implements OnItemClickListener,
		OnShopListener, OnRefreshListener {
	private String TAG = "HomeFragment";

	private NoSlideGridView listView;

	private HomeAdapter adapter;

	private List<ShopDataBean3> mListType = new ArrayList<>();

	private ShopModel homeModel;

	private String url;

	private ACache mCache;

	private ShopDataBean3 bean;
	private Gson gson;
	private MyScrollview scrollView;
	private ImageView mMoveTop;
	private ArrayList<String> bannerUrlList = new ArrayList<>();
	private Banner banner;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.move_top:
				scrollView.smoothScrollTo(0,0);
				mMoveTop.setVisibility(View.GONE);
				break;
			case R.id.call_service:
				Intent intent = new MQIntentBuilder(getActivity()).build();
				startActivity(intent);
				break;
		}
	}

	/* ????? */
	@Override
	protected void init() {
		mCache = ACache.get(context);
		// TODO Auto-generated method stub
		listView = (NoSlideGridView) context.findViewById(R.id.list_home);
		listView.setLayoutAnimation(controller);
		listView.setOnItemClickListener(this);
		banner = (Banner)context.findViewById(R.id.banner);
		adapter = new HomeAdapter(context, mListType);
		listView.setAdapter(adapter);
		scrollView = (MyScrollview) context.findViewById(R.id.scrollView1);
		mMoveTop = (ImageView) context.findViewById(R.id.move_top);
		mMoveTop.setVisibility(View.GONE);
		mMoveTop.setOnClickListener(this);
		swipeRefreshLayout = (VpSwipeRefreshLayout) context
				.findViewById(R.id.swipeRefreshLayout_home);
		swipeRefreshLayout.setOnRefreshListener(this);
		InitSwipeRefreshLayout();

		homeModel = new ShopModelImpl();
		if (mCache.getAsString(TAG) != null) {
			JsonResolve(mCache.getAsString(TAG));
			Log.i(TAG, "???????????  ---- " + mCache.getAsString(TAG));
		}
		context.findViewById(R.id.call_service).setOnClickListener(this);
		swipeRefreshLayout.post(new Runnable() {

			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(true);
			}
		});
		this.onRefresh();
		scrollView.setOnScrollToLongListener(new MyScrollview.OnScrollToLongListener() {
			@Override
			public void onScrolToLong(int distance) {
				if (distance > 0) {
					swipeRefreshLayout.setEnabled(false);
				} else {
					swipeRefreshLayout.setEnabled(true);
				}
				if (distance > 500) {
					mMoveTop.setVisibility(View.VISIBLE);
				} else {
					mMoveTop.setVisibility(View.GONE);
				}
			}
		});
	}

	/* ???url??? */
	private String getUrl() {
		url = Url.home3 + "?page=0";
		return url;
	}

	/* ?????????? */
	private void setData(List<ShopDataBean3> list) {
		mListType.clear();
		mListType.addAll(list);
		adapter.notifyDataSetChanged();
	}

	/**
	 * ???????? ?????????
	 */
	@Override
	public void onSuccess(String response) {
		// TODO Auto-generated method stub
		JsonResolve(response);
		mCache.put(TAG, response, 60 * 60 * 24 * 3);
		
		swipeRefreshLayout.post(new Runnable() {

			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(false);
			}
		});
	}

	/**
	 * ???????? ??????????
	 */
	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, error);
		
		swipeRefreshLayout.post(new Runnable() {

			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(false);
			}
		});
	}

	/**
	 * ???????
	 */
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onRefresh");
		homeModel.getInfo(getUrl(), this);
	}

	/**
	 * ?��???
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		String goodsid = (String) view.getTag(R.id.tag_first);
		Intent intent = new Intent(context, ShopDetailActivity.class);
		intent.putExtra("goodsid", goodsid);
		startActivity(intent);
	}

	public void JsonResolve(String response) {
		// TODO Auto-generated method stub
		List<ShopDataBean3> list = new ArrayList<>();
		try {
			JSONObject jsonObject = new JSONObject(response);
			String isnull = jsonObject.getString("isnull");
			if (isnull.equals("0")) {
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				JSONArray bannerList = jsonObject.getJSONArray("bannerlist");

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObjectSon = (JSONObject) jsonArray.opt(i);
					gson = new Gson();
					bean = gson.fromJson(jsonObjectSon.toString(),
							ShopDataBean3.class);
					list.add(bean);
				}
				for (int i = 0; i < bannerList.length(); i++) {
					String url = bannerList.getString(i);
					bannerUrlList.add(url);
				}
			} else if (isnull.equals("1")) {
				list = null;
			}
			//������
			banner.setImages(bannerUrlList)
					.setImageLoader(new GlideImageLoader())
					.start();
			setData(list);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
