package com.gcs.suban.bean;
/**
 * 描述：收货地址
 */
public class AddressBean {
	public String addressid;//地址id
	public String realname;//真实姓名
	public String mobile;//手机
	public String province;//省份	
	public String city;//城市
	public String area;//地区
	public String address;//详细地址
	public String isdefault;//是否默认地址

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
