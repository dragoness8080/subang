package com.gcs.suban.model;

import com.gcs.suban.listener.OnInventorySettleListener;

public interface InventorySettleModel {
    void getSettled(String url, String date, String page, OnInventorySettleListener listener);
    void getBalance(String url, String date, String page, OnInventorySettleListener listener);
}
