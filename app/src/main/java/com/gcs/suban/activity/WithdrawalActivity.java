package com.gcs.suban.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.eventbus.PersonEvent;
import com.gcs.suban.listener.OnBaseListener;
import com.gcs.suban.model.WithDrawModelImpl;
import com.gcs.suban.model.WithdrawModel;
import com.gcs.suban.tools.ToastTool;

import io.rong.eventbus.EventBus;

/**
 * 余额提现
 */
public class WithdrawalActivity extends BaseActivity implements OnBaseListener{
	private TextView Tv_title;
	
	private ImageButton IBtn_back;
	
	private Button Btn_confirm;
	
	private EditText Et_money;
	
	private String money;
	
	private WithdrawModel model;
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.btn_confirm:
			money=Et_money.getText().toString();
			if(!money.equals(""))
			{
				model.onWirhdraw(Url.withdraw, money, this);
				dialog.show();
			}
			else {
				ToastTool.showToast(context, "请输入提现金额");
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_withdrawal);
		Tv_title = (TextView) findViewById(R.id.title);
		Tv_title.setText("余额提现");
		IBtn_back = (ImageButton) context.findViewById(R.id.back);
		IBtn_back.setOnClickListener(this);
		Et_money=(EditText)findViewById(R.id.et_money);
		Btn_confirm = (Button) context.findViewById(R.id.btn_confirm);
		Btn_confirm.setOnClickListener(this);
		
		model=new WithDrawModelImpl();
	}
	
	/**
	 * 网络请求 结果返回成功
	 */
	@Override
	public void onSuccess(String resulttext) {
		// TODO Auto-generated method stub
		dialog.dismiss();
		ToastTool.showToast(context, resulttext);
		EventBus.getDefault().post(new PersonEvent("what", "msg"));
		finish();
	}
	/**
	 * 网络请求 结果返回失败
	 */
	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		dialog.dismiss();
		ToastTool.showToast(context, error);
	}
	
}
