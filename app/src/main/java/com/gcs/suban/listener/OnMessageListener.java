package com.gcs.suban.listener;

import java.util.List;

import com.gcs.suban.bean.MessageBean;

public interface OnMessageListener {
    /**
     * �ɹ�ʱ�ص�
     *
     * @param 
     */
    void onSuccess(List<MessageBean> mListType,String page);
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);
}
