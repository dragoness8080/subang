package com.gcs.suban.bean;

public class WeixinBean {
	public String openid;//微信id
	public String access_token;//令牌
	public String unionid;//唯一标识
	public String sex;//性别
	public String nickname;//姓名
	public String headimgurl;//头像

	public WeixinBean(String openid, String access_token, String unionid, String sex, String nickname,
			String img,String headimgurl) {
		super();
		this.openid = openid;
		this.access_token = access_token;
		this.unionid = unionid;
		this.sex = sex;
		this.nickname = nickname;
		this.headimgurl = headimgurl;
	}
}
