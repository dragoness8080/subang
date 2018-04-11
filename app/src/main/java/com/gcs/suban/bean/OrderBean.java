package com.gcs.suban.bean;

import java.util.List;

public class OrderBean {
	public String goodsid; // 商品id
	public String cartid; // 购物车id
	public String optionid; // 规格id
	public String addressid; // 收货地址id
	public String remark; // 备注
	public String dispatchprice; // 运费
	public double price; // 价格  （包含运费）
	public double goodsprice; // 商品价格（不包含运费）
	public String total; // 数量
	public String coupondataid; // 优惠券id
	public String couponprice;// 优惠券价格
	
	
	public String orderid; // 订单id
	public String ordersn; // 订单号
	public int status; // 订单状态
	public String statusname; // 订单状态
	public String goods; //订单信息列表
	public int refundstate;
	public int refundid;
	public String createtime;
	
	public String ordercommission;
	
	
	public double credit2; // 余额
	public String paytype;//付款类型
	
	public String nickname;
	public String level;
	
	public int iscomment;//评论次数
	
	public String shopphone;//商家号码 
	
	public String goodstitle; // 商品名
	public String time; // 商品交易时间
	public String refundmoney; // 退款金额
	
	public List<ShopDataBean> mListType;

}
