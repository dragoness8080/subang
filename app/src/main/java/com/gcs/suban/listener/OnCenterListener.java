package com.gcs.suban.listener;

import com.gcs.suban.bean.CenterBean;

public interface OnCenterListener {
    /**
     * �ɹ�ʱ�ص�
     *
     * @param 
     */
    void onSuccess(CenterBean bean);
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);
}
