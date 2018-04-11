package com.gcs.suban.model;

import com.gcs.suban.listener.OnShopDetailListener;

public interface ShopDetailModel {
	
	/**
	 * 获取商品详情
	 */
	void getInfo(String url,String goodsid, OnShopDetailListener listener);
	
	
	/**
	 * 收藏
	 */
	void collect(String url,String goodsid, OnShopDetailListener listener);
	
	
	/**
	 * 取消收藏 
	 */
	void cancle(String url,String goodsid, OnShopDetailListener listener);
}
