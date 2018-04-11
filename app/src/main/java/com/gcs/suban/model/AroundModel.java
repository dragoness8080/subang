package com.gcs.suban.model;

import com.gcs.suban.listener.OnAroundListener;

public interface AroundModel {
	/**
	 * 上传当前经纬度
	 */
	void postLocation(String url,String lat,String lng, OnAroundListener listener);
	/**
	 * 获取周边列表
	 */
	void getAround(String url, OnAroundListener listener);
}
