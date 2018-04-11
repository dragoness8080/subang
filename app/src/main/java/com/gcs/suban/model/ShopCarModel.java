package com.gcs.suban.model;

import com.gcs.suban.listener.OnShopCarListener;

public interface ShopCarModel {
	void getInfo(String url,OnShopCarListener listener);
	
	void setNum(String url,String cartid,String total,OnShopCarListener listener);
	
	void delete(String url,String cartid,OnShopCarListener listener);
}
