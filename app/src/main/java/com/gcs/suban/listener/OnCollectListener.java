package com.gcs.suban.listener;

import java.util.List;

import com.gcs.suban.bean.CollectBean;

public interface OnCollectListener {
    /**
     * �ɹ�ʱ�ص�
     *
     * @param 
     */
    void onSuccess(List<CollectBean> mListType,String page);
    /**
     * ȡ���ղسɹ�ʱ�ص�
     *
     * @param 
     */
    void onCollect(String resulttext);
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);

}
