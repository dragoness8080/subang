package com.gcs.suban.listener;

import java.util.List;

import com.gcs.suban.bean.AddressBean;
import com.gcs.suban.bean.OrderBean;
import com.gcs.suban.bean.ShopDataBean;

public interface OnDetailListener {
	  /**
     * 获取列表成功时回调
     *
     * @param 
     */
    void onDetailSuccess(OrderBean bean,AddressBean bean2,List<ShopDataBean> mListType);
    /**
     * 失败时回调
     */
    void onDetailError(String error);
}
