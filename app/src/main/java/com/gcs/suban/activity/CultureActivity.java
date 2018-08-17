package com.gcs.suban.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.gcs.suban.R;
import com.gcs.suban.app;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.tools.BrowserJsInject;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.wxapi.WXHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 使用WebView播放视频时需要注意的地方： 1、加网络访问权限（及其他所需要的权限）；
 * 2、WebViewClient中方法shouldOverrideUrlLoading可用来实现点击webView页面的链接；
 * 3、WebView中播放视频需要添加webView.setWebChromeClient(new WebChromeClient())；
 * 4、视频竖屏时，点击全屏，想要切换到横屏全屏的状态，那么必须在Manifest.xml配置文件该Activity的
 * 配置文件中添加android:configChanges="orientation|screenSize"语句。
 * 5、如果视频不能播放，或者播放比较卡，可以采用硬件加速，即在Application，或所在的Activity的配置文件中添加
 */
public class CultureActivity extends BaseActivity {

    private TextView Tv_title;

    private ImageButton IBtn_back;

//    private Button Btn_share;

    private String title;
    private String content;
    private String pic;

    private WebView webView;

    private String shareurl;
    private String mHomeUrl;

    private myWebChromeClient xwebchromeclient;

    private RelativeLayout Rlyt_title;

    private Boolean isFullscreen = false;
    private WXHelper wxHelper;
    private Bitmap bitmap;
    private boolean isLoad = false;

    private OrientationEventListener mOrientationEventListener;

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
      /*      case R.id.btn_share:
                Intent linkshares = new Intent(context, SharePopWindow.class);
                linkshares.putExtra("otherurl", mHomeUrl);
                linkshares.putExtra("title", title);
                linkshares.putExtra("summary", content);
                linkshares.putExtra("pic", pic);
                startActivity(linkshares);
                break;*/
            case R.id.wx_share:
                if (isLoad) {
                    wxHelper.share(0, content, title, mHomeUrl, bitmap);
                } else {
                    ToastTool.showToast(context, "分享内容载入中，请稍后");
                }
                break;
            case R.id.friend_share:
                if (isLoad) {
                    wxHelper.share(1, content, title, mHomeUrl, bitmap);
                } else {
                    ToastTool.showToast(context, "分享内容载入中，请稍后");
                }
                break;
            case R.id.copy:
                ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(mHomeUrl);
                Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void string2Bitmap() {
        ImageRequest imageRequest = new ImageRequest(pic,
                new Response.Listener<Bitmap>() {

                    @Override
                    public void onResponse(Bitmap arg0) {
                        bitmap = arg0;
                        //Rlyt_weixin.setClickable(true);
                        //Rlyt_friend.setClickable(true);
                        isLoad = true;
                    }
                }, 200, 200, Bitmap.Config.RGB_565, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                ToastTool.showToast(context, "分享图片加载错误");
            }
        });
        app.getHttpQueues().add(imageRequest);
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        pic = intent.getStringExtra("pic");
        shareurl = intent.getStringExtra("shareurl");
        string2Bitmap();
        // TODO Auto-generated method stub
        setContentView(R.layout.activity_webview);
        initView();

