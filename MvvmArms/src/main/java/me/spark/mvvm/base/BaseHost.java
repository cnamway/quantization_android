package me.spark.mvvm.base;


public class BaseHost {
    //获取域名配置url
//    public static String HOST = "http://api.bitotc.bench.bitpay.com/";
    public static String HOST = "http://cas.bitrade.quantization.com/";

    //        public static String LOGIN_HOST = "http://cas.bitotc.bench.bitpay.com/";
    public static String LOGIN_HOST = "http://cas.bitrade.quantization.com/";

    //        public static String LOGIN_SUB_HOST = "http://api.bitotc.bench.bitpay.com/";
    public static String LOGIN_SUB_HOST = "http://api.bitrade.quantization.com/";


    public static String UC_HOST = LOGIN_SUB_HOST + "uc/";
    public static String AC_HOST = LOGIN_SUB_HOST + "ac/";
    public static String OTC_HOST = LOGIN_SUB_HOST + "otc/";
    public static String SPOT_HOST = LOGIN_SUB_HOST + "spot/";
    public static String QUOTE_HOST = LOGIN_SUB_HOST + "quote/";
    public static String KLINE_HOST = LOGIN_SUB_HOST + "kline/";
    public static String CFD_HOST = LOGIN_SUB_HOST + "cfd/";
    public static String PRICE_HOST = LOGIN_SUB_HOST + "price-api/";
    public static String CMS_HOST = LOGIN_SUB_HOST + "cms-api/";
    public static String AGENT_HOST = "http://api.agent.sscoin.cc/";
    public static String QUANTIZATION_HOST = LOGIN_SUB_HOST + "quantization/";

    /**
     * AC,UC,OTC模块地址后缀
     */
    public static final String TYPE_AC = "ac";
    public static final String TYPE_UC = "uc";
    public static final String TYPE_OTC = "otc";
    public static final String TYPE_SPOT = "spot";
    public static final String TYPE_CFD = "cfd";
    public static final String TYPE_QUANTIZATION = "quantization";

    public static String getBusinessUrl(String businessType) {
        String url = "";
        switch (businessType) {
            case BaseHost.TYPE_AC:
                url = BaseHost.AC_HOST;
                break;
            case BaseHost.TYPE_UC:
                url = BaseHost.UC_HOST;
                break;
            case BaseHost.TYPE_OTC:
                url = BaseHost.OTC_HOST;
                break;
            case BaseHost.TYPE_SPOT:
                url = BaseHost.SPOT_HOST;
                break;
            case BaseHost.TYPE_CFD:
                url = BaseHost.CFD_HOST;
                break;
            case BaseHost.TYPE_QUANTIZATION:
                url = BaseHost.QUANTIZATION_HOST;
                break;
        }
        return url;
    }


}
