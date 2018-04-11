package com.gcs.suban.listener;

import java.util.List;

import com.gcs.suban.bean.CollectBean;

public interface OnCollectListener {
    /**
     * 成功时回调
     *
     * @param 
     */
    void onSuccess(List<CollectBean> mListType,String page);
    /**
     * 取消收藏成功时回调
     *
     * @param 
     */
    void onCollect(String resulttext);
    /**
     * 失败时回调
     */
    void onError(String error);

}
