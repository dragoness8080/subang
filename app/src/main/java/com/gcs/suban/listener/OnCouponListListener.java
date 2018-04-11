package com.gcs.suban.listener;

import java.util.List;

import com.gcs.suban.bean.CouponBean;


public interface OnCouponListListener {
	 /**
     * 成功时回调
     *
     * @param 
     */
    void onSuccess(List<CouponBean> mListType,String page);
    /**
     * 失败时回调
     */
    void onError(String error);
}
