package com.spark.qtz.app.me.safe.safecentre;

import android.arch.lifecycle.Observer;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.ActivitySafeCentreBinding;
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
@Route(path = ARouterPath.ACTIVITY_ME_SAFECENTRE)
public class SafeCentreActivity extends BaseActivity<ActivitySafeCentreBinding, SafeCentreViewModel> {
    private TitleBean mTitleModel;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_safe_centre;
    }

    @Override
    public int initVariableId() {
        return BR.safeCentreViewModel;
    }

    @Override
    public void initView() {
        super.initView();
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.safeCertreTitle.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, Color.WHITE);

        //TitleSet
        mTitleModel = new TitleBean();
        binding.safeCertreTitle.setViewTitle(mTitleModel);
        setTitleListener(binding.safeCertreTitle.titleRootLeft);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.initItemStatue();
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.safeLevel.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer < 3) {
                    binding.safeLevel.setText(getString(R.string.low));
                    binding.safeLevel.setTextColor(ContextCompat.getColor(SafeCentreActivity.this, R.color.red));
                    binding.safePgb.setProgress(integer * 20);
                    binding.safePgb.setProgressDrawable(getResources().getDrawable(R.drawable.pgb_danger));
                    binding.safeTip.setText(R.string.complete_certification);
                    binding.safeTip.setTextColor(ContextCompat.getColor(SafeCentreActivity.this, R.color.red));
                } else if (integer >= 3 && integer < 5) {
                    binding.safeLevel.setText(R.string.middle);
                    binding.safeLevel.setTextColor(ContextCompat.getColor(SafeCentreActivity.this, R.color.base_dark));
                    binding.safePgb.setProgress(integer * 20);
                    binding.safePgb.setProgressDrawable(getResources().getDrawable(R.drawable.pgb_warn));
                    binding.safeTip.setText(R.string.complete_certification);
                    binding.safeTip.setTextColor(ContextCompat.getColor(SafeCentreActivity.this, R.color.base_dark));
                } else {
                    binding.safeLevel.setText(R.string.high);
                    binding.safeLevel.setTextColor(ContextCompat.getColor(SafeCentreActivity.this, R.color.green));
                    binding.safePgb.setProgress(integer * 20);
                    binding.safePgb.setProgressDrawable(getResources().getDrawable(R.drawable.pgb_safe));
                    binding.safeTip.setText(R.string.certification_completed);
                    binding.safeTip.setTextColor(ContextCompat.getColor(SafeCentreActivity.this, R.color.green));
                }
            }
        });
    }
}
