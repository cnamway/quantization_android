package com.spark.ucclient.base;


public class UcHost {
    public static String geeCaptchaUrl = "captcha/mm/gee";
    public static String checkCaptchaUrl = "captcha/check";
    public static String userInfoUrl = "member/getUserInfo";
//    public static String phoneCaptchaUrl = "captcha/mm/phone";
    public static String phoneCaptchaUrl = "captcha/permission";
    public static String phoneCaptchaUrl2 = "captcha/mm/phone";
    public static String emailCaptchaUrl = "captcha/mm/email";
    public static String registerByPhoneUrl = "register/phone";
    public static String registerByEmailUrl = "register/email";
    public static String findSupportCountryUrl = "register/findSupportCountry";

    //上传base64处理后的图片
    public static String uploadBase64PicUrl = "upload/oss/base64";
    //实名认证申请
    public static String uploadAuthcUrl = "realname/apply";
    //实名认证详情
    public static String gauthInfoUrl = "realname/detail";
    //实名认证拒绝原因
    public static String authErrorInfoUrl = "member/security/setting";
    //修改登录密码
    public static String updateLoginPwdUrl = "member/update/password";
    //设置资金密码
    public static String setTradePasswordUrl = "member/setTradePassword";
    //查询佣金记录
    public static String commissionRecordUrl = "promotion/commission/record";
    //查询推广记录
    public static String recordPageUrl = "promotion/record/page";
    //锁仓
    public static String lockPositionRecordUrl = "promotion/lockPosition/record";
    //绑定邮箱
    public static String emailBindUrl = "member/bind/email";
    //绑定手机
    public static String phoneBindUrl = "member/bind/phone";
    //邀请详情
    public static String inviteDetilsUrl = "promotion/total/reward";
    //忘记密码-登录密码
    public static String forgetLoginPassUrl = "member/reset/password";
    //忘记密码-资金密码
    public static String forgetTradePassUrl = "member/reset/tradePassword";
    //修改资金密码
    public static String updateTradePassUrl = "member/update/tradePassword";
    //修改用户用户名
    public static String updateUserNameUrl = "member/modify/username";
    //生成Google验证码，用于绑定
    public static String googleAuthSendUrl = "member/googleAuth/send";
    //绑定Google验证
    public static String googleAuthBindUrl = "member/googleAuth/bind";
    //解除Google验证
    public static String googleAuthFreeUrl = "member/googleAuthFree/";
    //用户名修改校验
    public static String usernameChangeCheck = "member/username/change/check";
    //permission获取验证码
    public static String sendVertifyCodeUrl = "captcha/permission";
}
