package com.gcs.suban.bean;

public class ShopDataBean {
	public String goodsid;//��Ʒid
	public String optionid;//��Ʒ���id
	public String thumb;// ��ƷͼƬ��Դ
	public String title;// ��Ʒ����
	public String marketprice;// ��Ʒ�ּ�
	public String productprice;// ��Ʒԭ��
	public String salesreal;
	public int total;//��Ʒ����
	
	public String isspec;//�Ƿ��й��

	public String getOptionid() {
		return optionid;
	}

	public void setOptionid(String optionid) {
		this.optionid = optionid;
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

	public String getMarketprice() {
		return marketprice;
	}

	public void setMarketprice(String marketprice) {
		this.marketprice = marketprice;
	}

	public String getProductprice() {
		return productprice;
	}

	public void setProductprice(String productprice) {
		this.productprice = productprice;
	}

	public String getSalesreal() {
		return salesreal;
	}

	public void setSalesreal(String salesreal) {
		this.salesreal = salesreal;
	}

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
