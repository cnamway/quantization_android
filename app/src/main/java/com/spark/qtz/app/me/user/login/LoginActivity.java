package com.spark.qtz.app.me.user.login;

import android.arch.lifecycle.Observer;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.spark.qtz.App;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.ActivityLoginBinding;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.RegexUtils;
import com.spark.qtz.util.StatueBarUtils;

import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import me.spark.mvvm.base.BaseActivity;
import me.spark.mvvm.utils.StringUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/27
 * 描    述：
 * 修订历史：
 * ================================================
 */
@Route(path = ARouterPath.ACTIVITY_ME_LOGIN)
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {
    private TitleBean mTitleModel;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public int initVariableId() {
        return BR.loginViewModel;
    }

    @Override
    public void initView() {
        super.initView();
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.loginTitle.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, Color.TRANSPARENT);

        //TitleSet
        mTitleModel = new TitleBean();
        mTitleModel.setShowLeftImg(true);
        mTitleModel.setShowRightTV(true);
        mTitleModel.setRightTV(getString(R.string.register));
        binding.loginTitle.titleLeftImg.setImageDrawable(getResources().getDrawable(R.drawable.svg_cancel));
        binding.loginTitle.setViewTitle(mTitleModel);
        setTitleListener(binding.loginTitle.titleRootLeft, binding.loginTitle.titleRootRight);

        viewModel.initContext(this);
    }

    @Override
    protected void onTitleRightClick() {
        //注册
        ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_REGISTER)
                .navigation();
        finish();
    }

    @Override
    public void initViewObservable() {
        //密码显示开关
        viewModel.uc.pwdSwitchEvent.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                binding.pwdSwitch.setImageDrawable(aBoolean ?
                        getResources().getDrawable(R.mipmap.icon_viliable_un) :
                        getResources().getDrawable(R.mipmap.icon_viliable));
                binding.userPassword.setTransformationMethod(aBoolean ?
                        HideReturnsTransformationMethod.getInstance() :
                        PasswordTransformationMethod.getInstance());
                binding.userPassword.setSelection(viewModel.userPassWord.get().length());
            }
        });

        viewModel.uc.smsCodePopopShow.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                }else {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                }
            }
        });
        viewModel.uc.mGetCodeSuccessLiveEvent.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                binding.getSmsCode.setVisibility(View.INVISIBLE);
                binding.llCountdownView.setVisibility(View.VISIBLE);
                binding.countdownView.start(60 * 1000);
                binding.countdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                    @Override
                    public void onEnd(CountdownView cv) {
                        binding.getSmsCode.setVisibility(View.VISIBLE);
                        binding.llCountdownView.setVisibility(View.INVISIBLE);
                    }
                });
                Toasty.showSuccess(App.getInstance().getApplicationContext().getString(R.string.str_phone_code_success));
            }
        });
    }

    @OnClick({R.id.get_sms_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_sms_code:
                if (StringUtils.isEmpty(viewModel.userName.get())) {
                    return;
                }
                if (viewModel.userName.get().contains("@") || RegexUtils.isEmail(viewModel.userName.get())) {
                    viewModel.setType(1);
                    viewModel.getPhoneCode();
                } else {
                    viewModel.setType(0);
//                    if (StringUtils.isEmpty(viewModel.countryCode.get())) {
//                        Toasty.showError(getString(R.string.choose_country));
//                        return;
//                    }
//
//                    if (!RegexUtils.isMobileExact(viewModel.phoneNum.get())) {
//                        Toasty.showError(getString(R.string.valid_phone));
//                        return;
//                    }
                    viewModel.getPhoneCode();
                }
                break;
        }
    }
}
