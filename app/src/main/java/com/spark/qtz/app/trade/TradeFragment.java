package com.spark.qtz.app.trade;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.app.trade.contract.ContractFragment;
import com.spark.qtz.app.trade.currency.CurrencyFragment;
import com.spark.qtz.app.trade.legal_currency.LegalCurrencyFragment;
import com.spark.qtz.app.trade.lever.LeverFragment;
import com.spark.qtz.databinding.FragmentTradeBinding;
import com.spark.qtz.ui.tablayout.listener.OnTabSelectListener;

import me.spark.mvvm.base.BaseFragment;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.utils.LogUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/18
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class TradeFragment extends BaseFragment<FragmentTradeBinding, TradeViewModel> {
    private String[] mTitles;
    private CurrencyFragment mCurrencyFragment;
    private LegalCurrencyFragment mLegalCurrencyFragment;
    private ContractFragment mContractFragment;
    private LeverFragment mLeverFragment;

    private TradePagerAdapter mAdapter;
    private Fragment selectFragment;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_trade;
    }

    @Override
    public int initVariableId() {
        return BR.tradeViewModel;
    }

    @Override
    public void initView() {
        mLegalCurrencyFragment = new LegalCurrencyFragment();
        mCurrencyFragment = new CurrencyFragment();
        mContractFragment = new ContractFragment();
//        mLeverFragment = new LeverFragment();

        mTitles = getResources().getStringArray(R.array.trade_tab);
        mAdapter = new TradePagerAdapter(getChildFragmentManager());
        binding.tradeVp.setAdapter(mAdapter);
        binding.tradeTab.setTabData(mTitles);
        binding.tradeTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                Constant.tradePage = position;
                binding.tradeVp.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        binding.tradeVp.setOffscreenPageLimit(2);
    }

    private class TradePagerAdapter extends FragmentPagerAdapter {
        public TradePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    selectFragment = mLegalCurrencyFragment;
                    break;
                case 1:
                    selectFragment = mCurrencyFragment;
                    break;
                case 2:
                    selectFragment = mContractFragment;
                    break;
//                case 3:
//                    selectFragment = mLeverFragment;
//                    break;
            }
            return selectFragment;
        }
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.clickPosition.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                LogUtils.e("clickPosition",integer);
                binding.tradeTab.setCurrentTab(integer);
                Constant.tradePage = integer;
                binding.tradeVp.setCurrentItem(integer);
            }
        });
    }
}
