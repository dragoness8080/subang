package com.gcs.suban.model;

import com.gcs.suban.listener.OnPayApplyListener;
import com.gcs.suban.listener.OnPayStockListener;

public interface PayInventoryModel {
    void onPayCover(String url, int id, OnPayStockListener listener);
    void onPayApply(String url, String sn, OnPayApplyListener listener);
}
