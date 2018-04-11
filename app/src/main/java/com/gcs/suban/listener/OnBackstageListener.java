package com.gcs.suban.listener;

import com.gcs.suban.bean.BackstageBean;

public interface OnBackstageListener {
	/**
     * 成功时回调
     *
     * @param 
     */
    void onBackstage(BackstageBean bean);
    /**
     * 失败时回调
     */
    void onError(String error);
}
