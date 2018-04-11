package com.gcs.suban.listener;

import java.util.List;

import com.gcs.suban.bean.AddressBean;

public interface OnAddressListener {
    /**
     * ��ȡ�б�ɹ�ʱ�ص�
     *
     * @param 
     */
    void onSuccess(List<AddressBean> list);
    /**
     * ɾ������ɹ�ʱ�ص�
     *
     * @param 
     */
    void onDelete();
    /**
     * Ĭ�ϵ�ַ�޸�����ɹ�ʱ�ص�
     *
     * @param 
     */
    void onDefault();
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);
    
}
