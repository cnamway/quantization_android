package com.spark.qtz.app.me.finance.property;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.spark.acclient.pojo.CoinSupportBean;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.app.me.finance.property.contract.ContractAccountFragment;
import com.spark.qtz.app.me.finance.property.currency.CurAccountFragment;
import com.spark.qtz.app.me.finance.property.legal_currency.LegalCurAccountFragment;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.databinding.ActivityPropertyBinding;
import com.spark.qtz.ui.adapter.SlideTabPagerAdapter;
import com.spark.qtz.util.StatueBarUtils;

import java.util.ArrayList;

import me.spark.mvvm.base.BaseActivity;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.utils.LogUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/28
 * 描    述：
 * 修订历史：
 * ================================================
 */
@Route(path = ARouterPath.ACTIVITY_ME_PROPERTY)
public class PropertyActivity extends BaseActivity<ActivityPropertyBinding, PropertyViewModel> {
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private SlideTabPagerAdapter mAdapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_property;
    }

    @Override
    public int initVariableId() {
        return BR.propertyViewModel;
    }

    @Override
    public void initView() {
        super.initView();
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, Color.WHITE);
    }

    @Override
    public void initData() {
        binding.root.showLoading();
        viewModel.getCoinSupport(new OnRequestListener<CoinSupportBean>() {
            @Override
            public void onSuccess(CoinSupportBean coinSupportBean) {
                if (binding.root.isLoadingCurrentState()) binding.root.showContent();
                reInitView();
            }

            @Override
            public void onFail(String message) {
                binding.root.showError(R.drawable.svg_no_network, getString(R.string.network_abnormal), message, getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initData();
                    }
                });
            }
        });
    }

    private void reInitView() {
        mTitles = getResources().getStringArray(R.array.property_tab);
        mFragments.add(new CurAccountFragment());
        mFragments.add(new LegalCurAccountFragment());
        mFragments.add(new ContractAccountFragment());
//        mFragments.add(new ContractFragment());
        mAdapter = new SlideTabPagerAdapter(getSupportFragmentManager(),
                mFragments, mTitles);
        binding.propertyVp.setAdapter(mAdapter);
        binding.propertyTab.setViewPager(binding.propertyVp);

        binding.propertyVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                Constant.accountPage = position;
                LogUtils.e("onPageSelected", position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}
