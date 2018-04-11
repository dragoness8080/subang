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
import com.gcs.suban.bean.ReturnBean;
import com.gcs.suban.listener.OnImgUpListener;
import com.gcs.suban.listener.OnReturnOrderListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;

public class ReternModelImpl implements ReturnModel {

	private Context context = app.getContext();

	@Override
	public void getInfo(String url, String orderid,
			final OnReturnOrderListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url;
		Map<String, String> params = new HashMap<String, String>();
		params.put("openid", MyDate.getMyVid());
		params.put("orderid", orderid);
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
								ReturnBean bean = new ReturnBean();
								bean.ordersn = jsonObject.getString("ordersn");
								bean.price = jsonObject.getDouble("oldprice");
								bean.goodstitle = jsonObject
										.getString("goodstitle");
								bean.time = jsonObject.getString("time");
								bean.refundmoney = jsonObject.getString("refundmoney");
								listener.onInfo(bean);
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
	public void onApply(String url, ReturnBean bean,
			final OnReturnOrderListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url;
		Map<String, String> params = new HashMap<String, String>();
		params.put("openid", MyDate.getMyVid());
		params.put("orderid", bean.orderid);
		params.put("reason", bean.reason);
		params.put("refund_img", bean.refund_img);
		params.put("mobile", bean.mobile);
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
							String resulttext = jsonObject.getString("resulttext");
							if (result.equals("1001")) {
								listener.onConfirm(resulttext);
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

	@Override
	public void UpImg(String url, String file, final OnImgUpListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url;
		Map<String, String> params = new HashMap<String, String>();
		params.put("openid", MyDate.getMyVid());
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
							if (result.equals("1001")) {
								String img = jsonObject
										.getString("img");
								listener.onUpImg(resulttext, img);
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
