package com.spark.qtz.app.trade.legal_currency;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lxj.xpopup.XPopup;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.app.trade.legal_currency.viewpager.LcVPFragment;
import com.spark.qtz.bean.LcAdFilterBean;
import com.spark.qtz.databinding.FragmentLegalCurrentBinding;
import com.spark.qtz.ui.adapter.SlideTabPagerNoCacheAdapter;
import com.spark.qtz.ui.popup.LegalCurrencyFilterPopup;
import com.spark.qtz.ui.popup.LegalCurrencyMenuPopup;
import com.spark.qtz.ui.popup.LegalCurrencyTypePopup;
import com.spark.qtz.ui.popup.impl.OnLcAdFilterListener;
import com.spark.qtz.ui.popup.impl.OnTypeChooseListener;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.otcclient.pojo.LcCoinListResult;
import com.spark.otcclient.pojo.TradeAreaListResult;

import java.util.ArrayList;

import butterknife.OnClick;
import me.spark.mvvm.base.BaseFragment;
import me.spark.mvvm.base.BaseRequestCode;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.utils.EventBusUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/24
 * 描    述：法币交易
 * 修订历史：
 * ================================================
 */
public class LegalCurrencyFragment extends BaseFragment<FragmentLegalCurrentBinding, LegalCurrencyViewModel> {
    private String[] mTitles;
    private LegalCurrencyFilterPopup mLegalCurrencyFilterPopup;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private SlideTabPagerNoCacheAdapter mAdapter;
    private TradeAreaListResult mTradeAreaListResult;
    private boolean isInitTabSuccess = false;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_legal_current;
    }

    @Override
    public int initVariableId() {
        return BR.legalCurrentViewModel;
    }

    @Override
    public void initView() {
        binding.legalCurrentRoot.showLoading();
        viewModel.initContext(getActivity());
    }

    @Override
    public void loadLazyData() {
        isInitTabSuccess = false;
        if (!binding.legalCurrentRoot.isLoadingCurrentState())
            binding.legalCurrentRoot.showLoading();
        if (Constant.lcCoinPairThumbBeanList.isEmpty()) {
            viewModel.getTradeCoinList(new OnRequestListener<LcCoinListResult>() {
                @Override
                public void onSuccess(LcCoinListResult lcCoinListResult) {
                    Constant.lcCoinPairThumbBeanList.clear();
                    for (LcCoinListResult.DataBean datum : lcCoinListResult.getData()) {
                        Constant.lcCoinPairThumbBeanList.add(datum.getCoinName());
                    }
                    initFragment();
                }

                @Override
                public void onFail(String message) {
                    binding.legalCurrentRoot.showError(R.drawable.svg_no_network, getString(R.string.network_abnormal), message, getString(R.string.retry), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            loadLazyData();
                        }
                    });
                }
            });
        } else {
            initFragment();
        }
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.mTradeCoinListBeanSingleLiveEvent.observe(this, new Observer<LcCoinListResult>() {
            @Override
            public void onChanged(@Nullable LcCoinListResult lcCoinListResult) {
                Constant.lcCoinPairThumbBeanList.clear();
                for (LcCoinListResult.DataBean datum : lcCoinListResult.getData()) {
                    Constant.lcCoinPairThumbBeanList.add(datum.getCoinName());
                }
            }
        });

        viewModel.uc.isRefresh.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    loadLazyData();
                }
            }
        });
    }

    private void initFragment() {
        if (isInitTabSuccess) return;
        isInitTabSuccess = true;

        mTitles = Constant.lcCoinPairThumbBeanList.toArray(new String[Constant.lcCoinPairThumbBeanList.size()]);
        mFragments.clear();
        for (String title : mTitles) {
            mFragments.add(LcVPFragment.newInstance(title));
        }
        //fragment + viewPage + fragment 要使用getChildFragmentManager！！！！！
        mAdapter = new SlideTabPagerNoCacheAdapter(getChildFragmentManager(),
                mFragments, mTitles);
        binding.legalCurrentVp.setAdapter(mAdapter);
        binding.legalCurrentTab.setupWithViewPager(binding.legalCurrentVp);
        binding.legalCurrentVp.setOffscreenPageLimit(mTitles.length - 1);
        if (!binding.legalCurrentRoot.isContentCurrentState())
            binding.legalCurrentRoot.showContent();
    }

    @OnClick({R.id.legal_current_type,
            R.id.legal_current_filter,
            R.id.legal_current_menu})
    public void OnClick(final View view) {
        switch (view.getId()) {
            //我要买 - 我要卖
            case R.id.legal_current_type:
                new XPopup.Builder(getContext())
                        .atView(view)
                        .asCustom(new LegalCurrencyTypePopup(getContext(), new OnTypeChooseListener() {
                            @Override
                            public void onChooseType(int type, String content) {
                                binding.legalCurrentType.setText(content);
                                EventBusUtils.postSuccessEvent(EvKey.lcTradeType, type, content);
                            }
                        }))
                        .show();
                break;
            //筛选
            case R.id.legal_current_filter:
                if (mTradeAreaListResult == null) {
                    viewModel.loadCountry(new OnRequestListener<TradeAreaListResult>() {
                        @Override
                        public void onSuccess(TradeAreaListResult tradeAreaListResult) {
                            mTradeAreaListResult = tradeAreaListResult;
                            showFilterPopup(view);
                        }

                        @Override
                        public void onFail(String message) {
                            Toasty.showError(message);
                        }
                    });
                } else {
                    showFilterPopup(view);
                }
                break;
            //菜单
            case R.id.legal_current_menu:
                new XPopup.Builder(getContext())
                        .atView(view)
                        .asCustom(new LegalCurrencyMenuPopup(getContext()))
                        .show();
                break;
        }
    }

    private void showFilterPopup(View view) {
        if (mLegalCurrencyFilterPopup == null) {
            mLegalCurrencyFilterPopup = (LegalCurrencyFilterPopup) new XPopup.Builder(getActivity())
                    .atView(view)
                    .asCustom(new LegalCurrencyFilterPopup(getActivity(), mTradeAreaListResult, new OnLcAdFilterListener() {
                        @Override
                        public void onLcAdFilter(String country, String payMode, String minLimit, String maxLimit) {
                            LcAdFilterBean lcAdFilterBean = new LcAdFilterBean(payMode, country, minLimit, maxLimit);
                            EventBusUtils.postSuccessEvent(EvKey.lcAdFilterType, BaseRequestCode.OK, "", lcAdFilterBean);
                        }
                    }));
        }
        mLegalCurrencyFilterPopup.toggle();
    }
}
