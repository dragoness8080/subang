package com.gcs.suban.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.InventoryGoodsBean;

import java.util.List;

public class InventoryNumAdapter extends BaseListAdapter<InventoryGoodsBean> {

    protected class ViewHolder{
        public TextView Tv_goods_title;
        public TextView Tv_goods_money;
        public EditText Et_num;
        public ImageView Img_thumb;
    }

    public InventoryNumAdapter(Context context, List<InventoryGoodsBean> list) {
        super(context, list);
        InitImageLoader();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item_addgoods, null);
            viewHolder.Tv_goods_title = (TextView)convertView.findViewById(R.id.tv_goods);
            viewHolder.Tv_goods_money = (TextView)convertView.findViewById(R.id.tv_price);
            viewHolder.Et_num = (EditText)convertView.findViewById(R.id.et_buy_num);
            viewHolder.Et_num.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
            viewHolder.Img_thumb = (ImageView)convertView.findViewById(R.id.img_goods_thumb);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            convertView.setTag(viewHolder);
        }

        final InventoryGoodsBean bean = listItems.get(position);

        if(viewHolder.Et_num.getTag() instanceof TextWatcher){
            viewHolder.Et_num.removeTextChangedListener((TextWatcher)viewHolder.Et_num.getTag());
        }

        viewHolder.Et_num.setText(bean.num);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(s)){
                    bean.num = "0";
                }else{
                    bean.num = s.toString();
                }
            }
        };

        viewHolder.Et_num.addTextChangedListener(textWatcher);
        viewHolder.Et_num.setTag(textWatcher);

        viewHolder.Tv_goods_title.setText(bean.title);
        viewHolder.Tv_goods_money.setText(bean.money + " Ԫ");
        imageLoader.displayImage(bean.thumb,viewHolder.Img_thumb,options);

        return convertView;
    }
}
