package com.gcs.suban.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.InventoryGoodsBean;

import java.util.List;

public class StockGoodsAdapter extends BaseListAdapter<InventoryGoodsBean> {

    class ViewHolder{
        public ImageView thumb;
        public TextView title;
        public TextView num;
    }

    public StockGoodsAdapter(Context context, List<InventoryGoodsBean> list) {
        super(context, list);
        InitImageLoader();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item_stockgoods, null);
            viewHolder.thumb = (ImageView)convertView.findViewById(R.id.goods_thumb);
            viewHolder.title = (TextView)convertView.findViewById(R.id.goods_title);
            viewHolder.num = (TextView)convertView.findViewById(R.id.goods_num);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
            convertView.setTag(viewHolder);
        }

        InventoryGoodsBean goods = listItems.get(position);
        viewHolder.title.setText(goods.title);
        viewHolder.num.setText(goods.num);

        String url = goods.thumb;

        imageLoader.displayImage(url,viewHolder.thumb,options);

        return convertView;
    }
}
