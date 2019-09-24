package com.spark.acclient.base;


public class AcHost {
    public static String geeCaptchaUrl = "captcha/mm/gee";
    public static String checkCaptchaUrl = "captcha/check";
//    public static String phoneCaptchaUrl = "captcha/mm/phone";
    public static String phoneCaptchaUrl = "captcha/permission";

    //查询平台支持到币种信息
    public static String coinSupportUrl = "asset/coin/support";
    //查询指定币种的钱包地址
    public static String coinAddressUrl = "asset/address/";
    //查询用户某个业务所有资产信息
    public static String coinOutInfoUrl = "asset/wallet";
    //增加用户某个币种的提币地址信息
    public static String coinAddressAddUrl = "asset/withdrawAddress/add";
    //查询用户的某个币种的提币地址信息
    public static String coinAddressListUrl = "asset/withdrawAddress/list";
    //删除提币地址信息
    public static String coinAddressDeleteUrl = "asset/withdrawAddress/del";
    //批量删除提币地址信息
    public static String coinAddressBatchDeleteUrl = "asset/withdrawAddress/batch/del";
    //获取钱包金额
    public static String coinWalletUrl = "asset/wallet/";
    //查询用户的某个币种的提币地址信息
    public static String withdrawAddressUrl = "asset/withdrawAddress/";
    //用户提币请求
    public static String withdrawRequestUrl = "asset/wallet/withdraw";
    //用户资金划转请求
    public static String coinTransferUrl = "asset/wallet/transfer";
    //查询用户某个业务所有资产交易信息
    public static String transLogUrl = "asset/translog/";
    //资金明细type
    public static String transLogTypeUrl = "asset/transactionType/";
    //permission获取验证码
    public static String sendVertifyCodeUrl = "captcha/permission";
}
