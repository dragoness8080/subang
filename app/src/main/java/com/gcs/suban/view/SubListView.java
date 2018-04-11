package com.gcs.suban.view;

import android.widget.ListView;

public class SubListView extends ListView {
	public SubListView(android.content.Context context,
			android.util.AttributeSet attrs) {
		super(context, attrs);
	}

	
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}

}
