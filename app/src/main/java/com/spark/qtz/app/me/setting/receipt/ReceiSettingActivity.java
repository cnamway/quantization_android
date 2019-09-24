package com.spark.qtz.app.me.setting.receipt;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.databinding.ActivityReceiptSettingBinding;

import me.spark.mvvm.base.BaseActivity;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/20
 * 描    述：
 * 修订历史：
 * ================================================
 */

@Route(path = ARouterPath.ACTIVITY_ME_RECEIPT_SETTING)
public class ReceiSettingActivity extends BaseActivity<ActivityReceiptSettingBinding, ReceiSettingViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_receipt_setting;
    }

    @Override
    public int initVariableId() {
        return BR.receiSettingViewModel;
    }
}
