package com.gcs.suban.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.adapter.InventoryLogAdapter;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.InventoryLogBean;
import com.gcs.suban.bean.InventoryMemberBean;
import com.gcs.suban.listener.OnInventorySettleListener;
import com.gcs.suban.model.InvenotrySettleModelImpl;
import com.gcs.suban.model.InventorySettleModel;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.view.LoadListView;
import com.gcs.suban.view.LoadListView.onLoadListViewListener;

import java.util.ArrayList;
import java.util.List;

public class SettledLogActivity extends BaseActivity implements OnRefreshListener,onLoadListViewListener,OnInventorySettleListener {
    private ImageButton back;
    private TextView title;
    private TextView textView;
    private LoadListView listView;
    private LinearLayout linearLayout;
    private InventoryLogAdapter adapter;
    private List<InventoryLogBean> mListType = new ArrayList<>();
    private String page = "0";
    private Boolean isRefresh = true;
    private InventorySettleModel model;
    private int id = 0;

    @Override
    public void onRefresh() {
        page = "0";
        isRefresh = true;
        model.getSettled(Url.settle_list,id,"",page,this);
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        setContentView(R.layout.activity_settled_order);
        back = (ImageButton)findViewById(R.id.back);
        title = (TextView)findViewById(R.id.title);
        title.setText("结算订单");
        textView = (TextView)findViewById(R.id.textView1);
        textView.setText("您目前没有库存结算订单");
        listView = (LoadListView)findViewById(R.id.list_settle_order);
        adapter = new InventoryLogAdapter(context,mListType);
        listView.setAdapter(adapter);
        linearLayout = (LinearLayout)findViewById(R.id.layout_null_settled);

        back.setOnClickListener(this);
        listView.setOnLoadListViewListener(this);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_settle);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        InitSwipeRefreshLayout();
        model = new InvenotrySettleModelImpl();
        onRefresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoad() {
        model.getSettled(Url.settle_list,id,"",page,this);
    }

    @Override
    public void OnError(String error) {
        ToastTool.showToast(context,error);
        swipeRefreshLayout.setRefreshing(false);
        listView.loadComplete();
    }

    @Override
    public void OnSettledSuccess(String settled, List<InventoryLogBean> list, String page) {
        this.page = page;
        linearLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        if(list != null){
            listView.setComplete(false);
            if (isRefresh == true){
                isRefresh = false;
                adapter.clear();
                mListType.clear();
            }
            listView.loadComplete();
            mListType.addAll(list);
            adapter.notifyDataSetChanged();
        }else{
            if (isRefresh != true){
                listView.setComplete(true);
                listView.loadComplete();
                return;
            }else{
                adapter.clear();
                mListType.clear();
                adapter.notifyDataSetChanged();
                listView.loadComplete();
                linearLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void OnBalanceSuccess(String settled, List<InventoryMemberBean> list, String page) {

    }
}
