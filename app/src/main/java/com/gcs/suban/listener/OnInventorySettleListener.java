package com.gcs.suban.listener;

import com.gcs.suban.bean.InventoryLogBean;
import com.gcs.suban.bean.InventoryMemberBean;

import java.util.List;

public interface OnInventorySettleListener {
    void OnError(String error);
    void OnSettledSuccess(String settled, List<InventoryLogBean> list, String page);
    void OnBalanceSuccess(String settled, List<InventoryMemberBean> list, String page);
}
