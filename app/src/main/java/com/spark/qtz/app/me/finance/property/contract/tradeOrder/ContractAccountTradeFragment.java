package com.spark.qtz.app.me.finance.property.contract.tradeOrder;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.modulecfd.pojo.CfdAllThumbResult;
import com.example.modulecfd.pojo.CfdTradeOrderResult;
import com.spark.qtz.App;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.app.me.finance.property.contract.adapter.CfdTradeOrderAdapter;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.databinding.FragmentContractAccountTradeBinding;
import com.spark.qtz.ui.RvLoadMoreView;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.wsclient.pojo.CfdThumbBean;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.base.BaseFragment;
import me.spark.mvvm.http.impl.OnRequestListener;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-07-03
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ContractAccountTradeFragment extends BaseFragment<FragmentContractAccountTradeBinding, ContractAccountTradeViewModel> {
    private int pageNo = 1;
    private static final int PAGE_SIZE = 1;
    private CfdTradeOrderAdapter mCfdTradeOrderAdapter;
    private List<CfdTradeOrderResult.DataBean.RecordsBean> mDataBeanList = new ArrayList<>();

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_contract_account_trade;
    }

    @Override
    public int initVariableId() {
        return BR.contractAccountTradeViewModel;
    }


    @Override
    public void initView() {
        mCfdTradeOrderAdapter = new CfdTradeOrderAdapter(mDataBeanList);
        binding.rvTrade.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvTrade.setAdapter(mCfdTradeOrderAdapter);
        mCfdTradeOrderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                ARouter.getInstance().build(ARouterPath.ACTIVITY_TRADE_CFD_DETAILS)
                        .withInt("type", 4)
                        .withParcelable("accountTradeBean", mDataBeanList.get(position))
                        .navigation();
            }
        });

        //上拉加载
        mCfdTradeOrderAdapter.setLoadMoreView(new RvLoadMoreView());
        mCfdTradeOrderAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (App.getInstance().isAppLogin()) {
                    loadMore();
                }
            }
        }, binding.rvTrade);
    }

    @Override
    public void loadLazyData() {
        refresh();
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.currentCfdAllThumbResultObserve.observe(this, new Observer<CfdAllThumbResult>() {
            @Override
            public void onChanged(@Nullable CfdAllThumbResult cfdAllThumbResult) {
                for (int i = 0; i < mDataBeanList.size(); i++) {
                    for (CfdAllThumbResult.DataBean dataBean : cfdAllThumbResult.getData()) {
                        if (dataBean.getScreenName().equals(mDataBeanList.get(i).getSymbol())
                                && mDataBeanList.get(i).getIntType() == 0) {
                            mDataBeanList.get(i).setCurrentPrice(dataBean.getClose());
                            break;
                        }
                    }
                }
                mCfdTradeOrderAdapter.notifyDataSetChanged();
            }
        });

        viewModel.uc.mCfdThumbBeanSingleLiveEvent.observe(this, new Observer<CfdThumbBean>() {
            @Override
            public void onChanged(@Nullable CfdThumbBean cfdThumbBean) {
                for (int i = 0; i < mDataBeanList.size(); i++) {
                    for (CfdThumbBean.DateBean dateBean : cfdThumbBean.getDate()) {
                        if (mDataBeanList.get(i).getSymbol().startsWith(dateBean.getSymbol())
                                && mDataBeanList.get(i).getIntType() == 0) {
                            mDataBeanList.get(i).setCurrentPrice(dateBean.getClose());
                            break;
                        }
                    }
                }
                mCfdTradeOrderAdapter.notifyDataSetChanged();
            }
        });

        viewModel.uc.isRefresh.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    refresh();
                }
            }
        });
    }

    private void refresh() {
        binding.root.showLoading();
        pageNo = 1;
        viewModel.loadTradeDate(pageNo, new OnRequestListener<CfdTradeOrderResult>() {
            @Override
            public void onSuccess(CfdTradeOrderResult cfdTradeOrderResult) {
                if (!binding.root.isContentCurrentState()) {
                    binding.root.showContent();
                }
                mDataBeanList.clear();
                mDataBeanList.addAll(cfdTradeOrderResult.getData().getRecords());
                setData(true, mDataBeanList);
                mCfdTradeOrderAdapter.setEnableLoadMore(true);
            }

            @Override
            public void onFail(String message) {
                Toasty.showError(message);
                viewModel.isLoadDate = false;
                mCfdTradeOrderAdapter.setEnableLoadMore(true);
            }
        });
    }

    /**
     * 上拉加载
     */
    private void loadMore() {
        viewModel.loadTradeDate(pageNo, new OnRequestListener<CfdTradeOrderResult>() {
            @Override
            public void onSuccess(CfdTradeOrderResult cfdTradeOrderResult) {
                boolean isRefresh = pageNo == 1;
                mDataBeanList.addAll(cfdTradeOrderResult.getData().getRecords());
                setData(isRefresh, cfdTradeOrderResult.getData().getRecords());
            }

            @Override
            public void onFail(String message) {
                viewModel.isLoadDate = false;
                mCfdTradeOrderAdapter.loadMoreFail();
            }
        });
    }

    /**
     * * @param isRefresh
     *
     * @param data
     */
    private void setData(boolean isRefresh, List data) {
        pageNo++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mCfdTradeOrderAdapter.setNewData(data);
            if (size == 0) {
                mCfdTradeOrderAdapter.setEmptyView(R.layout.view_rv_empty, binding.rvTrade);
            }
        } else {
            if (size > 0) {
                mCfdTradeOrderAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mCfdTradeOrderAdapter.loadMoreEnd(isRefresh);
        } else {
            mCfdTradeOrderAdapter.loadMoreComplete();
        }

        viewModel.isLoadDate = false;
    }
}
