package com.spark.qtz.app.me.finance.property.contract;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.spark.acclient.FinanceClient;
import com.spark.acclient.pojo.SpotWalletResult;
import com.spark.qtz.R;
import com.spark.otcclient.LcTradeClient;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;

import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.binding.command.BindingAction;
import me.spark.mvvm.binding.command.BindingCommand;
import me.spark.mvvm.bus.event.SingleLiveEvent;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.utils.DfUtils;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.MathUtils;
import me.spark.mvvm.utils.SPUtils;
import me.spark.mvvm.utils.SpanUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/28
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ContractViewModel extends BaseViewModel {
    public ContractViewModel(@NonNull Application application) {
        super(application);
    }

    private Context mContext;
    public ObservableField<CharSequence> cfdWalletTotalChar = new ObservableField<>();
    public ObservableField<String> cfdWalletTransChar = new ObservableField<>();
    public ObservableField<String> marginAmount = new ObservableField<>();
    private OnRequestListener mOnRequestListener;
    private double walletTotal, walletTotalTrans;


    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<Boolean> isHideAccountSwitchEvent = new SingleLiveEvent<>();
    }

    //资金详情的展示与隐藏
    public BindingCommand hideAccountOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.isHideAccountSwitchEvent.setValue(uc.isHideAccountSwitchEvent.getValue() == null || !uc.isHideAccountSwitchEvent.getValue());
        }
    });

    public void initAccountText(boolean isHide) {
        if (isHide) {
            cfdWalletTotalChar.set("****** USDT");
            cfdWalletTransChar.set("≈ **** CNY " + mContext.getString(R.string.total_assets_convert));
        } else {
            cfdWalletTotalChar.set(initWalletTotal(walletTotal));
            cfdWalletTransChar.set(transWalletTotal(walletTotalTrans));
        }
    }

    public void iniCfdWallet(Context context, OnRequestListener mOnRequestListener) {
        this.mContext = context;
        this.mOnRequestListener = mOnRequestListener;
        FinanceClient.getInstance().getCoinWallet("CFD");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        if ((!isVisible2User())
                && (!eventBean.getOrigin().equals(EvKey.walletChange))
                && (!eventBean.getOrigin().equals(EvKey.coinWallet)))
            return;
        switch (eventBean.getOrigin()) {
            //cfd钱包查询业务处理
            case EvKey.coinWallet:
                if (mOnRequestListener == null || eventBean.getType() != 2) return;
                if (eventBean.isStatue()) {
                    updateSpotInfo((SpotWalletResult) eventBean.getObject());
                } else {
                    mOnRequestListener.onFail(eventBean.getMessage());
                }
                break;
            case EvKey.walletChange:
                LcTradeClient.getInstance().authMerchantFind();
                break;
            case EvKey.logout_success_401:
                if (eventBean.isStatue()) {
                    dismissDialog();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 更新币币资产信息
     *
     * @param spotWalletResult
     */
    private void updateSpotInfo(SpotWalletResult spotWalletResult) {
        if (mOnRequestListener == null) return;
        walletTotal = 0;
        walletTotalTrans = 0;
        for (SpotWalletResult.DataBean dataBean : spotWalletResult.getData()) {
            walletTotal = new BigDecimal(dataBean.getTotalPlatformAssetBalance()).add(new BigDecimal(walletTotal)).doubleValue();
            walletTotalTrans = new BigDecimal(dataBean.getTotalLegalAssetBalance()).add(new BigDecimal(walletTotalTrans)).doubleValue();
        }
        initAccountText(SPUtils.getInstance().isHideAccountCfd());
        mOnRequestListener.onSuccess(spotWalletResult);
    }

    /**
     * 钱包总额
     *
     * @param spotWalletTotal
     * @return
     */
    private CharSequence initWalletTotal(double spotWalletTotal) {
        String close = DfUtils.formatNum(MathUtils.getRundNumber(spotWalletTotal, 4, null));
        if (!close.contains(".")) return close;
        CharSequence text = new SpanUtils()
                .append(close.split("\\.")[0])
                .append("." + close.split("\\.")[1]).setFontSize(16, true)
                .append(" USDT").setFontSize(16, true)
                .create();
        return text;
    }

    private String transWalletTotal(double spotWalletTotal) {
        String close = DfUtils.formatNum(MathUtils.getRundNumber(spotWalletTotal, 2, null));
        return "≈ " + close + " CNY  " + mContext.getString(R.string.total_assets_convert);
    }

    private boolean isVisible2User() {
        return Constant.accountPage == 1;
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBusUtils.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBusUtils.unRegister(this);
        LogUtils.e("onStop");
    }
}
