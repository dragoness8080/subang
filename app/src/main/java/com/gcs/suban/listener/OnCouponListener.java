package com.gcs.suban.listener;

import com.gcs.suban.bean.CouponBean;

public interface OnCouponListener {
	  /**
     * ��ȡ�б�ɹ�ʱ�ص�
     *
     * @param 
     */
    void onSuccess(CouponBean bean);

    void onError(String error);
}
