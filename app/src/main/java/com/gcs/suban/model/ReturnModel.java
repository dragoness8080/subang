package com.gcs.suban.model;

import com.gcs.suban.bean.ReturnBean;
import com.gcs.suban.listener.OnImgUpListener;
import com.gcs.suban.listener.OnReturnOrderListener;

public interface ReturnModel {
	  void getInfo(String url,String orderid,OnReturnOrderListener listener);
	  
	  void onApply(String url,ReturnBean bean,OnReturnOrderListener listener);
	  
	  void UpImg(String url, String file, OnImgUpListener listener);
}
