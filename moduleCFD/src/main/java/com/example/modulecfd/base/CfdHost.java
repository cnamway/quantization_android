package com.example.modulecfd.base;


public class CfdHost {
    public static String geeCaptchaUrl = "captcha/mm/gee";
    public static String checkCaptchaUrl = "captcha/check";
    public static String phoneCaptchaUrl = "captcha/mm/phone";

    //CFD所有支持币种
    public static String cfdSymbolAll = "coin/all";
    //CFD所有支持币种缩略图
    public static String cfdAllThumbUrl = "query/api/v1/allThumb";
    //获取指定币种指定深度盘口
    public static String marketCfdSymbolUrl = "v1/market/cfd/depth/";
    //合约下单
    public static String orderPlaceUrl = "v1/cfd/user/order/place";
    //合约撤单
    public static String orderCancelUrl = "v1/cfd/user/order/cancel";
    //合约平仓
    public static String orderCloseUrl = "v1/cfd/user/order/close";
    //设置止盈止损价格
    public static String orderProfitLosslUrl = "v1/cfd/user/order/setClosePrice";
    //仓位
    public static String orderPositionUrl = "v1/cfd/user/order/position";
    //当前委托
    public static String orderCommissionUrl = "v1/cfd/user/order/currentOrder";
    //已成交
    public static String orderDealUrl = "v1/cfd/user/order/turnOver/detail";
    //已撤单
    public static String orderRevokeUrl = "v1/cfd/user/order/history";
    //交易记录
    public static String cfdTradeOrderUrl = "order/position";
}
