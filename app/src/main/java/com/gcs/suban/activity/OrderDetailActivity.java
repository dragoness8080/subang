package com.gcs.suban.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gcs.suban.OrderHelper;
import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.adapter.ConfirmAdapter;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.AddressBean;
import com.gcs.suban.bean.OrderBean;
import com.gcs.suban.bean.ShopDataBean;
import com.gcs.suban.eventbus.OrderEvent;
import com.gcs.suban.listener.OnDetailListener;
import com.gcs.suban.model.OrderModel;
import com.gcs.suban.model.OrderModelImpl;
import com.gcs.suban.tools.ToastTool;

import java.util.List;

import io.rong.eventbus.EventBus;


public class OrderDetailActivity extends BaseActivity implements OnDetailListener{

	private TextView Tv_title;
	private TextView Tv_realname;
	private TextView Tv_mobile;
	private TextView Tv_address;
	
	public TextView Tv_statusname;
	public TextView Tv_ordersn;
	public TextView Tv_topdispatchprice;
	public TextView Tv_dispatchprice;
	public TextView Tv_toppayprice;
	public TextView Tv_payprice;
	public TextView Tv_goodsprice;
	
	public ScrollView mainView;
	public RelativeLayout Rlyt_button;
	
	private ImageButton IBtn_back;

	public Button Btn_Left;
	public Button Btn_Right;

	private ListView listView;

	private ConfirmAdapter adapter;

	private OrderModel model;

	private OrderBean orderBean=new OrderBean();

	private String orderid;
	private String ordersn;
	//private String status;
	
	private OrderHelper helper=new OrderHelper(context);

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.btn_left:
			helper.leftClick(orderBean);
			break;
		case R.id.btn_right:
			helper.rightClick(orderBean);
			break;
		default:
			break;
		}
	}

	@Override
	protected void init() {		
		EventBus.getDefault().register(this);
		Intent intent = getIntent();
		orderid = intent.getStringExtra("orderid");
		ordersn = intent.getStringExtra("ordersn");
		orderBean.orderid=orderid;
		orderBean.ordersn=ordersn;
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_orderdetail);
		Tv_title = (TextView) findViewById(R.id.title);
		Tv_title.setText("订单详情");
		Tv_realname = (TextView) context.findViewById(R.id.tv_realname);
		Tv_mobile = (TextView) context.findViewById(R.id.tv_tel);
		Tv_address = (TextView) context.findViewById(R.id.tv_address);
		
		mainView=(ScrollView)context.findViewById(R.id.mainView);
		
		Rlyt_button=(RelativeLayout)context.findViewById(R.id.rlyt_button);
		
		listView=(ListView)context.findViewById(R.id.listView);
		listView.setFocusable(false);
		
		IBtn_back=(ImageButton)context.findViewById(R.id.back);
		IBtn_back.setOnClickListener(this);
		
		Tv_ordersn = (TextView) context
				.findViewById(R.id.tv_ordersn);
		Tv_statusname = (TextView) context
				.findViewById(R.id.tv_statusname);
		Tv_payprice = (TextView) context
				.findViewById(R.id.tv_payprice);
		Tv_goodsprice = (TextView) context
				.findViewById(R.id.tv_goodsprice);
		Tv_dispatchprice = (TextView) context
				.findViewById(R.id.tv_dispatchprice);
		Tv_toppayprice = (TextView) context
				.findViewById(R.id.tv_topprice);
		Tv_topdispatchprice = (TextView) context
				.findViewById(R.id.tv_topdispatchprice);
		
		Btn_Left = (Button) context.findViewById(R.id.btn_left);
		Btn_Left.setOnClickListener(this);
		Btn_Right = (Button) context.findViewById(R.id.btn_right);
		Btn_Right.setOnClickListener(this);
		
		hideView();
		
		model=new OrderModelImpl();
		model.getDetail(Url.orderdetails, orderBean, this);
	}
	
	@Override
	public void onDetailSuccess(OrderBean orderBean, AddressBean addressBean,
			List<ShopDataBean> mListType) {
		// TODO Auto-generated method stub
		this.orderBean=orderBean;
		helper.setButton(orderBean, Btn_Left, Btn_Right);

		adapter = new ConfirmAdapter(this, mListType);
		listView.setAdapter(adapter);
		setListViewHeightBasedOnChildren(listView);
		
		Tv_realname.setText(addressBean.realname);
		Tv_mobile.setText(addressBean.mobile);
		Tv_address.setText(addressBean.province+addressBean.city+addressBean.area+addressBean.address);
		
		Tv_statusname.setText(orderBean.statusname);
		Tv_ordersn.setText(orderBean.ordersn);
		Tv_goodsprice.setText("￥" +orderBean.goodsprice);
		Tv_dispatchprice.setText("￥" +orderBean.dispatchprice);
		Tv_topdispatchprice.setText("￥" +orderBean.dispatchprice);
		Tv_payprice.setText("￥" +orderBean.price);
		Tv_toppayprice.setText("￥" +orderBean.price);
		showView();
	}
	/**
	 * 网络请求 结果返回失败
	 */
	@Override
	public void onDetailError(String error) {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, error);
	}
	
	private void showView()
	{
		mainView.setVisibility(View.VISIBLE);
		Rlyt_button.setVisibility(View.VISIBLE);
		Tv_realname.setVisibility(View.VISIBLE);
		Tv_mobile.setVisibility(View.VISIBLE);
		Tv_address.setVisibility(View.VISIBLE);
		
		Tv_statusname.setVisibility(View.VISIBLE);
		Tv_ordersn.setVisibility(View.VISIBLE);
		Tv_goodsprice.setVisibility(View.VISIBLE);
		Tv_dispatchprice.setVisibility(View.VISIBLE);
		Tv_topdispatchprice.setVisibility(View.VISIBLE);
		Tv_payprice.setVisibility(View.VISIBLE);
		Tv_toppayprice.setVisibility(View.VISIBLE);
	}
	
	private void hideView()
	{
		mainView.setVisibility(View.INVISIBLE);
		Rlyt_button.setVisibility(View.INVISIBLE);
		Tv_realname.setVisibility(View.INVISIBLE);
		Tv_mobile.setVisibility(View.INVISIBLE);
		Tv_address.setVisibility(View.INVISIBLE);
		
		Tv_statusname.setVisibility(View.INVISIBLE);
		Tv_ordersn.setVisibility(View.INVISIBLE);
		Tv_goodsprice.setVisibility(View.INVISIBLE);
		Tv_dispatchprice.setVisibility(View.INVISIBLE);
		Tv_topdispatchprice.setVisibility(View.INVISIBLE);
		Tv_payprice.setVisibility(View.INVISIBLE);
		Tv_toppayprice.setVisibility(View.INVISIBLE);
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
