package com.gcs.suban.popwindows;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gcs.suban.AttrCanst;
import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.activity.OrderConfirmActivity;
import com.gcs.suban.adapter.AttrsSelectAdapter;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.AttrsBean;
import com.gcs.suban.bean.AttrsChildbean;
import com.gcs.suban.bean.ShopDataBean;
import com.gcs.suban.eventbus.CarEvent;
import com.gcs.suban.listener.OnAttrListener;
import com.gcs.suban.model.AttrModel;
import com.gcs.suban.model.AttrModelImpl;
import com.gcs.suban.tools.ToastTool;

import java.util.List;

import io.rong.eventbus.EventBus;


/**
 * 描述：商品属性选择页面
 */
public class attrSelectPopWindow extends BaseActivity implements
		OnClickListener, OnAttrListener {

	private ImageView Img_pic;

	private ListView listView;
	
	private LinearLayout Llyt_pop;

	private TextView Tv_price;
	private TextView Tv_stock;
	private TextView Tv_attr;
	private TextView Tv_num;

	private ImageButton Ibtn_plus;
	private ImageButton Ibtn_minus;

	private Button Btn_confirm;
	private Button Btn_cancel;

	private String goodsid;
	private String optionid = "0";
	private String price;// 当前价格
	private String thumb;// 当前价格
	private int stock;// 库存
	private int num = 1;// 当前数量

	private AttrsSelectAdapter adapter;

	private AttrModel model;

	private AttrsChildbean attrsChildbean;

	private ShopDataBean shopDataBean;

	private List<ShopDataBean> vListType;

	private int oNumber = 0; // 当前执行的行
	private int sumNum; // 总共的属性数量

	private String txt; // 当前执行行 所选商品属性 名称

	private String[] attrs; // 当前执行行 所选商品属性总属性

	private String attr = "";

	private int type;// 1为购物车 2为购买
	private String isspec;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_cancel:
			finish();
			break;
		case R.id.btn_confirm:
			if (allSelect()) {
				shopDataBean.setGoodsid(goodsid);
				shopDataBean.setTotal(num);
				shopDataBean.setOptionid(optionid);
				if (type == 1) {
					model.addCar(Url.addcar, shopDataBean, this);
				} else {
					Intent intent = new Intent(context,
							OrderConfirmActivity.class);
					intent.putExtra("goodsid", goodsid);
					intent.putExtra("optionid", optionid);
					intent.putExtra("num", num + "");
					startActivity(intent);
				}
				finish();
			} else {
				ToastTool.showToast(context, "请选择所有商品属性");
			}
			break;
		case R.id.icon_plus:
			if (num == stock) {
				ToastTool.showToast(context, "购买数量不得大于库存量");
			} else {
				num = num + 1;
				Tv_num.setText(num + "");
			}
			break;
		case R.id.icon_minus:
			if (num == 1) {
				ToastTool.showToast(context, "购买数量不得小于1");
			} else {
				num = num - 1;
				Tv_num.setText(num + "");
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void init() {
		InitImageLoader();
		setContentView(R.layout.popwindow_attr);
		SetWindow();
		Intent intent = getIntent();
		goodsid = intent.getStringExtra("goodsid");
		isspec = intent.getStringExtra("isspec");
		type = intent.getIntExtra("type", 0);
		// TODO Auto-generated method stub
		Llyt_pop= (LinearLayout) findViewById(R.id.llyt_pop);
		Llyt_pop.setVisibility(View.GONE);
		Img_pic = (ImageView) findViewById(R.id.img_logo);
		listView = (ListView) findViewById(R.id.list_attr);
		Btn_confirm = (Button) findViewById(R.id.btn_confirm);
		Btn_cancel = (Button) findViewById(R.id.btn_cancel);
		Ibtn_plus = (ImageButton) findViewById(R.id.icon_plus);
		Ibtn_minus = (ImageButton) findViewById(R.id.icon_minus);
		Tv_price = (TextView) findViewById(R.id.tv_price);
		Tv_attr = (TextView) findViewById(R.id.tv_attr);
		Tv_stock = (TextView) findViewById(R.id.tv_stock);
		Tv_num = (TextView) findViewById(R.id.tv_num);

		Ibtn_plus.setOnClickListener(this);
		Ibtn_minus.setOnClickListener(this);
		Btn_confirm.setOnClickListener(this);
		Btn_cancel.setOnClickListener(this);

		model = new AttrModelImpl();
		model.getInfo(Url.goodsspec + "?goodsid=" + goodsid, this);
		model.getValue(Url.goodsprice + "?goodsid=" + goodsid, this);

		attrsChildbean = new AttrsChildbean();
		shopDataBean = new ShopDataBean();

		Tv_stock.setVisibility(View.GONE);
	}

	/**
	 * 商品规格信息获取成功
	 */
	@Override
	public void onSuccess(List<AttrsBean> mListType, ShopDataBean bean) {
		if(mListType!=null)
		{
		attrs = new String[mListType.size()];
		listView.setVisibility(View.VISIBLE);
		}
		else {
			listView.setVisibility(View.GONE);
		}
		stock = bean.total;
		price = bean.marketprice;
		thumb = bean.thumb;
		Tv_stock.setText("库存：" + stock);
		Tv_price.setText(price + "元");
		imageLoader.displayImage(thumb, Img_pic, options);
		if (isspec.equals("1")) {
			adapter = new AttrsSelectAdapter(context, mListType, handler);
			listView.setAdapter(adapter);
			sumNum = mListType.size();
		} else {
			Tv_attr.setVisibility(View.GONE);
		}
		Llyt_pop.setVisibility(View.VISIBLE);
	}

	/**
	 * 商品规格参数对应价目
	 */
	@Override
	public void onValue(List<ShopDataBean> mListType) {
		// TODO Auto-generated method stub
		vListType = mListType;
	}

	/**
	 * 加入购物车成功
	 */
	@Override
	public void onAddCar() {
		// TODO Auto-generated method stub
		finish();
		ToastTool.showToast(context, "已添加进购物车");
		EventBus.getDefault().post(new CarEvent("type","msg"));
	}

	/**
	 * 网络请求失败
	 */
	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		//ToastTool.showToast(context, error);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			oNumber = msg.what;
			attrsChildbean = (AttrsChildbean) msg.obj;
			txt = attrsChildbean.title;
			attrs[oNumber] = txt;
			Log.e(TAG, oNumber + " " + txt);
			setAttr();
		}
	};

	private void setAttr() {
		attr = "";
		for (int i = 0; i < sumNum; i++) {
			for (int j = 0; j < 10; j++) {
				if (AttrCanst.attrs[i][j].is == true) {
					attr = attr + "+" + AttrCanst.attrs[i][j].title;
				}
			}
		}
		if (attr.length() >= 1) {
			attr = attr.substring(1);
		} else {
			attr = "请选择商品属性";
		}
		Tv_attr.setText(attr);

		getValue();
	}

	// 判断属性是否全都选择
	private boolean allSelect() {
		int nowNum = 0;
		for (int i = 0; i < sumNum; i++) {
			for (int j = 0; j < 10; j++) {
				if (AttrCanst.attrs[i][j].is == true) {
					nowNum++;
				}
			}
		}
		if (nowNum == sumNum) {
			return true;
		} else {
			return false;
		}
	}

	// 判断选择商品属性的对应价格
	private void getValue() {
		int num = vListType.size();
		String title;
		optionid = "0";
		for (int i = 0; i < num; i++) {
			title = vListType.get(i).title;
			if (title.equals(Tv_attr.getText().toString())) {
				optionid = vListType.get(i).optionid;
				Tv_price.setText(vListType.get(i).marketprice);
			}
		}
		Log.e(TAG, optionid);
	}
	

}
