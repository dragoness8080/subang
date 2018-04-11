package com.gcs.suban.listener;

import java.util.List;

import com.gcs.suban.bean.AttrsBean;
import com.gcs.suban.bean.ShopDataBean;

public interface OnAttrListener {
	  /**
     * 成功时回调
     *
     * @param 
     */
    void onSuccess(List<AttrsBean> mListType,ShopDataBean  bean);

	  /**
     * 加入购物车成功时回调
     *
     * @param 
     */
    void onAddCar();
	  /**
     * 加入购物车成功时回调
     *
     * @param 
     */
    void onValue(List<ShopDataBean> mListType);
    /**
     * 失败时回调
     */
    void onError(String error);
}
