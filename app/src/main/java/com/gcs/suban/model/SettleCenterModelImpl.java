package com.gcs.suban.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.gcs.suban.MyDate;
import com.gcs.suban.Url;
import com.gcs.suban.app;
import com.gcs.suban.listener.OnSettleCenterListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SettleCenterModelImpl implements SettleCenterModel {
    private Context context = app.getContext();
    @Override
    public void getSettled(String url, final OnSettleCenterListener listener) {
        final String TAG = url;
        Map<String,String> params = new HashMap<>();
        params.put("openid", MyDate.getMyVid());
        Log.i("settled","POST----->" + url);
        BaseVolleyRequest.StringRequestPost(context, url, TAG, params, new BaseStrVolleyInterFace(
                context,
                BaseStrVolleyInterFace.mListener,
                BaseStrVolleyInterFace.mErrorListener
        ) {
            @Override
            public void onSuccess(String response) {
                Log.i("settled","POST----->" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    String resulttext = jsonObject.getString("resulttext");
                    if(result.equals("1001")){
                        String t1 = jsonObject.getString("totalPay");
                        String t2 = jsonObject.getString("totalApply");
                        String t3 = jsonObject.getString("totalWaitPay");
                        String t4 = jsonObject.getString("applys");
                        String t5 = jsonObject.getString("total");
                        listener.onSuccess(t1,t2,t3,t4,t5);
                    }else{
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
    public void balance(String url, final OnSettleCenterListener listener) {
        final String TAG = url;
        Map<String,String> params = new HashMap<>();
        params.put("openid", MyDate.getMyVid());
        BaseVolleyRequest.StringRequestPost(context, url, TAG, params, new BaseStrVolleyInterFace(
                context,
                BaseStrVolleyInterFace.mListener,
                BaseStrVolleyInterFace.mErrorListener
        ) {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    String resulttext = jsonObject.getString("resulttext");
                    if(result.equals("1001")){
                        listener.onBalanceSuccess(resulttext);
                    }else{
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
