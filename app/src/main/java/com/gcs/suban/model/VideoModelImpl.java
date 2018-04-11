package com.gcs.suban.model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.gcs.suban.Url;
import com.gcs.suban.app;
import com.gcs.suban.bean.HlvSimpleBean;
import com.gcs.suban.listener.OnVideoListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;

public class VideoModelImpl implements VideoModel{
	private Context context = app.getContext();
	private List<HlvSimpleBean> mListType;
	private HlvSimpleBean bean;
	private String page = null;

	@Override
	public void getListInfo(String url, final OnVideoListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url;
		BaseVolleyRequest.StringRequestGet(context, url, TAG, 
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
							mListType = new ArrayList<HlvSimpleBean>();
							if (result.equals("1001")) {
								if (isnull.equals("0")) {
									JSONArray jsonArray;
									page = jsonObject.getString("page");
									jsonArray = jsonObject.getJSONArray("data");
									for (int i = 0; i < jsonArray.length(); i++) {
										JSONObject jsonObjectSon = (JSONObject) jsonArray
												.opt(i);
										bean=new HlvSimpleBean();
										bean.thumb = jsonObjectSon.getString("thumb");
										bean.title = jsonObjectSon.getString("title");
										bean.createtime = jsonObjectSon.getString("createtime");
										bean.article_id= jsonObjectSon.getString("article_id");
										bean.shareurl= jsonObjectSon.getString("shareurl");
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
	public void getSort(String url, final OnVideoListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url;
		BaseVolleyRequest.StringRequestGet(context, url, TAG, 
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
							mListType = new ArrayList<HlvSimpleBean>();
							if (result.equals("1001")) {
								if (isnull.equals("0")) {
									JSONArray jsonArray;
									jsonArray = jsonObject.getJSONArray("data");
									for (int i = 0; i < jsonArray.length(); i++) {
										JSONObject jsonObjectSon = (JSONObject) jsonArray
												.opt(i);
										bean=new HlvSimpleBean();
										bean.title = jsonObjectSon.getString("name");
										bean.id= jsonObjectSon.getString("id");
										mListType.add(bean);
									}
								} else {
									mListType = null;
								}
								listener.onSetSort(mListType);
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
