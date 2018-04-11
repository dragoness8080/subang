package com.gcs.suban.model;

import com.gcs.suban.bean.AddressBean;
import com.gcs.suban.listener.OnAddressListener;
import com.gcs.suban.listener.OnBaseListener;

public interface AddressModel {
	/**
	 * ��ȡ��ַ�б�
	 */
	void getInfo(String url, OnAddressListener listener);
	/**
	 * ��ӻ��޸ĵ�ַ
	 */
	void setAddress(String url,AddressBean bean,OnBaseListener listener);
	/**
	 * ɾ����ַ
	 */
	void delete(String url, String id, OnAddressListener listener);

	/**
	 * ����Ĭ�ϵ�ַ
	 */
	void setDefault(String url, String id, OnAddressListener listener);
}
