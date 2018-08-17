package com.gcs.suban.listener;

import com.gcs.suban.bean.GoodsBean;

import java.util.List;

public interface OnGoodsListener {
    void onError(String error);
    void onGoodsSuccess(String page, List<GoodsBean> list);
}
