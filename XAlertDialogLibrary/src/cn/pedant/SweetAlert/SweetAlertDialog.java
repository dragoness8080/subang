package cn.pedant.SweetAlert;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.pedant.birthselect.BirthArrayWheelAdapter;
import cn.pedant.birthselect.BirthOnWheelChangedListener;
import cn.pedant.birthselect.BirthSelect;
import cn.pedant.birthselect.BirthWheelView;

import com.padant.liveselect.ArrayWheelAdapter;
import com.padant.liveselect.LiveAddressSelect;
import com.padant.liveselect.OnWheelChangedListener;
import com.padant.liveselect.WheelView;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.xidian.xalertdialog.R;

import java.util.List;

public class SweetAlertDialog extends Dialog implements View.OnClickListener,
		OnWheelChangedListener, BirthOnWheelChangedListener {
	private View mDialogView;
	private AnimationSet mModalInAnim;
	private AnimationSet mModalOutAnim;
	private Animation mOverlayOutAnim;
	private Animation mErrorInAnim;
	private AnimationSet mErrorXInAnim;
	private AnimationSet mSuccessLayoutAnimSet;
	private Animation mSuccessBowAnim;
	private TextView mTitleTextView;
	private TextView mContentTextView;
	private String mTitleText;
	private String mContentText;
	private boolean mShowCancel;
	private boolean mShowContent;
	private String mCancelText;
	private String mConfirmText;
	private String mFirstBtnText;
	private String mSecondBtnText;
	private String mThirdBtnText;
	private int mAlertType;
	private FrameLayout mErrorFrame;
	private FrameLayout mSuccessFrame;
	private FrameLayout mProgressFrame;
	private SuccessTickView mSuccessTick;
	private ImageView mErrorX;
	private View mSuccessLeftMask;
	private View mSuccessRightMask;
	private Drawable mCustomImgDrawable;
	private ImageView mCustomImage;
	private Button mConfirmButton;
	private Button mCancelButton;
	private ProgressHelper mProgressHelper;
	private FrameLayout mWarningFrame;
	private OnSweetClickListener mCancelClickListener;
	private OnSweetClickListener mConfirmClickListener;
	private OnSweetClickListener AliPayClickListener;
	private OnSweetClickListener WxPayClickListener;
	private OnSweetClickListener Btn3ClickListener;
	private boolean mCloseFromCancel;
	private Button Btn_alipay;
	private Button Btn_wxpay;
	public EditText et;
	private LinearLayout Lv_Address;
	private LinearLayout Lv_Birth;
	private WheelView mViewProvince;
	private WheelView mViewCity;
	private WheelView mViewDistrict;
	private BirthWheelView mViewYear;
	private BirthWheelView mViewMonth;
	private BirthWheelView mViewDay;
	private Button btn_pay3;
	public static final int NORMAL_TYPE = 0;
	public static final int ERROR_TYPE = 1;
	public static final int SUCCESS_TYPE = 2;
	public static final int WARNING_TYPE = 3;
	public static final int CUSTOM_IMAGE_TYPE = 4;
	public static final int PROGRESS_TYPE = 5;
	public static final int PAY_TYPE = 6;
	public static final int EDIT_TYPE = 7;
	public static final int ADDRESS_TYPE = 8;
	public static final int BIRTH_TYPE = 9;
	public static int isneed = 2;

	public static interface OnSweetClickListener {
		public void onClick(SweetAlertDialog sweetAlertDialog);
	}

	public static interface OnPayClickListener {
		public void onClick(SweetAlertDialog sweetAlertDialog);
	}

	public SweetAlertDialog(Context context) {
		this(context, NORMAL_TYPE);
	}

	public SweetAlertDialog(Context context, int alertType) {
		super(context, R.style.alert_dialog);
		setCancelable(true);
		setCanceledOnTouchOutside(false);
		mProgressHelper = new ProgressHelper(context);
		mAlertType = alertType;
		mErrorInAnim = OptAnimationLoader.loadAnimation(getContext(),
				R.anim.error_frame_in);
		mErrorXInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(
				getContext(), R.anim.error_x_in);
		// 2.3.x system don't support alpha-animation on layer-list drawable
		// remove it from animation set
		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
			List<Animation> childAnims = mErrorXInAnim.getAnimations();
			int idx = 0;
			for (; idx < childAnims.size(); idx++) {
				if (childAnims.get(idx) instanceof AlphaAnimation) {
					break;
				}
			}
			if (idx < childAnims.size()) {
				childAnims.remove(idx);
			}
		}
		mSuccessBowAnim = OptAnimationLoader.loadAnimation(getContext(),
				R.anim.success_bow_roate);
		mSuccessLayoutAnimSet = (AnimationSet) OptAnimationLoader
				.loadAnimation(getContext(), R.anim.success_mask_layout);
		mModalInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(
				getContext(), R.anim.modal_in);
		mModalOutAnim = (AnimationSet) OptAnimationLoader.loadAnimation(
				getContext(), R.anim.modal_out);
		mModalOutAnim.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				mDialogView.setVisibility(View.GONE);
				mDialogView.post(new Runnable() {
					@Override
					public void run() {
						if (mCloseFromCancel) {
							SweetAlertDialog.super.cancel();
						} else {
							SweetAlertDialog.super.dismiss();
						}
					}
				});
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
		// dialog overlay fade out
		mOverlayOutAnim = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime,
					Transformation t) {
				WindowManager.LayoutParams wlp = getWindow().getAttributes();
				wlp.alpha = 1 - interpolatedTime;
				getWindow().setAttributes(wlp);
			}
		};
		mOverlayOutAnim.setDuration(120);
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alert_dialog);

		mDialogView = getWindow().getDecorView().findViewById(
				android.R.id.content);
		mTitleTextView = (TextView) findViewById(R.id.title_text);
		mContentTextView = (TextView) findViewById(R.id.content_text);
		mErrorFrame = (FrameLayout) findViewById(R.id.error_frame);
		mErrorX = (ImageView) mErrorFrame.findViewById(R.id.error_x);
		mSuccessFrame = (FrameLayout) findViewById(R.id.success_frame);
		mProgressFrame = (FrameLayout) findViewById(R.id.progress_dialog);
		Btn_alipay = (Button) findViewById(R.id.btn_alipay);
		Btn_wxpay = (Button) findViewById(R.id.btn_wxpay);
		Lv_Address = (LinearLayout) findViewById(R.id.lv_Address);
		mSuccessTick = (SuccessTickView) mSuccessFrame
				.findViewById(R.id.success_tick);
		et = (EditText) findViewById(R.id.edit);
		mSuccessLeftMask = mSuccessFrame.findViewById(R.id.mask_left);
		mSuccessRightMask = mSuccessFrame.findViewById(R.id.mask_right);
		mCustomImage = (ImageView) findViewById(R.id.custom_image);
		mWarningFrame = (FrameLayout) findViewById(R.id.warning_frame);
		mConfirmButton = (Button) findViewById(R.id.confirm_button);
		mCancelButton = (Button) findViewById(R.id.cancel_button);
		mViewProvince = (WheelView) findViewById(R.id.id_province);
		mViewCity = (WheelView) findViewById(R.id.id_city);
		mViewDistrict = (WheelView) findViewById(R.id.id_district);
		btn_pay3 = (Button) findViewById(R.id.btn_pay3);
		mProgressHelper
				.setProgressWheel((ProgressWheel) findViewById(R.id.progressWheel));
		Lv_Birth = (LinearLayout) findViewById(R.id.lv_Birth);
		mViewYear = (BirthWheelView) findViewById(R.id.id_year);
		mViewMonth = (BirthWheelView) findViewById(R.id.id_month);
		mViewDay = (BirthWheelView) findViewById(R.id.id_day);
		mConfirmButton.setOnClickListener(this);
		mCancelButton.setOnClickListener(this);
		Btn_alipay.setOnClickListener(this);
		Btn_wxpay.setOnClickListener(this);
		btn_pay3.setOnClickListener(this);
		setTitleText(mTitleText);
		setContentText(mContentText);
		setCancelText(mCancelText);
		setConfirmText(mConfirmText);
		setFirstBtnText(mFirstBtnText);
		setSecondBtnText(mSecondBtnText);
		setThirdBtnText(mThirdBtnText);
		setThirdBtnVisible(2);
		changeAlertType(mAlertType, true);

	}

	private void restore() {
		mCustomImage.setVisibility(View.GONE);
		mErrorFrame.setVisibility(View.GONE);
		mSuccessFrame.setVisibility(View.GONE);
		mWarningFrame.setVisibility(View.GONE);
		mProgressFrame.setVisibility(View.GONE);
		mConfirmButton.setVisibility(View.VISIBLE);
		mTitleTextView.setVisibility(View.VISIBLE);
		Lv_Address.setVisibility(View.GONE);
		Btn_alipay.setVisibility(View.GONE);
		Btn_wxpay.setVisibility(View.GONE);
		btn_pay3.setVisibility(View.GONE);
		et.setVisibility(View.GONE);
		Lv_Birth.setVisibility(View.GONE);
		mConfirmButton.setBackgroundResource(R.drawable.blue_button_background);
		mErrorFrame.clearAnimation();
		mErrorX.clearAnimation();
		mSuccessTick.clearAnimation();
		mSuccessLeftMask.clearAnimation();
		mSuccessRightMask.clearAnimation();
	}

	private void playAnimation() {
		if (mAlertType == ERROR_TYPE) {
			mErrorFrame.startAnimation(mErrorInAnim);
			mErrorX.startAnimation(mErrorXInAnim);
		} else if (mAlertType == SUCCESS_TYPE) {
			mSuccessTick.startTickAnim();
			mSuccessRightMask.startAnimation(mSuccessBowAnim);
		}
	}

	private void changeAlertType(int alertType, boolean fromCreate) {
		mAlertType = alertType;
		// call after created views
		if (mDialogView != null) {
			if (!fromCreate) {
				// restore all of views state before switching alert type
				restore();
			}
			switch (mAlertType) {
			case ERROR_TYPE:
				mErrorFrame.setVisibility(View.VISIBLE);
				break;
			case SUCCESS_TYPE:
				mSuccessFrame.setVisibility(View.VISIBLE);
				// initial rotate layout of success mask
				mSuccessLeftMask.startAnimation(mSuccessLayoutAnimSet
						.getAnimations().get(0));
				mSuccessRightMask.startAnimation(mSuccessLayoutAnimSet
						.getAnimations().get(1));
				break;
			case WARNING_TYPE:
				mConfirmButton
						.setBackgroundResource(R.drawable.red_button_background);
				mWarningFrame.setVisibility(View.VISIBLE);
				break;
			case CUSTOM_IMAGE_TYPE:
				setCustomImage(mCustomImgDrawable);
				break;
			case PROGRESS_TYPE:
				mProgressFrame.setVisibility(View.VISIBLE);
				mConfirmButton.setVisibility(View.GONE);
				break;
			case PAY_TYPE:
				Btn_alipay.setVisibility(View.VISIBLE);
				Btn_wxpay.setVisibility(View.VISIBLE);
				if (isneed == 1) {
					btn_pay3.setVisibility(View.VISIBLE);
				} else {
					btn_pay3.setVisibility(View.GONE);
				}

				mConfirmButton.setVisibility(View.GONE);
				break;
			case EDIT_TYPE:
				et.setVisibility(View.VISIBLE);
				mCancelButton.setVisibility(View.VISIBLE);
				break;
			case ADDRESS_TYPE:
				Lv_Address.setVisibility(View.VISIBLE);
				mTitleTextView.setVisibility(View.GONE);
				mConfirmButton.setVisibility(View.VISIBLE);
				mCancelButton.setVisibility(View.VISIBLE);
				setUpCityListener();
				setUpData();
				break;
			case BIRTH_TYPE:
				Lv_Birth.setVisibility(View.VISIBLE);
				mTitleTextView.setVisibility(View.GONE);
				mConfirmButton.setVisibility(View.VISIBLE);
				mCancelButton.setVisibility(View.VISIBLE);
				setUpBirthListener();
				setUpBirthData();
				break;
			}
			if (!fromCreate) {
				playAnimation();
			}
		}
	}

	public int getAlerType() {
		return mAlertType;
	}

	public void changeAlertType(int alertType) {
		changeAlertType(alertType, false);
	}

	public String getTitleText() {
		return mTitleText;
	}

	public SweetAlertDialog setTitleText(String text) {
		mTitleText = text;
		if (mTitleTextView != null && mTitleText != null) {
			mTitleTextView.setText(mTitleText);
		}
		return this;
	}

	public SweetAlertDialog setCustomImage(Drawable drawable) {
		mCustomImgDrawable = drawable;
		if (mCustomImage != null && mCustomImgDrawable != null) {
			mCustomImage.setVisibility(View.VISIBLE);
			mCustomImage.setImageDrawable(mCustomImgDrawable);
		}
		return this;
	}

	public SweetAlertDialog setCustomImage(int resourceId) {
		return setCustomImage(getContext().getResources().getDrawable(
				resourceId));
	}

	public String getContentText() {
		return mContentText;
	}

	public SweetAlertDialog setContentText(String text) {
		mContentText = text;
		if (mContentTextView != null && mContentText != null) {
			showContentText(true);
			mContentTextView.setText(mContentText);
		}
		return this;
	}

	public boolean isShowCancelButton() {
		return mShowCancel;
	}

	public SweetAlertDialog showCancelButton(boolean isShow) {
		mShowCancel = isShow;
		if (mCancelButton != null) {
			mCancelButton.setVisibility(mShowCancel ? View.VISIBLE : View.GONE);
		}
		return this;
	}

	public boolean isShowContentText() {
		return mShowContent;
	}

	public SweetAlertDialog showContentText(boolean isShow) {
		mShowContent = isShow;
		if (mContentTextView != null) {
			mContentTextView.setVisibility(mShowContent ? View.VISIBLE
					: View.GONE);
		}
		return this;
	}

	public String getCancelText() {
		return mCancelText;
	}

	public SweetAlertDialog setCancelText(String text) {
		mCancelText = text;
		if (mCancelButton != null && mCancelText != null) {
			showCancelButton(true);
			mCancelButton.setText(mCancelText);
		}
		return this;
	}

	public String getConfirmText() {
		return mConfirmText;
	}

	public SweetAlertDialog setConfirmText(String text) {
		mConfirmText = text;
		if (mConfirmButton != null && mConfirmText != null) {
			mConfirmButton.setText(mConfirmText);
		}
		return this;
	}

	public SweetAlertDialog setFirstBtnText(String text) {
		mFirstBtnText = text;
		if (Btn_alipay != null && mFirstBtnText != null) {
			Btn_alipay.setText(mFirstBtnText);
		}
		return this;
	}

	public SweetAlertDialog setSecondBtnText(String text) {
		mSecondBtnText = text;
		if (Btn_wxpay != null && mSecondBtnText != null) {
			Btn_wxpay.setText(mSecondBtnText);
		}
		return this;
	}

	public SweetAlertDialog setThirdBtnVisible(int num) {
		if (num == 1 && btn_pay3 != null) {
			this.btn_pay3.setVisibility(View.VISIBLE);
		}
		if (num == 2 && btn_pay3 != null) {
			this.btn_pay3.setVisibility(View.GONE);
		}

		return this;
	}

	public SweetAlertDialog setThirdBtnText(String text) {
		mThirdBtnText = text;
		if (btn_pay3 != null && mThirdBtnText != null) {
			btn_pay3.setText(mThirdBtnText);
		}
		return this;
	}

	public SweetAlertDialog setCancelClickListener(OnSweetClickListener listener) {
		mCancelClickListener = listener;
		return this;
	}

	public SweetAlertDialog setAliPayClickListener(OnSweetClickListener listener) {
		AliPayClickListener = listener;
		return this;
	}

	public SweetAlertDialog setWxPayClickListener(OnSweetClickListener listener) {
		WxPayClickListener = listener;
		return this;
	}

	public SweetAlertDialog setBtn3ClickListener(OnSweetClickListener listener) {
		Btn3ClickListener = listener;
		return this;
	}

	public SweetAlertDialog setConfirmClickListener(
			OnSweetClickListener listener) {
		mConfirmClickListener = listener;
		return this;
	}

	protected void onStart() {
		mDialogView.startAnimation(mModalInAnim);
		playAnimation();
	}

	/**
	 * The real Dialog.cancel() will be invoked async-ly after the animation
	 * finishes.
	 */
	@Override
	public void cancel() {
		dismissWithAnimation(true);
	}

	/**
	 * The real Dialog.dismiss() will be invoked async-ly after the animation
	 * finishes.
	 */
	public void dismissWithAnimation() {
		dismissWithAnimation(false);
	}

	private void dismissWithAnimation(boolean fromCancel) {
		mCloseFromCancel = fromCancel;
		mConfirmButton.startAnimation(mOverlayOutAnim);
		mDialogView.startAnimation(mModalOutAnim);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.cancel_button) {
			if (mCancelClickListener != null) {
				mCancelClickListener.onClick(SweetAlertDialog.this);
			} else {
				dismissWithAnimation();
			}
		} else if (v.getId() == R.id.confirm_button) {
			if (mConfirmClickListener != null) {
				mConfirmClickListener.onClick(SweetAlertDialog.this);
			} else {
				dismissWithAnimation();
			}
		}
		if (v.getId() == R.id.btn_alipay) {
			if (AliPayClickListener != null) {
				AliPayClickListener.onClick(SweetAlertDialog.this);
			} else {
				dismissWithAnimation();
			}
		} else if (v.getId() == R.id.btn_wxpay) {
			if (WxPayClickListener != null) {
				WxPayClickListener.onClick(SweetAlertDialog.this);
			} else {
				dismissWithAnimation();
			}
		} else if (v.getId() == R.id.btn_pay3) {
			if (Btn3ClickListener != null) {
				Btn3ClickListener.onClick(SweetAlertDialog.this);
			} else {
				dismissWithAnimation();
			}
		}
	}

	private void setUpCityListener() {
		mViewProvince.addChangingListener(this);
		// 添加change事件
		mViewCity.addChangingListener(this);
		// 添加change事件
		mViewDistrict.addChangingListener(this);
	}

	private void setUpData() {
		LiveAddressSelect.initProvinceDatas(getContext());
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(
				getContext(), LiveAddressSelect.mProvinceDatas));
		// 设置可见条目数量
		mViewProvince.setVisibleItems(7);
		mViewCity.setVisibleItems(7);
		mViewDistrict.setVisibleItems(7);
		updateCities();
