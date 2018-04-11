package com.gcs.suban.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.gcs.suban.MyDate;
import com.gcs.suban.Url;
import com.gcs.suban.app;
import com.gcs.suban.bean.StoreBean;
import com.gcs.suban.listener.OnStoreListener;
import com.gcs.suban.listener.OnStorenameListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;
import com.google.gson.Gson;

public class StoreModelImpl implements StoreModel{

	private Context context = app.getContext();
	private StoreBean bean;
	private Gson gson;
	
	@Override
	public void getInfo(String url, final OnStoreListener listener) {
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
								JSONObject dataJsonObject = new JSONObject(data);
								gson = new Gson();
								bean = gson.fromJson(dataJsonObject.toString(),
										StoreBean.class);
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
	public void setLogo(String url, String shopid,String file, final OnStoreListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url+shopid;
		Map<String, String> params = new HashMap<String, String>();
		params.put("shopid", shopid);
		params.put("file", file);
		params.put("openid", MyDate.getMyVid());
		Log.e(TAG, shopid+"  "+file);
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
								listener.onLogo();
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
	public void setImg(String url, final String shopid,String file, final OnStoreListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url+shopid;
		Map<String, String> params = new HashMap<String, String>();
		params.put("shopid", shopid);
		params.put("file", file);
		params.put("openid", MyDate.getMyVid());
		BaseVolleyRequest.StringRequestPost(context, url, TAG, params,
				new BaseStrVolleyInterFace(context,
						BaseStrVolleyInterFace.mListener,
						BaseStrVolleyInterFace.mErrorListener) {
					@Override
					public void onSuccess(String response) {
						Log.i(TAG, "POST请求成功-->" + response);
						Log.e(TAG, shopid);
						try {
							JSONObject jsonObject = new JSONObject(response);
							String result = jsonObject.getString("result");
							if (result.equals("1001")) {
								listener.onImg();
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
	public void setDesc(String url, String shopid,String file, final OnStoreListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url;
		Map<String, String> params = new HashMap<String, String>();
		params.put("shopid", shopid);
		params.put("desc", file);
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
								listener.ondesc();
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
	public void setName(String url, String shopid, String shopname,
			final OnStorenameListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url;
		Map<String, String> params = new HashMap<String, String>();
		params.put("shopid",shopid);
		params.put("shopname", shopname);
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
								listener.onSuccess();
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

}
