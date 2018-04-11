package com.gcs.suban.listener;

public interface OnNicknameListener {
	   /**
     * 成功时回调
     *
     * @param 
     */
    void onSuccess(String result);
    /**
     * 失败时回调
     */
    void onError(String error);
}
