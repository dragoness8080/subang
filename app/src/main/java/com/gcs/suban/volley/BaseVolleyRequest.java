package com.gcs.suban.volley;

import java.util.Map;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.gcs.suban.app;

public class BaseVolleyRequest {

	public static void StringRequestPost(Context mContext, String url,
			String tag, final Map<String, String> params,
			BaseStrVolleyInterFace vif) {
		app.getHttpQueues().cancelAll(tag);
		StringRequest stringrequest = new StringRequest(Method.POST, url,
				vif.loadingListener(), vif.errorListener()) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return params;
			}
		};
		stringrequest.setRetryPolicy(new DefaultRetryPolicy(0, 0, 2.0f));
		stringrequest.setTag(tag);
		app.getHttpQueues().add(stringrequest);
	}

	public static void StringRequestGet(Context mContext, String url,
			String tag, BaseStrVolleyInterFace vif) {
		app.getHttpQueues().cancelAll(tag);

		StringRequest stringrequest = new StringRequest(Method.GET, url,
				vif.loadingListener(), vif.errorListener()) {
		};
		stringrequest.setRetryPolicy(new DefaultRetryPolicy(0, 0, 2.0f));
		stringrequest.setTag(tag);
		app.getHttpQueues().add(stringrequest);

	}

}
