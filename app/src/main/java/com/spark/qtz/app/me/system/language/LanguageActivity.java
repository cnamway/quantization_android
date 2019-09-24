package com.spark.qtz.app.me.system.language;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.ActivityLanguageBinding;
import com.spark.qtz.util.AppUtils;
import com.spark.qtz.util.StatueBarUtils;

import butterknife.OnClick;
import me.spark.mvvm.base.AppManager;
import me.spark.mvvm.base.BaseActivity;
import me.spark.mvvm.utils.LanguageSPUtil;
import me.spark.mvvm.utils.language.LocalManageUtil;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/29
 * 描    述：
 * 修订历史：
 * ================================================
 */
@Route(path = ARouterPath.ACTIVITY_ME_LANGUAGE)
public class LanguageActivity extends BaseActivity<ActivityLanguageBinding, LanguageViewModel> {
    private TitleBean mTitleModel;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_language;
    }

    @Override
    public int initVariableId() {
        return BR.languageViewModel;
    }

    @Override
    public void initView() {
        super.initView();
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.languageTitle.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, Color.WHITE);

        //TitleSet
        mTitleModel = new TitleBean();
        binding.languageTitle.setViewTitle(mTitleModel);
        setTitleListener(binding.languageTitle.titleRootLeft);

        switch (LanguageSPUtil.getInstance(this).getSelectLanguage()) {
            //简体中文
            case 0:
                binding.chinese.setChecked(true);
                break;
            //English
            case 1:
                binding.english.setChecked(true);
                break;
        }
        binding.chinese.setClickable(false);
        binding.english.setClickable(false);
    }

    private void selectLanguage(int select) {
        //重启切换语言 todo 待优化
        LocalManageUtil.saveSelectLanguage(this, select);
        AppManager.getAppManager().finishAllActivity();
        Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(AppUtils.getContextPackageName());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @OnClick({R.id.ll_chinese,
            R.id.ll_english})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.ll_chinese:
                if (binding.chinese.isChecked()) return;
                binding.chinese.setChecked(true);
                binding.english.setChecked(false);
                selectLanguage(0);
                break;
            case R.id.ll_english:
                if (binding.english.isChecked()) return;
                binding.english.setChecked(true);
                binding.chinese.setChecked(false);
                selectLanguage(1);
                break;
        }
    }
}
