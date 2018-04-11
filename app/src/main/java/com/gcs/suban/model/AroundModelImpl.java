package com.gcs.suban.model;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.gcs.suban.MyDate;
import com.gcs.suban.Url;
import com.gcs.suban.app;
import com.gcs.suban.bean.AroundBean;
import com.gcs.suban.listener.OnAroundListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AroundModelImpl implements AroundModel {
    private Context context = app.getContext();
    private ArrayList<AroundBean> mListType;

    @Override
    public void postLocation(String url, String lat, String lng, final OnAroundListener listener) {
        final String TAG = url;
        Map<String, String> params = new HashMap<String, String>();
        params.put("openid", MyDate.getMyVid());
        params.put("lat", lat);
        params.put("lng", lng);

        BaseVolleyRequest.StringRequestPost(context, url, TAG, params,
                new BaseStrVolleyInterFace(context,
                        BaseStrVolleyInterFace.mListener,
                        BaseStrVolleyInterFace.mErrorListener) {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("result");
                            String resulttext = jsonObject
                                    .getString("resulttext");
                            if (result.equals("1001")) {
                                listener.onSuccess();
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
                        listener.onError(Url.networkError);
                    }
                });
    }

    @Override
    public void getAround(String url, final OnAroundListener listener) {
        final String TAG = url;
        Map<String, String> params = new HashMap<String, String>();
        params.put("openid", MyDate.getMyVid());
        params.put("distince", "10");
        BaseVolleyRequest.StringRequestPost(context, url, TAG, params,
                new BaseStrVolleyInterFace(context,
                        BaseStrVolleyInterFace.mListener,
                        BaseStrVolleyInterFace.mErrorListener) {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(new String(response));
                            String result = jsonObject.getString("result");
                            String resulttext = jsonObject
                                    .getString("resulttext");
                            String isnull = jsonObject
                                    .getString("isnull");
                            mListType = new ArrayList<>();
                            if (result.equals("1001")&& !TextUtils.equals(isnull,"1")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObjectSon = (JSONObject) jsonArray
                                            .opt(i);
                                    Gson gson = new Gson();
                                    AroundBean aroundBean = gson.fromJson(jsonObjectSon.toString(), AroundBean.class);
                                    mListType.add(aroundBean);
                                }
                                listener.getAroundData(mListType);


                            } else if (TextUtils.equals(isnull, "1")) {
                                Toast.makeText(context, "ÄúÖÜ±ßÔÝÎÞËØ°îÈË", Toast.LENGTH_SHORT).show();
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
                        listener.onError(Url.networkError);
                    }
                });
    }
}
