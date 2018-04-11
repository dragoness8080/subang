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
import com.gcs.suban.bean.BankBean;
import com.gcs.suban.bean.MemberBean;
import com.gcs.suban.listener.OnPersonListener;
import com.gcs.suban.listener.OnPhoneListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;
import com.google.gson.Gson;

public class PersonModeImpl implements PersonModel {

	private Context context = app.getContext();
	private MemberBean bean;
	private BankBean bean2;
	private Gson gson;

	@Override
	public void getInfo(String url, final OnPersonListener listener) {
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
										MemberBean.class);
								String bankcard = jsonObject.getString("bankcard");
								JSONObject dataJsonObject2 = new JSONObject(bankcard);
								gson = new Gson();
								bean2 = gson.fromJson(dataJsonObject2.toString(),
										BankBean.class);
								listener.onSuccess(bean,bean2);
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
	public void getCode(String url, final OnPhoneListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url;
		BaseVolleyRequest.StringRequestGet(context, url, TAG,
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
								listener.onCode(resulttext);
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
	public void setLogo(String url, String file, final OnPersonListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url;
		Map<String, String> params = new HashMap<String, String>();
		params.put("openid", MyDate.getMyVid());
		Log.e(TAG, file);
		params.put("file", file);
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
							String avatar = jsonObject.getString("avatar");
							if (result.equals("1001")) {
								listener.onLogo(resulttext, avatar);
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
						Log.i(TAG, "POST请求失败-->" + volleyError.toString());
						listener.onError(Url.networkError);
					}
				});
	}

	@Override
	public void setSex(String url, String gender,
			final OnPersonListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url;
		Map<String, String> params = new HashMap<String, String>();
		params.put("openid", MyDate.getMyVid());
		params.put("gender", gender);
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
								listener.onSex(resulttext);
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
						Log.i(TAG, "POST请求失败-->" + volleyError.toString());
						listener.onError(Url.networkError);
					}
				});
	}

	@Override
	public void setPhone(String url, String mobile, String code,
			final OnPhoneListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url;
		Map<String, String> params = new HashMap<String, String>();
		params.put("mobile", mobile);
		params.put("code", code);
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
							String resulttext = jsonObject
									.getString("resulttext");
							if (result.equals("1001")) {
								listener.onSuccess(resulttext);
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
						Log.i(TAG, "POST请求失败-->" + volleyError.toString());
						listener.onError(Url.networkError);
					}
				});
	}

	@Override
	public void setPwd(String url, String mobile, String code, String newpwd,
			final OnPhoneListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url;
		Map<String, String> params = new HashMap<String, String>();
		params.put("mobile", mobile);
		params.put("code", code);
		params.put("newpwd", newpwd);
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
							String resulttext = jsonObject
									.getString("resulttext");
							if (result.equals("1001")) {
								listener.onSuccess(resulttext);
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
						Log.i(TAG, "POST请求失败-->" + volleyError.toString());
						listener.onError(Url.networkError);
					}
				});
	}

}
