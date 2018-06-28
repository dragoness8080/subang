package com.gcs.suban.popwindows;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gcs.suban.Constants;
import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.activity.InventorySelfActivity;
import com.gcs.suban.activity.MyWalletActivity;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.eventbus.InventoryEvent;
import com.gcs.suban.eventbus.PersonEvent;
import com.gcs.suban.listener.OnBaseListener;
import com.gcs.suban.listener.OnPayApplyListener;
import com.gcs.suban.listener.OnPayListener;
import com.gcs.suban.listener.OnPayStockListener;
import com.gcs.suban.model.PayInventoryModel;
import com.gcs.suban.model.PayInventoryModelImpl;
import com.gcs.suban.model.PayModel;
import com.gcs.suban.model.PayModelImpl;
import com.gcs.suban.tools.ToastTool;

import cn.beecloud.BCPay;
import cn.beecloud.async.BCCallback;
import cn.beecloud.async.BCResult;
import cn.beecloud.entity.BCPayResult;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.rong.eventbus.EventBus;

public class PayStockPopWindow extends BaseActivity implements OnPayStockListener,OnBaseListener, OnPayApplyListener {

    protected TextView Tv_payway;
    protected TextView Tv_price;
    protected TextView Tv_dispatchprice;
    protected RelativeLayout Rlty_dispatchprice;
    protected RelativeLayout Rlty_payway;
    protected TextView Tv_payprice;
    protected Button Btn_confirm;
    protected Button Btn_cancel;

    protected int orderid;
    protected String ordersn;
    protected String dispatchprice;
    protected String paytype;
    protected double price;
    protected int third = 1; // 微信 1 支付宝2 银联3
    protected int m;
    protected int freightModal;

    protected PayInventoryModel model;

    protected int type = 0;

    @Override
    protected void init() {
        EventBus.getDefault().post(new PersonEvent("",""));
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        orderid = bundle.getInt("orderid");
        ordersn = bundle.getString("ordersn");
        dispatchprice = bundle.getString("dispatchprice");
        price = bundle.getDouble("price");
        freightModal = bundle.getInt("freightmodal");
        InitImageLoader();
        setContentView(R.layout.popstock_pay);
        SetWindow();
        Tv_payway = (TextView)findViewById(R.id.tv_payway);
        Tv_price = (TextView)findViewById(R.id.tv_price);
        Tv_dispatchprice = (TextView)findViewById(R.id.tv_dispatchprice);
        Tv_payprice = (TextView)findViewById(R.id.tv_payprice);

        Tv_price.setText("￥" + price);
        Tv_dispatchprice.setText("￥" + dispatchprice);
        Tv_payprice.setText("￥" + price);

        Rlty_dispatchprice = (RelativeLayout)findViewById(R.id.rlyt_freight);
        if(freightModal == 1){
            Rlty_dispatchprice.setVisibility(View.GONE);
        }
        Rlty_payway = (RelativeLayout)findViewById(R.id.rlyt_payway);

        Btn_confirm = (Button)findViewById(R.id.btn_confirm);
        Btn_cancel = (Button)findViewById(R.id.btn_cancel);
        Btn_confirm.setOnClickListener(this);
        Btn_cancel.setOnClickListener(this);
        Rlty_payway.setOnClickListener(this);

        //type = ordersn.indexOf("CP") != -1 ? 0 : (ordersn.indexOf("AP") != -1 ? 1 : 2);
        if(ordersn.indexOf("CP") != -1){
            type = 0;
        }else if(ordersn.indexOf("AP") != -1){
            type = 1;
        }else if(ordersn.indexOf("SP") != -1){
            type = 2;
        }

        model = new PayInventoryModelImpl();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BCPay.initWechatPay(PayStockPopWindow.this, Constants.WX_APP_KEY);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.rlyt_payway:
                selectPay();
                break;
            case R.id.btn_confirm:
                if(third == 1){
                    paytype = "21";
                }else{
                    paytype = "22";
                }
                dialog.show();
                m = (int)(price * 100);
                thirdPay(type);
                break;
            default:
                break;
        }
    }


    @Override
    public void onSuccess(String resulttext) {

    }

    @Override
    public void onError(String error) {
        ToastTool.showToast(context, error);
    }

    @Override
    public void onPayApplySuccess(String msg) {
        ToastTool.showToast(context, msg);
        finish();
    }

    @Override
    public void onPayCoverSuccess(String msg) {
        Intent intent = new Intent(context, MyWalletActivity.class);
        intent.putExtra("postion", "2");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        ToastTool.showToast(context, msg);
        EventBus.getDefault().post(new InventoryEvent("update","msg"));
        finish();
    }

    protected void selectPay(){
        final SweetAlertDialog sd2 = new SweetAlertDialog(this, SweetAlertDialog.PAY_TYPE);
        sd2.setTitleText("请选择支付方式");
        sd2.setCancelable(true);
        sd2.setCanceledOnTouchOutside(true);
        sd2.setAliPayClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                third = 2;
                Tv_payway.setText("支付宝支付");
                sd2.dismiss();
            }
        });
        sd2.setWxPayClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                third = 1;
                Tv_payway.setText("微信支付");
                sd2.dismiss();
            }
        });
        sd2.setBtn3ClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                third = 3;
                Tv_payway.setText("银联支付");
                sd2.dismiss();
            }
        });
        sd2.isneed = 1;
        sd2.setThirdBtnText("银联支付");
        sd2.setThirdBtnVisible(1);
        sd2.show();
    }

    protected void thirdPay(int type){
        String marke = "";
        if(type == 0){
            marke = "库存补仓";
        }else if(type == 1){
            marke = "云仓申请";
        }else{
            marke = "自提付款";
        }
        if(third == 1){
            BCPay.getInstance(PayStockPopWindow.this).reqWXPaymentAsync(
                    marke,
                    m,
                    ordersn,
                    null,
                    bcCallback
            );
        }else if(third == 2){
            BCPay.getInstance(PayStockPopWindow.this).reqAliPaymentAsync(
                    marke,
                    m,
                    ordersn,
                    null,
                    bcCallback
            );
        }else if(third == 3){
            BCPay.getInstance(PayStockPopWindow.this).reqUnionPaymentAsync(
                    marke,
                    m,
                    ordersn,
                    null,
                    bcCallback
            );
        }
    }

    BCCallback bcCallback = new BCCallback() {
        @Override
        public void done(BCResult bcResult) {
            dialog.dismiss();
            final BCPayResult bcPayResult = (BCPayResult) bcResult;
            switch (bcPayResult.getResult()){
                case BCPayResult.RESULT_SUCCESS:
                    if(type == 0){
                        payComplete();
                    }else if(type == 1){
                        payApplyComplete();
                    }else if(type == 2){
                        Intent intent = new Intent(context, InventorySelfActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        ToastTool.showToast(context,"支付成功");
                        EventBus.getDefault().post(new InventoryEvent("update","msg"));
                        finish();
                    }

                    break;
                case BCPayResult.RESULT_CANCEL:
                    //取消支付
                    break;
                case BCPayResult.RESULT_FAIL:
                    String errmsg = "支付失败，原因：" + bcPayResult.getErrCode() + "#" + bcPayResult.getErrMsg() + "#" + bcPayResult.getDetailInfo();
                    if(bcPayResult.getErrCode() == 7){
                        Toast.makeText(context, errmsg, Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post(new InventoryEvent("what","msg"));
    }

    protected void payComplete(){
        model.onPayCover(Url.add_cover,orderid,this);
    }

    protected void payApplyComplete(){
        model.onPayApply(Url.add_stock,ordersn,this);
    }
}
