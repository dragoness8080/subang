package com.gcs.suban.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.AroundBean;

import java.util.List;

import io.rong.imkit.RongIM;

/**
 * Created by Administrator on 2017/7/15 0015.
 */

public class AroundAdapter extends BaseListAdapter<AroundBean> {
    private List<AroundBean> mList;

    public AroundAdapter(Context context, List<AroundBean> list) {
        super(context, list);
        this.mList = list;
        this.context = context;
        InitImageLoader();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        AroundBean aroundBean = mList.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(parent.getContext(),R.layout.item_around_list, null);
            holder.nickName = (TextView) convertView.findViewById(R.id.nick_name);
            holder.distance = (TextView) convertView.findViewById(R.id.distance);
            holder.icon = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            convertView.setTag(holder);
        }
        holder.nickName.setText(aroundBean.nickname);
        holder.distance.setText("距离您:"+aroundBean.limit+"米");
        imageLoader.displayImage(aroundBean.avatar,holder.icon,options2);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RongIM.getInstance() != null)
                {
                    RongIM.getInstance().startPrivateChat(context,mList.get(position).uid, "会员聊天");
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView nickName;
        TextView distance;
        ImageView icon;

    }

}
