package com.gcs.suban.listener;

import java.util.List;

import com.gcs.suban.bean.ShopCarBean;

public interface OnShopCarListener {
    /**
     * �ɹ�ʱ�ص�
     *
     * @param 
     */
    void onSuccess(List<ShopCarBean> mListType);
    /**
     * �ı���Ʒ�����ɹ�
     *
     * @param 
     */
    void onNum();
    /**
     * ɾ����Ʒ�ɹ�
     *
     * @param 
     */
    void onDelete(String resulttext);
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);

}
