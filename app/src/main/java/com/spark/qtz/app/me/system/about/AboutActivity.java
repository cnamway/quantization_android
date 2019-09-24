package com.spark.qtz.app.me.system.about;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.api.pojo.UpdateBean;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.ActivityAboutBinding;
import com.spark.qtz.util.AppUtils;
import com.spark.qtz.util.StatueBarUtils;

import me.spark.mvvm.base.BaseActivity;
import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.StringUtils;


/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/29
 * 描    述：
 * 修订历史：
 * ================================================
 */
@Route(path = ARouterPath.ACTIVITY_ME_ABOUT)
public class AboutActivity extends BaseActivity<ActivityAboutBinding, AboutViewModel> {
    private TitleBean mTitleModel;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_about;
    }

    @Override
    public int initVariableId() {
        return BR.aboutViewModel;
    }

    @Override
    public void initView() {
        super.initView();
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.aboutTitle.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, Color.WHITE);

        //TitleSet
        mTitleModel = new TitleBean();
        binding.aboutTitle.setViewTitle(mTitleModel);
        setTitleListener(binding.aboutTitle.titleRootLeft);
        checkVersion();
        viewModel.getArticleList();
    }

    /**
     * 检测版本号
     */
    private void checkVersion() {
        try {
            if (StringUtils.isEmpty(Constant.updateInfoJson)) return;
            UpdateBean mUpdateBean = BaseApplication.gson.fromJson(Constant.updateInfoJson, UpdateBean.class);
            if (StringUtils.formatVersionCode(mUpdateBean.getData().getVersion()) >
                    StringUtils.formatVersionCode(AppUtils.getContextVersionName())) {
                binding.isHasNew.setVisibility(View.VISIBLE);
                viewModel.updateVersion.set(getString(R.string.discover_new_version));
            }
        } catch (Exception e) {
            LogUtils.e("checkVersion", e.toString());
        }
    }
}
