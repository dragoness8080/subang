package com.gcs.suban.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public class LoadGridView extends GridView implements OnScrollListener {
    private int totalItemCount;
    private int lastVisibleItem;
    private boolean isLoading;
    private boolean isComplete = false;
    private onLoadGridViewListener loadGridViewListener;


    public LoadGridView(Context context) {
        super(context);
        initView();
    }

    public LoadGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoadGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (totalItemCount == lastVisibleItem
                && scrollState == SCROLL_STATE_IDLE) {
            if (!isLoading) {
                isLoading = true;
                loadGridViewListener.onLoad();
            }
        }
    }

    public void setOnLoadGridViewListener(onLoadGridViewListener loadGridViewListener){
        this.loadGridViewListener = loadGridViewListener;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.lastVisibleItem = firstVisibleItem + visibleItemCount;
        this.totalItemCount = totalItemCount;
    }

    public interface onLoadGridViewListener{
        public void onLoad();
    }


    private void initView(){
        this.setOnScrollListener(this);
    }

    public void loadComplete() {
        isLoading = false;
    }

    public void setComplete(boolean is) {
        this.isComplete=is;
    }
}
