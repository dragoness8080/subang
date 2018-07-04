package com.gcs.suban.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.gcs.suban.Url;
import com.gcs.suban.adapter.InventorySelfBuyAdapter;
import com.gcs.suban.base.BaseFragment;
import com.gcs.suban.bean.AddressBean;
import com.gcs.suban.bean.InventorySelfBuyBean;
import com.gcs.suban.bean.OrderBean;
import com.gcs.suban.bean.ShopDataBean;
import com.gcs.suban.eventbus.InventoryEvent;
import com.gcs.suban.listener.OnInventorySelfListener;
import com.gcs.suban.model.InventorySelfBuyModel;
import com.gcs.suban.model.InventorySelfBuyModelimpl;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.view.LoadListView;
import com.gcs.suban.view.LoadListView.onLoadListViewListener;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;


public class InventorySelfBaseFragment extends BaseFragment implements onLoadListViewListener,OnRefreshListener,OnInventorySelfListener,AdapterView.OnItemClickListener {

    protected LoadListView loadListView;
    protected String page = "0";
    protected LinearLayout layout;
    protected boolean isRefresh = false;
    protected InventorySelfBuyAdapter adapter;
    protected InventorySelfBuyModel model;
    protected List<InventorySelfBuyBean> mListType = new ArrayList<InventorySelfBuyBean>();
    protected int status = 0;

    @Override
    protected void init() {
        EventBus.getDefault().register(this);
        loadListView.setOnLoadListViewListener(this);
        loadListView.setOnItemClickListener(this);

        adapter = new InventorySelfBuyAdapter(context, mListType);
        loadListView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(this);
        InitSwipeRefreshLayout();

        model = new InventorySelfBuyModelimpl();
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

    }

    @Override
    public void onLoad() {
        model.getSelfBuy(Url.getselfbuy,status,page,this);
    }

    @Override
    public void onRefresh() {
        page = "0";
        isRefresh = true;
        model.getSelfBuy(Url.getselfbuy,status,page,this);
    }

    @Override
    public void onError(String error) {
        ToastTool.showToast(context, error);
        swipeRefreshLayout.setRefreshing(false);
        loadListView.loadComplete();
    }

    @Override
    public void onSuccess(List<InventorySelfBuyBean> listType, String page) {
        layout.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        this.page = page;
        if(listType == null){
            if(isRefresh != true){
                loadListView.setComplete(true);
                loadListView.loadComplete();
                return;
            }else{
                adapter.clear();
                mListType.clear();
                adapter.notifyDataSetChanged();
                loadListView.loadComplete();
                layout.setVisibility(View.VISIBLE);
            }
        }else{
            loadListView.setComplete(true);
            setData(listType);
        }
    }

    @Override
    public void onSaveSuccess(OrderBean orderBean, AddressBean addressBean, List<ShopDataBean> mListType) {

    }

    @Override
    public void onConfirmSuccess(String orderid, String ordersn, int ispay, String money, String price) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int applyId = mListType.get(position).id;

    }

    /**
     * ¹ã²¥ÊÂ¼þ
     */
    public void onEventMainThread(InventoryEvent event) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        this.onRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    protected void setData(List<InventorySelfBuyBean> list){
        if(isRefresh == true){
            isRefresh = false;
            adapter.clear();
            mListType.clear();
        }
        loadListView.loadComplete();
        mListType.addAll(list);
        adapter.notifyDataSetChanged();
    }
}
