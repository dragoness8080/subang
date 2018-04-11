package com.gcs.suban.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.AbsListView.OnScrollListener;

import com.gcs.suban.R;

public class LoadExpandableListView extends ExpandableListView implements OnScrollListener {
	private View footer;
	private int lastVisibleItem;
	private int totalItemCount;
	private boolean isLoading;
	private boolean isComplete=false;
	private onLoadListViewListener onLoadListViewListener;

	public LoadExpandableListView(Context context) {
		super(context);
		initview(context);
	}

	public LoadExpandableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview(context);
	}

	public LoadExpandableListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initview(context);
	}

	@SuppressLint("InflateParams")
	private void initview(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		footer = inflater.inflate(R.layout.listview_footer, null);
		footer.findViewById(R.id.rlyt_footer).setVisibility(View.GONE);
		footer.findViewById(R.id.rlyt_complete).setVisibility(View.GONE);
		this.addFooterView(footer,null,false);
		this.setOnScrollListener(this);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (totalItemCount == lastVisibleItem
				&& scrollState == SCROLL_STATE_IDLE) {
			if (!isLoading) {
				isLoading = true;
				footer.findViewById(R.id.rlyt_footer).setVisibility(
						View.VISIBLE);
				footer.findViewById(R.id.rlyt_complete)
						.setVisibility(View.GONE);
				onLoadListViewListener.onLoad();
			}
		}
	}

	public void setOnLoadListViewListener(
			onLoadListViewListener onLoadListViewListener) {
		this.onLoadListViewListener = onLoadListViewListener;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.lastVisibleItem = firstVisibleItem + visibleItemCount;
		this.totalItemCount = totalItemCount;
	}

	public interface onLoadListViewListener {
		public void onLoad();
	}

	public void loadComplete() {
		isLoading = false;
		if (isComplete == false) {
			footer.findViewById(R.id.rlyt_footer).setVisibility(View.GONE);
			footer.findViewById(R.id.rlyt_complete).setVisibility(View.GONE);
		} else {
			footer.findViewById(R.id.rlyt_footer).setVisibility(View.GONE);
			footer.findViewById(R.id.rlyt_complete).setVisibility(View.VISIBLE);
		}
	}
	
	public void setComplete(boolean is) {
		this.isComplete=is;
	}
}
