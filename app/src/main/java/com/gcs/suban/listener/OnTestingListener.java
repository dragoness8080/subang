package com.gcs.suban.listener;

public interface OnTestingListener {
	  /**
     * 成功时回调
     *
     * @param 
     */
    void onSuccess(String txt);
    /**
     * 失败时回调
     */
    void onError(String error);
}
