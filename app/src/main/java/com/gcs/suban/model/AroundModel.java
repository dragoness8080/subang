package com.gcs.suban.model;

import com.gcs.suban.listener.OnAroundListener;

public interface AroundModel {
	/**
	 * �ϴ���ǰ��γ��
	 */
	void postLocation(String url,String lat,String lng, OnAroundListener listener);
	/**
	 * ��ȡ�ܱ��б�
	 */
	void getAround(String url, OnAroundListener listener);
}
