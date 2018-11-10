package com.gcs.suban.listener;

import com.gcs.suban.bean.RewardBean;

import java.util.List;

public interface OnRewardListener {
    void onError(String error);
    void onSuccess(List<RewardBean> mList, String page);
}
