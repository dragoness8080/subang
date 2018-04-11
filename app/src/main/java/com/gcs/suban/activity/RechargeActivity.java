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
 * ΢�ų�ֵ
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
				ToastTool.showToast(context, "�������ֵ���");
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
		Tv_title.setText("��ֵ");
		IBtn_back = (ImageButton) context.findViewById(R.id.back);
		IBtn_back.setOnClickListener(this);
		Et_money = (EditText) findViewById(R.id.et_money);
		Btn_confirm = (Button) context.findViewById(R.id.btn_confirm);
		Btn_confirm.setOnClickListener(this);

		model = new RechargeModelImpl();
	 }

	/**
	 * �������� ������سɹ�
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
	 * �������� �������ʧ��
	 */
	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, error);
		dialog.dismiss();
	}
	
	/** ֧����ʽѡ�񵯴� */
	private void SexPickDialog() {
		final SweetAlertDialog sd2 = new SweetAlertDialog(this,
				SweetAlertDialog.PAY_TYPE);
		sd2.setTitleText("ѡ���ֵ��ʽ");
		sd2.setCancelable(true);
		sd2.setCanceledOnTouchOutside(true);
		sd2.setFirstBtnText("΢��֧��");
		sd2.setSecondBtnText("֧����֧��");
		sd2.setAliPayClickListener(new SweetAlertDialog.OnSweetClickListener() {

			@Override
			public void onClick(SweetAlertDialog sweetAlertDialog) {
				sd2.dismiss();
				dialog.show();
				BCPay.getInstance(RechargeActivity.this).reqWXPaymentAsync(
						"�ذ�������˻���ֵ", // ��������
						m, // �������(��)
						logno, // ������ˮ��
						null, // ��չ����(����null)
						bcCallback); // ֧����ɺ�ص����
			}
		});
		sd2.setWxPayClickListener(new SweetAlertDialog.OnSweetClickListener() {

			@Override
			public void onClick(SweetAlertDialog sweetAlertDialog) {
				sd2.dismiss();
				dialog.show();
				BCPay.getInstance(RechargeActivity.this).reqAliPaymentAsync(
						"�ذ�������˻���ֵ", // ��������
						m, // �������(��)
						logno, // ������ˮ��
						null, // ��չ����(����null)
						bcCallback); // ֧����ɺ�ص����
			}
		});
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
				ToastTool.showToast(getApplicationContext(), "֧���ɹ�");
				EventBus.getDefault().post(new PersonEvent("what", "msg"));
				break;
			case BCPayResult.RESULT_CANCEL:
				// �û�ȡ��֧��"
				//ToastTool.showToast(getApplicationContext(), "ȡ��֧��");
				break;
			case BCPayResult.RESULT_FAIL:
				// ֧��ʧ��
				String toastMsg = "֧��ʧ��, ԭ��: " + bcPayResult.getErrCode()
						+ " # " + bcPayResult.getErrMsg() + " # "
						+ bcPayResult.getDetailInfo();
				Log.e(TAG, toastMsg);
			}
		}
	};

}
