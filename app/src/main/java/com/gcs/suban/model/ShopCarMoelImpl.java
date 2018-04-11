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
import com.gcs.suban.bean.ShopCarBean;
import com.gcs.suban.listener.OnShopCarListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;

public class ShopCarMoelImpl implements ShopCarModel {

	private Context context = app.getContext();
	private List<ShopCarBean> mListType;

	@Override
	public void getInfo(String url, final OnShopCarListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url;
		Map<String, String> params = new HashMap<String, String>();
		params.put("openid", MyDate.getMyVid());
		Log.e(TAG, url + " " + MyDate.getMyVid());
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
							String isnull = jsonObject.getString("isnull");
							mListType = new ArrayList<ShopCarBean>();
							if (result.equals("1001")) {
								if (isnull.equals("0")) {
									JSONArray jsonArray;
									jsonArray = jsonObject.getJSONArray("data");
									for (int i = 0; i < jsonArray.length(); i++) {
										JSONObject jsonObjectSon = (JSONObject) jsonArray
												.opt(i);
										ShopCarBean bean = new ShopCarBean();
										bean.setShopName(jsonObjectSon
												.getString("title"));
										bean.setCartid(jsonObjectSon
												.getInt("cartid"));
										bean.setGoodsid(jsonObjectSon
												.getInt("goodsid"));
										bean.setShopNumber(jsonObjectSon
												.getInt("total"));
										bean.setShopPicture(jsonObjectSon
												.getString("thumb"));
										bean.setShopPrice((float) jsonObjectSon
												.getDouble("marketprice"));
										mListType.add(bean);
									}
								} else {
									mListType = null;
								}
								listener.onSuccess(mListType);
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
	public void setNum(String url, String cartid, String total,
			final OnShopCarListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url;
		Map<String, String> params = new HashMap<String, String>();
		params.put("cartid", cartid);
		params.put("total", total);
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
								listener.onNum();
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
					public void onError(VolleyError error) {
						// TODO Auto-generated method stub
						Log.i(TAG, "POST请求失败-->" + error.toString());
						listener.onError(Url.networkError);
					}
				});
	}

	@Override
	public void delete(String url, String cartid, final OnShopCarListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url;
		Map<String, String> params = new HashMap<String, String>();
		params.put("cartid", cartid);
		Log.e(TAG, cartid);
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
							String resulttext = jsonObject
									.getString("resulttext");
							if (result.equals("1001")) {
								listener.onDelete(resulttext);
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
					public void onError(VolleyError error) {
						// TODO Auto-generated method stub
						Log.i(TAG, "POST请求失败-->" + error.toString());
						listener.onError(Url.networkError);
					}
				});
	}
}
