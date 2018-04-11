package com.gcs.suban.model;

import com.gcs.suban.listener.OnRecordListener;

public interface RecordModel {
	void getInfo(String url,String mPage,OnRecordListener listener);
}
