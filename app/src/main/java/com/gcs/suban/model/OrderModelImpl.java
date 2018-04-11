package com.gcs.suban.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
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
import com.gcs.suban.listener.OnDetailListener;
import com.gcs.suban.listener.OnOrderHelperListener;
import com.gcs.suban.listener.OnOrderListListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;
import com.google.gson.Gson;

public class OrderModelImpl implements OrderModel {

	private Context context = app.getContext();
	private List<OrderBean> mListType;
	private String page = null;

	private Gson gson;

	private OrderBean orderBean;
	private ShopDataBean shopDataBean;
	private AddressBean addressBean;

	@Override
	public void getList(String url, String status, String mPage,
			final OnOrderListListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url + status;
		Map<String, String> params = new HashMap<String, String>();
		params.put("openid", MyDate.getMyVid());
		params.put("page", mPage);
		params.put("status", status);
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
							page = jsonObject.getString("page");
							if (result.equals("1001")) {
								String isnull = jsonObject.getString("isnull");
								if (isnull.equals("0")) {
									JSONArray jsonArray;
									jsonArray = jsonObject.getJSONArray("data");
									mListType = new ArrayList<OrderBean>();
									for (int i = 0; i < jsonArray.length(); i++) {
										JSONObject jsonObjectSon = (JSONObject) jsonArray
												.opt(i);
										OrderBean bean = new OrderBean();
										bean.orderid = jsonObjectSon
												.getString("orderid");
										bean.refundid = jsonObjectSon
												.getInt("refundid");
										bean.status = jsonObjectSon
												.getInt("status");
										bean.statusname = jsonObjectSon
												.getString("statusname");
										bean.ordersn = jsonObjectSon
												.getString("ordersn");
										bean.goods = jsonObjectSon
												.getString("goods");
										bean.dispatchprice = jsonObjectSon
												.getString("dispatchprice");
										bean.couponprice = jsonObjectSon
												.getString("couponprice");
										bean.shopphone = jsonObjectSon
												.getString("shopphone");
										bean.iscomment = jsonObjectSon
												.getInt("iscomment");
										bean.refundstate = jsonObjectSon
												.getInt("refundstate");
										bean.goodsprice = jsonObjectSon
												.getDouble("goodsprice");
										bean.price = jsonObjectSon
												.getDouble("price");
										bean.credit2 = jsonObject
												.getDouble("credit2");
										mListType.add(bean);
									}
								} else {
									mListType = null;
								}
								listener.onSuccess(mListType, page);
							}

							else {
								String resulttext = jsonObject
										.getString("resulttext");
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

	@Override
	public void cancelOrder(String url, String orderid,
			final OnOrderHelperListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url + orderid;
		Map<String, String> params = new HashMap<String, String>();
		params.put("openid", MyDate.getMyVid());
		params.put("orderid", orderid);
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
								listener.onCancelOrder(resulttext);
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

	@Override
	public void confirmOrder(String url, String orderid,
			final OnOrderHelperListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url + orderid;
		Map<String, String> params = new HashMap<String, String>();
		params.put("openid", MyDate.getMyVid());
		params.put("orderid", orderid);
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
								listener.onConfirmOrder(resulttext);
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

	@Override
	public void getDetail(String url, OrderBean bean,
			final OnDetailListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url;
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderid", bean.orderid);
		params.put("ordersn", bean.ordersn);
		params.put("openid", MyDate.getMyVid());
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
								String order = jsonObject.getString("order");
								JSONObject orderJsonObject = new JSONObject(
										order);
								gson = new Gson();
								orderBean = gson.fromJson(
										orderJsonObject.toString(),
										OrderBean.class);
								List<ShopDataBean> mListType = new ArrayList<ShopDataBean>();
								JSONArray jsonArray = jsonObject
										.getJSONArray("goods");
								mListType = new ArrayList<ShopDataBean>();
								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject jsonObjectSon = (JSONObject) jsonArray
											.opt(i);
									gson = new Gson();
									shopDataBean = gson.fromJson(
											jsonObjectSon.toString(),
											ShopDataBean.class);
									mListType.add(shopDataBean);
								}
								String address = jsonObject
										.getString("address");
								JSONObject addressJsonObject = new JSONObject(
										address);
								gson = new Gson();
								addressBean = gson.fromJson(
										addressJsonObject.toString(),
										AddressBean.class);
								listener.onDetailSuccess(orderBean,
										addressBean, mListType);
							} else {
								listener.onDetailError(resulttext);
							}
						} catch (JSONException e) {
							// TODO Auto-generated cauotch block
							e.printStackTrace();
							listener.onDetailError(Url.jsonError);
						}
					}

					@Override
					public void onError(VolleyError error) {
						// TODO Auto-generated method stub
						Log.i(TAG, "POST请求失败-->" + error.toString());
						listener.onDetailError(Url.networkError);
					}
				});
	}

	@Override
	public void cancelRefund(String url, String orderid,
			final OnOrderHelperListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url + orderid;
		Map<String, String> params = new HashMap<String, String>();
		params.put("openid", MyDate.getMyVid());
		params.put("orderid", orderid);
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
								listener.onCancelRefund(resulttext);
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

	@Override
	public void deleteOrder(String url, String orderid,
			final OnOrderHelperListener listener) {
		// TODO Auto-generated method stub
		final String TAG = url + orderid;
		Map<String, String> params = new HashMap<String, String>();
		params.put("openid", MyDate.getMyVid());
		params.put("orderid", orderid);
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
								listener.onDeleteOrder(resulttext);
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
