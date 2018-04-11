package com.gcs.suban.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.bean.UserBean;
import com.gcs.suban.tools.ACache;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import io.rong.eventbus.EventBus;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ImageMessage;

/**
 * @作者： jiatao
 * @修改时间：2016-4-19 下午10:00:25
 * @包名：com.cctvjiaotao.rongyundemo
 * @文件名：ConversationActivity.java
 * @版权声明：www.cctvjiatao.com
 * @功能： TODO
 */
public class ConversationActivity extends FragmentActivity implements
		OnClickListener {

	private TextView Tv_title;

	private ImageButton IBtn_back;

	private UserInfo userInfo;

	private Context context = this;

	private ACache mCache;

	private UserBean userBean;

	/**
	 * 目标 Id
	 */
	private String mTargetId;

	/**
	 * 会话类型
	 */
	private Conversation.ConversationType mConversationType;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_conversation);

		Tv_title = (TextView) findViewById(R.id.title);

		IBtn_back = (ImageButton) findViewById(R.id.back);
		IBtn_back.setOnClickListener(this);

		mCache = ACache.get(context);

		Intent intent = getIntent();

		getIntentDate(intent);

		findUserById(mTargetId);

		RongIM.setConversationBehaviorListener(new MyConversationBehaviorListener());
	}

	private void getIntentDate(Intent intent) {

		mTargetId = intent.getData().getQueryParameter("targetId");
		mConversationType = Conversation.ConversationType.valueOf(intent
				.getData().getLastPathSegment()
				.toUpperCase(Locale.getDefault()));

		enterFragment(mConversationType, mTargetId);
	}

	private void enterFragment(Conversation.ConversationType mConversationType,
			String mTargetId) {

		ConversationFragment fragment = new ConversationFragment();

		Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName)
				.buildUpon().appendPath("conversation")
				.appendPath(mConversationType.getName().toLowerCase())
				.appendQueryParameter("targetId", mTargetId).build();

		fragment.setUri(uri);

		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		// xxx 为你要加载的 id
		transaction.add(R.id.rong_content, fragment);
		transaction.commit();

		RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

			@Override
			public UserInfo getUserInfo(String userId) {

				return findUserById(userId);// 根据 userId 去你的用户系统里查询对应的用户信息返回给融云
											// SDK。
			}

		}, true);

/*		// 扩展功能自定义
		InputProvider.ExtendProvider[] provider = {
				new ImageInputProvider(RongContext.getInstance()),// 图片
				new CameraInputProvider(RongContext.getInstance()),// 相机
		};
		RongIM.resetInputExtensionProvider(
				Conversation.ConversationType.PRIVATE, provider);*/
	}

	private UserInfo findUserById(String userId) {
		userBean = (UserBean) mCache.getAsObject(userId);
		if (userBean != null) {
			Log.e(userId + "-get-", userBean.toString());
			userInfo = new UserInfo(userId, userBean.getName(),
					Uri.parse(userBean.getUri()));
			RongIM.getInstance().refreshUserInfoCache(userInfo);
			Tv_title.setText(userBean.getName());
			return userInfo;
		} else {
			getInfo(Url.getuserinfo + "?id=" + userId, userId);
			return null;
		}
	}

	private void getInfo(String url, final String id) {
		final String TAG = url + id;
		// TODO Auto-generated method stub
		BaseVolleyRequest.StringRequestGet(context, url, TAG,
				new BaseStrVolleyInterFace(context,
						BaseStrVolleyInterFace.mListener,
						BaseStrVolleyInterFace.mErrorListener) {
					@Override
					public void onSuccess(String response) {
						Log.i(TAG, "GET请求成功-->" + response);
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
								userBean = new UserBean();
								userBean.setID(id);
								userBean.setName(nickname);
								userBean.setUri(avatar);
								userBean.setIscustomservice(iscustomservice);
								Tv_title.setText(userBean.getName());							
								Log.e("-put-", userBean.toString());
								mCache.put(id, userBean, 60*60*24*3);
								RongIM.getInstance().refreshUserInfoCache(
										userInfo);
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
						Log.i(TAG, "GET请求失败-->" + volleyError.toString());
					}
				});
	}

	private class MyConversationBehaviorListener implements
			RongIM.ConversationBehaviorListener {

		/**
		 * 当点击用户头像后执行。
		 * 
		 * @param context
		 *            上下文。
		 * @param conversationType
		 *            会话类型。
		 * @param userInfo
		 *            被点击的用户的信息。
		 * @return 如果用户自己处理了点击后的逻辑，则返回 true，否则返回 false，false 走融云默认处理方式。
		 */
		@Override
		public boolean onUserPortraitClick(Context context,
				Conversation.ConversationType conversationType,
				UserInfo userInfo) {
			return false;
		}

		/**
		 * 当长按用户头像后执行。
		 * 
		 * @param context
		 *            上下文。
		 * @param conversationType
		 *            会话类型。
		 * @param userInfo
		 *            被点击的用户的信息。
		 * @return 如果用户自己处理了点击后的逻辑，则返回 true，否则返回 false，false 走融云默认处理方式。
		 */
		@Override
		public boolean onUserPortraitLongClick(Context context,
				Conversation.ConversationType conversationType,
				UserInfo userInfo) {
			return false;
		}

		/**
		 * 当点击消息时执行。
		 * 
		 * @param context
		 *            上下文。
		 * @param view
		 *            触发点击的 View。
		 * @param message
		 *            被点击的消息的实体信息。
		 * @return 如果用户自己处理了点击后的逻辑，则返回 true， 否则返回 false, false 走融云默认处理方式。
		 */
		@Override
		public boolean onMessageClick(Context context, View view,
				Message message) {
			if (message.getContent() instanceof ImageMessage) {
				ImageMessage imsg = (ImageMessage) message.getContent();
				Intent intent = new Intent(context, BigImageActivity.class);
				intent.putExtra("uri",imsg.getLocalUri() == null 
					  ? imsg.getRemoteUri() : imsg.getLocalUri());
				context.startActivity(intent);
			}

			return true;
		}

		/**
		 * 当长按消息时执行。
		 * 
		 * @param context
		 *            上下文。
		 * @param view
		 *            触发点击的 View。
		 * @param message
		 *            被长按的消息的实体信息。
		 * @return 如果用户自己处理了长按后的逻辑，则返回 true，否则返回 false，false 走融云默认处理方式。
		 */
		@Override
		public boolean onMessageLongClick(Context context, View view,
				Message message) {
			return false;
		}

		/**
		 * 当点击链接消息时执行。
		 * 
		 * @param context
		 *            上下文。
		 * @param link
		 *            被点击的链接。
		 * @return 如果用户自己处理了点击后的逻辑处理，则返回 true， 否则返回 false, false 走融云默认处理方式。
		 */
		@Override
		public boolean onMessageLinkClick(Context context, String link) {
			return false;
		}
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
