package com.gcs.suban.bean;

public class WeixinBean {
	public String openid;//΢��id
	public String access_token;//����
	public String unionid;//Ψһ��ʶ
	public String sex;//�Ա�
	public String nickname;//����
	public String headimgurl;//ͷ��

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
