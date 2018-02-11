package com.paulz.hhb.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.core.framework.net.NetworkWorker;
import com.core.framework.util.DialogUtil;
import com.paulz.hhb.R;
import com.paulz.hhb.common.APIUtil;
import com.paulz.hhb.common.AppUrls;
import com.paulz.hhb.httputil.HttpRequester;
import com.paulz.hhb.httputil.ParamBuilder;
import com.paulz.hhb.model.Bank;
import com.paulz.hhb.model.BankCard;
import com.paulz.hhb.model.Model;
import com.paulz.hhb.parser.gson.BaseObject;
import com.paulz.hhb.parser.gson.GsonParser;
import com.paulz.hhb.utils.AppUtil;
import com.paulz.hhb.utils.Image13Loader;
import com.paulz.hhb.view.CommonDialog;

import butterknife.BindView;

/**
 * Created by pualbeben on 17/5/22.
 */

public class BankCardAdapter extends AbsMutipleAdapter<BankCard, BankCardAdapter.RecordHolder> {



    public BankCardAdapter(Activity context) {
        super(context);
    }



    @Override
    public RecordHolder onCreateViewHolder(int position, int viewType, ViewGroup parent) {
        return new RecordHolder(mInflater.inflate(R.layout.item_bank_card, null));
    }

    @Override
    public void onBindViewHolder(int position, RecordHolder holder) {
        final BankCard bean=(BankCard)getItem(position);
        holder.tvName.setText(bean.bank.name);
        holder.tvCardNumber.setText(""+bean.member_bankcard_no);
        Image13Loader.getInstance().loadImage(AppUrls.getInstance().IMG_BANKIMG+bean.bank.logo,holder.ivIcon);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog(bean);
            }
        });
    }

    private void showDeleteDialog(final BankCard bankCard){
        final CommonDialog dialog=new CommonDialog(mContext);
        dialog.setDesc("确定删除银行卡");
        dialog.setOnRightClickListener(new CommonDialog.OnClickListener() {
            @Override
            public void onClick() {
                if(mContext instanceof OnDeleteListener){
                    ((OnDeleteListener)mContext).onDelete(bankCard);
                }

            }
        });
        dialog.show();
    }



    public static class RecordHolder extends ViewHolder {

        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_card_number)
        TextView tvCardNumber;


        public RecordHolder(View view) {
            super(view);
        }
    }

    public interface OnDeleteListener{
        void onDelete(BankCard bean);
    }
}
