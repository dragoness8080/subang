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

    /**
     * ǩ���ɹ��ص�
     * @param flag
     */
    void onSignSuccess(int flag,String leaf);

    /**
     * �һ��ɹ��ص�
     * @param leaf
     * @param credit
     */
    void onExchangeSuccess(String leaf,String credit);
}
