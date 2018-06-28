package com.gcs.suban.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.InventoryGradeBean;

import java.util.List;

public class GradeAdapter extends BaseListAdapter<InventoryGradeBean> {

    class ViewHolder{
        public TextView Tv_title;
        //public TextView Tv_id;
    }


    public GradeAdapter(Context context, List<InventoryGradeBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.spinner_grade, null);
            holder.Tv_title = (TextView)convertView.findViewById(R.id.tv_grade_name);
            //holder.Tv_id = (TextView)convertView.findViewById(R.id.tv_grade_id);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
            convertView.setTag(holder);
        }

        holder.Tv_title.setText(listItems.get(position).title);
        //holder.Tv_id.setText(listItems.get(position).id);

        return convertView;
    }
}
