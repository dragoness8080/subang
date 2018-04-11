package com.gcs.suban.listener;

import com.gcs.suban.bean.WeixinBean;

public interface OnWeixinListener {
	/**
     * 获取微信令牌成功
     *
     * @param 
     */
    void onToken(WeixinBean bean);
	/**
     * 获取用户微信个人信息成功
     *
     * @param 
     */
    void onInfo(WeixinBean bean);
	/**
     * 登录成功
     *
     * @param 
     */
    void onLogin(String openid,String isboundphone,String id);
    /**
     * 失败时回调
     */
    void onError(String error);
}
