package com.gcs.suban.listener;

import java.util.List;

import com.gcs.suban.bean.MessageBean;

public interface OnMessageListener {
    /**
     * 成功时回调
     *
     * @param 
     */
    void onSuccess(List<MessageBean> mListType,String page);
    /**
     * 失败时回调
     */
    void onError(String error);
}
