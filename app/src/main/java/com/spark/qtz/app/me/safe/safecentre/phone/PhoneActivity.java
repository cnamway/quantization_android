package com.spark.qtz.app.me.safe.safecentre.phone;

import android.arch.lifecycle.Observer;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.spark.qtz.App;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.ActivityPhoneBinding;
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
 * 创建日期：2019/4/29
 * 描    述：
 * 修订历史：
 * ================================================
 */
@Route(path = ARouterPath.ACTIVITY_ME_SAFECENTRE_PHONE)
public class PhoneActivity extends BaseActivity<ActivityPhoneBinding, PhoneViewModel> {
    private TitleBean mTitleModel;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_phone;
    }

    @Override
    public int initVariableId() {
        return BR.phoneViewModel;
    }

    @Override
    public void initView() {
        super.initView();
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.phoneTitle.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, Color.WHITE);

        //TitleSet
        mTitleModel = new TitleBean();
        binding.phoneTitle.setViewTitle(mTitleModel);
        setTitleListener(binding.phoneTitle.titleRootLeft);
        viewModel.initContext(this);
    }

    @Override
    public void initViewObservable() {
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
                if (StringUtils.isEmpty(viewModel.countryCode.get())) {
                    Toasty.showError(getString(R.string.choose_country));
                    return;
                }
                if (StringUtils.isEmpty(viewModel.phoneNum.get())) {
                    Toasty.showError(getString(R.string.phone_num_hint));
                    return;
                }
                if (!RegexUtils.isMobileExact(viewModel.phoneNum.get())) {
                    Toasty.showError(getString(R.string.valid_phone));
                    return;
                }
                viewModel.getPhoneCode();
                break;
        }
    }
}
