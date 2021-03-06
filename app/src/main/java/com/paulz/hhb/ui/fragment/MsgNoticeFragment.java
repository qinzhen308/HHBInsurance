package com.paulz.hhb.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.paulz.hhb.R;
import com.paulz.hhb.adapter.AccountOrderAdapter;
import com.paulz.hhb.adapter.MsgNoticeAdapter;
import com.paulz.hhb.common.APIUtil;
import com.paulz.hhb.common.AppUrls;
import com.paulz.hhb.controller.LoadStateController;
import com.paulz.hhb.httputil.ParamBuilder;
import com.paulz.hhb.model.wrapper.BeanWraper;
import com.paulz.hhb.model.wrapper.MsgWraper;
import com.paulz.hhb.model.wrapper.OrderWraper;
import com.paulz.hhb.utils.AppUtil;
import com.paulz.hhb.view.pulltorefresh.PullListView;
import com.paulz.hhb.view.pulltorefresh.PullToRefreshBase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pualbeben on 17/11/1.
 */

public class MsgNoticeFragment extends BaseListFragment implements LoadStateController.OnLoadErrorListener, PullToRefreshBase.OnRefreshListener {


    MsgNoticeAdapter mAdapter;

    private String tag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = getArguments().getString("tag");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setView(inflater, R.layout.fragment_msg_notice, false);
        mLoadStateController = new LoadStateController(getActivity(), (ViewGroup) baseLayout.findViewById(R.id.load_state_container));
        hasLoadingState = true;
        ButterKnife.bind(this, baseLayout);
        return baseLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setListener();
        initData(false);
    }

    @Override
    public void heavyBuz() {

    }

    private void setListener() {
        mListView.setOnScrollListener(new MyOnScrollListener());
        mLoadStateController.setOnLoadErrorListener(this);
        mPullListView.setOnRefreshListener(this);

    }


    private void initView() {
        mPullListView = (PullListView) baseLayout.findViewById(R.id.listview);
        mListView = mPullListView.getRefreshableView();
        mListView.setDividerHeight(15);
        mAdapter = new MsgNoticeAdapter(getActivity());
        mListView.setAdapter(mAdapter);
    }

//    @OnClick({R.id.btn_add_customer})
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_add_customer:
//                AddCustomerActivity.invoke(getActivity());
//                break;
//            default:
//                break;
//        }
//
//    }


    @Override
    protected BeanWraper newBeanWraper() {
        return new MsgWraper();
    }

    private void initData(boolean isRefresh) {
        if (!isRefresh) {
            showLoading();
        }

        ParamBuilder params = new ParamBuilder();
        params.append("category", tag);
        if (isRefresh) {
            immediateLoadData(APIUtil.parseGetUrlHasMethod(params.getParamList(), AppUrls.getInstance().URL_MSG_LIST), MsgWraper.class);
        } else {
            reLoadData(APIUtil.parseGetUrlHasMethod(params.getParamList(), AppUrls.getInstance().URL_MSG_LIST), MsgWraper.class);
        }
    }

    @Override
    protected void handlerData(List allData, List currentData, boolean isLastPage) {
        // TODO Auto-generated method stub
        mPullListView.onRefreshComplete();
        if (AppUtil.isEmpty(allData)) {
            showNodata();
        } else {
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
        if (!isLoading()) {
            initData(true);
        }
    }

    @Override
    public void onAgainRefresh() {
        // TODO Auto-generated method stub
        initData(false);
    }

    public void updateAdapter() {
        mAdapter.notifyDataSetChanged();
    }


    public static MsgNoticeFragment createInstance(String tag) {
        MsgNoticeFragment fragment = new MsgNoticeFragment();
        Bundle data = new Bundle();
        data.putString("tag", tag);
        fragment.setArguments(data);
        return fragment;
    }



}
