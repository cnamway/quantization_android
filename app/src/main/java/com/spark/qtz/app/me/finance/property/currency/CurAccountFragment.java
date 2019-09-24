package com.spark.qtz.app.me.finance.property.currency;

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
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.databinding.FragmentCurAccountBinding;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.base.BaseFragment;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.utils.SPUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/28
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CurAccountFragment extends BaseFragment<FragmentCurAccountBinding, CurAccountViewModel> {
    private AccountAdapter mAccountAdapter;
    private List<SpotWalletResult.DataBean> dataBeanList = new ArrayList<>();

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_cur_account;
    }

    @Override
    public int initVariableId() {
        return BR.curAccountViewModel;
    }

    @Override
    public void initView() {
        binding.curRoot.showLoading();
        mAccountAdapter = new AccountAdapter(dataBeanList);
        binding.rvAccount.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvAccount.setAdapter(mAccountAdapter);
        mAccountAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_PROPERTY_DETAILS).
                        withString("busiType", "SPOT")
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

        binding.imgIsHideAccount.setImageDrawable(SPUtils.getInstance().isHideAccountSpot() ?
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
                SPUtils.getInstance().setIsHideAccountSpot(aBoolean);
                viewModel.initAccountText(aBoolean);
            }
        });
    }

    @Override
    public void loadLazyData() {
        viewModel.initSpotWallet(getActivity(), new OnRequestListener<SpotWalletResult>() {
            @Override
            public void onSuccess(SpotWalletResult spotWalletResult) {
                dataBeanList.clear();
                dataBeanList.addAll(spotWalletResult.getData());
                mAccountAdapter.notifyDataSetChanged();
                binding.swipeLayout.setRefreshing(false);
                if (binding.curRoot.isLoadingCurrentState()) {
                    binding.curRoot.showContent();
                }
            }

            @Override
            public void onFail(String message) {
                if (!binding.curRoot.isErrorCurrentState()) {
                    binding.curRoot.showError(R.drawable.svg_no_data, getString(R.string.error_occurred), message, getString(R.string.retry), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            binding.curRoot.showLoading();
                            loadLazyData();
                        }
                    });
                }
            }
        });
    }
}