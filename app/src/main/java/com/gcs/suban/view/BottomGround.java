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
		mWidth = w; // ��ͼ������
		mHeight = h; // ��ͼ����߶�
		sLeft = sTop = 0; // �ﾶ�� ����ϵ�����
		sRight = mWidth; // �ﾶ�� ��ռ�����ȫ��
		sBottom = mHeight * 0.8f; // �ﾶ���ײ� ռȫ��İٷ�֮��ʮ�� ����Ԥ���ٷ�֮��ʮ�Ŀռ仭��ť��Ӱ��
		RectF sRectF = new RectF(sLeft, sTop, sBottom, sBottom);
		sPath.arcTo(sRectF, 90, 180);
		sRectF.left = sRight - sBottom;
		sRectF.right = sRight;
		sPath.arcTo(sRectF, 270, 180);
		sPath.close(); // path׼���ﾶ����·��
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setAntiAlias(true);
		paint.setStyle(Style.FILL);
		paint.setColor(0x00ffffff);
		paint.setAlpha(0x50);
		canvas.drawPath(sPath, paint); // �����ﾶ��
		paint.reset();
	}
}