//        Btn_share = (Button) context.findViewById(R.id.btn_share);
//        Btn_share.setOnClickListener(this);

        mHomeUrl = shareurl;

        webView = (WebView) findViewById(R.id.webview);
        wxHelper = new WXHelper(context);
        initWebView();

        //屏幕转动
        mOrientationEventListener = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientation) {
                if(orientation == OrientationEventListener.ORIENTATION_UNKNOWN){
                    return;
                }

                if(orientation > 350 || orientation < 10 || (orientation > 170 && orientation < 190)){
                    inCustomView();
                    hideCustomView();
                }else if((orientation > 80 && orientation < 100) || (orientation > 260 && orientation < 280)){
                    setFullScreen();
                }

            }
        };

        if(mOrientationEventListener.canDetectOrientation()){
            mOrientationEventListener.enable();
        }else{
            mOrientationEventListener.disable();
        }
    }

    private void initView() {
        Tv_title = (TextView) findViewById(R.id.title);
        Tv_title.setText("素邦文化");

        Rlyt_title = (RelativeLayout) findViewById(R.id.rlyt_top);

        IBtn_back = (ImageButton) context.findViewById(R.id.back);
        IBtn_back.setOnClickListener(this);
        findViewById(R.id.friend_share).setOnClickListener(this);
        findViewById(R.id.wx_share).setOnClickListener(this);
        findViewById(R.id.copy).setOnClickListener(this);
    }

    /**
     * 展示网页界面
     **/
    private void initWebView() {

        WebSettings ws = webView.getSettings();

        //ws.setTextSize(TextSize.NORMAL);
        //ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);// 排版适应屏幕
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        ws.setUseWideViewPort(true);// 可任意比例缩放
        ws.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。

        ws.setSaveFormData(true);// 保存表单数据
        ws.setJavaScriptEnabled(true);
        ws.setGeolocationEnabled(true);// 启用地理定位
        ws.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");// 设置定位的数据库路径
        ws.setDomStorageEnabled(true);
        ws.setSupportMultipleWindows(true);// 新加
        ws.setTextZoom(100);

        xwebchromeclient = new myWebChromeClient();
        webView.setWebChromeClient(xwebchromeclient);
        webView.setWebViewClient(new myWebViewClient());


        webView.addJavascriptInterface(new Object() {

            @JavascriptInterface
            public void playing() {
                setFullScreen();
                Log.e("video", "=======================");
            }

        }, "local_obj");

        webView.loadUrl(mHomeUrl);


        // 长按点击事件
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final WebView.HitTestResult hitTestResult = webView
                        .getHitTestResult();
                // 如果是图片类型或者是带有图片链接的类型
                if (hitTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE
                        || hitTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                    // 弹出保存图片的对话框
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            context);
                    builder.setTitle("提示");
                    builder.setMessage("保存图片到本地");
                    builder.setPositiveButton("确认",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(
                                        DialogInterface dialogInterface, int i) {
                                    String url = hitTestResult.getExtra();
                                    // 下载图片到本地
                                    saveImage(url);
                                }
                            });
                    builder.setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {
                                // 自动dismiss
                                @Override
                                public void onClick(
                                        DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    public void saveImage(String url) {
        ImageLoader.getInstance().loadImage(url,
                new SimpleImageLoadingListener() {

                    @Override
                    public void onLoadingComplete(String imageUri, View view,
                                                  Bitmap loadedImage) {
                        saveImage(loadedImage);
                    }

                });
    }

    public class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            //super.onPageFinished(view, url);
            Log.e(TAG, "onPageFinished");
            view.loadUrl(BrowserJsInject.fullScreenByJs(url));
        }
    }


    /**
     * 处理Javascript的对话框、网站图标、网站标题以及网页加载进度等
     *
     * @author
     */
    public class myWebChromeClient extends WebChromeClient {
    }

    /**
     * 设置全屏
     */
    private void setFullScreen() {
        Log.e("视频全屏-->", "竖屏切换到横屏");
        Rlyt_title.setVisibility(View.GONE);
        isFullscreen = true;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // 设置全屏的相关属性，获取当前的屏幕状态，然后设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // 全屏下的状态码：1098974464
        // 窗口下的状态吗：1098973440
    }

    /**
     * 判断是否是全屏
     *
     * @return
     */
    public boolean inCustomView() {
        return isFullscreen;
    }

    /**
     * 全屏时按返加键执行退出全屏方法
     */
    public void hideCustomView() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        super.onResume();
        webView.onResume();
        webView.resumeTimers();

        /**
         * 设置为横屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public void saveImage(Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "Suban");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            Toast.makeText(context, "保存图片成功", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
        webView.pauseTimers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.loadUrl("about:blank");
        webView.stopLoading();
        webView.setWebChromeClient(null);
        webView.setWebViewClient(null);
        webView.setVisibility(View.GONE);
        webView.destroy();
        webView = null;
        mOrientationEventListener.disable();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (inCustomView()) {
                isFullscreen = false;
                Rlyt_title.setVisibility(View.VISIBLE);
                hideCustomView();
                return true;
            } else {
                webView.loadUrl("about:blank");
                CultureActivity.this.finish();
            }
        }
        return false;
    }

    @Override
    public void finish() {
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.removeAllViews();
        super.finish();
        mOrientationEventListener.disable();
    }

}
