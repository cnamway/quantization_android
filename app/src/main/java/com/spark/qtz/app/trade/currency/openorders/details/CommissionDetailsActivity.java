package com.spark.qtz.app.trade.currency.openorders.details;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.app.trade.currency.adapter.OpenOrdersDetailsAdapter;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.databinding.ActivityCommissionDetailsBinding;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.StatueBarUtils;
import com.spark.modulespot.pojo.OpenOrderDetailsResult;
import com.spark.modulespot.pojo.OpenOrdersResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import me.spark.mvvm.base.BaseActivity;
import me.spark.mvvm.http.impl.OnRequestListener;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/4
 * 描    述：
 * 修订历史：
 * ================================================
 */

@Route(path = ARouterPath.ACTIVITY_TRADE_COMMISION_DETAILS)
public class CommissionDetailsActivity extends BaseActivity<ActivityCommissionDetailsBinding, CommissionDetailsViewModel> {
    @Autowired(name = "commissionDetails")
    OpenOrdersResult.DataBean.ListBean commissionDetailsBean;

    private OpenOrdersDetailsAdapter mOpenOrdersDetailsAdapter;
    private List<OpenOrderDetailsResult.DataBean> mDataBeanList = new ArrayList<>();

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_commission_details;
    }

    @Override
    public int initVariableId() {
        return BR.commissionDetailsViewModel;
    }

    @Override
    public void initView() {
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, getResources().getColor(R.color.commission_bg));
        mOpenOrdersDetailsAdapter = new OpenOrdersDetailsAdapter(mDataBeanList);
        binding.rvOpenDetails.setLayoutManager(new LinearLayoutManager(this));
        binding.rvOpenDetails.setAdapter(mOpenOrdersDetailsAdapter);
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.isRefresh.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    initData();
                }
            }
        });
    }

    @Override
    public void initData() {
        if (!binding.root.isLoadingCurrentState()) binding.root.showLoading();
        viewModel.initDetails(commissionDetailsBean, new OnRequestListener<OpenOrderDetailsResult>() {
            @Override
            public void onSuccess(OpenOrderDetailsResult openOrderDetailsResult) {
                mDataBeanList.clear();
                mDataBeanList.addAll(openOrderDetailsResult.getData());
                mOpenOrdersDetailsAdapter.notifyDataSetChanged();
                if (binding.root.isLoadingCurrentState()) {
                    binding.root.showContent();
                }
            }

            @Override
            public void onFail(String message) {
                Toasty.showError(message);
                binding.root.showError(R.drawable.svg_no_data, getString(R.string.error_occurred), message, getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initData();
                    }
                });
            }
        });
    }

    @OnClick({R.id.title_left})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left:
                finish();
                break;
        }
    }
}
