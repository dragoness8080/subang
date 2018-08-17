package com.gcs.suban.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.adapter.ConfirmAdapter;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.AddressBean;
import com.gcs.suban.bean.InventorySelfBuyBean;
import com.gcs.suban.bean.OrderBean;
import com.gcs.suban.bean.ShopDataBean;
import com.gcs.suban.eventbus.InventoryEvent;
import com.gcs.suban.listener.OnInventorySelfListener;
import com.gcs.suban.model.InventorySelfBuyModelimpl;
import com.gcs.suban.popwindows.PayStockPopWindow;
import com.gcs.suban.tools.ClickFilter;
import com.gcs.suban.tools.ToastTool;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.rong.eventbus.EventBus;

public class SelfConfirmActivity extends BaseActivity implements OnInventorySelfListener {
    public ScrollView mainView;

    private LinearLayout Llyt_address;

    private TextView Tv_title;
    private TextView Tv_realname;
    private TextView Tv_mobile;
    private TextView Tv_address;
    private TextView Tv_totalnum;
    private TextView Tv_totalprice;
    private TextView Tv_mail;
    private TextView Tv_payprice;


    private Button Btn_confirm;

    private ImageButton IBtn_back;

    private ListView listView;

    private ConfirmAdapter adapter;

    private InventorySelfBuyModelimpl selfBuyModelimpl;

    private String goodsid;
    private String optionid;
    private String total;
    private String addressid = "-1";

    private String deduct="0";
    private String coupondataid="0";

    private String goodsList = "";


    @Override
    protected void init() {
        EventBus.getDefault().register(this);
        Intent intent = getIntent();
        goodsList = intent.getStringExtra("goodslist");

        // TODO Auto-generated method stub
        setContentView(R.layout.activity_selfconfirm);
        Tv_title = (TextView) findViewById(R.id.title);
        Tv_title.setText("自提确认");
        Tv_realname = (TextView) findViewById(R.id.self_tv_realname);
        Tv_mobile = (TextView) findViewById(R.id.self_tv_tel);
        Tv_address = (TextView) findViewById(R.id.self_tv_address);
        Tv_totalnum = (TextView) findViewById(R.id.self_tv_totalnum);
        Tv_totalprice = (TextView) findViewById(R.id.self_tv_totalprice);
        Tv_payprice = (TextView) findViewById(R.id.self_tv_paymoney);
        Tv_mail = (TextView) findViewById(R.id.self_tv_mail);

        listView = (ListView) findViewById(R.id.self_listView);
        listView.setFocusable(false);

        mainView = (ScrollView) findViewById(R.id.self_mainView);

        Btn_confirm = (Button) findViewById(R.id.self_btn_confirm);
        Btn_confirm.setOnClickListener(this);

        IBtn_back = (ImageButton) findViewById(R.id.back);
        IBtn_back.setOnClickListener(this);

        Llyt_address = (LinearLayout) findViewById(R.id.self_llyt_address);
        Llyt_address.setOnClickListener(this);

        selfBuyModelimpl = new InventorySelfBuyModelimpl();
        selfBuyModelimpl.saveSelfBuy(Url.selfapply,goodsList,"0",this);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.self_btn_confirm:
                if(ClickFilter.ispayFastClick())
                {
                    return;
                }
                if (addressid.equals("-1")) {
                    ToastTool.showToast(context, "请先选择收货地址");
                    return;
                }
                dialog.show();
                Btn_confirm.setClickable(false);
                selfBuyModelimpl.confirmSelfBuy(Url.addselfapply,goodsList,addressid,this);
                break;
            case R.id.self_llyt_address:
                Intent intent = new Intent(context, AddressSelectActivity.class);
                startActivityForResult(intent, 0);
                break;
            default:
                break;
        }
    }

    /**
     * 网络请求 结果返回失败
     */
    @Override
    public void onError(String error) {
        // TODO Auto-generated method stub
        Btn_confirm.setClickable(true);
        dialog.dismiss();
        ToastTool.showToast(context, error);
    }

    @Override
    public void onSuccess(List<InventorySelfBuyBean> mListType, String page) {

    }

    @Override
    public void onSaveSuccess(OrderBean orderBean,AddressBean addressBean, List<ShopDataBean> mListType) {
        dialog.dismiss();
        adapter = new ConfirmAdapter(this, mListType);
        listView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listView);
        Tv_totalnum.setText("共" + mListType.size() + "件商品");
        if(addressBean == null){
            addressid = "-1";
            Tv_realname.setText("您还没有收货地址");
            Tv_mobile.setText("");
            Tv_address.setText("请先添加收货地址");
        }else{
            addressid = addressBean.addressid;
            Tv_realname.setText(addressBean.realname);
            Tv_mobile.setText(addressBean.mobile);
            Tv_address.setText(addressBean.province + addressBean.city + addressBean.area
                    + addressBean.address);
        }

        Tv_totalprice.setText("￥" + orderBean.goodsprice);
        Tv_mail.setText("￥" + orderBean.dispatchprice);
        Tv_payprice.setText("￥" + (Double .valueOf(orderBean.dispatchprice) + orderBean.goodsprice));
    }

    @Override
    public void onConfirmSuccess(String orderid, String ordersn, int ispay, String money, String price) {
        dialog.dismiss();
        if(ispay == 1){
            Intent intent = new Intent(context, PayStockPopWindow.class);
            Bundle bundle = new Bundle();
            bundle.putInt("orderid",Integer.valueOf(orderid));
            bundle.putString("ordersn",ordersn);
            bundle.putString("dispatchprice",money);
            bundle.putDouble("price", Double.valueOf(price));
            bundle.putInt("freightmodal",0);
            intent.putExtras(bundle);
            startActivity(intent);
        }else{
            SweetAlertDialog dialog = new SweetAlertDialog(context,SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("自购")
                    .setContentText("自购成功")
                    .setConfirmText("确定")
                    .showCancelButton(false)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            EventBus.getDefault().post(new InventoryEvent("update","msg"));
                        }
                    });
            dialog.show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("selfbuyrequestCode", requestCode + "");
        switch (requestCode) {
            case 0:
                if(resultCode==RESULT_OK)
                {
                    addressid = data.getStringExtra("addressid");
                    Tv_realname.setText(data.getStringExtra("realname"));
                    Tv_mobile.setText(data.getStringExtra("mobile"));
                    Tv_address.setText(data.getStringExtra("address"));
                    selfBuyModelimpl.saveSelfBuy(Url.selfapply,goodsList,addressid,this);
                }
                break;
            case 1:
                if(resultCode==RESULT_OK)
                {
                    deduct = data.getStringExtra("deduct");
                    coupondataid = data.getStringExtra("coupondataid");
                }
                break;
        }
    }

    /**
     * 广播事件
     */
    public void onEventMainThread(InventoryEvent event) {
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
