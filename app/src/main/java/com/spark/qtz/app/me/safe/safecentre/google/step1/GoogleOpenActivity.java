package com.spark.qtz.app.me.safe.safecentre.google.step1;

import android.graphics.Color;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.ActivityGoogleOpenBinding;
import com.spark.qtz.util.StatueBarUtils;

import me.spark.mvvm.base.BaseActivity;


/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/29
 * 描    述：
 * 修订历史：
 * ================================================
 */
@Route(path = ARouterPath.ACTIVITY_ME_SAFECENTRE_GOOGLE_OPEN)
public class GoogleOpenActivity extends BaseActivity<ActivityGoogleOpenBinding, GoogleOpenViewModel> {
    private TitleBean mTitleModel;
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_google_open;
    }

    @Override
    public int initVariableId() {
        return BR.googleOpenViewModel;
    }

    @Override
    public void initView() {
        super.initView();
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.googleOpenTitle.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, Color.WHITE);

        //TitleSet
        mTitleModel = new TitleBean();
        binding.googleOpenTitle.setViewTitle(mTitleModel);
        setTitleListener(binding.googleOpenTitle.titleRootLeft);
    }
}
