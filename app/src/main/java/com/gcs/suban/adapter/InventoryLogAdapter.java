package com.gcs.suban.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.InventoryLogBean;

import java.util.List;

public class InventoryLogAdapter extends BaseListAdapter<InventoryLogBean> {

    public InventoryLogAdapter(Context context, List<InventoryLogBean> list) {
        super(context, list);
        InitImageLoader();
    }

    class ViewHolder{
        public TextView Tv_createtime;
        public TextView Tv_ordersn;
        public ImageView Img_goods;
        public TextView Tv_title;
        public TextView Tv_num;
        public TextView Tv_jiancang_price;
        public TextView Tv_settle_price;
        public TextView Tv_order_status;
        public TextView Tv_log_status;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_inventory_logs, null);
            holder.Tv_createtime = (TextView)convertView.findViewById(R.id.tv_createtime);
            holder.Tv_ordersn = (TextView)convertView.findViewById(R.id.tv_ordersn);
            holder.Img_goods = (ImageView)convertView.findViewById(R.id.img_goods);
            holder.Tv_title = (TextView)convertView.findViewById(R.id.tv_goods_title);
            holder.Tv_num = (TextView)convertView.findViewById(R.id.tv_goods_num);
            holder.Tv_jiancang_price = (TextView)convertView.findViewById(R.id.tv_jangcang_price);
            holder.Tv_settle_price = (TextView)convertView.findViewById(R.id.tv_settle_price);
            holder.Tv_order_status = (TextView)convertView.findViewById(R.id.tv_order_status);
            holder.Tv_log_status = (TextView)convertView.findViewById(R.id.tv_log_status);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
            convertView.setTag(holder);
        }

        InventoryLogBean bean = listItems.get(position);
        holder.Tv_createtime.setText(bean.createtime);
        holder.Tv_ordersn.setText(bean.ordersn);
        holder.Tv_title.setText(bean.title);
        holder.Tv_num.setText("x" + bean.num);
        holder.Tv_jiancang_price.setText(String.valueOf(bean.money));
        holder.Tv_settle_price.setText(String.valueOf(bean.settle_money));
        holder.Tv_order_status.setText(bean.statusStr);
        if(bean.status == 0){
            holder.Tv_order_status.setBackgroundResource(R.drawable.order_status_wait);
        }else if(bean.status == 1){
            holder.Tv_order_status.setBackgroundResource(R.drawable.order_status_pay);
        }else if(bean.status == 2){
            holder.Tv_order_status.setBackgroundResource(R.drawable.order_status_send);
        }else if(bean.status == 3){
            holder.Tv_order_status.setBackgroundResource(R.drawable.order_staus_finish);
        }else if(bean.status == -1){
            holder.Tv_order_status.setBackgroundResource(R.drawable.order_status_close);
        }else if(bean.status == 99){
            holder.Tv_order_status.setVisibility(View.GONE);
        }
        if(bean.refund == 0){
            holder.Tv_log_status.setText(bean.settledStr);
            if(bean.settled == -1){
                holder.Tv_log_status.setBackgroundResource(R.drawable.order_status_wait);
            }else{
                holder.Tv_log_status.setBackgroundResource(R.drawable.order_staus_finish);
            }
        }else{
            holder.Tv_log_status.setText(bean.refundStr);
            holder.Tv_log_status.setBackgroundResource(R.drawable.order_status_close);
        }
        imageLoader.displayImage(bean.thumb,holder.Img_goods,options);

        return convertView;
    }
}
