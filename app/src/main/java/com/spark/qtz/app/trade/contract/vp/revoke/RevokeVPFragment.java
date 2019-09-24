package com.spark.qtz.app.trade.contract.vp.revoke;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.modulecfd.pojo.CfdRevokeResult;
import com.spark.qtz.App;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.app.trade.contract.adapter.CfdRevokeAdapter;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.databinding.FragmentRevokeVpBinding;
import com.spark.qtz.ui.toast.Toasty;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.base.BaseFragment;
import me.spark.mvvm.http.impl.OnRequestListener;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-07-29
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class RevokeVPFragment extends BaseFragment<FragmentRevokeVpBinding, RevokeVPViewModel> {
    private static final String SYMBOL = "symbol";
    private static final String SCREENNAME = "screenname";
    private int pageNo = 1;
    private String mSymbol;

    private CfdRevokeAdapter mCfdRevokeAdapter;
    private List<CfdRevokeResult.DataBean.RecordsBean> mDataBeanList = new ArrayList<>();

    public static RevokeVPFragment newInstance(String quotesType,String screenName) {
        RevokeVPFragment revokeVPFragment = new RevokeVPFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SYMBOL, quotesType);
        bundle.putString(SCREENNAME, screenName);
        revokeVPFragment.setArguments(bundle);
        return revokeVPFragment;
    }


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_revoke_vp;
    }

    @Override
    public int initVariableId() {
        return BR.revokeVPViewModel;
    }

    @Override
    public void initView() {
        mSymbol = getArguments().getString(SYMBOL);
        viewModel.initSreenName(getArguments().getString(SCREENNAME));

        mCfdRevokeAdapter = new CfdRevokeAdapter(mDataBeanList);
        binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rv.setAdapter(mCfdRevokeAdapter);
        mCfdRevokeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                ARouter.getInstance().build(ARouterPath.ACTIVITY_TRADE_CFD_DETAILS)
                        .withInt("type", 3)
                        .withParcelable("revokeBean", mDataBeanList.get(position))
                        .navigation();
            }
        });
        mCfdRevokeAdapter.setEmptyView(R.layout.view_rv_empty, binding.rv);
    }

    @Override
    public void initData() {
        if (App.getInstance().isAppLogin()) {
            refresh();
        }
    }

    @Override
    public void initViewObservable() {
        //刷新
        viewModel.uc.mRefresh.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String symbol) {
                mSymbol = symbol;
                refresh();
            }
        });

        //登录状态
        viewModel.uc.mLoginStatue.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    refresh();
                }
            }
        });

        viewModel.uc.loadMore.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                loadMore();
            }
        });
    }

    /**
     * 下拉刷新
     */
    private void refresh() {
        if (viewModel.isLoadDate) return;
        pageNo = 1;
        viewModel.loadRevokeData(mSymbol, pageNo, new OnRequestListener<CfdRevokeResult>() {
            @Override
            public void onSuccess(CfdRevokeResult cfdRevokeResult) {
                mDataBeanList.clear();
                for (CfdRevokeResult.DataBean.RecordsBean record : cfdRevokeResult.getData().getRecords()) {
                    if (record.getSymbol().equals(mSymbol) && !mDataBeanList.contains(record)) {
                        record.setSymbol(viewModel.mScreenName);
                        mDataBeanList.add(record);
                    }
                }
                setData(true, mDataBeanList);
            }

            @Override
            public void onFail(String message) {
                viewModel.isLoadDate = false;
                viewModel.isWsReceive = false;
                Toasty.showError(message);
            }
        });
    }

    /**
     * 上拉加载
     */
    private void loadMore() {
        if (viewModel.isLoadDate) return;
        viewModel.loadRevokeData(mSymbol, pageNo, new OnRequestListener<CfdRevokeResult>() {
            @Override
            public void onSuccess(CfdRevokeResult cfdRevokeResult) {
                boolean isRefresh = pageNo == 1;
                for (CfdRevokeResult.DataBean.RecordsBean record : cfdRevokeResult.getData().getRecords()) {
                    if (record.getSymbol().equals(mSymbol) && !mDataBeanList.contains(record)) {
                        record.setSymbol(viewModel.mScreenName);
                        mDataBeanList.add(record);
                    }
                }
                setData(isRefresh, cfdRevokeResult.getData().getRecords());
            }

            @Override
            public void onFail(String message) {
                viewModel.isLoadDate = false;
                viewModel.isWsReceive = false;
                mCfdRevokeAdapter.loadMoreFail();
            }
        });
    }

    /**
     * * @param isRefresh
     *
     * @param data
     */
    private void setData(boolean isRefresh, List data) {
        final int size = data == null ? 0 : data.size();
        if (size > 0) pageNo++;
        if (isRefresh) {
            mCfdRevokeAdapter.setNewData(data);
            if (size == 0) {
                mCfdRevokeAdapter.setEmptyView(R.layout.view_rv_empty, binding.rv);
            }
        } else {
            if (size > 0) {
                mCfdRevokeAdapter.addData(data);
            }
        }
        viewModel.isLoadDate = false;
        viewModel.isWsReceive = false;
    }
}
