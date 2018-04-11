package com.gcs.suban.listener;

public interface OnPhoneListener {
	/**
	 * 获取验证码成功时回调
	 * 
	 * @param
	 */
	void onCode(String result);

	/**
	 * 绑定手机成功时回调
	 * 
	 * @param
	 */
	void onSuccess(String result);

	/**
	 * 失败时回调
	 */
	void onError(String error);
}
