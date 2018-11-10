package com.gcs.suban.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gcs.suban.Url;
import com.gcs.suban.adapter.RewardAdapter;
import com.gcs.suban.base.BaseFragment;
import com.gcs.suban.bean.RewardBean;
import com.gcs.suban.listener.OnRewardListener;
import com.gcs.suban.model.LotteryModel;
import com.gcs.suban.model.LotteryModelImpl;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.view.LoadListView;

import java.util.ArrayList;
import java.util.List;

public class RewardBaseFragment extends BaseFragment implements LoadListView.onLoadListViewListener,OnRewardListener,SwipeRefreshLayout.OnRefreshListener {
    protected RewardAdapter adapter;
    protected List<RewardBean> mListType = new ArrayList<>();
    protected LoadListView mLoadListView;
    protected LinearLayout layout;
    protected String page = "0";
    protected boolean isRefresh = false;
    protected LotteryModel model;
    protected int status;
    protected TextView textView;

    @Override
    protected void init() {
        textView.setText("您目前没有获奖信息");

        mLoadListView.setOnLoadListViewListener(this);
        InitSwipeRefreshLayout();

        adapter = new RewardAdapter(context,mListType);
        mLoadListView.setAdapter(adapter);

        model = new LotteryModelImpl();
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
    public void onError(String error) {
        ToastTool.showToast(context, error);
        swipeRefreshLayout.setRefreshing(false);
        mLoadListView.loadComplete();
    }

    @Override
    public void onSuccess(List<RewardBean> mList, String page) {
        layout.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        this.page = page;
        if(mList == null){
            if(isRefresh != true){
                mLoadListView.setComplete(true);
                mLoadListView.loadComplete();
                return;
            }else{
                adapter.clear();
                mListType.clear();
                adapter.notifyDataSetChanged();
                mLoadListView.loadComplete();
                layout.setVisibility(View.VISIBLE);
            }
        }else{
            mLoadListView.setComplete(false);
            setData(mList);
        }
    }

    @Override
    public void onLoad() {
        model.getRewardList(Url.reward_list, page, status, this);
    }

    @Override
    public void onRefresh() {
        page = "0";
        isRefresh = true;
        model.getRewardList(Url.reward_list, page, status, this);
    }

    private void setData(List<RewardBean> list){
        if(isRefresh == true){
            isRefresh = false;
            adapter.clear();
            mListType.clear();
        }
        mLoadListView.loadComplete();
        mListType.addAll(list);
        adapter.notifyDataSetChanged();
    }
}
