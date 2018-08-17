package com.gcs.suban.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.GoodsBean;

import java.util.List;

public class GoodsListAdapter extends BaseListAdapter<GoodsBean>{

    class ViewHolder{
        public ImageView goods_img;
        public TextView goods_price;
        public TextView goods_title;
    }

    public GoodsListAdapter(Context context, List list) {
        super(context, list);
        InitImageLoader();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item_goods2, null);
            viewHolder.goods_img = (ImageView)convertView.findViewById(R.id.goods_thumb);
            viewHolder.goods_price = (TextView)convertView.findViewById(R.id.goods_price);
            viewHolder.goods_title = (TextView)convertView.findViewById(R.id.goods_title);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
            convertView.setTag(viewHolder);
        }

        GoodsBean goodsBean = listItems.get(position);

        String thumb = goodsBean.getImgurl();

        imageLoader.displayImage(thumb,viewHolder.goods_img,options);
        viewHolder.goods_price.setText("гд" + goodsBean.getPricenow());
        viewHolder.goods_title.setText(goodsBean.getName());
        convertView.setTag(R.id.tag_first,String.valueOf(goodsBean.getGoodsid()));

        return convertView;
    }

}
