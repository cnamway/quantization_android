package me.spark.mvvm.base;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.R;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/15
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class Constant {
    //是否为调试模式
    public static final boolean isDebug = true;
    //http降级
    public static boolean isHttpAndWs = false;

    //币币可用币种
    public static List<String> coinPairThumbBeanList = new ArrayList<>();
    public static String spotJson = "";
    //法币可用币种
    public static List<String> lcCoinPairThumbBeanList = new ArrayList<>();
    //Cas配置Json
    public static String casConfigJson = "";
    //
    public static String casSymbol = "";

    //版本更新Json
    public static String updateInfoJson = "";
    public static String b2bKlinePushJson = "";
    public static String cfdKlinePushJson = "";
    //资产详情Type
    public static String propertyDetailsTypeJson = "";
    public static String accountJson = "";
    public static boolean isContract = false;
    // 1 - 买  0 - 卖
    public static int lcBuyOrSell = 1;

    //币种缩略图轮询控制
    public static boolean isHomeVisiable = false;
    public static boolean isIndexVisiable = false;
    public static boolean isQoutesVisiable = false;
    public static boolean isTradeVisiable = false;
    public static boolean isKineVisiable = false;
    public static boolean isKineHorizontalVisiable = false;
    public static boolean isCfdKineVisiable = false;
    public static boolean isCfdKineHorizontalVisiable = false;
    public static boolean isMeFinanceVisiable = false;
    public static boolean isOpenOrdersVisiable = false;
    public static boolean isLoginVisiable = false;
    //BB盘口symbol
    public static String currentSymbol = "";

    public static String[] lcCoinNameArray;
    public static String[] allThumbSymbol;

    public static final String alipay = "alipay";  // 支付宝
    public static final String wechat = "wechat";  // 微信
    public static final String card = "card";      // 银行卡
    public static final String PAYPAL = "paypal";  // PAYPAL
    public static final String other = "other";    // 其他

    public static int tradePage = 0;               // 1 - 币币  0 - 法币  2 -合约
    public static int accountPage = 0;             // 0 - 币币  1 - 法币  3 - 合约
    public static int currencySymbolRate = 4;      // 币币所选币对汇率
    public static int currencyPriceRate = 4;       // 币币价格汇率
    public static int currencyNumberRate = 4;      // 币币数量汇率
    public static int ContractSymbolRate = 4;      // 合约所选币对汇率
    public static int contractPriceRate = 4;       // 合约价格汇率
    public static int contractNumberRate = 4;      // 合约数量汇率
    public static String USD = "USD";
    public static String CNY = "CNY";
    public static double usd2cnyRate = 1;          //美元对人民币的汇率

    public static boolean isCcFirstLoad = true;    //币币页面是否第一次加载
    public static int ccFirstLoadSymbolType = 0;
    public static String ccFirstLoadSymbol;

    public static final String Pay_alipay = BaseApplication.getInstance().getString(R.string.str_alipay);  // 支付宝
    public static final String Pay_wechat = BaseApplication.getInstance().getString(R.string.str_wechat);    // 微信
    public static final String Pay_card = BaseApplication.getInstance().getString(R.string.str_bank);   // 银行卡
    public static final String Pay_PAYPAL = BaseApplication.getInstance().getString(R.string.str_paypal); // PAYPAL
    public static final String Pay_other = BaseApplication.getInstance().getString(R.string.str_other); // 其他


    //cas
    public static final String AC = "AC";
    public static final String UC = "UC";
    public static final String OTC = "CTC";
    public static final String SPOT = "SPOT";
    public static final String KLINE = "KLINE";
    public static final String QUOTE = "QUOTE";
    public static final String CFD = "CFD";
    public static final String PRICE = "PRICE";
    public static final String CMS = "CMS";
    //ws
    public static final String MARKETWS = "marketWs";           //盘口
    public static final String KLINEWS = "klineWs";             //K线
    public static final String SPOTTRADEWS = "spotTradeWs";     //BB交易
    public static final String CFDTRADEWS = "cfdTradeWs";       //CFD交易

    public static boolean isSpotWSLoginIN = false;
    public static boolean isCfdWSLoginIN = false;

    //invite
    public static String inviteUrl = "http://www.sscoin.cc/h5/#/register";
    public static String inviteUrlSub = "/?agent=";
    //banner
    public static final String LanguageZH = "zh";
    public static final String LanguageEN = "en";
    //最近一次推送BB行情推送时间
    public static long lastB2BKlinePushTime = 0;
    //最近一次推送BB盘口推送时间
    public static long lastB2BMarketPushTime = 0;
    //最近一次推送Cfd行情推送时间
    public static long lastCfdKlinePushTime = 0;
    //最近一次推送CFD盘口推送时间
    public static long lastCfdMarketPushTime = 0;
    public static boolean isMonitorB2BKlinePush = false;
    public static boolean isMonitorCfdKlinePush = false;

    public static String lastB2BMarketPushSymbol = "";
    public static String lastCfdMarketPushSymbol = "";
    public static int getCoinSupportRetryTime = 0;

    public static int COMMON_PAGE_SIZE = 20;
}
