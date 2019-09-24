package com.spark.qtz.app.me.finance.property.details;

import android.os.Bundle;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.spark.acclient.pojo.CoinTransDetailsResult;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.ActivityPropertyRecordDetailsBinding;
import com.spark.qtz.util.StatueBarUtils;

import me.spark.mvvm.base.BaseActivity;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/4
 * 描    述：
 * 修订历史：
 * ================================================
 */

@Route(path = ARouterPath.ACTIVITY_ME_PROPERTY_RECORD_DETAILS)
public class PropertyRecordDetailsActivity extends BaseActivity<ActivityPropertyRecordDetailsBinding, PropertyRecordDetailsViewModel> {
    @Autowired(name = "propertDetails")
    CoinTransDetailsResult.DataBean.RecordsBean propertDetailsBean;

    private TitleBean mTitleModel;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_property_record_details;
    }

    @Override
    public int initVariableId() {
        return BR.propertyRecordDetailsViewModel;
    }

    @Override
    public void initView() {
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.propertyDetailsTitle.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, getResources().getColor(R.color.white));

        //TitleSet
        mTitleModel = new TitleBean();
        mTitleModel.setTitleName(getString(R.string.trade_details));
        binding.propertyDetailsTitle.setViewTitle(mTitleModel);
        setTitleListener(binding.propertyDetailsTitle.titleRootLeft);
        binding.propertyDetailsTitle.llHeader.setBackgroundColor(getResources().getColor(R.color.blue));
        binding.propertyDetailsTitle.titleLeftImg.setImageDrawable(getResources().getDrawable(R.drawable.svg_back_white));
        binding.propertyDetailsTitle.titleRootLeft.setBackgroundColor(getResources().getColor(R.color.blue));
        binding.propertyDetailsTitle.titleRootRight.setBackgroundColor(getResources().getColor(R.color.blue));
        binding.propertyDetailsTitle.titleName.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public void initData() {
        viewModel.init(this, propertDetailsBean);
    }

}
