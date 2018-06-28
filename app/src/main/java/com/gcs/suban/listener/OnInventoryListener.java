package com.gcs.suban.listener;

import com.gcs.suban.bean.InventoryGradeBean;
import com.gcs.suban.bean.InventoryMemberBean;

import java.util.List;

public interface OnInventoryListener {
    /**
     * ���˵��ͼƬ��ȡ�ɹ�
     * @param url
     */
    void OnImgSuccess(String url);

    void OnError(String error);

    void OnGradeSuccess(List<InventoryGradeBean> beanList);

    void OnSuccess(String msg, String ordersn, double money, int ispay);

    void OnApplySuccess(InventoryMemberBean bean);
}
