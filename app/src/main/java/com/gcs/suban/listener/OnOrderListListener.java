package com.gcs.suban.listener;

import java.util.List;

import com.gcs.suban.bean.OrderBean;

public interface OnOrderListListener {
	   /**
     * �ɹ�ʱ�ص�
     *
     * @param 
     */
    void onSuccess(List<OrderBean> mListType,String page);
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);
}
