package com.gcs.suban.bean;

public class GoodsBean {

    private String imgurl;
    private int goodsid;
    private String name;
    private double priceold;
    private double pricenow;

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public int getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(int goodsid) {
        this.goodsid = goodsid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPriceold() {
        return priceold;
    }

    public void setPriceold(double priceold) {
        this.priceold = priceold;
    }

    public double getPricenow() {
        return pricenow;
    }

    public void setPricenow(double pricenow) {
        this.pricenow = pricenow;
    }
}
