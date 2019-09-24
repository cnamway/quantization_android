package com.spark.qtz.app.trade.legal_currency.order.details.unpay;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.spark.qtz.App;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.databinding.ActivityLcOrderUnpayDetailsBinding;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.StatueBarUtils;
import com.spark.otcclient.pojo.LcOrderResult;
import com.spark.otcclient.pojo.OrderDetailsResult;

import me.spark.mvvm.base.BaseActivity;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.utils.LogUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/12
 * 描    述：
 * 修订历史：
 * ================================================
 */

@Route(path = ARouterPath.ACTIVITY_TRADE_LC_UNPAY_DETAILS_ORDER)
public class LcOrderUnpayDetailsActivity extends BaseActivity<ActivityLcOrderUnpayDetailsBinding, LcOrderUnPAYDetailsViewModel> {
    @Autowired(name = "orderDetails")
    LcOrderResult.DataBean.RecordsBean orderDetailsBean;

    private long timeCountDown;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_lc_order_unpay_details;
    }

    @Override
    public int initVariableId() {
        return BR.lcOrderUnPAYDetailsViewModel;
    }

    @Override
    public void initView() {
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.commission_bg));
        binding.title.setText(App.getInstance().getApplicationContext().getString(R.string.str_unpay));
        viewModel.initContext(this);
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
        viewModel.initViewDate(orderDetailsBean, new OnRequestListener<OrderDetailsResult>() {
            @Override
            public void onSuccess(OrderDetailsResult orderDetailsResult) {
                binding.timeTips.setText("*" + App.getInstance().getApplicationContext().getString(R.string.str_please_within) + " " + orderDetailsResult.getData().getTimeLimit() + " " + App.getInstance().getApplicationContext().getString(R.string.str_complete_within));
                timeCountDown = (long) orderDetailsResult.getData().getTimeLimit() * 60 * 1000 - (System.currentTimeMillis() - orderDetailsResult.getData().getCreateTime());
                LogUtils.e("timeCountDown", timeCountDown);
                if (orderDetailsResult.getData().getOrderType().equals("0")) {
                    binding.countdownViewBuy.start(timeCountDown);
                } else {
                    binding.countdownViewSell.start(timeCountDown);
                }
                if (binding.root.isLoadingCurrentState()) binding.root.showContent();
            }

            @Override
            public void onFail(String message) {
                Toasty.showError(message);
                binding.root.showError(R.drawable.svg_no_data, App.getInstance().getApplicationContext().getString(R.string.str_http_error), message, App.getInstance().getApplicationContext().getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initData();
                    }
                });
            }
        });
    }
}
