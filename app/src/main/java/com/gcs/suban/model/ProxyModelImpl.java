package com.gcs.suban.model;

import android.content.Context;

import com.android.volley.VolleyError;
import com.gcs.suban.MyDate;
import com.gcs.suban.Url;
import com.gcs.suban.app;
import com.gcs.suban.listener.OnBaseListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProxyModelImpl implements ProxyModel {
    private Context context = app.getContext();
    @Override
    public void getProxy(String url, final OnBaseListener listener) {
        final String TAG = url;
        Map<String,String> params = new HashMap<>();
        params.put("openid", MyDate.getMyVid());
        BaseVolleyRequest.StringRequestPost(context, url, TAG, params, new BaseStrVolleyInterFace(context,BaseStrVolleyInterFace.mListener,BaseStrVolleyInterFace.mErrorListener) {
            @Override
            public void onSuccess(String respose) {
                try {
                    JSONObject jsonObject = new JSONObject(respose);
                    String result = jsonObject.getString("result");
                    String resulttext = jsonObject.getString("resulttext");
                    if(result.equals("1001")){
                        String image = jsonObject.getString("imgpath");
                        listener.onSuccess(image);
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
