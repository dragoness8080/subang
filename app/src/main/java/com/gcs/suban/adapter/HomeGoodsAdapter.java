package com.gcs.suban.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.activity.ShopDetailActivity;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.MenuBean;

import java.util.List;

public class HomeGoodsAdapter extends BaseListAdapter<MenuBean> {

    private Context context;

    class ViewHolder{
        public ImageView goods_img;
        public TextView goods_price;
        public TextView goods_title;
        private ImageButton buy;
    }

    public HomeGoodsAdapter(Context context) {
        super(context);
    }

    public HomeGoodsAdapter(Context context, List<MenuBean> list) {
        super(context, list);
        InitImageLoader();
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item_goods, null);
            viewHolder.goods_img = (ImageView)convertView.findViewById(R.id.goods_thumb);
            viewHolder.goods_price = (TextView)convertView.findViewById(R.id.goods_price);
            viewHolder.goods_title = (TextView)convertView.findViewById(R.id.goods_title);
            viewHolder.buy = (ImageButton)convertView.findViewById(R.id.buy_car);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
            convertView.setTag(viewHolder);
        }

        MenuBean goodsBean = listItems.get(position);

        String thumb = goodsBean.getImgurl();

        imageLoader.displayImage(thumb,viewHolder.goods_img,options);
        viewHolder.goods_price.setText("гд" + goodsBean.getPricenow());
        viewHolder.goods_title.setText(goodsBean.getTitle());
        viewHolder.buy.setOnClickListener(new OnMyClicklistener(goodsBean.getGoodsid()));
        viewHolder.goods_img.setOnClickListener(new OnMyClicklistener(goodsBean.getGoodsid()));


        return convertView;
    }

    public class OnMyClicklistener implements View.OnClickListener{

        private String goodsid;

        public OnMyClicklistener(String goodsid){  this.goodsid=goodsid;}

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ShopDetailActivity.class);
            intent.putExtra("goodsid", goodsid);
            context.startActivity(intent);
        }
    }
}
