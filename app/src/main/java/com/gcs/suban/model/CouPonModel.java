package com.gcs.suban.model;

import com.gcs.suban.listener.OnCouponListListener;
import com.gcs.suban.listener.OnCouponListener;

public interface CouPonModel {
	void getInfo(String url,OnCouponListener listener);
	
	void getList(String url,String status,String page,OnCouponListListener listener);
}
