package com.gcs.suban.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.MenuBean;

import java.util.List;

public class MenuAdapter extends BaseListAdapter<MenuBean> {

    class ViewHolder{
        public ImageView imageView;
        public TextView textView;
    }

    public MenuAdapter(Context context, List<MenuBean> list) {
        super(context, list);
        InitImageLoader();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item_menu,null);
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.menu_img);
            viewHolder.textView = (TextView)convertView.findViewById(R.id.menu_title);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
            convertView.setTag(viewHolder);
        }

        MenuBean menuBean = listItems.get(position);
        String imgUrl = menuBean.getImgurl();
        viewHolder.textView.setText(menuBean.getTitle());
        imageLoader.displayImage(imgUrl,viewHolder.imageView,options);

        return convertView;
    }

}
