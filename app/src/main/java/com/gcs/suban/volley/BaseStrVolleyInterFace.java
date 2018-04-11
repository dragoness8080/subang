package com.gcs.suban.volley;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

import android.content.Context;

public abstract class BaseStrVolleyInterFace {
	public static Listener<String> mListener;
	public static ErrorListener mErrorListener;
	public BaseStrVolleyInterFace(Context Context, Listener<String> listen,
			ErrorListener errorListener) {
		BaseStrVolleyInterFace.mErrorListener = errorListener;
		BaseStrVolleyInterFace.mListener = listen;
	}

	public abstract void onSuccess(String result);

	public abstract void onError(VolleyError error);

	public Listener<String> loadingListener() {
		mListener = new Listener<String>() {

			@Override
			public void onResponse(String result) {
				onSuccess(result);
			}
		};
		return mListener;
	}

	public ErrorListener errorListener() {
		mErrorListener = new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				onError(error);
			}
		};
		return mErrorListener;

	}
}
