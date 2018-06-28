package com.gcs.suban.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.InventoryGradeBean;
import com.gcs.suban.bean.InventoryMemberBean;
import com.gcs.suban.listener.OnInventoryListener;
import com.gcs.suban.model.InventoryModel;
import com.gcs.suban.model.InventoryModelImpl;
import com.gcs.suban.tools.ToastTool;

import java.util.List;

public class StockApplyActivity extends BaseActivity implements OnInventoryListener {

    private ImageView Img_stock;
    private Button Btn_apply;
    private InventoryModel model;

    @Override
    protected void init() {
        InitImageLoader();
        setContentView(R.layout.activity_stock_apply);
        Img_stock = (ImageView)findViewById(R.id.img_stock);
        Btn_apply = (Button)findViewById(R.id.btn_stock_apply);
        Btn_apply.setOnClickListener(this);
        model = new InventoryModelImpl();
        model.getImgUrl(Url.stock_img,this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_stock_apply:
                Intent intent = new Intent(context, InventoryApplyActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void OnImgSuccess(String url) {
        Log.i("StockActivity", url);
        imageLoader.displayImage(url, Img_stock, options);
    }

    @Override
    public void OnError(String error) {
        ToastTool.showToast(context,error);
    }

    @Override
    public void OnGradeSuccess(List<InventoryGradeBean> beanList) {

    }

    @Override
    public void OnSuccess(String msg,String ordersn, double money, int ispay) {

    }

    @Override
    public void OnApplySuccess(InventoryMemberBean bean) {

    }
}
