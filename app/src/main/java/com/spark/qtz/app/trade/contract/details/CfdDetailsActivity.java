package com.spark.qtz.app.trade.contract.details;

import android.graphics.Color;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.modulecfd.pojo.CfdCommissionResult;
import com.example.modulecfd.pojo.CfdDealResult;
import com.example.modulecfd.pojo.CfdPositionResult;
import com.example.modulecfd.pojo.CfdRevokeResult;
import com.example.modulecfd.pojo.CfdTradeOrderResult;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.ActivityCfdDetailsBinding;
import com.spark.qtz.util.StatueBarUtils;

import me.spark.mvvm.base.BaseActivity;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-07-01
 * 描    述：
 * 修订历史：
 * ================================================
 */
@Route(path = ARouterPath.ACTIVITY_TRADE_CFD_DETAILS)
public class CfdDetailsActivity extends BaseActivity<ActivityCfdDetailsBinding, CfdDetailsViewModel> {
    @Autowired(name = "type")
    int type;

    @Autowired(name = "positionBean")
    CfdPositionResult.DataBean positionBean;

    @Autowired(name = "commissionBean")
    CfdCommissionResult.DataBean.RecordsBean commissionBean;

    @Autowired(name = "dealBean")
    CfdDealResult.DataBean.RecordsBean dealBean;

    @Autowired(name = "revokeBean")
    CfdRevokeResult.DataBean.RecordsBean revokeBean;

    @Autowired(name = "accountTradeBean")
    CfdTradeOrderResult.DataBean.RecordsBean accountTradeBean;

    private TitleBean mTitleModel;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_cfd_details;
    }

    @Override
    public int initVariableId() {
        return BR.cfdDetailsViewModel;
    }

    @Override
    public void initView() {
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.cfdDetailsTitle.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, Color.WHITE);

        //TitleSet
        mTitleModel = new TitleBean();
        mTitleModel.setTitleName(getString(R.string.trade_details));
        binding.cfdDetailsTitle.setViewTitle(mTitleModel);
        setTitleListener(binding.cfdDetailsTitle.titleRootLeft);

        switch (type) {
            case 0:
                viewModel.initPositionDetails(positionBean);
                break;
            case 1:
                viewModel.initCommissionDetails(commissionBean);
                break;
            case 2:
                viewModel.initDealDetails(dealBean);
                break;
            case 3:
                viewModel.initRevokeDetails(revokeBean);
                break;
            case 4:
                viewModel.initAccountDetails(accountTradeBean);
                break;
        }
    }
}
