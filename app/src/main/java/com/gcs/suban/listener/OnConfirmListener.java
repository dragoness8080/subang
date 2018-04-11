package com.gcs.suban.listener;

import java.util.List;

import com.gcs.suban.bean.AddressBean;
import com.gcs.suban.bean.OrderBean;
import com.gcs.suban.bean.ShopDataBean;

public interface OnConfirmListener {
	  /**
     * ��ȡ�б�ɹ�ʱ�ص�
     *
     * @param 
     */
    void onSuccess(OrderBean bean,AddressBean bean2,List<ShopDataBean> mListType);
    /**
     * ȷ������ɹ�ʱ�ص�
     *
     * @param 
     */
    void onConfirm(OrderBean bean);
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);
}
