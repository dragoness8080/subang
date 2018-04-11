package com.gcs.suban.model;

import com.gcs.suban.bean.OrderBean;
import com.gcs.suban.listener.OnConfirmListener;

public interface ConfirmModel {
	 void getInfo(String url,OrderBean bean,OnConfirmListener listener);
	 
	 void confirm(String url,OrderBean bean,OnConfirmListener listener);
}
