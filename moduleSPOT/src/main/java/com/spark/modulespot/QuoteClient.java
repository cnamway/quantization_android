package com.spark.modulespot;

import com.spark.modulespot.base.SpotHost;
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
import me.spark.mvvm.utils.StringUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/27
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class QuoteClient extends BaseHttpClient {
    private static QuoteClient sQuoteClient;
    private String symbolCurrent = "";

    private QuoteClient() {
    }

    public static QuoteClient getInstance() {
        if (sQuoteClient == null) {
            synchronized (SpotCoinClient.class) {
                if (sQuoteClient == null) {
                    sQuoteClient = new QuoteClient();
                }
            }
        }
        return sQuoteClient;
    }

    /**
     * 获取指定币种指定深度盘口
     *
     * @param symbol
     */
    private void getMarketSymbol(final String symbol) {
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
    public void getMarketSymbolPolling(String symbol) {
        if (StringUtils.isEmpty(symbolCurrent)) {
            symbolCurrent = symbol;
            //2s刷新一次
            Observable.interval(0, 2, TimeUnit.SECONDS)
                    .doOnNext(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            if (Constant.isHomeVisiable) {
                                if (Constant.isTradeVisiable && Constant.tradePage == 1) {
                                    getMarketSymbol(symbolCurrent);
                                }
                            } else {
                                if (Constant.isKineVisiable) {
                                    getMarketSymbol(symbolCurrent);
                                }
                            }
                        }
                    }).subscribe();
        } else {
            symbolCurrent = symbol;
        }
    }
}
