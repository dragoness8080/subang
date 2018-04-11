package com.gcs.suban.model;

import com.gcs.suban.bean.WeixinBean;
import com.gcs.suban.listener.OnWeixinListener;

public interface WeixinModel {
	
	void getToken(String url,OnWeixinListener listener);
	
	void getInfo(String url,OnWeixinListener listener);
	
	void Login(String url,WeixinBean bean,OnWeixinListener listener);
}
