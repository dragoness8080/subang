package com.gcs.suban.listener;

public interface OnOrderHelperListener {
	  /**
     * ȡ���˿�����ɹ�ʱ�ص�
     *
     * @param 
     */
    void onCancelRefund(String resulttext);
	  /**
     * ȡ�������ɹ�ʱ�ص�
     *
     * @param 
     */
    void onCancelOrder(String resulttext);
    /**
     * ȡ�������ɹ�ʱ�ص�
     *
     * @param 
     */
    void onDeleteOrder(String resulttext);

	  /**
     * ȷ���ջ��ɹ�ʱ�ص�
     *
     * @param 
     */
    void onConfirmOrder(String resulttext);
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);
}
