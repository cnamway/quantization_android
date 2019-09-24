package com.spark.qtz.base;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/10
 * 描    述：ARouterPath
 * 修订历史：
 * ================================================
 */
public final class ARouterPath {
    public static final String ACTIVITY_URL_INDEX = "/app/IndexActivity";

    //quotes-module---------------------------------------------------------------
    public static final String ACTIVITY_QUOTES_SERACH = "/quotes/search/QuotesSearchActivity";
    public static final String ACTIVITY_QUOTES_DATAIL = "/quotes/detail/QuotesDetailActivity";
    public static final String ACTIVITY_QUOTES_KLINE = "/quotes/kline/KlineActivity";
    public static final String ACTIVITY_QUOTES_KLINE_HTTP = "/quotes/kline/KlineHttpActivity";
    public static final String ACTIVITY_QUOTES_KLINE_HTTP_HORIZONTAL = "/quotes/kline/KlineHttpHorizontalActivity";
    //quotes-module---------------------------------------------------------------

    //emex-module---------------------------------------------------------------
    public static final String ACTIVITY_EMEX_BANNER_INFO = "/emex/banner/BannerInfoActivity";
    public static final String ACTIVITY_EMEX_WEBDETAILS = "/emex/WebDetailsActivity";
    public static final String ACTIVITY_EMEX_ACCOUNT_DETAILS = "/emex/details/IndexAccountDetailsActivity";
    public static final String ACTIVITY_EMEX_STRATEGY_DETAILS = "/emex/details/IndexStrategyDetailsActivity";
    //emex-module---------------------------------------------------------------

    //---user
    //登录
    public static final String ACTIVITY_ME_LOGIN = "/me/user/LoginActivity";
    //注册
    public static final String ACTIVITY_ME_REGISTER = "/me/user/RegisterActivity";
    public static final String ACTIVITY_ME_REGISTER_PASS = "/me/user/RegisterPwdActivity";
    public static final String ACTIVITY_ME_VERIFYCODE = "/me/user/VerifyCodeActivity";
    //忘记密码
    public static final String ACTIVITY_ME_FORGET_PASS = "/me/ForgetPwdActivity";
    //---user

    //---finiance
    public static final String ACTIVITY_ME_PROPERTY = "/me/finance/property/PropertyActivity";
    public static final String ACTIVITY_ME_PROPERTY_DETAILS = "/me/finance/property/details/PropertyDetailsActivity";
    public static final String ACTIVITY_ME_PROPERTY_RECORD = "/me/finance/property/PropertyRecordActivity";
    public static final String ACTIVITY_ME_RECORD = "/me/finance/record/RecordActivity";
    public static final String ACTIVITY_ME_AD = "/me/finance/ad/AdActivity";
    public static final String ACTIVITY_ME_ORDER = "/me/finance/order/OrderActivity";
    public static final String ACTIVITY_ME_PROPERTY_RECORD_DETAILS = "/me/finance/property/details/PropertyRecordDetailsActivity";
    //---finiance

    //-setting
    public static final String ACTIVITY_ME_RECEIPT = "/me/setting/receipt/ReceiptActivity";
    public static final String ACTIVITY_ME_RECEIPT_SETTING = "/me/setting/receipt/ReceiSettingActivity";
    public static final String ACTIVITY_ME_RECEIPT_BIND = "/me/setting/receipt/ReceiptBindActivity";
    public static final String ACTIVITY_ME_COINADDRESS_COINADDRESS = "/me/setting/coinaddress/SelectPayWayActivity";
    public static final String ACTIVITY_ME_COINADDRESS_COINADDRESS_ADD = "/me/setting/coinaddress/CoinAddressAddActivity";
    public static final String ACTIVITY_ME_RECEIPT_ADD = "/me/setting/receipt/add/ReceiptAddActivity";

    //-setting

