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
import com.gcs.suban.Url;
import com.gcs.suban.app;
import com.gcs.suban.bean.LogisticsBean;
import com.gcs.suban.listener.OnLogisticsListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;

public class LogisticsModelImpl implements LogistModel {

	private Context context = app.getContext();
	private List<LogisticsBean> mListType;

	@Override
	public void getInfo(String url, String orderid,
			final OnLogisticsListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url;
		Map<String, String> params = new HashMap<String, String>();
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
							mListType = new ArrayList<LogisticsBean>();
							if (result.equals("1001")) {
								JSONArray jsonArray = jsonObject
										.getJSONArray("data");
								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject jsonObjectSon = (JSONObject) jsonArray
											.opt(i);
									LogisticsBean bean = new LogisticsBean();
									bean.address = jsonObjectSon
											.getString("address");
									bean.time = jsonObjectSon.getString("time");
									mListType.add(bean);
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

}
