package com.paulz.hhb.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.core.framework.app.devInfo.ScreenUtil;
import com.paulz.hhb.R;
import com.paulz.hhb.adapter.WithdrawAdapter;
import com.paulz.hhb.base.BaseListActivity;
import com.paulz.hhb.common.APIUtil;
import com.paulz.hhb.common.AppUrls;
import com.paulz.hhb.controller.LoadStateController;
import com.paulz.hhb.httputil.ParamBuilder;
import com.paulz.hhb.model.wrapper.BeanWraper;
import com.paulz.hhb.model.wrapper.WithdrawHistoryWraper;
import com.paulz.hhb.utils.AppUtil;
import com.paulz.hhb.view.pulltorefresh.PullListView;
import com.paulz.hhb.view.pulltorefresh.PullToRefreshBase;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by pualbeben on 17/5/22.
 * 提现记录
 */

public class WithdrawHistoryActivity extends BaseListActivity implements PullToRefreshBase.OnRefreshListener,LoadStateController.OnLoadErrorListener{

    private WithdrawAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActiviyContextView(R.layout.activity_bank_card, true, true);
        setTitleText("", "账户信息", 0, true);
        ButterKnife.bind(this);
        initView();
        setListener();
        initData(false);
    }


    private void initView() {
        mPullListView = (PullListView) findViewById(R.id.listview);
        mPullListView.setMode(-1);
        mListView = mPullListView.getRefreshableView();
        mListView.setDividerHeight(ScreenUtil.dip2px(this, 10));
        mAdapter = new WithdrawAdapter(this);
        mListView.setAdapter(mAdapter);
    }


    private void setListener(){
        mListView.setOnScrollListener(new MyOnScrollListener());
        mLoadStateController.setOnLoadErrorListener(this);
        mPullListView.setOnRefreshListener(this);
    }


    @Override
    protected BeanWraper newBeanWraper() {
        return new WithdrawHistoryWraper();
    }

    private void initData(boolean isRefresh){
        if(!isRefresh){
            showLoading();
        }

        ParamBuilder params=new ParamBuilder();

        if(isRefresh){
            immediateLoadData(APIUtil.parseGetUrlHasMethod(params.getParamList(), AppUrls.getInstance().URL_WITHDRAW_HISTORY), WithdrawHistoryWraper.class);
        }else {
            reLoadData(APIUtil.parseGetUrlHasMethod(params.getParamList(), AppUrls.getInstance().URL_WITHDRAW_HISTORY), WithdrawHistoryWraper.class);
        }
    }


    @Override
    protected void handlerData(List allData, List currentData, boolean isLastPage) {
        // TODO Auto-generated method stub
        mPullListView.onRefreshComplete();
        if(AppUtil.isEmpty(allData)){
            showNodata();
        }else {
            showSuccess();
        }
        mAdapter.setList(allData);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    protected void loadError(String message, Throwable throwable, int page) {
        // TODO Auto-generated method stub
        mPullListView.onRefreshComplete();
        showFailture();
    }

    @Override
    protected void loadTimeOut(String message, Throwable throwable) {
        // TODO Auto-generated method stub
        mPullListView.onRefreshComplete();
        showFailture();
    }

    @Override
    protected void loadNoNet() {
        // TODO Auto-generated method stub
        mPullListView.onRefreshComplete();
        showFailture();
    }

    @Override
    protected void loadServerError() {
        // TODO Auto-generated method stub
        mPullListView.onRefreshComplete();
        showFailture();

    }


    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub
        if(!isLoading()){
            initData(true);
        }
    }

    @Override
    public void onAgainRefresh() {
        // TODO Auto-generated method stub
        initData(false);
    }


    public static void invoke(Context context) {
        Intent intent = new Intent(context, WithdrawHistoryActivity.class);
        context.startActivity(intent);
    }


}
