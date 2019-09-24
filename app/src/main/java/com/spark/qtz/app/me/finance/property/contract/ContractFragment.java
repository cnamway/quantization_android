package com.spark.qtz.app.me.finance.property.contract;

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
import com.spark.acclient.pojo.SpotWalletResult;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.app.me.finance.property.adapter.AccountAdapter;
import com.spark.qtz.app.me.finance.property.legal_currency.LegalCurAccountViewModel;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.databinding.FragmentContractCurAccountBinding;
import com.spark.qtz.databinding.FragmentLegalCurAccountBinding;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.base.BaseFragment;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.utils.SPUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/8/22
 * 描    述：合约账户
 * 修订历史：
 * ================================================
 */
public class ContractFragment extends BaseFragment<FragmentContractCurAccountBinding, ContractViewModel> {
    private AccountAdapter mAccountAdapter;
    private List<SpotWalletResult.DataBean> dataBeanList = new ArrayList<>();

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_contract_cur_account;
    }

    @Override
    public int initVariableId() {
        return BR.contractCurAccountViewModel;
    }


    @Override
    public void initView() {
        binding.lcCurRoot.showLoading();
        mAccountAdapter = new AccountAdapter(dataBeanList);
        binding.cfdWalletRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.cfdWalletRv.setAdapter(mAccountAdapter);
        mAccountAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_PROPERTY_DETAILS).
                        withString("busiType", "CFD")
                        .withParcelable("propertDetails", dataBeanList.get(position))
                        .navigation();
            }
        });
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
        viewModel.iniCfdWallet(getContext(), new OnRequestListener<SpotWalletResult>() {
            @Override
            public void onSuccess(SpotWalletResult spotWalletResult) {
                dataBeanList.clear();
                dataBeanList.addAll(spotWalletResult.getData());
                mAccountAdapter.notifyDataSetChanged();
                binding.swipeLayout.setRefreshing(false);
                if (binding.lcCurRoot.isLoadingCurrentState()) {
                    binding.lcCurRoot.showContent();
                }
            }

            @Override
            public void onFail(String message) {
                if (!binding.lcCurRoot.isErrorCurrentState()) {
                    binding.lcCurRoot.showError(R.drawable.svg_no_data, getString(R.string.error_occurred), message, getString(R.string.retry), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            binding.lcCurRoot.showLoading();
                            loadLazyData();
                        }
                    });
                }
            }
        });
    }


}
