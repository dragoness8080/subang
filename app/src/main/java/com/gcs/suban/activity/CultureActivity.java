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
 * ʹ��WebView������Ƶʱ��Ҫע��ĵط��� 1�����������Ȩ�ޣ�����������Ҫ��Ȩ�ޣ���
 * 2��WebViewClient�з���shouldOverrideUrlLoading������ʵ�ֵ��webViewҳ������ӣ�
 * 3��WebView�в�����Ƶ��Ҫ���webView.setWebChromeClient(new WebChromeClient())��
 * 4����Ƶ����ʱ�����ȫ������Ҫ�л�������ȫ����״̬����ô������Manifest.xml�����ļ���Activity��
 * �����ļ������android:configChanges="orientation|screenSize"��䡣
 * 5�������Ƶ���ܲ��ţ����߲��űȽϿ������Բ���Ӳ�����٣�����Application�������ڵ�Activity�������ļ������
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
                    ToastTool.showToast(context, "�������������У����Ժ�");
                }
                break;
            case R.id.friend_share:
                if (isLoad) {
                    wxHelper.share(1, content, title, mHomeUrl, bitmap);
                } else {
                    ToastTool.showToast(context, "�������������У����Ժ�");
                }
                break;
            case R.id.copy:
                ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(mHomeUrl);
                Toast.makeText(context, "���Ƴɹ�", Toast.LENGTH_SHORT).show();
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
                ToastTool.showToast(context, "����ͼƬ���ش���");
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

        //��Ļת��
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
        Tv_title.setText("�ذ��Ļ�");

        Rlyt_title = (RelativeLayout) findViewById(R.id.rlyt_top);

        IBtn_back = (ImageButton) context.findViewById(R.id.back);
        IBtn_back.setOnClickListener(this);
        findViewById(R.id.friend_share).setOnClickListener(this);
        findViewById(R.id.wx_share).setOnClickListener(this);
        findViewById(R.id.copy).setOnClickListener(this);
    }

    /**
     * չʾ��ҳ����
     **/
    private void initWebView() {

        WebSettings ws = webView.getSettings();

        //ws.setTextSize(TextSize.NORMAL);
        //ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);// �Ű���Ӧ��Ļ
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        ws.setUseWideViewPort(true);// �������������
        ws.setLoadWithOverviewMode(true);// setUseWideViewPort��������webview�Ƽ�ʹ�õĴ��ڡ�setLoadWithOverviewMode����������webview���ص�ҳ���ģʽ��

        ws.setSaveFormData(true);// ���������
        ws.setJavaScriptEnabled(true);
        ws.setGeolocationEnabled(true);// ���õ���λ
        ws.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");// ���ö�λ�����ݿ�·��
        ws.setDomStorageEnabled(true);
        ws.setSupportMultipleWindows(true);// �¼�
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


        // ��������¼�
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final WebView.HitTestResult hitTestResult = webView
                        .getHitTestResult();
                // �����ͼƬ���ͻ����Ǵ���ͼƬ���ӵ�����
                if (hitTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE
                        || hitTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                    // ��������ͼƬ�ĶԻ���
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            context);
                    builder.setTitle("��ʾ");
                    builder.setMessage("����ͼƬ������");
                    builder.setPositiveButton("ȷ��",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(
                                        DialogInterface dialogInterface, int i) {
                                    String url = hitTestResult.getExtra();
                                    // ����ͼƬ������
                                    saveImage(url);
                                }
                            });
                    builder.setNegativeButton("ȡ��",
                            new DialogInterface.OnClickListener() {
                                // �Զ�dismiss
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
     * ����Javascript�ĶԻ�����վͼ�ꡢ��վ�����Լ���ҳ���ؽ��ȵ�
     *
     * @author
     */
    public class myWebChromeClient extends WebChromeClient {
    }

    /**
     * ����ȫ��
     */
    private void setFullScreen() {
        Log.e("��Ƶȫ��-->", "�����л�������");
        Rlyt_title.setVisibility(View.GONE);
        isFullscreen = true;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // ����ȫ����������ԣ���ȡ��ǰ����Ļ״̬��Ȼ������ȫ��
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // ȫ���µ�״̬�룺1098974464
        // �����µ�״̬��1098973440
    }

    /**
     * �ж��Ƿ���ȫ��
     *
     * @return
     */
    public boolean inCustomView() {
        return isFullscreen;
    }

    /**
     * ȫ��ʱ�����Ӽ�ִ���˳�ȫ������
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
         * ����Ϊ����
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
            Toast.makeText(context, "����ͼƬ�ɹ�", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // ��ΰ��ļ����뵽ϵͳͼ��
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // ���֪ͨͼ�����
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
