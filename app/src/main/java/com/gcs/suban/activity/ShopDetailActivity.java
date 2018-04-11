package com.gcs.suban.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.ShopDetailBean;
import com.gcs.suban.eventbus.CarEvent;
import com.gcs.suban.eventbus.CollectEvent;
import com.gcs.suban.eventbus.OrderEvent;
import com.gcs.suban.listener.OnGraphicListener;
import com.gcs.suban.listener.OnShopDetailListener;
import com.gcs.suban.model.GraphicModeImpl;
import com.gcs.suban.model.GraphicModel;
import com.gcs.suban.model.ShopDetailImpl;
import com.gcs.suban.model.ShopDetailModel;
import com.gcs.suban.popwindows.attrSelectPopWindow;
import com.gcs.suban.tools.ToastTool;
import com.meiqia.meiqiasdk.util.MQIntentBuilder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.rong.eventbus.EventBus;

public class ShopDetailActivity extends BaseActivity implements OnShopDetailListener, OnGraphicListener {
    private Context context = this;

    private ImageButton IBtn_back;

    private Button Btn_carnum;

    private TextView Tv_name;
    private TextView Tv_price;
    private TextView Tv_num;
    private TextView Tv_money;
    private TextView Tv_addcar;
    private TextView Tv_buy;
    private TextView txt;

    private LinearLayout Llyt_collect;
    private LinearLayout Llyt_car;

    private RelativeLayout Rlyt_comment;

    private ImageView Img_pic;
    private ImageView Img_collect;

    private WebView webView;

    private ShopDetailModel shopDetailModel;
    private GraphicModel graphicModel;

    private String goodsid;
    private String carnum;
    private String favorite = "0";
    private String isspec;
    private String webinfo;

    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_addcar:
                Intent intent_addcar = new Intent(context, attrSelectPopWindow.class);
                intent_addcar.putExtra("goodsid", goodsid);
                intent_addcar.putExtra("type", 1);
                intent_addcar.putExtra("isspec", isspec);
                startActivity(intent_addcar);
                break;
            case R.id.tv_buy:
                Intent intent_buy = new Intent(context, attrSelectPopWindow.class);
                intent_buy.putExtra("goodsid", goodsid);
                intent_buy.putExtra("type", 2);
                intent_buy.putExtra("isspec", isspec);
                startActivity(intent_buy);
                break;
            case R.id.llyt_car:
                Intent intent_car = new Intent(context, CarActivity.class);
                startActivity(intent_car);
                break;
            case R.id.llyt_collect:
                collect();
                break;
            case R.id.rlyt_comment:
                Intent intent_comment = new Intent(context, CommentActivity.class);
                intent_comment.putExtra("goodsid", goodsid);
                startActivity(intent_comment);
                break;
            case R.id.custom_service:
                Intent intent = new MQIntentBuilder(this).build();
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    protected void init() {
        EventBus.getDefault().register(this);
        // TODO Auto-generated method stub
        InitImageLoader();
        Intent intent = getIntent();
        goodsid = intent.getStringExtra("goodsid");
        setContentView(R.layout.activity_shopdetails);

        IBtn_back = (ImageButton) findViewById(R.id.back);
        IBtn_back.setOnClickListener(this);

        Btn_carnum = (Button) findViewById(R.id.btn_carnum);

        Img_collect = (ImageView) findViewById(R.id.img_collect);

        Llyt_collect = (LinearLayout) findViewById(R.id.llyt_collect);
        Llyt_collect.setOnClickListener(this);
        Llyt_car = (LinearLayout) findViewById(R.id.llyt_car);
        Llyt_car.setOnClickListener(this);

        Rlyt_comment = (RelativeLayout) findViewById(R.id.rlyt_comment);
        Rlyt_comment.setOnClickListener(this);

        Tv_addcar = (TextView) findViewById(R.id.tv_addcar);
        Tv_addcar.setOnClickListener(this);
        Tv_buy = (TextView) findViewById(R.id.tv_buy);
        Tv_buy.setOnClickListener(this);

        Tv_name = (TextView) findViewById(R.id.tv_name);
        Tv_price = (TextView) findViewById(R.id.tv_price);
        Tv_money = (TextView) findViewById(R.id.tv_money);
        Tv_num = (TextView) findViewById(R.id.tv_num);
        txt = (TextView) findViewById(R.id.txt);

        Img_pic = (ImageView) findViewById(R.id.img_pic);
        findViewById(R.id.custom_service).setOnClickListener(this);

        hideView();

        shopDetailModel = new ShopDetailImpl();
        shopDetailModel.getInfo(Url.shopdata, goodsid, this);

        graphicModel = new GraphicModeImpl();
        graphicModel.getInfo(Url.shopgraphic + "?goodsid=" + goodsid, this);
    }

