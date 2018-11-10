package com.gcs.suban.eventbus;

public class SettleEvent {
    private String type;
    private String msg;

    public SettleEvent(String type, String msg){
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
