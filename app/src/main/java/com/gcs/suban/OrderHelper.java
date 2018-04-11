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

	// 设置按钮
	public void setButton(OrderBean bean, Button leftbutton, Button rightButton) {
		this.bean = bean;
		switch (bean.status) {
		case -1:
			rightButton.setVisibility(View.VISIBLE);
			leftbutton.setVisibility(View.INVISIBLE);
			rightButton.setText("删除订单");
			break;
		case 0:
			rightButton.setVisibility(View.VISIBLE);
			leftbutton.setVisibility(View.VISIBLE);
			rightButton.setText("付款");
			leftbutton.setText("取消订单");
			break;
		case 1:
			if (bean.refundstate == 1 && bean.refundid > 0) {
				rightButton.setVisibility(View.VISIBLE);
				rightButton.setText("取消退款申请");
				leftbutton.setVisibility(View.INVISIBLE);
			} else {
				rightButton.setVisibility(View.VISIBLE);
				leftbutton.setVisibility(View.INVISIBLE);
				rightButton.setText("退款");
				leftbutton.setText("联系商家");
			}
			break;
		case 2:
			rightButton.setVisibility(View.VISIBLE);
			leftbutton.setVisibility(View.VISIBLE);
			rightButton.setText("确认收货");
			leftbutton.setText("查看物流 ");
			break;
		case 3:
			rightButton.setVisibility(View.VISIBLE);
			leftbutton.setVisibility(View.VISIBLE);
			if (bean.iscomment == 0) {
				rightButton.setText("我要评价");
			} else if (bean.iscomment == 1) {
				rightButton.setText("我要追评");
			} else if (bean.iscomment == 2) {
				rightButton.setText("已评价");
			}
			leftbutton.setText("删除订单");
			break;
		case 4:
			rightButton.setVisibility(View.VISIBLE);
			rightButton.setText("取消退款申请");
			leftbutton.setVisibility(View.INVISIBLE);
			break;
		default:
			rightButton.setVisibility(View.INVISIBLE);
			leftbutton.setVisibility(View.INVISIBLE);
			break;
		}
	}

	// 左按钮
	public void leftClick(OrderBean bean) {
		this.bean = bean;
		switch (bean.status) {
		case 0:
			// 取消订单
			CancelOrder();
			break;
		case 1:
			// 联系商家
			Call();
			break;
		case 2:
			// 查看物流
			Logistics();
			break;
		case 3:
			// 删除订单
			DeleteOrder();
			break;
		default:
			break;
		}
	}

	// 右按钮
	public void rightClick(OrderBean bean) {
		this.bean = bean;
		switch (bean.status) {
		case -1:
			// 删除订单
			DeleteOrder();
			break;
		case 0:
			// 付款
			Pay();
			break;
		case 1:
			if (bean.refundstate == 1 && bean.refundid > 0) {
				// 取消退款申请
				CancelRefund();
			} else {
				// 退货
				Refund();
			}
			break;
		case 2:
			// 确认收货
			ConfirmOrder();
			break;
		case 3:
			// 评价
			Comment();
			break;
		default:
			break;
		}
	}

	// 付款
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

	// 查看物流
	public void Logistics() {
		Intent intent = new Intent(context, LogisticsActivity.class);
		intent.putExtra("orderid", bean.orderid);
		context.startActivity(intent);
	}

	// 添加评论
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

	// 申请退款退货弹窗
	public void Refund() {
		Intent intent = new Intent(context, ReturnActivity.class);
		intent.putExtra("orderid", bean.orderid);
		context.startActivity(intent);
	}

	// 联系商家
	public void Call() {
		Intent intent = new Intent(Intent.ACTION_DIAL);
		Uri data = Uri.parse("tel:" + bean.shopphone);
		intent.setData(data);
		context.startActivity(intent);
	}

	// 确认收货
	public void ConfirmOrder() {
		dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
				.setTitleText("确认收货")
				.setContentText("是否确认收货？")
				.setCancelText("取消")
				.setConfirmText("确定")
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

	// 取消订单
	public void CancelOrder() {
		dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
				.setTitleText("取消订单")
				.setContentText("确定取消该订单？")
				.setCancelText("取消")
				.setConfirmText("确定")
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

	// 删除订单
	public void DeleteOrder() {
		dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
				.setTitleText("删除订单")
				.setContentText("确定删除该订单？")
				.setCancelText("取消")
				.setConfirmText("确定")
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

	// 取消退货申请弹窗
	public void CancelRefund() {
		dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
				.setTitleText("取消退货申请")
				.setContentText("确认取消退货申请？")
				.setCancelText("取消")
				.setConfirmText("确定")
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

	/* 确认订单 */
	private void confirmorder() {
		model.confirmOrder(Url.confirmtake, bean.orderid, this);
	}

	/* 取消订单 */
	private void cancelorder() {
		model.cancelOrder(Url.cancelorder, bean.orderid, this);
	}

	/* 删除订单 */
	private void deleteorder() {
		model.deleteOrder(Url.delorder, bean.orderid, this);
	}

	/* 取消退货申请 */
	private void cancelrefund() {
		model.cancelRefund(Url.cancelrefund, bean.orderid, this);
	}

	@Override
	public void onConfirmOrder(String resulttext) {
		// TODO Auto-generated method stub
		dialog.setTitleText("确认收货")
				.setContentText("确认收货成功！")
				.setConfirmText("确定")
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
		dialog.setTitleText("取消退货申请")
				.setContentText("取消退货申请成功！")
				.setConfirmText("确定")
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
		dialog.setTitleText("取消订单")
				.setContentText("取消订单成功！")
				.setConfirmText("确定")
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
		dialog.setTitleText("删除订单")
				.setContentText("删除订单成功！")
				.setConfirmText("确定")
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
	 * 网络请求 结果返回失败
	 */
	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		ToastTool.showToast(context, error);
	}

}
