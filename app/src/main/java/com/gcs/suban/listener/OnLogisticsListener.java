package com.gcs.suban.listener;

import java.util.List;

import com.gcs.suban.bean.LogisticsBean;

public interface OnLogisticsListener {
	 /**
     * 成功时回调
     *
     * @param 
     */
    void onSuccess(List<LogisticsBean> mListType);
    /**
     * 失败时回调
     */
    void onError(String error);

}
