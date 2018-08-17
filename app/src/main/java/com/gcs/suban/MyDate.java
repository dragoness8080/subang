package com.gcs.suban;

import android.util.Log;

import com.gcs.suban.tools.SharedPreference;

/**
 * 描述： 用户数据
 */
public class MyDate {
	public  static String myvid="";//用户vid
	public  static String jpushid="";//用户vid
	public  static String token="";//聊天
	public static String wxreq="";//微信请求
	public static int wxresp=0;//微信回调

	public static String getMyVid() {
		Log.i("MyData", "myvid="+myvid);
		if(myvid.equals(""))
		{
			String vid=(String) SharedPreference.getParam(app.getContext(), "vid", "String");
			MyDate.setMyVid(vid);
		}
		//return "obSNav4qf6_8qtirp-XA2bZJRegk";
		return myvid;
	}

	public static void setJpushid(String jpushid) {
		MyDate.jpushid = jpushid;
	}
	
	public static String getJpushid() {
		Log.i("Jpushid", "Jpushid="+jpushid);
		if(jpushid.equals(""))
		{
			String jpushid=(String) SharedPreference.getParam(app.getContext(), "jpushid", "String");
			MyDate.setJpushid(jpushid);
		}
		return jpushid;
	}

	public static void setMyVid(String myvid) {
		MyDate.myvid = myvid;
	}
	
	public static String getWxreq() {
		return wxreq;
	}

	public static void setWxreq(String wxreq) {
		MyDate.wxreq = wxreq;
	}
	
	public static int getWxresp() {
		return wxresp;
	}

	public static void setWxresp(int wxresp) {
		MyDate.wxresp = wxresp;
	}
	
	public static String getToken() {
		return token;
	}

	public static void setToken(String token) {
		MyDate.token = token;
	}

}