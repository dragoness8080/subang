package com.gcs.suban.listener;

import com.gcs.suban.bean.BackstageBean;

public interface OnBackstageListener {
	/**
     * �ɹ�ʱ�ص�
     *
     * @param 
     */
    void onBackstage(BackstageBean bean);
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);
}
