package com.gcs.suban.listener;

import com.gcs.suban.bean.StoreBean;

public interface OnStoreListener {
	/**
	 * 成功时回调
	 * 
	 * @param
	 */
	void onSuccess(StoreBean bean);

	/**
	 * 头像成功回调
	 */
	void onLogo();

	/**
	 * 店铺简介成功回调
	 */
	void ondesc();

	/**
	 * 店招成功回调
	 */
	void onImg();

	/**
	 * 失败时回调
	 */
	void onError(String error);
}
