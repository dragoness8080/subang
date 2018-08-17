package com.gcs.suban.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.activity.AddressActivity;
import com.gcs.suban.activity.AroundActivity;
import com.gcs.suban.activity.BackstageActivity;
import com.gcs.suban.activity.BalanceRecordActivity;
import com.gcs.suban.activity.CarActivity;
import com.gcs.suban.activity.CollectActivity;
import com.gcs.suban.activity.CouponActivity;
import com.gcs.suban.activity.CustomerActivity;
import com.gcs.suban.activity.FootprintActivity;
import com.gcs.suban.activity.IncomeActivity;
import com.gcs.suban.activity.InventoryActivity;
import com.gcs.suban.activity.MessageActvity;
import com.gcs.suban.activity.OrderActivity;
import com.gcs.suban.activity.PersonActivity;
import com.gcs.suban.activity.QrCodeActivity;
import com.gcs.suban.activity.RechargeActivity;
import com.gcs.suban.activity.SetActivity;
import com.gcs.suban.activity.StockApplyActivity;
import com.gcs.suban.activity.TeamActivity;
import com.gcs.suban.activity.TeamOrderActivity;
import com.gcs.suban.activity.WithdrawalActivity;
import com.gcs.suban.base.BaseFragment;
import com.gcs.suban.bean.MemberBean;
import com.gcs.suban.eventbus.CarEvent;
import com.gcs.suban.eventbus.CollectEvent;
import com.gcs.suban.eventbus.OrderEvent;
import com.gcs.suban.eventbus.PersonEvent;
import com.gcs.suban.listener.OnMemberListener;
import com.gcs.suban.model.MemberModel;
import com.gcs.suban.model.MemberModelImpl;
import com.gcs.suban.tools.ACache;
import com.gcs.suban.tools.ToastTool;
import com.google.gson.Gson;

import org.json.JSONObject;

import io.rong.eventbus.EventBus;


