package com.gcs.suban;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gcs.suban.activity.AddCommentActivity;
import com.gcs.suban.activity.AppendCommentActivity;
import com.gcs.suban.activity.LogisticsActivity;
import com.gcs.suban.activity.ReturnActivity;
import com.gcs.suban.bean.OrderBean;
import com.gcs.suban.eventbus.OrderEvent;
import com.gcs.suban.listener.OnOrderHelperListener;
import com.gcs.suban.model.OrderModel;
import com.gcs.suban.model.OrderModelImpl;
import com.gcs.suban.popwindows.PayPopWindow;
import com.gcs.suban.tools.ToastTool;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.rong.eventbus.EventBus;

public class OrderHelper implements OnOrderHelperListener {

	private OrderModel model;
	private Context context;
	private OrderBean bean;

	private SweetAlertDialog dialog;

	public OrderHelper(Context context) {
		model = new OrderModelImpl();
		this.context = context;
	}

	// ���ð�ť
	public void setButton(OrderBean bean, Button leftbutton, Button rightButton) {
		this.bean = bean;
		switch (bean.status) {
		case -1:
			rightButton.setVisibility(View.VISIBLE);
			leftbutton.setVisibility(View.INVISIBLE);
			rightButton.setText("ɾ������");
			break;
		case 0:
			rightButton.setVisibility(View.VISIBLE);
			leftbutton.setVisibility(View.VISIBLE);
			rightButton.setText("����");
			leftbutton.setText("ȡ������");
			break;
		case 1:
			if (bean.refundstate == 1 && bean.refundid > 0) {
				rightButton.setVisibility(View.VISIBLE);
				rightButton.setText("ȡ���˿�����");
				leftbutton.setVisibility(View.INVISIBLE);
			} else {
				rightButton.setVisibility(View.VISIBLE);
				leftbutton.setVisibility(View.INVISIBLE);
				rightButton.setText("�˿�");
				leftbutton.setText("��ϵ�̼�");
			}
			break;
		case 2:
			rightButton.setVisibility(View.VISIBLE);
			leftbutton.setVisibility(View.VISIBLE);
			rightButton.setText("ȷ���ջ�");
			leftbutton.setText("�鿴���� ");
			break;
		case 3:
			rightButton.setVisibility(View.VISIBLE);
			leftbutton.setVisibility(View.VISIBLE);
			if (bean.iscomment == 0) {
				rightButton.setText("��Ҫ����");
			} else if (bean.iscomment == 1) {
				rightButton.setText("��Ҫ׷��");
			} else if (bean.iscomment == 2) {
				rightButton.setText("������");
			}
			leftbutton.setText("ɾ������");
			break;
		case 4:
			rightButton.setVisibility(View.VISIBLE);
			rightButton.setText("ȡ���˿�����");
			leftbutton.setVisibility(View.INVISIBLE);
			break;
		default:
			rightButton.setVisibility(View.INVISIBLE);
			leftbutton.setVisibility(View.INVISIBLE);
			break;
		}
	}

	// ��ť
	public void leftClick(OrderBean bean) {
		this.bean = bean;
		switch (bean.status) {
		case 0:
			// ȡ������
			CancelOrder();
			break;
		case 1:
			// ��ϵ�̼�
			Call();
			break;
		case 2:
			// �鿴����
			Logistics();
			break;
		case 3:
			// ɾ������
			DeleteOrder();
			break;
		default:
			break;
		}
	}

	// �Ұ�ť
	public void rightClick(OrderBean bean) {
		this.bean = bean;
		switch (bean.status) {
		case -1:
			// ɾ������
			DeleteOrder();
			break;
		case 0:
			// ����
			Pay();
			break;
		case 1:
			if (bean.refundstate == 1 && bean.refundid > 0) {
				// ȡ���˿�����
				CancelRefund();
			} else {
				// �˻�
				Refund();
			}
			break;
		case 2:
			// ȷ���ջ�
			ConfirmOrder();
			break;
		case 3:
			// ����
			Comment();
			break;
		default:
			break;
		}
	}

