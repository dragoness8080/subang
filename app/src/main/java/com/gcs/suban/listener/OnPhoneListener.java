package com.gcs.suban.listener;

public interface OnPhoneListener {
	/**
	 * ��ȡ��֤��ɹ�ʱ�ص�
	 * 
	 * @param
	 */
	void onCode(String result);

	/**
	 * ���ֻ��ɹ�ʱ�ص�
	 * 
	 * @param
	 */
	void onSuccess(String result);

	/**
	 * ʧ��ʱ�ص�
	 */
	void onError(String error);
}
