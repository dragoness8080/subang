package com.gcs.suban.bean;

public class ShopDataBean {
	public String goodsid;//商品id
	public String optionid;//商品规格id
	public String thumb;// 商品图片资源
	public String title;// 商品名称
	public String marketprice;// 商品现价
	public String productprice;// 商品原价
	public String salesreal;
	public int total;//商品数量
	
	public String isspec;//是否有规格

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
