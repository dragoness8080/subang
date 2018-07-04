package com.gcs.suban.model;

import com.gcs.suban.listener.OnInventorySelfListener;
import com.gcs.suban.listener.OnSelfListener;

public interface InventorySelfBuyModel {
    void saveSelfBuy(String url,String goodsList,String addressid, OnInventorySelfListener listener);
    void getSelfBuy(String url,int status,String page,OnInventorySelfListener listener);
    void cancelSelf(String url, int id, OnSelfListener listener);
    void confirmSelfBuy(String url,String goodsList,String addressid,OnInventorySelfListener listener);
    void paySelf(String url,String id,OnSelfListener listener);
    void finishSelf(String url, String id, OnSelfListener listener);
}
