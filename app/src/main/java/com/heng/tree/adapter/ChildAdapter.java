package com.heng.tree.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gcs.suban.R;
import com.heng.tree.entity.ChildEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;

import io.rong.imkit.RongIM;

/**
 * 
 * @author Apathy????
 * 
 * <br/>
 * <br/>
 * 
 *         ??????????????
 * 
 * <br/>
 * <br/>
 * 
 *         ????{@link #isChildSelectable(int,int)} <b><font color='#ff00ff'
 *         size='2'>??????true</font></b>
 * 
 * */
public class ChildAdapter extends BaseExpandableListAdapter {

	private Context mContext;// ??????

	private ArrayList<ChildEntity> mChilds;// ?????
	
	protected ImageLoader imageLoader;
	protected DisplayImageOptions options; // ???????????????

	public ChildAdapter(Context context, ArrayList<ChildEntity> childs) {
		this.mContext = context;
		this.mChilds = childs;
		InitImageLoader();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mChilds.get(groupPosition).getChildNames() != null ? mChilds
				.get(groupPosition).getChildNames().size() : 0;
	}

	@Override
	public String getChild(int groupPosition, int childPosition) {
		if (mChilds.get(groupPosition).getChildNames() != null
				&& mChilds.get(groupPosition).getChildNames().size() > 0)
			return mChilds.get(groupPosition).getChildNames()
					.get(childPosition).toString();
		return null;
	}

	public String getChildIds(int groupPosition, int childPosition) {
		if (mChilds.get(groupPosition).getChildIds() != null
				&& mChilds.get(groupPosition).getChildIds().size() > 0)
			return mChilds.get(groupPosition).getChildIds().get(childPosition)
					.toString();
		return null;
	}
	
	public String getChildCounts(int groupPosition, int childPosition) {
		if (mChilds.get(groupPosition).getChildIds() != null
				&& mChilds.get(groupPosition).getChildIds().size() > 0)
			return mChilds.get(groupPosition).getTeamcount().get(childPosition)
					.toString();
		return null;
	}

