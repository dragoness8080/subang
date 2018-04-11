package com.gcs.suban.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.VolleyError;
import com.gcs.suban.Url;
import com.gcs.suban.app;
import com.gcs.suban.bean.WeixinBean;
import com.gcs.suban.listener.OnWeixinListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;
import com.google.gson.Gson;

public class WeixinModelImpl implements WeixinModel {

	private Context context = app.getContext();
	private WeixinBean bean;
	private Gson gson;

	@Override
	public void getToken(String url, final OnWeixinListener listener) {
		final String TAG = url;
		// TODO Auto-generated method stub
		BaseVolleyRequest.StringRequestGet(context, url, TAG,
				new BaseStrVolleyInterFace(context,
						BaseStrVolleyInterFace.mListener,
						BaseStrVolleyInterFace.mErrorListener) {
					@Override
					public void onSuccess(String response) {
						Log.i("weixin", "GET请求成功-->" + response);
						try {
							JSONObject jsonObject = new JSONObject(response);
							gson = new Gson();
							bean = gson.fromJson(jsonObject.toString(),
									WeixinBean.class);
							listener.onToken(bean);
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
	public void getInfo(String url, final OnWeixinListener listener) {
		// TODO Auto-generated method stub
		/*final String TAG = url;
		BaseVolleyRequest.StringRequestGet(context, url, TAG,
				new BaseStrVolleyInterFace(context,
						BaseStrVolleyInterFace.mListener,
						BaseStrVolleyInterFace.mErrorListener) {

					@Override
					public void onSuccess(String response) {
						Log.i("weixin", "GET请求成功-->" + response);
						try {
							JSONObject jsonObject = new JSONObject(response);
							gson = new Gson();
							bean = gson.fromJson(jsonObject.toString(),
									WeixinBean.class);
							listener.onInfo(bean);
						} catch (JSONException e) {
							e.printStackTrace();
							listener.onError(Url.jsonError);
						}
					}

					@Override
					public void onError(VolleyError volleyError) {
						Log.i(TAG, "GET请求失败-->" + volleyError.toString());
						listener.onError(Url.networkError);
					}
				});*/

		getInfoTask mGetUserInfoTask = new getInfoTask(listener, url); 
		mGetUserInfoTask.execute();
	}

	@Override
	public void Login(String url, WeixinBean bean,
			final OnWeixinListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url;
		Map<String, String> params = new HashMap<String, String>();
		params.put("unionid", bean.unionid);
		params.put("openid", bean.openid);
		params.put("sex", bean.sex);
		params.put("nickname", bean.nickname);
		params.put("picurl", bean.headimgurl);
		Log.e("weixin", url + " " + bean.unionid + " " + bean.sex + " "
				+ bean.headimgurl + " " + bean.nickname);
		BaseVolleyRequest.StringRequestPost(context, url, TAG, params,
				new BaseStrVolleyInterFace(context,
						BaseStrVolleyInterFace.mListener,
						BaseStrVolleyInterFace.mErrorListener) {
					@Override
					public void onSuccess(String response) {
						Log.i("weixin", "POST请求成功-->" + response);
						try {
							JSONObject jsonObject = new JSONObject(response);
							String result = jsonObject.getString("result");
							if (result.equals("1001")) {
								String isboundphone = jsonObject
										.getString("isboundphone");
								String openid = jsonObject.getString("openid");
								String id = jsonObject.getString("id");
								listener.onLogin(openid, isboundphone,id);
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

	public class getInfoTask extends AsyncTask<Object, Void, String> {

		private OnWeixinListener mListener;
		private String mUrl;

		public getInfoTask(OnWeixinListener listener, String getUserInfoUrl) {
			mListener = listener;
			mUrl = getUserInfoUrl;
		}

		@Override
		protected String doInBackground(Object... params) {
			HttpURLConnection conn = null;
			BufferedReader in = null;
			StringBuilder result = new StringBuilder();

			try {
				conn = (HttpURLConnection) new URL(mUrl).openConnection();
				in = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "UTF-8"));
				String buf;
				while ((buf = in.readLine()) != null)
					result.append(buf);
			} catch (Exception e) {
				e.printStackTrace();
				mListener.onError(Url.networkError);
			}
			return result.toString();
		}

		@Override
		protected void onPostExecute(String response) {
			Log.d("WXEntryActivity", "onPostExecute:" + response);
			try {
				JSONObject jsonObject = new JSONObject(response);
					gson = new Gson();
					bean = gson.fromJson(jsonObject.toString(),
							WeixinBean.class);
					mListener.onInfo(bean);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mListener.onError(Url.jsonError);
			}
		}

	}

}
