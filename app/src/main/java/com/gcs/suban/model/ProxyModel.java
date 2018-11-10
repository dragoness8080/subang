package com.gcs.suban.model;

import com.gcs.suban.listener.OnBaseListener;

public interface ProxyModel {
    void getProxy(String url, OnBaseListener listener);
}
