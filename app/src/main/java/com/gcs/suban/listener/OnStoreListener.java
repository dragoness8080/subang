package com.gcs.suban.listener;

import com.gcs.suban.bean.StoreBean;

public interface OnStoreListener {
	/**
	 * �ɹ�ʱ�ص�
	 * 
	 * @param
	 */
	void onSuccess(StoreBean bean);

	/**
	 * ͷ��ɹ��ص�
	 */
	void onLogo();

	/**
	 * ���̼��ɹ��ص�
	 */
	void ondesc();

	/**
	 * ���гɹ��ص�
	 */
	void onImg();

	/**
	 * ʧ��ʱ�ص�
	 */
	void onError(String error);
}
