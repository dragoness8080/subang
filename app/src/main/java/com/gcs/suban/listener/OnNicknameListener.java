package com.gcs.suban.listener;

public interface OnNicknameListener {
	   /**
     * �ɹ�ʱ�ص�
     *
     * @param 
     */
    void onSuccess(String result);
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);
}
