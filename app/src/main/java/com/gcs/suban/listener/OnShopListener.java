package com.gcs.suban.listener;

public interface OnShopListener {
    /**
     * �ɹ�ʱ�ص�
     *
     * @param 
     */
    void onSuccess(String response);
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);

}
