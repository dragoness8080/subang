package com.gcs.suban.model;

import com.gcs.suban.listener.OnPersonListener;
import com.gcs.suban.listener.OnPhoneListener;
/**
 * 个人资料
 */
public interface PersonModel {
	/**
	 * 获取个人详情
	 */
	void getInfo(String url, OnPersonListener listener);
	/**
	 * 获取手机验证码 
	 */
	void getCode(String url,OnPhoneListener listener);
	/**
	 * 设置头像
	 */
	void setLogo(String url,String file,OnPersonListener listener);
	/**
	 * 设置性别
	 */
	void setSex(String url,String gender,OnPersonListener listener);
	/**
	 * 设置性别
	 */
	void setPhone(String url,String mobile,String code, OnPhoneListener listener);
	/**
	 *  忘记密码  设置密码
	 */
	void setPwd(String url,String mobile,String code,String newpwd, OnPhoneListener listener);
}
