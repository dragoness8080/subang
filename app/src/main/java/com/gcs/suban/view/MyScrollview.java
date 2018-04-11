package com.gcs.suban.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * Created by King on 2017/1/19.
 * RecycleView兼容的ScrollView
 */

public class MyScrollview extends ScrollView {
    private int downX;
    private int downY;
    private int mTouchSlop;
    private OnScrollToBottomListener onScrollToBottom;
    private OnScrollToLongListener onScrollToLong;

    public MyScrollview(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(e);
    }

    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
                                  boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (/*getY()!=0&&*/scrollY > 0) {
            if (null != onScrollToBottom) {
                onScrollToBottom.onScrollBottomListener(clampedY);
            }
            if (onScrollToLong != null) {
                onScrollToLong.onScrolToLong(scrollY);
            }
        } else {
            if (onScrollToLong != null) {
                onScrollToLong.onScrolToLong(scrollY);
            }
        }
    }

    public interface OnScrollToBottomListener {
        void onScrollBottomListener(boolean isBottom);
    }

    public void setOnScrollToBottomLintener(OnScrollToBottomListener listener) {
        onScrollToBottom = listener;
    }
    public interface OnScrollToLongListener{
        void onScrolToLong(int distance);
    }
    public void setOnScrollToLongListener(OnScrollToLongListener listener) {
        onScrollToLong = listener;
    }
}
