package com.paulz.hhb.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.core.framework.net.NetworkWorker;
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

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pualbeben on 17/5/21.
 * 我的奖金
 */

public class MyBounsActivity extends BaseActivity {


    @BindView(R.id.tv_bouns)
    TextView tvBouns;
    @BindView(R.id.layout_bouns_detail)
    TextView layoutBounsDetail;
    @BindView(R.id.tv_bouns_all)
    TextView tvBounsAll;
    @BindView(R.id.layout_bouns_all)
    RelativeLayout layoutBounsAll;
    @BindView(R.id.tv_bouns_withdraw)
    TextView tvBounsWithdraw;
    @BindView(R.id.layout_bouns_withdraw)
    RelativeLayout layoutBounsWithdraw;
    @BindView(R.id.tv_bouns_can_withdraw)
    TextView tvBounsCanWithdraw;
    @BindView(R.id.layout_bouns_can_withdraw)
    RelativeLayout layoutBounsCanWithdraw;
    @BindView(R.id.tv_bouns_verify)
    TextView tvBounsVerify;
    @BindView(R.id.layout_bouns_verify)
    RelativeLayout layoutBounsVerify;
    @BindView(R.id.btn_withdraw)
    TextView btnWithdraw;
    @BindView(R.id.btn_history)
    TextView btnHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        loadData();
    }

    private void initView() {
        setActiviyContextView(R.layout.activity_my_bouns, false, true);
        setTitleText("", "我的佣金", 0, true);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_history,R.id.btn_withdraw,R.id.layout_bouns_all,R.id.layout_bouns_withdraw,R.id.layout_bouns_can_withdraw,R.id.layout_bouns_verify,R.id.layout_bouns_detail})
    public void otherClick(View view) {
        switch (view.getId()) {
            case R.id.btn_withdraw:
                WithdrawApplyActivity.invoke(this);
                break;
            case R.id.btn_history:
                WithdrawHistoryActivity.invoke(this);
                break;
            case R.id.layout_bouns_all:

                break;
            case R.id.layout_bouns_withdraw:

                break;
            case R.id.layout_bouns_can_withdraw:

                break;
            case R.id.layout_bouns_verify:

                break;
            case R.id.layout_bouns_detail:
                BounsRecordActivity.invoke(this);
                break;
        }

    }

    private void handleData(PageData data){
        tvBouns.setText(""+data.todaymoney);
        tvBounsAll.setText("￥"+data.allmoney);
        tvBounsCanWithdraw.setText("￥"+data.atming);
        tvBounsWithdraw.setText("￥"+data.atmed);
        tvBounsVerify.setText("￥"+data.approvemoney);
        if(data.atmstop==1){
            btnWithdraw.setVisibility(View.GONE);
        }

    }


    private void loadData(){

        DialogUtil.showDialog(lodDialog);
        ParamBuilder params = new ParamBuilder();
        NetworkWorker.getInstance().get(APIUtil.parseGetUrlHasMethod(params.getParamList(), AppUrls.getInstance().URL_BOUNS), new NetworkWorker.ICallback() {
            @Override
            public void onResponse(int status, String result) {
                if (!isFinishing()) DialogUtil.dismissDialog(lodDialog);
                if (status == 200) {
                    BaseObject<PageData> object = GsonParser.getInstance().parseToObj(result, PageData.class);
                    if (object != null && object.status == BaseObject.STATUS_OK && object.data != null) {
                        handleData(object.data);
                    } else {
                        AppUtil.showToast(getApplicationContext(),object!=null?object.msg:"加载失败");

                    }
                }else {
                    AppUtil.showToast(getApplicationContext(),"加载失败");

                }
            }
        });
    }

    public static void invoke(Context context) {
        Intent intent = new Intent(context, MyBounsActivity.class);
        context.startActivity(intent);
    }

    private class PageData{
        float allmoney;
        float todaymoney;
        float approvemoney;
        float atming;
        float atmed;
        int atmstop;

    }

}
