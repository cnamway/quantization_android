package com.spark.qtz.app.trade.contract.deal;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.modulecfd.pojo.CfdDealResult;
import com.spark.qtz.App;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.app.trade.contract.adapter.CfdDealAdapter;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.databinding.FragmentCfdDealBinding;
import com.spark.qtz.ui.toast.Toasty;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.base.BaseFragment;
import me.spark.mvvm.http.impl.OnRequestListener;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-06-27
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CfdDealFragment extends BaseFragment<FragmentCfdDealBinding, CfdDealViewModel> {
    private static final String SYMBOL = "symbol";
    private int pageNo = 1;
    private String mSymbol;

    private CfdDealAdapter mCfdDealAdapter;
    private List<CfdDealResult.DataBean.RecordsBean> mDataBeanList = new ArrayList<>();

    public static CfdDealFragment newInstance(String quotesType) {
        CfdDealFragment cfdDealFragment = new CfdDealFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SYMBOL, quotesType);
        cfdDealFragment.setArguments(bundle);
        return cfdDealFragment;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_cfd_deal;
    }

    @Override
    public void initView() {
        mSymbol = getArguments().getString(SYMBOL);

        mCfdDealAdapter = new CfdDealAdapter(mDataBeanList);
        binding.rvCfdDeal.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvCfdDeal.setAdapter(mCfdDealAdapter);
        mCfdDealAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                ARouter.getInstance().build(ARouterPath.ACTIVITY_TRADE_CFD_DETAILS)
                        .withInt("type", 2)
                        .withParcelable("dealBean", mDataBeanList.get(position))
                        .navigation();
            }
        });
    }

    @Override
    public int initVariableId() {
        return BR.cfdDealViewModel;
    }

    @Override
    public void loadLazyData() {
        if (App.getInstance().isAppLogin()) {
            refresh();
        } else {
            initLogin();
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

        viewModel.uc.loadMore.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                loadMore();
            }
        });

        //登录状态
        viewModel.uc.mLoginStatue.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    refresh();
                } else {
                    initLogin();
                }
            }
        });

    }


    /**
     * 下拉刷新
     */
    private void refresh() {
        if (viewModel.isLoadDate) return;
        pageNo = 1;
        viewModel.loadDealData(mSymbol, pageNo, new OnRequestListener<CfdDealResult>() {
            @Override
            public void onSuccess(CfdDealResult cfdDealResult) {
                if (!binding.root.isContentCurrentState()) {
                    binding.root.showContent();
                }
                mDataBeanList.clear();
                mDataBeanList.addAll(cfdDealResult.getData().getRecords());
                setData(true, mDataBeanList);
            }

            @Override
            public void onFail(String message) {
                Toasty.showError(message);
            }
        });
    }

    /**
     * 上拉加载
     */
    private void loadMore() {
        if (viewModel.isLoadDate) return;
        viewModel.loadDealData(mSymbol, pageNo, new OnRequestListener<CfdDealResult>() {
            @Override
            public void onSuccess(CfdDealResult cfdDealResult) {
                boolean isRefresh = pageNo == 1;
                mDataBeanList.addAll(cfdDealResult.getData().getRecords());
                setData(isRefresh, cfdDealResult.getData().getRecords());
            }

            @Override
            public void onFail(String message) {
                mCfdDealAdapter.loadMoreFail();
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
            mCfdDealAdapter.setNewData(data);
            if (size == 0) {
                mCfdDealAdapter.setEmptyView(R.layout.view_rv_empty, binding.rvCfdDeal);
            }
        } else {
            if (size > 0) {
                mCfdDealAdapter.addData(data);
            }
        }
        viewModel.isLoadDate = false;
    }


    private void initLogin() {
        binding.root.showError(R.drawable.svg_no_data, getString(R.string.no_login), getString(R.string.log2view), getString(R.string.login), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_LOGIN)
                        .navigation();
            }
        });
    }
}
