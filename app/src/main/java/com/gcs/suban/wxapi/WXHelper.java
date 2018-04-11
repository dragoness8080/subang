package com.gcs.suban.wxapi;

import android.content.Context;
import android.graphics.Bitmap;

import com.gcs.suban.Constants;
import com.gcs.suban.MyDate;
import com.gcs.suban.tools.ToastTool;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * 描述：微信帮助类
 */
public class WXHelper {
	private IWXAPI api;
	private Context context;

	// private static final int THUMB_SIZE = 150;

	/** 构造函数 */
	public WXHelper(Context context) {
		this.context = context;
		api = WXAPIFactory.createWXAPI(context, Constants.WX_APP_KEY, true);
		api.registerApp(Constants.WX_APP_KEY);
	}


	// 微信登入
	public void login() {
		if (!api.isWXAppInstalled()) {
			ToastTool.showToast(context, "您还未安装微信客户端");
			return;
		}
		MyDate.setWxreq("1");
		final SendAuth.Req req = new SendAuth.Req();
		req.scope = "snsapi_userinfo";
		req.state = "sb_wx_login";
		api.sendReq(req);
	}


	// 微信授权
	public void auth() {
		if (!api.isWXAppInstalled()) {
			ToastTool.showToast(context, "您还未安装微信客户端");
			return;
		}
		MyDate.setWxreq("2");
		final SendAuth.Req req = new SendAuth.Req();
		req.scope = "snsapi_userinfo";
		req.state = "sb_wx_login";
		api.sendReq(req);
	}

	// 微信分享
	public void share(int flag, String summary, String title, String otherurl,
			Bitmap pic) {
		if (!api.isWXAppInstalled()) {
			ToastTool.showToast(context, "您还未安装微信客户端");
			return;
		}
		MyDate.setWxreq("3");
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = otherurl; // 链接
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = title; // 标题
		msg.description = summary; // 内容
		msg.setThumbImage(pic);
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = String.valueOf(System.currentTimeMillis());
		req.message = msg;
		req.scene = flag;
		api.sendReq(req);
	}
}
