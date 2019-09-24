package com.spark.modulespot.base;


public class SpotHost {
    public static String geeCaptchaUrl = "captcha/mm/gee";
    public static String checkCaptchaUrl = "captcha/check";
    public static String phoneCaptchaUrl = "captcha/mm/phone";

    //查询平台支持的币币可用币种
    public static String spotCoinAllUrl = "v3/coin/all";
    //获取全部收藏币种
    public static String favorFindUrl = "v3/favor/find";
    //收藏币种
    public static String favorAddUrl = "v3/favor/add/";
    //删除币种
    public static String favorDeleteUrl = "v3/favor/delete/";
    //获取指定币种指定深度盘口
    public static String marketSymbolUrl = "v1/market/spot/depth/";
    //成交全币种缩略图
    public static String klineAllThumbUrl = "query/api/v1/allThumb";
    //查询历史K线
    public static String klineHistoryUrl = "query/api/v1/kline";
    //查询历史成交
    public static String klineLatestTradeUrl = "query/api/v1/latestTrade";
    //spot钱包查询业务处理
    public static String spotWalletUrl = "v3/rpc/wallet/query/";
    //当前委托
    public static String openOrdersUrl = "v1/user/order/query/openOrders";
    //历史委托
    public static String openOrdersHistoryUrl = "v1/user/order/query/history";
    //委托成交明细
    public static String openOrdersDetailsUrl = "v1/user/order/query/fill/";
    //用户下单
    public static String placeOrderUrl = "v1/user/order/place";
    //用户撤单
    public static String cancelOrderUrl = "v1/user/order/";
    public static String cancelOrderUrlEnd = "/submitcancel";
    //spot钱包指定交易对查询业务处理
    public static String queryWithSymbolUrl = "v3/wallet/queryWithSymbol";
}
