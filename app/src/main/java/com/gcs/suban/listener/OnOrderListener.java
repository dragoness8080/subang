package com.gcs.suban.listener;

public interface OnOrderListener {
	 /**
     * ��ȡ������Ϣ�ɹ�ʱ�ص�
     *
     * @param 
     */
    void onInfo(String data);
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);
}
