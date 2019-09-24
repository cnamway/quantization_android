package com.spark.qtz.app.me.finance.property.contract.transOrder;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.spark.acclient.pojo.CoinTransDetailsResult;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.app.me.finance.property.contract.adapter.CfdTransOrderAdapter;
import com.spark.qtz.databinding.FragmentContractAccountTransBinding;
import com.spark.qtz.ui.RvLoadMoreView;
import com.spark.qtz.ui.toast.Toasty;

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
public class ContractAccountTransFragment extends BaseFragment<FragmentContractAccountTransBinding, ContractAccountTransViewModel> {
    private int pageNo = 1;
    private CfdTransOrderAdapter mCfdTransOrderAdapter;
    private List<CoinTransDetailsResult.DataBean.RecordsBean> mDataBeanList = new ArrayList<>();
    private static final int PAGE_SIZE = 1;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_contract_account_trans;
    }

    @Override
    public int initVariableId() {
        return BR.contractAccountTransViewModel;
    }

    @Override
    public void initView() {
        mCfdTransOrderAdapter = new CfdTransOrderAdapter(mDataBeanList);
        binding.rvTrans.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvTrans.setAdapter(mCfdTransOrderAdapter);

        //上拉加载
        mCfdTransOrderAdapter.setLoadMoreView(new RvLoadMoreView());
        mCfdTransOrderAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, binding.rvTrans);
    }

    @Override
    public void loadLazyData() {
        refresh();
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.isRefresh.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean){
                    refresh();
                }
            }
        });
    }

    private void refresh() {
        binding.root.showLoading();
        pageNo = 1;
        viewModel.loadTradeDate(pageNo, new OnRequestListener<CoinTransDetailsResult>() {
            @Override
            public void onSuccess(CoinTransDetailsResult coinTransDetailsResult) {
                if (!binding.root.isContentCurrentState()) {
                    binding.root.showContent();
                }
                mDataBeanList.clear();
                mDataBeanList.addAll(coinTransDetailsResult.getData().getRecords());
                setData(true, mDataBeanList);
                mCfdTransOrderAdapter.setEnableLoadMore(true);
            }

            @Override
            public void onFail(String message) {
                Toasty.showError(message);
                mCfdTransOrderAdapter.setEnableLoadMore(true);
            }
        });
    }

    /**
     * 上拉加载
     */
    private void loadMore() {
        viewModel.loadTradeDate(pageNo, new OnRequestListener<CoinTransDetailsResult>() {
            @Override
            public void onSuccess(CoinTransDetailsResult coinTransDetailsResult) {
                boolean isRefresh = pageNo == 1;
                mDataBeanList.addAll(coinTransDetailsResult.getData().getRecords());
                setData(isRefresh, coinTransDetailsResult.getData().getRecords());
            }

            @Override
            public void onFail(String message) {
                mCfdTransOrderAdapter.loadMoreFail();
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
            mCfdTransOrderAdapter.setNewData(data);
            if (size == 0) {
                mCfdTransOrderAdapter.setEmptyView(R.layout.view_rv_empty, binding.rvTrans);
            }
        } else {
            if (size > 0) {
                mCfdTransOrderAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mCfdTransOrderAdapter.loadMoreEnd(isRefresh);
        } else {
            mCfdTransOrderAdapter.loadMoreComplete();
        }

    }
}
