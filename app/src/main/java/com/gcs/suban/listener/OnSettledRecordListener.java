package com.gcs.suban.listener;

import com.gcs.suban.bean.SettledBean;

import java.util.List;

public interface OnSettledRecordListener {
    void onSuccess(List<SettledBean> mListType, String page);
    void onError(String error);
}
