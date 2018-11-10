package com.gcs.suban.listener;

import com.gcs.suban.bean.LotteryBean;

import java.util.List;

public interface OnLotteryListener {
    void onGetPrizeSuccess(List<LotteryBean> mList,int times);
    void onError(String error);
    void onGetRewardSuccess(int index,String reward,String title,String thumb,int times);
}
