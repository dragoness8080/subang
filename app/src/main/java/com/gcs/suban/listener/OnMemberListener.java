package com.gcs.suban.listener;

public interface OnMemberListener {
	  /**
     * �ɹ�ʱ�ص�
     *
     * @param 
     */
    void onSuccess(String json);
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);
}
