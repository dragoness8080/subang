package com.gcs.suban.model;

import com.gcs.suban.bean.OrderBean;
import com.gcs.suban.listener.OnDetailListener;
import com.gcs.suban.listener.OnOrderHelperListener;
import com.gcs.suban.listener.OnOrderListListener;

public interface OrderModel {
	/**
	 * 获取订单列表
	 */
	void getList(String url,String status,String mPage,OnOrderListListener listener);
	/**
	 * 获取订单详情
	 */
	void getDetail(String url,OrderBean bean,OnDetailListener listener);
	/**
	 * 取消订单
	 */
	void cancelOrder(String url,String orderid,OnOrderHelperListener listener);
	/**
	 * 删除订单
	 */
	void deleteOrder(String url,String orderid,OnOrderHelperListener listener);
	/**
	 * 确认收货
	 */
	void confirmOrder(String url,String orderid,OnOrderHelperListener listener);
	/**
	 * 取消退款申请
	 */
	void cancelRefund(String url,String orderid,OnOrderHelperListener listener);
}
