package com.gcs.suban.listener;

public interface OnPayStockListener {
    void onError(String error);
    void onPayCoverSuccess(String msg);
}
