package com.spark.qtz.app.trade.legal_currency;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lxj.xpopup.XPopup;
import com.spark.acclient.pojo.CoinSupportBean;
import com.spark.acclient.pojo.MerberWalletResult;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.ui.popup.CoinTransPopup;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.otcclient.AdvertiseScanClient;
import com.spark.otcclient.LcTradeClient;
import com.spark.otcclient.pojo.AuthMerchantResult;
import com.spark.otcclient.pojo.LcCoinListResult;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.spark.mvvm.base.BaseRequestCode;
import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.bus.event.SingleLiveEvent;
import me.spark.mvvm.http.GeneralResult;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/24
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class LegalCurrencyViewModel extends BaseViewModel {
    public LegalCurrencyViewModel(@NonNull Application application) {
        super(application);
    }

    private CoinTransPopup mCoinTransPopup;
    private CoinSupportBean mSpotTransSupportBean;
    private Context mContext;
    private OnRequestListener mOnRequestListener;
    public boolean isOnPause = true;
    private boolean isPopRequest = false;

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<LcCoinListResult> mTradeCoinListBeanSingleLiveEvent = new SingleLiveEvent<>();
        public SingleLiveEvent<Boolean> isRefresh = new SingleLiveEvent<>();
    }

    public void initContext(Context context) {
        this.mContext = context;
    }

    public void getTradeCoinList(OnRequestListener onRequestListener) {
        this.mOnRequestListener = onRequestListener;
        isPopRequest = false;
        AdvertiseScanClient.getInstance().getIndexTradeCoinList();
    }

    public void loadCountry(OnRequestListener onRequestListener) {
        showDialog(App.getInstance().getString(R.string.str_requesting));
        this.mOnRequestListener = onRequestListener;
        AdvertiseScanClient.getInstance().getTradeAreaList();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        if ((!isVisible2User() || isOnPause) && (!eventBean.getOrigin().equals(EvKey.loginStatue)))
            return;
        switch (eventBean.getOrigin()) {
            //查询所有的币种
            case EvKey.indexCoinList:
                if (isPopRequest) {
                    if (eventBean.isStatue()) {
                        Constant.lcCoinPairThumbBeanList.clear();
                        for (LcCoinListResult.DataBean dataBean : ((LcCoinListResult) eventBean.getObject()).getData()) {
                            if (!Constant.lcCoinPairThumbBeanList.contains(dataBean.getCoinName()))
                                Constant.lcCoinPairThumbBeanList.add(dataBean.getCoinName());
                        }
                        mCoinTransPopup = new CoinTransPopup(mContext, 1, mSpotTransSupportBean);
                        new XPopup.Builder(mContext)
                                .asCustom(mCoinTransPopup)
                                .show();
                    } else {
                        Toasty.showError(App.getInstance().getString(R.string.network_abnormal));
                    }
                } else {
                    if (eventBean.isStatue()) {
                        mOnRequestListener.onSuccess(eventBean.getObject());
                    } else {
                        mOnRequestListener.onSuccess(eventBean.getMessage());
                    }
                }
                break;
            //查询所有的交易区
            case EvKey.tradeAreaList:
                dismissDialog();
                if (eventBean.isStatue()) {
                    mOnRequestListener.onSuccess(eventBean.getObject());
                } else {
                    mOnRequestListener.onFail(eventBean.getMessage());
                }
                break;
            //查询平台支持到币种信息
            case EvKey.coinSupport:
                if (eventBean.isStatue()) {
                    CoinSupportBean coinSupportBean = (CoinSupportBean) eventBean.getObject();
                    if (coinSupportBean.getData().isEmpty()) {
                        Toasty.showError(App.getInstance().getString(R.string.no_support_coin));
                        return;
                    }
                    mSpotTransSupportBean = coinSupportBean;
                    if (!Constant.lcCoinPairThumbBeanList.isEmpty()) {
                        mCoinTransPopup = new CoinTransPopup(mContext, 1, coinSupportBean);
                        new XPopup.Builder(mContext)
                                .asCustom(mCoinTransPopup)
                                .show();
                    } else {
                        isPopRequest = true;
                        AdvertiseScanClient.getInstance().getIndexTradeCoinList();
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
                    Toasty.showSuccess(App.getInstance().getApplicationContext().getString(R.string.coin_trans_success));
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //判断是否商家认证
            case EvKey.authMerchantFind:
                if (eventBean.isStatue()) {
                    AuthMerchantResult authMerchantResult = (AuthMerchantResult) eventBean.getObject();
                    int certifiedBusinessStatus = authMerchantResult.getData().getCertifiedBusinessStatus();
                    if (certifiedBusinessStatus == 2 || certifiedBusinessStatus == 6) {
                        ARouter.getInstance().build(ARouterPath.ACTIVITY_TRADE_AD_CREATE)
                                .navigation();
                    } else {
                        Toasty.showError(App.getInstance().getString(R.string.str_auth_merchant));
                    }
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //用户名修改校验
            case EvKey.usernameChangeCheck:
                if (eventBean.isStatue()) {
                    GeneralResult generalResult = (GeneralResult) eventBean.getObject();
                    if (generalResult.getCode() == BaseRequestCode.OK) {
                        LcTradeClient.getInstance().authMerchantFind();
                    } else {
                        Toasty.showError(eventBean.getMessage());
                    }
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            case EvKey.logout_success_401:
                if (eventBean.isStatue()) {
                    uc.isRefresh.setValue(true);
                }
                break;
            default:
                break;
        }
    }


    private boolean isVisible2User() {
        return Constant.isTradeVisiable && Constant.tradePage == 0;
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

    @Override
    public void onResume() {
        super.onResume();
        isOnPause = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        isOnPause = true;
    }
}
