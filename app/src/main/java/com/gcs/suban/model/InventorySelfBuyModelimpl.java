package com.gcs.suban.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.gcs.suban.MyDate;
import com.gcs.suban.Url;
import com.gcs.suban.app;
import com.gcs.suban.bean.AddressBean;
import com.gcs.suban.bean.InventorySelfBuyBean;
import com.gcs.suban.bean.OrderBean;
import com.gcs.suban.bean.ShopDataBean;
import com.gcs.suban.listener.OnBaseListener;
import com.gcs.suban.listener.OnInventorySelfListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventorySelfBuyModelimpl implements InventorySelfBuyModel {
    protected Context context = app.getContext();
    protected InventorySelfBuyBean selfBuyBean;
    protected List<InventorySelfBuyBean> selfBuyBeanList;
    protected ShopDataBean shopDataBean;
    @Override
    public void saveSelfBuy(String url, String goodsList,String addressid, final OnInventorySelfListener listener) {
        final String TAG = url;
        Map<String,String> params = new HashMap<String,String>();
        params.put("openid", MyDate.getMyVid());
        params.put("data",goodsList);
        params.put("addressid", addressid);
        Log.i("POAT请求成功-->", params.toString());
        BaseVolleyRequest.StringRequestPost(context, url, TAG, params, new BaseStrVolleyInterFace(context,BaseStrVolleyInterFace.mListener,BaseStrVolleyInterFace.mErrorListener) {
            @Override
            public void onSuccess(String response) {
                Log.i(TAG,"POAT请求成功-->" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    String resulttext = jsonObject.getString("resulttext");
                    if(result.equals("1001")){
                        int hasdefaultaddress = jsonObject.getInt("hasdefaultaddress");   //是否存在发货地址
                        String address = jsonObject.getString("address");   //发货地址信息
                        String dispatchprice = jsonObject.getString("dispatchprice");   //运费
                        double money = jsonObject.getDouble("price");   //支付金额
                        String total = jsonObject.getString("total");   //支付数量
                        List<ShopDataBean> list = new ArrayList<ShopDataBean>();
                        JSONArray jsonArray = jsonObject.getJSONArray("goods");
                        for (int i = 0; i < jsonArray.length(); i ++){
                            JSONObject jsonObject1 = (JSONObject)jsonArray.opt(i);
                            Gson gson = new Gson();
                            shopDataBean = gson.fromJson(jsonObject1.toString(),ShopDataBean.class);
                            list.add(shopDataBean);
                        }
                        OrderBean orderBean = new OrderBean();
                        orderBean.goodsprice = money;
                        orderBean.total = total;
                        orderBean.dispatchprice = dispatchprice;
                        AddressBean addressBean;
                        if(hasdefaultaddress == 0){
                            addressBean = null;
                        }else{
                            Gson gson = new Gson();
                            JSONObject jsonObject1 = new JSONObject(address);
                            addressBean = gson.fromJson(jsonObject1.toString(),AddressBean.class);
                        }
                        listener.onSaveSuccess(orderBean,addressBean,list);
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
    public void getSelfBuy(String url, int status, String page, final OnInventorySelfListener listener) {
        final String TAG = url + status;
        Map<String,String> params = new HashMap<String,String>();
        params.put("openid", MyDate.getMyVid());
        params.put("status", String.valueOf(status));
        params.put("page", page);
        Log.i(TAG,"selfbuy" + params.toString());
        BaseVolleyRequest.StringRequestPost(context, url, TAG, params, new BaseStrVolleyInterFace(context,BaseStrVolleyInterFace.mListener,BaseStrVolleyInterFace.mErrorListener) {
            @Override
            public void onSuccess(String response) {
                Log.i(TAG,"POST请求成功-->" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    String resulttext = jsonObject.getString("resulttext");
                    String page = jsonObject.getString("page");
                    if(result.equals("1001")){
                        String isnull = jsonObject.getString("isnull");
                        if(isnull.equals("0")){
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            selfBuyBeanList = new ArrayList<InventorySelfBuyBean>();
                            for (int i = 0; i < jsonArray.length(); i ++){
                                JSONObject jsonObject1 = (JSONObject)jsonArray.opt(i);
                                selfBuyBean = new InventorySelfBuyBean();
                                //selfBuyBean.address = jsonObject1.getString("address");
                                selfBuyBean.id=jsonObject1.getInt("id");
                                selfBuyBean.check_time = jsonObject1.getString("check_time");
                                selfBuyBean.create_time = jsonObject1.getString("create_time");
                                selfBuyBean.deliver_time = jsonObject1.getString("deliver_time");
                                selfBuyBean.finish_time = jsonObject1.getString("finish_time");
                                selfBuyBean.goods_list = jsonObject1.getString("goods_list");
                                selfBuyBean.money = jsonObject1.getDouble("money");
                                selfBuyBean.num = jsonObject1.getInt("num");
                                selfBuyBean.total = jsonObject1.getInt("total");
                                selfBuyBean.status_str = jsonObject1.getString("status_str");
                                selfBuyBean.freight = jsonObject1.getDouble("express_money");
                                selfBuyBeanList.add(selfBuyBean);
                            }

                        }else{
                            selfBuyBeanList = null;
                        }

                        listener.onSuccess(selfBuyBeanList,page);
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
    public void cancelSelf(String url, int id, final OnBaseListener listener) {
        final String TAG = url;
        Map<String,String> params = new HashMap<String,String>();
        params.put("id",String.valueOf(id));
        BaseVolleyRequest.StringRequestPost(context, url, TAG, params, new BaseStrVolleyInterFace(context,BaseStrVolleyInterFace.mListener,BaseStrVolleyInterFace.mErrorListener) {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    String resulttext = jsonObject.getString("resulttext");
                    if(result.equals("1001")){
                        listener.onSuccess(resulttext);
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
    public void confirmSelfBuy(String url, String goodsList, String addressid, final OnInventorySelfListener listener) {
        final String TAG = url;
        Map<String,String> params = new HashMap<String,String>();
        params.put("openid", MyDate.getMyVid());
        params.put("data",goodsList);
        params.put("addressid", addressid);
        BaseVolleyRequest.StringRequestPost(context, url, TAG, params, new BaseStrVolleyInterFace(context,BaseStrVolleyInterFace.mListener,BaseStrVolleyInterFace.mErrorListener) {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    String resulttext = jsonObject.getString("resulttext");
                    if(result.equals("1001")){
                        String orderid = jsonObject.getString("id");
                        String money = jsonObject.getString("freight");
                        int ispay = jsonObject.getInt("ispay");
                        String ordersn = jsonObject.getString("sn");
                        listener.onConfirmSuccess(orderid,ordersn,ispay,money);
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
