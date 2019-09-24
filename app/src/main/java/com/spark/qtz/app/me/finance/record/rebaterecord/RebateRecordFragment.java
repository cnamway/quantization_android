package com.spark.qtz.app.me.finance.record.rebaterecord;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.app.me.finance.record.adapter.RebateRecordAdapter;
import com.spark.qtz.databinding.FragmentRebateRecordBinding;
import com.spark.qtz.ui.RvLoadMoreView;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.ucclient.pojo.CommissionRecordResult;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.base.BaseFragment;
import me.spark.mvvm.http.impl.OnRequestListener;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-07-24
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class RebateRecordFragment extends BaseFragment<FragmentRebateRecordBinding, RebateRecordViewModel> {
    private RebateRecordAdapter mRebateRecordAdapter;
    private List<CommissionRecordResult.DataBean.RecordsBean> dataBeanList = new ArrayList<>();
    private int pageIndex = 1;
    private static final int PAGE_SIZE = 1;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_rebate_record;
    }

    @Override
    public int initVariableId() {
        return BR.rebateRecordViewModel;
    }

    @Override
    public void initView() {
        mRebateRecordAdapter = new RebateRecordAdapter(dataBeanList);
        binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rv.setAdapter(mRebateRecordAdapter);
        //下拉刷新
        binding.swipeLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.base));
        binding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        //上拉加载
        mRebateRecordAdapter.setLoadMoreView(new RvLoadMoreView());
        mRebateRecordAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, binding.rv);
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

    @Override
    public void loadLazyData() {
        refresh();
    }

    private void refresh() {
        binding.swipeLayout.setRefreshing(true);
        pageIndex = 1;
        viewModel.getDate(pageIndex, new OnRequestListener<CommissionRecordResult>() {
            @Override
            public void onSuccess(CommissionRecordResult commissionRecordResult) {
                dataBeanList.clear();
                dataBeanList.addAll(commissionRecordResult.getData().getRecords());
                setData(true, commissionRecordResult.getData().getRecords());
                mRebateRecordAdapter.setEnableLoadMore(true);
                binding.swipeLayout.setRefreshing(false);
            }

            @Override
            public void onFail(String message) {
                Toasty.showError(message);
                mRebateRecordAdapter.setEnableLoadMore(true);
                binding.swipeLayout.setRefreshing(false);
            }
        });
    }

    private void loadMore() {
        viewModel.getDate(pageIndex, new OnRequestListener<CommissionRecordResult>() {
            @Override
            public void onSuccess(CommissionRecordResult commissionRecordResult) {
                boolean isRefresh = pageIndex == 1;
                dataBeanList.addAll(commissionRecordResult.getData().getRecords());
                setData(isRefresh, commissionRecordResult.getData().getRecords());
            }

            @Override
            public void onFail(String message) {
                Toasty.showError(message);
                binding.swipeLayout.setRefreshing(false);
            }
        });
    }

    private void setData(boolean isRefresh, List data) {
        pageIndex++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mRebateRecordAdapter.setNewData(data);
            if (size == 0) {
                mRebateRecordAdapter.setEmptyView(R.layout.view_rv_empty, binding.rv);
            }
        } else {
            if (size > 0) {
                mRebateRecordAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mRebateRecordAdapter.loadMoreEnd(isRefresh);
        } else {
            mRebateRecordAdapter.loadMoreComplete();
        }
    }
}
