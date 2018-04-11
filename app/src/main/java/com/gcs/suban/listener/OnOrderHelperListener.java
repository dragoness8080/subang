package com.gcs.suban.listener;

public interface OnOrderHelperListener {
	  /**
     * 取消退款申请成功时回调
     *
     * @param 
     */
    void onCancelRefund(String resulttext);
	  /**
     * 取消订单成功时回调
     *
     * @param 
     */
    void onCancelOrder(String resulttext);
    /**
     * 取消订单成功时回调
     *
     * @param 
     */
    void onDeleteOrder(String resulttext);

	  /**
     * 确认收货成功时回调
     *
     * @param 
     */
    void onConfirmOrder(String resulttext);
    /**
     * 失败时回调
     */
    void onError(String error);
}
