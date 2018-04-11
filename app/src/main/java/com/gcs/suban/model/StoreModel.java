package com.gcs.suban.model;

import com.gcs.suban.listener.OnStoreListener;
import com.gcs.suban.listener.OnStorenameListener;

public interface StoreModel {
	/**
	 * ��ȡС������
	 */
	void getInfo(String url, OnStoreListener listener);
	/**
	 * ��������
	 */
	void setName(String url,String shopid,String shopname,OnStorenameListener listener);
	
	/**
	 * ����ͷ��
	 */
	void setLogo(String url,String shopid,String file,OnStoreListener listener);
	
	
	/**
	 * ���õ���
	 */
	void setImg(String url,String shopid,String file,OnStoreListener listener);
	
	/**
	 * ���ü��
	 */
	void setDesc(String url,String shopid,String desc,OnStoreListener listener);
}