public class MemberFragment extends BaseFragment implements OnMemberListener,
        OnRefreshListener {
    private String TAG = "MemberFragment";

    private Button Btn_person_set;
    private Button Btn_person_recharge;

    private TextView Tv_name;
    private TextView Tv_personal_number;
    private TextView Tv_personal_people;
    private TextView Tv_personal_time;

    // private TextView Tv_commissionprice;
    private TextView Tv_commission_ok;

    private Button Tv_person_teamnum;
    private Button Tv_person_customnum;
    private Button Tv_commission_num;
    private TextView Tv_commission_total;
    private Button Tv_person_tweetnum;

    // private TextView Tv_rank;
    private TextView Tv_credit1;
    private TextView Tv_credit2;
    private TextView Tv_time;

    private ImageView Img_logo;
    private ImageView Img_level;

    private CardView Card_person_info;
    //private CardView Card_person_withdrawals;
    private CardView Card_person_detail;
    private CardView Card_person_news;
    private CardView Card_person_car;
    private CardView Card_person_collect;
    private CardView Card_person_footprint;
    private CardView Card_person_getvoucher;
    private CardView Card_person_myvoucher;
    private CardView Card_person_address;
    private CardView Cv_person_qrcode;
    private CardView Cv_person_time;
    private CardView Cv_person_commission;
    private CardView Cv_person_customer;
    private CardView Cv_person_myteam;
    private CardView Cv_stock;
    private CardView Cv_person_income;
    private CardView Cv_person_tweet;

    private RelativeLayout Rlyt_all;
    private RelativeLayout Rlyt_paid;
    private RelativeLayout Rlyt_deliver;
    private RelativeLayout Rlyt_harvest;
    private RelativeLayout Rlyt_refund;
    private RelativeLayout Rlyt_income;

    // private RelativeLayout Rlyt_backstage;

    private LinearLayout Llyt_commision;

    private Button Btn_paidnum;
    private Button Btn_delivernum;
    private Button Btn_harvestnum;
    private Button Btn_refundnum;

    private Button Btn_messagenum;
    private Button Btn_carnum;
    private Button Btn_collectnum;

    private MemberModel memberModel;

    private MemberBean bean;
    private Gson gson;

    private String isVIP = "0";
    private String time = "0";
    private String inventory_grade = "0";

    private ACache mCache;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container,
                false);
        return view;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_person_set:
                Intent intent_set = new Intent(context, SetActivity.class);
                startActivity(intent_set);
                break;
            case R.id.btn_person_recharge:
                Intent intent_recharge = new Intent(context, RechargeActivity.class);
                startActivity(intent_recharge);
                break;
            case R.id.cv_person_qrcode:
                Intent intent_qrcode = new Intent(context, QrCodeActivity.class);
                startActivity(intent_qrcode);
                break;
            case R.id.cv_person_custom:
                Intent intent_custom = new Intent(context, CustomerActivity.class);
                startActivity(intent_custom);
                break;
            case R.id.cv_person_info:
                Intent intent_info = new Intent(context, PersonActivity.class);
                startActivity(intent_info);
                break;
            case R.id.rlyt_all_orders:
                Intent intent_all = new Intent(context, OrderActivity.class);
                intent_all.putExtra("postion", "0");
                startActivity(intent_all);
                break;
            case R.id.rlyt_person_paid:
                Intent intent_paid = new Intent(context, OrderActivity.class);
                intent_paid.putExtra("postion", "1");
                startActivity(intent_paid);
                break;
            case R.id.rlyt_person_deliver:
                Intent intent_deliver = new Intent(context, OrderActivity.class);
                intent_deliver.putExtra("postion", "2");
                startActivity(intent_deliver);
                break;
            case R.id.rlyt_person_harvest:
                Intent intent_harvest = new Intent(context, OrderActivity.class);
                intent_harvest.putExtra("postion", "3");
                startActivity(intent_harvest);
                break;
            case R.id.rlyt_person_refund:
                Intent intent_refund = new Intent(context, OrderActivity.class);
                intent_refund.putExtra("postion", "4");
                startActivity(intent_refund);
                break;
            case R.id.rlyt_backstage:
                // Intent intent_backstage = new Intent(context,
                // BackstageActivity.class);
                // startActivity(intent_backstage);
                break;
            case R.id.llyt_commission:
                Intent intent_backstage = new Intent(context,
                        BackstageActivity.class);
                startActivity(intent_backstage);
                break;
            //case R.id.cv_person_withdrawals:
                /*
                Intent intent_withdrawals = new Intent(context,
                        WithdrawalActivity.class);
                startActivity(intent_withdrawals);
                */
                //break;
            case R.id.cv_person_detail:
                Intent intent_detail = new Intent(context,
                        BalanceRecordActivity.class);
                startActivity(intent_detail);
                break;
            case R.id.cv_person_news:
                Intent intent_news = new Intent(context, MessageActvity.class);
                startActivity(intent_news);
                break;
            case R.id.cv_person_car:
                Intent intent_car = new Intent(context, CarActivity.class);
                startActivity(intent_car);
                break;
            case R.id.cv_person_collect:
                Intent intent_collect = new Intent(context, CollectActivity.class);
                startActivity(intent_collect);
                break;
            case R.id.cv_person_footprint:
                Intent intent_footprint = new Intent(context,
                        FootprintActivity.class);
                startActivity(intent_footprint);
                break;
            case R.id.cv_person_getvoucher:
                break;
            case R.id.cv_person_myvoucher:
                Intent intent_myvoucher = new Intent(context, CouponActivity.class);
                startActivity(intent_myvoucher);
                break;
            case R.id.cv_person_address:
                Intent intent_address = new Intent(context, AddressActivity.class);
                startActivity(intent_address);
                break;
            case R.id.cv_person_commission:
                Intent intent_commission = new Intent(context,
                        TeamOrderActivity.class);
                startActivity(intent_commission);
                break;
            case R.id.cv_person_myteam:
                Intent intent_team = new Intent(context, TeamActivity.class);
                startActivity(intent_team);
                break;
            case R.id.cv_person_around:
                Intent intent_around = new Intent(context, AroundActivity.class);
                startActivity(intent_around);
                break;
            case R.id.cv_stock:
                Log.i(TAG, "inventory: " + inventory_grade);
                if(inventory_grade == null || inventory_grade.equals("0")){
                    Intent intent_apply = new Intent(context, StockApplyActivity.class);
                    startActivity(intent_apply);
                }else{
                    Intent intent_stock = new Intent(context, InventoryActivity.class);
                    startActivity(intent_stock);
                }
                break;
            case R.id.cv_person_income:
                Intent intent_income = new Intent(context, IncomeActivity.class);
                startActivity(intent_income);
            default:
                break;
        }
    }

    @Override
    protected void init() {
        mCache = ACache.get(context);
        EventBus.getDefault().register(this);
        InitImageLoader();
        // TODO Auto-generated method stub
        Btn_person_set = (Button) context.findViewById(R.id.btn_person_set);
        Btn_person_recharge = (Button) context
                .findViewById(R.id.btn_person_recharge);
        context.findViewById(R.id.cv_person_around).setOnClickListener(this);
        Tv_name = (TextView) context.findViewById(R.id.tv_personal_name);

        // Tv_rank = (TextView) context.findViewById(R.id.tv_personal_rank);

        Tv_personal_number = (TextView) context
                .findViewById(R.id.tv_personal_number);
        Tv_personal_people = (TextView) context
                .findViewById(R.id.tv_personal_people);
        Tv_personal_time = (TextView) context
                .findViewById(R.id.tv_personal_time);
        Tv_credit1 = (TextView) context.findViewById(R.id.tv_credit1);
        Tv_credit2 = (TextView) context.findViewById(R.id.tv_credit2);
        // Tv_commissionprice= (TextView)
        // context.findViewById(R.id.tv_commissionprice);
        Tv_commission_ok = (TextView) context
                .findViewById(R.id.tv_commission_ok);
        Tv_person_teamnum = (Button) context
                .findViewById(R.id.tv_person_teamnum);
        Tv_person_customnum = (Button) context
                .findViewById(R.id.tv_person_customnum);
        Tv_commission_num = (Button) context
                .findViewById(R.id.tv_commission_num);
        Tv_commission_total = (TextView) context
                .findViewById(R.id.tv_commission_total);
        Tv_time = (TextView) context.findViewById(R.id.tv_duetotime);
        Tv_person_tweetnum = (Button)context.findViewById(R.id.tv_person_tweetnum);

        Img_logo = (ImageView) context.findViewById(R.id.img_personal_logo);
        Img_level = (ImageView) context.findViewById(R.id.img_level);

        Cv_person_qrcode = (CardView) context
                .findViewById(R.id.cv_person_qrcode);
        Cv_person_time = (CardView) context.findViewById(R.id.cv_person_time);

        Card_person_info = (CardView) context.findViewById(R.id.cv_person_info);
        /*
        Card_person_withdrawals = (CardView) context
                .findViewById(R.id.cv_person_withdrawals);
        */
        Card_person_detail = (CardView) context
                .findViewById(R.id.cv_person_detail);

        Card_person_news = (CardView) context.findViewById(R.id.cv_person_news);
        Card_person_car = (CardView) context.findViewById(R.id.cv_person_car);
        Card_person_collect = (CardView) context
                .findViewById(R.id.cv_person_collect);
        Card_person_footprint = (CardView) context
                .findViewById(R.id.cv_person_footprint);
        Card_person_getvoucher = (CardView) context
                .findViewById(R.id.cv_person_getvoucher);
        Card_person_myvoucher = (CardView) context
                .findViewById(R.id.cv_person_myvoucher);
        Card_person_address = (CardView) context
                .findViewById(R.id.cv_person_address);
        Cv_person_commission = (CardView) context
                .findViewById(R.id.cv_person_commission);
        Cv_person_customer = (CardView) context
                .findViewById(R.id.cv_person_custom);
        Cv_person_myteam = (CardView) context
                .findViewById(R.id.cv_person_myteam);
        Cv_stock = (CardView) context.findViewById(R.id.cv_stock);
        Cv_person_income = (CardView)context.findViewById(R.id.cv_person_income);
        Cv_person_tweet = (CardView)context.findViewById(R.id.cv_perspn_tweet);

        Rlyt_all = (RelativeLayout) context.findViewById(R.id.rlyt_all_orders);
        Rlyt_paid = (RelativeLayout) context
                .findViewById(R.id.rlyt_person_paid);
        Rlyt_deliver = (RelativeLayout) context
                .findViewById(R.id.rlyt_person_deliver);
        Rlyt_harvest = (RelativeLayout) context
                .findViewById(R.id.rlyt_person_harvest);
        Rlyt_refund = (RelativeLayout) context
                .findViewById(R.id.rlyt_person_refund);

        Llyt_commision = (LinearLayout) context
                .findViewById(R.id.llyt_commission);

        // Rlyt_backstage= (RelativeLayout) context.findViewById(
        // R.id.rlyt_backstage);

        Btn_paidnum = (Button) context.findViewById(R.id.paidnum);
        Btn_delivernum = (Button) context.findViewById(R.id.delivernum);
        Btn_harvestnum = (Button) context.findViewById(R.id.harvestnum);
        Btn_refundnum = (Button) context.findViewById(R.id.refundnum);
        Btn_messagenum = (Button) context.findViewById(R.id.newsnum);
        Btn_carnum = (Button) context.findViewById(R.id.carnum);
        Btn_collectnum = (Button) context.findViewById(R.id.collectnum);

        swipeRefreshLayout = (SwipeRefreshLayout) context
                .findViewById(R.id.swipeRefreshLayout_member);
        swipeRefreshLayout.setOnRefreshListener(this);
        InitSwipeRefreshLayout();

        Btn_person_set.setOnClickListener(this);
        Btn_person_recharge.setOnClickListener(this);

        Rlyt_all.setOnClickListener(this);
        Rlyt_paid.setOnClickListener(this);
        Rlyt_deliver.setOnClickListener(this);
        Rlyt_harvest.setOnClickListener(this);
        Rlyt_refund.setOnClickListener(this);

        Llyt_commision.setOnClickListener(this);
        // Rlyt_backstage.setOnClickListener(this);

        Card_person_info.setOnClickListener(this);
        //Card_person_withdrawals.setOnClickListener(this);
        Card_person_detail.setOnClickListener(this);
        Card_person_news.setOnClickListener(this);
        Card_person_car.setOnClickListener(this);
        Card_person_collect.setOnClickListener(this);
        Card_person_footprint.setOnClickListener(this);
        Card_person_getvoucher.setOnClickListener(this);
        Card_person_myvoucher.setOnClickListener(this);
        Card_person_address.setOnClickListener(this);
        Cv_person_qrcode.setOnClickListener(this);
        Cv_person_commission.setOnClickListener(this);
        Cv_person_customer.setOnClickListener(this);
        Cv_person_myteam.setOnClickListener(this);
        Cv_stock.setOnClickListener(this);
        Cv_person_income.setOnClickListener(this);
        Cv_person_tweet.setOnClickListener(this);

        hideView();

        memberModel = new MemberModelImpl();
        if (mCache.getAsString(TAG) != null) {
            JsonResolve(mCache.getAsString(TAG));
            Log.i(TAG, "???????????  ---- " + mCache.getAsString(TAG));
        }

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }

        });
        this.onRefresh();
    }

    private void setData(MemberBean bean) {
        this.bean = bean;

        Tv_name.setText(bean.nickname);
        // Tv_rank.setText("??????:"+bean.level);
        Tv_credit1.setText(bean.credit1);
        Tv_credit2.setText(bean.credit2);

        Tv_personal_number.setText(bean.memberid);
        Tv_personal_people.setText(bean.agentname);
        Tv_personal_time.setText(bean.createtime);
        // Tv_commissionprice.setText(bean.commission_orderprice+"?");
        Tv_commission_ok.setText(bean.commission_ok + "Ԫ");
        Tv_person_teamnum.setText(bean.myteam);
        Tv_person_customnum.setText(bean.mycustom);
        Tv_commission_num.setText(bean.commission_order);
        Tv_commission_total.setText(bean.commission_total + "Ԫ");
        Tv_person_tweetnum.setText(bean.tweer);

        Btn_paidnum.setText(bean.waitpay_num);
        Btn_delivernum.setText(bean.waitsend_num);
        Btn_harvestnum.setText(bean.waittake_num);
        Btn_refundnum.setText(bean.waitre_num);

        Btn_messagenum.setText(bean.message_num);
        Btn_carnum.setText(bean.car_num);
        Btn_collectnum.setText(bean.collect_num);

        isVIP = bean.dispose;
        time = bean.duetotime;

        Tv_time.setText(time);

        imageLoader.displayImage(bean.avatar, Img_logo, options3);

        inventory_grade = bean.inventory_grade;

        ShowView();
    }

    private void hideView() {
        Img_level.setVisibility(View.INVISIBLE);
        Tv_name.setVisibility(View.INVISIBLE);
        // Tv_rank.setVisibility(View.INVISIBLE);
        Tv_credit1.setVisibility(View.INVISIBLE);
        Tv_credit2.setVisibility(View.INVISIBLE);
        Btn_paidnum.setVisibility(View.INVISIBLE);
        Btn_delivernum.setVisibility(View.INVISIBLE);
        Btn_harvestnum.setVisibility(View.INVISIBLE);
        Btn_refundnum.setVisibility(View.INVISIBLE);
        Btn_carnum.setVisibility(View.INVISIBLE);
        Btn_collectnum.setVisibility(View.INVISIBLE);

        Cv_person_time.setVisibility(View.GONE);

        Tv_personal_number.setVisibility(View.INVISIBLE);
        Tv_personal_people.setVisibility(View.INVISIBLE);
        Tv_personal_time.setVisibility(View.INVISIBLE);
        // Tv_commissionprice.setVisibility(View.INVISIBLE);
        Tv_commission_ok.setVisibility(View.INVISIBLE);
        Tv_person_teamnum.setVisibility(View.INVISIBLE);
        Tv_person_customnum.setVisibility(View.INVISIBLE);
        Tv_commission_num.setVisibility(View.INVISIBLE);
        Tv_commission_total.setVisibility(View.INVISIBLE);
        Tv_person_tweetnum.setVisibility(View.INVISIBLE);
        Cv_person_tweet.setVisibility(View.GONE);
    }

    private void ShowView() {

        Tv_name.setVisibility(View.VISIBLE);
        // Tv_rank.setVisibility(View.VISIBLE);
        Tv_credit1.setVisibility(View.VISIBLE);
        Tv_credit2.setVisibility(View.VISIBLE);
        Btn_carnum.setVisibility(View.VISIBLE);
        Btn_collectnum.setVisibility(View.VISIBLE);

        Tv_personal_number.setVisibility(View.VISIBLE);
        Tv_personal_people.setVisibility(View.VISIBLE);
        Tv_personal_time.setVisibility(View.VISIBLE);
        // Tv_commissionprice.setVisibility(View.VISIBLE);
        Tv_commission_ok.setVisibility(View.VISIBLE);
        Tv_person_teamnum.setVisibility(View.VISIBLE);
        Tv_person_customnum.setVisibility(View.VISIBLE);
        Tv_commission_num.setVisibility(View.VISIBLE);
        Tv_commission_total.setVisibility(View.VISIBLE);
        Tv_person_tweetnum.setVisibility(View.VISIBLE);

        if (isVIP.equals("0")) {
            Cv_person_time.setVisibility(View.GONE);
            Img_level.setVisibility(View.INVISIBLE);
        } else {
            Img_level.setVisibility(View.VISIBLE);
            //Cv_person_time.setVisibility(View.VISIBLE);
        }


        if (bean.waitpay_num.equals("0")) {
            Btn_paidnum.setVisibility(View.GONE);
        } else {
            Btn_paidnum.setVisibility(View.VISIBLE);
        }

        if (bean.waitsend_num.equals("0")) {
            Btn_delivernum.setVisibility(View.GONE);
        } else {
            Btn_delivernum.setVisibility(View.VISIBLE);
        }

        if (bean.waittake_num.equals("0")) {
            Btn_harvestnum.setVisibility(View.GONE);
        } else {
            Btn_harvestnum.setVisibility(View.VISIBLE);
        }

        if (bean.waitre_num.equals("0")) {
            Btn_refundnum.setVisibility(View.GONE);
        } else {
            Btn_refundnum.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onSuccess(String json) {
        // TODO Auto-generated method stub
        JsonResolve(json);
        mCache.put(TAG, json, 60 * 60 * 24 * 3);

        swipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onError(String error) {
        // TODO Auto-generated method stub
        ToastTool.showToast(context, error);

        swipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub
        memberModel.getInfo(Url.member, this);
    }

    public void JsonResolve(String response) {
        // TODO Auto-generated method stub
        try {
            JSONObject jsonObject = new JSONObject(response);
            String result = jsonObject.getString("result");
            if (result.equals("1001")) {
                String data = jsonObject.getString("data");
                JSONObject dataJsonObject = new JSONObject(data);
                gson = new Gson();
                bean = gson.fromJson(dataJsonObject.toString(),
                        MemberBean.class);

                String vip = jsonObject.getString("vip");
                if (vip.equals("1")) {
                    Img_level.setBackgroundResource(R.drawable.vip1);
                } else if (vip.equals("2")) {
                    Img_level.setBackgroundResource(R.drawable.vip2);
                } else if (vip.equals("3")) {
                    Img_level.setBackgroundResource(R.drawable.vip3);
                }

                setData(bean);
            } else {
                String resulttext = jsonObject.getString("resulttext");
                ToastTool.showToast(context, resulttext);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ToastTool.showToast(context, Url.jsonError);
        }
    }

    public void onEventMainThread(PersonEvent event) {
        String msg = event.getMsg();
        if (event.getType().equals("nickname")) {
            Tv_name.setText(msg);
        } else if (event.getType().equals("logo")) {
            imageLoader.displayImage(msg, Img_logo, options3);
        } else {
            memberModel.getInfo(Url.member, this);
        }
    }

    public void onEventMainThread(CarEvent event) {
        memberModel.getInfo(Url.member, this);
    }

    public void onEventMainThread(OrderEvent event) {
        memberModel.getInfo(Url.member, this);
    }

    public void onEventMainThread(CollectEvent event) {
        memberModel.getInfo(Url.member, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
