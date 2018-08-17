package com.gcs.suban.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.MenuBean;

import java.util.List;

public class PictureAdapter extends BaseListAdapter<MenuBean> {

    class ViewHolder{
        public ImageView Img_pic;
    }

    public PictureAdapter(Context context) {
        super(context);
        InitImageLoader();
    }

    public PictureAdapter(Context context, List<MenuBean> list) {
        super(context, list);
        InitImageLoader();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            //convertView = mInflater.inflate(R.layout.list_item_home, null);
            //holder.Img_pic = (ImageView) convertView.findViewById(R.id.img_pic);
            convertView = mInflater.inflate(R.layout.list_item_picture,null);
            holder.Img_pic = (ImageView) convertView.findViewById(R.id.pic_img);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
            convertView.setTag(holder);
        }

        MenuBean menuBean = listItems.get(position);
        String url = menuBean.getImgurl();
        imageLoader.displayImage(url,holder.Img_pic,options);
        return convertView;
    }
}
