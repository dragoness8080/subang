package com.gcs.suban.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.gcs.suban.MyDate;
import com.gcs.suban.Url;
import com.gcs.suban.app;
import com.gcs.suban.bean.InventoryLogBean;
import com.gcs.suban.listener.OnInventoryStockListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryStockModelImpl implements InventoryStockModel {
    private Context context = app.getContext();
    private InventoryLogBean bean;
    @Override
    public void getTotalInfo(String url, final OnInventoryStockListener listener) {
        final String TAG = url;
        Map<String,String> params = new HashMap<String,String>();
        params.put("openid", MyDate.getMyVid());
        BaseVolleyRequest.StringRequestPost(context, url, TAG, params, new BaseStrVolleyInterFace(context,BaseStrVolleyInterFace.mListener,BaseStrVolleyInterFace.mErrorListener) {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    if(result.equals("1001")){
                        String money = jsonObject.getString("money");
                        String num = jsonObject.getString("num");
                        String bail = jsonObject.getString("bail");
                        String settle = jsonObject.getString("settle_money");
                        String extr = jsonObject.getString("extr_money");
                        double settle_money = Double.valueOf(settle) + Double.valueOf(extr);
                        //String title = jsonObject.getString("title");
                        String garde_money = jsonObject.getString("grade_money");
                        String grade_title = jsonObject.getString("grade_title");
                        String avatar = jsonObject.getString("avatar");
                        listener.OnTotalSuccess(money,num,bail,String.valueOf(settle_money),garde_money,grade_title,avatar);
                    }else{
                        listener.OnError(jsonObject.getString("resulttext"));
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

    @Override
    public void getLogs(String url, String types, String page, final OnInventoryStockListener listener) {
        final String TAG = url + types;
        Map<String,String> params = new HashMap<String,String>();
        params.put("openid", MyDate.getMyVid());
        params.put("page",page);
        params.put("types",types);
        BaseVolleyRequest.StringRequestPost(context, url, TAG, params, new BaseStrVolleyInterFace(context,BaseStrVolleyInterFace.mListener,BaseStrVolleyInterFace.mErrorListener) {
            @Override
            public void onSuccess(String response) {
                Log.i(TAG, "POSTÇëÇó³É¹¦-->" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.i("inventorylog",response);
                    String result = jsonObject.getString("result");
                    if(result.equals("1001")){
                        String page = jsonObject.getString("page");
                        String isnull = jsonObject.getString("isnull");
                        List<InventoryLogBean> list = new ArrayList<InventoryLogBean>();
                        if(isnull.equals("0")){
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObjectson = (JSONObject)jsonArray.opt(i);
                                Gson gson = new Gson();
                                bean = gson.fromJson(jsonObjectson.toString(),InventoryLogBean.class);
                                list.add(bean);
                            }
                        }else{
                            list = null;
                        }
                        listener.OnLogsSuccess(list,page);
                    }else{
                        listener.OnError(jsonObject.getString("resulttext"));
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

    @Override
    public void getStock(String url, OnInventoryStockListener listener) {

    }
}
