package com.spark.qtz.app.me.setting.receipt.add;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.spark.qtz.App;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.ActivityReceiptAddBinding;
import com.spark.qtz.databinding.ActivityReceiptBinding;
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
@Route(path = ARouterPath.ACTIVITY_ME_RECEIPT_ADD)
public class ReceiptAddActivity extends BaseActivity<ActivityReceiptAddBinding, ReceiptAddViewModel> {
    private TitleBean mTitleModel;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_receipt_add;
    }

    @Override
    public int initVariableId() {
        return BR.receiptAddViewModel;
    }

    @Override
    public void initView() {
        super.initView();
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.receiptTitle.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, Color.WHITE);

        //TitleSet
        mTitleModel = new TitleBean();
        mTitleModel.setTitleName(App.getInstance().getString(R.string.add));
        binding.receiptTitle.setViewTitle(mTitleModel);
        setTitleListener(binding.receiptTitle.titleRootLeft);
    }
}
