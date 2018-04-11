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
import com.gcs.suban.bean.CouponBean;
import com.gcs.suban.listener.OnCouponListListener;
import com.gcs.suban.listener.OnCouponListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;
import com.google.gson.Gson;

public class CouponModelImpl implements CouPonModel{
	private Context context = app.getContext();
	private CouponBean bean;
	private Gson gson;
	private List<CouponBean> mListType;
	private String page=null;


	@Override
	public void getInfo(String url, final OnCouponListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url;
		Map<String, String> params = new HashMap<String, String>();
		params.put("openid", MyDate.getMyVid());
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
								String data = jsonObject.getString("data");
								gson = new Gson();
								bean = gson.fromJson(data,
										CouponBean.class);
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
						Log.i(TAG, "POST请求失败-->" + volleyError.toString());
						listener.onError(Url.networkError);
					}
				});
	}
	
	@Override
	public void getList(String url,String status,String mpage,final OnCouponListListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url+status;
		Map<String, String> params = new HashMap<String, String>();
		params.put("openid", MyDate.getMyVid());
		params.put("type", status);
		params.put("page", mpage);
		BaseVolleyRequest.StringRequestPost(context, url, TAG, params,
				new BaseStrVolleyInterFace(context,
						BaseStrVolleyInterFace.mListener,
						BaseStrVolleyInterFace.mErrorListener) {
					@Override
					public void onSuccess(String response) {
						Log.i(TAG, "POST请求成功-->" + response);
						try {
							JSONObject jsonObject = new JSONObject(response);
							//String result = jsonObject.getString("result");
							String isnull = jsonObject.getString("isnull");
							mListType = new ArrayList<CouponBean>();
							if (isnull.equals("0")) {
								page = jsonObject.getString("page");
								JSONArray jsonArray = jsonObject
										.getJSONArray("data");
								for (int i = 0; i < jsonArray.length(); i++) {
						 			JSONObject jsonObjectSon = (JSONObject) jsonArray
											.opt(i);
									gson = new Gson();
									bean = gson.fromJson(
											jsonObjectSon.toString(),
											CouponBean.class);
									mListType.add(bean);
								}
							} else if (isnull.equals("1")) {
								mListType = null;
							}
							listener.onSuccess(mListType, page);
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
