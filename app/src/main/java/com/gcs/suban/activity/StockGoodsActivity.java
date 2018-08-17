package com.gcs.suban.activity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.adapter.StockGoodsAdapter;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.InventoryGoodsBean;
import com.gcs.suban.listener.OnInventoryGoodsListener;
import com.gcs.suban.model.InventoryGoodsModel;
import com.gcs.suban.model.InventoryGoodsModelImpl;
import com.gcs.suban.tools.ToastTool;

import java.util.ArrayList;
import java.util.List;

public class StockGoodsActivity extends BaseActivity implements OnInventoryGoodsListener {

    private ListView Stock_Goods_list;
    private ImageButton back;
    private TextView title;
    private StockGoodsAdapter adapter;
    private List<InventoryGoodsBean> mListType = new ArrayList<>();
    private InventoryGoodsModel model;

    @Override
    protected void init() {
        setContentView(R.layout.activity_stockgoods);
        back = (ImageButton)findViewById(R.id.back);
        title = (TextView)findViewById(R.id.title);
        Stock_Goods_list = (ListView)findViewById(R.id.stock_goods_list);
        adapter = new StockGoodsAdapter(context,mListType);
        Stock_Goods_list.setAdapter(adapter);
        title.setText("数量商品明细");
        back.setOnClickListener(this);

        model = new InventoryGoodsModelImpl();
        model.getInfo(Url.having_num,this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onError(String msg) {
        ToastTool.showToast(context,msg);
    }

    @Override
    public void onGoodsList(List<InventoryGoodsBean> list) {
        adapter.clear();
        mListType.clear();
        mListType.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onGoodsInfo(InventoryGoodsBean bean) {

    }
}
