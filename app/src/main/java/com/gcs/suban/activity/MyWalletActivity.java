package com.gcs.suban.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.adapter.InventoryLogAddAdapter;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.InventoryLogBean;
import com.gcs.suban.listener.OnInventoryStockListener;
import com.gcs.suban.model.InventoryStockModel;
import com.gcs.suban.model.InventoryStockModelImpl;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.view.LoadListView;
import com.gcs.suban.view.LoadListView.onLoadListViewListener;

import java.util.ArrayList;
import java.util.List;

public class MyWalletActivity extends BaseActivity implements OnInventoryStockListener,onLoadListViewListener {

    protected ImageButton Img_wallet_back;
    protected TextView Tv_wallet_title;
    protected TextView Tv_surplus_money;
    protected TextView Tv_surplus_num;
    protected TextView Tv_bail;
    protected LoadListView List_logs;
    protected Button Btn_wallet;
    protected InventoryStockModel model;
    protected InventoryLogAddAdapter adapter;
    protected String page = "0";
    protected List<InventoryLogBean> inventoryLogBeanList = new ArrayList<InventoryLogBean>();
    protected RelativeLayout Rl_surplus_money,Rl_surplus_num;
    protected LinearLayout Ll_left_line,Ll_right_line;


    @Override
    protected void init() {
        setContentView(R.layout.activity_mywallet);
        Img_wallet_back = (ImageButton)findViewById(R.id.wallet_back);
        Tv_wallet_title = (TextView)findViewById(R.id.wallet_title);
        Btn_wallet = (Button)findViewById(R.id.wallet_btn);
        Tv_surplus_money = (TextView)findViewById(R.id.tv_surplus_money);
        Tv_surplus_num = (TextView)findViewById(R.id.tv_surplus_num);
        Tv_bail = (TextView)findViewById(R.id.tv_bail);
        List_logs = (LoadListView) findViewById(R.id.list_logs);

        Rl_surplus_money = (RelativeLayout)findViewById(R.id.surplus_money);
        Rl_surplus_num = (RelativeLayout)findViewById(R.id.surplus_num);
        Ll_left_line = (LinearLayout)findViewById(R.id.left_line);
        Ll_right_line = (LinearLayout)findViewById(R.id.right_line);

        Tv_wallet_title.setText("我的钱包");

        Img_wallet_back.setOnClickListener(this);
        Btn_wallet.setOnClickListener(this);
        List_logs.setOnLoadListViewListener(this);

        adapter = new InventoryLogAddAdapter(context,inventoryLogBeanList);
        List_logs.setAdapter(adapter);

        model = new InventoryStockModelImpl();
        model.getLogs(Url.get_logs,"stock.add", page, this);
        model.getTotalInfo(Url.get_inventory,this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wallet_back:
                finish();
                break;
            case R.id.wallet_btn:
                Intent intent = new Intent(context,InventoryAddActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void OnTotalSuccess(String surplus, String num, String bail,String balance, String balanceTitle, String gradeTitle, String avatar) {
        Tv_surplus_money.setText(surplus + "元");
        Tv_surplus_num.setText(num + "件");
        Tv_bail.setText(bail + "元");

        if(surplus.equals('0') || surplus.isEmpty()){
            Rl_surplus_money.setVisibility(View.GONE);
            Ll_left_line.setVisibility(View.GONE);
        }
        if(num.isEmpty() || num.equals("0")){
            Rl_surplus_num.setVisibility(View.GONE);
            Ll_right_line.setVisibility(View.GONE);
        }
    }

    protected void setData(List<InventoryLogBean> list){
        //adapter.clear();
        //inventoryLogBeanList.clear();
        List_logs.loadComplete();
        inventoryLogBeanList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void OnLogsSuccess(List<InventoryLogBean> list, String page) {
        Log.i("inventorylogadd", "page=" + page);
        this.page = page;
        if(list == null){
            List_logs.setComplete(true);
            List_logs.loadComplete();
        }else{
            List_logs.setComplete(false);
            setData(list);
        }
    }

    @Override
    public void OnError(String error) {
        ToastTool.showToast(context,error);
    }

    @Override
    public void onLoad() {
        model.getLogs(Url.get_logs,"stock.add", page, this);
    }
}
