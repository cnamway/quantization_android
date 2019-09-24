package com.spark.qtz.app.me.safe.safecentre.google.remove;

import android.graphics.Color;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.ActivityGoogleRemoveBinding;
import com.spark.qtz.util.StatueBarUtils;

import me.spark.mvvm.base.BaseActivity;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/29
 * 描    述：解绑谷歌认证器
 * 修订历史：
 * ================================================
 */
@Route(path = ARouterPath.ACTIVITY_ME_SAFECENTRE_GOOGLE_REMOVE)
public class GoogleRemoveActivity extends BaseActivity<ActivityGoogleRemoveBinding, GoogleRemoveViewModel> {
    private TitleBean mTitleModel;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_google_remove;
    }

    @Override
    public int initVariableId() {
        return BR.googleRemoveViewModel;
    }

    @Override
    public void initView() {
        super.initView();
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.googleRemoveTitle.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, Color.WHITE);

        //TitleSet
        mTitleModel = new TitleBean();
        binding.googleRemoveTitle.setViewTitle(mTitleModel);
        setTitleListener(binding.googleRemoveTitle.titleRootLeft);
    }

    @Override
    public void initData() {
        viewModel.loadDate();
    }

}
