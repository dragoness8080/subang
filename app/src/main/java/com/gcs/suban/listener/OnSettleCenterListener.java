package com.gcs.suban.listener;

public interface OnSettleCenterListener {
    void onError(String err);
    void onSuccess(String payed,String examine,String waitpay,String apply,String total);
    void onBalanceSuccess(String str);
}
