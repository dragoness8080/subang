package com.gcs.suban.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.gcs.suban.MyDate;
import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.bean.CouponBean;
import com.gcs.suban.eventbus.MainEvent;
import com.gcs.suban.fragment.ConversationListDynamicFragment;
import com.gcs.suban.fragment.CultureFragment;
import com.gcs.suban.fragment.HomeFragment;
import com.gcs.suban.fragment.MemberFragment;
import com.gcs.suban.listener.OnCouponListener;
import com.gcs.suban.model.CouPonModel;
import com.gcs.suban.model.CouponModelImpl;
import com.gcs.suban.popwindows.CouponPopWindow;
import com.gcs.suban.tools.SharedPreference;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.tools.UpdateManager;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.rong.eventbus.EventBus;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

public class MainActivity extends FragmentActivity implements OnClickListener,
		OnCouponListener {
	private Context context = this;

	private String TAG = "MainActivity";

	private LinearLayout mTab1;
	private LinearLayout mTab2;
	private LinearLayout mTab3;
	private LinearLayout mTab4;
	private LinearLayout mTab5;

	private ImageButton Ibtn_home;
	private ImageButton Ibtn_car;
	private ImageButton Ibtn_culture;
	private ImageButton Ibtn_notice;
	private ImageButton Ibtn_person;

	private Fragment mTab01;
	private Fragment mTab02;
	private Fragment mTab03;
	private Fragment mTab04;
	private Fragment mTab05;

	private CouPonModel model;

	private String token;
	private UserInfo userInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		EventBus.getDefault().post(new MainEvent("login", "msg"));
		EventBus.getDefault().register(this);
		String vid = (String) SharedPreference.getParam(context, "vid",
				"");
		MyDate.setMyVid(vid);
		Log.i("vid", vid);
		initView();
		initEvent();
		setSelect(1);

		JPush();

		UpdateManager updateManager = new UpdateManager(context);
		updateManager.VolleyGet();

		getInfo(Url.gettoken + "?openid=" + MyDate.getMyVid());

		model = new CouponModelImpl();
		model.getInfo(Url.iscoupon, this);
	}

	private void JPush() {
		final String jpushid = (String) SharedPreference.getParam(context,
				"jpushid", "");
		if (!jpushid.equals("")) {
			Log.e(TAG, "jpushid:" + jpushid);
			JPushInterface.setAlias(context, jpushid, new TagAliasCallback() {
				@Override
				public void gotResult(int arg0, String arg1, Set<String> arg2) {

				}
			});
		}

		try {
			Bundle bundle = this.getIntent().getExtras();
			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
			final JSONObject extrasJson = new JSONObject(extras);
			final String txt = extrasJson.optString("txt");
			final String type = extrasJson.optString("type");
			final String btnname = extrasJson.optString("btnname");

			new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
					.setTitleText(txt)
					.setCancelText("取消")
					.setConfirmText(btnname)
					.showCancelButton(true)
					.setConfirmClickListener(
							new SweetAlertDialog.OnSweetClickListener() {
								@Override
								public void onClick(SweetAlertDialog dialog) {

									if (type.equals("xiaoxi")) {
										Intent intent = new Intent(context,
												MessageActvity.class);
										startActivity(intent);
										dialog.dismiss();
									}
								}
							})
					.setCancelClickListener(
							new SweetAlertDialog.OnSweetClickListener() {
								@Override
								public void onClick(SweetAlertDialog dialog) {
									dialog.cancel();
								}
							}).show();

		} catch (Exception e) {
			Log.d("Jpush", "bundle??????");
		}

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// ????????position
	}

	private void initEvent() {
		mTab1.setOnClickListener(this);
		mTab2.setOnClickListener(this);
		mTab3.setOnClickListener(this);
		mTab4.setOnClickListener(this);
		mTab5.setOnClickListener(this);
	}

	private void initView() {
		mTab1 = (LinearLayout) findViewById(R.id.tab_1);
		mTab2 = (LinearLayout) findViewById(R.id.tab_2);
		mTab3 = (LinearLayout) findViewById(R.id.tab_3);
		mTab4 = (LinearLayout) findViewById(R.id.tab_4);
		mTab5 = (LinearLayout) findViewById(R.id.tab_5);

		Ibtn_home = (ImageButton) findViewById(R.id.ibtn_home);
		Ibtn_car = (ImageButton) findViewById(R.id.ibtn_car);
		Ibtn_culture = (ImageButton) findViewById(R.id.ibtn_culture);
		Ibtn_notice = (ImageButton) findViewById(R.id.ibtn_notice);
		Ibtn_person = (ImageButton) findViewById(R.id.ibtn_person);

	}

	public void setSelect(int i) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		switch (i) {
		case 1:
			hideFragment(transaction);
			if (mTab01 == null) {
				mTab01 = new HomeFragment();
				transaction.add(R.id.id_content, mTab01);
			} else {
				transaction.show(mTab01);
			}
			Ibtn_home.setBackgroundResource(R.drawable.btn_home_press);
			break;
		case 2:
			Intent intent2 = new Intent(context, CarActivity.class);
			startActivity(intent2);
			break;
		case 3:
			hideFragment(transaction);
			if (mTab03 == null) {
				mTab03 = new CultureFragment();
				transaction.add(R.id.id_content, mTab03);
			} else {
				transaction.show(mTab03);
			}
			Ibtn_culture.setBackgroundResource(R.drawable.btn_culture_press);
			break;
		case 4:
			hideFragment(transaction);
			if (mTab04 == null) {
				mTab04 = new ConversationListDynamicFragment();
				transaction.add(R.id.id_content, mTab04);
			} else {
				transaction.show(mTab04);
			}
			Ibtn_notice.setBackgroundResource(R.drawable.btn_notice_press);
			break;
		case 5:
			hideFragment(transaction);
			if (mTab05 == null) {
				mTab05 = new MemberFragment();
				transaction.add(R.id.id_content, mTab05);
			} else {
				transaction.show(mTab05);
			}
			Ibtn_person.setBackgroundResource(R.drawable.btn_person_press);
			break;
		default:
			break;
		}

		transaction.commit();
	}

	private void hideFragment(FragmentTransaction transaction) {
		if (mTab01 != null) {
			transaction.hide(mTab01);
		}
		if (mTab02 != null) {
			transaction.hide(mTab02);
		}
		if (mTab03 != null) {
			transaction.hide(mTab03);
		}
		if (mTab04 != null) {
			transaction.hide(mTab04);
		}
		if (mTab05 != null) {
			transaction.hide(mTab05);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tab_1:
			resetImgs();
			setSelect(1);
			break;
		case R.id.tab_2:
			setSelect(2);
			break;
		case R.id.tab_3:
			resetImgs();
			setSelect(3);
			break;
		case R.id.tab_4:
			if(RongIM.getInstance()!=null && RongIM.getInstance().getRongIMClient()!=null){
				resetImgs();
				setSelect(4);
	        }
			else {
				getInfo(Url.gettoken + "?openid=" + MyDate.getMyVid());

			}
			break;
		case R.id.tab_5:
			resetImgs();
			setSelect(5);
			break;
		default:
			break;
		}
	}


	public void resetImgs() {
		Ibtn_home.setBackgroundResource(R.drawable.btn_home_normal);
		Ibtn_car.setBackgroundResource(R.drawable.btn_car_normal);
		Ibtn_culture.setBackgroundResource(R.drawable.btn_culture_normol);
		Ibtn_notice.setBackgroundResource(R.drawable.btn_notice_normal);
		Ibtn_person.setBackgroundResource(R.drawable.btn_person_normal);
	}

	public void onEventMainThread(MainEvent event) {
		if (event.getType().equals("exit")) {
			finish();
		}
		if (event.getType().equals("select")) {
			resetImgs();
			setSelect(0);
		}
	}

	private void getInfo(String url) {
		final String TAG = url;
		// TODO Auto-generated method stub
		BaseVolleyRequest.StringRequestGet(context, url, TAG,
				new BaseStrVolleyInterFace(context,
						BaseStrVolleyInterFace.mListener,
						BaseStrVolleyInterFace.mErrorListener) {
					@Override
					public void onSuccess(String response) {

						try {
							JSONObject jsonObject = new JSONObject(response);
							String result = jsonObject.getString("result");
							String resulttext = jsonObject
									.getString("resulttext");
							if (result.equals("1001")) {
								token = jsonObject.getString("token");
								SharedPreference.setParam(context, "token",
										token);
								MyDate.setToken(token);
								String nickname = jsonObject
										.getString("nickname");
								String avatar = jsonObject.getString("avatar");
								String id = jsonObject.getString("id");
								userInfo = new UserInfo(id,
										nickname, Uri.parse(avatar));
								RongIM.getInstance().refreshUserInfoCache(
										userInfo);
								conn();
							} else {
								ToastTool.showToast(context, resulttext);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Error(Url.jsonError);
						}
					}

					@Override
					public void onError(VolleyError volleyError) {
						Error(Url.networkError);
					}
				});
	}

	private void Error(String error) {
		ToastTool.showToast(context, error);
	}


	private void conn() {
		RongIM.connect(token, new RongIMClient.ConnectCallback() {


			@Override
			public void onTokenIncorrect() {
				Log.e("LoginActivity", "--onTokenIncorrect--");
			}

					@Override
			public void onSuccess(String userid) {
				Log.e("LoginActivity", "--onSuccess--" + userid);

			}

			@Override
			public void onError(RongIMClient.ErrorCode errorCode) {

				Log.e("LoginActivity", "--onError--" + errorCode);
			}
		});
	}

	@Override
	public void onSuccess(CouponBean bean) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(context, CouponPopWindow.class);
		intent.putExtra("couponname", bean.couponname);
		intent.putExtra("deduct", bean.deduct);
		intent.putExtra("useday", bean.useday);
		startActivity(intent);
	}

	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		Log.e(TAG, "error----" + error);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			initBackDialog();
			return true;
		} else
			return super.onKeyDown(keyCode, event);
	}

	// ???????
	private void initBackDialog() {
		new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
				.setTitleText("用户退出")
				.setContentText("是否退出程序?")
				.setCancelText("取消")
				.setConfirmText("确认")
				.showCancelButton(true)
				.setConfirmClickListener(
						new SweetAlertDialog.OnSweetClickListener() {
							@Override
							public void onClick(SweetAlertDialog sDialog) {
								sDialog.cancel();
								Intent home = new Intent(Intent.ACTION_MAIN);
								home.addCategory(Intent.CATEGORY_HOME);
								startActivity(home);
								// MyApplication.getInstance().AppExit();
							}
						})
				.setCancelClickListener(
						new SweetAlertDialog.OnSweetClickListener() {
							@Override
							public void onClick(SweetAlertDialog sDialog) {
								sDialog.cancel();
							}
						}).show();
	}

}