package com.gcs.suban.listener;

public interface OnStorenameListener {
	 /**
     * �ɹ�ʱ�ص�
     *
     * @param 
     */
    void onSuccess();
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);
}
