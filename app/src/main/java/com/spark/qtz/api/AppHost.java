package com.spark.qtz.api;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/3
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class AppHost {
    public static String bannerUrl = "webConfig/query";
    public static String articleListUrl = "article/list";
    //获取数字货币->法币的汇率
    public static String coin2convertCurrencyUrl = "digiccy/v1/getFiatPrice/";
    //获取货币A->货币B的汇率
    public static String convertCurrency2convertCurrencyUrl = "legal/v1/exchangeRate/";
    //客服地址
    public static String contactCustomerUrl = "https://lzt.zoosnet.net/LR/Chatpre.aspx?id=LZT83514790&lng=cn";
    //邀请码验证
    public static String promotionLimitUrl = "register/promotion/limit";
    public static String promotionCodeUrl = "register/check/inviteCode";
    public static String promotionCodeAgentApiUrl = "agentApi/promotion/check/code";
    //公告
    public static String announceUrl = "webOtherContent/query";
    //版本更新
    public static String updateUrl = "app/check-version";
}
