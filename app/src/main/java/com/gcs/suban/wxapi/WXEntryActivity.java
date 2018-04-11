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
 * ������΢�Żص�ҳ��
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler, OnWeixinListener {
    // IWXAPI �ǵ�����app��΢��ͨ�ŵ�openapi�ӿ�
    private IWXAPI api;

    private Context context = this;

    private String TAG = "WXEntryActivity";
    private String code;
    private String access_token;
    private String openid;
    private String wxreq = MyDate.getWxreq();
    // 1 ���� 2��Ȩ 3����
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
     * ��ȡ΢�����Ƴɹ�
     */
    @Override
    public void onToken(WeixinBean bean) {
        // TODO Auto-generated method stub
        access_token = bean.access_token;
        openid = bean.openid;
        model.getInfo(getInfoUrl(), this);
    }

    /**
     * ��ȡ�û�΢�Ÿ�����Ϣ�ɹ�
     */
    @Override
    public void onInfo(WeixinBean bean) {
        model.Login(Url.wxLogin, bean, this);
    }


    /**
     * ��¼�ɹ�
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
     * �������� �������ʧ��
     */
    @Override
    public void onError(String error) {
        // TODO Auto-generated method stub
        ToastTool.showToast(context, error);
        Log.e(TAG, error);
        MyDate.wxresp = 4;
        finish();
    }

    // ΢�Żص�
    @Override
    public void onReq(BaseReq arg0) {
        Log.e(TAG, "arg0" + arg0.toString());
    }

    /* ��Ȩ���� �û������󷵻����� */
    @Override
    public void onResp(BaseResp resp) {
        Log.i(TAG, "resp.errCode:" + resp.errCode + ",resp.errStr:" + resp.errStr);
        switch (resp.errCode) {
            // (�û�ͬ����Ȩ)
            case BaseResp.ErrCode.ERR_OK:
                Log.i(TAG, wxreq);
                MyDate.wxresp = 1;
                if (wxreq.equals("1")) {
                    code = ((SendAuth.Resp) resp).code;
                    model.getToken(getTokenUrl(), this);
                } else if (wxreq.equals("3")) {
                    ToastTool.showToast(context, "����ɹ�");
                }
                break;
            // (�û�ȡ����Ȩ)
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                MyDate.wxresp = 2;
                Log.i(TAG, "ȡ��");
                finish();
                break;
            // (�û��ܾ���Ȩ)
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                MyDate.wxresp = 3;
                Log.i(TAG, "�ܾ�");
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