package com.gcs.suban.activity;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.BankBean;
import com.gcs.suban.eventbus.PersonEvent;
import com.gcs.suban.listener.OnBaseListener;
import com.gcs.suban.model.BankModel;
import com.gcs.suban.model.BankModelImpl;
import com.gcs.suban.tools.ToastTool;

import io.rong.eventbus.EventBus;


public class BankBundingActivity extends BaseActivity implements OnBaseListener {

	private TextView Tv_title;

	private EditText Et_number;
	private EditText Et_name;
	private EditText Et_bank;
	
	private CardView Cv_bank;

	private ImageButton IBtn_back;

	private Button Btn_confirm;

	private BankModel model;

	private BankBean bean=new BankBean();

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.btn_confirm:
			if (!Et_name.getText().toString().equals("")
					&& !Et_number.getText().toString().equals("")
					&& !Et_bank.getText().toString().equals("")) {
				bean.diytixianxingming=Et_name.getText().toString();
				bean.diyyinxingkahao=Et_number.getText().toString();
				bean.diykaihuxing=Et_bank.getText().toString();
				model.setBank(Url.updatabankcard, bean, this);
			} else {
				ToastTool.showToast(context, "请填写完整信息");
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_bundingbank);
		Tv_title = (TextView) findViewById(R.id.title);
		Tv_title.setText("绑定银行卡");

		Et_number = (EditText) findViewById(R.id.et_number);
		Et_name = (EditText) findViewById(R.id.et_name);
		Et_bank= (EditText) findViewById(R.id.et_bank);
		
		Cv_bank=(CardView)findViewById(R.id.card_bank);
		Cv_bank.setOnClickListener(this);

		IBtn_back = (ImageButton) context.findViewById(R.id.back);
		IBtn_back.setOnClickListener(this);

		Btn_confirm = (Button) context.findViewById(R.id.btn_confirm);
		Btn_confirm.setOnClickListener(this);

		model = new BankModelImpl();
	}

	/**
	 * 网络请求 结果返回成功
	 */
	@Override
	public void onSuccess(String resulttext) {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, resulttext);
		EventBus.getDefault().post(new PersonEvent("bank","msg"));
		finish();
	}

	/**
	 * 网络请求 结果返回失败
	 */
	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, error);
	}

}
