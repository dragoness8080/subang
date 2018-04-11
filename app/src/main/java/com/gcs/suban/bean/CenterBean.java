package com.gcs.suban.bean;
/**
 * 描述：分销中心
 */
public class CenterBean {
	public String commission;// 可提现佣金
	public String commissionsum;// 累计佣金
	public String cashsum;// 累计提现
	public String uncom;// 未结算佣金
	public String putincome;// 已申请佣金
	public String myteam;// 我的团队
	public String mycustom;// 我的客户
	public String shopname;// 店名
	public String saleorder;// 分销订单
	public String income;// 预计收益
	public String createtime;// 创建时间

	public CenterBean(String commission, String commissionsum, String cashsum,
			String uncom, String putincome, String myteam, String mycustom,
			String shopname, String saleorder, String income, String createtime) {
		super();
		this.commission = commission;
		this.commissionsum = commissionsum;
		this.cashsum = cashsum;
		this.uncom = uncom;
		this.putincome = putincome;
		this.myteam = myteam;
		this.mycustom = mycustom;
		this.shopname = shopname;
		this.saleorder = saleorder;
		this.income = income;
		this.createtime = createtime;
	}
}
