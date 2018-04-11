package com.gcs.suban.model;

import com.gcs.suban.listener.OnBackstageListener;

public interface BackstageModel {
	void getInfo(String url,OnBackstageListener listener);
}
