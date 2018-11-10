package com.gcs.suban.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.adapter.InventoryLogAdapter;
import com.gcs.suban.base.BaseFragment;
import com.gcs.suban.bean.InventoryLogBean;
import com.gcs.suban.bean.InventoryMemberBean;
import com.gcs.suban.listener.OnInventorySettleListener;
import com.gcs.suban.model.InvenotrySettleModelImpl;
import com.gcs.suban.model.InventorySettleModel;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.view.LoadListView;
import com.gcs.suban.view.LoadListView.onLoadListViewListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StockSettleFragment extends BaseFragment implements OnInventorySettleListener,onLoadListViewListener {

    protected ImageButton Ib_left;
    protected ImageButton Ib_right;
    protected TextView Tv_settled;
    protected TextView Tv_date;
    protected LoadListView Lv_settled;

    protected SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    protected Calendar calendar = Calendar.getInstance();
    protected int cur_day = calendar.get(Calendar.DAY_OF_MONTH);
    protected int cur_year = calendar.get(Calendar.YEAR);
    protected int cur_month = calendar.get(Calendar.MONTH) + 1;
    protected int i = 0;
    protected String settle_date;

    protected InventorySettleModel model;
    protected String page = "0";
    protected List<InventoryLogBean> mListType = new ArrayList<InventoryLogBean>();
    protected InventoryLogAdapter adapter;
    protected Boolean isRefresh = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stock_settle,container,false);
        return view;
    }

    @Override
    protected void init() {
        Ib_left = (ImageButton)context.findViewById(R.id.img_left);
        Ib_right = (ImageButton)context.findViewById(R.id.img_right);
        Tv_settled = (TextView)context.findViewById(R.id.tv_settled_money);
        Tv_date = (TextView)context.findViewById(R.id.tv_settle_date);
        Lv_settled = (LoadListView) context.findViewById(R.id.list_settled);
        Lv_settled.setOnLoadListViewListener(this);

        adapter = new InventoryLogAdapter(context, mListType);
        Lv_settled.setAdapter(adapter);

        Ib_left.setOnClickListener(this);
        Ib_right.setOnClickListener(this);

        model = new InvenotrySettleModelImpl();

        setDate();
        load();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_left:
                leftDate();
                load();
                break;
            case R.id.img_right:
                rightDate();
                load();
                break;
            default:
                break;
        }
    }

    protected void setDate(){
        calendar.set(Calendar.YEAR, cur_year);
        calendar.set(Calendar.MONTH, cur_month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Log.i("inventory_settle", String.valueOf(cur_year));
        Log.i("inventory_settle", String.valueOf(cur_month));
        Log.i("inventory_settle", String.valueOf(cur_day));
        Log.i("inventory_settle", format.format(calendar.getTime()));

        if(cur_day <= 15){
            calendar.add(Calendar.DAY_OF_MONTH, -1);    //最后一天
            settle_date = format.format(calendar.getTime());
        }else{
            settle_date = cur_year + "-" + String.format("%02d",cur_month) + "-15";
        }
    }

    protected void leftDate(){
        i++;
        if(cur_day <= 15){
            cur_day = 16;
        }else{
            cur_day = 15;
            --cur_month;
            if(cur_month <= 0){
                cur_month = 12;
                --cur_year;
            }
        }

        setDate();
    }

    protected void rightDate(){
        if(i > 0){
            i--;
            if(cur_day > 15){
                cur_day = 15;
            }else{
                cur_day = 16;
                ++cur_month;
                if(cur_month > 12){
                    cur_month = 1;
                    ++cur_year;
                }
            }

            setDate();
        }
    }

    protected void load(){
        Tv_date.setText(settle_date);
        page = "0";
        adapter.clear();
        mListType.clear();
        model.getSettled(Url.getsettled,0,settle_date,page,this);
    }

    @Override
    public void OnError(String error) {
        ToastTool.showToast(context,error);
    }

    @Override
    public void OnSettledSuccess(String settled, List<InventoryLogBean> list, String page) {
        Tv_settled.setText(settled + " 元");
        this.page = page;
        if(list != null){
            Lv_settled.setComplete(false);
            if (isRefresh == true){
                isRefresh = false;
                adapter.clear();
                mListType.clear();
            }
            Lv_settled.loadComplete();
            mListType.addAll(list);
            adapter.notifyDataSetChanged();
        }else{
            if (isRefresh != true){
                Lv_settled.setComplete(true);
                Lv_settled.loadComplete();
                return;
            }else{
                adapter.clear();
                mListType.clear();
                adapter.notifyDataSetChanged();
                Lv_settled.loadComplete();
            }
        }
    }

    @Override
    public void OnBalanceSuccess(String settled, List<InventoryMemberBean> list, String page) {

    }

    @Override
    public void onLoad() {
        model.getSettled(Url.getsettled,0,settle_date,page,this);
    }
}
