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

public class InventoryGoodsAdapter extends BaseListAdapter<InventoryGoodsBean> {

    protected String pic;
    protected final class ViewHolder{
        public ImageView Img_thumb;
        public TextView Tv_title;
        public TextView Tv_price;
        public TextView Tv_num;
    }

    public InventoryGoodsAdapter(Context context, List<InventoryGoodsBean> list) {
        super(context, list);
        InitImageLoader();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item_confirm, null);
            viewHolder.Img_thumb = (ImageView)convertView.findViewById(R.id.img_pic);
            viewHolder.Tv_title = (TextView)convertView.findViewById(R.id.tv_name);
            viewHolder.Tv_price = (TextView)convertView.findViewById(R.id.tv_price);
            viewHolder.Tv_num = (TextView)convertView.findViewById(R.id.tv_num);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
            convertView.setTag(viewHolder);
        }

        final InventoryGoodsBean goodsBean = listItems.get(position);
        pic = goodsBean.thumb;
        viewHolder.Tv_num.setText(goodsBean.num);
        viewHolder.Tv_price.setText(goodsBean.money);
        viewHolder.Tv_title.setText(goodsBean.title);
        imageLoader.displayImage(pic,viewHolder.Img_thumb,options);

        return convertView;
    }
}
