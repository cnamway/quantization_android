package com.spark.qtz.app;

import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.modulecfd.CfdClient;
import com.example.modulecfd.CfdWsClient;
import com.spark.casclient.CasClient;
import com.spark.casclient.pojo.CasConfig;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.api.AppClient;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.modulespot.B2BWsClient;
import com.spark.modulespot.KlineClient;
import com.spark.modulespot.SpotCoinClient;
import com.spark.modulespot.pojo.SpotCoinResult;
import com.spark.otcclient.AdvertiseScanClient;
import com.spark.otcclient.pojo.LcCoinListResult;
import com.spark.wsclient.base.WsHost;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.spark.mvvm.base.BaseHost;
import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.StringUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/5
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class SplashViewModel extends BaseViewModel {
    public SplashViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 获取 cas配置Json
     */
    public void loadCasConfig() {
        //CasClient.getInstance().loadCasConfig();
        if (App.getInstance().isAppLogin()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ARouter.getInstance().build(ARouterPath.ACTIVITY_URL_INDEX)
                            .navigation();
                    finish();
                }
            }, 3000);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_LOGIN)
                            .navigation();
                    finish();
                }
            }, 3000);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            //Cas配置
            case EvKey.casConfig:
                if (eventBean.isStatue()) {
                    CasConfig casConfig = (CasConfig) eventBean.getObject();
                    //配置 base Host
                    if (null != casConfig.getData().getHome()) {
                        BaseHost.HOST = casConfig.getData().getHome() + "/";
                        App.getInstance().initEasyHttp();
                    }
                    if (null != casConfig.getData().getSymbol()) {
                        Constant.casSymbol = casConfig.getData().getSymbol();
                    }

                    //配置 cas Url
                    for (CasConfig.DataBean.CasBean.ApplicationsBean application : casConfig.getData().getCas().getApplications()) {
                        if (null != application.getName()) {
                            switch (application.getName()) {
                                case Constant.AC:
                                    BaseHost.AC_HOST = application.getBasePath() + "/";
                                    break;
                                case Constant.UC:
                                    BaseHost.UC_HOST = application.getBasePath() + "/";
                                    break;
                                case Constant.OTC:
                                    BaseHost.OTC_HOST = application.getBasePath() + "/";
                                    break;
                                case Constant.SPOT:
                                    BaseHost.SPOT_HOST = application.getBasePath() + "/";
                                    break;
                                case Constant.KLINE:
                                    BaseHost.KLINE_HOST = application.getBasePath() + "/";
                                    break;
                                case Constant.QUOTE:
                                    BaseHost.QUOTE_HOST = application.getBasePath() + "/";
                                    break;
                                case Constant.PRICE:
                                    BaseHost.PRICE_HOST = application.getBasePath() + "/";
                                    break;
                                case Constant.CFD:
                                    BaseHost.CFD_HOST = application.getBasePath() + "/";
                                    break;
                                case Constant.CMS:
                                    BaseHost.CMS_HOST = application.getBasePath() + "/";
                                    break;
                            }
                        }
                    }
                    //配置聊天ws Url
                    if (!StringUtils.isEmpty(casConfig.getData().getWebsocket())) {
                        WsHost.chatWssUrl = casConfig.getData().getWebsocket();
                    }
                    if (!StringUtils.isEmpty(casConfig.getData().getAgentUrl())) {
                        BaseHost.AGENT_HOST = casConfig.getData().getAgentUrl() + "/";
                    }
                    if (!StringUtils.isEmpty(casConfig.getData().getAppregister())) {
                        Constant.inviteUrl = casConfig.getData().getAppregister();
                    }
                    LogUtils.e("chatWssUrl", WsHost.chatWssUrl);
                    LogUtils.e("AGENT_HOST", BaseHost.AGENT_HOST);
                    LogUtils.e("inviteUrl", Constant.inviteUrl);
                    //配置ws Url
                    for (CasConfig.DataBean.WsBean wsBean : casConfig.getData().getWs()) {
                        if (null != wsBean.getIdentity()) {
                            switch (wsBean.getIdentity()) {
                                case Constant.MARKETWS:
                                    WsHost.handicapWssUrl = wsBean.getProtocol() + "://" + wsBean.getPath();
                                    break;
                                case Constant.KLINEWS:
                                    WsHost.klineWssUrl = wsBean.getProtocol() + "://" + wsBean.getPath();
                                    break;
                                case Constant.SPOTTRADEWS:
                                    WsHost.bbTradeWssUrl = wsBean.getProtocol() + "://" + wsBean.getPath();
                                    break;
                                case Constant.CFDTRADEWS:
                                    WsHost.cfdTradeWssUrl = wsBean.getProtocol() + "://" + wsBean.getPath();
                                    break;
                            }
                        }
                    }
                    Constant.casConfigJson = eventBean.getMessage();
                }
                SpotCoinClient.getInstance().getSpotCoinAll();
                break;
            //查询币币可用币种
            case EvKey.spotCoinAll:
                if (eventBean.isStatue()) {
                    Constant.coinPairThumbBeanList.clear();
                    Constant.coinPairThumbBeanList.add(App.getInstance().getString(R.string.favorites));
                    Constant.spotJson = App.gson.toJson(eventBean.getObject());
                    LogUtils.e("spotJson", Constant.spotJson);
                    for (SpotCoinResult.DataBean dataBean : ((SpotCoinResult) eventBean.getObject()).getData()) {
                        if (!Constant.coinPairThumbBeanList.contains(dataBean.getBaseSymbol()))
                            Constant.coinPairThumbBeanList.add(dataBean.getBaseSymbol());
                    }
                } else {
                    Toasty.showError(App.getInstance().getString(R.string.network_abnormal));
                }
                AdvertiseScanClient.getInstance().getIndexTradeCoinList();
                break;
            //查询法币可用币种
            case EvKey.indexCoinList:
                if (eventBean.isStatue()) {
                    Constant.lcCoinPairThumbBeanList.clear();
                    for (LcCoinListResult.DataBean dataBean : ((LcCoinListResult) eventBean.getObject()).getData()) {
                        if (!Constant.lcCoinPairThumbBeanList.contains(dataBean.getCoinName()))
                            Constant.lcCoinPairThumbBeanList.add(dataBean.getCoinName());
                    }
                } else {
                    Toasty.showError(App.getInstance().getString(R.string.network_abnormal));
                }
                AppClient.getInstance().checkVersionUpdate();
                break;
            case EvKey.checkVersionUpdate:
                if (eventBean.isStatue()) {
                    Constant.updateInfoJson = App.gson.toJson(eventBean.getObject());
                } else {
                    Toasty.showError(App.getInstance().getString(R.string.network_abnormal));
                }
                LogUtils.e("updateInfoJson", Constant.updateInfoJson);
                if (!Constant.isHttpAndWs) {
                    //打开缩略图轮询
                    KlineClient.getInstance().getAllThumbPolling();
                    CfdClient.getInstance().getAllThumbPolling();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_LOGIN)
                                    .navigation();
                            finish();
                        }
                    }, 500);
                } else {
                    B2BWsClient.getInstance().getB2BKlinePush();
                }
                break;
            //B2B缩略图
            case EvKey.klineThumbAll:
                if (eventBean.isStatue()) {
                    Constant.b2bKlinePushJson = App.gson.toJson(eventBean.getObject());
                    CfdWsClient.getInstance().getCfdKlinePush();
                } else {
                    Toasty.showError(App.getInstance().getString(R.string.network_abnormal));
                }
                break;
            //Cfd缩略图
            case EvKey.cfdAllSymbolPush:
                if (eventBean.isStatue()) {
                    Constant.cfdKlinePushJson = App.gson.toJson(eventBean.getObject());
                } else {
                    Toasty.showError(App.getInstance().getString(R.string.network_abnormal));
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_LOGIN)
                                .navigation();
                        finish();
                    }
                }, 500);
                break;
            default:
                break;
        }
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
