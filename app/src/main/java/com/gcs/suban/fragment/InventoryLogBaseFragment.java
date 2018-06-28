package com.gcs.suban.fragment;

import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.gcs.suban.Url;
import com.gcs.suban.adapter.InventoryLogAdapter;
import com.gcs.suban.base.BaseFragment;
import com.gcs.suban.bean.InventoryLogBean;
import com.gcs.suban.listener.OnInventoryStockListener;
import com.gcs.suban.model.InventoryStockModel;
import com.gcs.suban.model.InventoryStockModelImpl;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.view.LoadListView;
import com.gcs.suban.view.LoadListView.onLoadListViewListener;

import java.util.ArrayList;
import java.util.List;

public class InventoryLogBaseFragment extends BaseFragment implements onLoadListViewListener,OnRefreshListener,OnInventoryStockListener {

    protected LoadListView listView;
    protected LinearLayout layout;

    protected String page = "0";
    protected Boolean isRefresh = false;

    protected InventoryLogAdapter adapter;
    protected List<InventoryLogBean> mListType = new ArrayList<InventoryLogBean>();
    protected InventoryStockModel model;
    protected String types;

    @Override
    protected void init() {
        listView.setOnLoadListViewListener(this);
        adapter = new InventoryLogAdapter(context, mListType);
        listView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(this);
        InitSwipeRefreshLayout();

        model = new InventoryStockModelImpl();
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        this.onRefresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default:
                break;
        }
    }

    @Override
    public void onLoad() {
        model.getLogs(Url.get_logs, types, page, this);
    }

    @Override
    public void onRefresh() {
        Log.i("inventorylog","onRefresh");
        page = "0";
        isRefresh = true;
        model.getLogs(Url.get_logs, types, page, this);
    }

    @Override
    public void OnTotalSuccess(String surplus, String num, String bail, String balance, String balanceTitle, String gradeTitle, String avatar) {

    }

    @Override
    public void OnLogsSuccess(List<InventoryLogBean> list, String page) {
        layout.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        this.page = page;
        if(list == null){
            if(isRefresh  != true){
                listView.setComplete(true);
                listView.loadComplete();
                return;
            }else{
                adapter.clear();
                mListType.clear();
                adapter.notifyDataSetChanged();
                listView.loadComplete();
                layout.setVisibility(View.VISIBLE);
            }
        }else{
            listView.setComplete(false);
            setData(list);
        }
    }

    @Override
    public void OnError(String error) {
        ToastTool.showToast(context, error);
        swipeRefreshLayout.setRefreshing(false);
        listView.loadComplete();
    }

    protected void setData(List<InventoryLogBean> listType){
        if(isRefresh == true){
            isRefresh = false;
            adapter.clear();
            mListType.clear();
        }
        listView.loadComplete();
        mListType.addAll(listType);
        adapter.notifyDataSetChanged();
    }
}
