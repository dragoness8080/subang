package com.gcs.suban.model;

import com.gcs.suban.listener.OnBaseListener;

public interface RechargeModel {
	void getInfo(String url,String money,OnBaseListener listener);
}
