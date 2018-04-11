package com.gcs.suban.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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


public class AddressSelectActivity extends BaseActivity implements
		OnAddressListener, OnItemClickListener {
	private TextView Tv_title;
	private ImageButton IBtn_back;
	private RelativeLayout Rlyt_add;

	private ListView listView;
	private AddressAdapter adapter; // �Զ���������
	private List<AddressBean> mListType = new ArrayList<AddressBean>();
	private AddressModel addressModel;

	private String id;

	SweetAlertDialog dialog = null;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.rlty_add:
			Intent intent = new Intent(context, AddressAddActivity.class);
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
		Tv_title.setText("�ջ���ַѡ��");
		IBtn_back = (ImageButton) context.findViewById(R.id.back);
		Rlyt_add = (RelativeLayout) context.findViewById(R.id.rlty_add);
		IBtn_back.setOnClickListener(this);
		Rlyt_add.setOnClickListener(this);

		listView = (ListView) findViewById(R.id.list_address);
		listView.setOnItemClickListener(this);
		
		adapter = new AddressAdapter(this, mListType);
		listView.setAdapter(adapter);
		addressModel = new AddressModelImpl();
		addressModel.getInfo(Url.address, this);

	}


	/**
	 * ����������
	 */
	private void setData(List<AddressBean> ListType) {
		mListType.clear();
		mListType.addAll(ListType);
		adapter.notifyDataSetChanged();
	}

	/**
	 * ��ȡ��ַ�б����� ������سɹ�
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
	 * ɾ����ַ���� ������سɹ�
	 */
	@Override
	public void onDelete() {
		// TODO Auto-generated method stub
		dialog.setTitleText("ɾ����ַ").setContentText("ɾ����ַ�ɹ���")
				.setConfirmText("ȷ��").showCancelButton(false)
				.setCancelClickListener(null).setConfirmClickListener(null)
				.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
		addressModel.getInfo(Url.address, this);
	}

	/**
	 * Ĭ�ϵ�ַ�޸����� ������سɹ�
	 */
	@Override
	public void onDefault() {
		// TODO Auto-generated method stub
		addressModel.getInfo(Url.address, this);
	}

	/**
	 * �������� �������ʧ��
	 */
	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		if (dialog != null) {
			dialog.dismiss();
		}
		ToastTool.showToast(context, error);
	}

	/**
	 * �㲥�¼�
	 */
	public void onEventMainThread(AddressEvent event) {
		if (event.getType().equals("delete")) {
			id = event.getMsg();
			deletedialog();
		} else if (event.getType().equals("default")) {
			id = event.getMsg();
			setDefault();
		} else if (event.getType().equals("updata")) {
			addressModel.getInfo(Url.address, this);
		}
	}

	/**
	 * ɾ����ʾ��
	 */
	public void deletedialog() {
		dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
				.setTitleText("ɾ����ַ")
				.setContentText("ȷ��ɾ�����ջ���ַ��")
				.setCancelText("ȡ��")
				.setConfirmText("ȷ��")
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
	 * ɾ����ַ
	 */
	public void delete() {
		addressModel.delete(Url.deleteaddress, id, this);
	}

	/**
	 * ����Ĭ�ϵ�ַ
	 */
	public void setDefault() {
		addressModel.setDefault(Url.defaultaddress, id, this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		String addressid = mListType.get(position).addressid;
		String realname = mListType.get(position).realname;
		String mobile = mListType.get(position).mobile;
		String address = mListType.get(position).province + " "
				+ mListType.get(position).city + " "
				+ mListType.get(position).area + " "
				+ mListType.get(position).address;
		Intent intent = new Intent();
		intent.putExtra("addressid", addressid);
		intent.putExtra("realname", realname);
		intent.putExtra("mobile", mobile);
		intent.putExtra("address", address);
		context.setResult(RESULT_OK, intent);
		finish();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

}
