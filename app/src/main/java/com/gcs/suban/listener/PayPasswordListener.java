package com.gcs.suban.listener;


public interface PayPasswordListener {
	/**
     * 成功时回调
     *
     * @param 
     */
    void onSuccess(String resulttext);
    /**
     * 失败时回调
     */
    void onError(String error);
}
