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
import com.gcs.suban.MyDate;
import com.gcs.suban.Url;
import com.gcs.suban.app;
import com.gcs.suban.bean.CollectBean;
import com.gcs.suban.listener.OnCollectListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;

public class CollectModelImpl implements CollectModel {

	private Context context = app.getContext();
	private List<CollectBean> mListType;
	private String page = null;

	@Override
	public void getInfo(String url, String mPage,
			final OnCollectListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url;
		Map<String, String> params = new HashMap<String, String>();
		params.put("openid", MyDate.getMyVid());
		params.put("page", mPage);
		Log.e(TAG, url + " " + mPage + " " + MyDate.getMyVid());
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
							page = jsonObject.getString("page");
							mListType = new ArrayList<CollectBean>();
							if (result.equals("1001")) {
								String isnull = jsonObject.getString("isnull");
								if (isnull.equals("0")) {
									JSONArray jsonArray;
								
									jsonArray = jsonObject.getJSONArray("data");
									for (int i = 0; i < jsonArray.length(); i++) {
										JSONObject jsonObjectSon = (JSONObject) jsonArray
												.opt(i);
										CollectBean bean = new CollectBean();
										bean.setShopName(jsonObjectSon
												.getString("title"));
										bean.setShopId(jsonObjectSon
												.getInt("goodsid"));
										bean.setId(jsonObjectSon
												.getInt("goodsid"));
										bean.setShopPicture(jsonObjectSon
												.getString("thumb"));
										bean.setShopPrice((float) jsonObjectSon
												.getDouble("marketprice"));
										bean.setShopPrice2((float) jsonObjectSon
												.getDouble("productprice"));
										mListType.add(bean);
									}
								} else {
									mListType = null;
								}
								listener.onSuccess(mListType, page);
							}

							else {
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
					public void onError(VolleyError error) {
						// TODO Auto-generated method stub
						Log.i(TAG, "POST请求失败-->" + error.toString());
						listener.onError(Url.networkError);
					}
				});
	}

	@Override
	public void cancel(String url, final String goodsid, final OnCollectListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url+goodsid;
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
							String resulttext = jsonObject
									.getString("resulttext");
							if (result.equals("1001")) {
								listener.onCollect(resulttext);
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

}
