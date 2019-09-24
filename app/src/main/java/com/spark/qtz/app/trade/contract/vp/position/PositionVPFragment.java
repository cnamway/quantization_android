package com.spark.qtz.app.trade.contract.vp.position;

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
import com.spark.qtz.databinding.FragmentPositionVpBinding;
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
import me.spark.mvvm.utils.LogUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-07-29
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class PositionVPFragment extends BaseFragment<FragmentPositionVpBinding, PositionVPViewModel> {
    private static final String SYMBOL = "symbol";
    private static final String SCREENNAME = "screenname";
    private static final String CURRENT_CLOSE = "currentClose";

    private CfdPositionAdapter mCfdPositionAdapter;
    private List<CfdPositionResult.DataBean> mDataBeanList = new ArrayList<>();
    private String mSymbol;

    public static PositionVPFragment newInstance(String quotesType, String screenName, double currentClose) {
        PositionVPFragment positionVPFragment = new PositionVPFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SYMBOL, quotesType);
        bundle.putString(SCREENNAME, screenName);
        bundle.putDouble(CURRENT_CLOSE, currentClose);
        positionVPFragment.setArguments(bundle);
        return positionVPFragment;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_position_vp;
    }

    @Override
    public int initVariableId() {
        return BR.positionVPViewModel;
    }

    @Override
    public void initView() {
        viewModel.mCurrentClose = getArguments().getDouble(CURRENT_CLOSE);
        mSymbol = getArguments().getString(SYMBOL);
        viewModel.initSreenName(getArguments().getString(SCREENNAME));

        mCfdPositionAdapter = new CfdPositionAdapter(mDataBeanList);
        binding.rv.setLayoutManager(new WrapContentLinearLayoutManager(getContext()));
        binding.rv.setAdapter(mCfdPositionAdapter);
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
        mCfdPositionAdapter.setEmptyView(R.layout.view_rv_empty, binding.rv);
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
                LogUtils.e("asdf",symbol);
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
    }

    private void refresh() {
        if (viewModel.isLoadDate) return;
        viewModel.loadPositionData(mSymbol, new OnRequestListener<CfdPositionResult>() {
            @Override
            public void onSuccess(CfdPositionResult cfdPositionResult) {
                mDataBeanList.clear();
                for (CfdPositionResult.DataBean dataBean : cfdPositionResult.getData()) {
                    LogUtils.e("asdf",dataBean.getSymbol() + "--" + mSymbol);
                    if (dataBean.getSymbol().equals(mSymbol) && !mDataBeanList.contains(dataBean)) {
                        dataBean.setSymbol(viewModel.mScreenName);
                        mDataBeanList.add(dataBean);
                    }
                }
                notifyAdapter();
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
     * 监听数据 刷新布局
     */
    private void notifyAdapter() {
        double profitAndLoss = 0;

        for (CfdPositionResult.DataBean dataBean : mDataBeanList) {
            if (viewModel.mCurrentSymbol.startsWith(dataBean.getSymbol())) {
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
        } else {
            binding.head.setVisibility(View.GONE);
            mCfdPositionAdapter.setEmptyView(R.layout.view_rv_empty, binding.rv);
        }
        viewModel.isLoadDate = false;
        viewModel.isWsReceive = false;
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