    private void hideView() {
        txt.setVisibility(View.INVISIBLE);
        Btn_carnum.setVisibility(View.INVISIBLE);
        Tv_name.setVisibility(View.INVISIBLE);
        Tv_price.setVisibility(View.INVISIBLE);
        Tv_money.setVisibility(View.INVISIBLE);
        Tv_num.setVisibility(View.INVISIBLE);

        Tv_buy.setClickable(false);
        Tv_addcar.setClickable(false);
        Llyt_car.setClickable(false);
        Llyt_collect.setClickable(false);
    }

    private void showView() {
        if (carnum.equals("0")) {
            Btn_carnum.setVisibility(View.INVISIBLE);
        } else {
            Btn_carnum.setVisibility(View.VISIBLE);
        }
        Tv_name.setVisibility(View.VISIBLE);
        Tv_price.setVisibility(View.VISIBLE);
        Tv_money.setVisibility(View.VISIBLE);
        //Tv_num.setVisibility(View.VISIBLE);
        txt.setVisibility(View.VISIBLE);

        Tv_buy.setClickable(true);
        Tv_addcar.setClickable(true);
        Llyt_car.setClickable(true);
        Llyt_collect.setClickable(true);
    }

    /**
     * 商品详情 结果返回成功
     */
    @Override
    public void onSuccess(ShopDetailBean bean) {
        // TODO Auto-generated method stub
        Tv_name.setText(bean.data.title);
        Tv_price.setText("￥" + bean.data.marketprice);
        Tv_money.setText("￥" + bean.data.productprice);
        Tv_money.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        Tv_num.setText("销量" + bean.data.salesreal);

        carnum = bean.shoppingcartcount;
        Btn_carnum.setText(carnum);

        String pic = bean.data.thumb;
        imageLoader.displayImage(pic, Img_pic, options);

        favorite = bean.favorite;
        isspec = bean.isspec;

        isCollect();

        showView();
    }

    /**
     * 图文详情 结果返回成功
     */
    @Override
    public void onSuccess(String data) {
        // TODO Auto-generated method stub
        SetInfo(data);
    }

    /**
     * 收藏 成功
     */
    @Override
    public void onCollect() {
        // TODO Auto-generated method stub
        Llyt_collect.setClickable(true);
        changeCollect();
        isCollect();
        ToastTool.showToast(context, "收藏成功");
        EventBus.getDefault().post(new CollectEvent("updata", goodsid));
    }

    /**
     * 取消收藏 成功
     */
    @Override
    public void onCancel() {
        // TODO Auto-generated method stub
        Llyt_collect.setClickable(true);
        changeCollect();
        isCollect();
        ToastTool.showToast(context, "取消收藏成功");
        EventBus.getDefault().post(new CollectEvent("updata", goodsid));
    }

    /**
     * 网络请求 结果返回失败
     */
    @Override
    public void onError(String error) {
        // TODO Auto-generated method stub
        ToastTool.showToast(context, error);
    }

    /**
     * 收藏 请求
     */
    public void collect() {
        Llyt_collect.setClickable(false);
        if (favorite.equals("0")) {
            shopDetailModel.collect(Url.collect, goodsid, this);
        } else {
            shopDetailModel.cancle(Url.cancelcollect, goodsid, this);
        }
    }

    /**
     * 收藏 状态改变
     */
    public void changeCollect() {
        if (favorite.equals("0")) {
            favorite = "1";
        } else {
            favorite = "0";
        }
    }

    /**
     * 收藏图标
     */
    public void isCollect() {
        if (favorite.equals("0")) {
            Img_collect.setBackgroundResource(R.drawable.icon_collect);
        } else {
            Img_collect.setBackgroundResource(R.drawable.icon_collect_light);
        }
    }

    /**
     * 设置webinfo
     */
    private void SetInfo(String info) {
        webinfo = info;
        webinfo = webinfo.replaceAll("\n", "");
        webinfo = webinfo.replaceAll("\r", "");
        Document doc_Dis = Jsoup.parse(webinfo);
        Elements ele_Img = doc_Dis.getElementsByTag("img");
        if (ele_Img.size() != 0) {
            for (Element e_Img : ele_Img) {
                e_Img.attr("style", "max-width: 100%; width:auto; max-height: 100%; height:auto;");
            }
        }
        webinfo = doc_Dis.toString();
        getHtmlData(webinfo);
        setWebView();
    }

    /**
     * 添加头文件
     */
    private String getHtmlData(String bodyHTML) {
        String head = "<head>" + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " + "<style>img{max-width: 100%; width:auto; max-height: 100%; height:auto;}</style>" + "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    /**
     * web加载html数据
     */
    private void setWebView() {
        webView = (WebView) findViewById(R.id.webView_graphic);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadDataWithBaseURL(null, webinfo, "text/html", "UTF-8", null);
        webView.setFocusable(false);
    }

    /**
     * 广播事件
     */
    public void onEventMainThread(CarEvent event) {
        shopDetailModel.getInfo(Url.shopdata, goodsid, this);
    }

    public void onEventMainThread(OrderEvent event) {
        finish();
    }

    @Override
    protected void onPause() {
        webView.onPause();
        super.onPause();
    }

    public void onDestroy() {
        super.onDestroy();
        webView.destroy();
        EventBus.getDefault().unregister(this);
    }

}
