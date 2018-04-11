package com.gcs.suban.listener;

import com.gcs.suban.bean.CouponBean;

public interface OnCouponListener {
	  /**
     * 获取列表成功时回调
     *
     * @param 
     */
    void onSuccess(CouponBean bean);

    void onError(String error);
}
