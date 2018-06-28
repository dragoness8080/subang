package com.gcs.suban.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.ShopDataBean;

import java.util.List;

public class TeamOrderGoodsAdapter extends BaseListAdapter<ShopDataBean> {
    private String pic;

    public TeamOrderGoodsAdapter(Context context, List<ShopDataBean> list) {
        super(context, list);
        InitImageLoader();
    }

    class ViewHolder{
        public ImageView Img_goods;
        public TextView Tv_title;
        public TextView Tv_num;
        public TextView Tv_status;
        public TextView Tv_award;
        public TextView Tv_money;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ShopDataBean bean = listItems.get(position);

        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item_teamorder_list,null);
            holder.Img_goods = (ImageView)convertView.findViewById(R.id.goods_image);
            holder.Tv_title = (TextView)convertView.findViewById(R.id.tv_title);
            holder.Tv_num = (TextView)convertView.findViewById(R.id.tv_num);
            holder.Tv_status = (TextView)convertView.findViewById(R.id.tv_status);
            holder.Tv_award = (TextView)convertView.findViewById(R.id.tv_award);
            holder.Tv_money = (TextView)convertView.findViewById(R.id.tv_money);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
            convertView.setTag(holder);
        }

        holder.Tv_title.setText(bean.title);
        holder.Tv_money.setText(bean.marketprice + "元");
        holder.Tv_num.setText("x" + bean.total);
        holder.Tv_award.setText(bean.commission + "元");

        pic = bean.thumb;
        imageLoader.displayImage(pic,holder.Img_goods,options);

        int status = bean.status;
        if(status == 1){
            holder.Tv_status.setText("已支付");
            holder.Tv_status.setBackgroundResource(R.drawable.order_status_pay);
        }else if(status == 2){
            holder.Tv_status.setText("已发货");
            holder.Tv_status.setBackgroundResource(R.drawable.order_status_send);
        }else if(status == 3){
            holder.Tv_status.setText("已完成");
            holder.Tv_status.setBackgroundResource(R.drawable.order_staus_finish);
        }else if(status == 0){
            holder.Tv_status.setText("待支付");
            holder.Tv_status.setBackgroundResource(R.drawable.order_status_wait);
        }else if(status == -1){
            holder.Tv_status.setText("已关闭");
            holder.Tv_status.setBackgroundResource(R.drawable.order_status_close);
        }

        return convertView;
    }
}
