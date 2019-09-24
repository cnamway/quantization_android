package com.spark.qtz.app.trade.contract.vp.position;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.example.modulecfd.CfdClient;
import com.example.modulecfd.pojo.CfdAllThumbResult;
import com.example.modulecfd.pojo.CfdPositionResult;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.wsclient.base.WsCMD;
import com.spark.wsclient.pojo.CfdThumbBean;
import com.spark.wsclient.utils.WsConstant;
import com.spark.wsclient.utils.WsUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.spark.mvvm.base.BaseRequestCode;
import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.bus.event.SingleLiveEvent;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.SPUtils;
import me.spark.mvvm.utils.WebSocketRequest;
import me.spark.mvvm.utils.WebSocketResponse;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-07-29
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class PositionVPViewModel extends BaseViewModel {
    public PositionVPViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<String> profitAndLossAll = new ObservableField<>("");
    public ObservableField<Boolean> isProfitOrLoss = new ObservableField<>(true);

    public double mCurrentClose;
    public String mCurrentSymbol = "";
    public String mScreenName = "";
    private OnRequestListener mOnRequestListener;
    public boolean isLoadDate = false;
    public UIChangeObservable uc = new UIChangeObservable();
    public boolean isWsReceive = false;

    public class UIChangeObservable {
        public SingleLiveEvent<Double> mCurrentCloseObserve = new SingleLiveEvent<>();
        public SingleLiveEvent<String> mRefresh = new SingleLiveEvent<>();
        public SingleLiveEvent<Boolean> mLoginStatue = new SingleLiveEvent<>();
    }

    public void initSreenName(String screenName){
        this.mScreenName = screenName;
    }
    public void loadPositionData(String symbol, OnRequestListener onRequestListener) {
        isLoadDate = true;
        mCurrentSymbol = symbol;
        profitAndLossAll.set("--");
        this.mOnRequestListener = onRequestListener;
        if (!Constant.isCfdWSLoginIN) {
            loginWs(WsConstant.CODE_CFD_TRADE, SPUtils.getInstance().getCfdCookie());
        }
        CfdClient.getInstance().orderPosition();
        LogUtils.e("cfdVpRequest", "loadPositionData");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(final EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            //币种缩略图推送
            case EvKey.cfdAllSymbolPush:
                if (!App.getInstance().isAppLogin()) return;
                synchronized (this) {
                    if (eventBean.isStatue() && !isLoadDate) {
                        for (CfdAllThumbResult.DataBean dataBean : ((CfdAllThumbResult) eventBean.getObject()).getData()) {
                            if (dataBean.getScreenName().equals(mCurrentSymbol)) {
                                mCurrentClose = dataBean.getClose();
                                uc.mCurrentCloseObserve.setValue(mCurrentClose);
                                break;
                            }
                        }
                    }
                }
                break;
            //持仓明细
            case EvKey.cfdOrderPosition:
                if (eventBean.isStatue()) {
                    CfdPositionResult cfdPositionResult = (CfdPositionResult) eventBean.getObject();
                    EventBusUtils.postSuccessEvent(EvKey.profitAndLossAllObserve, BaseRequestCode.OK, "", cfdPositionResult);
                    mOnRequestListener.onSuccess(cfdPositionResult);
                } else {
                    mOnRequestListener.onFail(eventBean.getMessage());
                }
                break;
            //平仓
            case EvKey.cfdOrderClose:
                if (eventBean.isStatue()) {
                    Toasty.showSuccess(App.getInstance().getString(R.string.position_close_success));
                    EventBusUtils.postSuccessEvent(EvKey.cfdRefresh, BaseRequestCode.OK, mCurrentSymbol, mCurrentClose);
                    EventBusUtils.postSuccessEvent(EvKey.cfdWalletRefresh, BaseRequestCode.OK, "");
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //设置止盈止损
            case EvKey.cfdOrderProfitLoss:
                if (eventBean.getType() != 0) return;
                if (eventBean.isStatue()) {
                    Toasty.showSuccess(App.getInstance().getString(R.string.set_profit_loss_success));
                    EventBusUtils.postSuccessEvent(EvKey.cfdRefresh, BaseRequestCode.OK, mCurrentSymbol, mCurrentClose);
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            case EvKey.cfdRefreshSceenName:
                initSreenName(eventBean.getMessage());
                break;
            //刷新
            case EvKey.cfdRefresh:
                isLoadDate = false;
                isWsReceive = false;
                mCurrentClose = (double) eventBean.getObject();
                uc.mRefresh.setValue(eventBean.getMessage());
                break;
            case EvKey.logout_success_401:
                if (eventBean.isStatue()) {
                    dismissDialog();
                }
                break;
            //登录状态监听
            case EvKey.loginStatue:
                uc.mLoginStatue.setValue(App.getInstance().isAppLogin());
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(WebSocketResponse webSocketResponse) {
        switch (webSocketResponse.getCmd()) {
            //缩略图推送
            case WsCMD.PUSH_CFD_THUMB:
                Constant.lastCfdKlinePushTime = System.currentTimeMillis();
                if (isLoadDate) return;
                try {
                    String json = "{\"date\":" + webSocketResponse.getResponse() + "}";
                    CfdThumbBean cfdThumbBean = App.gson.fromJson(json, CfdThumbBean.class);
                    for (CfdThumbBean.DateBean dateBean : cfdThumbBean.getDate()) {
                        if (mCurrentSymbol.startsWith(dateBean.getSymbol())) {
                            mCurrentClose = dateBean.getClose();
                            uc.mCurrentCloseObserve.setValue(mCurrentClose);
                            LogUtils.e("mCurrentClose", mCurrentSymbol + "-->" + mCurrentClose);
                            break;
                        }
                    }
                } catch (Exception e) {
                    LogUtils.e("Exception", e.toString());
                }
                break;
            //Ws 登录
            case WsCMD.JSONLOGIN:
                if (webSocketResponse.getCode() == BaseRequestCode.OK) {
                    if (webSocketResponse.getType() == WsConstant.CODE_CFD_TRADE) {
                        Constant.isCfdWSLoginIN = true;
                    }
                } else {
                    if (webSocketResponse.getType() == WsConstant.CODE_CFD_TRADE) {
                        Constant.isCfdWSLoginIN = false;
                    }
                }
                break;
            case WsCMD.PUSH_EXCHANGE_ORDER_TRADE:
                if (!isWsReceive) {
                    uc.mRefresh.setValue(mCurrentSymbol);
                    isWsReceive = true;
                }
                break;
            case WsCMD.PUSH_EXCHANGE_ORDER_COMPLETED:
                if (!isWsReceive) {
                    LogUtils.e("asdf",mCurrentSymbol);
                    uc.mRefresh.setValue(mCurrentSymbol);
                    isWsReceive = true;
                }
                break;
            case WsCMD.ACCEPT_ORDER:
                if (!isWsReceive) {
                    uc.mRefresh.setValue(mCurrentSymbol);
                    isWsReceive = true;
                }
                break;
            case WsCMD.RISK_FLAT_NOTIFY:
                if (!isWsReceive) {
                    uc.mRefresh.setValue(mCurrentSymbol);
                    isWsReceive = true;
                }
                break;
            default:
                break;
        }
    }

    /**
     * 登录BB和CFD的Ws
     *
     * @param type
     * @param cookie
     */
    private void loginWs(int type, String cookie) {
        WebSocketRequest mWebSocketRequest = new WebSocketRequest();
        mWebSocketRequest.setCode(type);
        mWebSocketRequest.setCmd(WsCMD.JSONLOGIN);
        mWebSocketRequest.setBody(App.gson.toJson(WsUtils.setLoginJsonMap(cookie)).getBytes());
        EventBusUtils.postEvent(mWebSocketRequest);
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
