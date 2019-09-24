package com.spark.qtz.app.trade.currency.openorders;

import android.arch.lifecycle.Observer;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.lxj.xpopup.XPopup;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.app.trade.currency.openorders.fragment.OpenOrdersVpFragemnt;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.ActivityOpenOrdersBinding;
import com.spark.qtz.ui.adapter.SlideTabPagerAdapter;
import com.spark.qtz.ui.popup.OpenOrderFilterPopup;
import com.spark.qtz.ui.popup.impl.OnOpenOrderFilterListener;
import com.spark.qtz.util.StatueBarUtils;

import java.util.ArrayList;

import me.spark.mvvm.base.BaseActivity;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.utils.StringUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/13
 * 描    述：
 * 修订历史：
 * ================================================
 */
@Route(path = ARouterPath.ACTIVITY_TRADE_CC_OPEN_ORDERS)
public class OpenOrdersActivity extends BaseActivity<ActivityOpenOrdersBinding, OpenOrdersViewModel> {
    @Autowired
    String symbol;

    private TitleBean mTitleModel;
    private String[] mTitles;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private SlideTabPagerAdapter mAdapter;
    private OpenOrderFilterPopup mOpenOrderFilterPopup;
    private OpenOrdersVpFragemnt mOpenOrdersVpFragemnt, mOpenOrdersVpHistoryFragemnt;
    private int currentPage = 0;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_open_orders;
    }

    @Override
    public int initVariableId() {
        return BR.openOrdersViewModel;
    }

    @Override
    public void initView() {
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.openOrdersTitle.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, Color.WHITE);

        mTitles = getResources().getStringArray(R.array.open_order_tab);
        //TitleSet
        mTitleModel = new TitleBean();
        mTitleModel.setShowRightImg(true);
        binding.openOrdersTitle.titleRightImg.setImageDrawable(getResources().getDrawable(R.drawable.svg_filter));
        binding.openOrdersTitle.setViewTitle(mTitleModel);
        setTitleListener(binding.openOrdersTitle.titleRootLeft, binding.openOrdersTitle.titleRootRight);

        binding.root.showLoading();
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.isLoadSuccess.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (binding.root.isLoadingCurrentState()) {
                    binding.root.showContent();
                }
                mOpenOrdersVpFragemnt = OpenOrdersVpFragemnt.newInstance(0, StringUtils.isEmpty(symbol) ? Constant.allThumbSymbol[0] : symbol);
                mOpenOrdersVpHistoryFragemnt = OpenOrdersVpFragemnt.newInstance(1, StringUtils.isEmpty(symbol) ? Constant.allThumbSymbol[0] : symbol);
                mFragments.add(mOpenOrdersVpFragemnt);
                mFragments.add(mOpenOrdersVpHistoryFragemnt);

                mAdapter = new SlideTabPagerAdapter(getSupportFragmentManager(),
                        mFragments, mTitles);
                binding.openOrdersVp.setAdapter(mAdapter);
                binding.openOrdersTab.setViewPager(binding.openOrdersVp);
                binding.openOrdersVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {

                    }

                    @Override
                    public void onPageSelected(int i) {
                        currentPage = i;
                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {

                    }
                });
            }
        });
    }

    @Override
    protected void onTitleRightClick() {
        if (!viewModel.isLoadAllThumb) return;

        if (mOpenOrderFilterPopup == null) {
            mOpenOrderFilterPopup = (OpenOrderFilterPopup) new XPopup.Builder(this)
                    .atView(binding.openOrdersTitle.titleLeftImg)
                    .asCustom(new OpenOrderFilterPopup(this, StringUtils.isEmpty(symbol) ? Constant.allThumbSymbol[0] : symbol, new OnOpenOrderFilterListener() {
                        @Override
                        public void onLcSelect(String symbol, String status) {
                            switch (currentPage) {
                                case 0:
                                    mOpenOrdersVpFragemnt.filter(symbol, status);
                                    break;
                                case 1:
                                    mOpenOrdersVpHistoryFragemnt.filter(symbol, status);
                                    break;
                            }
                        }
                    }));
        }
        mOpenOrderFilterPopup.toggle();
    }
}
