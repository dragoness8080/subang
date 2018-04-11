package com.gcs.suban.listener;

import com.gcs.suban.bean.AroundBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/15 0015.
 */

public interface OnAroundListener {
    /**
     * 上传经纬度成功时回调
     */
    void onSuccess();
    /**
     * 失败时回调
     */
    void onError(String error);

    /**
     * 获取身边素邦人列表
     */
    void getAroundData(List<AroundBean> mListType);
}
