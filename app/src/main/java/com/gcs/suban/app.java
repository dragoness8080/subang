package com.gcs.suban;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.meiqia.core.callback.OnInitCallback;
import com.meiqia.meiqiasdk.util.MQConfig;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.L;

import cn.beecloud.BeeCloud;
import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;

public class app extends Application {

    private static Context mContext;

    public static RequestQueue queues;

    @Override
    public void onCreate() {
        super.onCreate();
        //System.setProperty("http.keepAlive", "false");
        //System.setProperty("sun.net.http.retryPost", "false");

        RongIM.init(this);

        JPushInterface.init(this); // ��ʼ�� JPush
        BeeCloud.setAppIdAndSecret(Constants.BeeCloud_APP_ID, Constants.BeeCloud_APP_SECRET);

        mContext = getApplicationContext();

        queues = Volley.newRequestQueue(getApplicationContext());

        initImageLoader(getApplicationContext());

        L.writeLogs(false);
        MQConfig.init(this, Constants.MEI_QIA_APP_KEY, new OnInitCallback() {
            @Override
            public void onSuccess(String clientId) {
            }

            @Override
            public void onFailure(int code, String message) {
            }
        });
    }

    public static Context getContext() {
        return mContext;
    }

    public static RequestQueue getHttpQueues() {
        return queues;
    }

    // ͼƬ��ܳ�ʼ��
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPoolSize(3).threadPriority(Thread.NORM_PRIORITY - 1).denyCacheImageMultipleSizesInMemory().diskCacheFileNameGenerator(new Md5FileNameGenerator()).memoryCache(new UsingFreqLimitedMemoryCache(5 * 1024 * 1024))
                // �ڴ滺������ֵ
                .diskCacheSize(50 * 1024 * 1024).tasksProcessingOrder(QueueProcessingType.LIFO).imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)).build();
        ImageLoader.getInstance().init(config);

    }
}
