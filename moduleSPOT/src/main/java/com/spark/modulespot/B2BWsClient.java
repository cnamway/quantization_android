package com.spark.modulespot;

import com.spark.modulespot.base.SpotHost;
import com.spark.modulespot.pojo.AllThumbResult;
import com.spark.modulespot.pojo.KlineControllerBean;
import com.spark.modulespot.pojo.MarketSymbolResult;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.base.BaseHost;
import me.spark.mvvm.base.BaseRequestCode;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.http.BaseHttpClient;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.StringUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-08-07
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class B2BWsClient extends BaseHttpClient {
    private static B2BWsClient sB2BWsClient;
    private KlineControllerBean klineControllerBean = new KlineControllerBean();
    private String symbolCurrent = "";

    private B2BWsClient() {
    }

    public static B2BWsClient getInstance() {
        if (sB2BWsClient == null) {
            synchronized (B2BWsClient.class) {
                if (sB2BWsClient == null) {
                    sB2BWsClient = new B2BWsClient();
                }
            }
        }
        return sB2BWsClient;
    }


    /**
     * 成交全币种缩略图
     */
    public void getB2BKlinePush() {
        LogUtils.e("getB2BKlinePush", "-----------ws断开 http进行请求-----------");
        klineControllerBean.setService("SPOT");
        EasyHttp.post(SpotHost.klineAllThumbUrl)
                .baseUrl(BaseHost.KLINE_HOST)
                .retryCount(0)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(klineControllerBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        AllThumbResult allThumbResult = BaseApplication.gson.fromJson(s, AllThumbResult.class);
                        if (allThumbResult.getCode() == BaseRequestCode.OK) {
                            EventBusUtils.postSuccessEvent(EvKey.klineThumbAll, allThumbResult.getCode(), allThumbResult.getMessage(), allThumbResult);
                        } else {
                            EventBusUtils.postErrorEvent(EvKey.klineThumbAll, allThumbResult.getCode(), allThumbResult.getMessage());
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        EventBusUtils.postErrorEvent(EvKey.klineThumbAll, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 成交全币种缩略图轮询
     */
    public void monitorB2BKlinePush() {
        if (!Constant.isMonitorB2BKlinePush) {
            //2s刷新一次
            Observable.interval(2, 2, TimeUnit.SECONDS)
                    .doOnNext(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            if (System.currentTimeMillis() - Constant.lastB2BKlinePushTime > 5000) {
                                if (Constant.isHomeVisiable) {
                                    if (Constant.isIndexVisiable
                                            || Constant.isQoutesVisiable
                                            || (Constant.isTradeVisiable && Constant.tradePage == 1)) {
                                        getB2BKlinePush();
                                    }
                                } else {
                                    if (Constant.isKineVisiable || Constant.isKineHorizontalVisiable || Constant.isOpenOrdersVisiable) {
                                        getB2BKlinePush();
                                    }
                                }

                            } else {
                                LogUtils.e("getB2BKlinePush", "-----------ws进行请求-----------");
                            }

                        }
                    }).subscribe();
            Constant.isMonitorB2BKlinePush = true;
        }
    }


    /**
     * 获取指定币种指定深度盘口
     *
     * @param symbol
     */
    private void getB2BMarketPush(final String symbol) {
        LogUtils.e("getCfdMarketPush", "-----------ws断开 http进行请求-----------");

        EasyHttp.get(SpotHost.marketSymbolUrl + symbol.replace("/", "-") + "/20")
                .baseUrl(BaseHost.QUOTE_HOST)
                .retryCount(0)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        MarketSymbolResult marketSymbolResult = BaseApplication.gson.fromJson(s, MarketSymbolResult.class);
                        if (marketSymbolResult.getCode() == BaseRequestCode.OK) {
                            EventBusUtils.postSuccessEvent(EvKey.marketSymbol, marketSymbolResult.getCode(), symbol, marketSymbolResult);
                        } else {
                            EventBusUtils.postErrorEvent(EvKey.marketSymbol, marketSymbolResult.getCode(), marketSymbolResult.getMessage());
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        EventBusUtils.postErrorEvent(EvKey.marketSymbol, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 获取指定币种指定深度盘口轮询
     */
    public void monitorB2BMarketPush(String symbol) {
        if (StringUtils.isEmpty(symbolCurrent)) {
            symbolCurrent = symbol;
            //2s刷新一次
            Observable.interval(0, 2, TimeUnit.SECONDS)
                    .doOnNext(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            if (System.currentTimeMillis() - Constant.lastB2BMarketPushTime > 5000) {
                                if (Constant.isHomeVisiable) {
                                    if (Constant.isTradeVisiable && Constant.tradePage == 1) {
                                        getB2BMarketPush(symbolCurrent);
                                    }
                                } else {
                                    if (Constant.isKineVisiable) {
                                        getB2BMarketPush(symbolCurrent);
                                    }
                                }
                            } else {
                                LogUtils.e("getCfdMarketPush", "-----------ws进行请求-----------");
                            }
                        }
                    }).subscribe();
        } else {
            symbolCurrent = symbol;
        }
    }
}
