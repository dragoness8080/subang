package com.gcs.suban.model;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.gcs.suban.MyDate;
import com.gcs.suban.Url;
import com.gcs.suban.app;
import com.gcs.suban.listener.OnMemberListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MemberModelImpl implements MemberModel {
	private Context context = app.getContext();
	
	@Override
	public void getInfo(String url, final OnMemberListener listener) {
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
						Log.i(TAG, "POST??????-->" + response);
						listener.onSuccess(response);
					}

					@Override
					public void onError(VolleyError volleyError) {
						Log.i(TAG, "POST???????-->" + volleyError.toString());
						listener.onError(Url.networkError);
					}
				});
	}

	@Override
	public void signIn(String url, final OnMemberListener listener) {
		final String TAG = url;
		Map<String,String> params = new HashMap<>();
		params.put("openid",MyDate.getMyVid());
		BaseVolleyRequest.StringRequestPost(context, url, TAG, params,
				new BaseStrVolleyInterFace(context,
						BaseStrVolleyInterFace.mListener,
						BaseStrVolleyInterFace.mErrorListener) {
			@Override
			public void onSuccess(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					String result = jsonObject.getString("result");
					if(result.equals("1001")){
						int flag = jsonObject.getInt("flag");
						String leaf = jsonObject.getString("leaf");
						listener.onSignSuccess(flag,leaf);
					}else{
						String resulttext = jsonObject.getString("resulttext");
						listener.onError(resulttext);
					}
				} catch (JSONException e) {
					e.printStackTrace();
					listener.onError(Url.jsonError);
				}
			}

			@Override
			public void onError(VolleyError error) {
				listener.onError(Url.networkError);
			}
		});
	}

	@Override
	public void exchange(String url, final OnMemberListener listener) {
		final String TAG = url;
		Map<String,String> params = new HashMap<>();
		params.put("openid",MyDate.getMyVid());
		BaseVolleyRequest.StringRequestPost(context, url, TAG, params,
				new BaseStrVolleyInterFace(context,
						BaseStrVolleyInterFace.mListener,
						BaseStrVolleyInterFace.mErrorListener) {
					@Override
					public void onSuccess(String response) {
						try {
							JSONObject jsonObject = new JSONObject(response);
							String result = jsonObject.getString("result");
							if(result.equals("1001")){
								String leaf = jsonObject.getString("leaf");
								String credit = jsonObject.getString("credit");
								listener.onExchangeSuccess(leaf,credit);
							}else{
								String resulttext = jsonObject.getString("resulttext");
								listener.onError(resulttext);
							}
						} catch (JSONException e) {
							e.printStackTrace();
							listener.onError(Url.jsonError);
						}
					}

					@Override
					public void onError(VolleyError error) {
						listener.onError(Url.networkError);
					}
				}
		);
	}

}
