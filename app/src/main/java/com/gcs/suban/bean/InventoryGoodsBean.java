package com.gcs.suban.bean;

public class InventoryGoodsBean {
    public int id;
    public int goodsid;
    public String thumb;
    public String title;
    public String money;
    public String settle_money;
    public String num;
    public int own_num;  //已经拥有的数量
    public int type;    //拥有的商品类型0金额类型，1数量类型；金额类型不固定购买数量，数量类型固定购买数量
}
