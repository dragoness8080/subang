package com.gcs.suban.model;

import com.gcs.suban.listener.OnShopDetailListener;

public interface ShopDetailModel {
	
	/**
	 * ��ȡ��Ʒ����
	 */
	void getInfo(String url,String goodsid, OnShopDetailListener listener);
	
	
	/**
	 * �ղ�
	 */
	void collect(String url,String goodsid, OnShopDetailListener listener);
	
	
	/**
	 * ȡ���ղ� 
	 */
	void cancle(String url,String goodsid, OnShopDetailListener listener);
}
