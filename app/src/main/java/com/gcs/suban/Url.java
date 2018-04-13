package com.gcs.suban;

public interface Url {

    //String host = "http://sbyssh.86tudi.com/index.php/";
    String host = "http://ios.sbyssh.com/index.php/";


    String welcome = host + "Share/wheeljpg";//欢迎界面

    String gettoken = host + "Im/gettoken";//请求token
    String getservice = host + "Im/getcustomservice";//请求客服id
    String getuserinfo = host + "Im/getnicknameandavatar";//请求userinfo

    String phoneset = host + "Login/bound_phone";//手机号码设置
    String phonemod = host + "Login/mod_phone";//手机号码修改
    String forgetpwd = host + "Login/forgetpwd";//忘记支付密码修改
    String getcode = host + "Send/phonechang_verificode";//性别修改

    String recharge = host + "Pay/recharge";//充值

    String iscoupon = host + "Coupon/isgetappdownloadcoupon";//是否有优惠券
    String couponlist = host + "Coupon/couponlist";//优惠券列表

    String home = host + "Index/index";//首页
    String home3 = host + "Index/index3";
    String center = host + "Sellcenter/index";//分销中心
    String shopcar = host + "Order/shoppinglist";//购物车
    String member = host + "Sellcenter/memberindex";//会员中心
    String person = host + "Member/index";//个人中心

    String articlelist = host + "Article/articlelist";//素邦文化列表
    String artcilecolum = host + "Article/artcilecolum";//素邦文化分类 列表
    String article = host + "Article/article";//素邦文化webview

    String commission = host + "Sellcenter/commissionlog";//佣金明细
    String mycustom = host + "Sellcenter/mycustom";//我的顾客
    String myteam = host + "Sellcenter/myteam";//我的团队
    //String sellorders = host + "Sellcenter/sellorders";//分销订单
    String sellorders = host + "TestSeller/sellorders"; //分销订单
    String commissionup = host + "Sellcenter/commissionup";//分销订单
    String mycommission = host + "Sellcenter/mycommission";//我的后台

    String shopindex = host + "Shop/index";//小店信息
    String shopnamemod = host + "Shop/shopnamemod";//小店名称修改
    String upfilelogo = host + "Shop/upfilelogo";//小店Logo修改
    String upfileimg = host + "Shop/upfileimg";//小店店招修改
    String shopdescmod = host + "Shop/shopdescmod";//小店简介修改

    String avatarmod = host + "Member/avatarmod";//头像修改
    String nickname = host + "Member/nicknamemod";//昵称修改
    String gendermod = host + "Member/gendermod";//性别修改
    String withdraw = host + "Member/withdraw";//会员提现
    String record = host + "Member/rechargedetail";//我的足迹
    String footprintlist = host + "Member/myhistory";//我的足迹
    String cancelfootprintt = host + "Member/historydel";//删除足迹
    String messagelist = host + "Member/messagelist";//消息列表
    String updatabankcard = host + "Member/updatabankcard";//添加银行卡信息
    String bankcard = host + "Member/hasbankcard";//银行卡信息
    String isagent = host + "Member/isagent";//是否是分销商

    String shopdata = host + "Goods/goodsdetails";//商品详情
    String shopgraphic = host + "Goods/goodscontent";//商品图文详情
    String getcomments = host + "Goods/getcomments";//商品订单评论
    String goodsspec = host + "Goods/getgoodsspec";//商品规格
    String goodsprice = host + "Goods/getgoodsspecprice";//商品规格

    String collectlist = host + "Favorite/favoritelist";//收藏列表
    String collect = host + "Favorite/addfavorite";//收藏
    String cancelcollect = host + "Favorite/delfavorite";//取消收藏

    String addcar = host + "Order/addshoppingcart";//加入购物车
    String carnummod = host + "Order/cartgoodsedit";//购物车数量修改
    String cardel = host + "Order/cartgoodsdel";//购物车删除
    String cartaccount = host + "OrderForm/cartaccount";//购物车结算
    String confirmcar = host + "OrderForm/cartup";//购物车结算
    String confirmorder = host + "OrderForm/confirmorder";//订单确认界面
    String addorder = host + "OrderForm/order";//订单确认界面
    String paysuccess = host + "OrderForm/paysuccess";//支付成功
    String orderlist = host + "OrderForm/orderlist";//订单列表
    String orderdetails = host + "OrderForm/getorderdetails";//订单详情
    String cancelorder = host + "OrderForm/cancelorder";//取消订单
    String delorder = host + "OrderForm/delorder";//删除订单
    String reordersn = host + "OrderForm/reordersn";//重新生成订单号
    String confirmtake = host + "OrderForm/confirmtake";//取消收货订单
    String refund = host + "OrderForm/refund";//退款申请
    String cancelrefund = host + "OrderForm/cancelrefund";//取消退款申请
    String express = host + "OrderForm/express";//物流信息
    String commentimgup = host + "OrderForm/commentimgup";//评论图片上传
    String commentsup = host + "OrderForm/commentsup";//评论上传
    String appendcommentsup = host + "OrderForm/appendcommentsup";//追加评论上传
    String refundinfo = host + "OrderForm/confirmrefund";//退款信息
    String refundimgup = host + "OrderForm/refundimgup";//退款图片上传

    String address = host + "Address/addresslist";//收货地址列表
    String addaddress = host + "Address/newaddress";//添加收货地址
    String changeaddress = host + "Address/modaddress";//修改收货地址
    String deleteaddress = host + "Address/addressdel";//删除收货地址
    String defaultaddress = host + "Address/defaultaddress";//修改默认地址
    String get_lat_lng = host + "Address/get_lat_lng";      //上传经纬度
    String side_member = host + "Address/side_member";      //搜索身边的素邦人
    String search = host + "Member/search";

    String sharecontent = host + "Share/share_content";//推广二维码

    String wxToken = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Constants.WX_APP_KEY
            + "&secret=" + Constants.WX_APP_SECRET
            + "&grant_type=authorization_code";//获取微信openid
    String wxInfo = "https://api.weixin.qq.com/sns/userinfo";//获取微信个人信息
    String wxLogin = host + "Login/login_wechatauth_submit";//获取微信个人信息

    String jsonError = "服务器数据解析错误";
    String networkError = "请检查您的网络状况";
}
