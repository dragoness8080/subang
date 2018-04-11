package com.gcs.suban.model;

import com.gcs.suban.listener.OnBaseListener;

public interface WithdrawModel {
	void onWirhdraw(String url,String money, OnBaseListener listener);
	
	void onCommissionWirhdraw(String url,String type, OnBaseListener listener);
}
