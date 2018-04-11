package com.gcs.suban.model;

import com.gcs.suban.listener.OnQrCodeListener;

public interface QrCodeModel {
	void getInfo(String url,OnQrCodeListener listener);
}
