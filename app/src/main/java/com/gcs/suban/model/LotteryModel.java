package com.gcs.suban.model;

import com.gcs.suban.listener.OnLotteryListener;
import com.gcs.suban.listener.OnRewardListener;

public interface LotteryModel {
    void getPrizeList(String url, OnLotteryListener listener);
    void getReward(String url,OnLotteryListener listener);
    void getRewardList(String url, String page, int status, OnRewardListener listener);
}
