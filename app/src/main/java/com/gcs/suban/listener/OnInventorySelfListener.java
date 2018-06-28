package com.gcs.suban.listener;

import com.gcs.suban.bean.AddressBean;
import com.gcs.suban.bean.InventorySelfBuyBean;
import com.gcs.suban.bean.OrderBean;
import com.gcs.suban.bean.ShopDataBean;

import java.util.List;

public interface OnInventorySelfListener {
    void onError(String error);
    void onSuccess(List<InventorySelfBuyBean> mListType, String page);
    void onSaveSuccess(OrderBean orderBean, AddressBean addressBean, List<ShopDataBean> mListType);
    void onConfirmSuccess(String orderid,String ordersn,int ispay, String money);
}
