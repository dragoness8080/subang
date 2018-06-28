package com.gcs.suban.model;

import android.content.Context;

import com.android.volley.VolleyError;
import com.gcs.suban.MyDate;
import com.gcs.suban.Url;
import com.gcs.suban.app;
import com.gcs.suban.listener.OnPayApplyListener;
import com.gcs.suban.listener.OnPayStockListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PayInventoryModelImpl implements PayInventoryModel {
    protected Context context = app.getContext();
    @Override
    public void onPayCover(String url, int id, final OnPayStockListener listener) {
        final String TAG = url;
        Map<String,String> params = new HashMap<String,String>();
        params.put("openid", MyDate.getMyVid());
        params.put("id",String.valueOf(id));
        BaseVolleyRequest.StringRequestPost(context, url, TAG, params, new BaseStrVolleyInterFace(context,BaseStrVolleyInterFace.mListener,BaseStrVolleyInterFace.mErrorListener) {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    String msg = jsonObject.getString("resulttext");
                    if(result.equals("1001")){
                        listener.onPayCoverSuccess(msg);
                    }else{
                        listener.onError(msg);
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
    public void onPayApply(String url, String sn, final OnPayApplyListener listener) {
        final String TAG = url;
        Map<String,String> params = new HashMap<String,String>();
        params.put("openid", MyDate.getMyVid());
        params.put("sn",sn);
        BaseVolleyRequest.StringRequestPost(context, url, TAG, params, new BaseStrVolleyInterFace(context,BaseStrVolleyInterFace.mListener,BaseStrVolleyInterFace.mErrorListener) {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    String msg = jsonObject.getString("resulttext");
                    if(result.equals("1001")){
                        listener.onPayApplySuccess(msg);
                    }else{
                        listener.onError(msg);
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
