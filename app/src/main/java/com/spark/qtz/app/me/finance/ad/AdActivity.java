package com.spark.qtz.app.me.finance.ad;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.ActivityAdBinding;
import com.spark.qtz.ui.adapter.SlideTabPagerAdapter;
import com.spark.qtz.util.StatueBarUtils;

import java.util.ArrayList;

import me.spark.mvvm.base.BaseActivity;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/28
 * 描    述：
 * 修订历史：
 * ================================================
 */
@Route(path = ARouterPath.ACTIVITY_ME_AD)
public class AdActivity extends BaseActivity<ActivityAdBinding, AdViewModel> {
    @Autowired(name = "adUp")
    public boolean adUp;
    private TitleBean mTitleModel;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private SlideTabPagerAdapter mAdapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_ad;
    }

    @Override
    public int initVariableId() {
        return BR.adViewModel;
    }

    @Override
    public void initView() {
        super.initView();
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.adTitle.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, Color.WHITE);
        mTitles = getResources().getStringArray(R.array.ad_tab);

        //TitleSet
        mTitleModel = new TitleBean();
        mTitleModel.setShowRightImg(false);
        binding.adTitle.titleRightImg.setImageDrawable(getResources().getDrawable(R.drawable.svg_filter));
        binding.adTitle.setViewTitle(mTitleModel);
        setTitleListener(binding.adTitle.titleRootLeft, binding.adTitle.titleRootRight);

        mFragments.add(AdFragment.newInstance(0));
        mFragments.add(AdFragment.newInstance(1));
        mAdapter = new SlideTabPagerAdapter(getSupportFragmentManager(), mFragments, mTitles);
        binding.adVp.setAdapter(mAdapter);
        binding.adTab.setViewPager(binding.adVp);
        if (adUp) {
            binding.adVp.setCurrentItem(1);
        }
    }

    @Override
    protected void onTitleRightClick() {
    }
}
