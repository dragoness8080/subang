package com.gcs.suban.listener;

public interface OnShopListener {
    /**
     * 成功时回调
     *
     * @param 
     */
    void onSuccess(String response);
    /**
     * 失败时回调
     */
    void onError(String error);

}
