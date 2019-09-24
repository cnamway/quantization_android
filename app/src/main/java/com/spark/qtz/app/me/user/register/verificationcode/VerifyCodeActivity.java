package com.spark.qtz.app.me.user.register.verificationcode;

import android.arch.lifecycle.Observer;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.ActivityVerifycodeBinding;
import com.spark.qtz.ui.SMSCodeView;
import com.spark.qtz.util.StatueBarUtils;
import com.spark.ucclient.CaptchaGetClient;

import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import me.spark.mvvm.base.BaseActivity;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/8
 * 描    述：验证码 - 注册
 * 修订历史：
 * ================================================
 */
@Route(path = ARouterPath.ACTIVITY_ME_VERIFYCODE)
public class VerifyCodeActivity extends BaseActivity<ActivityVerifycodeBinding, VerifyCodeViewModel> {
    @Autowired()
    String phoneNum;
    @Autowired()
    String strCountry;
    @Autowired()
    int type;

    private TitleBean mTitleModel;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_verifycode;
    }

    @Override
    public int initVariableId() {
        return BR.verifyCodeViewModel;
    }

    @Override
    public void initView() {
        super.initView();
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.verifyCodeTitle.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, Color.WHITE);

        mTitleModel = new TitleBean();
        binding.verifyCodeTitle.setViewTitle(mTitleModel);
        setTitleListener(binding.verifyCodeTitle.titleRootLeft);

        binding.verifyCodePhone.setText((type == 0 ? getString(R.string.input_your_phone) : getString(R.string.input_your_email)) + phoneNum + getString(R.string.code_get));
        viewModel.initContext(this, type, strCountry, phoneNum);
        startTiming();
    }

    private void startTiming() {
        //60s倒计时
        binding.verifyCodeResend.setEnabled(false);
        binding.verifyCodeTimer.start(60 * 1000);
        binding.verifyCodeTimer.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                binding.verifyCodeResend.setTextColor(Color.BLACK);
                binding.verifyCodeResend.setEnabled(true);
            }
        });

        //监听输入验证码
        binding.verifyCode.setOnInputEndCallBack(new SMSCodeView.inputEndListener() {
            @Override
            public void input(String text) {
                ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_REGISTER_PASS)
                        .withString("phoneNum", phoneNum)
                        .withString("strCountry", strCountry)
                        .withString("code", text)
                        .withInt("type", type)
                        .navigation();
            }

            @Override
            public void afterTextChanged(String text) {

            }
        });
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.isSendSuccess.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    startTiming();
                }
            }
        });
    }

    @OnClick({R.id.verify_code_resend})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.verify_code_resend:
                resendSms();
                break;
        }
    }

    private void resendSms() {
        showDialog(getString(R.string.loading));
        switch (type) {
            //手机注册
            case 0:
                CaptchaGetClient.getInstance().phoneCaptcha(phoneNum);
                break;
            //邮箱注册
            case 1:
                CaptchaGetClient.getInstance().emailCaptcha(phoneNum);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.verifyCode.clearText();
    }
}
