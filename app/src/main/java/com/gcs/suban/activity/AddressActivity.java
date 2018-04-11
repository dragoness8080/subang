package com.gcs.suban.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.adapter.AddressAdapter;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.AddressBean;
import com.gcs.suban.eventbus.AddressEvent;
import com.gcs.suban.listener.OnAddressListener;
import com.gcs.suban.model.AddressModel;
import com.gcs.suban.model.AddressModelImpl;
import com.gcs.suban.tools.ToastTool;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.rong.eventbus.EventBus;

/**
 * 收货地址列表
 */
public class AddressActivity extends BaseActivity implements OnAddressListener {
	private TextView Tv_title;
	private ImageButton IBtn_back;
	private RelativeLayout Rlyt_add;
 
	private ListView listView;
	private AddressAdapter adapter; // 自定义适配器
	private List<AddressBean> mListType = new ArrayList<AddressBean>(); 
	private AddressModel addressModel;

	private String url
	;
	private String id;

	SweetAlertDialog dialog=null;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.rlty_add:
			Intent intent=new Intent(context,AddressAddActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		EventBus.getDefault().register(this);
		setContentView(R.layout.activity_address);
		Tv_title = (TextView) findViewById(R.id.title);
		Tv_title.setText("收货地址管理");
		IBtn_back = (ImageButton) context.findViewById(R.id.back);
		Rlyt_add=(RelativeLayout)context.findViewById(R.id.rlty_add);
		IBtn_back.setOnClickListener(this);
		Rlyt_add.setOnClickListener(this);

		listView = (ListView) findViewById(R.id.list_address);
		adapter = new AddressAdapter(this, mListType);
		listView.setAdapter(adapter);
		addressModel = new AddressModelImpl();
		addressModel.getInfo(getUrl(), this);
		
	}

	/**
	 * 获取url地址
	 */
	private String getUrl() {
		url = Url.address;
		return url;
	}

	/**
	 * 更新适配器
	 */
	private void setData(List<AddressBean> ListType) {
		mListType.clear();
		mListType.addAll(ListType);
		adapter.notifyDataSetChanged();
	}

	/**
	 * 获取地址列表请求 结果返回成功
	 */
	@Override
	public void onSuccess(List<AddressBean> ListType) {
		// TODO Auto-generated method stub
		if (ListType != null) {
			setData(ListType);
		} else {
			mListType.clear();
			adapter.notifyDataSetChanged();
		}
	}

	/**
	 * 删除地址请求 结果返回成功
	 */
	@Override
	public void onDelete() {
		// TODO Auto-generated method stub
		dialog.setTitleText("删除地址").setContentText("删除地址成功！")
		.setConfirmText("确定").showCancelButton(false)
		.setCancelClickListener(null).setConfirmClickListener(null)
		.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
		addressModel.getInfo(getUrl(), this);
	}

	/**
	 * 默认地址修改请求 结果返回成功
	 */
	@Override
	public void onDefault() {
		// TODO Auto-generated method stub
		addressModel.getInfo(getUrl(), this);
	}

	/**
	 * 网络请求 结果返回失败
	 */
	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		if(dialog!=null)
		{
			dialog.dismiss();
		}
		ToastTool.showToast(context, error);
	}

	/**
	 * 删除提示框
	 */
	public void deletedialog() {
		dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
				.setTitleText("删除地址")
				.setContentText("确定删除该收货地址？")
				.setCancelText("取消")
				.setConfirmText("确定")
				.showCancelButton(true)
				.setConfirmClickListener(
						new SweetAlertDialog.OnSweetClickListener() {
							@Override
							public void onClick(SweetAlertDialog sDialog) {
								delete();
							}
						})
				.setCancelClickListener(
						new SweetAlertDialog.OnSweetClickListener() {
							@Override
							public void onClick(SweetAlertDialog sDialog) {
								sDialog.cancel();
							}
							
						});
		dialog.show();
	}
	
	/**
	 * 删除地址
	 */
	public void delete(){
		addressModel.delete(Url.deleteaddress, id, this);
	}

	
	/**
	 * 设置默认地址
	 */
	public void setDefault() {
		addressModel.setDefault(Url.defaultaddress, id, this);
	}
	
	/**
	 * 广播事件
	 */
	public void onEventMainThread(AddressEvent event) {
		if(event.getType().equals("delete"))
		{
			id=event.getMsg();
			Log.e("id","id"+ id);
			deletedialog();
		}
		else if(event.getType().equals("default"))
		{
			id=event.getMsg();
			setDefault();
		}
		else if(event.getType().equals("updata"))
		{
			addressModel.getInfo(getUrl(), this);
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

}
