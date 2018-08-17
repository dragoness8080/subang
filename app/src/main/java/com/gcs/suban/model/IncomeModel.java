package com.gcs.suban.model;

import com.gcs.suban.listener.OnIncomeListener;

public interface IncomeModel {
    void getIncome(String url, String page, OnIncomeListener listener);
}
