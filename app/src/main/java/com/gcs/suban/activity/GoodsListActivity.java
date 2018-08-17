package com.gcs.suban.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.adapter.GoodsListAdapter;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.GoodsBean;
import com.gcs.suban.listener.OnGoodsListener;
import com.gcs.suban.model.GoodsModel;
import com.gcs.suban.model.GoodsModelImpl;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.view.LoadGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsListActivity extends BaseActivity implements OnGoodsListener,OnItemClickListener,LoadGridView.onLoadGridViewListener {

    private String keyWord;

    private ImageButton back_img;
    private TextView back;
    private ImageButton category;
    private TextView title;
    private ImageView orderby;
    private ImageView search;
    private LoadGridView goods_list;
    private RelativeLayout category_list;
    private TextView all_cate;
    private ImageView cate_back;
    private TextView isnew;
    private TextView isrecommand;
    private TextView ishot;
    private TextView istime;
    private TextView isdiscount;
    private String pcate = "0";
    private String ccate = "0";
    private String orderBy = "";

    private GoodsListAdapter goodsAdapter;
    private List<GoodsBean> mListType = new ArrayList<GoodsBean>();

    private String status = "0";
    private String page = "0";
    private GoodsModel model;

    private boolean isshow = false;
    private Map<String,String> params = new HashMap<String,String>();

    private Dialog dialog;
    private TextView sales_desc;
    private TextView price_asc;
    private TextView price_desc;
    private TextView evaluate_desc;

    @Override
    protected void init() {
        setContentView(R.layout.activity_goodslist);
        Intent intent = getIntent();
        keyWord = intent.getStringExtra("keyWord");
        pcate = intent.getStringExtra("pcate");
        ccate = intent.getStringExtra("ccate");
        params.put("keyword",keyWord == null ? "" : keyWord);
        params.put("pcate",pcate == null ? "0" : pcate);
        params.put("ccate",ccate == null ? "0" : ccate);

        back_img = (ImageButton) findViewById(R.id.back);
        back = (TextView)findViewById(R.id.title);
        back.setText("商品列表");
        back_img.setOnClickListener(this);
        category = (ImageButton)findViewById(R.id.category_btn);
        title = (TextView)findViewById(R.id.list_title);
        orderby = (ImageView)findViewById(R.id.goods_order);
        search = (ImageView)findViewById(R.id.goods_search);
        goods_list = (LoadGridView) findViewById(R.id.goods_list);

        category_list = (RelativeLayout)findViewById(R.id.category_list);
        all_cate = (TextView)findViewById(R.id.all_cate);
        cate_back = (ImageView)findViewById(R.id.cate_back);
        isnew = (TextView)findViewById(R.id.isnew);
        isrecommand = (TextView)findViewById(R.id.isrecommand);
        ishot = (TextView)findViewById(R.id.ishot);
        istime = (TextView)findViewById(R.id.istime);
        isdiscount = (TextView)findViewById(R.id.isdiscount);

        all_cate.setOnClickListener(this);
        cate_back.setOnClickListener(this);
        isnew.setOnClickListener(this);
        isrecommand.setOnClickListener(this);
        ishot.setOnClickListener(this);
        istime.setOnClickListener(this);
        isdiscount.setOnClickListener(this);
        category.setOnClickListener(this);
        back_img.setOnClickListener(this);
        orderby.setOnClickListener(this);

        category_list.setVisibility(View.INVISIBLE);

        goodsAdapter = new GoodsListAdapter(context, mListType);
        goods_list.setAdapter(goodsAdapter);
        goods_list.setOnItemClickListener(this);
        goods_list.setOnLoadGridViewListener(this);

        model = new GoodsModelImpl();
        model.getGoodsList(Url.goods_list,page,"0",params,this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.category_btn:
                showCategory();
                break;
            case R.id.all_cate:
                setParams("","");
                break;
            case R.id.isnew:
                setParams("isnew","1");
                break;
            case R.id.ishot:
                setParams("ishot","1");
                break;
            case R.id.isrecommand:
                setParams("isrecommand","1");
                break;
            case R.id.istime:
                setParams("istime","1");
                break;
            case R.id.isdiscount:
                setParams("isdiscount","1");
                break;
            case R.id.cate_back:
                showCategory();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.goods_order:
                showDialog();
                break;
            case R.id.sales_desc:
                setOrderBy("sales desc");
                break;
            case R.id.price_asc:
                setOrderBy("marketprice asc");
                break;
            case R.id.price_desc:
                setOrderBy("marketprice desc");
                break;
            case R.id.evaluate_desc:
                setOrderBy("viewcount desc");
                break;
            default:
                break;
        }
    }

    @Override
    public void onError(String error) {
        ToastTool.showToast(context,error);
    }

    @Override
    public void onGoodsSuccess(String page, List<GoodsBean> list) {
        this.page = page;
        if(list == null){

        }else{
            mListType.addAll(list);
            goodsAdapter.notifyDataSetChanged();
            goods_list.loadComplete();
        }
    }

    private void showCategory(){
        if(isshow){
            category_list.animate()
                    .alpha(0f)
                    .setDuration(1000)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            category_list.setVisibility(View.INVISIBLE);
                        }
                    });
            isshow = false;
        }else{
            category_list.animate()
                    .alpha(1f)
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            category_list.setVisibility(View.VISIBLE);
                        }
                    });
            isshow = true;
        }
    }

    private void setParams(String key, String val){
        page = "0";
        params.clear();
        goodsAdapter.clear();
        goodsAdapter.notifyDataSetChanged();
        if(!key.isEmpty()){
            params.put(key,val);
        }
        model.getGoodsList(Url.goods_list,page,"0",params,this);
        showCategory();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String goodsid = (String)view.getTag(R.id.tag_first);
        Intent intent = new Intent(context, ShopDetailActivity.class);
        intent.putExtra("goodsid", goodsid);
        startActivity(intent);
    }

    @Override
    public void onLoad() {
        model.getGoodsList(Url.goods_list,page,"0",params,this);
    }

    private void showDialog(){
        dialog = new Dialog(context,R.style.ActionSheetDialogStyle);
        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_orderby,null);
        sales_desc = (TextView)view.findViewById(R.id.sales_desc);
        price_asc = (TextView)view.findViewById(R.id.price_asc);
        price_desc = (TextView)view.findViewById(R.id.price_desc);
        evaluate_desc = (TextView)view.findViewById(R.id.evaluate_desc);

        setOrderText();

        sales_desc.setOnClickListener(this);
        price_asc.setOnClickListener(this);
        price_desc.setOnClickListener(this);
        evaluate_desc.setOnClickListener(this);
        //将布局设置给Dialog
        dialog.setContentView(view);
        //获取当前Activity所在的窗体
        Window window = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        window.setGravity(Gravity.BOTTOM);
        //获得窗体属性
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.y = 0;   //设置Dialog距离窗体底部的距离
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        dialog.show();
    }

    private void setOrderBy(String order){
        goodsAdapter.clear();
        goodsAdapter.notifyDataSetChanged();
        page = "0";
        orderBy = order;
        dialog.dismiss();
        params.put("orderby", orderBy);
        model.getGoodsList(Url.goods_list,page,"0",params,this);
    }

    private void setOrderText(){
        sales_desc.setTextColor(R.color.black);
        price_asc.setTextColor(R.color.black);
        price_desc.setTextColor(R.color.black);
        evaluate_desc.setTextColor(R.color.black);

        if(orderBy != ""){
            if(orderBy == "sales desc"){
                sales_desc.setTextColor(R.color.orange);
            }else if(orderBy == "marketprice asc"){
                price_asc.setTextColor(R.color.orange);
            }else if(orderBy == "marketprice desc"){
                price_desc.setTextColor(R.color.orange);
            }else if(orderBy == "viewcount desc"){
                evaluate_desc.setTextColor(R.color.orange);
            }
        }
    }
}
