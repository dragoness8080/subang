package com.gcs.suban.listener;

import java.util.List;

import com.gcs.suban.bean.AddressBean;

public interface OnAddressListener {
    /**
     * 获取列表成功时回调
     *
     * @param 
     */
    void onSuccess(List<AddressBean> list);
    /**
     * 删除请求成功时回调
     *
     * @param 
     */
    void onDelete();
    /**
     * 默认地址修改请求成功时回调
     *
     * @param 
     */
    void onDefault();
    /**
     * 失败时回调
     */
    void onError(String error);
    
}
