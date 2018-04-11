package com.gcs.suban.listener;

public interface OnMemberListener {
	  /**
     * 成功时回调
     *
     * @param 
     */
    void onSuccess(String json);
    /**
     * 失败时回调
     */
    void onError(String error);
}
