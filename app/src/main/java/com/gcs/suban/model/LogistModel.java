package com.gcs.suban.model;

import com.gcs.suban.listener.OnLogisticsListener;

public interface LogistModel {
	void getInfo(String url,String orderid,OnLogisticsListener listener);
}
