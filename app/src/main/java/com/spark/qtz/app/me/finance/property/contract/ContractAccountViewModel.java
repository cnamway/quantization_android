package com.spark.qtz.app.me.finance.property.contract;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.lxj.xpopup.XPopup;
import com.spark.acclient.FinanceClient;
import com.spark.acclient.pojo.CoinSupportBean;
import com.spark.acclient.pojo.MerberWalletResult;
import com.spark.acclient.pojo.SpotWalletResult;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.ui.popup.CoinTransPopup;
import com.spark.qtz.ui.toast.Toasty;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;

import me.spark.mvvm.base.BaseRequestCode;
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
 * 创建日期：2019-07-03
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ContractAccountViewModel extends BaseViewModel {
    public ContractAccountViewModel(@NonNull Application application) {
        super(application);
    }

    private Context mContext;
    public ObservableField<CharSequence> cfdWalletTotalChar = new ObservableField<>();
    public ObservableField<String> cfdWalletTransChar = new ObservableField<>();
    public ObservableField<String> cfdWalletAvailable = new ObservableField<>();
    public ObservableField<String> cfdWalletFreeze = new ObservableField<>();
    private OnRequestListener mOnRequestListener;

    private double walletTotal, walletTotalTrans, walletAvailable, walletFreeze;
    private CoinTransPopup mCoinTransPopup;

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

    public BindingCommand coinTransCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            showDialog(mContext.getString(R.string.loading));
            FinanceClient.getInstance().getCoinSupport();
        }
    });

    public void initCfdWallet(Context context, OnRequestListener mOnRequestListener) {
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
            //CFD钱包查询业务处理
            case EvKey.coinWallet:
                if (mOnRequestListener == null || eventBean.getType() != 2) return;
                if (eventBean.isStatue()) {
                    updateCfdInfo((SpotWalletResult) eventBean.getObject());
                } else {
                    mOnRequestListener.onFail(eventBean.getMessage());
                }
                break;
            //查询平台支持到币种信息
            case EvKey.coinSupport:
                dismissDialog();
                if (eventBean.isStatue()) {
                    CoinSupportBean coinSupportBean = (CoinSupportBean) eventBean.getObject();
                    if (coinSupportBean.getData().isEmpty()) {
                        Toasty.showError(App.getInstance().getString(R.string.no_support_coin));
                        return;
                    } else {
                        mCoinTransPopup = new CoinTransPopup(mContext, 2, coinSupportBean);
                        new XPopup.Builder(mContext)
                                .asCustom(mCoinTransPopup)
                                .show();
                    }
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //法币选定币种提币信息
            case EvKey.merberOtcWallet:
                dismissDialog();
                if (eventBean.isStatue()) {
                    MerberWalletResult merberWalletResult = (MerberWalletResult) eventBean.getObject();
                    mCoinTransPopup.updateTransNumAvailable(merberWalletResult);
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //划转
            case EvKey.coinTransfer:
                if (eventBean.isStatue()) {
                    if (mCoinTransPopup != null && mCoinTransPopup.isShow()) {
                        mCoinTransPopup.dismiss();
                    }
                    Toasty.showSuccess(mContext.getString(R.string.coin_trans_success));
                    FinanceClient.getInstance().getCoinWallet("CFD");
                    EventBusUtils.postSuccessEvent(EvKey.walletChange, BaseRequestCode.OK, "");
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            case EvKey.walletChange:
                FinanceClient.getInstance().getCoinWallet("CFD");
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

    private void updateCfdInfo(SpotWalletResult spotWalletResult) {
        if (mOnRequestListener == null) return;
        walletTotal = 0;
        walletTotalTrans = 0;
        walletAvailable = 0;
        walletFreeze = 0;

        for (SpotWalletResult.DataBean dataBean : spotWalletResult.getData()) {
            walletTotal = new BigDecimal(dataBean.getBalance() + dataBean.getFrozenBalance()).add(new BigDecimal(walletTotal)).doubleValue();
            walletTotalTrans = new BigDecimal(dataBean.getTotalLegalAssetBalance()).add(new BigDecimal(walletTotalTrans)).doubleValue();
            walletAvailable = new BigDecimal(dataBean.getBalance()).add(new BigDecimal(walletAvailable)).doubleValue();
            walletFreeze = new BigDecimal(dataBean.getFrozenBalance()).add(new BigDecimal(walletFreeze)).doubleValue();
        }

        initAccountText(SPUtils.getInstance().isHideAccountCfd());
        mOnRequestListener.onSuccess(spotWalletResult);
    }

    public void initAccountText(boolean isHide) {
        if (isHide) {
            cfdWalletTotalChar.set("****** USDT");
            cfdWalletTransChar.set("≈ **** CNY " + mContext.getString(R.string.total_assets_convert));
            cfdWalletAvailable.set(mContext.getString(R.string.available_funds) + "****");
            cfdWalletFreeze.set(mContext.getString(R.string.freezing_funds) + "****");
        } else {
            cfdWalletTotalChar.set(initWalletTotal(walletTotal));
            cfdWalletTransChar.set(transWalletTotal(walletTotalTrans));
            cfdWalletAvailable.set(initWalletAvailable(walletAvailable));
            cfdWalletFreeze.set(initWalletFreeze(walletFreeze));
        }
    }

    /**
     * 钱包总额
     *
     * @param spotWalletTotal
     * @return
     */
    private CharSequence initWalletTotal(double spotWalletTotal) {
        String close = DfUtils.formatNum(MathUtils.getRundNumber(spotWalletTotal, 6, null));
        if (!close.contains(".")) return close;
        CharSequence text = new SpanUtils()
                .append(close.split("\\.")[0])
                .append("." + close.split("\\.")[1]).setFontSize(16, true)
                .append(" USDT").setFontSize(16, true)
                .create();
        return text;
    }

    private String transWalletTotal(double spotWalletTotal) {
        String close = DfUtils.formatNum(MathUtils.getRundNumber(spotWalletTotal, 6, null));
        return "≈ " + close + " CNY " + mContext.getString(R.string.total_assets_convert);
    }

    private String initWalletAvailable(double spotWalletTotal) {
        String close = DfUtils.formatNum(MathUtils.getRundNumber(spotWalletTotal, 6, null));
        return mContext.getString(R.string.available_funds) + close + " USDT";
    }

    private String initWalletFreeze(double spotWalletTotal) {
        String close = DfUtils.formatNum(MathUtils.getRundNumber(spotWalletTotal, 6, null));
        return mContext.getString(R.string.freezing_funds) + close + " USDT";
    }

    private boolean isVisible2User() {
        return  Constant.accountPage == 2;
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
