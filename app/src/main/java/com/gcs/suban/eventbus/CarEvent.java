package com.gcs.suban.eventbus;

public class CarEvent {
	private String type;
	private String msg;

	public CarEvent(String type, String msg) {
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
