package com.gcs.suban.listener;

import com.gcs.suban.bean.ReturnBean;

public interface OnReturnOrderListener {
		/**
	     * ��ȡ�б�ɹ�ʱ�ص�
	     *
	     * @param 
	     */
	    void onInfo(ReturnBean bean);
	    /**
	     * ȷ������ɹ�ʱ�ص�
	     *
	     * @param 
	     */
	    void onConfirm(String resulttext);
	    /**
	     * ʧ��ʱ�ص�
	     */
	    void onError(String error);
}
