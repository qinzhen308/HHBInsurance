package com.paulz.hhb.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paulz.hhb.R;
import com.paulz.hhb.common.APIUtil;
import com.paulz.hhb.model.Customer;
import com.paulz.hhb.model.Model;
import com.paulz.hhb.ui.CustomerInfoActivity;
import com.paulz.hhb.utils.AppUtil;
import com.paulz.hhb.utils.DateUtil;

import butterknife.BindView;

/**
 * Created by pualbeben on 17/5/21.
 */

public class CustomerAdapter extends AbsMutipleAdapter<Customer, CustomerAdapter.CustomerHolder> {




    public CustomerAdapter(Activity context) {
        super(context);
    }



    @Override
    public CustomerHolder onCreateViewHolder(int position, int viewType, ViewGroup parent) {
        return new CustomerHolder(mInflater.inflate(R.layout.item_customer, null));
    }

    @Override
    public void onBindViewHolder(int position, CustomerHolder holder) {
        final Customer bean=(Customer)getItem(position);

//        if(bean.cday>2000000000){
//            holder.tvDate.setText("未设置");
//
//        }else {
//
//            holder.tvDate.setText(bean.cday/(60*60*24)+"天到期");
//        }
        holder.tvDate.setText(bean.cday);

        holder.tvCarId.setText(AppUtil.isNull(bean.carnumber)?"暂无车":bean.carnumber);
        holder.tvName.setText(bean.customer_name);
        holder.ivPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iCallPhone!=null){
                    iCallPhone.onCall(bean.customer_tel);
                }
            }
        });
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerInfoActivity.invoke(mContext,bean.customer_id);
            }
        });

    }

    public static class CustomerHolder extends ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_phone)
        ImageView ivPhone;
        @BindView(R.id.tv_car_id)
        TextView tvCarId;
        @BindView(R.id.tv_date)
        TextView tvDate;

        public CustomerHolder(View view) {
            super(view);
        }
    }

    ICallPhone iCallPhone;

    public void setICallPhone(ICallPhone iCallPhone) {
        this.iCallPhone = iCallPhone;
    }

    public interface ICallPhone{
        void onCall(String phone);
    }
}
