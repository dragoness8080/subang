package com.gcs.suban.bean;

public class CollectBean {
	private int shopId; // ��ƷID
	private int id; // ����ID
	private String shopPicture; // ��ƷͼƬ��ԴID
	private String shopName; // ��Ʒ����
	private float shopPrice; // ��Ʒ�ּ�
	private float shopPrice2; // ��Ʒԭ��
	private boolean isChoosed; // ��Ʒ�Ƿ��ڹ��ﳵ�б�ѡ��

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
