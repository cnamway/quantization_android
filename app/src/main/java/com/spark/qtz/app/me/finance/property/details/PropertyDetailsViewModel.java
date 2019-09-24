package com.spark.qtz.app.me.finance.property.details;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lxj.xpopup.XPopup;
import com.spark.acclient.CaptchaAcClient;
import com.spark.acclient.FinanceClient;
import com.spark.acclient.pojo.CoinAddressBean;
import com.spark.acclient.pojo.CoinExtractBean;
import com.spark.acclient.pojo.CoinExtractSubmitBean;
import com.spark.acclient.pojo.CoinSupportBean;
import com.spark.acclient.pojo.CoinWithdrawAddressResult;
import com.spark.acclient.pojo.MerberWalletResult;
import com.spark.acclient.pojo.PropertyDetailsTypeResult;
import com.spark.acclient.pojo.SpotWalletResult;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
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
import me.spark.mvvm.utils.StringUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/4
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class PropertyDetailsViewModel extends BaseViewModel {
    private SpotWalletResult.DataBean mDataBean;
    private String mBusiType;
    private OnRequestListener onRequestListener;
    private Context mContext;
    private int getCoinSupportType = 0;           //0 - 充币  1 - 提币   2 - 划转
    private CoinSupportBean.DataBean mCoinSupportBean;
    private CoinExtractPopup mCoinExtractPopup;
    private CoinExtractBean mCoinExtractBean;
    private CoinTransPopup mCoinTransPopup;
    private CoinSupportBean mSpotTransSupportBean;

    public PropertyDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<String> coinName = new ObservableField<>();
    public ObservableField<String> balance = new ObservableField<>();
    public ObservableField<String> frozenBalance = new ObservableField<>();
    public ObservableField<String> transCNY = new ObservableField<>();
    private User mUser;
    public int type = 0; //0 - 手机注册 1 - 邮箱注册
    //充币
    public BindingCommand coinInCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            getCoinSupportType = 0;
            getCoinSupport();
        }
    });

    //提币
    public BindingCommand coinOutCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            getCoinSupportType = 1;
            getCoinSupport();
        }
    });

    //划转
    public BindingCommand coinTransCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            getCoinSupportType = 2;
            getCoinSupport();
        }
    });

    //去交易
    public BindingCommand coinTradeCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            EventBusUtils.postJumpEvent(EvKey.fromPropertyDetails);
            finish();
        }
    });

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<Boolean> refresh = new SingleLiveEvent<>();
        public SingleLiveEvent<PropertyDetailsTypeResult> mainType = new SingleLiveEvent<>();
        public SingleLiveEvent<PropertyDetailsTypeResult> subType = new SingleLiveEvent<>();
    }

    public void init(Context context, String busiType, SpotWalletResult.DataBean propertDetailsBean) {
        this.mContext = context;
        this.mDataBean = propertDetailsBean;
        this.mBusiType = busiType;
        initViewDate();
        mUser = App.getInstance().getCurrentUser();
        if (mUser != null) {
            if (StringUtils.isNotEmpty(mUser.getMobilePhone())) {
                type = 0;
            } else {
                type = 1;
            }
        }
    }

    public void getType() {
        Constant.isContract = false;
        FinanceClient.getInstance().getPropertyDetailsType(mContext);
        FinanceClient.getInstance().getPropertyDetailsTypeMain(mContext);
    }

    public void loadPageDateFilter(int page, String filterType, String filterSubType, String minLimit, String maxLimit, String startTime, String endTime, OnRequestListener onRequestListener) {
        this.onRequestListener = onRequestListener;
        FinanceClient.getInstance().getProperyDetails(page, mDataBean.getCoinId(), mBusiType, filterType, filterSubType, minLimit, maxLimit, startTime, endTime);
    }


    private void initViewDate() {
        coinName.set(DfUtils.numberFormat(mDataBean.getBalance() + mDataBean.getFrozenBalance(), 4) + " " + mDataBean.getCoinId());
        balance.set(DfUtils.numberFormat(mDataBean.getBalance(), 4));
        frozenBalance.set(DfUtils.numberFormat(mDataBean.getFrozenBalance(), 4));
        transCNY.set(DfUtils.numberFormat((mDataBean.getBalance() + mDataBean.getFrozenBalance()) * mDataBean.getLegalRate(), 2) + " CNY");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            case EvKey.propertyDetailsType:
                if (eventBean.isStatue()) {
                    Constant.propertyDetailsTypeJson = App.gson.toJson(eventBean.getObject());
                }
                uc.refresh.setValue(true);
                uc.subType.setValue((PropertyDetailsTypeResult) eventBean.getObject());
                break;
            case EvKey.propertyDetailsTypeMain:
                uc.mainType.setValue((PropertyDetailsTypeResult) eventBean.getObject());
                break;
            //otc钱包查询业务处理
            case EvKey.propertyDetails:
                if (eventBean.isStatue()) {
                    onRequestListener.onSuccess(eventBean.getObject());
                } else {
                    onRequestListener.onFail(eventBean.getMessage());
                }
                break;
            //锁仓
            case EvKey.propertyLockDetails:
                if (eventBean.isStatue()) {
                    onRequestListener.onSuccess(eventBean.getObject());
                } else {
                    onRequestListener.onFail(eventBean.getMessage());
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
                                        switch (getCoinSupportType) {
                                            case 0:
                                                ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_PROPERTY_RECORD)
                                                        .withInt("recordType", 0)
                                                        .navigation();
                                                break;
                                            case 1:
                                                ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_PROPERTY_RECORD)
                                                        .withInt("recordType", 1)
                                                        .navigation();
                                                break;
                                        }
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
                            mCoinTransPopup = new CoinTransPopup(mContext, mBusiType.equals("SPOT") ? 0 : 1, coinSupportBean, mDataBean.getCoinId());
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
                    mCoinTransPopup = new CoinTransPopup(mContext, mBusiType.equals("SPOT") ? 0 : 1, mSpotTransSupportBean, mDataBean.getCoinId());
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
            //币币选定币种提币信息
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
                    Toasty.showSuccess(App.getInstance().getString(R.string.coin_out_success));
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
                    Toasty.showSuccess(App.getInstance().getString(R.string.coin_trans_success));
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            default:
                break;
        }
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

    @Override
    public void onCreate() {
        super.onCreate();
        EventBusUtils.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtils.unRegister(this);
    }
}
