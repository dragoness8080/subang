package com.gcs.suban.model;

import com.gcs.suban.listener.OnMemberListener;

public interface MemberModel {
	void getInfo(String url,OnMemberListener listener);
	void signIn(String url,OnMemberListener listener);
	void exchange(String url,OnMemberListener listener);
}
