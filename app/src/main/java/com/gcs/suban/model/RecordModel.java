package com.gcs.suban.model;

import com.gcs.suban.listener.OnRecordListener;
import com.gcs.suban.listener.OnSettledRecordListener;

public interface RecordModel {
	void getInfo(String url,String mPage,OnRecordListener listener);
	void getSettleList(String url, String mPage, OnSettledRecordListener listener);
}
