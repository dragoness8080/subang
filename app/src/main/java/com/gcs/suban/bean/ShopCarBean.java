package com.gcs.suban.bean;

public class ShopCarBean {
	private int goodsid;				//商品ID
	private int cartid;				//购物车ID
	private String shopPicture;		//商品图片资源ID
	private String shopName;		//商品名称
	private float shopPrice;		//商品单价
	private int shopNumber;			//商品数量
	private boolean isChoosed;		//商品是否在购物车中被选中

	public int getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(int goodsid) {
		this.goodsid = goodsid;
	}
	public int getCartid() {
		return cartid;
	}
	public void setCartid(int cartid) {
		this.cartid = cartid;
	}
	public String getShopPicture() {
		return shopPicture;
	}
	public void setShopPicture(String shopPicture) {
		this.shopPicture = shopPicture;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public float getShopPrice() {
		return shopPrice;
	}
	public void setShopPrice(float shopPrice) {
		this.shopPrice = shopPrice;
	}
	public int getShopNumber() {
		return shopNumber;
	}
	public void setShopNumber(int shopNumber) {
		this.shopNumber = shopNumber;
	}
	public boolean isChoosed() {
		return isChoosed;
	}
	public void setChoosed(boolean isChoosed) {
		this.isChoosed = isChoosed;
	}
	
}
