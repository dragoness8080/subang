package com.gcs.suban.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gcs.suban.R;
import com.gcs.suban.view.LoadListView;

public class InventoryLogAllFragment extends InventoryLogBaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_all,container, false);
        return view;
    }

    @Override
    protected void init() {
        types = "";

        listView = (LoadListView)context.findViewById(R.id.list_all);
        layout = (LinearLayout)context.findViewById(R.id.layout_null_collect);
        swipeRefreshLayout = (SwipeRefreshLayout)context.findViewById(R.id.swipeRefreshLayout_all);

        super.init();
    }
}
