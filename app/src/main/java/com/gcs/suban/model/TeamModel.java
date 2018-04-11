package com.gcs.suban.model;

import com.gcs.suban.listener.OnTeamListener;

public interface TeamModel {
	void getInfo(String url,String page,OnTeamListener listener);

	void searchInfo(String url,String type, String content,String page, OnTeamListener listener);
}
