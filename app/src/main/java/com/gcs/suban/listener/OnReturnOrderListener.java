package com.gcs.suban.listener;

import com.gcs.suban.bean.ReturnBean;

public interface OnReturnOrderListener {
		/**
	     * 获取列表成功时回调
	     *
	     * @param 
	     */
	    void onInfo(ReturnBean bean);
	    /**
	     * 确认请求成功时回调
	     *
	     * @param 
	     */
	    void onConfirm(String resulttext);
	    /**
	     * 失败时回调
	     */
	    void onError(String error);
}
