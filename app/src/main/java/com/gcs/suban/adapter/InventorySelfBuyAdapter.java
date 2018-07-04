package com.gcs.suban.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.activity.SelfLogisticsActivity;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.InventoryGoodsBean;
import com.gcs.suban.bean.InventorySelfBuyBean;
import com.gcs.suban.eventbus.InventoryEvent;
import com.gcs.suban.listener.OnBaseListener;
import com.gcs.suban.listener.OnSelfListener;
import com.gcs.suban.model.InventorySelfBuyModel;
import com.gcs.suban.model.InventorySelfBuyModelimpl;
import com.gcs.suban.popwindows.PayStockPopWindow;
import com.gcs.suban.tools.ToastTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.rong.eventbus.EventBus;

public class InventorySelfBuyAdapter extends BaseListAdapter<InventorySelfBuyBean> implements OnSelfListener {

    protected InventoryGoodsAdapter adapter;
    protected List<InventoryGoodsBean> mListType;
    protected InventorySelfBuyModel model = new InventorySelfBuyModelimpl();
    protected SweetAlertDialog dialog;
    protected int payType = 0;

    @Override
    public void onPaySuccess(String msg) {
        dialog.setTitleText("֧������")
                .setContentText("֧�������ɹ�")
                .setConfirmText("ȷ��")
                .showCancelButton(false)
                .setCancelClickListener(null)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        EventBus.getDefault().post(new InventoryEvent("update","msg"));
                        dialog.dismiss();
                    }
                }).changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
    }

    @Override
    public void onFinfishSuccess(String msg) {
        dialog.setTitleText("ȷ���ջ�")
                .setContentText("ȷ���ջ��ɹ�")
                .setConfirmText("ȷ��")
                .showCancelButton(false)
                .setCancelClickListener(null)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        EventBus.getDefault().post(new InventoryEvent("update","msg"));
                        dialog.dismiss();
                    }
                }).changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
    }

    @Override
    public void onError(String error) {
        ToastTool.showToast(context, error);
    }

    @Override
    public void onCancelSuccess(String msg) {
        dialog.setTitleText("ȡ������")
                .setContentText("ȡ������ɹ�")
                .setConfirmText("ȷ��")
                .showCancelButton(false)
                .setCancelClickListener(null)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        EventBus.getDefault().post(new InventoryEvent("update","msg"));
                        dialog.dismiss();
                    }
                }).changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
    }

    protected class ViewHolder{
        public TextView Tv_time;
        public TextView Tv_status_str;
        public TextView Tv_express_price;
        public TextView Tv_paymoney;
        public Button Btn_left;
        public Button Btn_right;
        public ListView listView;

    }

    public InventorySelfBuyAdapter(Context context, List<InventorySelfBuyBean> list) {
        super(context, list);
        InitImageLoader();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("selfbuy", "getView" + position);
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item_selfbuy, null);
            viewHolder.Tv_time = (TextView)convertView.findViewById(R.id.tv_time);
            viewHolder.Tv_status_str = (TextView)convertView.findViewById(R.id.tv_status_str);
            viewHolder.Tv_express_price = (TextView)convertView.findViewById(R.id.tv_express_price);
            viewHolder.Tv_paymoney = (TextView)convertView.findViewById(R.id.tv_paymoney);
            viewHolder.Btn_left = (Button)convertView.findViewById(R.id.btn_left);
            viewHolder.Btn_right = (Button)convertView.findViewById(R.id.btn_right);
            viewHolder.listView = (ListView)convertView.findViewById(R.id.listview_goods);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
            convertView.setTag(viewHolder);
        }

        final InventorySelfBuyBean selfBuyBean = listItems.get(position);
        viewHolder.Tv_time.setText(selfBuyBean.create_time);
        viewHolder.Tv_status_str.setText(selfBuyBean.status_str);
        viewHolder.Tv_paymoney.setText(String.valueOf(selfBuyBean.price));
        mListType = new ArrayList<InventoryGoodsBean>();
        if(!selfBuyBean.goods_list.isEmpty()){
            try {
                JSONArray jsonArray = new JSONArray(selfBuyBean.goods_list);
                for (int i = 0; i < jsonArray.length(); i ++){
                    JSONObject jsonObject = (JSONObject)jsonArray.opt(i);
                    InventoryGoodsBean goodsBean = new InventoryGoodsBean();
                    goodsBean.thumb = jsonObject.getString("thumb");
                    goodsBean.num = jsonObject.getString("num");
                    goodsBean.goodsid = jsonObject.getInt("goodsid");
                    goodsBean.money = jsonObject.getString("price");
                    goodsBean.title = jsonObject.getString("title");
                    mListType.add(goodsBean);
                }
                adapter = new InventoryGoodsAdapter(context,mListType);
                viewHolder.listView.setAdapter(adapter);
                setListViewHeightBasedOnChildren(viewHolder.listView);
                viewHolder.listView.setFocusable(false);
                viewHolder.listView.setClickable(false);
                viewHolder.listView.setEnabled(false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        viewHolder.Tv_express_price.setText(String.valueOf(selfBuyBean.freight));
        //viewHolder.Tv_paymoney.setText(String.valueOf(selfBuyBean.freight));

        viewHolder.Btn_right.setTag(position);
        viewHolder.Btn_left.setTag(position);

        if(selfBuyBean.status == 0){
            viewHolder.Btn_left.setText("ȡ������");
            viewHolder.Btn_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelSelf(selfBuyBean);
                }
            });

            viewHolder.Btn_right.setText("����");
            viewHolder.Btn_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pay(selfBuyBean);
                }
            });
        }else if(selfBuyBean.status == 2){
            viewHolder.Btn_left.setText("�鿴����");
            viewHolder.Btn_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getExpress(selfBuyBean.id);
                }
            });

            viewHolder.Btn_right.setText("ȷ���ջ�");
            viewHolder.Btn_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmOrder(selfBuyBean);
                }
            });
        }else{
            viewHolder.Btn_left.setVisibility(View.GONE);
            viewHolder.Btn_right.setVisibility(View.GONE);
        }

        return convertView;
    }

    /* ��ListView�߶����� */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // ��ȡListView��Ӧ��Adapter
        InventoryGoodsAdapter listAdapter = (InventoryGoodsAdapter) listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()�������������Ŀ
            View listItem = listAdapter.getView(i, null, listView);
            // ��������View �Ŀ��
            listItem.measure(0, 0);
            // ͳ������������ܸ߶�
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()��ȡ�����ָ���ռ�õĸ߶�
        // params.height���õ�����ListView������ʾ��Ҫ�ĸ߶�
        listView.setLayoutParams(params);
    }

    protected void cancelSelf(final InventorySelfBuyBean bean){
        dialog = new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE)
                .setTitleText("ȡ������")
                .setContentText("ȷ��ȡ������")
                .setCancelText("ȡ��")
                .setConfirmText("ȷ��")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        cancelSelfBuy(bean.id);
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        dialog.cancel();
                    }
                });
        dialog.show();
    }

    protected void cancelSelfBuy(int id){
        model.cancelSelf(Url.cancelself,id,this);
    }

    protected void pay(final InventorySelfBuyBean bean){
        dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("����")
                .setContentText("ȷ��֧��?")
                .setConfirmText("ȷ��")
                .showCancelButton(true)
                .setCancelText("ȡ��")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if(bean.ispay == 1){
                            dialog.dismiss();
                            Intent intent = new Intent(context, PayStockPopWindow.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("orderid", bean.id);
                            bundle.putString("ordersn", bean.ordersn);
                            bundle.putString("dispatchprice", String.valueOf(bean.freight));
                            bundle.putDouble("price", bean.price);
                            bundle.putInt("freightmodal",0);
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }else{
                            if(bean.price > 0){
                                dialog.setTitleText("֧��")
                                        .setContentText("֧�����Ƚϴ���������ϵ�ͷ�֧��")
                                        .setConfirmText("ȷ��")
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                dialog.dismiss();
                                            }
                                        }).changeAlertType(SweetAlertDialog.ERROR_TYPE);

                            }else{
                                payment(bean.id);
                            }
                        }
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        dialog.cancel();
                    }
                });
        dialog.show();
    }

    protected void payment(int id){
        model.paySelf(Url.payself,String.valueOf(id),this);
    }

    protected void confirmOrder(final InventorySelfBuyBean bean){
        dialog = new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE)
                .setTitleText("ȷ���ջ�")
                .setContentText("�Ƿ�ȷ���ջ�")
                .setConfirmText("ȷ��")
                .setCancelText("ȡ��")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        finishOrder(bean.id);
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        dialog.cancel();
                    }
                });
        dialog.show();
    }

    protected void getExpress(int id){
        Intent intent = new Intent(context, SelfLogisticsActivity.class);
        intent.putExtra("orderid", String.valueOf(id));
        context.startActivity(intent);
    }

    protected void finishOrder(int id){
        model.finishSelf(Url.finish_self,String.valueOf(id),this);
    }

}
