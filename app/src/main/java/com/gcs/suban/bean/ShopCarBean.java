package com.gcs.suban.bean;

public class ShopCarBean {
	private int goodsid;				//��ƷID
	private int cartid;				//���ﳵID
	private String shopPicture;		//��ƷͼƬ��ԴID
	private String shopName;		//��Ʒ����
	private float shopPrice;		//��Ʒ����
	private int shopNumber;			//��Ʒ����
	private boolean isChoosed;		//��Ʒ�Ƿ��ڹ��ﳵ�б�ѡ��

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
