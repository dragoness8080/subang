package com.gcs.suban.listener;

import com.gcs.suban.bean.ShopDetailBean;

public interface OnShopDetailListener {
	/**
	 * 成功时回调
	 * 
	 * @param
	 */
	void onSuccess(ShopDetailBean bean);

	/**
	 * 收藏成功时回调
	 */
	void onCollect();
	
	/**
	 * 取消收藏成功时回调
	 */
	void onCancel();
	
	/**
	 * 失败时回调
	 */
	void onError(String error);

}
