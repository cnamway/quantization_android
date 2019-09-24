package com.spark.qtz.app;

import android.os.Bundle;

import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.databinding.ActivitySplashBinding;
import com.spark.qtz.util.StatueBarUtils;

import me.spark.mvvm.base.BaseActivity;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/5
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_splash;
    }

    @Override
    public int initVariableId() {
        return BR.splashViewModel;
    }

    @Override
    public void initView() {
        StatueBarUtils.setStatusBarVisibility(this, false);
    }

    @Override
    public void initData() {
        viewModel.loadCasConfig();
    }

}
