package com.gcs.suban.listener;

import java.util.List;

import com.gcs.suban.bean.AddressBean;
import com.gcs.suban.bean.OrderBean;
import com.gcs.suban.bean.ShopDataBean;

public interface OnDetailListener {
	  /**
     * ��ȡ�б�ɹ�ʱ�ص�
     *
     * @param 
     */
    void onDetailSuccess(OrderBean bean,AddressBean bean2,List<ShopDataBean> mListType);
    /**
     * ʧ��ʱ�ص�
     */
    void onDetailError(String error);
}
