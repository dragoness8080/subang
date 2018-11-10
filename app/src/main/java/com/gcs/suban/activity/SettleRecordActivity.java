package com.gcs.suban.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.adapter.SettledRecordAdapter;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.SettledBean;
import com.gcs.suban.listener.OnSettledRecordListener;
import com.gcs.suban.model.RecordModel;
import com.gcs.suban.model.RecordModelImpl;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.view.LoadListView;
import com.gcs.suban.view.LoadListView.onLoadListViewListener;

import java.util.ArrayList;
import java.util.List;

public class SettleRecordActivity extends BaseActivity implements OnRefreshListener,onLoadListViewListener,OnSettledRecordListener,OnItemClickListener {

    private TextView Tv_title;
    private ImageButton IBtn_back;
    private Button Btn_confirm;
    protected LinearLayout layout;
    private LoadListView listView;
    private SettledRecordAdapter adapter; // 自定义适配器
    private List<SettledBean> mListType = new ArrayList<SettledBean>(); // 购物车数据集合
    private RecordModel model;
    private String page = "0";
    private Boolean isRefresh = true;


    @Override
    public void onRefresh() {
        page = "0";
        isRefresh = true;
        model.getSettleList(Url.commission, page, this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.activity_record);
        Tv_title = (TextView) findViewById(R.id.title);
        Tv_title.setText("佣金明细");
        IBtn_back = (ImageButton) context.findViewById(R.id.back);
        IBtn_back.setOnClickListener(this);
        Btn_confirm= (Button) context.findViewById(R.id.layout_commission)
                .findViewById(R.id.btn_confirm);
        Btn_confirm.setOnClickListener(this);
        layout = (LinearLayout) context.findViewById(R.id.layout_null_commission);
        listView = (LoadListView) findViewById(R.id.list_record);
        listView.setOnLoadListViewListener(this);
        adapter = new SettledRecordAdapter(this, mListType);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        swipeRefreshLayout = (SwipeRefreshLayout) context
                .findViewById(R.id.swipeRefreshLayout_record);
        swipeRefreshLayout.setOnRefreshListener(this);
        InitSwipeRefreshLayout();
        model = new RecordModelImpl();
        model.getSettleList(Url.commission_settle, page, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_confirm:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoad() {
        model.getSettleList(Url.commission_settle, page, this);
    }

    /**
     * 更新适配器
     */
    private void setData(List<SettledBean> ListType) {
        if (isRefresh == true) {
            isRefresh = false;
            adapter.clear();
            mListType.clear();
        }
        listView.loadComplete();

        mListType.addAll(ListType);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccess(List<SettledBean> ListType, String page) {
        layout.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        this.page = page;
        if (ListType == null){
            if (isRefresh != true) {
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
            setData(ListType);
        }
    }

    @Override
    public void onError(String error) {
        ToastTool.showToast(context, error);
        swipeRefreshLayout.setRefreshing(false);
        listView.loadComplete();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SettledBean bean = (SettledBean) mListType.get(position);
        int settle_id = bean.id;
        Intent intent = new Intent(context, SettledLogActivity.class);
        intent.putExtra("id",settle_id);
        startActivity(intent);
    }
}
