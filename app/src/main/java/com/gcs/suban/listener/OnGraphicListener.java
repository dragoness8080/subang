package com.gcs.suban.listener;

public interface OnGraphicListener {
	   /**
     * �ɹ�ʱ�ص�
     *
     * @param 
     */
    void onSuccess(String data);
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);
}
