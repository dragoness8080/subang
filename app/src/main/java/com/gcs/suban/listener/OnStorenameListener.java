package com.gcs.suban.listener;

public interface OnStorenameListener {
	 /**
     * 成功时回调
     *
     * @param 
     */
    void onSuccess();
    /**
     * 失败时回调
     */
    void onError(String error);
}
