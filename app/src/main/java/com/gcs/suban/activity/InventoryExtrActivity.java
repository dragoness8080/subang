package com.gcs.suban.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.adapter.InventoryExtrAdapter;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.InventoryExtrBean;
import com.gcs.suban.listener.OnInventoryExtrListener;
import com.gcs.suban.model.InventoryStockModel;
import com.gcs.suban.model.InventoryStockModelImpl;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.view.LoadListView;
import com.gcs.suban.view.LoadListView.onLoadListViewListener;

import java.util.ArrayList;
import java.util.List;

public class InventoryExtrActivity extends BaseActivity implements OnRefreshListener,onLoadListViewListener,OnInventoryExtrListener {

    private LinearLayout layout_null_extr;
    private ImageButton back;
    private TextView title;
    private TextView textView1;
    private LoadListView loadlistview_extr;
    private String page="0";
    private boolean isRefresh = false;
    private InventoryStockModel model;
    private List<InventoryExtrBean> mListType = new ArrayList<>();
    private InventoryExtrAdapter adapter;


    @Override
    protected void init() {
        setContentView(R.layout.activity_extr);
        layout_null_extr = (LinearLayout)findViewById(R.id.layout_null_extr);
        back = (ImageButton)findViewById(R.id.back);
        title = (TextView)findViewById(R.id.title);
        textView1 = (TextView)findViewById(R.id.textView1);
        loadlistview_extr = (LoadListView)findViewById(R.id.loadlistview_extr);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swiperefreshlayout_extr);
        InitSwipeRefreshLayout();

        adapter = new InventoryExtrAdapter(context,mListType);
        loadlistview_extr.setAdapter(adapter);

        title.setText("云仓返点");
        textView1.setText("您目前还没有返点信息");
        back.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        loadlistview_extr.setOnLoadListViewListener(this);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        model = new InventoryStockModelImpl();
        this.onRefresh();
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
    public void onRefresh() {
        page = "0";
        isRefresh = true;
        model.getExtr(Url.get_extr,page,this);
    }

    @Override
    public void onLoad() {
        model.getExtr(Url.get_extr,page,this);
    }

    @Override
    public void onError(String error) {
        ToastTool.showToast(context,error);
        swipeRefreshLayout.setRefreshing(false);
        loadlistview_extr.loadComplete();
    }

    @Override
    public void onExtrSuccess(String page, List<InventoryExtrBean> mList) {
        layout_null_extr.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        this.page = page;
        if(mList == null){
            if(isRefresh != true){
                loadlistview_extr.setComplete(true);
                loadlistview_extr.loadComplete();
                return;
            }else{
                //isRefresh = false;
                adapter.clear();
                mListType.clear();
                adapter.notifyDataSetChanged();
                loadlistview_extr.loadComplete();
                layout_null_extr.setVisibility(View.VISIBLE);
            }
        }else{
            loadlistview_extr.setComplete(false);
            if(isRefresh == true){
                isRefresh = false;
                adapter.clear();
                mListType.clear();
            }
            loadlistview_extr.loadComplete();
            mListType.addAll(mList);
            adapter.notifyDataSetChanged();
        }
    }
}
