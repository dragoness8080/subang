package com.gcs.suban.model;

import com.gcs.suban.listener.OnCollectListener;

public interface CollectModel {
	void getInfo(String url,String mPage,OnCollectListener listener);
	
	void cancel(String url,String id,OnCollectListener listener);
}
