package com.gcs.suban.model;

import android.content.Context;

import com.android.volley.VolleyError;
import com.gcs.suban.MyDate;
import com.gcs.suban.Url;
import com.gcs.suban.app;
import com.gcs.suban.bean.IncomeBean;
import com.gcs.suban.listener.OnIncomeListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncomeModelImpl implements IncomeModel {
    private Context context =app.getContext();
    private List<IncomeBean> mList;
    @Override
    public void getIncome(String url, String page, final OnIncomeListener listener) {
        final String TAG = url;
        Map<String,String> params = new HashMap<>();
        params.put("openid", MyDate.getMyVid());
        params.put("page", page);
        BaseVolleyRequest.StringRequestPost(context, url, TAG, params, new BaseStrVolleyInterFace(context,BaseStrVolleyInterFace.mListener,BaseStrVolleyInterFace.mErrorListener) {
            @Override
            public void onSuccess(String respones) {
                try {
                    JSONObject jsonObject = new JSONObject(respones);
                    String result = jsonObject.getString("result");
                    String resulttext = jsonObject.getString("resulttext");
                    String page = jsonObject.getString("page");
                    if(result.equals("1001")){
                        String isnull = jsonObject.getString("isnull");
                        if(isnull.equals("1")){
                            mList = null;
                        }else{
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            mList = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i ++){
                                IncomeBean incomeBean = new IncomeBean();
                                JSONObject json = (JSONObject)jsonArray.opt(i);
                                incomeBean.setDate(json.getString("date"));
                                incomeBean.setOrder(json.getInt("has_order"));
                                incomeBean.setOrderCommission(json.getDouble("order_commission"));
                                incomeBean.setAgent(json.getInt("has_agent"));
                                incomeBean.setAgentCommission(json.getDouble("agent_commission"));
                                incomeBean.setTeam(json.getInt("has_team"));
                                incomeBean.setTeamCommission(json.getDouble("team_commission"));
                                incomeBean.setStock(json.getInt("has_stock"));
                                incomeBean.setStockCommission(json.getDouble("stock_commission"));
                                incomeBean.setBalance(json.getInt("has_achievement"));
                                incomeBean.setBalanceCommission(json.getDouble("achievement_commission"));
                                incomeBean.setManualAgent(json.getInt("has_manual_agent"));
                                incomeBean.setManualAgentCommission(json.getDouble("manual_agent_commission"));
                                incomeBean.setManualRecommend(json.getInt("has_manual_recommend"));
                                incomeBean.setManualRecommendCommission(json.getDouble("manual_recommend_commission"));
                                mList.add(incomeBean);
                            }
                        }
                        listener.onIncomeSuccess(page,mList);
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