	public String getChildLogos(int groupPosition, int childPosition) {
		if (mChilds.get(groupPosition).getChildLogos() != null
				&& mChilds.get(groupPosition).getChildLogos().size() > 0)
			return mChilds.get(groupPosition).getChildLogos()
					.get(childPosition).toString();
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isExpanded, View convertView, ViewGroup parent) {
		ChildHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.list_clist_team, null);
			holder = new ChildHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ChildHolder) convertView.getTag();
		}
		holder.update(groupPosition, childPosition);
		imageLoader.displayImage(getChildLogos(groupPosition, childPosition),
				holder.Img_logo, options);

		holder.Img_notice.setTag(R.id.tag_first,groupPosition);
		holder.Img_notice.setTag(R.id.tag_second,childPosition);
		holder.Img_notice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int position1 = (int) v.getTag(R.id.tag_first);
				int position2 = (int) v.getTag(R.id.tag_second);

				if (RongIM.getInstance() != null) {
					RongIM.getInstance().startPrivateChat(mContext,
							getChildIds(position1, position2), "???????");
				}
			}
		});

		return convertView;
	}

	/**
	 * @author Apathy????
	 * 
	 *         Holder???
	 * */
	class ChildHolder {

		private TextView childChildTV;
		public ImageView Img_notice;
		public ImageView Img_logo;
		public RatingBar ratingBar;

		public ChildHolder(View v) {
			childChildTV = (TextView) v.findViewById(R.id.tv_nickname);
			Img_notice = (ImageView) v.findViewById(R.id.img_notice);
			Img_logo= (ImageView) v.findViewById(R.id.img_logo);
			ratingBar= (RatingBar) v.findViewById(R.id.ratingBar);
			
		}

		public void update(int a,int b) {
			childChildTV.setText(getChild(a, b));
			childChildTV.setTextColor(Color.parseColor("#000000"));
			
			int i=Integer.parseInt(getChildCounts(a, b));
			
			Log.e("a"+"  "+"b", i+"");

			if(i==0)
			{
				childChildTV.setTextColor(Color.parseColor("#000000"));
			}
			else
			{
				childChildTV.setTextColor(Color.parseColor("#FF0000"));
			}

			if(i>=10&&i<20)
			{
				ratingBar.setRating(1);
				ratingBar.setVisibility(View.VISIBLE);
			}
			else if(i>=20&&i<30)
			{
				ratingBar.setRating(2);
				ratingBar.setVisibility(View.VISIBLE);
			}
			else if(i>=30&&i<40)
			{
				ratingBar.setRating(3);
				ratingBar.setVisibility(View.VISIBLE);
			}
			else if(i>=40&&i<50)
			{
				ratingBar.setRating(4);
				ratingBar.setVisibility(View.VISIBLE);
			}
			else if(i>=50)
			{
				ratingBar.setRating(5);
				ratingBar.setVisibility(View.VISIBLE);
			}
			else {
				ratingBar.setRating(0);
				ratingBar.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public Object getGroup(int groupPosition) {
		if (mChilds != null && mChilds.size() > 0)
			return mChilds.get(groupPosition);
		return null;
	}

	@Override
	public int getGroupCount() {
		return mChilds != null ? mChilds.size() : 0;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.list_child_team, null);
			holder = new GroupHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (GroupHolder) convertView.getTag();
		}
		holder.update(mChilds.get(groupPosition));

		holder.Img_notice.setTag(groupPosition);
		holder.Img_notice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int position = (int) v.getTag();

				if (RongIM.getInstance() != null) {
					RongIM.getInstance().startPrivateChat(mContext,
							mChilds.get(position).getGroupId(), "???????");
				}
			}
		});

		return convertView;
	}

	/**
	 * @author Apathy????
	 * 
	 *         Holder???
	 * */
	class GroupHolder {

		private TextView childGroupTV;
		public ImageView Img_notice;
		public ImageView Img_logo;
		public TextView tv_group_count;

		public GroupHolder(View v) {
			childGroupTV = (TextView) v.findViewById(R.id.tv_nickname);
			Img_notice = (ImageView) v.findViewById(R.id.img_notice);
			Img_logo= (ImageView) v.findViewById(R.id.img_logo);
			tv_group_count= (TextView) v.findViewById(R.id.tv_group_count);
		}

		public void update(ChildEntity model) {
			childGroupTV.setText(model.getGroupName());
			childGroupTV.setTextColor(model.getGroupColor());
			imageLoader.displayImage(model.getGroupLogo(),
					Img_logo, options);
			
			if(model.getChildIds().size()==0)
			{
				childGroupTV.setTextColor(Color.parseColor("#000000"));
			}
			else
			{
				childGroupTV.setTextColor(Color.parseColor("#FF0000"));
			}
			tv_group_count.setText("("+model.getChildIds().size()+")");
		}
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		/**
		 * ==============================================
		 * ?????????true??????????????????????===============
		 * ==============================================
		 **/
		return true;
	}
	
	// imageLoader?????
			protected void InitImageLoader() {
				imageLoader = ImageLoader.getInstance();	
				options = new DisplayImageOptions.Builder()
					.showImageOnLoading(R.drawable.bg_pic) // ????????????????????
					.showImageForEmptyUri(R.drawable.bg_pic) // ??????Uri????????????????????
					.showImageOnFail(R.drawable.bg_pic) // ??????????????????з┘??????????????
					.imageScaleType(ImageScaleType.IN_SAMPLE_INT) // ????????
					.bitmapConfig(Bitmap.Config.RGB_565)// ???????????????//
					 .cacheInMemory(true) // ??????????????????????
					 .cacheOnDisk(true) // ?????????????????SD????
					 .displayer(new RoundedBitmapDisplayer(90)) // ???ио??????
					.build(); // ???????
			}

}
