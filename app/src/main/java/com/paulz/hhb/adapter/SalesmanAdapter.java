package com.paulz.hhb.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.paulz.hhb.R;
import com.paulz.hhb.common.AppUrls;
import com.paulz.hhb.common.recyclerview.CircleTransform;
import com.paulz.hhb.model.Salesman;
import com.paulz.hhb.model.Team;
import com.paulz.hhb.view.CircleImageView;

import butterknife.BindView;

/**
 * Created by pualbeben on 17/5/21.
 */

public class SalesmanAdapter extends AbsMutipleAdapter<Salesman, SalesmanAdapter.TeamHolder> {


    private CircleTransform circleTransform;


    public SalesmanAdapter(Activity context) {
        super(context);
        circleTransform=new CircleTransform(context);
    }


    @Override
    public TeamHolder onCreateViewHolder(int position, int viewType, ViewGroup parent) {
        return new TeamHolder(mInflater.inflate(R.layout.item_team, null));
    }

    @Override
    public void onBindViewHolder(int position, TeamHolder holder) {
        final Salesman bean = (Salesman) getItem(position);
        Glide.with(mContext).load(AppUrls.getInstance().DOMAIN+bean.member_avatar).transform(circleTransform).placeholder(R.drawable.user2).error(R.drawable.user2).into(holder.ivAvatar);
        holder.tvDate.setText(bean.createtime);
        holder.tvManagerName.setText(bean.member_username);
        holder.tvTeamName.setText(bean.store_name);
        holder.tvStatus.setText(bean.sfsm);
        holder.tvTotalFee.setText("总保费"+bean.allins);
        holder.tvMonthlyFee.setText("当月保费"+bean.monthins);
        holder.tvDaylyFee.setText("当日保费"+bean.dayins);
        holder.tvTotalCount.setText("总出单量"+bean.allchudan);
        holder.tvMonthlyCount.setText("当月单量"+bean.monthchudan);
        holder.tvDaylyCount.setText("当日单量"+bean.daychudan);


    }

    public static class TeamHolder extends ViewHolder {

        @BindView(R.id.iv_avatar)
        ImageView ivAvatar;
        @BindView(R.id.tv_manager_name)
        TextView tvManagerName;
        @BindView(R.id.tv_team_name)
        TextView tvTeamName;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_total_fee)
        TextView tvTotalFee;
        @BindView(R.id.tv_monthly_fee)
        TextView tvMonthlyFee;
        @BindView(R.id.tv_dayly_fee)
        TextView tvDaylyFee;
        @BindView(R.id.tv_total_count)
        TextView tvTotalCount;
        @BindView(R.id.tv_monthly_count)
        TextView tvMonthlyCount;
        @BindView(R.id.tv_dayly_count)
        TextView tvDaylyCount;

        public TeamHolder(View view) {
            super(view);
        }
    }

    ICallPhone iCallPhone;

    public void setICallPhone(ICallPhone iCallPhone) {
        this.iCallPhone = iCallPhone;
    }

    public interface ICallPhone {
        void onCall(String phone);
    }
}
