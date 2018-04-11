package com.gcs.suban.bean;

public class CollectBean {
	private int shopId; // 商品ID
	private int id; // 特殊ID
	private String shopPicture; // 商品图片资源ID
	private String shopName; // 商品名称
	private float shopPrice; // 商品现价
	private float shopPrice2; // 商品原价
	private boolean isChoosed; // 商品是否在购物车中被选中

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public float getShopPrice2() {
		return shopPrice2;
	}

	public void setShopPrice2(float shopPrice2) {
		this.shopPrice2 = shopPrice2;
	}

	public boolean isChoosed() {
		return isChoosed;
	}

	public void setChoosed(boolean isChoosed) {
		this.isChoosed = isChoosed;
	}
}
