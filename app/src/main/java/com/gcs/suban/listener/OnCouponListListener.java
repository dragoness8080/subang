package com.gcs.suban.listener;

import java.util.List;

import com.gcs.suban.bean.CouponBean;


public interface OnCouponListListener {
	 /**
     * �ɹ�ʱ�ص�
     *
     * @param 
     */
    void onSuccess(List<CouponBean> mListType,String page);
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);
}
