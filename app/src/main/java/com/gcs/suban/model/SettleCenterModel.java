package com.gcs.suban.model;

import com.gcs.suban.listener.OnSettleCenterListener;

public interface SettleCenterModel {
    void getSettled(String url, OnSettleCenterListener listener);
    void balance(String url, OnSettleCenterListener listener);
}
