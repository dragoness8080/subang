package com.gcs.suban.model;

import com.gcs.suban.listener.OnInventoryCoverListener;

public interface InventoryCoverModel {
    void setStockCover(String url,String type,String data,OnInventoryCoverListener listener);
}
