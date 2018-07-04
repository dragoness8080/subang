package com.gcs.suban.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.app;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.InventoryLogBean;
import com.gcs.suban.listener.OnInventoryStockListener;
import com.gcs.suban.model.InventoryStockModel;
import com.gcs.suban.model.InventoryStockModelImpl;
import com.gcs.suban.tools.SharedPreference;
import com.gcs.suban.tools.ToastTool;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class InventoryActivity extends BaseActivity implements OnInventoryStockListener {

    protected ImageView Img_member_avatar;
    protected TextView Tv_stock;
    protected TextView Tv_grade;
    protected RelativeLayout Rl_wallet;
    protected RelativeLayout Rl_settle_center;
    protected RelativeLayout Rl_bill;
    protected RelativeLayout Rl_stock_order;
    protected RelativeLayout Rl_self_order;
    protected InventoryStockModel model;

    @Override
    protected void init() {
        setContentView(R.layout.activity_inventory);
        Tv_grade = (TextView)findViewById(R.id.tv_grade_name);
        Tv_stock = (TextView)findViewById(R.id.tv_stock);
        Rl_wallet = (RelativeLayout)findViewById(R.id.rl_wallet);
        Rl_settle_center = (RelativeLayout)findViewById(R.id.rl_settle_center);
        Rl_bill = (RelativeLayout)findViewById(R.id.rl_bill);
        Rl_stock_order = (RelativeLayout)findViewById(R.id.rl_stock_order);
        Rl_self_order = (RelativeLayout)findViewById(R.id.rl_self_order);
        Img_member_avatar = (ImageView)findViewById(R.id.img_member_avatar);

        Rl_wallet.setOnClickListener(this);
        Rl_settle_center.setOnClickListener(this);
        Rl_bill.setOnClickListener(this);
        Rl_stock_order.setOnClickListener(this);
        Rl_self_order.setOnClickListener(this);

        model = new InventoryStockModelImpl();
        model.getTotalInfo(Url.get_inventory,this);

        InitImageLoader();
        dialog.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_wallet:
                Intent intent_wallet = new Intent(context,MyWalletActivity.class);
                startActivity(intent_wallet);
                break;
            case R.id.rl_settle_center:
                Intent intent_settle = new Intent(context,InventorySettleActivity.class);
                startActivity(intent_settle);
                break;
            case R.id.rl_bill:
                Intent intent_bill = new Intent(context, InventoryAddActivity.class);
                startActivity(intent_bill);
                break;
            case R.id.rl_stock_order:
                Intent intent_log = new Intent(context, InventoryLogActivity.class);
                startActivity(intent_log);
                break;
            case R.id.rl_self_order:
                Intent intent_self = new Intent(context, InventorySelfActivity.class);
                startActivity(intent_self);
                /*
                dialog = new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("提示")
                        .setContentText("功能正在完善中。")
                        .setConfirmText("确定")
                        .showCancelButton(false)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.cancel();
                            }
                        });
                dialog.show();
                */
                break;
            default:
                break;
        }
    }

    @Override
    public void OnTotalSuccess(String surplus, String num, String bail,String balance, String gradeMoney, String gradeTitle,String avatar) {
        Tv_grade.setText(gradeTitle);
        String stock = surplus + "元" + "," + num + "件";
        Tv_stock.setText(stock);
        imageLoader.displayImage(avatar,Img_member_avatar,options4);
        //保存金额到本地
        SharedPreference.setParam(context,"gradeMoney",gradeMoney);
        dialog.dismiss();
    }

    @Override
    public void OnLogsSuccess(List<InventoryLogBean> list, String page) {

    }


    @Override
    public void OnError(String error) {
        ToastTool.showToast(context,error);
    }

}
