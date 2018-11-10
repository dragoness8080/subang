package com.gcs.suban.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.activity.CultureActivity;
import com.gcs.suban.activity.GoodsListActivity;
import com.gcs.suban.activity.ShopDetailActivity;
import com.gcs.suban.adapter.CubeAdapter;
import com.gcs.suban.adapter.CubeChildAdapter;
import com.gcs.suban.adapter.HomeGoodsAdapter;
import com.gcs.suban.adapter.MenuAdapter;
import com.gcs.suban.adapter.PictureAdapter;
import com.gcs.suban.base.BaseFragment;
import com.gcs.suban.bean.HomeBean;
import com.gcs.suban.bean.MenuBean;
import com.gcs.suban.listener.OnShopListener;
import com.gcs.suban.model.ShopModel;
import com.gcs.suban.model.ShopModelImpl;
import com.gcs.suban.tools.ACache;
import com.gcs.suban.tools.BrowserJsInject;
import com.gcs.suban.tools.GlideImageLoader;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.view.AutoScrollTextView;
import com.gcs.suban.view.MyScrollview;
import com.gcs.suban.view.NoSlideGridView;
import com.gcs.suban.view.VpSwipeRefreshLayout;
import com.meiqia.meiqiasdk.util.MQIntentBuilder;
import com.youth.banner.Banner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewHomeFragment_bak extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,OnShopListener {

    private String TAG = "NewHomeFragment";
    private MyScrollview myScrollview;
    private LinearLayout linearLayout;
    private ACache mCache;
    private LayoutInflater layoutInflater;
    private ShopModel homeModal;
    private ImageView mMoveTop;

    private AutoScrollTextView notice_text;

    private EditText search_text;
    private ImageView search_btn;

    private NoSlideGridView picture_gridview;

    private ArrayList<String> bannerUrlList = new ArrayList<>();

    private int screenHeight = 0;
    private int curViewHeight = 0;
    private boolean isComplate = false;
    private int curFlage = 0;
    private float screen_ave = 0;

    private List<HomeBean> homeBeanList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_fragment_home,container,false);
        return view;
    }

    @Override
    public void onRefresh() {
        curViewHeight = 0;
        curFlage = 0;
        isComplate = false;
        homeModal.getInfo(Url.new_index,this);
    }

    @Override
    protected void init() {
        layoutInflater = LayoutInflater.from(context);
        mCache = ACache.get(context);
        myScrollview = (MyScrollview)context.findViewById(R.id.new_scrollview);
        linearLayout = (LinearLayout)context.findViewById(R.id.new_index_line);
        mMoveTop = (ImageView)context.findViewById(R.id.new_move_top);
        mMoveTop.setOnClickListener(this);
        context.findViewById(R.id.new_call_service).setOnClickListener(this);
        swipeRefreshLayout = (VpSwipeRefreshLayout)context.findViewById(R.id.new_vpswiperefresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        InitSwipeRefreshLayout();

        DisplayMetrics dm = getResources().getDisplayMetrics();
        Log.e("Height", "screen=" + dm.heightPixels);
        screenHeight = dm.heightPixels;
        screen_ave = dm.density;
        
        homeModal = new ShopModelImpl();
        if(mCache.getAsString(TAG) != null){
            JsonResolve(mCache.getAsString(TAG));
        }else{
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                }
            });
            this.onRefresh();
        }
        myScrollview.setOnScrollToLongListener(new MyScrollview.OnScrollToLongListener() {
            @Override
            public void onScrolToLong(int distance) {
                Log.e("distance", "distance=" + distance);
                if (distance > 0) {
                    swipeRefreshLayout.setEnabled(false);
                } else {
                    swipeRefreshLayout.setEnabled(true);
                }
                if (distance > 500) {
                    mMoveTop.setVisibility(View.VISIBLE);
                } else {
                    mMoveTop.setVisibility(View.GONE);
                }
                if(distance > (curViewHeight - screenHeight - 100)){
                    display();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.new_move_top:
                myScrollview.smoothScrollTo(0,0);
                mMoveTop.setVisibility(View.GONE);
                break;
            case R.id.new_call_service:
                Intent intent = new MQIntentBuilder(getActivity()).build();
                startActivity(intent);
            default:
                break;
        }
    }

    private void JsonResolve(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            String isnull = jsonObject.getString("isnull");
            if(isnull.equals("0")){
                linearLayout.removeAllViews();
                homeBeanList.clear();
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i ++){
                    HomeBean homeBean = new HomeBean();
                    JSONObject jsonObject1 = (JSONObject)jsonArray.opt(i);
                    String temp = jsonObject1.getString("temp");
                    homeBean.setSign(temp); //保存标志
                    if(temp.equals("notice")){
                        String text = jsonObject1.getString("text");
                        homeBean.setText(text);
                    }else if(temp.equals("search")){

                    }else if(temp.equals("menu")){
                        homeBean.setItem(jsonObject1.getString("item"));
                    }else if(temp.equals("picture")){
                        homeBean.setItem(jsonObject1.getString("item"));
                    }else if(temp.equals("blank")){

                    }else if(temp.equals("goods")){
                        int style = jsonObject1.getInt("style");
                        homeBean.setStyle(style);
                        homeBean.setItem(jsonObject1.getString("item"));
                    }else if(temp.equals("richtext")){
                        String url = jsonObject1.getString("text");
                        homeBean.setText(url);
                    }else if(temp.equals("banner")){
                        homeBean.setItem(jsonObject1.getString("item"));
                        bannerUrlList.clear();
                    }else if(temp.equals("cube")){
                        homeBean.setItem(jsonObject1.getString("item"));
                    }
                    homeBeanList.add(homeBean);
                }
                display();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private View noticeView(String text){
        View view = layoutInflater.inflate(R.layout.layout_index_notice,null);
        notice_text = (AutoScrollTextView)view.findViewById(R.id.new_scroll_text);
        notice_text.setText(text);
        notice_text.init(getActivity().getWindowManager());
        notice_text.startScroll();
        displayViewHeihgt(view,"noticeView");
        return view;
    }

    private View searchView(){
        View view = layoutInflater.inflate(R.layout.layout_index_search,null);
        search_text = (EditText)view.findViewById(R.id.search_content);
        search_btn = (ImageView)view.findViewById(R.id.search);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyWord = search_text.getText().toString();
                Intent intent = new Intent(context, GoodsListActivity.class);
                intent.putExtra("keyWord", keyWord);
                startActivity(intent);
            }
        });
        displayViewHeihgt(view,"searchView");
        return view;
    }

    private View menuView(int count, final List<MenuBean> mListType){
        View view = layoutInflater.inflate(R.layout.layout_index_menu,null);
        final NoSlideGridView menu_list = (NoSlideGridView)view.findViewById(R.id.menu_gridview);
        menu_list.setNumColumns(count);

        MenuAdapter menuAdapter = new MenuAdapter(context, mListType);
        menu_list.setAdapter(menuAdapter);
        menuAdapter.notifyDataSetChanged();

        menu_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MenuBean menuBean = mListType.get(position);
                if(menuBean.getIsarr() == 1){
                    if(menuBean.getPlugin().equals("list")){
                        Intent intent = new Intent(context, GoodsListActivity.class);
                        intent.putExtra("pcate",menuBean.getPcate());
                        intent.putExtra("ccate",menuBean.getCcate());
                        startActivity(intent);
                    }else if(menuBean.getPlugin().equals("designer")){
                         ToastTool.showToast(context,"功能完善中，请等待...");
                    }else if(menuBean.getPlugin().equals("category")){
                        ToastTool.showToast(context,"功能完善中，请等待...");
                    }
                }else if(menuBean.getIsarr() == 0){
                    if(menuBean.getHrefurl().isEmpty()){
                        ToastTool.showToast(context,"功能完善中，请等待。。。");
                    }else{
                        Intent intent = new Intent(context, CultureActivity.class);
                        intent.putExtra("shareurl",menuBean.getHrefurl());
                        startActivity(intent);
                    }
                }
            }
        });
        displayViewHeihgt(view,"menuView");
        return view;
    }

    private View pictureView(final List<MenuBean> mListType){
        View view = layoutInflater.inflate(R.layout.layout_index_picture,null);
        picture_gridview = (NoSlideGridView)view.findViewById(R.id.new_list_home);
        PictureAdapter pictureAdapter = new PictureAdapter(context, mListType);
        pictureAdapter.notifyDataSetChanged();
        picture_gridview.setAdapter(pictureAdapter);

        picture_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MenuBean menuBean = mListType.get(position);
                if(menuBean.getIsarr() == 1){
                    if(!menuBean.getId().equals("0")){
                        Intent intent = new Intent(context, ShopDetailActivity.class);
                        intent.putExtra("goodsid", menuBean.getId());
                        startActivity(intent);
                    }
                }else{
                    if(!menuBean.getHrefurl().isEmpty()){
                        Intent intent = new Intent(context, CultureActivity.class);
                        intent.putExtra("shareurl", menuBean.getHrefurl());
                        startActivity(intent);
                    }
                }
            }
        });
        displayViewHeihgt(view,"pictureView");
        return view;
    }

    private View blackView(){
        View view = layoutInflater.inflate(R.layout.layout_index_space, null);
        displayViewHeihgt(view,"blackView");
        return view;
    }

    private View goodsView(int style, List<MenuBean> mListType){
        View view = layoutInflater.inflate(R.layout.layout_index_goods, null);
        NoSlideGridView goodsGridView = (NoSlideGridView) view.findViewById(R.id.goods_gridview);
        goodsGridView.setNumColumns(style);
        HomeGoodsAdapter goodsAdapter = new HomeGoodsAdapter(context, mListType);
        goodsGridView.setAdapter(goodsAdapter);
        goodsAdapter.notifyDataSetChanged();
        displayViewHeihgt(view,"goodsView");
        return view;
    }

    /*
    private View richtext(String url){
        View view = layoutInflater.inflate(R.layout.layout_index_webview,null);
        WebView webView = (WebView)view.findViewById(R.id.video);

        WebSettings ws = webView.getSettings();

        //ws.setTextSize(TextSize.NORMAL);
        //ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        ws.setUseWideViewPort(true);
        ws.setLoadWithOverviewMode(true);

        ws.setSaveFormData(true);
        ws.setJavaScriptEnabled(true);
        ws.setGeolocationEnabled(true);
        ws.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");
        ws.setDomStorageEnabled(true);
        ws.setSupportMultipleWindows(true);
        ws.setTextZoom(100);

        myWebChromeClient xwebchromeclient = new myWebChromeClient();
        webView.setWebChromeClient(xwebchromeclient);
        webView.setWebViewClient(new myWebViewClient());


        webView.addJavascriptInterface(new Object() {

            @JavascriptInterface
            public void playing() {
                Log.e("video", "=======================");
            }

        }, "local_obj");


        webView.loadUrl(url);

        //final VideoView videoView = (VideoView)view.findViewById(R.id.video);
        //Uri uri = Uri.parse(url);
        //videoView.setMediaController(new MediaController(context));
        //videoView.setVideoURI(uri);
        //videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
        //    @Override
        //    public boolean onError(MediaPlayer mp, int what, int extra) {
        //        videoView.stopPlayback();
        //        return true;
        //    }
        //});
        //videoView.start();

        displayViewHeihgt(view,"richtext");
        return view;
    }
    */

    private View banner(){
        View view = layoutInflater.inflate(R.layout.layout_index_banner,null);
        Banner banner = (Banner)view.findViewById(R.id.new_banner);
        banner.setImages(bannerUrlList).setImageLoader(new GlideImageLoader()).start();
        displayViewHeihgt(view,"banner");
        return view;
    }

    private View cube(List<MenuBean> list){
        List<MenuBean> left = new ArrayList<>();
        List<MenuBean> right = new ArrayList<>();
        if(list != null){
            for(int i = 0; i < list.size(); i ++){
                MenuBean bean = list.get(i);
                Log.i("recywidht", "i=" + i + ",cur_cols="  + bean.getCur_cols());
                if(bean.getCur_cols() == 0){
                    left.add(bean);
                }else{
                    right.add(bean);
                }
            }
        }else{
            left = null;
            right = null;
        }

        CubeChildAdapter leftAdapter = new CubeChildAdapter(context,left);
        leftAdapter.notifyDataSetChanged();
        CubeChildAdapter rightAdapter = new CubeChildAdapter(context, right);
        rightAdapter.notifyDataSetChanged();

        List<CubeChildAdapter> cubeChildAdapterList = new ArrayList<>();
        cubeChildAdapterList.add(leftAdapter);
        cubeChildAdapterList.add(rightAdapter);

        View view = layoutInflater.inflate(R.layout.layout_index_cube,null);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.cube);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration( new DividerItemDecoration());
        recyclerView.setAdapter(new CubeAdapter(context,cubeChildAdapterList));
        displayViewHeihgt(view,"cube");
        return view;
    }

    @Override
    public void onSuccess(String response) {
        JsonResolve(response);
        mCache.put(TAG,response,60 * 60 * 24 * 3);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onError(String error) {
        ToastTool.showToast(context, error);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /*
    public class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.loadUrl(BrowserJsInject.fullScreenByJs(url));
        }
    }
    */

    /**
     *
     *
     * @author
     */
    /*
    public class myWebChromeClient extends WebChromeClient {
    }
    */

    public class DividerItemDecoration extends RecyclerView.ItemDecoration{

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            if(position == 1){
                outRect.left = 5;
            }
        }
    }

    protected void displayViewHeihgt(View view,String tag){
        int width = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        view.measure(width,height);
        curViewHeight += view.getMeasuredHeight();
        Log.e("Height", tag + "=" + view.getMeasuredHeight());
    }

    protected void display(){
        if(isComplate == false){
            if(homeBeanList == null){
                ToastTool.showToast(context, Url.networkError);
                isComplate = true;
                return;
            }
            if(curFlage == homeBeanList.size()){
                isComplate = true;
                return;
            }
            for(int i = curFlage; i < homeBeanList.size(); i++){
                HomeBean bean = homeBeanList.get(i);
                if(bean.getSign().equals("notice")){
                    linearLayout.addView(noticeView(bean.getText()));
                }else if(bean.getSign().equals("search")){
                    linearLayout.addView(searchView());
                }else if(bean.getSign().equals("menu")){
                    List<MenuBean> list = bean.getMenuItem();
                    if(list != null){
                        linearLayout.addView(menuView(list.size(),list));
                    }
                }else if(bean.getSign().equals("picture")){
                    List<MenuBean> list = bean.getPcitureItem();
                    if(list != null){
                        linearLayout.addView(pictureView(list));
                    }
                }else if(bean.getSign().equals("blank")){
                    linearLayout.addView(blackView());
                }else if(bean.getSign().equals("goods")){
                    List<MenuBean> list = bean.getGoodsItem();
                    if(list != null){
                        linearLayout.addView(goodsView(bean.getStyle(), list));
                    }
                }else if(bean.getSign().equals("banner")){
                    bannerUrlList.clear();
                    bannerUrlList = bean.getBannerItem();
                    linearLayout.addView(banner());
                }

                if(curViewHeight >= screenHeight){
                    curFlage = (i + 1);
                    break;
                }
            }
        }
    }
}
