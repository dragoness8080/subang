package com.gcs.suban.model;

import com.gcs.suban.bean.BankBean;
import com.gcs.suban.listener.OnBankInfoListener;
import com.gcs.suban.listener.OnBaseListener;

public interface BankModel {
	
	void getInfo(String url,OnBankInfoListener listener);
	
	void setBank(String url,BankBean bean,OnBaseListener listener);
}
