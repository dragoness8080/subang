package com.gcs.suban.listener;

import com.gcs.suban.bean.InventoryGoodsBean;

import java.util.List;

public interface OnInventoryGoodsListener {

    void onError(String msg);
    void onGoodsList(List<InventoryGoodsBean> list);
    void onGoodsInfo(InventoryGoodsBean bean);
}
