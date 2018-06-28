package com.gcs.suban.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.adapter.InventoryNumAdapter;
import com.gcs.suban.base.BaseFragment;
import com.gcs.suban.bean.InventoryGoodsBean;
import com.gcs.suban.listener.OnInventoryCoverListener;
import com.gcs.suban.listener.OnInventoryGoodsListener;
import com.gcs.suban.model.InventoryCoverModel;
import com.gcs.suban.model.InventoryCoverModelImpl;
import com.gcs.suban.model.InventoryGoodsModel;
import com.gcs.suban.model.InventoryGoodsModelImpl;
import com.gcs.suban.popwindows.PayStockPopWindow;
import com.gcs.suban.tools.ToastTool;

import java.util.ArrayList;
import java.util.List;

public class AddGoodsFragment extends BaseFragment implements OnInventoryGoodsListener,OnInventoryCoverListener {

    protected ListView List_goods;
    protected Button Btn_save;
    protected InventoryNumAdapter adapter;
    protected List<InventoryGoodsBean> list = new ArrayList<InventoryGoodsBean>();
    protected InventoryGoodsModel model;
    protected InventoryGoodsBean goodsBean;
    protected InventoryCoverModel coverModel;


    @Override
    protected void init() {
        List_goods = (ListView)context.findViewById(R.id.list_goods);
        adapter = new InventoryNumAdapter(context,list);
        List_goods.setAdapter(adapter);

        Btn_save = (Button)context.findViewById(R.id.btn_save_num);
        Btn_save.setOnClickListener(this);
        model = new InventoryGoodsModelImpl();
        model.getGoodsList(Url.goodslist,this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save_num:
                setCover();
                break;
            default:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_num,container,false);
        return view;
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void OnError(String error) {
        ToastTool.showToast(context,error);
    }

    @Override
    public void OnSuccess(String money, String sn, String id,int ispay) {
        Double payMoney = Double.valueOf(money);
        if(ispay == 1){
            Intent intent = new Intent(context, PayStockPopWindow.class);
            Bundle bundle = new Bundle();
            bundle.putInt("orderid", Integer.valueOf(id));
            bundle.putString("ordersn", sn);
            bundle.putDouble("price", payMoney);
            bundle.putString("dispatchprice", "0");
            bundle.putInt("freightmodal", 1);
            intent.putExtras(bundle);
            startActivity(intent);
        }else{
            Toast.makeText(context, "补仓申请成功，请及时联系管理员", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onGoodsList(List<InventoryGoodsBean> list) {
        if(list != null){
            this.list.addAll(list);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onGoodsInfo(InventoryGoodsBean bean) {

    }

    protected void setCover(){
        int count = adapter.getCount();
        if(count > 0){
            StringBuffer dataStr = new StringBuffer();
            for(int i = 0; i < count; i ++){
                goodsBean = adapter.getItem(i);
                if(goodsBean.num != null && Integer.valueOf(goodsBean.num) > 0){
                    if(dataStr.length() <= 0){
                        dataStr.append(goodsBean.goodsid).append("#").append(goodsBean.num);
                    }else{
                        dataStr.append(",").append(goodsBean.goodsid).append("#").append(goodsBean.num);
                    }
                }
            }
            if(dataStr.length() > 0){
                String goodsStr = dataStr.toString();
                Log.i("addgoods","str=" + goodsStr);
                coverModel = new InventoryCoverModelImpl();
                coverModel.setStockCover(Url.stock_cover,"2",goodsStr,this);
            }
        }
    }
}
