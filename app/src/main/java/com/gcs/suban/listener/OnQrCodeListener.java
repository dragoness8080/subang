package com.gcs.suban.listener;

import com.gcs.suban.bean.CodeBean;

public interface OnQrCodeListener {
	 /**
     * ��ά����Ϣ��ȡ�ɹ�
     *
     * @param 
     */
    void onQrCode(CodeBean bean);
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);
}
