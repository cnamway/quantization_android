package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.lxj.xpopup.core.DrawerPopupView;
import com.spark.qtz.R;
import com.spark.qtz.app.trade.currency.drawer.DrawerFragment;
import com.spark.qtz.app.trade.currency.drawer.DrawerWsFragment;
import com.spark.qtz.ui.adapter.SlideTabPagerAdapter;
import com.spark.qtz.ui.progresslayout.ProgressLinearLayout;
import com.spark.qtz.ui.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.spark.mvvm.base.Constant;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/30
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class B2BDrawerPopup extends DrawerPopupView {
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private SlideTabPagerAdapter mAdapter;

    private FragmentManager fragmentManager;

    @BindView(R.id.drawer_tab)
    SlidingTabLayout mDrawerTab;
    @BindView(R.id.drawer_tab_vp)
    ViewPager mDrawerTabVp;
    @BindView(R.id.drawer_root)
    ProgressLinearLayout mDrawerRoot;

    public B2BDrawerPopup(@NonNull Context context, FragmentManager fragmentManager) {
        super(context);
        this.fragmentManager = fragmentManager;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mDrawerRoot.showLoading();
        if (Constant.coinPairThumbBeanList.isEmpty()) return;
        mTitles = Constant.coinPairThumbBeanList.toArray(new String[Constant.coinPairThumbBeanList.size()]);
        for (String title : mTitles) {
            if (Constant.isHttpAndWs) {
                mFragments.add(DrawerWsFragment.newInstance(title));
            } else {
                mFragments.add(DrawerFragment.newInstance(title));
            }
        }
        mAdapter = new SlideTabPagerAdapter(fragmentManager, mFragments, mTitles);
        mDrawerTabVp.setAdapter(mAdapter);
        mDrawerTab.setViewPager(mDrawerTabVp);
        mDrawerRoot.showContent();
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_b2b_drawer;
    }

    @OnClick({})
    public void onClick(View view) {

    }
}
