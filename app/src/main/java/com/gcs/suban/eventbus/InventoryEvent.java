package com.gcs.suban.eventbus;

public class InventoryEvent {
    private String type;
    private String msg;
    public InventoryEvent(String type,String msg){
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
