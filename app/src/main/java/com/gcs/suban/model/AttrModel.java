package com.gcs.suban.model;

import com.gcs.suban.bean.ShopDataBean;
import com.gcs.suban.listener.OnAttrListener;

public interface AttrModel {
	 void getInfo(String url,OnAttrListener listener);
	 
	 void getValue(String url,OnAttrListener listener);
	 
	 void addCar(String url,ShopDataBean bean,OnAttrListener listener);
	 
}
