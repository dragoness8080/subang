package com.gcs.suban.listener;

import java.util.List;

import com.gcs.suban.bean.AttrsBean;
import com.gcs.suban.bean.ShopDataBean;

public interface OnAttrListener {
	  /**
     * �ɹ�ʱ�ص�
     *
     * @param 
     */
    void onSuccess(List<AttrsBean> mListType,ShopDataBean  bean);

	  /**
     * ���빺�ﳵ�ɹ�ʱ�ص�
     *
     * @param 
     */
    void onAddCar();
	  /**
     * ���빺�ﳵ�ɹ�ʱ�ص�
     *
     * @param 
     */
    void onValue(List<ShopDataBean> mListType);
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);
}
