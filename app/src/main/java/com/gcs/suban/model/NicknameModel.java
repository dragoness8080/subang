package com.gcs.suban.model;

import com.gcs.suban.listener.OnNicknameListener;

public interface NicknameModel {
	  void getInfo(String url,String nickname,OnNicknameListener listener);
}
