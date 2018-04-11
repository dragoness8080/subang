package com.gcs.suban.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.gcs.suban.Constants;
import com.gcs.suban.MyDate;
import com.gcs.suban.Url;
import com.gcs.suban.activity.FirstBiningActivity;
import com.gcs.suban.activity.MainActivity;
import com.gcs.suban.bean.WeixinBean;
import com.gcs.suban.listener.OnWeixinListener;
import com.gcs.suban.model.WeixinModel;
import com.gcs.suban.model.WeixinModelImpl;
import com.gcs.suban.tools.SharedPreference;
import com.gcs.suban.tools.ToastTool;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;

/**
 * 描述：微信回调页面
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler, OnWeixinListener {
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;

    private Context context = this;

    private String TAG = "WXEntryActivity";
    private String code;
    private String access_token;
    private String openid;
    private String wxreq = MyDate.getWxreq();
    // 1 登入 2授权 3分享
    private WeixinModel model = new WeixinModelImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constants.WX_APP_KEY, true);
        api.handleIntent(getIntent(), this);
        api.registerApp(Constants.WX_APP_KEY);
    }

    private String getTokenUrl() {
        return Url.wxToken + "&code=" + code;
    }

    private String getInfoUrl() {
        return Url.wxInfo + "?access_token=" + access_token + "&openid=" + openid;
    }

    /**
     * 获取微信令牌成功
     */
    @Override
    public void onToken(WeixinBean bean) {
        // TODO Auto-generated method stub
        access_token = bean.access_token;
        openid = bean.openid;
        model.getInfo(getInfoUrl(), this);
    }

    /**
     * 获取用户微信个人信息成功
     */
    @Override
    public void onInfo(WeixinBean bean) {
        model.Login(Url.wxLogin, bean, this);
    }


    /**
     * 登录成功
     */
    @Override
    public void onLogin(String openid, String isboundphone, String id) {
        // TODO Auto-generated method stub
        if (isboundphone.equals("0")) {
            Intent intent = new Intent(context, FirstBiningActivity.class);
            MyDate.setMyVid(openid);
            MyDate.setJpushid(id);
            startActivity(intent);
        } else {
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            SharedPreference.setParam(context, "vid", openid);
//            		SharedPreference.setParam(context, "vid", "obSNav4qf6_8qtirp-XA2bZJRegk");
            SharedPreference.setParam(context, "jpushid", id);
        }
        finish();
    }

    /**
     * 网络请求 结果返回失败
     */
    @Override
    public void onError(String error) {
        // TODO Auto-generated method stub
        ToastTool.showToast(context, error);
        Log.e(TAG, error);
        MyDate.wxresp = 4;
        finish();
    }

    // 微信回调
    @Override
    public void onReq(BaseReq arg0) {
        Log.e(TAG, "arg0" + arg0.toString());
    }

    /* 授权界面 用户操作后返回数据 */
    @Override
    public void onResp(BaseResp resp) {
        Log.i(TAG, "resp.errCode:" + resp.errCode + ",resp.errStr:" + resp.errStr);
        switch (resp.errCode) {
            // (用户同意授权)
            case BaseResp.ErrCode.ERR_OK:
                Log.i(TAG, wxreq);
                MyDate.wxresp = 1;
                if (wxreq.equals("1")) {
                    code = ((SendAuth.Resp) resp).code;
                    model.getToken(getTokenUrl(), this);
                } else if (wxreq.equals("3")) {
                    ToastTool.showToast(context, "分享成功");
                }
                break;
            // (用户取消授权)
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                MyDate.wxresp = 2;
                Log.i(TAG, "取消");
                finish();
                break;
            // (用户拒绝授权)
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                MyDate.wxresp = 3;
                Log.i(TAG, "拒绝");
                finish();
                break;
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}