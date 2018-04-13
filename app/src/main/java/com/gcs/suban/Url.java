package com.gcs.suban;

public interface Url {

    //String host = "http://sbyssh.86tudi.com/index.php/";
    String host = "http://ios.sbyssh.com/index.php/";


    String welcome = host + "Share/wheeljpg";//��ӭ����

    String gettoken = host + "Im/gettoken";//����token
    String getservice = host + "Im/getcustomservice";//����ͷ�id
    String getuserinfo = host + "Im/getnicknameandavatar";//����userinfo

    String phoneset = host + "Login/bound_phone";//�ֻ���������
    String phonemod = host + "Login/mod_phone";//�ֻ������޸�
    String forgetpwd = host + "Login/forgetpwd";//����֧�������޸�
    String getcode = host + "Send/phonechang_verificode";//�Ա��޸�

    String recharge = host + "Pay/recharge";//��ֵ

    String iscoupon = host + "Coupon/isgetappdownloadcoupon";//�Ƿ����Ż�ȯ
    String couponlist = host + "Coupon/couponlist";//�Ż�ȯ�б�

    String home = host + "Index/index";//��ҳ
    String home3 = host + "Index/index3";
    String center = host + "Sellcenter/index";//��������
    String shopcar = host + "Order/shoppinglist";//���ﳵ
    String member = host + "Sellcenter/memberindex";//��Ա����
    String person = host + "Member/index";//��������

    String articlelist = host + "Article/articlelist";//�ذ��Ļ��б�
    String artcilecolum = host + "Article/artcilecolum";//�ذ��Ļ����� �б�
    String article = host + "Article/article";//�ذ��Ļ�webview

    String commission = host + "Sellcenter/commissionlog";//Ӷ����ϸ
    String mycustom = host + "Sellcenter/mycustom";//�ҵĹ˿�
    String myteam = host + "Sellcenter/myteam";//�ҵ��Ŷ�
    //String sellorders = host + "Sellcenter/sellorders";//��������
    String sellorders = host + "TestSeller/sellorders"; //��������
    String commissionup = host + "Sellcenter/commissionup";//��������
    String mycommission = host + "Sellcenter/mycommission";//�ҵĺ�̨

    String shopindex = host + "Shop/index";//С����Ϣ
    String shopnamemod = host + "Shop/shopnamemod";//С�������޸�
    String upfilelogo = host + "Shop/upfilelogo";//С��Logo�޸�
    String upfileimg = host + "Shop/upfileimg";//С������޸�
    String shopdescmod = host + "Shop/shopdescmod";//С�����޸�

    String avatarmod = host + "Member/avatarmod";//ͷ���޸�
    String nickname = host + "Member/nicknamemod";//�ǳ��޸�
    String gendermod = host + "Member/gendermod";//�Ա��޸�
    String withdraw = host + "Member/withdraw";//��Ա����
    String record = host + "Member/rechargedetail";//�ҵ��㼣
    String footprintlist = host + "Member/myhistory";//�ҵ��㼣
    String cancelfootprintt = host + "Member/historydel";//ɾ���㼣
    String messagelist = host + "Member/messagelist";//��Ϣ�б�
    String updatabankcard = host + "Member/updatabankcard";//������п���Ϣ
    String bankcard = host + "Member/hasbankcard";//���п���Ϣ
    String isagent = host + "Member/isagent";//�Ƿ��Ƿ�����

    String shopdata = host + "Goods/goodsdetails";//��Ʒ����
    String shopgraphic = host + "Goods/goodscontent";//��Ʒͼ������
    String getcomments = host + "Goods/getcomments";//��Ʒ��������
    String goodsspec = host + "Goods/getgoodsspec";//��Ʒ���
    String goodsprice = host + "Goods/getgoodsspecprice";//��Ʒ���

    String collectlist = host + "Favorite/favoritelist";//�ղ��б�
    String collect = host + "Favorite/addfavorite";//�ղ�
    String cancelcollect = host + "Favorite/delfavorite";//ȡ���ղ�

    String addcar = host + "Order/addshoppingcart";//���빺�ﳵ
    String carnummod = host + "Order/cartgoodsedit";//���ﳵ�����޸�
    String cardel = host + "Order/cartgoodsdel";//���ﳵɾ��
    String cartaccount = host + "OrderForm/cartaccount";//���ﳵ����
    String confirmcar = host + "OrderForm/cartup";//���ﳵ����
    String confirmorder = host + "OrderForm/confirmorder";//����ȷ�Ͻ���
    String addorder = host + "OrderForm/order";//����ȷ�Ͻ���
    String paysuccess = host + "OrderForm/paysuccess";//֧���ɹ�
    String orderlist = host + "OrderForm/orderlist";//�����б�
    String orderdetails = host + "OrderForm/getorderdetails";//��������
    String cancelorder = host + "OrderForm/cancelorder";//ȡ������
    String delorder = host + "OrderForm/delorder";//ɾ������
    String reordersn = host + "OrderForm/reordersn";//�������ɶ�����
    String confirmtake = host + "OrderForm/confirmtake";//ȡ���ջ�����
    String refund = host + "OrderForm/refund";//�˿�����
    String cancelrefund = host + "OrderForm/cancelrefund";//ȡ���˿�����
    String express = host + "OrderForm/express";//������Ϣ
    String commentimgup = host + "OrderForm/commentimgup";//����ͼƬ�ϴ�
    String commentsup = host + "OrderForm/commentsup";//�����ϴ�
    String appendcommentsup = host + "OrderForm/appendcommentsup";//׷�������ϴ�
    String refundinfo = host + "OrderForm/confirmrefund";//�˿���Ϣ
    String refundimgup = host + "OrderForm/refundimgup";//�˿�ͼƬ�ϴ�

    String address = host + "Address/addresslist";//�ջ���ַ�б�
    String addaddress = host + "Address/newaddress";//����ջ���ַ
    String changeaddress = host + "Address/modaddress";//�޸��ջ���ַ
    String deleteaddress = host + "Address/addressdel";//ɾ���ջ���ַ
    String defaultaddress = host + "Address/defaultaddress";//�޸�Ĭ�ϵ�ַ
    String get_lat_lng = host + "Address/get_lat_lng";      //�ϴ���γ��
    String side_member = host + "Address/side_member";      //������ߵ��ذ���
    String search = host + "Member/search";

    String sharecontent = host + "Share/share_content";//�ƹ��ά��

    String wxToken = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Constants.WX_APP_KEY
            + "&secret=" + Constants.WX_APP_SECRET
            + "&grant_type=authorization_code";//��ȡ΢��openid
    String wxInfo = "https://api.weixin.qq.com/sns/userinfo";//��ȡ΢�Ÿ�����Ϣ
    String wxLogin = host + "Login/login_wechatauth_submit";//��ȡ΢�Ÿ�����Ϣ

    String jsonError = "���������ݽ�������";
    String networkError = "������������״��";
}
