package com.spark.qtz.app.trade.contract.deal;

import android.app.Application;
import android.support.annotation.NonNull;

import com.example.modulecfd.pojo.CfdDealResult;
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
public class CfdDealViewModel extends BaseViewModel {
    public CfdDealViewModel(@NonNull Application application) {
        super(application);
    }

    public String mCurrentSymbol = "";

    public String interceptSymbol;
    public int interceptPage;
    private OnRequestListener mOnRequestListener;
    public boolean isLoadDate = false;

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<Boolean> mLoginStatue = new SingleLiveEvent<>();
        public SingleLiveEvent<String> mRefresh = new SingleLiveEvent<>();
        public SingleLiveEvent<Integer> loadMore = new SingleLiveEvent<>();
    }

    public void loadDealData(String symbol, int pageNo, OnRequestListener onRequestListener) {
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
        mWebSocketRequest.setCmd(WsCMD.CFD_TURNOVER_DETAIL);
        mWebSocketRequest.setBody(App.gson.toJson(WsUtils.setCfdOrderDetailJsonMap(symbol, String.valueOf(pageNo), "10")).getBytes());
        EventBusUtils.postEvent(mWebSocketRequest);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            //刷新
            case EvKey.cfdRefresh:
                uc.mRefresh.setValue(eventBean.getMessage());
                break;
            //滑到底部
            case EvKey.contractSrollEnd:
                if (eventBean.getCode() != 2) return;
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
                        loadDealData(interceptSymbol, interceptPage, mOnRequestListener);
                    }
                } else {
                    if (webSocketResponse.getType() == WsConstant.CODE_CFD_TRADE) {
                        Constant.isCfdWSLoginIN = false;
                    }
                }
                break;
            //已成交
            case WsCMD.CFD_TURNOVER_DETAIL:
                try {
                    CfdDealResult cfdDealResult = App.gson.fromJson(webSocketResponse.getResponse(), CfdDealResult.class);
                    mOnRequestListener.onSuccess(cfdDealResult);
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