    //-safe
    public static final String ACTIVITY_ME_AUTHENTICATION = "/me/safe/authentication/AuthenticationActivity";
    public static final String ACTIVITY_ME_SAFECENTRE = "/me/safe/safecentre/SafeCentreActivity";
    public static final String ACTIVITY_ME_SAFECENTRE_PHONE = "/me/safe/safecentre/PhoneActivity";
    public static final String ACTIVITY_ME_SAFECENTRE_EMAIL = "/me/safe/safecentre/EmailActivity";
    public static final String ACTIVITY_ME_SAFECENTRE_GOOGLE_OPEN = "/me/safe/safecentre/GoogleOpenActivity";
    public static final String ACTIVITY_ME_SAFECENTRE_GOOGLE_BIND = "/me/safe/safecentre/GoogleBindActivity";
    public static final String ACTIVITY_ME_SAFECENTRE_APIINTERFACE = "/me/safe/apiinterface/ApiInterfaceActivity";
    public static final String ACTIVITY_ME_SAFECENTRE_LOGIN_PASS = "/me/safe/safecentre/LoginPwdUpdateActivity";
    public static final String ACTIVITY_ME_TRADE_PASS = "/me/safe/safecentre/TradePwdActivity";
    public static final String ACTIVITY_ME_TRADE_PASS_FORGET = "/me/safe/safecentre/pwdforget/ForgetPwdActivity";
    public static final String ACTIVITY_ME_SAFECENTRE_GOOGLE_REMOVE = "/me/safe/safecentre/GoogleRemoveActivity";
    //-safe

    //-trade
    public static final String ACTIVITY_TRADE_AD_CREATE = "/trade/AdCreateActivity";
    public static final String ACTIVITY_TRADE_COMMISION_DETAILS = "/trade/CommissionDetailsActivity";
    public static final String ACTIVITY_TRADE_LC_ORDER = "/trade/LcOrderActivity";
    public static final String ACTIVITY_TRADE_LC_DETAILS_ORDER = "/trade/Lc/LcOrderDetailsActivity";
    public static final String ACTIVITY_TRADE_LC_UNPAY_DETAILS_ORDER = "/trade/Lc/LcOrderUnpayDetailsActivity";
    public static final String ACTIVITY_TRADE_LC_PAIY_DETAILS_ORDER = "/trade/Lc/LcOrderPaidDetailsActivity";
    public static final String ACTIVITY_TRADE_CC_OPEN_ORDERS = "/trade/Cc/OpenOrdersActivity";
    public static final String ACTIVITY_TRADE_LC_ORCER_APPEAL = "/trade/Cc/LcOrderAppealActivity";
    public static final String ACTIVITY_TRADE_LC_ORCER_RECEIPT_CODE = "/trade/Cc/LcReceiptCodeActivity";
    public static final String ACTIVITY_TRADE_LC_CHAT = "/trade/Cc/ChatActivity";
    public static final String ACTIVITY_TRADE_AD_SELECT_PAYWAY = "/trade/adCreate/SelectPayWayActivity";
    public static final String ACTIVITY_TRADE_CFD_KLINE = "/trade/cfd/CfdKlineActivity";
    public static final String ACTIVITY_TRADE_CFD_KLINE_HORIZONTAL = "/trade/cfd/CfdKlineHorizontalActivity";
    public static final String ACTIVITY_TRADE_CFD_DETAILS = "/trade/cfd/CfdDetailsActivity";

    //-trade

    //-system
    public static final String ACTIVITY_ME_VALUETYPE = "/me/system/ValueTypeActivity";
    public static final String ACTIVITY_ME_LANGUAGE = "/me/system/LanguageActivity";
    public static final String ACTIVITY_ME_ABOUT = "/me/system/AboutActivity";
    //-system

    //me-module---------------------------------------------------------------

    //user---------------------------------------------------------------
    //绑定手机号
    public static final String ACTIVITY_USER_PHONE = "/user/UserPhoneActivity";
    //绑定邮箱
    public static final String ACTIVITY_USER_EMAIL = "/user/UserEmailActivity";
    //身份认证
    public static final String ACTIVITY_USER_ID_CARD = "/user/UserIdCardActivity";
    //user----------------------------------------------------------------


    //onActivityResult----------------------------------------------------------------
    public static final int REGISTERVERIFYCODE = 1000;
    //onActivityResult----------------------------------------------------------------

}
