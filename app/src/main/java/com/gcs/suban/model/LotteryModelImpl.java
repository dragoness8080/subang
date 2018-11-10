package com.gcs.suban.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.gcs.suban.MyDate;
import com.gcs.suban.Url;
import com.gcs.suban.app;
import com.gcs.suban.bean.LotteryBean;
import com.gcs.suban.bean.RewardBean;
import com.gcs.suban.listener.OnLotteryListener;
import com.gcs.suban.listener.OnRewardListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LotteryModelImpl implements LotteryModel {

    private Context mContext = app.getContext();

    @Override
    public void getPrizeList(String url, final OnLotteryListener listener) {
        final String TAG = url;
        Map<String,String> params = new HashMap<>();
        params.put("openid", MyDate.getMyVid());
        BaseVolleyRequest.StringRequestPost(mContext, url, TAG, params,new BaseStrVolleyInterFace(
                mContext,BaseStrVolleyInterFace.mListener,
                BaseStrVolleyInterFace.mErrorListener) {
            @Override
            public void onSuccess(String response) {
                Log.i(TAG,"POST请求成功-->" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    String resulttext = jsonObject.getString("resulttext");
                    int times = jsonObject.getInt("times");
                    if(result.equals("1001")){
                        String isnull = jsonObject.getString("isnull");
                        if(isnull.equals("1")){
                            listener.onError(resulttext);
                        }else{
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            List<LotteryBean> mList = new ArrayList<>();
                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject1 = (JSONObject)jsonArray.opt(i);
                                LotteryBean bean = new LotteryBean();
                                bean.setId(jsonObject1.getInt("id"));
                                bean.setNum(jsonObject1.getInt("num"));
                                bean.setTitle(jsonObject1.getString("title"));
                                bean.setBgcolor(jsonObject1.getString("bgcolor"));
                                bean.setThumb(jsonObject1.getString("thumb"));
                                mList.add(bean);
                            }
                            listener.onGetPrizeSuccess(mList,times);
                        }
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
    public void getReward(String url, final OnLotteryListener listener) {
        final String TAG = url;
        Map<String,String> params = new HashMap<>();
        params.put("openid", MyDate.getMyVid());
        BaseVolleyRequest.StringRequestPost(mContext, url, TAG, params, new BaseStrVolleyInterFace(
                mContext,
                BaseStrVolleyInterFace.mListener,
                BaseStrVolleyInterFace.mErrorListener) {
            @Override
            public void onSuccess(String response) {
                Log.i(TAG,"POST请求成功-->" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    String resulttext = jsonObject.getString("resulttext");
                    int index = jsonObject.getInt("index");
                    String reward = jsonObject.getString("reward");
                    String title = jsonObject.getString("title");
                    String thumb = jsonObject.getString("thumb");
                    int times = jsonObject.getInt("times");
                    listener.onGetRewardSuccess(index,reward,title,thumb,times);
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
    public void getRewardList(String url, String page, int status, final OnRewardListener listener) {
        final String TAG = url + status;
        Map<String,String> params = new HashMap<>();
        params.put("openid", MyDate.getMyVid());
        params.put("page", page);
        params.put("status", String.valueOf(status));

        BaseVolleyRequest.StringRequestPost(mContext, url, TAG, params, new BaseStrVolleyInterFace(
                mContext,
                BaseStrVolleyInterFace.mListener,
                BaseStrVolleyInterFace.mErrorListener) {
            @Override
            public void onSuccess(String response) {
                Log.i(TAG,"POST请求成功-->" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    String resulttext = jsonObject.getString("resulttext");
                    String isnull = jsonObject.getString("isnull");
                    String page = jsonObject.getString("page");
                    if(result.equals("1001")){
                        if(isnull.equals("0")){
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            List<RewardBean> list = new ArrayList<>();
                            for (int i = 0 ; i < jsonArray.length(); i++){
                                RewardBean bean = new RewardBean();
                                JSONObject jsonObject1 = (JSONObject)jsonArray.opt(i);
                                bean.setTitle(jsonObject1.getString("title"));
                                bean.setCreatetime(jsonObject1.getString("createtime"));
                                bean.setExchangetime(jsonObject1.getString("exchangetime"));
                                bean.setRankStr(jsonObject1.getString("rankStr"));
                                bean.setStatus(jsonObject1.getInt("status"));
                                bean.setThumb(jsonObject1.getString("thumb"));
                                list.add(bean);
                            }
                            listener.onSuccess(list,page);
                        }else{
                            listener.onSuccess(null,page);
                        }
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
