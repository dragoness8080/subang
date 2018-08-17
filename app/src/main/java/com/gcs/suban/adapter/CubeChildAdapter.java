package com.gcs.suban.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.MenuBean;

import java.util.List;

public class CubeChildAdapter extends BaseListAdapter<MenuBean> {

    class ViewHolder{
        public ImageView imageView;
    }

    public CubeChildAdapter(Context context, List<MenuBean> list) {
        super(context, list);
        InitImageLoader();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item_cube_picture, null);
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.cube_picture);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
            convertView.setTag(viewHolder);
        }

        MenuBean bean = listItems.get(position);
        imageLoader.displayImage(bean.getImgurl(),viewHolder.imageView,options);
        return convertView;
    }
}
