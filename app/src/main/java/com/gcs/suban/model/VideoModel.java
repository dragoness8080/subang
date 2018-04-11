package com.gcs.suban.model;

import com.gcs.suban.listener.OnVideoListener;

public interface VideoModel {
	void getListInfo(String url,OnVideoListener listener);
	
	void getSort(String url,OnVideoListener listener);
}
