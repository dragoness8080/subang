package com.gcs.suban.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.adapter.AchievementAdapter;
import com.gcs.suban.base.BaseFragment;
import com.gcs.suban.bean.InventoryLogBean;
import com.gcs.suban.bean.InventoryMemberBean;
import com.gcs.suban.listener.OnInventorySettleListener;
import com.gcs.suban.model.InvenotrySettleModelImpl;
import com.gcs.suban.model.InventorySettleModel;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.view.LoadListView;
import com.gcs.suban.view.LoadListView.onLoadListViewListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AchievementFragment extends BaseFragment implements onLoadListViewListener,DatePicker.OnDateChangedListener,OnInventorySettleListener {

    protected TextView Tv_search;
    protected ImageButton Ib_search;
    protected TextView Tv_bonus;
    protected LoadListView listView;
    protected String page = "0";
    protected StringBuffer date;
    protected int year,month,day;
    protected String settledDate;
    protected AchievementAdapter adapter;
    protected boolean isRefresh = false;
    protected List<InventoryMemberBean> mListyType = new ArrayList<InventoryMemberBean>();
    protected InventorySettleModel model;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_achievement,container,false);
        return view;
    }

    @Override
    protected void init() {
        Tv_search = (TextView)context.findViewById(R.id.search_date);
        Ib_search = (ImageButton)context.findViewById(R.id.search_btn);
        Tv_bonus = (TextView)context.findViewById(R.id.tv_bonus);
        listView = (LoadListView)context.findViewById(R.id.bonus_list);
        Tv_bonus.setText("0元");

        adapter = new AchievementAdapter(context,mListyType);
        listView.setAdapter(adapter);

        Tv_search.setOnClickListener(this);
        Ib_search.setOnClickListener(this);

        date = new StringBuffer();
        initDate();

        model = new InvenotrySettleModelImpl();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_date:
                showDialog();
                break;
            case R.id.search_btn:
                page = "0";
                model.getBalance(Url.getbalance,settledDate,page,this);
                break;
        }
    }

    @Override
    public void onLoad() {
        model.getBalance(Url.getbalance,settledDate,page,this);
    }

    protected void initDate(){
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    protected void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(date.length() > 0){  date.delete(0,date.length());}
                Tv_search.setText(date.append(String.valueOf(year)).append("-").append(String.format("%02d",month)));
                settledDate = date.toString().replace("-","");
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(context,R.layout.layout_date,null);
        final DatePicker datePicker = (DatePicker)dialogView.findViewById(R.id.datepicker);
        dialog.setTitle("设置日期");
        dialog.setView(dialogView);
        dialog.show();
        datePicker.init(year, month - 1, day, this);
        hideDay(datePicker);
    }

    protected void hideDay(DatePicker datePicker){
        /* 处理andorid5.0以上的特殊问题 */
        try{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                int mDaySpinnerId = Resources.getSystem().getIdentifier("day", "id", "android");
                if(mDaySpinnerId != 0){
                    View daySpinner = datePicker.findViewById(mDaySpinnerId);
                    if(daySpinner != null){
                        daySpinner.setVisibility(View.GONE);
                    }
                }
            }else{
                Field[] datePickerfFields = datePicker.getClass().getDeclaredFields();
                for(Field datePickerfFiled : datePickerfFields){
                    if("mDaySpinner".equals(datePickerfFiled.getName()) || ("mDayPicker".equals(datePickerfFiled.getName()))){
                        datePickerfFiled.setAccessible(true);
                        Object dayPicker = new Object();
                        try {
                            dayPicker = datePickerfFiled.get(datePicker);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (IllegalArgumentException e){
                            e.printStackTrace();
                        }
                        ((View) dayPicker).setVisibility(View.GONE);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;
    }

    @Override
    public void OnError(String error) {
        ToastTool.showToast(context,error);
    }

    @Override
    public void OnSettledSuccess(String settled, List<InventoryLogBean> list, String page) {

    }

    @Override
    public void OnBalanceSuccess(String settled, List<InventoryMemberBean> list, String page) {
        Tv_bonus.setText(settled + "元");
        this.page = page;
        if(list != null){
            listView.setComplete(false);
            if(isRefresh == true){
                isRefresh = false;
                adapter.clear();
                mListyType.clear();
            }
            listView.loadComplete();
            mListyType.addAll(list);
            adapter.notifyDataSetChanged();
        }else{
            if(isRefresh != true){
                listView.setComplete(true);
                listView.loadComplete();
                return;
            }else{
                adapter.clear();
                mListyType.clear();
                adapter.notifyDataSetChanged();
                listView.loadComplete();
            }
        }
    }
}
