package com.gcs.suban.listener;

public interface OnPayListener {
	  /**
     * 成功时回调
     *
     * @param 
     */
    void onPay(String resulttext);
    /**
     * 失败时回调
     */
    void onError(String error);
}
