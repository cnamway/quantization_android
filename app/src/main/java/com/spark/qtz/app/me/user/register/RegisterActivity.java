package com.spark.qtz.app.me.user.register;

import android.graphics.Color;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.ActivityRegisterBinding;
import com.spark.qtz.util.StatueBarUtils;

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
@Route(path = ARouterPath.ACTIVITY_ME_REGISTER)
public class RegisterActivity extends BaseActivity<ActivityRegisterBinding, RegisterViewModel> {
    private TitleBean mTitleModel;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_register;
    }

    @Override
    public int initVariableId() {
        return BR.registerViewModel;
    }

    @Override
    public void initView() {
        super.initView();
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.registerTitle.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, Color.WHITE);

        //TitleSet
        mTitleModel = new TitleBean();
        mTitleModel.setShowRightTV(true);
        mTitleModel.setRightTV(getString(R.string.register_email));
        binding.registerTitle.titleLeftImg.setImageDrawable(getResources().getDrawable(R.drawable.svg_cancel));
        binding.registerTitle.setViewTitle(mTitleModel);
        setTitleListener(binding.registerTitle.titleRootLeft, binding.registerTitle.titleRootRight);

        viewModel.initContext(this);
    }

    @Override
    protected void onTitleRightClick() {

        switch (viewModel.type) {
            //手机注册
            case 0:
                binding.registerTitle.titleRightTv.setText(getString(R.string.register_phone));
                viewModel.type = 1;
                viewModel.phoneNum.set("");
                viewModel.countryCode.set("");
                viewModel.countryName.set("");
                viewModel.title.set(getString(R.string.register_email));
                viewModel.subTitle.set(getString(R.string.register_description_email));
                viewModel.registerType.set(getString(R.string.email_num));
                viewModel.registerHint.set(getString(R.string.email_num_hint));
                break;
            //邮箱注册
            case 1:
                binding.registerTitle.titleRightTv.setText(getString(R.string.register_email));
                viewModel.type = 0;
                viewModel.phoneNum.set("");
                viewModel.countryCode.set("");
                viewModel.countryName.set("");
                viewModel.title.set(getString(R.string.register_phone));
                viewModel.subTitle.set(getString(R.string.register_description));
                viewModel.registerType.set(getString(R.string.phone_num));
                viewModel.registerHint.set(getString(R.string.phone_num_hint));
                break;
        }
    }
}
