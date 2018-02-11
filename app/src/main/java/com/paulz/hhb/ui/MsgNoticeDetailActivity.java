package com.paulz.hhb.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.core.framework.net.NetworkWorker;
import com.core.framework.util.DESUtil;
import com.core.framework.util.DialogUtil;
import com.paulz.hhb.R;
import com.paulz.hhb.base.BaseActivity;
import com.paulz.hhb.common.APIUtil;
import com.paulz.hhb.common.AppUrls;
import com.paulz.hhb.httputil.HttpRequester;
import com.paulz.hhb.httputil.ParamBuilder;
import com.paulz.hhb.parser.gson.BaseObject;
import com.paulz.hhb.parser.gson.GsonParser;
import com.paulz.hhb.utils.AppUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pualbeben on 17/11/7.
 */

public class MsgNoticeDetailActivity extends BaseActivity {

    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_describ)
    TextView tvDescrib;
    @BindView(R.id.tv_detail)
    TextView tvDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setActiviyContextView(R.layout.activity_msg_notice_detail, false, true);
        ButterKnife.bind(this);
        setTitleText("", "消息中心", 0, true);
    }


    public static void invoke(Context context, String msgId) {
        Intent intent = new Intent(context, MsgNoticeDetailActivity.class);
        intent.putExtra("extra_id", msgId);
        context.startActivity(intent);
    }



    private void loadData() {

        DialogUtil.showDialog(lodDialog);
        ParamBuilder params = new ParamBuilder();
        HttpRequester requester = new HttpRequester();
        NetworkWorker.getInstance().post(APIUtil.parseGetUrlHasMethod(params.getParamList(), AppUrls.getInstance().URL_VERIFY_OR_MODIFY_TEL), new NetworkWorker.ICallback() {
            @Override
            public void onResponse(int status, String result) {
                if (!isFinishing()) DialogUtil.dismissDialog(lodDialog);
                if (status == 200) {
                    BaseObject<Object> object = GsonParser.getInstance().parseToObj(result, Object.class);
                    if (object != null && object.status == BaseObject.STATUS_OK) {

                        handleMsg();
                    } else {

                        AppUtil.showToast(getApplicationContext(), "获取失败");
                    }
                }
            }
        }, requester, DESUtil.SECRET_DES);


    }


    private void handleMsg(){

    }


}