	// ����
	public void Pay() {
		Intent intent = new Intent(context, PayPopWindow.class);
		Bundle bundle = new Bundle();
		bundle.putDouble("price", bean.price);
		bundle.putDouble("goodsprice", bean.goodsprice);
		bundle.putString("couponprice", bean.couponprice);
		bundle.putString("dispatchprice", bean.dispatchprice);
		bundle.putString("orderid", bean.orderid);
		bundle.putString("ordersn", bean.ordersn);
		bundle.putDouble("credit2", bean.credit2);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}

	// �鿴����
	public void Logistics() {
		Intent intent = new Intent(context, LogisticsActivity.class);
		intent.putExtra("orderid", bean.orderid);
		context.startActivity(intent);
	}

	// �������
	public void Comment() {
		if (bean.iscomment == 0) {
			Intent intent = new Intent(context, AddCommentActivity.class);
			intent.putExtra("orderid", bean.orderid);
			intent.putExtra("goodsid", bean.goodsid);
			context.startActivity(intent);
		} else if (bean.iscomment == 1) {
			Intent intent = new Intent(context, AppendCommentActivity.class);
			intent.putExtra("orderid", bean.orderid);
			intent.putExtra("goodsid", bean.goodsid);
			context.startActivity(intent);
		} else if (bean.iscomment == 2) {

		}
	}

	// �����˿��˻�����
	public void Refund() {
		Intent intent = new Intent(context, ReturnActivity.class);
		intent.putExtra("orderid", bean.orderid);
		context.startActivity(intent);
	}

	// ��ϵ�̼�
	public void Call() {
		Intent intent = new Intent(Intent.ACTION_DIAL);
		Uri data = Uri.parse("tel:" + bean.shopphone);
		intent.setData(data);
		context.startActivity(intent);
	}

	// ȷ���ջ�
	public void ConfirmOrder() {
		dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
				.setTitleText("ȷ���ջ�")
				.setContentText("�Ƿ�ȷ���ջ���")
				.setCancelText("ȡ��")
				.setConfirmText("ȷ��")
				.showCancelButton(true)
				.setConfirmClickListener(
						new SweetAlertDialog.OnSweetClickListener() {
							@Override
							public void onClick(SweetAlertDialog sDialog) {
								confirmorder();
							}
						})
				.setCancelClickListener(
						new SweetAlertDialog.OnSweetClickListener() {
							@Override
							public void onClick(SweetAlertDialog dialog) {
								dialog.cancel();
							}
						});
		dialog.show();

	}

	// ȡ������
	public void CancelOrder() {
		dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
				.setTitleText("ȡ������")
				.setContentText("ȷ��ȡ���ö�����")
				.setCancelText("ȡ��")
				.setConfirmText("ȷ��")
				.showCancelButton(true)
				.setConfirmClickListener(
						new SweetAlertDialog.OnSweetClickListener() {
							@Override
							public void onClick(SweetAlertDialog dialog) {
								cancelorder();
							}
						})
				.setCancelClickListener(
						new SweetAlertDialog.OnSweetClickListener() {
							@Override
							public void onClick(SweetAlertDialog dialog) {
								dialog.cancel();
							}
						});
		dialog.show();
	}

	// ɾ������
	public void DeleteOrder() {
		dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
				.setTitleText("ɾ������")
				.setContentText("ȷ��ɾ���ö�����")
				.setCancelText("ȡ��")
				.setConfirmText("ȷ��")
				.showCancelButton(true)
				.setConfirmClickListener(
						new SweetAlertDialog.OnSweetClickListener() {
							@Override
							public void onClick(SweetAlertDialog dialog) {
								deleteorder();
							}
						})
				.setCancelClickListener(
						new SweetAlertDialog.OnSweetClickListener() {
							@Override
							public void onClick(SweetAlertDialog dialog) {
								dialog.cancel();
							}
						});
		dialog.show();

	}