//		updateAreas();
	}

	private void updateAreas() {
		int pCurrent = mViewCity.getCurrentItem();
		LiveAddressSelect.mCurrentCityName = LiveAddressSelect.mCitisDatasMap
				.get(LiveAddressSelect.mCurrentProviceName)[pCurrent];
		String[] areas = LiveAddressSelect.mDistrictDatasMap
				.get(LiveAddressSelect.mCurrentCityName);

		if (areas == null) {
			areas = new String[] { "" };
		} else {
			LiveAddressSelect.mCurrentDistrictName = areas[0];
		}
		mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(
				getContext(), areas));
		mViewDistrict.setCurrentItem(0);
	}

	private void updateCities() {
		int pCurrent = mViewProvince.getCurrentItem();
		LiveAddressSelect.mCurrentProviceName = LiveAddressSelect.mProvinceDatas[pCurrent];
		String[] cities = LiveAddressSelect.mCitisDatasMap
				.get(LiveAddressSelect.mCurrentProviceName);
		if (cities == null) {
			cities = new String[] { "" };
		}
		mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(getContext(),
				cities));
		mViewCity.setCurrentItem(0);
		updateAreas();
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		if (wheel == mViewProvince) {
			updateCities();
		} else if (wheel == mViewCity) {
			updateAreas();
		} else if (wheel == mViewDistrict) {
			LiveAddressSelect.mCurrentDistrictName = LiveAddressSelect.mDistrictDatasMap
					.get(LiveAddressSelect.mCurrentCityName)[newValue];
			LiveAddressSelect.mCurrentZipCode = LiveAddressSelect.mZipcodeDatasMap
					.get(LiveAddressSelect.mCurrentDistrictName);
		}
	}

	public ProgressHelper getProgressHelper() {
		return mProgressHelper;
	}

	private void setUpBirthListener() {
		mViewYear.addChangingListener(this);
		// 添加change事件
		mViewMonth.addChangingListener(this);
		// 添加change事件
		mViewDay.addChangingListener(this);
	}

	private void setUpBirthData() {
		BirthSelect.initProvinceDatas(getContext());
		mViewYear.setViewAdapter(new BirthArrayWheelAdapter<String>(
				getContext(), BirthSelect.mYearDatas));
		// 设置可见条目数量
		mViewYear.setVisibleItems(7);
		mViewMonth.setVisibleItems(7);
		mViewDay.setVisibleItems(7);
		updateMonth();
		updateDay();
	}

	private void updateDay() {
		int pCurrent = mViewMonth.getCurrentItem();
		BirthSelect.mCurrentMonthName = BirthSelect.mMonthDatasMap
				.get(BirthSelect.mCurrentYearName)[pCurrent];
		String[] day = BirthSelect.mDayDatasMap
				.get(BirthSelect.mCurrentYearName
						+ BirthSelect.mCurrentMonthName);
		if (day == null) {
			day = new String[] { "" };
		}
		mViewDay.setViewAdapter(new BirthArrayWheelAdapter<String>(
				getContext(), day));
		mViewDay.setCurrentItem(0);
	}

	private void updateMonth() {
		int pCurrent = mViewYear.getCurrentItem();
		BirthSelect.mCurrentYearName = BirthSelect.mYearDatas[pCurrent];
		String[] month = BirthSelect.mMonthDatasMap
				.get(BirthSelect.mCurrentYearName);
		if (month == null) {
			month = new String[] { "" };
		}
		mViewMonth.setViewAdapter(new BirthArrayWheelAdapter<String>(
				getContext(), month));
		mViewMonth.setCurrentItem(0);
		updateDay();
	}

	@Override
	public void onChangedBirth(BirthWheelView wheel, int oldValue, int newValue) {
		if (wheel == mViewYear) {
			updateMonth();
		} else if (wheel == mViewMonth) {
			updateDay();
		} else if (wheel == mViewDay) {
			BirthSelect.mCurrentDayName = BirthSelect.mDayDatasMap
					.get(BirthSelect.mCurrentYearName
							+ BirthSelect.mCurrentMonthName)[newValue];
			BirthSelect.mCurrentZipCode = BirthSelect.mZipcodeDatasMap
					.get(BirthSelect.mCurrentDayName);
		}
	}
}