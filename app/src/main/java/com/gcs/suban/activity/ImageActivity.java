package com.gcs.suban.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ImageActivity extends BaseActivity implements OnPageChangeListener {
	private String uri;
	private int page;
	/**
	 * ViewPager
	 */
	private ViewPager viewPager;

	/**
	 * 装点点的ImageView数组
	 */
	private ImageView[] tips;

	/**
	 * 装ImageView数组
	 */
	private ImageView[] mImageViews;

	private List<String> images;

	private ImageView Ibtn_down;

	File appDir;

	private Bitmap bitmap = null;

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

	@Override
	protected void init() {
		Intent intent = getIntent();
		uri = intent.getStringExtra("url");
		page = intent.getIntExtra("page", 0);
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_bigimage);
		InitImageLoader();

		ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);
		viewPager = (ViewPager) findViewById(R.id.viewPager);

		Ibtn_down = (ImageButton) context.findViewById(R.id.ibtn_down);

		Ibtn_down.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e(TAG, page+"");
				getBitmap(page);
			}
		});

		// 载入图片资源ID
		images = Arrays.asList(uri.split(","));

		// 将点点加入到ViewGroup中
		tips = new ImageView[images.size()];
		for (int i = 0; i < tips.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setLayoutParams(new LayoutParams(10, 10));
			tips[i] = imageView;
			if (i == 0) {
				tips[i].setBackgroundResource(R.drawable.icon_spot_focused);
			} else {
				tips[i].setBackgroundResource(R.drawable.icon_spot_unfocused);
			}

			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
			layoutParams.leftMargin = 5;
			layoutParams.rightMargin = 5;
			group.addView(imageView, layoutParams);
		}

		// 将图片装载到数组中
		mImageViews = new ImageView[images.size()];
		for (int i = 0; i < mImageViews.length; i++) {
			ImageView imageView = new ImageView(this);
			mImageViews[i] = imageView;
			String img = images.get(i);
			img = img.replace("\"", "");
			img = img.replace("\\", "");
			imageLoader.displayImage(img, mImageViews[i], options);
		}

		// 设置Adapter
		viewPager.setAdapter(new MyAdapter());
		// 设置监听，主要是设置点点的背景
		viewPager.addOnPageChangeListener(this);
		// 设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
		viewPager.setCurrentItem(page);
	}

	public class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return images.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			// ((ViewGroup) container).removeViewAt(position);
		}

		@Override
		public Object instantiateItem(View container, int position) {
			try {
				((ViewGroup) container).addView(mImageViews[position]);
			} catch (Exception e) {
				// handler something
				e.printStackTrace();
			}
			return mImageViews[position];
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		setImageBackground(arg0 % mImageViews.length);

		page = arg0;
		Log.e(TAG, page + "");
	}

	/**
	 * 设置选中的tip的背景
	 * 
	 * @param selectItems
	 */
	private void setImageBackground(int selectItems) {
		for (int i = 0; i < tips.length; i++) {
			if (i == selectItems) {
				tips[i].setBackgroundResource(R.drawable.icon_spot_focused);
			} else {
				tips[i].setBackgroundResource(R.drawable.icon_spot_unfocused);
			}
		}
	}

	public void getBitmap(int i) {
		Log.e(TAG, images.get(i));
		String img=images.get(i);
		img = img.replace("\"", "");
		img = img.replace("\\", "");
		ImageLoader.getInstance().loadImage(img,
				new SimpleImageLoadingListener() {

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						bitmap = loadedImage;
						saveImage(bitmap);
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
		Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		Uri uri = Uri.fromFile(appDir);
		intent.setData(uri);
		context.sendBroadcast(intent);
	}

}
