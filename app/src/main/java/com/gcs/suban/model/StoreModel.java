package com.gcs.suban.model;

import com.gcs.suban.listener.OnStoreListener;
import com.gcs.suban.listener.OnStorenameListener;

public interface StoreModel {
	/**
	 * 获取小店详情
	 */
	void getInfo(String url, OnStoreListener listener);
	/**
	 * 设置名称
	 */
	void setName(String url,String shopid,String shopname,OnStorenameListener listener);
	
	/**
	 * 设置头像
	 */
	void setLogo(String url,String shopid,String file,OnStoreListener listener);
	
	
	/**
	 * 设置店招
	 */
	void setImg(String url,String shopid,String file,OnStoreListener listener);
	
	/**
	 * 设置简介
	 */
	void setDesc(String url,String shopid,String desc,OnStoreListener listener);
}
