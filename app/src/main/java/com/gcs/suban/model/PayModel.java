package com.gcs.suban.model;

import com.gcs.suban.bean.OrderBean;
import com.gcs.suban.listener.OnBaseListener;
import com.gcs.suban.listener.OnPayListener;

public interface PayModel {
	void onPay(String url,OrderBean bean,OnPayListener listener);
	
	void onReset(String url,String orderid,OnBaseListener listener);
}
