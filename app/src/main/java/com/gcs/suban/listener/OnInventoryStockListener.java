package com.gcs.suban.listener;

import com.gcs.suban.bean.InventoryLogBean;

import java.util.List;

public interface OnInventoryStockListener {

    void OnTotalSuccess(String surplus,String num,String bail,String balance,String balanceTitle,String gradeTitle,String avatar);
    void OnLogsSuccess(List<InventoryLogBean> list,String page);
    void OnError(String error);
}
