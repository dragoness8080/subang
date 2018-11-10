package com.gcs.suban.bean;

public class RewardBean {
    private String rankStr;
    private String thumb;
    private String title;
    private int status;
    private String statusStr;
    private String createtime;
    private String exchangetime;

    public String getRankStr() {
        return rankStr;
    }

    public void setRankStr(String rankStr) {
        this.rankStr = rankStr;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusStr() {
        if(status == 1){
            statusStr = "ÒÑ¶Ò»»";
        }else if(status == 0){
            statusStr = "Î´¶Ò»»";
        }
        return statusStr;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getExchangetime() {
        return exchangetime;
    }

    public void setExchangetime(String exchangetime) {
        this.exchangetime = exchangetime;
    }
}
