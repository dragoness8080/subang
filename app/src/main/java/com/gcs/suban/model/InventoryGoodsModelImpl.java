package com.gcs.suban.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.gcs.suban.MyDate;
import com.gcs.suban.Url;
import com.gcs.suban.app;
import com.gcs.suban.bean.InventoryGoodsBean;
import com.gcs.suban.listener.OnInventoryGoodsListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryGoodsModelImpl implements InventoryGoodsModel {
    protected Context context = app.getContext();
    protected InventoryGoodsBean bean;
    @Override
    public void getGoodsList(String url, final OnInventoryGoodsListener listener) {
        final String TAG = url;
        Log.i("inventorygoods",TAG);
        Map<String,String> params = new HashMap<String,String>();
        params.put("openid", MyDate.getMyVid());
        BaseVolleyRequest.StringRequestPost(context, url, TAG, params, new BaseStrVolleyInterFace(context, BaseStrVolleyInterFace.mListener, BaseStrVolleyInterFace.mErrorListener) {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    if(result.equals("1001")){
                        List<InventoryGoodsBean> listType = new ArrayList<InventoryGoodsBean>();
                        String isnull = jsonObject.getString("isnull");
                        if(isnull.equals("1")){
                            listType = null;
                        }else{
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1 = (JSONObject)jsonArray.opt(i);
                                Gson gson = new Gson();
                                bean = gson.fromJson(jsonObject1.toString(),InventoryGoodsBean.class);
                                listType.add(bean);
                            }
                        }
                        listener.onGoodsList(listType);
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
    public void getInfo(String url, OnInventoryGoodsListener listener) {

    }
}
