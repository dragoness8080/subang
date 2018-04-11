package com.gcs.suban.model;

import com.gcs.suban.listener.OnMessageListener;

public interface MessageModel {
	void getInfo(String url,String page,OnMessageListener listener);
}
