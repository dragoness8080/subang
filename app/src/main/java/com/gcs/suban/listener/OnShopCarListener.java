package com.gcs.suban.listener;

import java.util.List;

import com.gcs.suban.bean.ShopCarBean;

public interface OnShopCarListener {
    /**
     * 成功时回调
     *
     * @param 
     */
    void onSuccess(List<ShopCarBean> mListType);
    /**
     * 改变商品数量成功
     *
     * @param 
     */
    void onNum();
    /**
     * 删除商品成功
     *
     * @param 
     */
    void onDelete(String resulttext);
    /**
     * 失败时回调
     */
    void onError(String error);

}
