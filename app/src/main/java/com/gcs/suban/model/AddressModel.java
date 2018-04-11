package com.gcs.suban.model;

import com.gcs.suban.bean.AddressBean;
import com.gcs.suban.listener.OnAddressListener;
import com.gcs.suban.listener.OnBaseListener;

public interface AddressModel {
	/**
	 * 获取地址列表
	 */
	void getInfo(String url, OnAddressListener listener);
	/**
	 * 添加或修改地址
	 */
	void setAddress(String url,AddressBean bean,OnBaseListener listener);
	/**
	 * 删除地址
	 */
	void delete(String url, String id, OnAddressListener listener);

	/**
	 * 设置默认地址
	 */
	void setDefault(String url, String id, OnAddressListener listener);
}
