package com.gcs.suban.listener;

import java.util.List;

import com.gcs.suban.bean.RecordBean;

public interface OnRecordListener {
	   /**
     * �ɹ�ʱ�ص�
     *
     * @param 
     */
    void onSuccess(List<RecordBean> mListType,String page);
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);
}
