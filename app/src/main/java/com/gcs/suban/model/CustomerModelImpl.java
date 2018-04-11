package com.gcs.suban.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.gcs.suban.MyDate;
import com.gcs.suban.Url;
import com.gcs.suban.app;
import com.gcs.suban.bean.TeamBean;
import com.gcs.suban.listener.OnTeamListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerModelImpl implements TeamModel{
	private Context context = app.getContext();
	private List<TeamBean> mListType;
	private TeamBean bean;
	private String page = null;
	
	@Override
	public void getInfo(String url, String mPage, final OnTeamListener listener) {
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
						Log.i(TAG, "POST����ɹ�-->" + response);
						try {
							JSONObject jsonObject = new JSONObject(response);
							String result = jsonObject.getString("result");
							String isnull = jsonObject.getString("isnull");
							mListType = new ArrayList<TeamBean>();
							if (result.equals("1001")) {
								if (isnull.equals("0")) {
									JSONArray jsonArray;
									page = jsonObject.getString("page");
									jsonArray = jsonObject.getJSONArray("data");
									for (int i = 0; i < jsonArray.length(); i++) {
										JSONObject jsonObjectSon = (JSONObject) jsonArray
												.opt(i);
										bean=new TeamBean();
										bean.avatar = jsonObjectSon.getString("avatar");
										bean.nickname = jsonObjectSon.getString("nickname");
										bean.createtime = jsonObjectSon.getString("createtime");
										bean.id= jsonObjectSon.getString("id");
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
						Log.i(TAG, "POST����ʧ��-->" + error.toString());
						listener.onError(Url.networkError);
					}
				});
	}

	@Override
	public void searchInfo(String url,String typr, String content, String page, OnTeamListener listener) {
	}

}

