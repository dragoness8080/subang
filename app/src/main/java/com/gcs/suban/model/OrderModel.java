package com.gcs.suban.model;

import com.gcs.suban.bean.OrderBean;
import com.gcs.suban.listener.OnDetailListener;
import com.gcs.suban.listener.OnOrderHelperListener;
import com.gcs.suban.listener.OnOrderListListener;

public interface OrderModel {
	/**
	 * ��ȡ�����б�
	 */
	void getList(String url,String status,String mPage,OnOrderListListener listener);
	/**
	 * ��ȡ��������
	 */
	void getDetail(String url,OrderBean bean,OnDetailListener listener);
	/**
	 * ȡ������
	 */
	void cancelOrder(String url,String orderid,OnOrderHelperListener listener);
	/**
	 * ɾ������
	 */
	void deleteOrder(String url,String orderid,OnOrderHelperListener listener);
	/**
	 * ȷ���ջ�
	 */
	void confirmOrder(String url,String orderid,OnOrderHelperListener listener);
	/**
	 * ȡ���˿�����
	 */
	void cancelRefund(String url,String orderid,OnOrderHelperListener listener);
}
