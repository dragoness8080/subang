package com.gcs.suban.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.listener.OnBaseListener;
import com.gcs.suban.model.ProxyModel;
import com.gcs.suban.model.ProxyModelImpl;
import com.gcs.suban.tools.ToastTool;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProxyActivity extends BaseActivity implements OnBaseListener {

    private ImageButton back;
    private TextView title;
    private ImageView image;
    private ProxyModel model;
    private String imgUrl = "";

    @Override
    protected void init() {
        InitImageLoader();
        setContentView(R.layout.activity_proxy);
        back = (ImageButton) findViewById(R.id.back);
        title = (TextView)findViewById(R.id.title);
        image = (ImageView)findViewById(R.id.img_proxy);
        back.setOnClickListener(this);
        title.setText("证书");

        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示");
                builder.setMessage("保存图片到本地");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveImage(imgUrl);
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
            }
        });

        model = new ProxyModelImpl();
        model.getProxy(Url.proxy,this);

        dialog.show();
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
    public void onSuccess(String resulttext) {
        imgUrl = resulttext;
        imageLoader.displayImage(resulttext,image,options);
        dialog.cancel();
    }

    @Override
    public void onError(String error) {
        ToastTool.showToast(context,error);
        dialog.cancel();
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

    public void saveImage(Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "Suban");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
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
}
