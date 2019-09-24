package com.spark.wsclient.utils;

import com.spark.wsclient.pojo.ChatBean;

import java.util.HashMap;

import me.spark.mvvm.base.BaseApplication;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/10
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class WsUtils {
    private static HashMap<String, String> mHashMap = new HashMap<>();

    //BB行情
    public static HashMap<String, String> setSubscribeThumbSPOTJsonMap() {
        mHashMap.clear();
        mHashMap.put(WsConstant.service, WsConstant.SPOT);
        return mHashMap;
    }
    public static HashMap<String, String> setSubscribeThumbCFDJsonMap() {
        mHashMap.clear();
        mHashMap.put(WsConstant.service, WsConstant.CFD);
        return mHashMap;
    }

    //B2B盘口
    public static HashMap<String, String> setB2BMarketJsonMap(String symbol) {
        mHashMap.clear();
        mHashMap.put("symbol", symbol);
        mHashMap.put(WsConstant.service, WsConstant.SPOT);
        return mHashMap;
    }

    //CFD盘口
    public static HashMap<String, String> setCFDMarketJsonMap(String symbol) {
        mHashMap.clear();
        mHashMap.put("symbol", symbol);
        mHashMap.put(WsConstant.service, WsConstant.CFD);
        return mHashMap;
    }

    /**
     * 登录
     *
     * @param cookie
     * @return
     */
    public static HashMap<String, String> setLoginJsonMap(String cookie) {
        mHashMap.clear();
        mHashMap.put("sid", cookie);
        return mHashMap;
    }

    /**
     * K线历史数据
     *
     * @param symbol
     * @param from
     * @param to
     * @param resolution
     * @return
     */
    public static HashMap<String, String> setKlineHistoryJsonMap(String symbol, Long from, Long to, String resolution) {
        mHashMap.clear();
        mHashMap.put("symbol", symbol);
        mHashMap.put("from", String.valueOf(from));
        mHashMap.put("to", String.valueOf(to));
        mHashMap.put("resolution", resolution);
        mHashMap.put(WsConstant.service, WsConstant.SPOT);
        return mHashMap;
    }

    /**
     * 发送聊天
     *
     * @param mChatSendBean
     * @return
     */
    public static HashMap<String, String> setChatJsonMap(ChatBean mChatSendBean) {
        mHashMap.clear();
        mHashMap.put("orderId", mChatSendBean.getOrderId());
        mHashMap.put("uidFrom", mChatSendBean.getUidFrom());
        mHashMap.put("uidTo", mChatSendBean.getUidTo());
        mHashMap.put("nameTo", mChatSendBean.getNameTo());
        mHashMap.put("nameFrom", mChatSendBean.getNameFrom());
        mHashMap.put("messageType", "1");
        mHashMap.put("avatar", mChatSendBean.getFromAvatar());
        mHashMap.put("content", mChatSendBean.getContent());
        return mHashMap;
    }

    /**
     * 聊天订阅 - 反订阅
     *
     * @return
     */
    public static HashMap<String, String> chatSubscribeJsonMap() {
        mHashMap.clear();
        mHashMap.put("uid", BaseApplication.getInstance().getCurrentUser().getId());
        return mHashMap;
    }

    /**
     * K线订阅
     *
     * @param symbol
     * @param resolution
     * @return
     */
    public static HashMap<String, String> setKlineSubscribeJsonMap(String symbol, String service, String resolution) {
        mHashMap.clear();
        mHashMap.put("symbol", symbol);
        mHashMap.put(WsConstant.service, WsConstant.SPOT);
        mHashMap.put("resolution", resolution);
        return mHashMap;
    }

    public static HashMap<String, String> setDealSubscribeJsonMap(String symbol) {
        mHashMap.clear();
        mHashMap.put("symbol", symbol);
        mHashMap.put(WsConstant.service, WsConstant.SPOT);
        return mHashMap;
    }

    public static HashMap<String, String> setTradePlateHistoryJsonMap(String symbol) {
        mHashMap.clear();
        mHashMap.put("symbol", symbol);
        mHashMap.put(WsConstant.service, WsConstant.SPOT);
        mHashMap.put("size", "20");
        return mHashMap;
    }

    public static HashMap<String, String> setTradePlateSubscribeJsonMap(String symbol) {
        mHashMap.clear();
        mHashMap.put("symbol", symbol);
        mHashMap.put(WsConstant.service, WsConstant.SPOT);
        return mHashMap;
    }

    /**
     * 持仓明细
     *
     * @return
     */
    public static HashMap<String, String> setCfdPositonJsonMap() {
        mHashMap.clear();
        mHashMap.put("memberId", BaseApplication.getInstance().getCurrentUser().getId());
        return mHashMap;
    }

    /**
     * CFD 当前委托 已成交 已撤单
     *
     * @param symbol
     * @param pageNo
     * @param pageSize
     * @return
     */
    public static HashMap<String, String> setCfdOrderDetailJsonMap(String symbol, String pageNo, String pageSize) {
        mHashMap.clear();
        mHashMap.put("symbol", symbol);
        mHashMap.put("pageNo", pageNo);
        mHashMap.put("pageSize", pageSize);
        return mHashMap;
    }

    /**
     * CFD 成交记录
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    public static HashMap<String, String> setCfdTradeOrderJsonMap(String pageNo, String pageSize) {
        mHashMap.clear();
        mHashMap.put("pageNo", pageNo);
        mHashMap.put("pageSize", pageSize);
        return mHashMap;
    }
}
