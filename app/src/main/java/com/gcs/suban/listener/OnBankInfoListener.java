package com.gcs.suban.listener;

import com.gcs.suban.bean.BankBean;

public interface OnBankInfoListener {
	/**
     * �ɹ�ʱ�ص�
     *
     * @param 
     */
    void onSuccess(BankBean bean);
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);
}
