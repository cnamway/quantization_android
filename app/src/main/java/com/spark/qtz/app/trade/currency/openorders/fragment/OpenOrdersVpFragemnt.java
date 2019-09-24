package com.spark.qtz.app.trade.currency.openorders.fragment;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxj.xpopup.XPopup;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.app.trade.currency.adapter.OpenOrdersAdapter;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.databinding.FragmentOpenOrdersVpBinding;
import com.spark.qtz.ui.RvLoadMoreView;
import com.spark.qtz.ui.popup.CancelOrderPopup;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.modulespot.pojo.OpenOrdersResult;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.base.BaseFragment;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.utils.LogUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/13
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class OpenOrdersVpFragemnt extends BaseFragment<FragmentOpenOrdersVpBinding, OpenOrdersVpViewModel> {
    private static final String TYPE = "type";
    private static final String SYMBOL = "symbol";

    private int pageIndex = 1;
    private static final int PAGE_SIZE = 5;
    private List<OpenOrdersResult.DataBean.ListBean> openOrdersResultList = new ArrayList<>();
    private OpenOrdersAdapter mOpenOrdersAdapter;
    private String symbol, slide = "";

    /**
     * 0 - 当前委托  1- 历史委托
     *
     * @param adType
     * @return
     */
    public static OpenOrdersVpFragemnt newInstance(int adType, String symbol) {
        OpenOrdersVpFragemnt openOrdersVpFragemnt = new OpenOrdersVpFragemnt();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, adType);
        bundle.putString(SYMBOL, symbol);
        openOrdersVpFragemnt.setArguments(bundle);
        return openOrdersVpFragemnt;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_open_orders_vp;
    }

    @Override
    public int initVariableId() {
        return BR.openOrdersVpViewModel;
    }

    @Override
    public void initView() {
        mOpenOrdersAdapter = new OpenOrdersAdapter(openOrdersResultList);
        binding.rvOpenOrdersType.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvOpenOrdersType.setAdapter(mOpenOrdersAdapter);
        mOpenOrdersAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                switch (getArguments().getInt(TYPE)) {
                    case 0:
                        new XPopup.Builder(getActivity())
                                .asCustom(new CancelOrderPopup(getActivity(), openOrdersResultList.get(position)))
                                .show();
                        break;
                    case 1:
                        if (openOrdersResultList.get(position).getStatus() == 5) {
                            ARouter.getInstance().build(ARouterPath.ACTIVITY_TRADE_COMMISION_DETAILS)
                                    .withParcelable("commissionDetails", openOrdersResultList.get(position))
                                    .navigation();
                        }
                        break;
                }
            }
        });

        //下拉刷新
        binding.swipeLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.base));
        binding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        //上拉加载
        mOpenOrdersAdapter.setLoadMoreView(new RvLoadMoreView());
        mOpenOrdersAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, binding.rvOpenOrdersType);

        symbol = getArguments().getString(SYMBOL);
    }

    @Override
    public void loadLazyData() {
        refresh();
    }


    @Override
    public void initViewObservable() {
        viewModel.uc.orderStatue.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    refresh();
                }
            }
        });

        viewModel.uc.isRefresh.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean){
                    refresh();
                }
            }
        });
    }

    public void filter(String symbol, String slide) {
        this.symbol = symbol;
        this.slide = slide;
        refresh();
    }

    /**
     * 下拉刷新
     */
    private void refresh() {
        binding.swipeLayout.setRefreshing(true);

        pageIndex = 1;
        mOpenOrdersAdapter.setEnableLoadMore(false);
        viewModel.findOpenOrdersType(getArguments().getInt(TYPE), pageIndex, symbol, slide, new OnRequestListener<OpenOrdersResult>() {
            @Override
            public void onSuccess(OpenOrdersResult openOrdersResult) {
                openOrdersResultList.clear();
                openOrdersResultList.addAll(openOrdersResult.getData().getList());
                setData(true, openOrdersResultList);
                mOpenOrdersAdapter.setEnableLoadMore(true);
                binding.swipeLayout.setRefreshing(false);
            }

            @Override
            public void onFail(String message) {
                Toasty.showError(message);
                mOpenOrdersAdapter.setEnableLoadMore(true);
                binding.swipeLayout.setRefreshing(false);
            }
        });
    }

    /**
     * 上拉加载
     */
    private void loadMore() {
        LogUtils.e("loadMore", pageIndex);
        viewModel.findOpenOrdersType(getArguments().getInt(TYPE), pageIndex, symbol, slide, new OnRequestListener<OpenOrdersResult>() {
            @Override
            public void onSuccess(OpenOrdersResult openOrdersResult) {
                boolean isRefresh = pageIndex == 1;
                openOrdersResultList.addAll(openOrdersResult.getData().getList());
                setData(isRefresh, openOrdersResult.getData().getList());
            }

            @Override
            public void onFail(String message) {
                mOpenOrdersAdapter.loadMoreFail();
            }
        });
    }

    /**
     * * @param isRefresh
     *
     * @param data
     */
    private void setData(boolean isRefresh, List data) {
        pageIndex++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mOpenOrdersAdapter.setNewData(data);
            if (size == 0) {
                mOpenOrdersAdapter.setEmptyView(R.layout.view_rv_empty, binding.rvOpenOrdersType);
            }
        } else {
            if (size > 0) {
                mOpenOrdersAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mOpenOrdersAdapter.loadMoreEnd(isRefresh);
        } else {
            mOpenOrdersAdapter.loadMoreComplete();
        }
    }
}
