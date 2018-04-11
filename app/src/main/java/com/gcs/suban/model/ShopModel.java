package com.gcs.suban.model;

import com.gcs.suban.listener.OnShopListener;

public interface ShopModel {
    void getInfo(String url,OnShopListener listener);
}
