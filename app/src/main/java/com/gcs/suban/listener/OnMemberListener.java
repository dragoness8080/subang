package com.gcs.suban.listener;

public interface OnMemberListener {
	  /**
     * 成功时回调
     *
     * @param 
     */
    void onSuccess(String json);
    /**
     * 失败时回调
     */
    void onError(String error);

    /**
     * 签到成功回调
     * @param flag
     */
    void onSignSuccess(int flag,String leaf);

    /**
     * 兑换成功回调
     * @param leaf
     * @param credit
     */
    void onExchangeSuccess(String leaf,String credit);
}
