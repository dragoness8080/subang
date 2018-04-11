package com.gcs.suban.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class BottomGround extends View {
	private final Paint paint = new Paint();
	private final Path sPath = new Path();

	private int mWidth, mHeight;
	private float sLeft, sTop, sRight, sBottom;
	public BottomGround(Context context) {
		this(context, null);
	}

	public BottomGround(Context context, AttributeSet attrs) {
		super(context, attrs);
		setLayerType(LAYER_TYPE_SOFTWARE, null);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = (int) (widthSize * 0.275f);
		setMeasuredDimension(widthSize, heightSize);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mWidth = w; // 视图自身宽度
		mHeight = h; // 视图自身高度
		sLeft = sTop = 0; // 田径场 左和上的坐标
		sRight = mWidth; // 田径场 右占自身的全部
		sBottom = mHeight * 0.8f; // 田径场底部 占全身的百分之八十， 下面预留百分之二十的空间画按钮阴影。
		RectF sRectF = new RectF(sLeft, sTop, sBottom, sBottom);
		sPath.arcTo(sRectF, 90, 180);
		sRectF.left = sRight - sBottom;
		sRectF.right = sRight;
		sPath.arcTo(sRectF, 270, 180);
		sPath.close(); // path准备田径场的路径
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setAntiAlias(true);
		paint.setStyle(Style.FILL);
		paint.setColor(0x00ffffff);
		paint.setAlpha(0x50);
		canvas.drawPath(sPath, paint); // 画出田径场
		paint.reset();
	}
}