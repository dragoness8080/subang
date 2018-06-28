package com.gcs.suban.model;

import com.gcs.suban.listener.OnInventoryStockListener;

public interface InventoryStockModel {
    void getTotalInfo(String url, OnInventoryStockListener listener);
    void getLogs(String url,String types,String page,OnInventoryStockListener listener);
    void getStock(String url,OnInventoryStockListener listener);
}
