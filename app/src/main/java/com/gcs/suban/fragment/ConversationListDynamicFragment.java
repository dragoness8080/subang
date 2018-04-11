package com.gcs.suban.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.volley.VolleyError;
import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.activity.TeamActivity;
import com.gcs.suban.base.BaseFragment;
import com.gcs.suban.bean.UserBean;
import com.gcs.suban.tools.ACache;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;
import com.meiqia.meiqiasdk.util.MQIntentBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Conversation.ConversationType;
import io.rong.imlib.model.UserInfo;

public class ConversationListDynamicFragment extends BaseFragment {
	
	private Button Btn_service;
	
	private ImageButton Ibtn_friend;

	private UserInfo userInfo;

	private Context context;

	private ACache mCache;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_conversationlist,
				container, false);

		ConversationListFragment fragment = new ConversationListFragment();
		Uri uri = Uri
				.parse("rong://"
						+ getActivity().getApplicationInfo().packageName)
				.buildUpon()
				.appendPath("conversationlist")
				.appendQueryParameter(
						Conversation.ConversationType.PRIVATE.getName(),
						"false") // ����˽�ĻỰ�Ǿۺ���ʾ
				.appendQueryParameter(
						Conversation.ConversationType.GROUP.getName(), "true")// ����Ⱥ��Ự�ۺ���ʾ
				.appendQueryParameter(
						Conversation.ConversationType.DISCUSSION.getName(),
						"false")// ����������Ự�Ǿۺ���ʾ
				.appendQueryParameter(
						Conversation.ConversationType.SYSTEM.getName(), "false")// ����ϵͳ�Ự�Ǿۺ���ʾ
				.build();
		fragment.setUri(uri);

		FragmentTransaction transaction = getActivity()
				.getSupportFragmentManager().beginTransaction();
		transaction.add(R.id.rong_content, fragment);
		transaction.commit();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		context = getActivity();
		mCache = ACache.get(context);

		/**
		 * �����û���Ϣ���ṩ�ߣ��� RongIM ���û�ȡ�û����ƺ�ͷ����Ϣ��
		 * 
		 * @param userInfoProvider
		 *            �û���Ϣ�ṩ�ߡ�
		 * @param isCacheUserInfo
		 *            �����Ƿ��� IMKit �������û���Ϣ��<br>
		 *            ��� App �ṩ�� UserInfoProvider
		 *            ÿ�ζ���Ҫͨ�����������û����ݣ������ǽ��û����ݻ��浽�����ڴ棬��Ӱ���û���Ϣ�ļ����ٶȣ�<br>
		 *            ��ʱ��ý�����������Ϊ true���� IMKit ���û���Ϣ���浽�����ڴ��С�
		 * @see UserInfoProvider
		 */
		RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

			@Override
			public UserInfo getUserInfo(String userId) {

				return findUserById(userId);// ���� userId ȥ����û�ϵͳ���ѯ��Ӧ���û���Ϣ���ظ�����
											// SDK��
			}

		}, true);
		
		
	}
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		Btn_service = (Button) getActivity().findViewById(R.id.btn_service);
		Btn_service.setOnClickListener(this);
		Ibtn_friend=(ImageButton) getActivity().findViewById(R.id.ibtn_friend);
		Ibtn_friend.setOnClickListener(this);
	}

	private UserInfo findUserById(String userId) {
		UserBean userBean = (UserBean) mCache.getAsObject(userId);
		if (userBean != null) {
			Log.e(userId+"-get-", userBean.toString());
			userInfo = new UserInfo(userId, userBean.getName(),
					Uri.parse(userBean.getUri()));
			RongIM.getInstance().refreshUserInfoCache(
					userInfo);
			String iscustomservice=userBean.getIscustomservice();
			if(iscustomservice.equals("1"))
			{
				RongIM.getInstance().getRongIMClient().setConversationToTop(ConversationType.PRIVATE, userId, true);
			}
			return userInfo;
		} else {
			getInfo(Url.getuserinfo + "?id=" + userId, userId);
			return null;
		}
	}

	/**
	 * ��ȡ�û���Ϣ
	 */
	private void getInfo(String url, final String id) {
		final String TAG = url+id;
		final UserBean userBean = new UserBean();
		// TODO Auto-generated method stub
		BaseVolleyRequest.StringRequestGet(context, url, TAG,
				new BaseStrVolleyInterFace(context,
						BaseStrVolleyInterFace.mListener,
						BaseStrVolleyInterFace.mErrorListener) {
					@Override
					public void onSuccess(String response) {
						Log.i(TAG, "GET����ɹ�-->" + response);
						try {
							JSONObject jsonObject = new JSONObject(response);
							String result = jsonObject.getString("result");
							String resulttext = jsonObject
									.getString("resulttext");
							if (result.equals("1001")) {
								String nickname = jsonObject
										.getString("nickname");
								String avatar = jsonObject.getString("avatar");
								String iscustomservice = jsonObject.getString("iscustomservice");
								userInfo = new UserInfo(id, nickname, Uri
										.parse(avatar));
								userBean.setID(id);
								userBean.setName(nickname);
								userBean.setUri(avatar);
								userBean.setIscustomservice(iscustomservice);
								Log.e("-put-", userBean.toString());
								mCache.put(id, userBean, 60*60*24*3);
								RongIM.getInstance().refreshUserInfoCache(
										userInfo);
								if(iscustomservice.equals("1"))
								{
									RongIM.getInstance().getRongIMClient().setConversationToTop(ConversationType.PRIVATE, id, true);
								}
							} else {
								ToastTool.showToast(context, resulttext);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onError(VolleyError volleyError) {
						Log.i(TAG, "GET����ʧ��-->" + volleyError.toString());
					}
				});
	}
	
/*	*//**
	 * ��ȡ�ͷ�id
	 *//*
	private void getService(String url) {
		final String TAG = url;
		// TODO Auto-generated method stub
		BaseVolleyRequest.StringRequestGet(context, url, TAG,
				new BaseStrVolleyInterFace(context,
						BaseStrVolleyInterFace.mListener,
						BaseStrVolleyInterFace.mErrorListener) {
					@Override
					public void onSuccess(String response) {
						Log.i(TAG, "GET����ɹ�-->" + response);
						try {
							JSONObject jsonObject = new JSONObject(response);
							String result = jsonObject.getString("result");
							String resulttext = jsonObject
									.getString("resulttext");
							if (result.equals("1001")) {
								String customserviceid = jsonObject
										.getString("customserviceid");
								RongIM.getInstance().startPrivateChat(context,customserviceid, "�ͷ�����");
							} else {
								ToastTool.showToast(context, resulttext);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onError(VolleyError volleyError) {
						Log.i(TAG, "GET����ʧ��-->" + volleyError.toString());
					}
				});
	}*/

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_service:
//			getService(Url.getservice);
			Intent intent = new MQIntentBuilder(getActivity()).build();
			startActivity(intent);
			break;
		case R.id.ibtn_friend:
			Intent intent2=new Intent(getActivity(),TeamActivity.class);
			startActivity(intent2);
			break;
		default:
			break;
		}
	}


}