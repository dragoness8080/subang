package com.gcs.suban.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.tools.ImageViewTool;
import com.gcs.suban.tools.ToastTool;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class BigImageActivity extends BaseActivity {
	private Uri pic;

	private String uri;
	private ImageView imageView;
	private ImageView Ibtn_down;

	private Bitmap bitmap;
	ProgressBar progressBar1;
	
	File appDir;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		default:
			break;
		}
	}
	
	protected void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_image);
		InitImageLoader();

		imageView = (ImageView) context.findViewById(R.id.img_big);
		progressBar1 = (ProgressBar) context.findViewById(R.id.progressBar1);
		Ibtn_down= (ImageButton)context.findViewById(R.id.ibtn_down);
		Ibtn_down.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(bitmap!=null)
				saveImage(bitmap);
				else {
					ToastTool.showToast(context, "正在加载图片");
				}
			}
		});
	}

	@Override
	protected void init() {
		initView();
		InitImageLoader();

		Intent intent = getIntent();
		pic = intent.getParcelableExtra("uri");
		uri = pic.toString();

		ImageLoader.getInstance().loadImage(uri, new SimpleImageLoadingListener(){  
			  
            @Override  
            public void onLoadingComplete(String imageUri, View view,  
                    Bitmap loadedImage) {  
               bitmap=loadedImage;
               progressBar1.setVisibility(View.GONE);
               ImageViewTool.setImageViewMathParent(context, imageView, bitmap);
            }  
              
        });  
	}
	
	public void saveImage(Bitmap bmp) {
	     appDir = new File(Environment.getExternalStorageDirectory(), "Suban");
	    if (!appDir.exists()) {
	        appDir.mkdir();
	    }
	    String fileName = System.currentTimeMillis() + ".jpg";
	    File file = new File(appDir, fileName);
	    try {
	        FileOutputStream fos = new FileOutputStream(file);
	        bmp.compress(CompressFormat.JPEG, 100, fos);
	        fos.flush();
	        fos.close();
	    	Toast.makeText(context, "保存图片成功", Toast.LENGTH_SHORT).show();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
		// 其次把文件插入到系统图库
		try {
			MediaStore.Images.Media.insertImage(context.getContentResolver(),
					file.getAbsolutePath(), fileName, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    
	    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		Uri uri = Uri.fromFile(file);
		intent.setData(uri);
		context.sendBroadcast(intent);
	}


}
