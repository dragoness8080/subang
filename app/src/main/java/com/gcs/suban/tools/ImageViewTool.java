package com.gcs.suban.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

public class ImageViewTool {
	/** 
	* ����ӦͼƬ��ImageView 
	* 
	* @param context �����Ķ��� 
	* @param image imageveiw ���� 
	* @param bitmap ���� 
	*/ 
	public static void setImageViewMathParent(Context context, 
	ImageView image, Bitmap bitmap) {

	    //���ImageView�Ĳ�����
	    ViewGroup.LayoutParams vgl = image.getLayoutParams();

	    if (bitmap == null) {
	        return;
	    }
	     //��ȡbitmap�Ŀ��
	    float bitWidth = bitmap.getWidth();
	    //��ȡbitmap�Ŀ��
	    float bithight = bitmap.getHeight();

	    //�����ͼƬ�Ŀ�߱ȣ�Ȼ����ͼƬ�ı���ȥ����ͼƬ
	    float bitScalew = bitWidth / bithight;
	    //�����ͼƬ�Ŀ���ڸ�  �������Ļ�����֮������ͼƬ�Ŀ� �߰��ձ�������
	    float imgWidth = getScreenWith(context) ;
	    //�����ͼƬ�ĸߴ��ڿ�  �������Ļ������֮һ����ͼƬ�ĸ� ���ձ�������
	    float imgHight =getScreenHeight(context) ;
	    //���ͼƬ��ȴ��ڸ߶�
	    if (bitWidth > bithight) {
	        vgl.width = (int) imgWidth;
	        vgl.height = (int) (imgWidth / bitScalew);

	    } else {
	        //��ͼƬ�ĸ߶ȴ��ڿ�ȵ�
	        vgl.width = (int) (imgHight * bitScalew);
	        vgl.height = (int) imgHight;

	    }
	    //����ͼƬ����ImageView�ؼ�
	    image.setScaleType(ImageView.ScaleType.CENTER_CROP);
	    //�ȱ�������
	    image.setAdjustViewBounds(true);
	    image.setLayoutParams(vgl);
	    image.setImageBitmap(bitmap);


	}
	//�����ǻ�ȡ��Ļ��Ⱥ͸߶ȵķ�������÷��ڸ���Ļ��صĹ�����

	@SuppressWarnings("deprecation")
	public static int getScreenWith(Context context) {
	    WindowManager manager = (WindowManager) context
	            .getSystemService(Context.WINDOW_SERVICE);
	    Display display = manager.getDefaultDisplay();
	    return display.getWidth();
	}

	@SuppressWarnings("deprecation")
	public static int getScreenHeight(Context context) {
	    WindowManager manager = (WindowManager) context
	            .getSystemService(Context.WINDOW_SERVICE);
	    Display display = manager.getDefaultDisplay();
	    return display.getHeight();
	}
}
