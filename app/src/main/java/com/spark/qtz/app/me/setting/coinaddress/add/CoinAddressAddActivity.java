package com.spark.qtz.app.me.setting.coinaddress.add;

import android.arch.lifecycle.Observer;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lxj.xpopup.XPopup;
import com.spark.acclient.pojo.CoinSupportBean;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.ActivityCoinAddressAddBinding;
import com.spark.qtz.ui.popup.CoinChoosePopup;
import com.spark.qtz.ui.popup.impl.OnCoinChooseListener;
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
@Route(path = ARouterPath.ACTIVITY_ME_COINADDRESS_COINADDRESS_ADD)
public class CoinAddressAddActivity extends BaseActivity<ActivityCoinAddressAddBinding, CoinAddressAddViewModel> {
    private TitleBean mTitleModel;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_coin_address_add;
    }

    @Override
    public int initVariableId() {
        return BR.coinAddressAddViewModel;
    }

    @Override
    public void initView() {
        super.initView();
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.coinAddressAddTitle.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, Color.WHITE);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //TitleSet
        mTitleModel = new TitleBean();
        binding.coinAddressAddTitle.setViewTitle(mTitleModel);
        setTitleListener(binding.coinAddressAddTitle.titleRootLeft);

        viewModel.initContext(this);
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.mCoinSupportBeanSingleLiveEvent.observe(this, new Observer<CoinSupportBean>() {
            @Override
            public void onChanged(@Nullable final CoinSupportBean coinSupportBean) {
                new XPopup.Builder(CoinAddressAddActivity.this)
                        .asCustom(new CoinChoosePopup(CoinAddressAddActivity.this, coinSupportBean.getData(), false, new OnCoinChooseListener() {
                            @Override
                            public void onClickOrder() {
                                //记录
                            }

                            @Override
                            public void onClickItem(int position) {
                                //item点击
                                viewModel.setSelectCoinAddress(coinSupportBean.getData().get(position).getCoinName());
                            }
                        }))
                        .show();
            }
        });
    }
}
