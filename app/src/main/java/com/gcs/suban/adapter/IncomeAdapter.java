package com.gcs.suban.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gcs.suban.R;
import com.gcs.suban.base.BaseListAdapter;
import com.gcs.suban.bean.IncomeBean;

import java.util.List;

public class IncomeAdapter extends BaseListAdapter<IncomeBean> {

    class ViewHolder{
        public TextView income_date;
        public RelativeLayout comission;
        public TextView commission_title;
        public TextView commission_price;
        public RelativeLayout manager_commission;
        public TextView manager_title;
        public TextView manager_price;
        public RelativeLayout manager_team_commission;
        public TextView manager_team_title;
        public TextView manager_team_price;
        public RelativeLayout stock_commission;
        public TextView stock_title;
        public TextView stock_price;
        public RelativeLayout balance_commission;
        public TextView balance_title;
        public TextView balance_price;
    }

    public IncomeAdapter(Context context, List<IncomeBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item_incomes, null);
            viewHolder.income_date = (TextView)convertView.findViewById(R.id.income_date);
            viewHolder.comission = (RelativeLayout)convertView.findViewById(R.id.comission);
            viewHolder.commission_title = (TextView)convertView.findViewById(R.id.commission_title);
            viewHolder.commission_price = (TextView)convertView.findViewById(R.id.commission_price);
            viewHolder.manager_commission = (RelativeLayout)convertView.findViewById(R.id.manager_commission);
            viewHolder.manager_title = (TextView)convertView.findViewById(R.id.manager_title);
            viewHolder.manager_price = (TextView)convertView.findViewById(R.id.manager_price);
            viewHolder.manager_team_commission = (RelativeLayout)convertView.findViewById(R.id.manager_team_commission);
            viewHolder.manager_team_title = (TextView)convertView.findViewById(R.id.manager_team_title);
            viewHolder.manager_team_price = (TextView)convertView.findViewById(R.id.manager_team_price);
            viewHolder.stock_commission = (RelativeLayout)convertView.findViewById(R.id.stock_commission);
            viewHolder.stock_title = (TextView)convertView.findViewById(R.id.stock_title);
            viewHolder.stock_price = (TextView)convertView.findViewById(R.id.stock_price);
            viewHolder.balance_commission = (RelativeLayout)convertView.findViewById(R.id.balance_commission);
            viewHolder.balance_title = (TextView)convertView.findViewById(R.id.balance_title);
            viewHolder.balance_price = (TextView)convertView.findViewById(R.id.balance_price);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
            convertView.setTag(viewHolder);
        }

        IncomeBean incomeBean = listItems.get(position);
        viewHolder.income_date.setText(incomeBean.getDate());
        if(incomeBean.hasOrder() == 1){
            viewHolder.comission.setVisibility(View.VISIBLE);
            viewHolder.commission_title.setText("分销佣金");
            viewHolder.commission_price.setText(String.valueOf(incomeBean.getOrderCommission()));
        }else{
            viewHolder.comission.setVisibility(View.GONE);
        }
        if(incomeBean.hasAgent() == 1){
            viewHolder.manager_commission.setVisibility(View.VISIBLE);
            viewHolder.manager_title.setText("代理佣金");
            viewHolder.manager_price.setText(String.valueOf(incomeBean.getAgentCommission()));

            if(incomeBean.hasTeam() == 1){
                viewHolder.manager_team_commission.setVisibility(View.VISIBLE);
                viewHolder.manager_team_title.setText("团队奖金");
                viewHolder.manager_team_price.setText(String.valueOf(incomeBean.getTeamCommission()));
            }else{
                viewHolder.manager_team_commission.setVisibility(View.GONE);
            }

        }else{
            viewHolder.manager_commission.setVisibility(View.GONE);
        }
        if(incomeBean.hasStock() == 1){
            viewHolder.stock_commission.setVisibility(View.VISIBLE);
            viewHolder.stock_title.setText("库存结算");
            viewHolder.stock_price.setText(String.valueOf(incomeBean.getStockCommission()));
        }else{
            viewHolder.stock_commission.setVisibility(View.GONE);
        }
        if(incomeBean.hasBalance() == 1){
            viewHolder.balance_commission.setVisibility(View.VISIBLE);
            viewHolder.balance_title.setText("库存业绩");
            viewHolder.balance_price.setText(String.valueOf(incomeBean.getBalanceCommission()));
        }else{
            viewHolder.balance_commission.setVisibility(View.GONE);
        }

        return convertView;
    }
}
