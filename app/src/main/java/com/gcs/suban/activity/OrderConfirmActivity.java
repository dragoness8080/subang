package com.gcs.suban.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.adapter.ConfirmAdapter;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.AddressBean;
import com.gcs.suban.bean.OrderBean;
import com.gcs.suban.bean.ShopDataBean;
import com.gcs.suban.eventbus.OrderEvent;
import com.gcs.suban.listener.OnConfirmListener;
import com.gcs.suban.model.ConfirmModel;
import com.gcs.suban.model.ConfirmOrderModelImpl;
import com.gcs.suban.popwindows.PayPopWindow;
import com.gcs.suban.tools.ClickFilter;
import com.gcs.suban.tools.ToastTool;

import java.util.List;

import io.rong.eventbus.EventBus;


public class OrderConfirmActivity extends BaseActivity implements
		OnConfirmListener {
	public ScrollView mainView;

	private LinearLayout Llyt_address;

	private TextView Tv_title;
	private TextView Tv_realname;
	private TextView Tv_mobile;
	private TextView Tv_address;
	private TextView Tv_totalnum;
	private TextView Tv_totalprice;
	private TextView Tv_mail;
	private TextView Tv_payprice;
	private TextView Tv_all;
	private TextView Tv_coupon;
	

	private EditText Et_msg;

	private Button Btn_confirm;

	private ImageButton IBtn_back;

	private ListView listView;

	private ConfirmAdapter adapter;

	private ConfirmModel model;

	private OrderBean orderBean;
	
	private RelativeLayout Rlyt_coupon;

	private String goodsid;
	private String optionid;
	private String total;
	private String addressid = "-1";
	private String remark;
	
	private String couponname;
	private String deduct="0";
	private String coupondataid="0";
	
	private Double	discount;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.rlyt_coupon:
			Intent intent_coupon = new Intent(context, CouponSelectActivity.class);
			startActivityForResult(intent_coupon, 1);
			break;
		case R.id.btn_confirm:
			if(ClickFilter.ispayFastClick())
			{
				return;
			}
			if (addressid.equals("-1")) {
				ToastTool.showToast(context, "请先选择收货地址");
				return;
			}
			remark = Et_msg.getText().toString();
			orderBean.goodsid = goodsid;
			orderBean.addressid = addressid;
			orderBean.remark = remark;
			orderBean.total = total;
			orderBean.optionid = optionid;
			orderBean.coupondataid = coupondataid;
			dialog.show();
			Btn_confirm.setClickable(false);
			model.confirm(Url.addorder, orderBean, this);
			break;
		case R.id.llyt_address:
			Intent intent = new Intent(context, AddressSelectActivity.class);
			startActivityForResult(intent, 0);
			break;
		default:
			break;
		}
	}

	@Override
	protected void init() {
		EventBus.getDefault().register(this);
		Intent intent = getIntent();
		goodsid = intent.getStringExtra("goodsid");
		optionid = intent.getStringExtra("optionid");
		total = intent.getStringExtra("num");
		orderBean = new OrderBean();
		orderBean.goodsid = goodsid;
		orderBean.optionid = optionid;
		orderBean.addressid="";
		orderBean.total = total;
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_orderconfirm);
		Tv_title = (TextView) findViewById(R.id.title);
		Tv_title.setText("确认订单");
		Tv_realname = (TextView) context.findViewById(R.id.tv_realname);
		Tv_mobile = (TextView) context.findViewById(R.id.tv_tel);
		Tv_address = (TextView) context.findViewById(R.id.tv_address);
		Tv_totalnum = (TextView) context.findViewById(R.id.tv_totalnum);
		Tv_totalprice = (TextView) context.findViewById(R.id.tv_totalprice);
		Tv_payprice = (TextView) context.findViewById(R.id.tv_paymoney);
		Tv_mail = (TextView) context.findViewById(R.id.tv_mail);
		Tv_all = (TextView) context.findViewById(R.id.tv_all);
		Tv_coupon= (TextView) context.findViewById(R.id.tv_coupon);

		Et_msg = (EditText) context.findViewById(R.id.et_msg);
		Et_msg.clearFocus();

		listView = (ListView) context.findViewById(R.id.listView);
		listView.setFocusable(false);

		mainView = (ScrollView) context.findViewById(R.id.mainView);
		mainView.setVisibility(View.INVISIBLE);

		Btn_confirm = (Button) context.findViewById(R.id.btn_confirm);
		Btn_confirm.setOnClickListener(this);

		IBtn_back = (ImageButton) context.findViewById(R.id.back);
		IBtn_back.setOnClickListener(this);

		Llyt_address = (LinearLayout) context.findViewById(R.id.llyt_address);
		Llyt_address.setOnClickListener(this);
		
		Rlyt_coupon= (RelativeLayout) context.findViewById(R.id.rlyt_coupon);
		Rlyt_coupon.setOnClickListener(this);

		hideView();

		model = new ConfirmOrderModelImpl();
		model.getInfo(Url.confirmorder, orderBean, this);

	}

	@Override
	public void onSuccess(OrderBean bean, AddressBean bean2,
			List<ShopDataBean> mListType) {
		// TODO Auto-generated method stub

		adapter = new ConfirmAdapter(this, mListType);
		listView.setAdapter(adapter);
		setListViewHeightBasedOnChildren(listView);
		Tv_totalnum.setText("共" + mListType.size() + "件商品");
		if (bean2 == null) {
			addressid = "-1";
			Tv_realname.setText("您还没有收货地址");
			Tv_mobile.setText("");
			Tv_address.setText("请先添加收货地址");
		} else {
			addressid = bean2.addressid;
			Tv_realname.setText(bean2.realname);
			Tv_mobile.setText(bean2.mobile);
			Tv_address.setText(bean2.province + bean2.city + bean2.area
					+ bean2.address);
		}

		Tv_totalprice.setText("￥" + bean.price);
		Tv_mail.setText("￥" + bean.dispatchprice);
		Tv_payprice.setText("￥" + bean.price);

		showView();
	}

	/**
	 * 订单确认 结果返回成功
	 */
	@Override
	public void onConfirm(OrderBean bean) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(context, PayPopWindow.class);
		Bundle bundle = new Bundle();
		bundle.putDouble("price", bean.price);
		bundle.putDouble("goodsprice", bean.goodsprice);
		bundle.putString("couponprice", deduct);
		bundle.putString("dispatchprice", bean.dispatchprice);
		bundle.putString("orderid", bean.orderid);
		bundle.putString("ordersn", bean.ordersn);
		bundle.putDouble("credit2", bean.credit2);
		intent.putExtras(bundle);
		startActivity(intent);
		Btn_confirm.setClickable(true);
		dialog.dismiss();
	}

	/**
	 * 网络请求 结果返回失败
	 */
	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		Btn_confirm.setClickable(true);
		dialog.dismiss();
		ToastTool.showToast(context, error);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i(TAG, resultCode + "");
		switch (requestCode) {
		case 0:
			if(resultCode==RESULT_OK)
			{
			addressid = data.getStringExtra("addressid");
			Tv_realname.setText(data.getStringExtra("realname"));
			Tv_mobile.setText(data.getStringExtra("mobile"));
			Tv_address.setText(data.getStringExtra("address"));
			orderBean.addressid=addressid;
			model.getInfo(Url.confirmorder, orderBean, this);
			}
			break;
		case 1:
			if(resultCode==RESULT_OK)
			{
				couponname = data.getStringExtra("couponname");
				deduct = data.getStringExtra("deduct");
				coupondataid = data.getStringExtra("coupondataid");
				discount=Double.valueOf(deduct);
				Tv_coupon.setText(couponname);
				Tv_payprice.setText("￥"+(orderBean.price-discount));
			}
			break;
		}
	}

	private void showView() {
		mainView.setVisibility(View.VISIBLE);
		Tv_all.setVisibility(View.VISIBLE);
		Tv_payprice.setVisibility(View.VISIBLE);
		Btn_confirm.setVisibility(View.VISIBLE);
	}

	private void hideView() {
		mainView.setVisibility(View.INVISIBLE);
		Tv_all.setVisibility(View.INVISIBLE);
		Tv_payprice.setVisibility(View.INVISIBLE);
		Btn_confirm.setVisibility(View.INVISIBLE);
	}

	/**
	 * 广播事件
	 */
	public void onEventMainThread(OrderEvent event) {
		finish();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

}
