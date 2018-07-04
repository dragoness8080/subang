package com.gcs.suban.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.gcs.suban.Url;
import com.gcs.suban.app;
import com.gcs.suban.bean.LogisticsBean;
import com.gcs.suban.listener.OnLogisticsListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelfLogistModelImpl implements SelfLogistModel {

    protected Context context = app.getContext();
    protected List<LogisticsBean> mListType;

    @Override
    public void getInfo(String url, String id, final OnLogisticsListener listener) {
        final String TAG = url;
        Map<String,String> params = new HashMap<String,String>();
        params.put("id", id);
        BaseVolleyRequest.StringRequestPost(context, url, TAG, params, new BaseStrVolleyInterFace(context, BaseStrVolleyInterFace.mListener, BaseStrVolleyInterFace.mErrorListener) {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    mListType = new ArrayList<LogisticsBean>();
                    if (result.equals("1001")){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject1 = (JSONObject)jsonArray.opt(i);
                            LogisticsBean bean = new LogisticsBean();
                            bean.address = jsonObject1
                                    .getString("address");
                            bean.time = jsonObject1.getString("time");
                            mListType.add(bean);
                        }
                        listener.onSuccess(mListType);
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
}
