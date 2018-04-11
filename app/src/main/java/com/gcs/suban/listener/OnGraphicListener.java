package com.gcs.suban.listener;

public interface OnGraphicListener {
	   /**
     * 成功时回调
     *
     * @param 
     */
    void onSuccess(String data);
    /**
     * 失败时回调
     */
    void onError(String error);
}
