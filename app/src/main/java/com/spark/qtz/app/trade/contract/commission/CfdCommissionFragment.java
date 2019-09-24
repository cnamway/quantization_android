package com.spark.qtz.app.trade.contract.commission;

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
import com.spark.qtz.databinding.FragmentCfdCommissionBinding;
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
 * 创建日期：2019-06-27
 * 描    述：当前委托
 * 修订历史：
 * ================================================
 */
public class CfdCommissionFragment extends BaseFragment<FragmentCfdCommissionBinding, CfdCommissionViewModel> {
    private static final String SYMBOL = "symbol";
    private static final String CURRENT_CLOSE = "currentClose";

    private CfdCommissionAdapter mCfdCommissionAdapter;
    private List<CfdCommissionResult.DataBean.RecordsBean> mDataBeanList = new ArrayList<>();

    private String mSymbol;
    private int pageNo = 1;

    public static CfdCommissionFragment newInstance(String quotesType, double currentClose) {
        CfdCommissionFragment cfdCommissionFragment = new CfdCommissionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SYMBOL, quotesType);
        bundle.putDouble(CURRENT_CLOSE, currentClose);
        cfdCommissionFragment.setArguments(bundle);
        return cfdCommissionFragment;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_cfd_commission;
    }

    @Override
    public int initVariableId() {
        return BR.cfdCommissionViewModel;
    }

    @Override
    public void initView() {
        viewModel.mCurrentClose = getArguments().getDouble(CURRENT_CLOSE);
        mSymbol = getArguments().getString(SYMBOL);

        mCfdCommissionAdapter = new CfdCommissionAdapter(mDataBeanList);
        binding.rvCfdCommission.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvCfdCommission.setAdapter(mCfdCommissionAdapter);
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
        //最新价刷新监听
        viewModel.uc.mCurrentCloseObserve.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(@Nullable Double aDouble) {
                if (mDataBeanList.isEmpty()) return;
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
                if (!binding.root.isContentCurrentState()) {
                    binding.root.showContent();
                }
                mDataBeanList.clear();
                mDataBeanList.addAll(cfdCommissionResult.getData().getRecords());
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
        viewModel.loadCommissionData(mSymbol, pageNo, new OnRequestListener<CfdCommissionResult>() {
            @Override
            public void onSuccess(CfdCommissionResult cfdCommissionResult) {
                boolean isRefresh = pageNo == 1;
                mDataBeanList.addAll(cfdCommissionResult.getData().getRecords());
                setData(isRefresh, cfdCommissionResult.getData().getRecords());
            }

            @Override
            public void onFail(String message) {
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
        pageNo++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mCfdCommissionAdapter.setNewData(data);
            if (size == 0) {
                mCfdCommissionAdapter.setEmptyView(R.layout.view_rv_empty, binding.rvCfdCommission);
            }
        } else {
            if (size > 0) {
                mCfdCommissionAdapter.addData(data);
            }
        }
        viewModel.isLoadDate = false;
    }


    private void initLogin() {
        binding.root.showError(R.drawable.svg_no_data,  getString(R.string.no_login), getString(R.string.log2view), getString(R.string.login), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_LOGIN)
                        .navigation();
            }
        });
    }
}
