package com.gcs.suban.popwindows;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gcs.suban.Constants;
import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.activity.OrderActivity;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.OrderBean;
import com.gcs.suban.eventbus.OrderEvent;
import com.gcs.suban.eventbus.PersonEvent;
import com.gcs.suban.listener.OnBaseListener;
import com.gcs.suban.listener.OnPayListener;
import com.gcs.suban.model.PayModel;
import com.gcs.suban.model.PayModelImpl;
import com.gcs.suban.tools.ToastTool;
import com.unionpay.UPPayAssistEx;

import cn.beecloud.BCPay;
import cn.beecloud.async.BCCallback;
import cn.beecloud.async.BCResult;
import cn.beecloud.entity.BCPayResult;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.rong.eventbus.EventBus;


public class PayPopWindow extends BaseActivity implements OnPayListener,
		OnBaseListener {

	private TextView Tv_credit2;
	private TextView Tv_price;
	private TextView Tv_dispatchprice;
	private TextView Tv_coupon;
	private TextView Tv_payprice;
	private TextView Tv_payway;

	private Button Btn_isCredit2;
	private Button Btn_confirm;
	private Button Btn_cancel;

	private RelativeLayout Rlyt_payway;
	private RelativeLayout Rlyt_coupon;

	private String orderid;
	private String ordersn;
	private String dispatchprice;// �˷�
	private String couponprice;
	private String paytype;

	private double price;// �ܼ�
	private double goodsprice;// ��Ʒ�۸�
	private double credit2;// ���

	private boolean isCredit2 = false;

	private int third = 1; // ΢�� 1 ֧����2 ����3
	private int m;

	private PayModel model;
	private View mCardView;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.cancel:
				mCardView.setVisibility(View.GONE);
				break;
			case R.id.confirm:
				mCardView.setVisibility(View.GONE);
				UPPayAssistEx.installUPPayPlugin(PayPopWindow.this);
				break;
		case R.id.btn_cancel:
			finish();
			break;
		case R.id.btn_confirm:
			if (!isCredit2) {
				if (third == 1) {
					paytype = "21";
				} else {
					paytype = "22";
				}
			} else if (isCredit2) {
				paytype = "1";
			}
			dialog.show();
			m = (int) (price * 100);
			thirdPay();
			break;
		case R.id.btn_select:
			if(price>credit2)
			{
				ToastTool.showToast(context, "���㣬���ֵ������ʹ���������ʽ");
			}
			else
			{
			isCredit2 = (!isCredit2);
			setData();
			}
			break;
		case R.id.rlyt_payway:
			selectpay();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BCPay.initWechatPay(PayPopWindow.this, Constants.WX_APP_KEY);
	}

	@Override
	protected void init() {
		EventBus.getDefault().post(new PersonEvent("", ""));
		Intent intent = getIntent();
		Bundle bundle = new Bundle();
		bundle = intent.getExtras();
		orderid = bundle.getString("orderid");
		ordersn = bundle.getString("ordersn");
		price = bundle.getDouble("price");
		goodsprice= bundle.getDouble("goodsprice");
		dispatchprice = bundle.getString("dispatchprice");
		credit2 = bundle.getDouble("credit2");
		couponprice= bundle.getString("couponprice");

		InitImageLoader();

		setContentView(R.layout.popwindow_pay);
		SetWindow();

		// TODO Auto-generated method stub
		Btn_confirm = (Button) findViewById(R.id.btn_confirm);
		Btn_confirm.setOnClickListener(this);
		Btn_cancel = (Button) findViewById(R.id.btn_cancel);
		Btn_cancel.setOnClickListener(this);
		mCardView = findViewById(R.id.dialog);

		findViewById(R.id.confirm).setOnClickListener(this);
		findViewById(R.id.cancel).setOnClickListener(this);

		mCardView.setVisibility(View.GONE);
		Btn_isCredit2 = (Button) findViewById(R.id.btn_select);
		Btn_isCredit2.setOnClickListener(this);

		Rlyt_payway = (RelativeLayout) findViewById(R.id.rlyt_payway);
		Rlyt_payway.setOnClickListener(this);
		Rlyt_coupon = (RelativeLayout) findViewById(R.id.rlyt_coupon);

		Tv_credit2 = (TextView) findViewById(R.id.tv_credit2);
		Tv_price = (TextView) findViewById(R.id.tv_price);
		Tv_dispatchprice = (TextView) findViewById(R.id.tv_dispatchprice);
		Tv_coupon= (TextView) findViewById(R.id.tv_coupon);
		Tv_payprice = (TextView) findViewById(R.id.tv_payprice);
		Tv_payway = (TextView) findViewById(R.id.tv_payway);
		Tv_price.setText("��" + goodsprice);
		Tv_credit2.setText("��" + credit2);
		Tv_dispatchprice.setText("��" + dispatchprice);
		Tv_coupon.setText("��" + couponprice);
		Tv_payprice.setText("��" + price);
		
		if(couponprice.equals("0"))
		{
			Rlyt_coupon.setVisibility(View.GONE);
		}
		else
		{
			Rlyt_coupon.setVisibility(View.VISIBLE);
		}

		setData();

		model = new PayModelImpl();
	}

	@Override
	public void onSuccess(String ordersn) {
		// TODO Auto-generated method stub
		this.ordersn = ordersn;
		thirdPay();
	}

	@Override
	public void onPay(String resulttext) {
		// TODO Auto-generated method stub
		Intent intent_deliver = new Intent(context, OrderActivity.class);
		intent_deliver.putExtra("postion", "2");
		intent_deliver.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent_deliver);

		ToastTool.showToast(context, resulttext);
		EventBus.getDefault().post(new OrderEvent("update", "msg"));
		finish();
	}

	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, error);
	}

	private void setData() {
		if (isCredit2) {
				Btn_isCredit2.setBackgroundResource(R.drawable.icon_all_select);
		} else {
			Btn_isCredit2.setBackgroundResource(R.drawable.icon_all_unselect);
		}
	}

	private void Reset() {
		model.onReset(Url.reordersn, orderid, this);
	}

	private void PayComplete() {
		OrderBean bean = new OrderBean();
		bean.paytype = paytype;
		bean.orderid = orderid;
		Log.e(TAG, paytype + "");
		model.onPay(Url.paysuccess, bean, this);
	}

	private void thirdPay() {
		if (!paytype.equals("1")) {
			if (third == 1) {
				// ����֧��
				BCPay.getInstance(PayPopWindow.this).reqWXPaymentAsync(
						"�ذ���Ʒ֧��", // ��������
						m, // �������(��)
						ordersn, // ������ˮ��
						null, // ��չ����(����null)
						bcCallback); // ֧����ɺ�ص����
			} else if (third == 2){
				BCPay.getInstance(PayPopWindow.this).reqAliPaymentAsync(
						"�ذ���Ʒ֧��", // ��������
						m, // �������(��)
						ordersn, // ������ˮ��
						null, // ��չ����(����null)
						bcCallback); // ֧����ɺ�ص����
			} else if (third==3) {
				BCPay.getInstance(PayPopWindow.this).reqUnionPaymentAsync("�ذ���Ʒ֧��", // ��������
						m, // �������(��)
						ordersn, // ������ˮ��
						null, // ��չ����(����null)
						bcCallback);
			}
		} else {
			PayComplete();
		}
	}

	// ѡַ֧����ʽ
	private void selectpay() {
		final SweetAlertDialog sd2 = new SweetAlertDialog(this,
				SweetAlertDialog.PAY_TYPE);
		sd2.setTitleText("��ѡ��֧����ʽ");
		sd2.setCancelable(true);
		sd2.setCanceledOnTouchOutside(true);
		sd2.setAliPayClickListener(new SweetAlertDialog.OnSweetClickListener() {

			@Override
			public void onClick(SweetAlertDialog sweetAlertDialog) {
				 third = 2;
				 Tv_payway.setText("֧����֧��");
				//ToastTool.showToast(context, "֧����֧��������δ���ţ������ڴ�");
				sd2.dismiss();
			}
		});
		sd2.setWxPayClickListener(new SweetAlertDialog.OnSweetClickListener() {

			@Override
			public void onClick(SweetAlertDialog sweetAlertDialog) {
				third = 1;
				Tv_payway.setText("΢��֧��");
				sd2.dismiss();
			}
		});
		sd2.setBtn3ClickListener(new SweetAlertDialog.OnSweetClickListener() {
			@Override
			public void onClick(SweetAlertDialog sweetAlertDialog) {
				third = 3;
				Tv_payway.setText("����֧��");
				sd2.dismiss();
			}
		});
		sd2.isneed = 1;
		sd2.setThirdBtnText("����֧��");
		sd2.setThirdBtnVisible(1);
		sd2.show();
	}

	// ����ص�
	BCCallback bcCallback = new BCCallback() {
		@Override
		public void done(final BCResult bcResult) {
			// �˴�����ҵ����Ҫ����֧�����
			dialog.dismiss();
			final BCPayResult bcPayResult = (BCPayResult) bcResult;

			switch (bcPayResult.getResult()) {
			case BCPayResult.RESULT_SUCCESS:
				// �û�֧���ɹ�
				PayComplete();
				break;
			case BCPayResult.RESULT_CANCEL:
				// �û�ȡ��֧��"
				break;
			case BCPayResult.RESULT_FAIL:

				String toastMsg = "֧��ʧ��, ԭ��: " + bcPayResult.getErrCode()
						+ " # " + bcPayResult.getErrMsg() + " # "
						+ bcPayResult.getDetailInfo();
				if (bcPayResult.getErrMsg().equals(BCPayResult.FAIL_PLUGIN_NOT_INSTALLED)) {
					//������Ҫ���°�װ�ؼ�
					Message msg = mHandler.obtainMessage();
					msg.what = 1;
					mHandler.sendMessage(msg);
					return;
				}
				if (bcPayResult.getErrCode() == 7)// �������ظ�
				{
					Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show();
//					Reset();
//					dialog.show();
				}
			}
		}
	};

	private Handler mHandler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
				case 1:
					mCardView.setVisibility(View.VISIBLE);
					break;
				case 2:
					break;
			}
			return true;
		}
	});


	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().post(new OrderEvent("what", "msg"));
	}
}
