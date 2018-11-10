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
import com.gcs.suban.bean.RecordBean;
import com.gcs.suban.bean.SettledBean;
import com.gcs.suban.listener.OnRecordListener;
import com.gcs.suban.listener.OnSettledRecordListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;
import com.google.gson.Gson;

public class RecordModelImpl implements RecordModel{

	
	private Context context = app.getContext();
	private List<RecordBean> mListType;
	private List<SettledBean> settledBeanList;
	private String page;
	private RecordBean bean;
	private Gson gson;
	
	@Override
	public void getInfo(String url, String mPage, final OnRecordListener listener) {
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
							mListType = new ArrayList<RecordBean>();
							if (result.equals("1001")) {
								String isnull = jsonObject.getString("isnull");
								if(isnull.equals("0"))
								{
									JSONArray jsonArray;
									jsonArray = jsonObject.getJSONArray("data");
									for (int i = 0; i < jsonArray.length(); i++) {
										JSONObject jsonObjectSon = (JSONObject) jsonArray
												.opt(i);
										gson = new Gson();
										bean = gson.fromJson(
												jsonObjectSon.toString(),
												RecordBean.class);
										mListType.add(bean);
									}
								}
								else {
									mListType=null;
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
	public void getSettleList(String url, String mPage, final OnSettledRecordListener listener) {
		final String TAG = url;
		Map<String, String> params = new HashMap<String, String>();
		params.put("openid", MyDate.getMyVid());
		params.put("page", mPage);
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
							settledBeanList = new ArrayList<SettledBean>();
							if (result.equals("1001")) {
								String isnull = jsonObject.getString("isnull");
								if(isnull.equals("0"))
								{
									JSONArray jsonArray;
									jsonArray = jsonObject.getJSONArray("data");
									for (int i = 0; i < jsonArray.length(); i++) {
										JSONObject jsonObjectSon = (JSONObject) jsonArray
												.opt(i);
										gson = new Gson();
										SettledBean settledBean = gson.fromJson(
												jsonObjectSon.toString(),
												SettledBean.class);
										settledBeanList.add(settledBean);
									}
								}
								else {
									settledBeanList=null;
								}
								listener.onSuccess(settledBeanList,page);
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
