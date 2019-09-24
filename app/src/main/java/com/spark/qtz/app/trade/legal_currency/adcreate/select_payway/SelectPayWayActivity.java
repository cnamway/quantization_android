package com.spark.qtz.app.trade.legal_currency.adcreate.select_payway;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.app.trade.legal_currency.adapter.SelectPayWayAdapter;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.ActivitySelectPaywayBinding;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.StatueBarUtils;
import com.spark.otcclient.pojo.PayListBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.spark.mvvm.base.BaseActivity;
import me.spark.mvvm.http.impl.OnRequestListener;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/28
 * 描    述：请选择收款方式
 * 修订历史：
 * ================================================
 */
@Route(path = ARouterPath.ACTIVITY_TRADE_AD_SELECT_PAYWAY)
public class SelectPayWayActivity extends BaseActivity<ActivitySelectPaywayBinding, SelectPayWayViewModel> {
    @Autowired(name = "payWaySettingsSelected")
    public ArrayList<PayListBean.DataBean> mSelectList;//选中的收款方式列表
    private HashMap<String, PayListBean.DataBean> hashMap = new HashMap<>();
    @Autowired(name = "AD_TYPE")
    public int AD_TYPE = 0;//广告类型 0 - 发布购买  1- 发布出售

    private TitleBean mTitleModel;
    private List<PayListBean.DataBean> mCoinAddressList = new ArrayList<>();
    private SelectPayWayAdapter mAdapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_select_payway;
    }

    @Override
    public int initVariableId() {
        return BR.selectPayWayViewModel;
    }

    @Override
    public void initView() {
        super.initView();
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.coinAddressTitle.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, Color.WHITE);

        //TitleSet
        mTitleModel = new TitleBean();
        binding.coinAddressTitle.setViewTitle(mTitleModel);

        mTitleModel.setShowRightTV(true);
        mTitleModel.setRightTV(getString(R.string.ensure));
        setTitleListener(binding.coinAddressTitle.titleRootLeft, binding.coinAddressTitle.titleRootRight);

        mAdapter = new SelectPayWayAdapter(mCoinAddressList);
        binding.rvCoinAddress.setLayoutManager(new LinearLayoutManager(this));
        binding.rvCoinAddress.setAdapter(mAdapter);

        mAdapter.setOnSelectListener(new SelectPayWayAdapter.OnSelectListener() {
            @Override
            public void onSelect(PayListBean.DataBean dataBean) {
                observedBottomText(dataBean);
            }
        });

        //下拉刷新
        binding.swipeLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.base));
        binding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        for (PayListBean.DataBean payWaySetting : mSelectList) {
            String payType = payWaySetting.getPayType();
            hashMap.put(payType, payWaySetting);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    protected void onTitleRightClick() {
        //确定
        ArrayList<PayListBean.DataBean> payWaySettingsSelected = new ArrayList<>();//选择的收款方式
        for (PayListBean.DataBean payWaySetting : mCoinAddressList) {
            if (payWaySetting.getIsSelected() == 1) {
                payWaySettingsSelected.add(payWaySetting);
            }
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt("AD_TYPE", AD_TYPE);
        bundle.putParcelableArrayList("payWaySettingsSelected", payWaySettingsSelected);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.isRefresh.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    refresh();
                }
            }
        });
    }

    private void refresh() {
        binding.swipeLayout.setRefreshing(true);
        viewModel.getCoinAddressList(new OnRequestListener<PayListBean>() {
            @Override
            public void onSuccess(PayListBean coinAddressListBean) {
                binding.swipeLayout.setRefreshing(false);
                mCoinAddressList.clear();
                for (PayListBean.DataBean payWaySetting : coinAddressListBean.getData()) {
                    if (payWaySetting.getStatus() == 1) {
                        mCoinAddressList.add(payWaySetting);
                    }
                }
                for (PayListBean.DataBean payWaySetting : mCoinAddressList) {
                    for (PayListBean.DataBean temp : mSelectList) {
                        if (temp.getId() == payWaySetting.getId()) {
                            payWaySetting.setIsSelected(1);
                        }
                    }
                }
                mAdapter.update();
            }

            @Override
            public void onFail(String message) {
                Toasty.showError(message);
                binding.swipeLayout.setRefreshing(false);
                mAdapter.setEmptyView(R.layout.view_rv_empty, (ViewGroup) binding.rvCoinAddress.getParent());
            }
        });
    }

    /**
     * 选中后更新视图
     *
     * @param payWaySetting
     */
    public void observedBottomText(PayListBean.DataBean payWaySetting) {
        String payType = payWaySetting.getPayType();
        if (hashMap.containsKey(payType)) {
            PayListBean.DataBean temp = hashMap.get(payType);
            if (temp.getId() != payWaySetting.getId()) {
                Toasty.showError(getString(R.string.str_payway_only_one));
            } else {
                hashMap.remove(payType);
                for (PayListBean.DataBean dataBean : mCoinAddressList) {
                    if (dataBean.getId() == payWaySetting.getId()) {
                        dataBean.setIsSelected(0);
                    }
                }
                mAdapter.update();
            }
        } else {
            hashMap.put(payType, payWaySetting);
            for (PayListBean.DataBean dataBean : mCoinAddressList) {
                if (dataBean.getId() == payWaySetting.getId()) {
                    dataBean.setIsSelected(1);
                }
            }
            mAdapter.update();
        }
    }
}
