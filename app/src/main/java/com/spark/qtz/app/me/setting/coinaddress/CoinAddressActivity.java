package com.spark.qtz.app.me.setting.coinaddress;

import android.arch.lifecycle.Observer;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.spark.acclient.pojo.CoinAddressListBean;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.app.me.setting.coinaddress.adapter.CoinAddressAdapter;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.ActivityCoinAddressBinding;
import com.spark.qtz.ui.WrapContentLinearLayoutManager;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.StatueBarUtils;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.base.BaseActivity;
import me.spark.mvvm.http.impl.OnRequestListener;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/29
 * 描    述：
 * 修订历史：
 * ================================================
 */
@Route(path = ARouterPath.ACTIVITY_ME_COINADDRESS_COINADDRESS)
public class CoinAddressActivity extends BaseActivity<ActivityCoinAddressBinding, CoinAddressViewModel> {
    private TitleBean mTitleModel;
    private List<CoinAddressListBean.DataBean> mCoinAddressList = new ArrayList<>();
    private CoinAddressAdapter mCoinAddressAdapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_coin_address;
    }

    @Override
    public int initVariableId() {
        return BR.coinAddressViewModel;
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
        setTitleListener(binding.coinAddressTitle.titleRootLeft);

        mCoinAddressAdapter = new CoinAddressAdapter(mCoinAddressList);
        binding.rvCoinAddress.setLayoutManager(new WrapContentLinearLayoutManager(this));
        binding.rvCoinAddress.setAdapter(mCoinAddressAdapter);

        mCoinAddressAdapter.setOnSelectListener(new CoinAddressAdapter.OnSelectListener() {
            @Override
            public void onSelect(List<CoinAddressListBean.DataBean> selectList) {
                viewModel.observedBottomText(selectList);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.deleteEvent.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) refresh();
            }
        });
    }

    private void refresh() {
        binding.swipeLayout.setRefreshing(true);
        viewModel.getCoinAddressList(new OnRequestListener<CoinAddressListBean>() {
            @Override
            public void onSuccess(CoinAddressListBean coinAddressListBean) {
                binding.swipeLayout.setRefreshing(false);
                mCoinAddressList.clear();
                mCoinAddressList.addAll(coinAddressListBean.getData());
                if (!mCoinAddressList.isEmpty()) {
                    mCoinAddressAdapter.update();
                } else {
                    mCoinAddressAdapter.setEmptyView(R.layout.view_rv_empty, binding.rvCoinAddress);
                }
            }

            @Override
            public void onFail(String message) {
                Toasty.showError(message);
                binding.swipeLayout.setRefreshing(false);
                mCoinAddressAdapter.setEmptyView(R.layout.view_rv_empty, binding.rvCoinAddress);
            }
        });
    }
}
