package com.gcs.suban.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class TopSoap extends View {
	private final Paint paint = new Paint();
	private final Path sPath = new Path();
	private final Path bPath = new Path();
	private final RectF bRectF = new RectF();
	private int mWidth, mHeight;
	private float sLeft, sTop, sRight, sBottom;
	private float bOffset;
	private float bRadius, bStrokeWidth;
	private float bLeft, bTop, bRight, bBottom;
	public TopSoap(Context context, AttributeSet attrs) {
		super(context, attrs);
		setLayerType(LAYER_TYPE_SOFTWARE, null);
	}

	public TopSoap(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = (int) (widthSize * 0.432f);
		setMeasuredDimension(widthSize, heightSize);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mWidth = w;
		mHeight = h;
		sLeft = sTop = 0;
		sRight = mWidth;
		sBottom = mHeight * 0.91f;
		bLeft = bTop = 0;
		bRight = bBottom = sBottom;
		final float halfHeightOfS = (sBottom - sTop) / 2;
		bRadius = halfHeightOfS * 0.95f;
		bOffset = bRadius * 0.2f;
		bStrokeWidth = (halfHeightOfS - bRadius) * 2;
		RectF sRectF = new RectF(sLeft, sTop, sBottom, sBottom);
		sPath.arcTo(sRectF, 90, 180);
		sRectF.left = sRight - sBottom;
		sRectF.right = sRight;
		sPath.arcTo(sRectF, 270, 180);
		sPath.close();
		bRectF.left = bLeft;
		bRectF.right = bRight;
		bRectF.top = bTop + bStrokeWidth / 2;
		bRectF.bottom = bBottom - bStrokeWidth / 2;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		calcBPath(15);
		paint.setStyle(Style.FILL);
		paint.setColor(0xffffffff);
		canvas.drawPath(bPath, paint);
	}

	private void calcBPath(float percent) {
		bPath.reset();
		bRectF.left = bLeft + bStrokeWidth / 2;
		bRectF.right = bRight - bStrokeWidth / 2;
		bPath.arcTo(bRectF, 90, 180);
		bRectF.left = bLeft + percent * bOffset + bStrokeWidth / 2;
		bRectF.right = bRight + percent * bOffset - bStrokeWidth / 2;
		bPath.arcTo(bRectF, 270, 180);
		bPath.close();
	}

}
