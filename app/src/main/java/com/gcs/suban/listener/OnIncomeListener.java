package com.gcs.suban.listener;

import com.gcs.suban.bean.IncomeBean;

import java.util.List;

public interface OnIncomeListener {
    void onError(String error);
    void onIncomeSuccess(String page, List<IncomeBean> incomeBeanList);
}
