package com.gcs.suban.listener;

import com.gcs.suban.bean.WeixinBean;

public interface OnWeixinListener {
	/**
     * ��ȡ΢�����Ƴɹ�
     *
     * @param 
     */
    void onToken(WeixinBean bean);
	/**
     * ��ȡ�û�΢�Ÿ�����Ϣ�ɹ�
     *
     * @param 
     */
    void onInfo(WeixinBean bean);
	/**
     * ��¼�ɹ�
     *
     * @param 
     */
    void onLogin(String openid,String isboundphone,String id);
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);
}
