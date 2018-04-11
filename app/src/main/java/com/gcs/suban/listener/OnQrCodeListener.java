package com.gcs.suban.listener;

import com.gcs.suban.bean.CodeBean;

public interface OnQrCodeListener {
	 /**
     * 二维码信息获取成功
     *
     * @param 
     */
    void onQrCode(CodeBean bean);
    /**
     * 失败时回调
     */
    void onError(String error);
}
