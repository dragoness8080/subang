package com.gcs.suban.bean;

import java.util.List;

public class OrderBean {
	public String goodsid; // ��Ʒid
	public String cartid; // ���ﳵid
	public String optionid; // ���id
	public String addressid; // �ջ���ַid
	public String remark; // ��ע
	public String dispatchprice; // �˷�
	public double price; // �۸�  �������˷ѣ�
	public double goodsprice; // ��Ʒ�۸񣨲������˷ѣ�
	public String total; // ����
	public String coupondataid; // �Ż�ȯid
	public String couponprice;// �Ż�ȯ�۸�
	
	
	public String orderid; // ����id
	public String ordersn; // ������
	public int status; // ����״̬
	public String statusname; // ����״̬
	public String goods; //������Ϣ�б�
	public int refundstate;
	public int refundid;
	public String createtime;
	
	public String ordercommission;
	
	
	public double credit2; // ���
	public String paytype;//��������
	
	public String nickname;
	public String level;
	
	public int iscomment;//���۴���
	
	public String shopphone;//�̼Һ��� 
	
	public String goodstitle; // ��Ʒ��
	public String time; // ��Ʒ����ʱ��
	public String refundmoney; // �˿���
	
	public List<ShopDataBean> mListType;

}
