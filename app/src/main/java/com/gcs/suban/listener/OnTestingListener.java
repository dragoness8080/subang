package com.gcs.suban.listener;

public interface OnTestingListener {
	  /**
     * �ɹ�ʱ�ص�
     *
     * @param 
     */
    void onSuccess(String txt);
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);
}
