package com.gcs.suban.model;

import com.gcs.suban.listener.OnLogisticsListener;

public interface SelfLogistModel {
    void getInfo(String url, String id, OnLogisticsListener listener);
}
