package com.gcs.suban.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.InventoryExtrBean;

import java.util.List;

public class InventoryExtrAdapter extends BaseListAdapter<InventoryExtrBean> {

    class ViewHolder{
        TextView createtime_tv;
        TextView ordersn_tv;
        ImageView thumb_img;
        TextView title_tv;
        TextView num_tv;
        TextView nickname_tv;
        TextView price_tv;
        TextView types_tv;
        TextView status_tv;
    }

    public InventoryExtrAdapter(Context context, List<InventoryExtrBean> list) {
        super(context, list);
        InitImageLoader();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item_extr,null);
            viewHolder.createtime_tv = (TextView)convertView.findViewById(R.id.createtime_tv);
            viewHolder.ordersn_tv = (TextView)convertView.findViewById(R.id.ordersn_tv);
            viewHolder.thumb_img = (ImageView)convertView.findViewById(R.id.thumb_img);
            viewHolder.title_tv = (TextView)convertView.findViewById(R.id.title_tv);
            viewHolder.num_tv = (TextView)convertView.findViewById(R.id.num_tv);
            viewHolder.nickname_tv = (TextView)convertView.findViewById(R.id.nickname_tv);
            viewHolder.price_tv = (TextView)convertView.findViewById(R.id.price_tv);
            viewHolder.types_tv = (TextView)convertView.findViewById(R.id.types_tv);
            viewHolder.status_tv = (TextView)convertView.findViewById(R.id.status_tv);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
            convertView.setTag(viewHolder);
        }

        InventoryExtrBean inventoryExtrBean = listItems.get(position);
        viewHolder.createtime_tv.setText(inventoryExtrBean.getCreatetime());
        viewHolder.ordersn_tv.setText(inventoryExtrBean.getOrdersn());
        viewHolder.title_tv.setText(inventoryExtrBean.getTitle());
        viewHolder.num_tv.setText("x" + inventoryExtrBean.getNum());
        viewHolder.nickname_tv.setText(inventoryExtrBean.getFrom());
        viewHolder.price_tv.setText(String.valueOf(inventoryExtrBean.getMoney()));
        if(inventoryExtrBean.getType().equals("stock.minus")){
            viewHolder.types_tv.setText("订单购买");
            viewHolder.types_tv.setBackgroundResource(R.drawable.order_staus_finish);
        }else if(inventoryExtrBean.getType().equals("stock.selfbuy")){
            viewHolder.types_tv.setText("下属自提");
            viewHolder.types_tv.setBackgroundResource(R.drawable.order_status_wait);
        }
        if(inventoryExtrBean.getRefund() == 1){
            viewHolder.status_tv.setText("已关闭");
            viewHolder.status_tv.setBackgroundResource(R.drawable.order_status_close);
        }else{
            if(inventoryExtrBean.getSettled() == -1){
                viewHolder.status_tv.setText("待结算");
                viewHolder.status_tv.setBackgroundResource(R.drawable.order_status_wait);
            }else if(inventoryExtrBean.getSettled() == 1){
                viewHolder.status_tv.setText("已结算");
                viewHolder.status_tv.setBackgroundResource(R.drawable.order_staus_finish);
            }
        }
        imageLoader.displayImage(inventoryExtrBean.getThumb(),viewHolder.thumb_img,options);

        return convertView;
    }
}
