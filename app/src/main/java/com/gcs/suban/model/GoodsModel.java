package com.gcs.suban.model;

import com.gcs.suban.listener.OnGoodsListener;

import java.util.Map;

public interface GoodsModel {
    void getGoodsList(String url, String page, String status, Map<String,String> listParam, OnGoodsListener listener);
}
