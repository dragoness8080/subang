package com.gcs.suban.model;

import android.content.Context;
import android.util.Log;
import com.android.volley.VolleyError;

import com.gcs.suban.Url;
import com.gcs.suban.app;
import com.gcs.suban.listener.OnShopListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;

public class ShopModelImpl implements ShopModel {

	private Context context = app.getContext();

	@Override
	public void getInfo(String url, final OnShopListener listener) {
		final String TAG = url;
		// TODO Auto-generated method stub
		BaseVolleyRequest.StringRequestGet(context, url, TAG,
				new BaseStrVolleyInterFace(context,
						BaseStrVolleyInterFace.mListener,
						BaseStrVolleyInterFace.mErrorListener) {
					@Override
					public void onSuccess(String response) {
						Log.i("HomeFragment", "GET����ɹ�-->" + response);
						listener.onSuccess(response);
					}

					@Override
					public void onError(VolleyError volleyError) {
						Log.i(TAG, "GET����ʧ��-->" + volleyError.toString());
						listener.onError(Url.networkError);
					}
				});
	}

}
