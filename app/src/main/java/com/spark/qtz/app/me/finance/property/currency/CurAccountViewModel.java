package com.spark.qtz.app.me.finance.property.currency;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.lxj.xpopup.XPopup;
import com.spark.acclient.CaptchaAcClient;
import com.spark.acclient.FinanceClient;
import com.spark.acclient.pojo.CoinAddressBean;
import com.spark.acclient.pojo.CoinExtractBean;
import com.spark.acclient.pojo.CoinExtractSubmitBean;
import com.spark.acclient.pojo.CoinSupportBean;
import com.spark.acclient.pojo.CoinWithdrawAddressResult;
import com.spark.acclient.pojo.MerberWalletResult;
import com.spark.acclient.pojo.SpotWalletResult;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.ui.popup.CoinChoosePopup;
import com.spark.qtz.ui.popup.CoinExtractPopup;
import com.spark.qtz.ui.popup.CoinExtractSubmitPopup;
import com.spark.qtz.ui.popup.CoinRechargePopup;
import com.spark.qtz.ui.popup.CoinTransPopup;
import com.spark.qtz.ui.popup.CoinWithdrawAddressPopup;
import com.spark.qtz.ui.popup.SmsVerifyPopup;
import com.spark.qtz.ui.popup.impl.OnCoinChooseListener;
import com.spark.qtz.ui.popup.impl.OnCoinExtractListener;
import com.spark.qtz.ui.popup.impl.OnCoinExtractSubmitListener;
import com.spark.qtz.ui.popup.impl.OnCoinRechargeListener;
import com.spark.qtz.ui.popup.impl.OnEtContentListener;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.otcclient.AdvertiseScanClient;
import com.spark.otcclient.pojo.LcCoinListResult;

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
import me.spark.mvvm.pojo.User;
import me.spark.mvvm.utils.DfUtils;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.MathUtils;
import me.spark.mvvm.utils.SPUtils;
import me.spark.mvvm.utils.SpanUtils;
import me.spark.mvvm.utils.StringUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/28
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CurAccountViewModel extends BaseViewModel {
    public CurAccountViewModel(@NonNull Application application) {
        super(application);
    }

    private Context mContext;
    public ObservableField<CharSequence> spotWalletTotalChar = new ObservableField<>();
    public ObservableField<String> spotWalletTransChar = new ObservableField<>();
    private OnRequestListener mOnRequestListener;
    private int getCoinSupportType = 0;           //0 - 充币  1 - 提币   2 - 划转
    private CoinSupportBean.DataBean mCoinSupportBean;
    private CoinExtractPopup mCoinExtractPopup;
    private CoinExtractBean mCoinExtractBean;
    private CoinTransPopup mCoinTransPopup;
    private CoinSupportBean mSpotTransSupportBean;
    private double walletTotal, walletTotalTrans;
    private User mUser;
    public int type = 0; //0 - 手机注册 1 - 邮箱注册
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
            spotWalletTotalChar.set("****** USDT");
            spotWalletTransChar.set("≈ **** CNY " + mContext.getString(R.string.total_assets_convert));
        } else {
            spotWalletTotalChar.set(initWalletTotal(walletTotal));
            spotWalletTransChar.set(transWalletTotal(walletTotalTrans));
        }
    }

    public BindingCommand coinInCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            getCoinSupportType = 0;
            getCoinSupport();
        }
    });

    public BindingCommand coinOutCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            getCoinSupportType = 1;
            getCoinSupport();
        }
    });

    public BindingCommand coinTransCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            getCoinSupportType = 2;
            getCoinSupport();
        }
    });


    public void initSpotWallet(Context context, OnRequestListener mOnRequestListener) {
        this.mContext = context;
        this.mOnRequestListener = mOnRequestListener;
        FinanceClient.getInstance().getCoinWallet("SPOT");
//        PubClient.getInstance().findDigitalToLegalCoinRate("USDT", "CNY");
        mUser = App.getInstance().getCurrentUser();
        if (mUser != null) {
            if (StringUtils.isNotEmpty(mUser.getMobilePhone())) {
                type = 0;
            } else {
                type = 1;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        if ((!isVisible2User())
                && (!eventBean.getOrigin().equals(EvKey.walletChange))
                && (!eventBean.getOrigin().equals(EvKey.coinWallet)))
            return;
        switch (eventBean.getOrigin()) {
            //spot钱包查询业务处理
            case EvKey.coinWallet:
                if (mOnRequestListener == null || eventBean.getType() != 0) return;
                if (eventBean.isStatue()) {
                    updateSpotInfo((SpotWalletResult) eventBean.getObject());
                } else {
                    mOnRequestListener.onFail(eventBean.getMessage());
                }
                break;
            //查询平台支持到币种信息
            case EvKey.coinSupport:
                dismissDialog();
                if (eventBean.isStatue()) {
                    final CoinSupportBean coinSupportBean = (CoinSupportBean) eventBean.getObject();
                    if (getCoinSupportType == 0 || getCoinSupportType == 1) {
                        new XPopup.Builder(mContext)
                                .asCustom(new CoinChoosePopup(mContext, coinSupportBean.getData(), new OnCoinChooseListener() {
                                    @Override
                                    public void onClickOrder() {
                                        //记录
                                        // TODO: 2019/5/21
                                    }

                                    @Override
                                    public void onClickItem(int position) {
                                        mCoinSupportBean = coinSupportBean.getData().get(position);
                                        //item点击
                                        switch (getCoinSupportType) {
                                            case 0:
                                                getCoinInInfo(coinSupportBean.getData().get(position).getCoinName());
                                                break;
                                            case 1:
                                                getCoinOutInfo(coinSupportBean.getData().get(position).getCoinName());
                                                break;
                                        }
                                    }
                                }))
                                .show();
                    } else {
                        if (coinSupportBean.getData().isEmpty()) {
                            Toasty.showError(App.getInstance().getString(R.string.no_support_coin));
                            return;
                        }
                        mSpotTransSupportBean = coinSupportBean;
                        if (!Constant.lcCoinPairThumbBeanList.isEmpty()) {
                            mCoinTransPopup = new CoinTransPopup(mContext, 0, coinSupportBean);
                            new XPopup.Builder(mContext)
                                    .asCustom(mCoinTransPopup)
                                    .show();
                        } else {
                            AdvertiseScanClient.getInstance().getIndexTradeCoinList();
                        }
                    }
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //查询法币可用币种
            case EvKey.indexCoinList:
                if (eventBean.isStatue()) {
                    Constant.lcCoinPairThumbBeanList.clear();
                    for (LcCoinListResult.DataBean dataBean : ((LcCoinListResult) eventBean.getObject()).getData()) {
                        if (!Constant.lcCoinPairThumbBeanList.contains(dataBean.getCoinName()))
                            Constant.lcCoinPairThumbBeanList.add(dataBean.getCoinName());
                    }
                    mCoinTransPopup = new CoinTransPopup(mContext, 0, mSpotTransSupportBean);
                    new XPopup.Builder(mContext)
                            .asCustom(mCoinTransPopup)
                            .show();
                } else {
                    Toasty.showError(App.getInstance().getString(R.string.network_abnormal));
                }
                break;
            //查询指定币种的钱包地址
            case EvKey.coinAddress:
                dismissDialog();
                if (eventBean.isStatue()) {
                    CoinAddressBean coinAddressBean = (CoinAddressBean) eventBean.getObject();
                    new XPopup.Builder(mContext)
                            .asCustom(new CoinRechargePopup(mContext, coinAddressBean, mCoinSupportBean, new OnCoinRechargeListener() {
                                @Override
                                public void onClickOrder() {
                                    //记录
                                    // TODO: 2019/5/21
                                }

                                @Override
                                public void onClickChoose() {
                                    //选择币种
                                    getCoinSupport();
                                }
                            }))
                            .show();
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //查询用户某个业务所有资产信息
            case EvKey.merberWallet:
                dismissDialog();
                if (eventBean.isStatue()) {
                    MerberWalletResult merberWalletResult = (MerberWalletResult) eventBean.getObject();
                    mCoinExtractPopup = new CoinExtractPopup(mContext, merberWalletResult, mCoinSupportBean, new OnCoinExtractListener() {
                        @Override
                        public void onReceiveCoinExtract(CoinExtractSubmitBean coinExtractSubmitBean) {
                            new XPopup.Builder(mContext)
                                    .asCustom(new CoinExtractSubmitPopup(mContext, coinExtractSubmitBean, new OnCoinExtractSubmitListener() {
                                        @Override
                                        public void onReceiveCoinExtractSubmit(final CoinExtractBean coinExtractBean) {
                                            mCoinExtractBean = coinExtractBean;
                                            showDialog(mContext.getString(R.string.loading));
                                            CaptchaAcClient.getInstance().sendVertifyCode(type);
                                        }
                                    }))
                                    .show();
                        }
                    });
                    new XPopup.Builder(mContext)
                            .asCustom(mCoinExtractPopup)
                            .show();
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
            //获取短信验证码
            case EvKey.acPhoneCaptcha:
                dismissDialog();
                if (eventBean.isStatue()) {
                    new XPopup.Builder(mContext)
                            .asCustom(new SmsVerifyPopup(mContext, App.getInstance().getCurrentUser().getMobilePhone(), new OnEtContentListener() {
                                @Override
                                public void onCEtContentInput(String content) {
                                    showDialog(mContext.getString(R.string.loading));
                                    FinanceClient.getInstance().withdrawRequest(content, mCoinExtractBean);
                                }
                            })).show();
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //查询用户的某个币种的提币地址信息
            case EvKey.coinWithdrawAddress:
                dismissDialog();
                if (eventBean.isStatue()) {
                    CoinWithdrawAddressResult coinWithdrawAddressResult = (CoinWithdrawAddressResult) eventBean.getObject();
                    new XPopup.Builder(mContext)
                            .asCustom(new CoinWithdrawAddressPopup(mContext, coinWithdrawAddressResult.getData(), new OnEtContentListener() {
                                @Override
                                public void onCEtContentInput(String content) {
                                    mCoinExtractPopup.updateAddress(content);
                                }
                            }))
                            .show();
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //提币
            case EvKey.coinWithdrawRequest:
                dismissDialog();
                if (eventBean.isStatue()) {
                    Toasty.showSuccess(mContext.getString(R.string.coin_out_success));
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //划转
            case EvKey.coinTransfer:
                if (eventBean.isStatue()) {
                    FinanceClient.getInstance().getCoinWallet("SPOT");
                    EventBusUtils.postSuccessEvent(EvKey.walletChange, BaseRequestCode.OK, "");
                    if (mCoinTransPopup != null && mCoinTransPopup.isShow()) {
                        mCoinTransPopup.dismiss();
                    }
                    Toasty.showSuccess(mContext.getString(R.string.coin_trans_success));
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            case EvKey.walletChange:
                FinanceClient.getInstance().getCoinWallet("SPOT");
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
        initAccountText(SPUtils.getInstance().isHideAccountSpot());
        mOnRequestListener.onSuccess(spotWalletResult);
    }

    /**
     * 查询平台支持到币种信息
     */
    public void getCoinSupport() {
        showDialog(mContext.getString(R.string.loading));
        FinanceClient.getInstance().getCoinSupport();
    }

    /**
     * 充币详情
     *
     * @param coinAddress
     */
    public void getCoinInInfo(String coinAddress) {
        showDialog(mContext.getString(R.string.loading));
        FinanceClient.getInstance().getCoinAddress(coinAddress);
    }

    /**
     * 提币详情
     *
     * @param coinAddress
     */
    public void getCoinOutInfo(String coinAddress) {
        showDialog(mContext.getString(R.string.loading));
        FinanceClient.getInstance().getCoinOutInfo(coinAddress);
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
        return "≈ " + close + " CNY " + mContext.getString(R.string.total_assets_convert);
    }


    private boolean isVisible2User() {
        return Constant.accountPage == 0;
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
