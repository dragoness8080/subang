package com.gcs.suban.listener;

public interface OnOrderListener {
	 /**
     * 获取订单信息成功时回调
     *
     * @param 
     */
    void onInfo(String data);
    /**
     * 失败时回调
     */
    void onError(String error);
}
