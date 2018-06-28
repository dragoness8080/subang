package com.gcs.suban.model;

import com.gcs.suban.listener.OnBaseListener;
import com.gcs.suban.listener.OnInventorySelfListener;

public interface InventorySelfBuyModel {
    void saveSelfBuy(String url,String goodsList,String addressid, OnInventorySelfListener listener);
    void getSelfBuy(String url,int status,String page,OnInventorySelfListener listener);
    void cancelSelf(String url, int id, OnBaseListener listener);
    void confirmSelfBuy(String url,String goodsList,String addressid,OnInventorySelfListener listener);
}
