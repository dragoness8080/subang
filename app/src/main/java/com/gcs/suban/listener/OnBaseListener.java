package com.gcs.suban.listener;

public interface OnBaseListener {
	 /**
     * ��ȡ�б�ɹ�ʱ�ص�
     *
     * @param 
     */
    void onSuccess(String resulttext);
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);
}
