package com.gcs.suban.bean;
/**
 * 描述：商品属性  规格父项
 */
public class AttrsBean {
	public String specid;//规格父项id
	public String title;//规格父项名
	public int num;//

	public AttrsBean(String specid, String title, int num) {
		super();
		this.specid = specid;
		this.title = title;
		this.num = num;
	}
}
