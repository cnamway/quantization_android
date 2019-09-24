package com.spark.qtz.app.me.finance.property.contract;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spark.acclient.pojo.SpotWalletResult;
import com.spark.qtz.R;
import com.spark.qtz.app.me.finance.property.contract.tradeOrder.ContractAccountTradeFragment;
import com.spark.qtz.app.me.finance.property.contract.transOrder.ContractAccountTransFragment;
import com.spark.qtz.databinding.FragmentContractAccountBinding;
import com.spark.qtz.ui.adapter.SlideTabPagerAdapter;

import java.util.ArrayList;

import me.spark.mvvm.BR;
import me.spark.mvvm.base.BaseFragment;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.utils.SPUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-07-03
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ContractAccountFragment extends BaseFragment<FragmentContractAccountBinding, ContractAccountViewModel> {
    private String[] mTitles;
    private ContractAccountTradeFragment mContractAccountTradeFragment;
    private ContractAccountTransFragment mContractAccountTransFragment;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private SlideTabPagerAdapter mAdapter;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_contract_account;
    }

    @Override
    public int initVariableId() {
        return BR.contractAccountViewModel;
    }

    @Override
    public void initView() {
        binding.root.showLoading();

        mTitles = getResources().getStringArray(R.array.contract_account_tab);
        mContractAccountTradeFragment = new ContractAccountTradeFragment();
        mContractAccountTransFragment = new ContractAccountTransFragment();
        mFragments.add(mContractAccountTradeFragment);
        mFragments.add(mContractAccountTransFragment);

        mAdapter = new SlideTabPagerAdapter(getChildFragmentManager(), mFragments, mTitles);
        binding.contractAccountVp.setAdapter(mAdapter);
        binding.contractAccountTab.setViewPager(binding.contractAccountVp);

        //下拉刷新
        binding.swipeLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.base));
        binding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeLayout.setRefreshing(true);
                loadLazyData();
            }
        });
        binding.imgIsHideAccount.setImageDrawable(SPUtils.getInstance().isHideAccountCfd() ?
                getResources().getDrawable(R.drawable.svg_hide) :
                getResources().getDrawable(R.drawable.svg_show));
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.isHideAccountSwitchEvent.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                binding.imgIsHideAccount.setImageDrawable(aBoolean ?
                        getResources().getDrawable(R.drawable.svg_hide) :
                        getResources().getDrawable(R.drawable.svg_show));
                SPUtils.getInstance().setIsHideAccountCfd(aBoolean);
                viewModel.initAccountText(aBoolean);
            }
        });
    }

    @Override
    public void loadLazyData() {
        viewModel.initCfdWallet(getContext(), new OnRequestListener<SpotWalletResult>() {
            @Override
            public void onSuccess(SpotWalletResult spotWalletResult) {
                binding.swipeLayout.setRefreshing(false);
                if (!binding.root.isContentCurrentState()) {
                    binding.root.showContent();
                }
            }

            @Override
            public void onFail(String message) {
                if (!binding.root.isErrorCurrentState()) {
                    binding.root.showError(R.drawable.svg_no_data, getString(R.string.error_occurred), message, getString(R.string.retry), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            binding.root.showLoading();
                            loadLazyData();
                        }
                    });
                }
            }
        });
    }
}
