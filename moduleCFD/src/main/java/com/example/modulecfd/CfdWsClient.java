package com.example.modulecfd;

import com.example.modulecfd.base.CfdHost;
import com.example.modulecfd.pojo.CfdAllThumbResult;
import com.spark.modulespot.pojo.KlineControllerBean;
import com.spark.modulespot.pojo.MarketSymbolResult;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
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
 * 创建日期：2019-08-07
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CfdWsClient extends BaseHttpClient {
    private static CfdWsClient sB2BWsClient;
    private KlineControllerBean klineControllerBean = new KlineControllerBean();
    private List<MarketSymbolResult.DataBean.AskBean> askList = new ArrayList<>();
    private List<MarketSymbolResult.DataBean.BidBean> bidList = new ArrayList<>();
    private String symbolCurrent = "";

    private CfdWsClient() {
    }

    public static CfdWsClient getInstance() {
        if (sB2BWsClient == null) {
            synchronized (CfdWsClient.class) {
                if (sB2BWsClient == null) {
                    sB2BWsClient = new CfdWsClient();
                }
            }
        }
        return sB2BWsClient;
    }


    /**
     * 成交全币种缩略图
     */
    public void getCfdKlinePush() {
        LogUtils.e("getCfdKlinePush", "-----------ws断开 http进行请求-----------");
        klineControllerBean.setService("CFD");
        EasyHttp.post(CfdHost.cfdAllThumbUrl)
                .baseUrl(BaseHost.KLINE_HOST)
                .retryCount(0)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(klineControllerBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        CfdAllThumbResult cfdAllThumbResult = BaseApplication.gson.fromJson(s, CfdAllThumbResult.class);
                        if (cfdAllThumbResult.getCode() == BaseRequestCode.OK) {
                            EventBusUtils.postSuccessEvent(EvKey.cfdAllSymbolPush, cfdAllThumbResult.getCode(), cfdAllThumbResult.getMessage(), cfdAllThumbResult);
                        } else {
                            EventBusUtils.postErrorEvent(EvKey.cfdAllSymbolPush, cfdAllThumbResult.getCode(), cfdAllThumbResult.getMessage());
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        EventBusUtils.postErrorEvent(EvKey.cfdAllSymbolPush, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 成交全币种缩略图轮询
     */
    public void monitorCfdKlinePush() {
        //2s刷新一次
        Observable.interval(2, 2, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (System.currentTimeMillis() - Constant.lastCfdKlinePushTime > 5000) {
                            if (Constant.isHomeVisiable) {
                                if (Constant.isTradeVisiable && Constant.tradePage == 2) {
                                    getCfdKlinePush();
                                }
                            } else {
                                if (Constant.isCfdKineVisiable || Constant.isCfdKineHorizontalVisiable || Constant.isMeFinanceVisiable) {
                                    getCfdKlinePush();
                                }
                            }
                        } else {
                            LogUtils.e("getCfdKlinePush", "-----------ws进行请求-----------");

                        }
                    }
                }).subscribe();
    }


    /**
     * 获取指定币种指定深度盘口
     *
     * @param symbol
     */
    private void getCfdMarketPush(final String symbol) {
        LogUtils.e("getCfdMarketPush", "-----------ws断开 http进行请求-----------");
        EasyHttp.get(CfdHost.marketCfdSymbolUrl + symbol.replace("/", "-") + "/20")
                .baseUrl(BaseHost.QUOTE_HOST)
                .retryCount(0)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                JSONArray askArray = (JSONArray) new JSONObject((String) BaseApplication.gson.fromJson(s, Map.class).get("data")).get("asks");
                                if (askArray != null) {
                                    askList.clear();
                                    for (int i = 0; i < askArray.length(); i++) {
                                        JSONArray data = askArray.optJSONArray(i);
                                        MarketSymbolResult.DataBean.AskBean askBean = new MarketSymbolResult.DataBean.AskBean();
                                        askBean.setPrice(data.optDouble(0));
                                        askBean.setAmount(data.optDouble(1));
                                        askList.add(askBean);
                                    }
                                }
                                JSONArray bidArray = (JSONArray) new JSONObject((String) BaseApplication.gson.fromJson(s, Map.class).get("data")).get("bids");
                                if (bidArray != null) {
                                    bidList.clear();
                                    for (int i = 0; i < bidArray.length(); i++) {
                                        JSONArray data = bidArray.optJSONArray(i);
                                        MarketSymbolResult.DataBean.BidBean bidBean = new MarketSymbolResult.DataBean.BidBean();
                                        bidBean.setPrice(data.optDouble(0));
                                        bidBean.setAmount(data.optDouble(1));
                                        bidList.add(bidBean);
                                    }
                                }
                                MarketSymbolResult marketSymbolResult = new MarketSymbolResult();
                                marketSymbolResult.setData(new MarketSymbolResult.DataBean(askList, bidList));

                                EventBusUtils.postSuccessEvent(EvKey.marketCfdSymbol, generalResult.getCode(), symbol, marketSymbolResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.marketCfdSymbol, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            LogUtils.e("getMarketCfdSymbol", e.toString());
                            postException(EvKey.marketCfdSymbol, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        EventBusUtils.postErrorEvent(EvKey.marketCfdSymbol, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 获取指定币种指定深度盘口轮询
     */
    public void monitorCfdMarketPush(String symbol) {
        if (StringUtils.isEmpty(symbolCurrent)) {
            symbolCurrent = symbol;
            //2s刷新一次
            Observable.interval(5, 5, TimeUnit.SECONDS)
                    .doOnNext(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            if (System.currentTimeMillis() - Constant.lastCfdMarketPushTime > 5000) {
                                if (Constant.isHomeVisiable) {
                                    if (Constant.isTradeVisiable && Constant.tradePage == 2) {
                                        getCfdMarketPush(symbolCurrent);
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
