package com.gcs.suban.listener;

public interface OnPayListener {
	  /**
     * �ɹ�ʱ�ص�
     *
     * @param 
     */
    void onPay(String resulttext);
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);
}
