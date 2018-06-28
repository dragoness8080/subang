package com.gcs.suban.model;

import com.gcs.suban.listener.OnInventoryGoodsListener;

public interface InventoryGoodsModel {
    void getGoodsList(String url, OnInventoryGoodsListener listener);
    void getInfo(String url,OnInventoryGoodsListener listener);
}
