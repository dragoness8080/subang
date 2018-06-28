package com.gcs.suban.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.InventoryMemberBean;

import java.util.List;

public class AchievementAdapter extends BaseListAdapter<InventoryMemberBean> {

    protected class ViewHolder{
        public TextView Tv_nickname;
        public TextView Tv_team_balance;
        public TextView Tv_ratio;
        public TextView Tv_static;
        public ImageView Iv_avatar;
    }

    public AchievementAdapter(Context context, List<InventoryMemberBean> list) {
        super(context, list);
        InitImageLoader();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item_achievement, null);
            viewHolder.Tv_nickname = (TextView)convertView.findViewById(R.id.settle_name);
            viewHolder.Tv_team_balance = (TextView)convertView.findViewById(R.id.team_balance);
            viewHolder.Tv_ratio = (TextView)convertView.findViewById(R.id.settle_ratio);
            viewHolder.Tv_static = (TextView)convertView.findViewById(R.id.stock_static);
            viewHolder.Iv_avatar = (ImageView)convertView.findViewById(R.id.settle_avatar);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            convertView.setTag(viewHolder);
        }

        final InventoryMemberBean bean = listItems.get(position);
        viewHolder.Tv_nickname.setText(bean.nickname);
        viewHolder.Tv_team_balance.setText(bean.balance);
        viewHolder.Tv_ratio.setText(bean.ratio + "%");
        viewHolder.Tv_static.setBackgroundResource(bean.gradeid == 0 ? R.drawable.order_status_close : R.drawable.order_staus_finish);
        imageLoader.displayImage(bean.imgUrl,viewHolder.Iv_avatar,options);

        return convertView;
    }
}
