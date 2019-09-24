package com.spark.qtz.app.quotes.detail;

import android.graphics.Color;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.ActivityQuotesDetailBinding;
import com.spark.qtz.util.StatueBarUtils;

import me.spark.mvvm.base.BaseActivity;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/30
 * 描    述：
 * 修订历史：
 * ================================================
 */
@Route(path = ARouterPath.ACTIVITY_QUOTES_DATAIL)
public class QuotesDetailActivity extends BaseActivity<ActivityQuotesDetailBinding, QuotesDetailViewModel> {
    private TitleBean mTitleModel;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_quotes_detail;
    }

    @Override
    public int initVariableId() {
        return BR.quotesDetailViewModel;
    }

    @Override
    public void initView() {
        super.initView();
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.quotesDetailTitle.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, Color.WHITE);

        //TitleSet
        mTitleModel = new TitleBean();
        mTitleModel.setShowRightImg(true);
        mTitleModel.setTitleName("BTC/USDT");
        mTitleModel.setShowTitleLine(true);
        binding.quotesDetailTitle.titleRightImg.setImageDrawable(getResources().getDrawable(R.drawable.svg_collect));
        binding.quotesDetailTitle.setViewTitle(mTitleModel);
        setTitleListener(binding.quotesDetailTitle.titleRootLeft, binding.quotesDetailTitle.titleRootRight);
    }

    @Override
    protected void onTitleRightClick() {
    }
}
