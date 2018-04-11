package com.gcs.suban.listener;

import com.gcs.suban.bean.ShopDetailBean;

public interface OnShopDetailListener {
	/**
	 * �ɹ�ʱ�ص�
	 * 
	 * @param
	 */
	void onSuccess(ShopDetailBean bean);

	/**
	 * �ղسɹ�ʱ�ص�
	 */
	void onCollect();
	
	/**
	 * ȡ���ղسɹ�ʱ�ص�
	 */
	void onCancel();
	
	/**
	 * ʧ��ʱ�ص�
	 */
	void onError(String error);

}