	// ȡ���˻����뵯��
	public void CancelRefund() {
		dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
				.setTitleText("ȡ���˻�����")
				.setContentText("ȷ��ȡ���˻����룿")
				.setCancelText("ȡ��")
				.setConfirmText("ȷ��")
				.showCancelButton(true)
				.setConfirmClickListener(
						new SweetAlertDialog.OnSweetClickListener() {
							@Override
							public void onClick(SweetAlertDialog dialog) {
								cancelrefund();
							}
						})
				.setCancelClickListener(
						new SweetAlertDialog.OnSweetClickListener() {
							@Override
							public void onClick(SweetAlertDialog dialog) {
								dialog.cancel();
							}
						});
		dialog.show();
	}

	/* ȷ�϶��� */
	private void confirmorder() {
		model.confirmOrder(Url.confirmtake, bean.orderid, this);
	}

	/* ȡ������ */
	private void cancelorder() {
		model.cancelOrder(Url.cancelorder, bean.orderid, this);
	}

	/* ɾ������ */
	private void deleteorder() {
		model.deleteOrder(Url.delorder, bean.orderid, this);
	}

	/* ȡ���˻����� */
	private void cancelrefund() {
		model.cancelRefund(Url.cancelrefund, bean.orderid, this);
	}

	@Override
	public void onConfirmOrder(String resulttext) {
		// TODO Auto-generated method stub
		dialog.setTitleText("ȷ���ջ�")
				.setContentText("ȷ���ջ��ɹ���")
				.setConfirmText("ȷ��")
				.showCancelButton(false)
				.setCancelClickListener(null)
				.setConfirmClickListener(
						new SweetAlertDialog.OnSweetClickListener() {
							@Override
							public void onClick(SweetAlertDialog dialog) {
								EventBus.getDefault().post(
										new OrderEvent("update","msg"));
								dialog.dismiss();
							}
						}).changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
	}

	@Override
	public void onCancelRefund(String resulttext) {
		// TODO Auto-generated method stub
		dialog.setTitleText("ȡ���˻�����")
				.setContentText("ȡ���˻�����ɹ���")
				.setConfirmText("ȷ��")
				.showCancelButton(false)
				.setCancelClickListener(null)
				.setConfirmClickListener(
						new SweetAlertDialog.OnSweetClickListener() {
							@Override
							public void onClick(SweetAlertDialog dialog) {
								EventBus.getDefault().post(
										new OrderEvent("update","msg"));
								dialog.dismiss();
							}
						}).changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
	}

	@Override
	public void onCancelOrder(String resulttext) {
		// TODO Auto-generated method stub
		dialog.setTitleText("ȡ������")
				.setContentText("ȡ�������ɹ���")
				.setConfirmText("ȷ��")
				.showCancelButton(false)
				.setCancelClickListener(null)
				.setConfirmClickListener(
						new SweetAlertDialog.OnSweetClickListener() {
							@Override
							public void onClick(SweetAlertDialog dialog) {
								EventBus.getDefault().post(
										new OrderEvent("update","msg"));
								dialog.dismiss();
							}
						}).changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
	}

	@Override
	public void onDeleteOrder(String resulttext) {
		// TODO Auto-generated method stub
		dialog.setTitleText("ɾ������")
				.setContentText("ɾ�������ɹ���")
				.setConfirmText("ȷ��")
				.showCancelButton(false)
				.setCancelClickListener(null)
				.setConfirmClickListener(
						new SweetAlertDialog.OnSweetClickListener() {
							@Override
							public void onClick(SweetAlertDialog dialog) {
								EventBus.getDefault().post(
										new OrderEvent("update","msg"));
								dialog.dismiss();
							}
						}).changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
	}

	/**
	 * �������� �������ʧ��
	 */
	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, error);
	}

}
