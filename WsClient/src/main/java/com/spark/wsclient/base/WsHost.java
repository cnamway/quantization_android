package com.spark.wsclient.base;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/9
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class WsHost {
    public static String klineWssUrl = "ws://ws.bitotc.bench.bitpay.com/kline";         //K线
    public static String bbTradeWssUrl = "ws://ws.bitotc.bench.bitpay.com/trade";       //BB交易
    public static String cfdTradeWssUrl = "ws://ws.bitotc.bench.bitpay.com/cfdTrade";   //CFD交易
    public static String handicapWssUrl = "ws://ws.bitotc.bench.bitpay.com/quote";      //盘口
    public static String chatWssUrl = "ws://192.168.2.12:28905/ws";                     //聊天
}
