package com.spark.wsclient.utils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/6
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class WsConstant {
    public static final String service = "service";     //所有的订阅  都加参数service
    public static final String currency = "currency";
    public static final String CFD = "CFD";             //CFD
    public static final String SPOT = "SPOT";           //币币
    public static final String SWAP = "SWAP";           //有续

    public static final int CODE_BB_TRADE = 0; // 币币交易code
    public static final int CODE_CHAT = 1; // 聊天code
    public static final int CODE_MARKET = 2; // 行情code
    public static final int CODE_KLINE = 3; // k线code
    public static final int CODE_CFD_TRADE = 4; // CFD交易code

    public static final long heartPeriod = 60; // 心跳时间
}
