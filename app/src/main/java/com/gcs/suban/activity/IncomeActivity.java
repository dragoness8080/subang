package com.gcs.suban.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.adapter.IncomeAdapter;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.IncomeBean;
import com.gcs.suban.listener.OnIncomeListener;
import com.gcs.suban.model.IncomeModel;
import com.gcs.suban.model.IncomeModelImpl;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.view.LoadListView;
import com.gcs.suban.view.LoadListView.onLoadListViewListener;

import java.util.ArrayList;
import java.util.List;

public class IncomeActivity extends BaseActivity implements onLoadListViewListener,OnIncomeListener {

    private ImageButton back_img;
    private TextView title;
    private Button title_btn;
    private LoadListView loadListView;
    private String page = "0";
    private IncomeAdapter adapter;
    private List<IncomeBean> mListType = new ArrayList<>();
    private IncomeModel model;

    @Override
    protected void init() {
        setContentView(R.layout.activity_income);
        back_img = (ImageButton)findViewById(R.id.back_stock);
        title = (TextView)findViewById(R.id.title_stock);
        title_btn = (Button)findViewById(R.id.wallet_btn);
        loadListView = (LoadListView)findViewById(R.id.income_list);

        adapter = new IncomeAdapter(context, mListType);
        loadListView.setAdapter(adapter);
        loadListView.setOnLoadListViewListener(this);

        title.setText(" ’»Î√˜œ∏");
        title_btn.setVisibility(View.GONE);

        back_img.setOnClickListener(this);

        model = new IncomeModelImpl();
        model.getIncome(Url.incomes,page,this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_stock:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoad() {
        model.getIncome(Url.incomes,page,this);
    }

    @Override
    public void onError(String error) {
        ToastTool.showToast(context,error);
        loadListView.loadComplete();
    }

    @Override
    public void onIncomeSuccess(String page, List<IncomeBean> incomeBeanList) {
        this.page = page;
        if(incomeBeanList == null){
            loadListView.setComplete(true);
            loadListView.loadComplete();
        }else{
            loadListView.setComplete(false);
            loadListView.loadComplete();
            mListType.addAll(incomeBeanList);
        }
        adapter.notifyDataSetChanged();
    }
}
