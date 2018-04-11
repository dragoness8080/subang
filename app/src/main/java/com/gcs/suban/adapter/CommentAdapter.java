package com.gcs.suban.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gcs.suban.R;
import com.gcs.suban.activity.ImageActivity;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.CommentBean;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CommentAdapter extends BaseListAdapter<CommentBean> {

	public CommentAdapter(Context context, List<CommentBean> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
		InitImageLoader();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.i("method", "getView" + position);
		// 自定义视图
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			// 获取list_item布局文件的视图
			convertView = mInflater.inflate(R.layout.list_item_comment, null);
			// 获取控件对象
			holder.logo = (ImageView) convertView.findViewById(R.id.logo);
			holder.username = (TextView) convertView
					.findViewById(R.id.tv_username);
			holder.ratingBar = (RatingBar) convertView
					.findViewById(R.id.ratingBar);
			holder.Tv_creattime = (TextView) convertView
					.findViewById(R.id.tv_createtime);
			
			holder.Rlyt_content = (RelativeLayout) convertView
					.findViewById(R.id.rlyt_content);
			holder.Rlyt_images = (RelativeLayout) convertView
					.findViewById(R.id.rlyt_images);
			holder.Rlyt_reply_content = (RelativeLayout) convertView
					.findViewById(R.id.rlyt_reply_content);
			holder.Rlyt_reply_images = (RelativeLayout) convertView
					.findViewById(R.id.rlyt_reply_images);
			holder.Rlyt_append_content = (RelativeLayout) convertView
					.findViewById(R.id.rlyt_append_content);
			holder.Rlyt_append_images = (RelativeLayout) convertView
					.findViewById(R.id.rlyt_append_images);
			holder.Rlyt_append_reply_content = (RelativeLayout) convertView
					.findViewById(R.id.rlyt_append_reply_content);
			holder.Rlyt_append_reply_images = (RelativeLayout) convertView
					.findViewById(R.id.rlyt_append_reply_images);

			holder.Tv_content = (TextView) convertView
					.findViewById(R.id.tv_content);
			holder.Tv_reply_content = (TextView) convertView
					.findViewById(R.id.tv_reply_content);
			holder.Tv_append_content = (TextView) convertView
					.findViewById(R.id.tv_append_content);
			holder.Tv_append_reply_content = (TextView) convertView
					.findViewById(R.id.tv_append_reply_content);

			holder.imageView1 = (ImageView) convertView
					.findViewById(R.id.imageView1);
			holder.imageView2 = (ImageView) convertView
					.findViewById(R.id.imageView2);
			holder.imageView3 = (ImageView) convertView
					.findViewById(R.id.imageView3);
			holder.imageView4 = (ImageView) convertView
					.findViewById(R.id.imageView4);
			holder.imageView5 = (ImageView) convertView
					.findViewById(R.id.imageView5);

			holder.reply_imageView1 = (ImageView) convertView
					.findViewById(R.id.reply_imageView1);
			holder.reply_imageView2 = (ImageView) convertView
					.findViewById(R.id.reply_imageView2);
			holder.reply_imageView3 = (ImageView) convertView
					.findViewById(R.id.reply_imageView3);
			holder.reply_imageView4 = (ImageView) convertView
					.findViewById(R.id.reply_imageView4);
			holder.reply_imageView5 = (ImageView) convertView
					.findViewById(R.id.reply_imageView5);

			holder.append_imageView1 = (ImageView) convertView
					.findViewById(R.id.append_imageView1);
			holder.append_imageView2 = (ImageView) convertView
					.findViewById(R.id.append_imageView2);
			holder.append_imageView3 = (ImageView) convertView
					.findViewById(R.id.append_imageView3);
			holder.append_imageView4 = (ImageView) convertView
					.findViewById(R.id.append_imageView4);
			holder.append_imageView5 = (ImageView) convertView
					.findViewById(R.id.append_imageView5);

			holder.append_reply_imageView1 = (ImageView) convertView
					.findViewById(R.id.append_reply_imageView1);
			holder.append_reply_imageView2 = (ImageView) convertView
					.findViewById(R.id.append_reply_imageView2);
			holder.append_reply_imageView3 = (ImageView) convertView
					.findViewById(R.id.append_reply_imageView3);
			holder.append_reply_imageView4 = (ImageView) convertView
					.findViewById(R.id.append_reply_imageView4);
			holder.append_reply_imageView5 = (ImageView) convertView
					.findViewById(R.id.append_reply_imageView5);

			// 设置控件集到convertView
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			convertView.setTag(holder);
		}
		holder.username.setText(listItems.get(position).nickname);
		imageLoader.displayImage(listItems.get(position).headimgurl,
				holder.logo, options);
		holder.ratingBar.setRating(listItems.get(position).level);
		holder.Tv_creattime.setText(listItems.get(position).createtime);

		holder.Tv_content.setText(listItems.get(position).content);
		holder.Tv_reply_content.setText(listItems.get(position).reply_content);
		holder.Tv_append_content
				.setText(listItems.get(position).append_content);
		holder.Tv_append_reply_content
				.setText(listItems.get(position).append_reply_content);

		// 评价图片
		String str = listItems.get(position).images;
		str = str.replace("[", "");
		str = str.replace("]", "");
		final String url1=str;
		List<String> images = Arrays.asList(str.split(","));

		List<ImageView> imageViews = new ArrayList<ImageView>();
		imageViews.add(holder.imageView1);
		imageViews.add(holder.imageView2);
		imageViews.add(holder.imageView3);
		imageViews.add(holder.imageView4);
		imageViews.add(holder.imageView5);

		for (int i = 0; i < imageViews.size(); i++) {
			imageViews.get(i).setVisibility(View.INVISIBLE);
		}
		for (int j = 0; j < images.size(); j++) {
			String img = images.get(j);
			img = img.replace("\"", "");
			img = img.replace("\\", "");
			imageLoader.displayImage(img, imageViews.get(j), options);
			imageViews.get(j).setTag(j+"");
			imageViews.get(j).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent =new Intent(context,ImageActivity.class);
					intent.putExtra("url", url1);
					intent.putExtra("page", Integer.parseInt(v.getTag().toString()));
					context.startActivity(intent);
				}
			});
			imageViews.get(j).setVisibility(View.VISIBLE);
		}

		// 回复图片
		String str2 = listItems.get(position).reply_images;
		str2 = str2.replace("[", "");
		str2 = str2.replace("]", "");
		final String url2=str;
		List<String> images2 = Arrays.asList(str2.split(","));

		List<ImageView> imageViews2 = new ArrayList<ImageView>();
		imageViews2.add(holder.reply_imageView1);
		imageViews2.add(holder.reply_imageView2);
		imageViews2.add(holder.reply_imageView3);
		imageViews2.add(holder.reply_imageView4);
		imageViews2.add(holder.reply_imageView5);

		for (int i = 0; i < imageViews2.size(); i++) {
			imageViews2.get(i).setVisibility(View.INVISIBLE);
		}
		for (int j = 0; j < images2.size(); j++) {
			String img = images2.get(j);
			img = img.replace("\"", "");
			img = img.replace("\\", "");
			imageLoader.displayImage(img, imageViews2.get(j), options);
			imageViews2.get(j).setTag(j+"");
			imageViews2.get(j).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent =new Intent(context,ImageActivity.class);
					intent.putExtra("url", url2);
					intent.putExtra("page", Integer.parseInt(v.getTag().toString()));
					context.startActivity(intent);
				}
			});
			imageViews2.get(j).setVisibility(View.VISIBLE);
		}

		// 追加评论图片
		String str3 = listItems.get(position).append_images;
		str3 = str3.replace("[", "");
		str3 = str3.replace("]", "");
		final String url3=str;
		List<String> images3 = Arrays.asList(str3.split(","));

		List<ImageView> imageViews3 = new ArrayList<ImageView>();
		imageViews3.add(holder.append_imageView1);
		imageViews3.add(holder.append_imageView2);
		imageViews3.add(holder.append_imageView3);
		imageViews3.add(holder.append_imageView4);
		imageViews3.add(holder.append_imageView5);

		for (int i = 0; i < imageViews3.size(); i++) {
			imageViews3.get(i).setVisibility(View.INVISIBLE);
		}
		for (int j = 0; j < images3.size(); j++) {
			String img = images3.get(j);
			img = img.replace("\"", "");
			img = img.replace("\\", "");
			imageLoader.displayImage(img, imageViews3.get(j), options);
			imageViews3.get(j).setTag(j+"");
			imageViews3.get(j).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent =new Intent(context,ImageActivity.class);
					intent.putExtra("url", url3);
					intent.putExtra("page", Integer.parseInt(v.getTag().toString()));
					context.startActivity(intent);
				}
			});
			imageViews3.get(j).setVisibility(View.VISIBLE);
		}

		// 追加评论回复图片
		String str4 = listItems.get(position).append_reply_images;
		str4 = str4.replace("[", "");
		str4 = str4.replace("]", "");
		final String url4=str;
		List<String> images4 = Arrays.asList(str4.split(","));

		List<ImageView> imageViews4 = new ArrayList<ImageView>();
		imageViews4.add(holder.append_reply_imageView1);
		imageViews4.add(holder.append_reply_imageView2);
		imageViews4.add(holder.append_reply_imageView3);
		imageViews4.add(holder.append_reply_imageView4);
		imageViews4.add(holder.append_reply_imageView5);

		for (int i = 0; i < imageViews4.size(); i++) {
			imageViews4.get(i).setVisibility(View.INVISIBLE);
		}
		for (int j = 0; j < images4.size(); j++) {
			String img = images4.get(j);
			img = img.replace("\"", "");
			img = img.replace("\\", "");
			imageLoader.displayImage(img, imageViews4.get(j), options);
			imageViews4.get(j).setTag(j+"");
			imageViews4.get(j).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent =new Intent(context,ImageActivity.class);
					intent.putExtra("url", url4);
					intent.putExtra("page", Integer.parseInt(v.getTag().toString()));
					context.startActivity(intent);
				}
			});
			imageViews4.get(j).setVisibility(View.VISIBLE);
		}

		// 评论,
		if (listItems.get(position).content.equals("")) {
			holder.Rlyt_content.setVisibility(View.GONE);
		} else {
			holder.Rlyt_content.setVisibility(View.VISIBLE);
		}

		if (listItems.get(position).images.equals("")) {
			holder.Rlyt_images.setVisibility(View.GONE);
		} else {
			holder.Rlyt_images.setVisibility(View.VISIBLE);
		}
		// 回复评论
		if (listItems.get(position).reply_content.equals("")) {
			holder.Tv_reply_content.setVisibility(View.GONE);
		} else {
			holder.Tv_reply_content.setVisibility(View.VISIBLE);
		}

		if (listItems.get(position).reply_images.equals("")) {
			holder.Rlyt_reply_images.setVisibility(View.GONE);
		} else {
			holder.Rlyt_reply_images.setVisibility(View.VISIBLE);
		}

		if (listItems.get(position).reply_images.equals("")
				&& listItems.get(position).reply_content.equals("")) {
			holder.Rlyt_reply_content.setVisibility(View.GONE);
		} else {
			holder.Rlyt_reply_content.setVisibility(View.VISIBLE);
		}

		// 追加评论
		if (listItems.get(position).append_content.equals("")) {
			holder.Tv_append_content.setVisibility(View.GONE);
		} else {
			holder.Tv_append_content.setVisibility(View.VISIBLE);
		}

		if (listItems.get(position).append_images.equals("")) {
			holder.Rlyt_append_images.setVisibility(View.GONE);
		} else {
			holder.Rlyt_append_images.setVisibility(View.VISIBLE);
		}

		if (listItems.get(position).append_images.equals("")
				&& listItems.get(position).append_content.equals("")) {
			holder.Rlyt_append_content.setVisibility(View.GONE);
		} else {
			holder.Rlyt_append_content.setVisibility(View.VISIBLE);
		}

		// 回复追加评论
		if (listItems.get(position).append_reply_content.equals("")) {
			holder.Tv_append_reply_content.setVisibility(View.GONE);
		} else {
			holder.Tv_append_reply_content.setVisibility(View.VISIBLE);
		}

		if (listItems.get(position).append_reply_images.equals("")) {
			holder.Rlyt_append_reply_images.setVisibility(View.GONE);
		} else {
			holder.Rlyt_append_reply_images.setVisibility(View.VISIBLE);
		}

		if (listItems.get(position).append_reply_images.equals("")
				&& listItems.get(position).append_reply_content.equals("")) {
			holder.Rlyt_append_reply_content.setVisibility(View.GONE);
		} else {
			holder.Rlyt_append_reply_content.setVisibility(View.VISIBLE);
		}

		return convertView;
	}

	private final class ViewHolder {
		public TextView username;
		public ImageView logo;
		public RatingBar ratingBar;
		public TextView Tv_creattime;
		public RelativeLayout Rlyt_content;
		public RelativeLayout Rlyt_images;
		public RelativeLayout Rlyt_reply_content;
		public RelativeLayout Rlyt_reply_images;
		public RelativeLayout Rlyt_append_content;
		public RelativeLayout Rlyt_append_images;
		public RelativeLayout Rlyt_append_reply_content;
		public RelativeLayout Rlyt_append_reply_images;

		public TextView Tv_content;
		public TextView Tv_reply_content;
		public TextView Tv_append_content;
		public TextView Tv_append_reply_content;

		public ImageView imageView1;
		public ImageView imageView2;
		public ImageView imageView3;
		public ImageView imageView4;
		public ImageView imageView5;

		public ImageView reply_imageView1;
		public ImageView reply_imageView2;
		public ImageView reply_imageView3;
		public ImageView reply_imageView4;
		public ImageView reply_imageView5;
		

		public ImageView append_imageView1;
		public ImageView append_imageView2;
		public ImageView append_imageView3;
		public ImageView append_imageView4;
		public ImageView append_imageView5;

		public ImageView append_reply_imageView1;
		public ImageView append_reply_imageView2;
		public ImageView append_reply_imageView3;
		public ImageView append_reply_imageView4;
		public ImageView append_reply_imageView5;

	}

}
