package com.gcs.suban.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.activity.SettleRecordActivity;
import com.gcs.suban.activity.SettledLogActivity;
import com.gcs.suban.base.BaseFragment;
import com.gcs.suban.eventbus.SettleEvent;
import com.gcs.suban.listener.OnSettleCenterListener;
import com.gcs.suban.model.SettleCenterModel;
import com.gcs.suban.model.SettleCenterModelImpl;
import com.gcs.suban.tools.ToastTool;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.rong.eventbus.EventBus;

public class SettleCenterFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,OnSettleCenterListener {

    private TextView commission_settled;
    private TextView record;
    private TextView commission_sum;
    private TextView commission_applied;
    private TextView commission_checked;
    private TextView commission_locked;
    private TextView commission_payed;
    private Button pay;
    private SettleCenterModel model;
    private double commission = 0f;
    private SweetAlertDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settle_center,container,false);
        return view;
    }

    @Override
    protected void init() {
        EventBus.getDefault().register(this);
        commission_settled = (TextView)context.findViewById(R.id.tv_commission_settled);
        record = (TextView)context.findViewById(R.id.tv_record);
        commission_sum = (TextView)context.findViewById(R.id.tv_commission_sum);
        commission_applied = (TextView)context.findViewById(R.id.tv_commission_applied);
        commission_checked = (TextView)context.findViewById(R.id.tv_commission_checked);
        commission_locked = (TextView)context.findViewById(R.id.tv_commission_locked);
        commission_payed = (TextView)context.findViewById(R.id.tv_commission_payed);
        pay = (Button)context.findViewById(R.id.btn_commission_pay);
        swipeRefreshLayout = (SwipeRefreshLayout)context.findViewById(R.id.swipeRefreshLayout_settled);
        swipeRefreshLayout.setOnRefreshListener(this);
        InitSwipeRefreshLayout();

        initDialog();

        commission_settled.setOnClickListener(this);
        record.setOnClickListener(this);
        pay.setOnClickListener(this);

        model = new SettleCenterModelImpl();
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        this.onRefresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_commission_settled:
                Intent intent_cur = new Intent(context, SettledLogActivity.class);
                startActivity(intent_cur);
                break;
            case R.id.tv_record:
                Intent intent = new Intent(context, SettleRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_commission_pay:
                if(commission > 0f){
                    dialog.show();
                    model.balance(Url.balance_settle,this);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        model.getSettled(Url.settled,this);
    }

    @Override
    public void onError(String err) {
        dialog.dismiss();
        swipeRefreshLayout.setRefreshing(false);
        ToastTool.showToast(context, err);
    }

    @Override
    public void onSuccess(String payed, String examine, String waitpay, String apply, String total) {
        swipeRefreshLayout.setRefreshing(false);
        commission = Double.valueOf(apply);
        commission_settled.setText(apply);
        commission_sum.setText(total);
        commission_applied.setText(examine);
        commission_checked.setText(waitpay);
        commission_locked.setText(apply);
        commission_payed.setText(payed);
    }

    @Override
    public void onBalanceSuccess(String str) {
        dialog.dismiss();
        ToastTool.showToast(context,str);
        EventBus.getDefault().post(new SettleEvent("wait","msg"));
    }

    public void onEventMainThread(SettleEvent event){
        this.onRefresh();
    }

    private void initDialog(){
        dialog = new SweetAlertDialog(context,
                SweetAlertDialog.PROGRESS_TYPE).setTitleText("Мгдижа...");
        dialog.setCancelable(false);
    }
}
