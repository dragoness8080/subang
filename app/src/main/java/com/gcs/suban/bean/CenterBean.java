package com.gcs.suban.bean;
/**
 * ��������������
 */
public class CenterBean {
	public String commission;// ������Ӷ��
	public String commissionsum;// �ۼ�Ӷ��
	public String cashsum;// �ۼ�����
	public String uncom;// δ����Ӷ��
	public String putincome;// ������Ӷ��
	public String myteam;// �ҵ��Ŷ�
	public String mycustom;// �ҵĿͻ�
	public String shopname;// ����
	public String saleorder;// ��������
	public String income;// Ԥ������
	public String createtime;// ����ʱ��

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
