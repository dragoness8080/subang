package com.gcs.suban.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.InventoryLogBean;

import java.util.List;

public class InventoryLogAddAdapter extends BaseListAdapter<InventoryLogBean> {

    protected InventoryLogBean inventoryLogBean;

    class ViewHolder{
        public TextView Tv_log_time;
        public TextView Tv_price;
        public TextView Tv_num;
        public TextView Tv_remark;
    }

    public InventoryLogAddAdapter(Context context, List<InventoryLogBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.list_item_logadd,null);
            holder = new ViewHolder();
            holder.Tv_log_time = (TextView)convertView.findViewById(R.id.tv_log_time);
            holder.Tv_price = (TextView)convertView.findViewById(R.id.tv_price);
            holder.Tv_num = (TextView)convertView.findViewById(R.id.tv_number);
            holder.Tv_remark = (TextView)convertView.findViewById(R.id.tv_remark);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
            convertView.setTag(holder);
        }

        inventoryLogBean = listItems.get(position);
        holder.Tv_log_time.setText(inventoryLogBean.createtime);
        holder.Tv_price.setText(inventoryLogBean.money + "ิช");
        holder.Tv_num.setText(inventoryLogBean.num + "ผþ");
        holder.Tv_remark.setText(inventoryLogBean.remark);

        return convertView;
    }
}
