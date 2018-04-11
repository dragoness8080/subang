package com.gcs.suban.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.ShoppingCanst;
import com.gcs.suban.Url;
import com.gcs.suban.adapter.ShopAdapter;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.ShopCarBean;
import com.gcs.suban.eventbus.CarEvent;
import com.gcs.suban.listener.OnShopCarListener;
import com.gcs.suban.model.ShopCarModel;
import com.gcs.suban.model.ShopCarMoelImpl;
import com.gcs.suban.tools.ToastTool;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;


/**
 * ���ﳵ
 */
public class CarActivity extends BaseActivity implements OnItemClickListener,
		OnShopCarListener {
	private TextView Tv_title;
	private TextView Tv_allprice;

	private Button Btn_confirm;
	private Button Btn_delete;
	private Button Btn_checkout;

	private CheckBox checkBox;

	private ImageButton IBtn_back;
	
	protected LinearLayout layout;
	protected LinearLayout Llyt_checkout;

	private ListView listView;
	private ShopAdapter adapter; // �Զ���������
	private List<ShopCarBean> mListType = new ArrayList<ShopCarBean>(); // ���ﳵ���ݼ���
	private ShopCarModel model;

	private boolean flag = true; // ȫѡ��ȫȡ��
	
	private String cartid;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_confirm:
			context.finish();
			break;
		case R.id.back:
			finish();
			break;
		case R.id.check_all:
			selectedAll();
			break;
		case R.id.btn_checkOut:
			cartid = deleteOrCheckOutShop();
			if(!cartid.equals("-1"))
			{
			Log.i(TAG, cartid);
			Intent intent = new Intent(context, CarConfirmActivity.class);
			intent.putExtra("cartid", cartid);
			startActivity(intent);
			finish();
			}
			else {
				ToastTool.showToast(context, "��ѡ����Ʒ");
			}
			break;
		case R.id.btn_car_delete:
			cartid = deleteOrCheckOutShop();
			if(!cartid.equals("-1"))
			{
			model.delete(Url.cardel, cartid, this);
			}
			else {
				ToastTool.showToast(context, "��ѡ����Ʒ");
			}
			break;
		default:
			break;
		}
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 10) { // ����ѡ����Ʒ���ܼ۸�
				double price = (double) msg.obj;
				if (price >= 0) {
					Tv_allprice.setText("��" + price);
				} else {
				}
			} else if (msg.what == 11) {
				// �����б��е���Ʒȫ����ѡ�У���ȫѡ��ťҲ��ѡ��
				// flag��¼�Ƿ�ȫ��ѡ��
				flag = !(Boolean) msg.obj;
				checkBox.setChecked((Boolean) msg.obj);
			} else if (msg.what == 12) {
				editNum(msg);
			}
		}
	};

	@Override
	protected void init() {
		ShoppingCanst.list = new ArrayList<ShopCarBean>();
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_car);
		Tv_title = (TextView) findViewById(R.id.title);
		Tv_title.setText("���ﳵ");
		Tv_allprice = (TextView) findViewById(R.id.tv_car_allprice);
		
		Btn_confirm= (Button) context.findViewById(R.id.layout_car)
				.findViewById(R.id.btn_confirm);
		Btn_confirm.setOnClickListener(this);
		Btn_delete = (Button) context.findViewById(R.id.btn_car_delete);
		Btn_delete.setOnClickListener(this);
		Btn_checkout = (Button) context.findViewById(R.id.btn_checkOut);
		Btn_checkout.setOnClickListener(this);

		checkBox = (CheckBox) context.findViewById(R.id.check_all);
		checkBox.setOnClickListener(this);

		IBtn_back = (ImageButton) context.findViewById(R.id.back);
		IBtn_back.setOnClickListener(this);

		listView = (ListView) findViewById(R.id.list_car);
		adapter = new ShopAdapter(this, mListType, handler);
		listView.setAdapter(adapter);
		
		layout = (LinearLayout) context.findViewById(R.id.layout_null_car);
		
		Llyt_checkout= (LinearLayout) context.findViewById(R.id.llyt_checkout);
		
		model = new ShopCarMoelImpl();
		model.getInfo(Url.shopcar, this);
	}

	/* ȫѡ��ȫȡ�� */
	private void selectedAll() {
		for (int i = 0; i < mListType.size(); i++) {
			ShopAdapter.getIsSelected().put(i, flag);
		}
		if (mListType.size() != 0) {
			adapter.notifyDataSetChanged();
		}
	}

	/*ɾ���������Ʒ*/
	private String deleteOrCheckOutShop() {
		StringBuffer sb = new StringBuffer();
		int cartid = -1;
		for (int i = 0; i < mListType.size(); i++) {
			if (ShopAdapter.getIsSelected().get(i)) {
				cartid = mListType.get(i).getCartid();
				sb.append(cartid);
				sb.append(",");
			}
		}
		if (cartid != -1) {
			sb.deleteCharAt(sb.length() - 1);
		} else {
			return cartid+"";
		}
		return sb.toString();
	}

	/* �޸Ĺ��ﳵ��Ʒ���� */
	private void editNum(Message msg) {
		Bundle bundle = (Bundle) msg.obj;
		String cartid = (String) bundle.get("cartid");
		String total = (String) bundle.get("total");
		if (!total.equals("0")) {
			model.setNum(Url.carnummod, cartid, total, this);
		}
	}

	/* ���������� */
	private void setData(List<ShopCarBean> ListType) {
		adapter.clear();
		mListType.clear();
		
		ShoppingCanst.list = ListType;
		mListType.addAll(ListType);
		adapter.initDate();
		adapter.notifyDataSetChanged();
	}

	/**
	 * �������� ������سɹ�
	 */
	@Override
	public void onSuccess(List<ShopCarBean> ListType) {
		// TODO Auto-generated method stub
		layout.setVisibility(View.GONE);
		if (ListType != null) {
			Btn_delete.setVisibility(View.VISIBLE);
			Llyt_checkout.setVisibility(View.VISIBLE);
			setData(ListType);
		} else {
			adapter.clear();
			mListType.clear();
			adapter.notifyDataSetChanged();
			layout.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * �ı����� �ɹ�
	 */
	@Override
	public void onNum() {
		// TODO Auto-generated method stub
		model.getInfo(Url.shopcar, this);
	}

	/**
	 * ɾ����Ʒ �ɹ�
	 */
	@Override
	public void onDelete(String resulttext) {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, resulttext);
		model.getInfo(Url.shopcar, this);
		EventBus.getDefault().post(new CarEvent("type","msg"));
	}

	/**
	 * �������� �������ʧ��
	 */
	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, error);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		String goods_id = (String) view.getTag(R.id.tag_first);
		Intent intent = new Intent(context, ShopDetailActivity.class);
		intent.putExtra("goods_id", goods_id);
		startActivity(intent);
	}

}