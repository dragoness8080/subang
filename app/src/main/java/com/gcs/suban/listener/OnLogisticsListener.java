package com.gcs.suban.listener;

import java.util.List;

import com.gcs.suban.bean.LogisticsBean;

public interface OnLogisticsListener {
	 /**
     * �ɹ�ʱ�ص�
     *
     * @param 
     */
    void onSuccess(List<LogisticsBean> mListType);
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);

}
