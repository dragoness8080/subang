package com.gcs.suban.listener;

import com.gcs.suban.bean.BankBean;

public interface OnBankInfoListener {
	/**
     * 成功时回调
     *
     * @param 
     */
    void onSuccess(BankBean bean);
    /**
     * 失败时回调
     */
    void onError(String error);
}
