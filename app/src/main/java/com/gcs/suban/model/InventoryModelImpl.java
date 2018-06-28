package com.gcs.suban.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.gcs.suban.MyDate;
import com.gcs.suban.Url;
import com.gcs.suban.app;
import com.gcs.suban.bean.InventoryGradeBean;
import com.gcs.suban.bean.InventoryMemberBean;
import com.gcs.suban.listener.OnInventoryListener;
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

public class InventoryModelImpl implements InventoryModel {

    private Context context = app.getContext();
    private List<InventoryGradeBean> gradeBeanList;
    private Gson gson;

    @Override
    public void getImgUrl(String url, final OnInventoryListener listener) {
        final String TAG = url;
        BaseVolleyRequest.StringRequestGet(context, url, TAG, new BaseStrVolleyInterFace(context, BaseStrVolleyInterFace.mListener, BaseStrVolleyInterFace.mErrorListener) {
            @Override
            public void onSuccess(String response) {
                Log.i("StockActivity", response);
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    if(result.equals("1001")){
                        listener.OnImgSuccess(jsonObject.getString("uri"));
                    }else{
                        listener.OnImgSuccess(null);
                    }
                }catch (JSONException e){
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
    public void getGradeList(String url, final OnInventoryListener listener) {
        final String TAG = url;
        BaseVolleyRequest.StringRequestGet(context, url, TAG, new BaseStrVolleyInterFace(context, BaseStrVolleyInterFace.mListener, BaseStrVolleyInterFace.mErrorListener) {
            @Override
            public void onSuccess(String response) {
                Log.i("gradelist", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    if(result.equals("1001")){
                        String isNull = jsonObject.getString("isnull");
                        if(isNull.equals("0")){
                            gradeBeanList = new ArrayList<InventoryGradeBean>();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObjectson = (JSONObject)jsonArray.opt(i);
                                InventoryGradeBean bean = new InventoryGradeBean();
                                bean.id = jsonObjectson.getInt("id");
                                bean.title = jsonObjectson.getString("title");
                                gradeBeanList.add(bean);
                            }
                        }else{
                            gradeBeanList = null;
                        }
                    }else{
                        gradeBeanList = null;
                    }
                    listener.OnGradeSuccess(gradeBeanList);
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
    public void apply(String url, InventoryMemberBean bean, final OnInventoryListener listener) {
        final String TAG = url;
        Log.i("inventoryapply", "openid=" + MyDate.getMyVid() + ",name=" + bean.realname + ",mobile=" + bean.mobile + ",grade=" + bean.gradeid);
        Map<String,String> params = new HashMap<String,String>();
        params.put("openid", MyDate.getMyVid());
        params.put("name", bean.realname);
        params.put("mobile", bean.mobile);
        params.put("grade", String.valueOf(bean.gradeid));
        BaseVolleyRequest.StringRequestPost(context, url, TAG, params, new BaseStrVolleyInterFace(context, BaseStrVolleyInterFace.mListener, BaseStrVolleyInterFace.mErrorListener) {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    String msg = jsonObject.getString("resulttext");
                    if(result.equals("1001")){
                        String sn = jsonObject.getString("sn");
                        double money = jsonObject.getDouble("money");
                        int ispay = jsonObject.getInt("ispay");
                        listener.OnSuccess(msg,sn,money,ispay);
                    }else{
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

    @Override
    public void getApply(String url, final OnInventoryListener listener) {
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
                        InventoryMemberBean bean = new InventoryMemberBean();
                        String isNull = jsonObject.getString("isnull");
                        if(isNull.equals("1")){
                            bean = null;
                        }else{
                            JSONObject jsonObjectson = jsonObject.getJSONObject("data");
                            gson = new Gson();
                            bean = gson.fromJson(jsonObjectson.toString(),InventoryMemberBean.class);
                        }
                        listener.OnApplySuccess(bean);
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
}
