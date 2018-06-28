package com.gcs.suban.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gcs.suban.R;
import com.gcs.suban.view.LoadListView;

public class InventorySelfPayFragment extends InventorySelfBaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selfbuy_pay,container, false);
        return view;
    }

    @Override
    protected void init() {
        status = 1;
        loadListView = (LoadListView)context.findViewById(R.id.list_pay);
        layout = (LinearLayout)context.findViewById(R.id.layout_null_pay);
        swipeRefreshLayout = (SwipeRefreshLayout)context.findViewById(R.id.swipeRefreshLayout_pay);
        super.init();
    }
}