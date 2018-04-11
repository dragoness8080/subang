package com.gcs.suban.listener;

import com.gcs.suban.bean.CenterBean;

public interface OnCenterListener {
    /**
     * 成功时回调
     *
     * @param 
     */
    void onSuccess(CenterBean bean);
    /**
     * 失败时回调
     */
    void onError(String error);
}
