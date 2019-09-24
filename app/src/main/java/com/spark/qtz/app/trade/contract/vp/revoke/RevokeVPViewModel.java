package com.spark.qtz.app.trade.contract.vp.revoke;

import android.app.Application;
import android.support.annotation.NonNull;

import com.example.modulecfd.CfdClient;
import com.spark.qtz.App;
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
public class RevokeVPViewModel extends BaseViewModel {
    public RevokeVPViewModel(@NonNull Application application) {
        super(application);
    }

    public String mCurrentSymbol = "";
    public String mScreenName = "";
    private OnRequestListener mOnRequestListener;
    public boolean isLoadDate = false;
    public boolean isWsReceive = false;

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<String> mRefresh = new SingleLiveEvent<>();
        public SingleLiveEvent<Integer> loadMore = new SingleLiveEvent<>();
        public SingleLiveEvent<Boolean> mLoginStatue = new SingleLiveEvent<>();
    }

    public void initSreenName(String screenName){
        this.mScreenName = screenName;
    }

    public void loadRevokeData(String symbol, int pageNo, OnRequestListener onRequestListener) {
        isLoadDate = true;
        this.mOnRequestListener = onRequestListener;
        mCurrentSymbol = symbol;
        if (!Constant.isCfdWSLoginIN) {
            loginWs(WsConstant.CODE_CFD_TRADE, SPUtils.getInstance().getCfdCookie());
        }
        CfdClient.getInstance().orderRevoke(symbol, pageNo);
        LogUtils.e("cfdVpRequest", "loadRevokeData");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(final EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            //当前委托
            case EvKey.cfdOrderRevoke:
                if (eventBean.isStatue()) {
                    mOnRequestListener.onSuccess(eventBean.getObject());
                } else {
                    mOnRequestListener.onFail(eventBean.getMessage());
                }
                break;
            case EvKey.cfdRefreshSceenName:
                initSreenName(eventBean.getMessage());
                break;
            //刷新
            case EvKey.cfdRefresh:
                isLoadDate = false;
                isWsReceive = false;
                uc.mRefresh.setValue(eventBean.getMessage());
                break;
            //滑到底部
            case EvKey.contractSrollEnd:
                if (eventBean.getCode() != 3) return;
                uc.loadMore.setValue(eventBean.getCode());
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
            case WsCMD.PUSH_EXCHANGE_ORDER_CANCELED:
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
