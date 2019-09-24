package com.spark.wsclient.utils;

import com.spark.wsclient.base.WsCMD;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.WebSocketRequest;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-06-28
 * 描    述：Ws心跳包
 * 修订历史：
 * ================================================
 */
public class HeartBeatUtils {
    private static HeartBeatUtils sHeartBeatUtils;

    private static Disposable mDisposableKline, mDisposableBBTrade, mDisposableHandicap, mDisposableCfdTrade, mDisposableChat;

    private HeartBeatUtils() {
    }

    public static HeartBeatUtils getInstance() {
        if (sHeartBeatUtils == null) {
            synchronized (HeartBeatUtils.class) {
                if (sHeartBeatUtils == null) {
                    sHeartBeatUtils = new HeartBeatUtils();
                }
            }
        }
        return sHeartBeatUtils;
    }


    //K线心跳
    public void startKlineHeartBeat() {
        if (null != mDisposableKline) return;
        //20s刷新一次
        mDisposableKline = Observable.interval(0, WsConstant.heartPeriod, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        heartBeat(WsConstant.CODE_KLINE);
                    }
                }).subscribe();
    }

    //BB交易心跳
    public void startBBTradeHeartBeat() {
        if (null != mDisposableBBTrade) return;
        //20s刷新一次
        mDisposableBBTrade = Observable.interval(0,  WsConstant.heartPeriod, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        heartBeat(WsConstant.CODE_BB_TRADE);
                    }
                }).subscribe();
    }

    //盘口心跳
    public void startHandicapHeartBeat() {
        if (null != mDisposableHandicap) return;
        //20s刷新一次
        mDisposableHandicap = Observable.interval(0,  WsConstant.heartPeriod, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        heartBeat(WsConstant.CODE_MARKET);
                    }
                }).subscribe();
    }

    //合约交易心跳
    public void startCfdTradeHeartBeat() {
        if (null != mDisposableCfdTrade) return;
        //20s刷新一次
        mDisposableCfdTrade = Observable.interval(0,  WsConstant.heartPeriod, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        heartBeat(WsConstant.CODE_CFD_TRADE);
                    }
                }).subscribe();
    }

    //聊天心跳
    public void startChatHeartBeat() {
        if (null != mDisposableChat) return;
        //20s刷新一次
        mDisposableChat = Observable.interval(0,  WsConstant.heartPeriod, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        heartBeat(WsConstant.CODE_CHAT);
                    }
                }).subscribe();
    }


    private void heartBeat(int type) {
        WebSocketRequest mWebSocketRequest = new WebSocketRequest();
        mWebSocketRequest.setCode(type);
        mWebSocketRequest.setCmd(WsCMD.HEART_BEAT);
        mWebSocketRequest.setBody(null);
        EventBusUtils.postEvent(mWebSocketRequest);
    }

    public static void stopAllHeartBeat() {
        if (null != mDisposableKline && !mDisposableKline.isDisposed()) {
            mDisposableKline.dispose();
            mDisposableKline = null;
        }

        if (null != mDisposableBBTrade && !mDisposableBBTrade.isDisposed()) {
            mDisposableBBTrade.dispose();
            mDisposableBBTrade = null;
        }

        if (null != mDisposableHandicap && !mDisposableHandicap.isDisposed()) {
            mDisposableHandicap.dispose();
            mDisposableHandicap = null;
        }

        if (null != mDisposableCfdTrade && !mDisposableCfdTrade.isDisposed()) {
            mDisposableCfdTrade.dispose();
            mDisposableCfdTrade = null;
        }

        if (null != mDisposableChat && !mDisposableChat.isDisposed()) {
            mDisposableChat.dispose();
            mDisposableChat = null;
        }
        LogUtils.e("HeartBeatUtils", "-------------取消心跳----------");
    }
}
