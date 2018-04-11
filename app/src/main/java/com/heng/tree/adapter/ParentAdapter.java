package com.heng.tree.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.heng.tree.entity.ChildEntity;
import com.heng.tree.entity.ParentEntity;
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
 *         ??????????????
 *
 * <br/>
 * <br/>
 *
 *         ???? {@link #getChildView(int, int, boolean, View, ViewGroup)}<b><font
 *         color='#ff00ff' size='2'>???????</font></b>
 *
 * */

public class ParentAdapter extends BaseExpandableListAdapter {

	private Context mContext;

	private ArrayList<ParentEntity> mParents;

	private OnChildTreeViewClickListener mTreeViewClickListener;

	protected ImageLoader imageLoader;
	protected DisplayImageOptions options;

	//private UserInfo userInfo;

	public ParentAdapter(Context context, ArrayList<ParentEntity> parents) {
		this.mContext = context;
		this.mParents = parents;
		InitImageLoader();
	}

	@Override
	public ChildEntity getChild(int groupPosition, int childPosition) {
		return mParents.get(groupPosition).getChilds().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mParents.get(groupPosition).getChilds() != null ? mParents
				.get(groupPosition).getChilds().size() : 0;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isExpanded, View convertView, ViewGroup parent) {

		final ExpandableListView eListView = getExpandableListView();

		ArrayList<ChildEntity> childs = new ArrayList<ChildEntity>();

		final ChildEntity child = getChild(groupPosition, childPosition);

		childs.add(child);

		final ChildAdapter childAdapter = new ChildAdapter(this.mContext,
				childs);

		eListView.setAdapter(childAdapter);

		/**
		 * @author Apathy????
		 *
		 *         ?????ExpandableListView????????????????
		 * */
		eListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView arg0, View arg1,
					int groupIndex, int childIndex, long arg4) {

				if (mTreeViewClickListener != null) {

					mTreeViewClickListener.onClickPosition(groupPosition,
							childPosition, childIndex);
				}
				return false;
			}
		});


		eListView.setOnGroupExpandListener(new OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {

				LayoutParams lp = new LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, (child
								.getChildNames().size() + 1)
								* (int) mContext.getResources().getDimension(
										R.dimen.list_hight));
				eListView.setLayoutParams(lp);
			}
		});

		eListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
			@Override
			public void onGroupCollapse(int groupPosition) {

				LayoutParams lp = new LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, (int) mContext
								.getResources().getDimension(
										R.dimen.list_hight));
				eListView.setLayoutParams(lp);
			}
		});
		return eListView;

	}

	public ExpandableListView getExpandableListView() {
		ExpandableListView mExpandableListView = new ExpandableListView(
				mContext);
		LayoutParams lp = new LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, (int) mContext
						.getResources().getDimension(
								R.dimen.list_hight));
		mExpandableListView.setLayoutParams(lp);
		mExpandableListView.setDividerHeight(0); // ???group???????
		mExpandableListView.setChildDivider(null);// ???child???????
		//mExpandableListView.setGroupIndicator(null);// ????????-?????????
		return mExpandableListView;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mParents.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return mParents != null ? mParents.size() : 0;
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
					R.layout.list_group_team, null);
			holder = new GroupHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (GroupHolder) convertView.getTag();
		}
		holder.update(mParents.get(groupPosition));
		holder.Img_notice.setTag(groupPosition);
		holder.Img_notice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int position=(int) v.getTag();

				if(RongIM.getInstance() != null)
				{
				RongIM.getInstance().startPrivateChat(mContext,mParents.get(position).getId(), "???????");
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

		private TextView parentGroupTV;
		public ImageView Img_logo;
		public ImageView Img_notice;
		public TextView tv_child_count;

		public GroupHolder(View v) {
			parentGroupTV = (TextView) v.findViewById(R.id.tv_nickname);
			Img_logo= (ImageView) v.findViewById(R.id.img_logo);
			Img_notice=(ImageView) v.findViewById(R.id.img_notice);
            tv_child_count= (TextView) v.findViewById(R.id.child_count);
		}

		public void update(ParentEntity model) {
			parentGroupTV.setText(model.getGroupName());
			parentGroupTV.setTextColor(model.getGroupColor());
			imageLoader.displayImage(model.getLogo(),
					Img_logo, options);

			if(model.getChilds().size()==0)
			{
				parentGroupTV.setTextColor(Color.parseColor("#000000"));
			}
			else
			{
				parentGroupTV.setTextColor(Color.parseColor("#FF0000"));
			}
			int count = 0;
			if (model.getChilds().size()>0) {
				for (int i = 0; i < model.getChilds().size(); i++) {
					count = count + model.getChilds().get(i).getTeamcount().size();
				}
			}
			tv_child_count.setText(model.getChilds().size()+"/"+count);
		}
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

	/**
	 * @author Apathy????
	 *
	 *         ????????ExpandableListView????????
	 * */
	public void setOnChildTreeViewClickListener(
			OnChildTreeViewClickListener treeViewClickListener) {
		this.mTreeViewClickListener = treeViewClickListener;
	}

	/**
	 * @author Apathy????
	 *
	 *         ?????ExpandableListView??????????
	 * */
	public interface OnChildTreeViewClickListener {

		void onClickPosition(int parentPosition, int groupPosition,
				int childPosition);
	}

	// imageLoader?????
		protected void InitImageLoader() {
			imageLoader = ImageLoader.getInstance();
			options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.bg_pic) // ????????????????????
				.showImageForEmptyUri(R.drawable.bg_pic) // ??????Uri????????????????????
				.showImageOnFail(R.drawable.bg_pic) // ??????????????????��??????????????
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT) // ????????
				.bitmapConfig(Bitmap.Config.RGB_565)// ???????????????//
				 .cacheInMemory(true) // ??????????????????????
				 .cacheOnDisk(true) // ?????????????????SD????
				 .displayer(new RoundedBitmapDisplayer(90)) // ???��??????
				.build(); // ???????
		}

}
