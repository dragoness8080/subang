package com.gcs.suban.listener;

public interface OnSelfListener {
    void onPaySuccess(String msg);
    void onFinfishSuccess(String msg);
    void onError(String error);
    void onCancelSuccess(String msg);
}
