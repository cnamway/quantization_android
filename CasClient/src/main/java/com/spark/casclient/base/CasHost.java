package com.spark.casclient.base;

import me.spark.mvvm.base.BaseHost;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/9
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CasHost {
    public static String casUrl = "cas/v1/tickets";
    public static String serviceTicketUrl = "cas/v1/tickets/";
    public static String appCasUrl = "cas";
    public static String casCheckUrl = "check";
    public static String sendVertifyCodeUrl = "cas/captcha/permission";
    public static String vertifyCodeUrl = "cas/captcha/check";
    public static String casInfo = "cas-web/getinfo.json";
    public static String geeCaptchaUrl = "cas/captcha/mm/gee";

    /**
     * 获取service
     */
    public static String getService(String businessType) {
        return BaseHost.getBusinessUrl(businessType) + "cas?client_name=CasClient";
    }


}
