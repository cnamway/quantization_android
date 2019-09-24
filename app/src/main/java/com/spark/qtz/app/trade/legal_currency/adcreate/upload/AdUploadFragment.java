package com.spark.qtz.app.trade.legal_currency.adcreate.upload;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sevenheaven.iosswitch.ShSwitchView;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.databinding.FragmentAdUploadBinding;
import com.spark.otcclient.pojo.AdSelfDownFindResult;

import butterknife.OnClick;
import me.spark.mvvm.base.BaseFragment;
import me.spark.mvvm.utils.DfUtils;
import me.spark.mvvm.utils.StringUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/27
 * 描    述：发布或编辑广告
 * 修订历史：
 * ================================================
 */
public class AdUploadFragment extends BaseFragment<FragmentAdUploadBinding, AdUploadViewModel> {
    private int AD_TYPE = 0;//广告类型 0 - 发布购买  1- 发布出售
    private AdSelfDownFindResult.DataBean.RecordsBean ads;

    /**
     * 广告类型 0 - 发布购买  1- 发布出售
     *
     * @param adType
     * @return
     */
    public static AdUploadFragment newInstance(int adType, AdSelfDownFindResult.DataBean.RecordsBean ads) {
        AdUploadFragment adUploadFragment = new AdUploadFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", adType);
        bundle.putParcelable("ads", ads);
        adUploadFragment.setArguments(bundle);
        return adUploadFragment;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_ad_upload;
    }

    @Override
    public int initVariableId() {
        return BR.adUploadViewModel;
    }

    @Override
    public void initView() {
        AD_TYPE = getArguments().getInt("type");
        ads = getArguments().getParcelable("ads");
        viewModel.initContext(getActivity(), AD_TYPE, ads);
        //是否为固定价格
        binding.isFixedPriceSwitch.setOnSwitchStateChangeListener(new ShSwitchView.OnSwitchStateChangeListener() {
            @Override
            public void onSwitchStateChange(boolean isOn) {
                viewModel.isFixedPrice.set(isOn);
            }
        });
        //是否自动回复
        binding.isAutoResponseSwitch.setOnSwitchStateChangeListener(new ShSwitchView.OnSwitchStateChangeListener() {
            @Override
            public void onSwitchStateChange(boolean isOn) {
                viewModel.isAutoResponse.set(isOn);
            }
        });
        binding.adFixPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.tradePrice.set(s.toString());
            }
        });
        binding.adPremiumPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ads == null || ads.getPriceType() == 1) {
                    if (StringUtils.isEmpty(s.toString())) {
                        viewModel.tradePrice.set("");
                    } else {
                        viewModel.tradePrice.set(DfUtils.numberFormat(viewModel.ratePrice * (Double.valueOf(s.toString()) / 100), 2));
                    }
                }
            }
        });
        binding.adTradeNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.countChange();
            }
        });
        if (ads != null) {
            binding.isFixedPriceSwitch.setOn(ads.getPriceType() == 0);
            viewModel.isAutoResponse.set(ads.getAutoReply() == 1 ? true : false);
            viewModel.fixedPrice.set(ads.getPriceType() == 0 ? ads.getPrice() + "" : 0 + "");
            //viewModel.isFixedPrice.set(ads.getPriceType() == 0 ? true : false);
        }
    }

    @Override
    public void loadLazyData() {
        viewModel.getTradeCoinList();
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.isRefresh.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    loadLazyData();
                }
            }
        });
    }

    @OnClick({R.id.ad_pay_mode})
    public void OnClick(final View view) {
        switch (view.getId()) {
            case R.id.ad_pay_mode:
                /*new XPopup.Builder(getContext())
                        .atView(view)
                        .asCustom(new AdPayModePopup(getContext(), new OnTypeChooseListener() {
                            @Override
                            public void onChooseType(int type, String content) {
                                viewModel.updatePayType(type, content);
                            }
                        }))
                        .show();*/
                //选择收款方式跳转界面
                ARouter.getInstance().build(ARouterPath.ACTIVITY_TRADE_AD_SELECT_PAYWAY)
                        .withInt("AD_TYPE", AD_TYPE)
                        .withParcelableArrayList("payWaySettingsSelected", viewModel.mSelectList)
                        .navigation(getActivity(), 1);
                break;
        }
    }

}
