package com.gcs.suban.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.RewardBean;

import java.util.List;

public class RewardAdapter extends BaseListAdapter<RewardBean> {

    class ViewHolder{
        public TextView createtime;
        public TextView exchangetime;
        public ImageView thumb;
        public TextView title;
        public TextView rank;
        public TextView status;
    }

    public RewardAdapter(Context context, List<RewardBean> list) {
        super(context, list);
        InitImageLoader();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item_reward, null);
            viewHolder.createtime = (TextView)convertView.findViewById(R.id.reward_createtime);
            viewHolder.exchangetime = (TextView)convertView.findViewById(R.id.reward_exchangetime);
            viewHolder.rank = (TextView)convertView.findViewById(R.id.reward_rank);
            viewHolder.status = (TextView)convertView.findViewById(R.id.reward_status);
            viewHolder.title = (TextView)convertView.findViewById(R.id.reward_title);
            viewHolder.thumb = (ImageView)convertView.findViewById(R.id.reward_img);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
            convertView.setTag(viewHolder);
        }

        RewardBean bean = listItems.get(position);
        viewHolder.createtime.setText(bean.getCreatetime());
        if(bean.getExchangetime().equals("0")){
            viewHolder.exchangetime.setVisibility(View.GONE);
        }else{
            viewHolder.exchangetime.setText(bean.getExchangetime());
        }
        viewHolder.title.setText(bean.getTitle());
        viewHolder.status.setText(bean.getStatusStr());
        if(bean.getStatus() == 0){
            viewHolder.status.setBackgroundResource(R.drawable.order_status_wait);
        }else{
            viewHolder.status.setBackgroundResource(R.drawable.order_staus_finish);
        }
        viewHolder.rank.setText(bean.getRankStr());
        imageLoader.displayImage(bean.getThumb(),viewHolder.thumb,options);

        return convertView;
    }
}
