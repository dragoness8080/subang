package com.gcs.suban.listener;

public interface OnPayApplyListener {
    void onError(String error);
    void onPayApplySuccess(String msg);
}
