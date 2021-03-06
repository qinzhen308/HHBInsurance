package com.paulz.hhb.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.core.framework.net.NetworkWorker;
import com.core.framework.util.DESUtil;
import com.paulz.hhb.R;
import com.paulz.hhb.common.APIUtil;
import com.paulz.hhb.common.AppUrls;
import com.paulz.hhb.httputil.HttpRequester;
import com.paulz.hhb.httputil.ParamBuilder;
import com.paulz.hhb.model.CustomerDetail;
import com.paulz.hhb.model.Msg;
import com.paulz.hhb.model.Order;
import com.paulz.hhb.parser.gson.BaseObject;
import com.paulz.hhb.parser.gson.GsonParser;
import com.paulz.hhb.ui.AccountActivity;
import com.paulz.hhb.ui.BounsRecordActivity;
import com.paulz.hhb.ui.CommonWebActivity;
import com.paulz.hhb.ui.CustomerInfoActivity;
import com.paulz.hhb.ui.MsgCenterActivity;
import com.paulz.hhb.ui.MyBounsActivity;
import com.paulz.hhb.ui.NameVerifyActivity;
import com.paulz.hhb.ui.OrderInfoActivity;
import com.paulz.hhb.ui.WithdrawHistoryActivity;

import butterknife.BindView;

/**
 * Created by pualbeben on 17/5/21.
 */

public class MsgOrderAdapter extends AbsMutipleAdapter<Msg, MsgOrderAdapter.ViewHolderImpl> {



    public MsgOrderAdapter(Activity context) {
        super(context);
    }


    @Override
    public ViewHolderImpl onCreateViewHolder(int position, int viewType, ViewGroup parent) {
        return new ViewHolderImpl(mInflater.inflate(R.layout.item_msg_order, null));
    }

    @Override
    public void onBindViewHolder(int position, ViewHolderImpl holder) {
        final Msg msg=(Msg)getItem(position);
        holder.tvName.setText(msg.title);
        holder.tvDate.setText(msg.date);
        holder.tvDescrib.setText(msg.abstractStr);
        if(msg.unread==0){
            holder.vUnread.setVisibility(View.GONE);
        }else {
            holder.vUnread.setVisibility(View.VISIBLE);
        }
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //消息类型 0无跳转无详情，1公告类型，图文消息，2以上为各种跳转链接，3人工核保结果 ，4支付完成（保单生效），
                // 5入账，6提现处理，7订单即将关闭，8车险即将到期，9实名认证审核结果
                if(msg.unread==1){
                    setReaded(msg);
                }
                if(msg.type==1){
                    ParamBuilder params=new ParamBuilder();
                    params.append("id",msg.id);
                    CommonWebActivity.invoke(mContext, APIUtil.parseGetUrlHasMethod(params.getParamList(),AppUrls.getInstance().URL_MSG_DETAIL),"公告详情");
                }else if(msg.type==7||msg.type==4||msg.type==3){
                    OrderInfoActivity.invoke(mContext,msg.message_extra.sn);
                }else if(msg.type==9){
                    NameVerifyActivity.invoke(mContext);
                }else if(msg.type==5){
                    MyBounsActivity.invoke(mContext);
                }else if(msg.type==6){
                    WithdrawHistoryActivity.invoke(mContext);
                }else if(msg.type==8){
                    CustomerInfoActivity.invoke(mContext,msg.message_extra.id);
                }else if(msg.type==2){
                    CommonWebActivity.invoke(mContext, msg.message_extra.url,"");
                }

            }
        });

    }

    public void setReaded(final Msg msg){
        ParamBuilder params=new ParamBuilder();
        HttpRequester requester=new HttpRequester();
        requester.getParams().put("id",msg.id);
        NetworkWorker.getInstance().post(APIUtil.parseGetUrlHasMethod(params.getParamList(),AppUrls.getInstance().URL_MSG_DETAIL ), new NetworkWorker.ICallback() {
            @Override
            public void onResponse(int status, String result) {
                if(status==200){
//                    BaseObject<Object> obj= GsonParser.getInstance().parseToObj(result,Object.class);
                        msg.unread=0;
                        notifyDataSetChanged();
                        ((MsgCenterActivity)mContext).loadMsgCount();
                }
            }
        },requester, DESUtil.SECRET_DES);

    }



    public static class ViewHolderImpl extends ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_describ)
        TextView tvDescrib;
        @BindView(R.id.v_unread)
        View vUnread;

        public ViewHolderImpl(View view) {
            super(view);
        }
    }
}
