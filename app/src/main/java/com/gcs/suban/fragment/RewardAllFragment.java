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

public class RewardAllFragment extends RewardBaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reward_all,container, false);
        return view;
    }

    @Override
    protected void init() {

        status = -1;
        swipeRefreshLayout = (SwipeRefreshLayout)context.findViewById(R.id.swipe_reward_all);
        layout = (LinearLayout)context.findViewById(R.id.reward_all_null);
        mLoadListView = (LoadListView)context.findViewById(R.id.loadlistview_all);
        textView = (TextView)context.findViewById(R.id.textView1);

        super.init();
    }
}
