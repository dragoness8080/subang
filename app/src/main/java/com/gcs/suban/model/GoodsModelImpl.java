package com.gcs.suban.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.gcs.suban.Url;
import com.gcs.suban.app;
import com.gcs.suban.bean.GoodsBean;
import com.gcs.suban.listener.OnGoodsListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsModelImpl implements GoodsModel {
    private Context context = app.getContext();
    @Override
    public void getGoodsList(String url, String page, String status, Map<String,String> listParam, final OnGoodsListener listener) {
        final String TAG = url + status;
        Map<String,String> params = new HashMap<String,String>();
        params.put("page",page);
        for (Map.Entry<String,String> entry :listParam.entrySet()){
            params.put(entry.getKey(),entry.getValue());
        }
        Log.i(TAG,"POST---->" + params.toString());
        BaseVolleyRequest.StringRequestPost(context, url, TAG, params, new BaseStrVolleyInterFace(context,BaseStrVolleyInterFace.mListener,BaseStrVolleyInterFace.mErrorListener) {
            @Override
            public void onSuccess(String response) {
                Log.i(TAG,"POST---->" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    if(result.equals("1001")){
                        String page = jsonObject.getString("page");
                        String isnull = jsonObject.getString("isnull");
                        List<GoodsBean> list;
                        if(isnull.equals("1")){
                            list = null;
                        }else{
                            list = new ArrayList<GoodsBean>();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for(int i = 0; i < jsonArray.length(); i ++){
                                GoodsBean goodsBean = new GoodsBean();
                                JSONObject jsonObject1 = (JSONObject)jsonArray.opt(i);
                                goodsBean.setImgurl(jsonObject1.getString("thumb"));
                                goodsBean.setGoodsid(jsonObject1.getInt("id"));
                                goodsBean.setName(jsonObject1.getString("title"));
                                goodsBean.setPricenow(jsonObject1.getDouble("marketprice"));
                                list.add(goodsBean);
                            }
                        }
                        listener.onGoodsSuccess(page,list);
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
