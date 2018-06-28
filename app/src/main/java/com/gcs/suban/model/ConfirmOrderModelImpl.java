package com.gcs.suban.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.gcs.suban.MyDate;
import com.gcs.suban.Url;
import com.gcs.suban.app;
import com.gcs.suban.bean.AddressBean;
import com.gcs.suban.bean.OrderBean;
import com.gcs.suban.bean.ShopDataBean;
import com.gcs.suban.listener.OnConfirmListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;
import com.google.gson.Gson;

public class ConfirmOrderModelImpl implements ConfirmModel {

	private Context context = app.getContext();

	private List<ShopDataBean> mListType;
	private ShopDataBean shopDataBean;
	private AddressBean addressBean;
	private Gson gson;

	@Override
	public void getInfo(String url, final OrderBean bean,
			final OnConfirmListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url;
		Map<String, String> params = new HashMap<String, String>();
		params.put("openid", MyDate.getMyVid());
		if(!bean.addressid.equals("")){
			params.put("addressid", bean.addressid);
			}
		params.put("goodsid", bean.goodsid);
		params.put("optionid", bean.optionid);
		params.put("total", bean.total);
		BaseVolleyRequest.StringRequestPost(context, url, TAG, params,
				new BaseStrVolleyInterFace(context,
						BaseStrVolleyInterFace.mListener,
						BaseStrVolleyInterFace.mErrorListener) {
					@Override
					public void onSuccess(String response) {
						Log.i(TAG, "POST请求成功-->" + response);
						try {
							JSONObject jsonObject = new JSONObject(response);
							String result = jsonObject.getString("result");
							String resulttext = jsonObject
									.getString("resulttext");
							String hasdefaultaddress = jsonObject
									.getString("hasdefaultaddress");

							if (result.equals("1001")) {
								double price = jsonObject.getDouble("price");
								String dispatchprice = jsonObject
										.getString("dispatchprice");
								bean.price = price;
								bean.dispatchprice = dispatchprice;
								mListType = new ArrayList<ShopDataBean>();
								String goods = jsonObject.getString("goods");
								JSONObject jsonObject2 = new JSONObject(goods);
								gson = new Gson();
								shopDataBean = gson.fromJson(
										jsonObject2.toString(),
										ShopDataBean.class);
								mListType.add(shopDataBean);
								String address = jsonObject
										.getString("address");
								if (hasdefaultaddress.equals("0")) {
									addressBean = null;
								} else {
									JSONObject addressJsonObject = new JSONObject(
											address);
									gson = new Gson();
									addressBean = gson.fromJson(
											addressJsonObject.toString(),
											AddressBean.class);
								}
								listener.onSuccess(bean, addressBean, mListType);
							} else {
								listener.onError(resulttext);
							}
						} catch (JSONException e) {
							// TODO Auto-generated cauotch block
							e.printStackTrace();
							listener.onError(Url.jsonError);
						}
					}

					@Override
					public void onError(VolleyError error) {
						// TODO Auto-generated method stub
						Log.i(TAG, "POST请求失败-->" + error.toString());
						listener.onError(Url.networkError);
					}
				});
	}

	@Override
	public void confirm(String url, OrderBean bean,
			final OnConfirmListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url;
		Map<String, String> params = new HashMap<String, String>();
		params.put("openid", MyDate.getMyVid());
		params.put("goodsid", bean.goodsid);
		params.put("total", bean.total);
		params.put("addressid", bean.addressid);
		params.put("optionid", bean.optionid);
		params.put("remark", bean.remark);
		params.put("coupondataid", bean.coupondataid);
		Log.i(TAG,"POST请求成功-->" + params.toString());
		Log.e(TAG, "addressid:" + bean.addressid);
		BaseVolleyRequest.StringRequestPost(context, url, TAG, params,
				new BaseStrVolleyInterFace(context,
						BaseStrVolleyInterFace.mListener,
						BaseStrVolleyInterFace.mErrorListener) {
					@Override
					public void onSuccess(String response) {
						Log.i(TAG, "POST请求成功-->" + response);
						try {
							JSONObject jsonObject = new JSONObject(response);
							String result = jsonObject.getString("result");
							String resulttext = jsonObject
									.getString("resulttext");
							if (result.equals("1001")) {
								OrderBean bean = new OrderBean();
								bean.price = jsonObject.getDouble("price");
								bean.goodsprice = jsonObject.getDouble("goodsprice");
								bean.dispatchprice = jsonObject
										.getString("dispatchprice");
								bean.orderid = jsonObject.getString("orderid");
								bean.ordersn = jsonObject.getString("ordersn");
								bean.credit2 = jsonObject.getDouble("credit2");
								listener.onConfirm(bean);
							} else {
								listener.onError(resulttext);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							listener.onError(Url.jsonError);
						}
					}

					@Override
					public void onError(VolleyError error) {
						// TODO Auto-generated method stub
						Log.i(TAG, "POST请求失败-->" + error.toString());
						listener.onError(Url.networkError);
					}
				});
		
	}

}
