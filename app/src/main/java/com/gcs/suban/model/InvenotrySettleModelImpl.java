package com.gcs.suban.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.gcs.suban.MyDate;
import com.gcs.suban.Url;
import com.gcs.suban.app;
import com.gcs.suban.bean.InventoryLogBean;
import com.gcs.suban.bean.InventoryMemberBean;
import com.gcs.suban.listener.OnInventorySettleListener;
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

public class InvenotrySettleModelImpl implements InventorySettleModel {
    protected Context context = app.getContext();
    protected InventoryLogBean logBean;
    protected InventoryMemberBean memberBean;
    protected Gson gson;
    @Override
    public void getSettled(String url, int id, String date, String page, final OnInventorySettleListener listener) {
        final String TAG = url;
        Map<String,String> params = new HashMap<String,String>();
        params.put("openid", MyDate.getMyVid());
        params.put("page", page);
        params.put("id", String.valueOf(id));
        params.put("date", date);
        Log.i("settled","POST------>" + params.toString());
        BaseVolleyRequest.StringRequestPost(context, url, TAG, params, new BaseStrVolleyInterFace(context,BaseStrVolleyInterFace.mListener,BaseStrVolleyInterFace.mErrorListener) {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    if(result.equals("1001")){
                        String page = jsonObject.getString("page");
                        String settle = jsonObject.getString("settle_money");
                        String isnull = jsonObject.getString("isnull");
                        List<InventoryLogBean> logBeanList = new ArrayList<InventoryLogBean>();
                        if(isnull.equals("0")){
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1 = (JSONObject)jsonArray.opt(i);
                                gson = new Gson();
                                logBean = gson.fromJson(jsonObject1.toString(),InventoryLogBean.class);
                                logBeanList.add(logBean);
                            }
                        }else{
                            logBeanList = null;
                        }

                        listener.OnSettledSuccess(settle,logBeanList,page);
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
    public void getBalance(String url, String date, String page, final OnInventorySettleListener listener) {
        final String TAG = url;
        Map<String,String> params = new HashMap<String,String>();
        params.put("openid", MyDate.getMyVid());
        params.put("page", page);
        params.put("date", date);
        Log.i(TAG,"POST请求成功-->" + params.toString());
        BaseVolleyRequest.StringRequestPost(context, url, TAG, params, new BaseStrVolleyInterFace(context,BaseStrVolleyInterFace.mListener,BaseStrVolleyInterFace.mErrorListener) {
            @Override
            public void onSuccess(String response) {
                Log.i(TAG,"POST请求成功-->" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    if(result.equals("1001")){
                        String page = jsonObject.getString("page");
                        String settled = jsonObject.getString("settled");
                        String isnull = jsonObject.getString("isnull");
                        List<InventoryMemberBean> memberBeanList = new ArrayList<InventoryMemberBean>();
                        if(isnull.equals("0")){
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1 = (JSONObject) jsonArray.opt(i);
                                memberBean = new InventoryMemberBean();
                                memberBean.realname = jsonObject1.getString("realname");
                                memberBean.imgUrl = jsonObject1.getString("avatar");
                                memberBean.nickname = jsonObject1.getString("nickname");
                                memberBean.gradeid = jsonObject1.getInt("inventory_grade");
                                memberBean.balance = jsonObject1.getString("settled_self");
                                memberBean.commission = jsonObject1.getString("balance");
                                memberBean.ratio = jsonObject1.getString("ratio");
                                memberBean.teamBalance = jsonObject1.getString("team_balance");
                                memberBeanList.add(memberBean);
                            }
                        }else{
                            memberBeanList = null;
                        }
                        listener.OnBalanceSuccess(settled,memberBeanList,page);
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
