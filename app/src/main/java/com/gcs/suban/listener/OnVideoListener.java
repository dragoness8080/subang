package com.gcs.suban.listener;

import java.util.List;

import com.gcs.suban.bean.HlvSimpleBean;

public interface OnVideoListener {
	 /**
     * �ɹ�ʱ�ص�
     *
     * @param 
     */
    void onSuccess(List<HlvSimpleBean> mListType,String page);
    /**
     * �ɹ�ʱ�ص�
     *
     * @param 
     */
    void onSetSort(List<HlvSimpleBean> mListType);
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);
}
