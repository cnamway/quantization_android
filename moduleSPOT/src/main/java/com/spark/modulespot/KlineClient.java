package com.spark.modulespot;

import com.spark.modulespot.base.SpotHost;
import com.spark.modulespot.pojo.AllThumbResult;
import com.spark.modulespot.pojo.DealResult;
import com.spark.modulespot.pojo.KlineControllerBean;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.cache.model.CacheResult;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.base.BaseHost;
import me.spark.mvvm.base.BaseRequestCode;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.http.BaseHttpClient;
import me.spark.mvvm.http.GeneralResult;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.StringUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/28
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class KlineClient extends BaseHttpClient {
    private static KlineClient sKlineClient;
    private String klineCache;
    private KlineControllerBean klineControllerBean = new KlineControllerBean();
    private Disposable mLastDisposable;

    private KlineClient() {
    }

    public static KlineClient getInstance() {
        if (sKlineClient == null) {
            synchronized (SpotCoinClient.class) {
                if (sKlineClient == null) {
                    sKlineClient = new KlineClient();
                }
            }
        }
        return sKlineClient;
    }


    /**
     * 成交全币种缩略图
     */
    private void getAllThumb() {
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
    public void getAllThumbPolling() {
        klineControllerBean.setService("SPOT");
        //2s刷新一次
        Observable.interval(0, 2, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (Constant.isHomeVisiable) {
                            if (Constant.isIndexVisiable
                                    || Constant.isQoutesVisiable
                                    || (Constant.isTradeVisiable && Constant.tradePage == 1)) {
                                getAllThumb();
                            }
                        } else {
                            if (Constant.isKineVisiable || Constant.isKineHorizontalVisiable || Constant.isOpenOrdersVisiable) {
                                getAllThumb();
                            }
                        }

                    }
                }).subscribe();
    }

    /**
     * 查询历史K线
     *
     * @param symbol
     * @param service
     * @param from
     * @param to
     * @param resolution 机制 先加载缓存数据 再加载网络数据 若网络数据和缓存数据除最后一条一样的话则不刷新 否则刷新
     */
    public void getKlineHistory(String symbol, String service, Long from, Long to, String resolution) {
        klineCache = "";
        if (mLastDisposable != null) EasyHttp.cancelSubscription(mLastDisposable);

        KlineControllerBean klineControllerBean = new KlineControllerBean();
        klineControllerBean.setSymbol(symbol);
        klineControllerBean.setFrom(from);
        klineControllerBean.setTo(to);
        klineControllerBean.setResolution(resolution);
        klineControllerBean.setService(service);
        mLastDisposable = EasyHttp.post(SpotHost.klineHistoryUrl)
                .baseUrl(BaseHost.KLINE_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(klineControllerBean))
                .cacheMode(CacheMode.CACHEANDREMOTEDISTINCT)
                .cacheKey(service + resolution)
                .execute(new SimpleCallBack<CacheResult<String>>() {
                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.klineHistory, e);
                    }

                    @Override
                    public void onSuccess(CacheResult<String> stringCacheResult) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(stringCacheResult.data, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                if (stringCacheResult.isFromCache) {
                                    LogUtils.e("getKlineHistory", "-------From cache");
                                    klineCache = stringCacheResult.data;
                                    EventBusUtils.postSuccessEvent(EvKey.klineHistory, BaseRequestCode.OK, generalResult.getMessage(), stringCacheResult.data);
                                } else {
                                    if (StringUtils.isEmpty(klineCache)) {
                                        LogUtils.e("getKlineHistory", "-------From netWork");
                                        EventBusUtils.postSuccessEvent(EvKey.klineHistory, BaseRequestCode.OK, generalResult.getMessage(), stringCacheResult.data);
                                    } else {
                                        if (!klineCache.substring(0, klineCache.lastIndexOf("[")).equals(stringCacheResult.data.substring(0, stringCacheResult.data.lastIndexOf("[")))) {
                                            LogUtils.e("getKlineHistory", "-------NetWork update");
                                            EventBusUtils.postSuccessEvent(EvKey.klineHistory, BaseRequestCode.OK, generalResult.getMessage(), stringCacheResult.data);
                                        }
                                    }
                                }
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.klineHistory, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.klineHistory, e);
                        }
                    }
                });
    }

    /**
     * 查询历史成交
     *
     * @param symbol
     */
    public void getLatestTradeUrl(String symbol) {
        KlineControllerBean klineControllerBean = new KlineControllerBean();
        klineControllerBean.setSymbol(symbol);
        klineControllerBean.setSize(20);
        klineControllerBean.setService("SPOT");

        EasyHttp.post(SpotHost.klineLatestTradeUrl)
                .baseUrl(BaseHost.KLINE_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(klineControllerBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("getLatestTradeUrl", s);
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                DealResult dealResult = BaseApplication.gson.fromJson(s, DealResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.dealHistory, BaseRequestCode.OK, generalResult.getMessage(), dealResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.dealHistory, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.dealHistory, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.dealHistory, e);
                    }
                });
    }
}
