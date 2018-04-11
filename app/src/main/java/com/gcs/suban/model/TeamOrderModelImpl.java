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
import com.gcs.suban.bean.OrderBean;
import com.gcs.suban.listener.OnDetailListener;
import com.gcs.suban.listener.OnOrderHelperListener;
import com.gcs.suban.listener.OnOrderListListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;

public class TeamOrderModelImpl implements OrderModel {

	private Context context = app.getContext();
	private List<OrderBean> mListType;
	private String page = null;

	@Override
	public void getList(String url, String status, String mPage,
			final OnOrderListListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url + status;
		Map<String, String> params = new HashMap<String, String>();
		params.put("openid",  MyDate.getMyVid());
		params.put("page", mPage);
		params.put("status", status);
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
							if (result.equals("1001")) {
								String isnull = jsonObject.getString("isnull");
								if (isnull.equals("0")) {
									JSONArray jsonArray;
									jsonArray = jsonObject.getJSONArray("data");
									mListType = new ArrayList<OrderBean>();
									for (int i = 0; i < jsonArray.length(); i++) {
										JSONObject jsonObjectSon = (JSONObject) jsonArray
												.opt(i);
										OrderBean bean = new OrderBean();
										bean.price = jsonObjectSon
												.getDouble("ordercommission");
										bean.createtime = jsonObjectSon
												.getString("createtime");
										bean.statusname = jsonObjectSon
												.getString("statusname");
										bean.ordersn = jsonObjectSon
												.getString("ordersn");
										bean.nickname = jsonObjectSon
												.getString("nickname");
										bean.level = jsonObjectSon
												.getString("level");
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
	public void getDetail(String url, OrderBean bean,
			final OnDetailListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelOrder(String url, String orderid,
			OnOrderHelperListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void confirmOrder(String url, String orderid,
			OnOrderHelperListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelRefund(String url, String orderid,
			OnOrderHelperListener listener) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void deleteOrder(String url, String orderid,
			OnOrderHelperListener listener) {
		// TODO Auto-generated method stub
		
	}
}