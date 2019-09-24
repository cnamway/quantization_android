package com.spark.qtz.app.trade.contract.vp.commission;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.modulecfd.pojo.CfdCommissionResult;
import com.lxj.xpopup.XPopup;
import com.spark.qtz.App;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.app.trade.contract.adapter.CfdCommissionAdapter;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.databinding.FragmentCommissionVpBinding;
import com.spark.qtz.ui.popup.PositionClosePopup;
import com.spark.qtz.ui.popup.ProfitLossSetPopup;
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
public class CommissionVPFragment extends BaseFragment<FragmentCommissionVpBinding, CommissionVPViewModel> {
    private static final String SYMBOL = "symbol";
    private static final String SCREENNAME = "screenname";
    private static final String CURRENT_CLOSE = "currentClose";

    private CfdCommissionAdapter mCfdCommissionAdapter;
    private List<CfdCommissionResult.DataBean.RecordsBean> mDataBeanList = new ArrayList<>();

    private String mSymbol;
    private int pageNo = 1;

    public static CommissionVPFragment newInstance(String quotesType,String screenName, double currentClose) {
        CommissionVPFragment commissionVPFragment = new CommissionVPFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SYMBOL, quotesType);
        bundle.putString(SCREENNAME, screenName);
        bundle.putDouble(CURRENT_CLOSE, currentClose);
        commissionVPFragment.setArguments(bundle);
        return commissionVPFragment;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_commission_vp;
    }

    @Override
    public int initVariableId() {
        return BR.commissionVPViewModel;
    }

    @Override
    public void initView() {
        viewModel.mCurrentClose = getArguments().getDouble(CURRENT_CLOSE);
        mSymbol = getArguments().getString(SYMBOL);
        viewModel.initSreenName(getArguments().getString(SCREENNAME));

        mCfdCommissionAdapter = new CfdCommissionAdapter(mDataBeanList);
        binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rv.setAdapter(mCfdCommissionAdapter);
        mCfdCommissionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                ARouter.getInstance().build(ARouterPath.ACTIVITY_TRADE_CFD_DETAILS)
                        .withInt("type", 1)
                        .withParcelable("commissionBean", mDataBeanList.get(position))
                        .navigation();
            }
        });
        mCfdCommissionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    //设置止盈止损
                    case R.id.setProfitAndLoss:
                        new XPopup.Builder(getContext())
                                .asCustom(new ProfitLossSetPopup(getContext(), 1, mDataBeanList.get(position)))
                                .show();
                        break;
                    //撤单
                    case R.id.withDrawal:
                        new XPopup.Builder(getContext())
                                .asCustom(new PositionClosePopup(getContext(), 1, mDataBeanList.get(position)))
                                .show();
                        break;
                }
            }
        });
        mCfdCommissionAdapter.setEmptyView(R.layout.view_rv_empty, binding.rv);
    }

    @Override
    public void initData() {
        if (App.getInstance().isAppLogin()) {
            refresh();
        }
    }

    @Override
    public void initViewObservable() {
        //最新价刷新监听
        viewModel.uc.mCurrentCloseObserve.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(@Nullable Double aDouble) {
                if (mDataBeanList.isEmpty() || viewModel.isLoadDate) return;
                notifyAdapter();
            }
        });

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
     * 监听数据 刷新布局
     */
    private void notifyAdapter() {
        for (CfdCommissionResult.DataBean.RecordsBean recordsBean : mDataBeanList) {
            recordsBean.setCurrentPrice(viewModel.mCurrentClose);
        }
        mCfdCommissionAdapter.notifyDataSetChanged();
    }


    /**
     * 下拉刷新
     */
    private void refresh() {
        if (viewModel.isLoadDate) return;
        pageNo = 1;
        viewModel.loadCommissionData(mSymbol, pageNo, new OnRequestListener<CfdCommissionResult>() {
            @Override
            public void onSuccess(CfdCommissionResult cfdCommissionResult) {
                mDataBeanList.clear();
                for (CfdCommissionResult.DataBean.RecordsBean record : cfdCommissionResult.getData().getRecords()) {
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
        viewModel.loadCommissionData(mSymbol, pageNo, new OnRequestListener<CfdCommissionResult>() {
            @Override
            public void onSuccess(CfdCommissionResult cfdCommissionResult) {
                boolean isRefresh = pageNo == 1;
                for (CfdCommissionResult.DataBean.RecordsBean record : cfdCommissionResult.getData().getRecords()) {
                    if (record.getSymbol().equals(mSymbol) && !mDataBeanList.contains(record)) {
                        record.setSymbol(viewModel.mScreenName);
                        mDataBeanList.add(record);
                    }
                }
                setData(isRefresh, cfdCommissionResult.getData().getRecords());
            }

            @Override
            public void onFail(String message) {
                viewModel.isLoadDate = false;
                viewModel.isWsReceive = false;
                mCfdCommissionAdapter.loadMoreFail();
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
            mCfdCommissionAdapter.setNewData(data);
            if (size == 0) {
                mCfdCommissionAdapter.setEmptyView(R.layout.view_rv_empty, binding.rv);
            }
        } else {
            if (size > 0) {
                mCfdCommissionAdapter.addData(data);
            }
        }
        viewModel.isLoadDate = false;
        viewModel.isWsReceive = false;
    }
}
