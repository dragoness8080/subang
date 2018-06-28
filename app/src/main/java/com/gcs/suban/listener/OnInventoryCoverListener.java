package com.gcs.suban.listener;

public interface OnInventoryCoverListener {
    void OnError(String error);
    void OnSuccess(String money,String sn,String id,int ispay);
}
