package com.gcs.suban.listener;

import com.gcs.suban.bean.InventoryExtrBean;

import java.util.List;

public interface OnInventoryExtrListener {
    void onError(String error);
    void onExtrSuccess(String page, List<InventoryExtrBean> mList);
}
