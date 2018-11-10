package com.gcs.suban.bean;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LotteryBean {
    private int id;
    private String title;
    private String thumb;
    private int num;
    private String bgcolor;
    private Bitmap prize;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public void setNum(int num){    this.num = num;}

    public int getNum(){    return num;}

    public boolean isNoNum(){   return num <= 0;}

    public String getBgcolor() {
        return bgcolor;
    }

    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }

    public Bitmap getPrize(){
        return prize;
    }
    public void setPrize(Bitmap prize){ this.prize = prize;}
    public void setPrize(){
        try {
            URL url = new URL(thumb);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);  //允许输入流，允许下载
            conn.setUseCaches(false); //不使用缓存
            conn.setRequestMethod("GET");
            InputStream in = conn.getInputStream();
            prize = BitmapFactory.decodeStream(in);
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
