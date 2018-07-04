package com.gcs.suban.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.adapter.InventoryNumAdapter;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.InventoryGoodsBean;
import com.gcs.suban.bean.InventorySelfBuyBean;
import com.gcs.suban.eventbus.InventoryEvent;
import com.gcs.suban.listener.OnInventoryGoodsListener;
import com.gcs.suban.listener.OnInventorySelfListener;
import com.gcs.suban.model.InventoryGoodsModel;
import com.gcs.suban.model.InventoryGoodsModelImpl;
import com.gcs.suban.model.InventorySelfBuyModel;
import com.gcs.suban.model.InventorySelfBuyModelimpl;
import com.gcs.suban.tools.ToastTool;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

public class InventorySelfAddActivity extends BaseActivity implements OnInventoryGoodsListener {

    protected TextView Tv_title;
    protected ImageButton Ib_back;
    protected ListView listView;
    protected Button Btn_ok;
    protected InventoryNumAdapter adapter;
    protected List<InventoryGoodsBean> goodsBeanList = new ArrayList<InventoryGoodsBean>();
    protected InventoryGoodsModel goodsModel;

    @Override
    protected void init() {
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_selfbuy);
        Tv_title = (TextView)findViewById(R.id.title);
        Ib_back = (ImageButton)findViewById(R.id.back);
        listView = (ListView)findViewById(R.id.list_goods);
        Btn_ok = (Button)findViewById(R.id.btn_ok);

        Tv_title.setText("…Í«Î◊‘Ã·");
        Ib_back.setOnClickListener(this);
        Btn_ok.setOnClickListener(this);

        adapter = new InventoryNumAdapter(context, goodsBeanList);
        listView.setAdapter(adapter);

        goodsModel = new InventoryGoodsModelImpl();
        goodsModel.getGoodsList(Url.goodslist,this);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.btn_ok:
                setSelfGoodsList();
                break;
            default:
                break;
        }
    }

    @Override
    public void onError(String error) {
        ToastTool.showToast(context, error);
    }

    @Override
    public void onGoodsList(List<InventoryGoodsBean> list) {
        if(list != null){
            goodsBeanList.addAll(list);
            adapter.notifyDataSetChanged();
        }
        dialog.dismiss();
    }

    @Override
    public void onGoodsInfo(InventoryGoodsBean bean) {

    }

    protected void setSelfGoodsList(){
        InventoryGoodsBean goodsBean;
        String data = null;
        for(int i = 0; i < goodsBeanList.size(); i ++){
            goodsBean = goodsBeanList.get(i);
            if(goodsBean.num == null){
            }else{
                if(data == null){
                    data = goodsBean.goodsid + "#" + goodsBean.num;
                }else{
                    data = data + "," + goodsBean.goodsid + "#" + goodsBean.num;
                }
            }
        }

        Intent intent = new Intent(context, SelfConfirmActivity.class);
        intent.putExtra("goodslist", data);
        startActivity(intent);
    }

    public void onEventMainThread(InventoryEvent event){
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
