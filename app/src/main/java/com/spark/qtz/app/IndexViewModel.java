package com.spark.qtz.app;

import android.app.Application;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.spark.casclient.CasClient;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.api.pojo.CoinPairThumbBean;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.CheckErrorUtil;
import com.spark.wsclient.base.WsCMD;
import com.spark.wsclient.utils.WsConstant;
import com.spark.wsclient.utils.WsUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.spark.mvvm.base.BaseHost;
import me.spark.mvvm.base.BaseRequestCode;
import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.binding.command.BindingAction;
import me.spark.mvvm.binding.command.BindingCommand;
import me.spark.mvvm.bus.event.SingleLiveEvent;
import me.spark.mvvm.http.GeneralResult;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.SPUtils;
import me.spark.mvvm.utils.ServiceOpenSuccessEvent;
import me.spark.mvvm.utils.StringUtils;
import me.spark.mvvm.utils.WebSocketRequest;
import me.spark.mvvm.utils.WebSocketResponse;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/15
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class IndexViewModel extends BaseViewModel {
    public UIChangeObservable indexUC = new UIChangeObservable();
    private boolean isEnableSymbolGet = false;

    public class UIChangeObservable {
        public SingleLiveEvent<Integer> clickPosition = new SingleLiveEvent<>();
    }

    public IndexViewModel(@NonNull Application application) {
        super(application);
    }

    //Emex
    public BindingCommand emexOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            indexUC.clickPosition.setValue(0);
        }
    });
    //行情
    public BindingCommand quotesOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            indexUC.clickPosition.setValue(1);
        }
    });
    //我的
    public BindingCommand meOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            indexUC.clickPosition.setValue(2);
        }
    });

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(WebSocketResponse webSocketResponse) {
        switch (webSocketResponse.getCmd()) {
            case WsCMD.ENABLE_SYMBOL:
                if (isEnableSymbolGet) return;
                CoinPairThumbBean coinPairThumbBean = App.gson.fromJson(webSocketResponse.getResponse(),
                        CoinPairThumbBean.class);
                Constant.coinPairThumbBeanList.clear();
                Constant.coinPairThumbBeanList.add(App.getInstance().getString(R.string.favorites));
                for (CoinPairThumbBean.DataBean dataBean : coinPairThumbBean.getData()) {
                    if (!Constant.coinPairThumbBeanList.contains(dataBean.getBaseSymbol()))
                        Constant.coinPairThumbBeanList.add(dataBean.getBaseSymbol());
                }
                isEnableSymbolGet = true;
                break;
            //Ws 登录
            case WsCMD.JSONLOGIN:
                LogUtils.e("JSONLOGIN", webSocketResponse.getType() + "--" + webSocketResponse.getResponse());
                if (webSocketResponse.getCode() == BaseRequestCode.OK) {
                    switch (webSocketResponse.getType()) {
                        case WsConstant.CODE_BB_TRADE:
                            Constant.isSpotWSLoginIN = true;
                            loginWsPush(WsConstant.CODE_BB_TRADE);
                            break;
                        case WsConstant.CODE_CFD_TRADE:
                            Constant.isCfdWSLoginIN = true;
                            loginWsPush(WsConstant.CODE_CFD_TRADE);
                            break;
                    }
                } else {
                    switch (webSocketResponse.getType()) {
                        case WsConstant.CODE_BB_TRADE:
                            Constant.isSpotWSLoginIN = false;
                            break;
                        case WsConstant.CODE_CFD_TRADE:
                            Constant.isCfdWSLoginIN = false;
                            break;
                    }
                }
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            case EvKey.goQoutes:
                indexUC.clickPosition.setValue(1);
                break;
            case EvKey.goTrade:
                indexUC.clickPosition.setValue(2);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        EventBusUtils.postJumpEvent(EvKey.goTradeItem);
                    }
                }, 100);
                break;
            case EvKey.fromPropertyDetails:
                indexUC.clickPosition.setValue(2);
                break;
            case EvKey.loginStatue:
                if (App.getInstance().isAppLogin()) {
                    loginWs(WsConstant.CODE_BB_TRADE, SPUtils.getInstance().getSpotCookie());
                    loginWs(WsConstant.CODE_CFD_TRADE, SPUtils.getInstance().getCfdCookie());
                } else {
                    Constant.isSpotWSLoginIN = false;
                    Constant.isCfdWSLoginIN = false;
                }
                break;
            case EvKey.logout_fail_401:
                GeneralResult generalResult = (GeneralResult) eventBean.getObject();
                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                    if (StringUtils.isNotEmpty(generalResult.getUrl())) {
                        LogUtils.e("generalResult===" + generalResult.getCode() + ",generalResult.getUrl()==" + generalResult.getUrl());
                        if (generalResult.getUrl().contains("/" + BaseHost.TYPE_UC)) {
                            CasClient.getInstance().checkBusinessLogin(BaseHost.TYPE_UC);
                        } else if (generalResult.getUrl().contains("/" + BaseHost.TYPE_AC)) {
                            CasClient.getInstance().checkBusinessLogin(BaseHost.TYPE_AC);
                        } else if (generalResult.getUrl().contains("/" + BaseHost.TYPE_OTC)) {
                            CasClient.getInstance().checkBusinessLogin(BaseHost.TYPE_OTC);
                        } else if (generalResult.getUrl().contains("/" + BaseHost.TYPE_SPOT)) {
                            CasClient.getInstance().checkBusinessLogin(BaseHost.TYPE_SPOT);
                        } else if (generalResult.getUrl().contains("/" + BaseHost.TYPE_CFD)) {
                            CasClient.getInstance().checkBusinessLogin(BaseHost.TYPE_CFD);
                        } else if (generalResult.getUrl().contains("/" + BaseHost.TYPE_QUANTIZATION)) {
                            CasClient.getInstance().checkBusinessLogin(BaseHost.TYPE_QUANTIZATION);
                        } else {
                            if (App.getInstance().isAppLogin()) {
                                CasClient.getInstance().logout(true);
                            }
                        }
                    }
                }
                break;
            //check成功
            case EvKey.loginApp:
                if (eventBean.isStatue()) {
                    LogUtils.e("uodateLogin", "------------重新登录成功");
                    EventBusUtils.postSuccessEvent(EvKey.logout_success_401, eventBean.getCode(), "");
                } else {
                    CheckErrorUtil.checkError(eventBean);
                }
                break;
            //check退出登录
            case EvKey.logout_check:
                if (eventBean.isStatue()) {
                    App.getInstance().deleteCurrentUser();
                    EventBusUtils.postSuccessEvent(EvKey.loginStatue, BaseRequestCode.OK, "");
                    Toasty.showSuccess(App.getInstance().getString(R.string.logout_success_check));
                } else {
                    CheckErrorUtil.checkError(eventBean);
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

    private void loginWsPush(int type) {
        WebSocketRequest mWebSocketRequest = new WebSocketRequest();
        mWebSocketRequest.setCode(type);
        mWebSocketRequest.setCmd(WsCMD.PUSH_REQUEST);
        mWebSocketRequest.setBody(null);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ServiceOpenSuccessEvent serviceOpenSuccessEvent) {
        if (App.getInstance().isAppLogin()) {
            //订阅聊天
            subscribeChat();
        }
    }

    /**
     * 订阅聊天
     */
    private void subscribeChat() {
        WebSocketRequest mWebSocketRequest = new WebSocketRequest();
        mWebSocketRequest.setCode(WsConstant.CODE_CHAT);
        mWebSocketRequest.setCmd(WsCMD.SUBSCRIBE_GROUP_CHAT);
        mWebSocketRequest.setBody(App.gson.toJson(WsUtils.chatSubscribeJsonMap()).getBytes());
        EventBusUtils.postEvent(mWebSocketRequest);
    }
}
