package com.paulz.hhb.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.core.framework.net.NetworkWorker;
import com.core.framework.util.DialogUtil;
import com.paulz.hhb.R;
import com.paulz.hhb.base.BaseActivity;
import com.paulz.hhb.common.APIUtil;
import com.paulz.hhb.common.AppUrls;
import com.paulz.hhb.common.IActions;
import com.paulz.hhb.httputil.ParamBuilder;
import com.paulz.hhb.parser.gson.BaseObject;
import com.paulz.hhb.parser.gson.GsonParser;
import com.paulz.hhb.utils.AppUtil;
import com.paulz.hhb.utils.Image13Loader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pualbeben on 17/6/18.
 * 核保失败原因
 */

public class InsureFailedReasonActivity extends BaseActivity {


    PageData data;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.layout_order_detail)
    LinearLayout layoutOrderDetail;
    @BindView(R.id.tv_reason)
    TextView tvReason;
    @BindView(R.id.btn_next)
    TextView btnNext;
    @BindView(R.id.tv_status)
    TextView tvStatua;

    private String orderSn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderSn=getIntent().getStringExtra("order_sn");
        initView();
        loadData();
    }

    private void initView() {
        setActiviyContextView(R.layout.activity_failed_season, true, true);
        setTitleText("", "核保结果", 0, true);
        ButterKnife.bind(this);
    }


    public static void invoke(Context context,String orderSn) {
        Intent intent = new Intent(context, InsureFailedReasonActivity.class);
        intent.putExtra("order_sn",orderSn);
        context.startActivity(intent);
    }


    public void loadData() {

        showLoading();
        ParamBuilder params = new ParamBuilder();
        params.append("sn",orderSn);
        NetworkWorker.getInstance().get(APIUtil.parseGetUrlHasMethod(params.getParamList(), AppUrls.getInstance().URL_INSURE_FAILED_REASON), new NetworkWorker.ICallback() {
            @Override
            public void onResponse(int status, String result) {
                if (status == 200) {
                    BaseObject<PageData> object = GsonParser.getInstance().parseToObj(result, PageData.class);
                    if (object != null && object.status == BaseObject.STATUS_OK && object.data != null) {
                        data = object.data;
                        showSuccess();
                        handleData();

                    } else {
                        showNodata();
                    }
                } else {
                    showFailture();
                }
            }
        });
    }

    private void handleData() {
        tvTitle.setText(data.companyname + "-" + data.carnumber);
        Image13Loader.getInstance().loadImage(AppUrls.getInstance().DOMAIN + data.img, ivIcon);
        String reason=data.hbdesc.replace("\\n","\n");
        tvReason.setText(reason);
        tvStatua.setText(data.status);
        if(data.status.equals("核保中")){
            btnNext.setVisibility(View.GONE);
        }

        setTitleText("", data.status, 0, true);

    }

    @OnClick({R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_next:

                InsureInfoActivity.invoke(this);
                break;
        }
    }



    private class PageData {
        public String img;
        public String carnumber;
        public String companyname;
        public String hbdesc;
        public String status;
    }

}
