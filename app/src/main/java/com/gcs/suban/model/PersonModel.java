package com.gcs.suban.model;

import com.gcs.suban.listener.OnPersonListener;
import com.gcs.suban.listener.OnPhoneListener;
/**
 * ��������
 */
public interface PersonModel {
	/**
	 * ��ȡ��������
	 */
	void getInfo(String url, OnPersonListener listener);
	/**
	 * ��ȡ�ֻ���֤�� 
	 */
	void getCode(String url,OnPhoneListener listener);
	/**
	 * ����ͷ��
	 */
	void setLogo(String url,String file,OnPersonListener listener);
	/**
	 * �����Ա�
	 */
	void setSex(String url,String gender,OnPersonListener listener);
	/**
	 * �����Ա�
	 */
	void setPhone(String url,String mobile,String code, OnPhoneListener listener);
	/**
	 *  ��������  ��������
	 */
	void setPwd(String url,String mobile,String code,String newpwd, OnPhoneListener listener);
}
