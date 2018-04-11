package com.gcs.suban.eventbus;

public class StoreEvent {
	private String type;
	private String msg;

	public StoreEvent(String type, String msg) {
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
