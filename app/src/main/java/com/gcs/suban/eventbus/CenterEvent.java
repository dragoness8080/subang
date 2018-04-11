package com.gcs.suban.eventbus;

public class CenterEvent  {
	private String type;
	private String msg;

	public CenterEvent(String type, String msg) {
		// TODO Auto-generated constructor stub
		this.type = type;
		this.msg = msg;
	}

	public String getType() {
		return type;
	}

	public String getMsg() {
		return msg;
	}
}