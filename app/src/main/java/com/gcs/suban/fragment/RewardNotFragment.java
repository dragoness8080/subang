package com.gcs.suban.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.view.LoadListView;

public class RewardNotFragment extends RewardBaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reward_not,container,false);
        return view;
    }

    @Override
    protected void init() {
        status = 0;
        swipeRefreshLayout = (SwipeRefreshLayout)context.findViewById(R.id.swipe_reward_not);
        layout = (LinearLayout)context.findViewById(R.id.reward_not_null);
        mLoadListView = (LoadListView)context.findViewById(R.id.loadlistview_not);
        textView = (TextView)context.findViewById(R.id.textView1);

        super.init();
    }
}
