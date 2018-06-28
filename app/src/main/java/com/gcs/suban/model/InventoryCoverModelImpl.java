package com.gcs.suban.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.gcs.suban.MyDate;
import com.gcs.suban.Url;
import com.gcs.suban.app;
import com.gcs.suban.listener.OnInventoryCoverListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InventoryCoverModelImpl implements InventoryCoverModel {
    protected Context context = app.getContext();
    @Override
    public void setStockCover(String url, String type, String data, final OnInventoryCoverListener listener) {
        final String TAG = url;
        Map<String,String> params = new HashMap<String,String>();
        params.put("openid", MyDate.getMyVid());
        params.put("type", type);
        params.put("data", data);
        BaseVolleyRequest.StringRequestPost(context, url, TAG, params, new BaseStrVolleyInterFace(context,BaseStrVolleyInterFace.mListener,BaseStrVolleyInterFace.mErrorListener) {
            @Override
            public void onSuccess(String response) {
                Log.i(TAG,"POSTÇëÇó--->" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    if(result.equals("1001")){
                        String id = jsonObject.getString("id");
                        String ordersn = jsonObject.getString("ordersn");
                        String money = jsonObject.getString("money");
                        int ispay = jsonObject.getInt("ispay");
                        listener.OnSuccess(money,ordersn,id,ispay);
                    }else{
                        String msg = jsonObject.getString("resulttext");
                        listener.OnError(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.OnError(Url.jsonError);
                }
            }

            @Override
            public void onError(VolleyError error) {
                listener.OnError(Url.networkError);
            }
        });
    }
}
