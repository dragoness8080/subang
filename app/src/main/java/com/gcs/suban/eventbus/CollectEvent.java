package com.gcs.suban.eventbus;

public class CollectEvent {
	private String type;
	private String msg;

	public CollectEvent(String type, String msg) {
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
