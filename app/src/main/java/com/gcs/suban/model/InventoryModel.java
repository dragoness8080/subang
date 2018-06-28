package com.gcs.suban.model;

import com.gcs.suban.bean.InventoryMemberBean;
import com.gcs.suban.listener.OnInventoryListener;

public interface InventoryModel {
    void getImgUrl(String url, OnInventoryListener listener);
    void getGradeList(String url,OnInventoryListener listener);
    void apply(String url, InventoryMemberBean bean, OnInventoryListener listener);
    void getApply(String url, OnInventoryListener listener);
}
