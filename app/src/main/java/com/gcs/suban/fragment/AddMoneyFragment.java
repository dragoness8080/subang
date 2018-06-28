package com.gcs.suban.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.base.BaseFragment;
import com.gcs.suban.listener.OnInventoryCoverListener;
import com.gcs.suban.model.InventoryCoverModel;
import com.gcs.suban.model.InventoryCoverModelImpl;
import com.gcs.suban.popwindows.PayStockPopWindow;
import com.gcs.suban.tools.SharedPreference;
import com.gcs.suban.tools.ToastTool;


import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddMoneyFragment extends BaseFragment implements OnInventoryCoverListener {

    protected String TAG = "AddMoneyFragment";
    protected EditText Et_money;
    protected Button Btn_save;
    protected float money;
    protected SweetAlertDialog dialog;
    protected InventoryCoverModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_money,container,false);
        return view;
    }

    @Override
    protected void init() {
        Et_money = (EditText)context.findViewById(R.id.et_money);
        Et_money.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        String money = (String)SharedPreference.getParam(context,"gradeMoney","");
        if(!money.isEmpty()){
            Et_money.setText(money);
        }
        Btn_save = (Button)context.findViewById(R.id.btn_save_money);
        Btn_save.setOnClickListener(this);
        model = new InventoryCoverModelImpl();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save_money:
                money = Float.valueOf(Et_money.getText().toString());
                model.setStockCover(Url.stock_cover,"1",String.valueOf(money),this);
                break;
            default:
                break;
        }
    }

    @Override
    public void OnError(String error) {
        ToastTool.showToast(context,error);
    }

    @Override
    public void OnSuccess(String money, String sn, String id, int ispay) {
        double payMoney = Double.valueOf(money);
        if(ispay == 1){
            Intent intent = new Intent(context, PayStockPopWindow.class);
            Bundle bundle = new Bundle();
            bundle.putInt("orderid", Integer.valueOf(id));
            bundle.putString("ordersn",sn);
            bundle.putDouble("price",payMoney);
            bundle.putString("dispatchprice", "0");
            bundle.putInt("freightmodal",1);
            intent.putExtras(bundle);
            startActivity(intent);
        }else{
            //Toast.makeText(context, "补仓申请成功，请及时联系管理员", Toast.LENGTH_LONG).show();
            dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("申请成功")
                .setContentText("补仓申请成功，请及时联系管理员")
                .setConfirmText("确定")
                .showCancelButton(false)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.cancel();
                    }
                });
            dialog.show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
