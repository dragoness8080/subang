package com.gcs.suban.listener;

import java.util.List;

import com.gcs.suban.bean.AddressBean;
import com.gcs.suban.bean.OrderBean;
import com.gcs.suban.bean.ShopDataBean;

public interface OnConfirmListener {
	  /**
     * 获取列表成功时回调
     *
     * @param 
     */
    void onSuccess(OrderBean bean,AddressBean bean2,List<ShopDataBean> mListType);
    /**
     * 确认请求成功时回调
     *
     * @param 
     */
    void onConfirm(OrderBean bean);
    /**
     * 失败时回调
     */
    void onError(String error);
}
