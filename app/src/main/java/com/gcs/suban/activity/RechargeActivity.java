package com.gcs.suban.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gcs.suban.Constants;
import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.eventbus.PersonEvent;
import com.gcs.suban.listener.OnBaseListener;
import com.gcs.suban.model.RechargeModel;
import com.gcs.suban.model.RechargeModelImpl;
import com.gcs.suban.tools.ToastTool;

import cn.beecloud.BCPay;
import cn.beecloud.async.BCCallback;
import cn.beecloud.async.BCResult;
import cn.beecloud.entity.BCPayResult;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.rong.eventbus.EventBus;


/**
 * 微信充值
 */
public class RechargeActivity extends BaseActivity implements OnBaseListener {
	private TextView Tv_title;

	private ImageButton IBtn_back;

	private Button Btn_confirm;

	private EditText Et_money;

	private String money;
	private String logno;
	
	private int m;

	private RechargeModel model;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.btn_confirm:
			money = Et_money.getText().toString();
			if (!money.equals("")) {
				model.getInfo(Url.recharge, money, this);
				dialog.show();
			} else {
				ToastTool.showToast(context, "请输入充值金额");
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void init() {
		BCPay.initWechatPay(RechargeActivity.this, Constants.WX_APP_KEY);
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_recharge);
		Tv_title = (TextView) findViewById(R.id.title);
		Tv_title.setText("充值");
		IBtn_back = (ImageButton) context.findViewById(R.id.back);
		IBtn_back.setOnClickListener(this);
		Et_money = (EditText) findViewById(R.id.et_money);
		Btn_confirm = (Button) context.findViewById(R.id.btn_confirm);
		Btn_confirm.setOnClickListener(this);

		model = new RechargeModelImpl();
	 }

	/**
	 * 网络请求 结果返回成功
	 */
	@Override
	public void onSuccess(String logno) {
		// TODO Auto-generated method stub
		dialog.dismiss();
		SexPickDialog();
		m = Integer.parseInt(money);
		m = m * 100;
		this.logno=logno;

	}

	/**	
	 * 网络请求 结果返回失败
	 */
	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, error);
		dialog.dismiss();
	}
	
	/** 支付方式选择弹窗 */
	private void SexPickDialog() {
		final SweetAlertDialog sd2 = new SweetAlertDialog(this,
				SweetAlertDialog.PAY_TYPE);
		sd2.setTitleText("选择充值方式");
		sd2.setCancelable(true);
		sd2.setCanceledOnTouchOutside(true);
		sd2.setFirstBtnText("微信支付");
		sd2.setSecondBtnText("支付宝支付");
		sd2.setAliPayClickListener(new SweetAlertDialog.OnSweetClickListener() {

			@Override
			public void onClick(SweetAlertDialog sweetAlertDialog) {
				sd2.dismiss();
				dialog.show();
				BCPay.getInstance(RechargeActivity.this).reqWXPaymentAsync(
						"素邦生活馆账户充值", // 订单标题
						m, // 订单金额(分)
						logno, // 订单流水号
						null, // 扩展参数(可以null)
						bcCallback); // 支付完成后回调入口
			}
		});
		sd2.setWxPayClickListener(new SweetAlertDialog.OnSweetClickListener() {

			@Override
			public void onClick(SweetAlertDialog sweetAlertDialog) {
				sd2.dismiss();
				dialog.show();
				BCPay.getInstance(RechargeActivity.this).reqAliPaymentAsync(
						"素邦生活馆账户充值", // 订单标题
						m, // 订单金额(分)
						logno, // 订单流水号
						null, // 扩展参数(可以null)
						bcCallback); // 支付完成后回调入口
			}
		});
		sd2.show();
	}

	// 定义回调
	BCCallback bcCallback = new BCCallback() {
		@Override
		public void done(final BCResult bcResult) {
			// 此处根据业务需要处理支付结果
			dialog.dismiss();
			final BCPayResult bcPayResult = (BCPayResult) bcResult;

			switch (bcPayResult.getResult()) {
			case BCPayResult.RESULT_SUCCESS:
				ToastTool.showToast(getApplicationContext(), "支付成功");
				EventBus.getDefault().post(new PersonEvent("what", "msg"));
				break;
			case BCPayResult.RESULT_CANCEL:
				// 用户取消支付"
				//ToastTool.showToast(getApplicationContext(), "取消支付");
				break;
			case BCPayResult.RESULT_FAIL:
				// 支付失败
				String toastMsg = "支付失败, 原因: " + bcPayResult.getErrCode()
						+ " # " + bcPayResult.getErrMsg() + " # "
						+ bcPayResult.getDetailInfo();
				Log.e(TAG, toastMsg);
			}
		}
	};

}
