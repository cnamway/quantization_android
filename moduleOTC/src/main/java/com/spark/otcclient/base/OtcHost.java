package com.spark.otcclient.base;


public class OtcHost {
    public static String geeCaptchaUrl = "captcha/mm/gee";
    public static String checkCaptchaUrl = "captcha/check";
    public static String phoneCaptchaUrl = "captcha/mm/phone";
    //上传base64处理后的图片
    public static String uploadBase64PicUrl = "upload/oss/base64";

    //查询用户支付配置列表
    public static String queryListUrl = "pay/queryList";
    //添加支付方式
    public static String payTypeAddUrl = "pay/add";
    //修改支付方式
    public static String payTypeUpdateUrl = "pay/update/";
    //上传base64处理后的图片
    public static String uploadQrcode = "upload/oss/base64";
    //检查支付方式
    public static String payTypeCheckUrl = "pay/check";
    //查询所有的币种
    public static String tradeCoinListUrl = "selfAdvertise/advertiseCoin/list";
    //按指定币种查询上架的广告
    public static String findPageUrl = "advertise/findPage";
    //查询所有的交易区
    public static String tradeAreaListUrl = "advertise/tradeArea/list";
    //创建广告
    public static String adCreateUrl = "selfAdvertise/create";
    //查询我的上架广告
    public static String selfAdvertiseUpUrl = "selfAdvertise/find";
    //查询我的归档广告(下架广告)
    public static String selfAdvertiseDownUrl = "selfAdvertise/findAchive";
    //下架广告
    public static String offShelvesUrl = "selfAdvertise/offShelves/";
    //上架广告
    public static String onShelvesUrl = "selfAdvertise/onShelves/";
    //价格获取接口
    public static String priceFindUrl = "price/find";
    //创建订单请求
    public static String orderCreateUrl = "trade/order/create";
    //查询我的归档订单
    public static String lcOrderQueryUrl = "trade/order/findAchivePage";
    //查询我的在途订单
    public static String lcOrderTransitPageQueryUrl = "trade/order/findInTransitPage";
    //查询在途订单详情
    public static String lcOrderorderInTransitDetailUrl = "trade/orderInTransit/detail/";
    //查询归档订单详情
    public static String lcOrderorderIAchiveDetailUrl = "trade/orderAchive/detail/";
    //确认付款
    public static String lcPaymentlUrl = "trade/order/payment";
    //取消订单
    public static String lcOrderCancelUrl = "trade/order/cancel/";
    //订单放行
    public static String lcOrderReleaseUrl = "trade/order/release/";
    //订单申诉申请
    public static String lcOrderAppealUrl = "appeal/apply";
    //查看自己认证商家信息
    public static String authMerchantFindUrl = "authMerchant/find";
    //广告交易币种信息
    public static String advertiseCoinListUrl = "selfAdvertise/advertiseCoin/list";
    //修改广告
    public static String selfAdvertiseUpdate = "selfAdvertise/update/";
    //删除支付方式
    public static String payTypeDeleteUrl = "pay/delete/";
    //认证商家信息查询
    public static String findMerchantUrl = "advertise/findMerchant/";
    //法币支持币种
    public static String findIndexCoinListUrl = "advertise/tradeCoin/list";
    //全部订单
    public static String lcOrderListUrl = "trade/order/findAllPage";
    //删除广告
    public static String selfAdvertiseDelete = "selfAdvertise/delete/";
    //获取历史聊天记录
    public static String getTradeChatListUrl = "trade/order/chat/";
}
