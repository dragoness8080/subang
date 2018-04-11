package com.gcs.suban.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.gcs.suban.MyDate;
import com.gcs.suban.Url;
import com.gcs.suban.app;
import com.gcs.suban.bean.ShopDataBean;
import com.gcs.suban.bean.ShopDetailBean;
import com.gcs.suban.listener.OnShopDetailListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;
import com.google.gson.Gson;

public class ShopDetailImpl implements ShopDetailModel {

	private Context context = app.getContext();
	private ShopDetailBean bean;
	private Gson gson;
	protected List<ShopDataBean> mListType = new ArrayList<ShopDataBean>();

	@Override
	public void getInfo(String url, String goodsid,
			final OnShopDetailListener listener) {
		final String TAG = url;
		// TODO Auto-generated method stub
		Map<String, String> params = new HashMap<String, String>();
		params.put("goodsid", goodsid);
		params.put("openid", MyDate.getMyVid());
		BaseVolleyRequest.StringRequestPost(context, url, TAG, params,
				new BaseStrVolleyInterFace(context,
						BaseStrVolleyInterFace.mListener,
						BaseStrVolleyInterFace.mErrorListener) {
					@Override
					public void onSuccess(String response) {
						Log.i(TAG, "GET请求成功-->" + response);
						try {
							JSONObject jsonObject = new JSONObject(response);
							String result = jsonObject.getString("result");
							if (result.equals("1001")) {
								gson = new Gson();
								bean = gson.fromJson(jsonObject.toString(),
										ShopDetailBean.class);
								listener.onSuccess(bean);
							} else {
								String resulttext = jsonObject
										.getString("resulttext");
								listener.onError(resulttext);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							listener.onError(Url.jsonError);
						}
					}

					@Override
					public void onError(VolleyError volleyError) {
						Log.i(TAG, "GET请求失败-->" + volleyError.toString());
						listener.onError(Url.networkError);
					}
				});
	}

	@Override
	public void collect(String url, String goodsid,
			final OnShopDetailListener listener) {
		final String TAG = url;
		// TODO Auto-generated method stub
		Map<String, String> params = new HashMap<String, String>();
		params.put("goodsid", goodsid);
		params.put("openid", MyDate.getMyVid());
		BaseVolleyRequest.StringRequestPost(context, url, TAG, params,
				new BaseStrVolleyInterFace(context,
						BaseStrVolleyInterFace.mListener,
						BaseStrVolleyInterFace.mErrorListener) {
					@Override
					public void onSuccess(String response) {
						Log.i(TAG, "GET请求成功-->" + response);
						try {
							JSONObject jsonObject = new JSONObject(response);
							String result = jsonObject.getString("result");
							if (result.equals("1001")) {
								listener.onCollect();
							} else {
								String resulttext = jsonObject
										.getString("resulttext");
								listener.onError(resulttext);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							listener.onError(Url.jsonError);
						}
					}

					@Override
					public void onError(VolleyError volleyError) {
						Log.i(TAG, "GET请求失败-->" + volleyError.toString());
						listener.onError(Url.networkError);
					}
				});
	}

	@Override
	public void cancle(String url, String goodsid, final OnShopDetailListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url;
		// TODO Auto-generated method stub
		Map<String, String> params = new HashMap<String, String>();
		params.put("goodsid", goodsid);
		params.put("openid", MyDate.getMyVid());
		BaseVolleyRequest.StringRequestPost(context, url, TAG, params,
				new BaseStrVolleyInterFace(context,
						BaseStrVolleyInterFace.mListener,
						BaseStrVolleyInterFace.mErrorListener) {
					@Override
					public void onSuccess(String response) {
						Log.i(TAG, "GET请求成功-->" + response);
						try {
							JSONObject jsonObject = new JSONObject(response);
							String result = jsonObject.getString("result");
							if (result.equals("1001")) {
								listener.onCancel();
							} else {
								String resulttext = jsonObject
										.getString("resulttext");
								listener.onError(resulttext);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							listener.onError(Url.jsonError);
						}
					}

					@Override
					public void onError(VolleyError volleyError) {
						Log.i(TAG, "GET请求失败-->" + volleyError.toString());
						listener.onError(Url.networkError);
					}
				});
	}

}
