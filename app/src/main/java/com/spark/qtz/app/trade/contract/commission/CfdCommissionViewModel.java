package com.spark.qtz.app.trade.contract.commission;

import android.app.Application;
import android.support.annotation.NonNull;

import com.example.modulecfd.pojo.CfdAllThumbResult;
import com.example.modulecfd.pojo.CfdCommissionResult;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.wsclient.base.WsCMD;
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
import me.spark.mvvm.utils.SPUtils;
import me.spark.mvvm.utils.WebSocketRequest;
import me.spark.mvvm.utils.WebSocketResponse;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-06-27
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CfdCommissionViewModel extends BaseViewModel {
    public CfdCommissionViewModel(@NonNull Application application) {
        super(application);
    }

    public double mCurrentClose;
    public String mCurrentSymbol = "";

    public String interceptSymbol;
    public int interceptPage;
    private OnRequestListener mOnRequestListener;
    public boolean isLoadDate = false;

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<Double> mCurrentCloseObserve = new SingleLiveEvent<>();
        public SingleLiveEvent<Boolean> mLoginStatue = new SingleLiveEvent<>();
        public SingleLiveEvent<String> mRefresh = new SingleLiveEvent<>();
        public SingleLiveEvent<Integer> loadMore = new SingleLiveEvent<>();
    }

    public void loadCommissionData(String symbol, int pageNo, OnRequestListener onRequestListener) {
        isLoadDate = true;
        this.mOnRequestListener = onRequestListener;
        mCurrentSymbol = symbol;
        if (!Constant.isCfdWSLoginIN) {
            interceptSymbol = symbol;
            interceptPage = pageNo;
            loginWs(WsConstant.CODE_CFD_TRADE, SPUtils.getInstance().getCfdCookie());
            return;
        }
        WebSocketRequest mWebSocketRequest = new WebSocketRequest();
        mWebSocketRequest.setCode(WsConstant.CODE_CFD_TRADE);
        mWebSocketRequest.setCmd(WsCMD.CFD_CURRENT_ORDER);
        mWebSocketRequest.setBody(App.gson.toJson(WsUtils.setCfdOrderDetailJsonMap(symbol, String.valueOf(pageNo), "10")).getBytes());
        EventBusUtils.postEvent(mWebSocketRequest);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            //币种缩略图推送
            case EvKey.cfdAllSymbolPush:
                synchronized (this) {
                    if (eventBean.isStatue() && !isLoadDate) {
                        for (CfdAllThumbResult.DataBean dataBean : ((CfdAllThumbResult) eventBean.getObject()).getData()) {
                            if (dataBean.getSymbol().equals(mCurrentSymbol)) {
                                mCurrentClose = dataBean.getClose();
                                uc.mCurrentCloseObserve.setValue(mCurrentClose);
                                break;
                            }
                        }
                    }
                }
                break;
            //撤单
            case EvKey.cfdOrderCancel:
                if (eventBean.isStatue()) {
                    Toasty.showSuccess(App.getInstance().getString(R.string.withdrawal_success));
                    EventBusUtils.postSuccessEvent(EvKey.cfdRefresh, BaseRequestCode.OK, mCurrentSymbol, mCurrentClose);
                    EventBusUtils.postSuccessEvent(EvKey.cfdWalletRefresh, BaseRequestCode.OK, "");
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //设置止盈止损
            case EvKey.cfdOrderProfitLoss:
                if (eventBean.getType() != 1) return;
                if (eventBean.isStatue()) {
                    Toasty.showSuccess(App.getInstance().getString(R.string.set_profit_loss_success));
                    EventBusUtils.postSuccessEvent(EvKey.cfdRefresh, BaseRequestCode.OK, mCurrentSymbol, mCurrentClose);
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //刷新
            case EvKey.cfdRefresh:
                mCurrentClose = (double) eventBean.getObject();
                uc.mRefresh.setValue(eventBean.getMessage());
                break;
            //滑到底部
            case EvKey.contractSrollEnd:
                if (eventBean.getCode() != 1) return;
                uc.loadMore.setValue(eventBean.getCode());
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
            //Ws 登录
            case WsCMD.JSONLOGIN:
                if (webSocketResponse.getCode() == BaseRequestCode.OK) {
                    if (webSocketResponse.getType() == WsConstant.CODE_CFD_TRADE) {
                        Constant.isCfdWSLoginIN = true;
                        loadCommissionData(interceptSymbol, interceptPage, mOnRequestListener);
                    }
                } else {
                    if (webSocketResponse.getType() == WsConstant.CODE_CFD_TRADE) {
                        Constant.isCfdWSLoginIN = false;
                    }
                }
                break;
            //当前委托
            case WsCMD.CFD_CURRENT_ORDER:
                try {
                    CfdCommissionResult cfdCommissionResult = App.gson.fromJson(webSocketResponse.getResponse(), CfdCommissionResult.class);
                    mOnRequestListener.onSuccess(cfdCommissionResult);
                } catch (Exception e) {
                    mOnRequestListener.onFail(e.toString());
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
