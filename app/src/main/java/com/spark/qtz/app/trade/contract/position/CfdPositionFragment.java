package com.spark.qtz.app.trade.contract.position;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.modulecfd.pojo.CfdPositionResult;
import com.lxj.xpopup.XPopup;
import com.spark.qtz.App;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.app.trade.contract.adapter.CfdPositionAdapter;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.databinding.FragmentCfdPositionBinding;
import com.spark.qtz.ui.WrapContentLinearLayoutManager;
import com.spark.qtz.ui.popup.PositionCloseOnekeyPopup;
import com.spark.qtz.ui.popup.PositionClosePopup;
import com.spark.qtz.ui.popup.ProfitLossSetPopup;
import com.spark.qtz.ui.toast.Toasty;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import me.spark.mvvm.base.BaseFragment;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.utils.DfUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-06-27
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CfdPositionFragment extends BaseFragment<FragmentCfdPositionBinding, CfdPositionViewModel> {
    private static final String SYMBOL = "symbol";
    private static final String CURRENT_CLOSE = "currentClose";

    private CfdPositionAdapter mCfdPositionAdapter;
    private List<CfdPositionResult.DataBean> mDataBeanList = new ArrayList<>();
    private String mSymbol;

    public static CfdPositionFragment newInstance(String quotesType, double currentClose) {
        CfdPositionFragment cfdPositionFragment = new CfdPositionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SYMBOL, quotesType);
        bundle.putDouble(CURRENT_CLOSE, currentClose);
        cfdPositionFragment.setArguments(bundle);
        return cfdPositionFragment;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_cfd_position;
    }

    @Override
    public int initVariableId() {
        return BR.cfdPositionViewModel;
    }


    @Override
    public void initView() {
        viewModel.mCurrentClose = getArguments().getDouble(CURRENT_CLOSE);
        mSymbol = getArguments().getString(SYMBOL);

        mCfdPositionAdapter = new CfdPositionAdapter(mDataBeanList);
        binding.rvCfdPosition.setLayoutManager(new WrapContentLinearLayoutManager(getContext()));
        binding.rvCfdPosition.setAdapter(mCfdPositionAdapter);
        mCfdPositionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                ARouter.getInstance().build(ARouterPath.ACTIVITY_TRADE_CFD_DETAILS)
                        .withInt("type", 0)
                        .withParcelable("positionBean", mDataBeanList.get(position))
                        .navigation();
            }
        });
        mCfdPositionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    //设置止盈止损
                    case R.id.setProfitAndLoss:
                        new XPopup.Builder(getContext())
                                .asCustom(new ProfitLossSetPopup(getContext(), 0, mDataBeanList.get(position)))
                                .show();
                        break;
                    //平仓
                    case R.id.closePosition:
                        new XPopup.Builder(getContext())
                                .asCustom(new PositionClosePopup(getContext(), 0, mDataBeanList.get(position)))
                                .show();
                        break;
                }
            }
        });
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
                refresh(symbol);
            }
        });

        //登录状态
        viewModel.uc.mLoginStatue.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    refresh(mSymbol);
                } else {
                    initLogin();
                }
            }
        });

    }

    private void refresh(final String symbol) {
        if (viewModel.isLoadDate) return;
        mDataBeanList.clear();
        viewModel.loadPositionData(symbol, new OnRequestListener<CfdPositionResult>() {
            @Override
            public void onSuccess(CfdPositionResult cfdPositionResult) {
                if (!binding.root.isContentCurrentState()) {
                    binding.root.showContent();
                }

                for (CfdPositionResult.DataBean dataBean : cfdPositionResult.getData()) {
                    if (dataBean.getSymbol().equals(symbol) && !mDataBeanList.contains(dataBean)) {
                        mDataBeanList.add(dataBean);
                    }
                }
                notifyAdapter();
            }

            @Override
            public void onFail(String message) {
                Toasty.showError(message);
            }
        });
    }

    /**
     * 监听数据 刷新布局
     */
    private void notifyAdapter() {
        double profitAndLoss = 0;

        for (CfdPositionResult.DataBean dataBean : mDataBeanList) {
            if (dataBean.getSymbol().equals(viewModel.mCurrentSymbol)) {
                dataBean.setCurrentPrice(viewModel.mCurrentClose);
                profitAndLoss += (dataBean.getProfitAndloss() * dataBean.getVolume() * dataBean.getMultiplier());
            }
        }

        if (!mDataBeanList.isEmpty()) {
            binding.head.setVisibility(View.VISIBLE);
            viewModel.profitAndLossAll.set(profitAndLoss >= 0 ?
                    "+" + DfUtils.numberFormat(profitAndLoss, 4) :
                    DfUtils.numberFormat(profitAndLoss, 4));
            viewModel.isProfitOrLoss.set(profitAndLoss >= 0);
            mCfdPositionAdapter.notifyDataSetChanged();
            binding.head.setVisibility(View.VISIBLE);
        } else {
            binding.head.setVisibility(View.GONE);
            mCfdPositionAdapter.setEmptyView(R.layout.view_rv_empty, binding.rvCfdPosition);
        }

        viewModel.isLoadDate = false;
    }

    @Override
    public void loadLazyData() {
        if (App.getInstance().isAppLogin()) {
            refresh(mSymbol);
        } else {
            initLogin();
        }
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

    @OnClick({R.id.position_close_one_key})
    public void OnClick(View view) {
        switch (view.getId()) {
            //一键平仓
            case R.id.position_close_one_key:
                int mProfit = 0, mLoss = 0;
                for (CfdPositionResult.DataBean dataBean : mDataBeanList) {
                    if (dataBean.getProfitAndloss() >= 0) {
                        mProfit += 1;
                    } else {
                        mLoss += 1;
                    }
                }
                new XPopup.Builder(getContext())
                        .asCustom(new PositionCloseOnekeyPopup(getContext(), mProfit, mLoss, mDataBeanList))
                        .show();
                break;
        }
    }
}
