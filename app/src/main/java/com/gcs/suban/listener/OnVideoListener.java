package com.gcs.suban.listener;

import java.util.List;

import com.gcs.suban.bean.HlvSimpleBean;

public interface OnVideoListener {
	 /**
     * 成功时回调
     *
     * @param 
     */
    void onSuccess(List<HlvSimpleBean> mListType,String page);
    /**
     * 成功时回调
     *
     * @param 
     */
    void onSetSort(List<HlvSimpleBean> mListType);
    /**
     * 失败时回调
     */
    void onError(String error);
}
