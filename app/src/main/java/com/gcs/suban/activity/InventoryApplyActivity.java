package com.gcs.suban.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.adapter.GradeAdapter;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.InventoryGradeBean;
import com.gcs.suban.bean.InventoryMemberBean;
import com.gcs.suban.listener.OnInventoryListener;
import com.gcs.suban.model.InventoryModel;
import com.gcs.suban.model.InventoryModelImpl;
import com.gcs.suban.popwindows.PayStockPopWindow;
import com.gcs.suban.tools.ToastTool;

import java.util.List;


public class InventoryApplyActivity extends BaseActivity implements OnInventoryListener{

    private TextView Tv_title;
    private ImageButton Ibtn_back;
    private EditText Et_name;
    private EditText Et_phone;
    private Button Btn_save;
    private RelativeLayout Btn_type;
    private Spinner Sr_type;

    private InventoryModel model;

    private GradeAdapter adapter;

    private InventoryMemberBean memberBean;

    private int gradeId;
    private String ordersn;


    @Override
    protected void init() {
        setContentView(R.layout.activity_inventoryapply);
        Et_name = (EditText)findViewById(R.id.et_name);
        Et_phone = (EditText)findViewById(R.id.et_phone);
        Btn_save = (Button)findViewById(R.id.btn_save_apply);
        Btn_type = (RelativeLayout)findViewById(R.id.layout_type);
        Sr_type = (Spinner) findViewById(R.id.sr_types);

        Tv_title = (TextView)findViewById(R.id.title);
        Tv_title.setText("‘∆≤÷…Í«Î");

        Ibtn_back = (ImageButton)findViewById(R.id.back);
        Ibtn_back.setOnClickListener(this);

        //Btn_type.setOnClickListener(this);
        Btn_save.setOnClickListener(this);

        Sr_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GradeAdapter gradeAdapter = (GradeAdapter)parent.getAdapter();
                gradeId = gradeAdapter.getItem(position).id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ToastTool.showToast(context, "«Î—°‘Ò≤÷ø‚¿‡–Õ");
            }
        });

        model = new InventoryModelImpl();
        model.getGradeList(Url.gradelist,this);
        //model.getApply(Url.get_apply,this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save_apply:
                memberBean = new InventoryMemberBean();
                memberBean.gradeid = gradeId;
                memberBean.realname = Et_name.getText().toString();
                memberBean.mobile = Et_phone.getText().toString();

                model.apply(Url.stock_apply, memberBean,this);
                break;
            case R.id.back:
                finish();
            default:
                break;
        }
    }

    @Override
    public void OnImgSuccess(String url) {

    }

    @Override
    public void OnError(String error) {
        ToastTool.showToast(context, error);
    }

    @Override
    public void OnGradeSuccess(List<InventoryGradeBean> grade) {
        model.getApply(Url.get_apply, this);
        if(grade != null){
            adapter = new GradeAdapter(context,grade);
            Sr_type.setAdapter(adapter);
        }
    }

    @Override
    public void OnSuccess(String msg, String ordersn, double money, int ispay) {
        this.ordersn = ordersn;
        if(ispay == 0){
            ToastTool.showToast(context, msg);
            finish();
        }else{
            Intent intent = new Intent(context, PayStockPopWindow.class);
            Bundle bundle = new Bundle();
            bundle.putString("ordersn", ordersn);
            bundle.putString("dispatchprice","0");
            bundle.putInt("orderid", 0);
            bundle.putDouble("price", money);
            bundle.putInt("freightmodal",1);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void OnApplySuccess(InventoryMemberBean bean) {

        if(bean != null){
            Et_name.setText(bean.realname);
            Et_phone.setText(bean.mobile);

            GradeAdapter gradeAdapter = (GradeAdapter) Sr_type.getAdapter();

            if(gradeAdapter != null){
                for (int i=0;i<gradeAdapter.getCount();i++){
                    if(bean.gradeid == gradeAdapter.getItem(i).id){
                        Log.i("inventoryapply", "option=" + i);
                        Sr_type.setSelection(i);
                    }
                }
            }

        }

    }
}
