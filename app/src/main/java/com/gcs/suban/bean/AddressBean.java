package com.gcs.suban.bean;
/**
 * �������ջ���ַ
 */
public class AddressBean {
	public String addressid;//��ַid
	public String realname;//��ʵ����
	public String mobile;//�ֻ�
	public String province;//ʡ��	
	public String city;//����
	public String area;//����
	public String address;//��ϸ��ַ
	public String isdefault;//�Ƿ�Ĭ�ϵ�ַ

	public AddressBean(String addressid, String realname, String mobile,
			String province,String city,String area,String address,String isdefault) {
		super();
		this.addressid = addressid;
		this.realname = realname;
		this.mobile = mobile;
		this.province = province;
		this.city = city;
		this.area = area;
		this.address = address;
		this.isdefault = isdefault;
	}
}
