package com.gcs.suban.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.gcs.suban.AttrCanst;
import com.gcs.suban.MyDate;
import com.gcs.suban.Url;
import com.gcs.suban.app;
import com.gcs.suban.bean.AttrsBean;
import com.gcs.suban.bean.AttrsChildbean;
import com.gcs.suban.bean.ShopDataBean;
import com.gcs.suban.listener.OnAttrListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;
import com.google.gson.Gson;

public class AttrModelImpl implements AttrModel {

	private Context context = app.getContext();
	private List<AttrsBean> mListType;
	private List<ShopDataBean> vListType;
	private AttrsBean bean;
	private Gson gson;

	@Override
	public void getInfo(String url, final OnAttrListener listener) {
		final String TAG = url;
		// TODO Auto-generated method stub
		BaseVolleyRequest.StringRequestGet(context, url, TAG,
				new BaseStrVolleyInterFace(context,
						BaseStrVolleyInterFace.mListener,
						BaseStrVolleyInterFace.mErrorListener) {
					@Override
					public void onSuccess(String response) {
						Log.i(TAG, "GET请求成功-->" + response);
						AttrCanst.attrs = new AttrsChildbean[10][10];
						for (int i = 0; i < 10; i++) {
							for (int j = 0; j < 10; j++) {
								AttrCanst.attrs[i][j] = new AttrsChildbean();
								AttrCanst.attrs[i][j].is = false;
							}
						}
						try {
							JSONObject jsonObject = new JSONObject(response);
							String result = jsonObject.getString("result");
							String resulttext = jsonObject
									.getString("resulttext");
							String isspec = jsonObject.getString("isspec");
							mListType = new ArrayList<AttrsBean>();
							if (result.equals("1001")) {
								gson = new Gson();
								ShopDataBean shopDataBean = gson.fromJson(
										jsonObject.toString(),
										ShopDataBean.class);
								if (isspec.equals("1")) {
									JSONArray jsonArray = jsonObject
											.getJSONArray("spec");
									for (int i = 0; i < jsonArray.length(); i++) {
										JSONObject jsonObjectSon = (JSONObject) jsonArray
												.opt(i);
										JSONArray jsonArraySon = jsonObjectSon
												.getJSONArray("speci");
										bean = new AttrsBean(jsonObjectSon
												.getString("specid"),
												jsonObjectSon
														.getString("title"),
												jsonArraySon.length());
										mListType.add(bean);
										for (int j = 0; j < jsonArraySon
												.length(); j++) {
											JSONObject jsonObjectSon2 = (JSONObject) jsonArraySon
													.opt(j);
											AttrCanst.attrs[i][j].speciid = jsonObjectSon2
													.getString("speciid");
											AttrCanst.attrs[i][j].title = jsonObjectSon2
													.getString("title");
											AttrCanst.attrs[i][j].thumb = jsonObjectSon2
													.getString("thumb");
											AttrCanst.attrs[i][j].sort = jsonObjectSon
													.getString("title");
											AttrCanst.attrs[i][j].specid = jsonObjectSon
													.getString("specid");
										}
									}
								} else {
									mListType = null;
								}
								listener.onSuccess(mListType, shopDataBean);
							} else {
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
	public void getValue(String url, final OnAttrListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url;
		// TODO Auto-generated method stub
		BaseVolleyRequest.StringRequestGet(context, url, TAG,
				new BaseStrVolleyInterFace(context,
						BaseStrVolleyInterFace.mListener,
						BaseStrVolleyInterFace.mErrorListener) {
					@Override
					public void onSuccess(String response) {
						try {
							Log.i(TAG, "GET请求成功-->" + response);
							JSONObject jsonObject = new JSONObject(response);
							String result = jsonObject.getString("result");
							String resulttext = jsonObject
									.getString("resulttext");
							if (result.equals("1001")) {
								JSONArray jsonArray = jsonObject
										.getJSONArray("price");
								vListType = new ArrayList<ShopDataBean>();
								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject jsonObjectSon = (JSONObject) jsonArray
											.opt(i);
									gson = new Gson();
									ShopDataBean bean = gson.fromJson(
											jsonObjectSon.toString(),
											ShopDataBean.class);
									vListType.add(bean);
								}
								listener.onValue(vListType);
							} else {
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
	public void addCar(String url, ShopDataBean bean,
			final OnAttrListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url;
		Map<String, String> params = new HashMap<String, String>();
		params.put("openid", MyDate.getMyVid());
		params.put("goodsid", bean.goodsid);
		params.put("optionid", bean.optionid);
		params.put("total", bean.total + "");
		BaseVolleyRequest.StringRequestPost(context, url, TAG, params,
				new BaseStrVolleyInterFace(context,
						BaseStrVolleyInterFace.mListener,
						BaseStrVolleyInterFace.mErrorListener) {
					@Override
					public void onSuccess(String response) {
						Log.i(TAG, "POST请求成功-->" + response);
						try {
							JSONObject jsonObject = new JSONObject(response);
							String result = jsonObject.getString("result");
							if (result.equals("1001")) {
								listener.onAddCar();
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
						Log.i(TAG, "POST请求失败-->" + volleyError.toString());
						listener.onError(Url.networkError);
					}
				});
	}

}
