package com.spark.acclient;

import com.spark.acclient.base.AcHost;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.base.BaseHost;
import me.spark.mvvm.base.BaseRequestCode;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.http.BaseHttpClient;
import me.spark.mvvm.http.GeneralResult;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.SPUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/10
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CaptchaAcClient extends BaseHttpClient {
    private static CaptchaAcClient sCaptchaAcClient;

    private CaptchaAcClient() {
    }

    public static CaptchaAcClient getInstance() {
        if (sCaptchaAcClient == null) {
            synchronized (CaptchaAcClient.class) {
                if (sCaptchaAcClient == null) {
                    sCaptchaAcClient = new CaptchaAcClient();
                }
            }
        }
        return sCaptchaAcClient;
    }


    /**
     * 获取短信验证码
     *
     * @param phone
     */
    public void phoneCaptcha(String phone) {
        EasyHttp.get(AcHost.phoneCaptchaUrl)
                .baseUrl(BaseHost.AC_HOST)
                .headers("Cookie", SPUtils.getInstance().getAcCookie())
                .params("type", "phone")
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.acPhoneCaptcha, generalResult.getCode(), generalResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.acPhoneCaptcha, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.acPhoneCaptcha, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.acPhoneCaptcha, e);
                    }
                });
    }

    /**
     * 发送验证码
     */
    public void sendVertifyCode(int type) {
        EasyHttp.get(AcHost.sendVertifyCodeUrl)
                .baseUrl(BaseHost.AC_HOST)
                .headers("tgt", SPUtils.getInstance().getTgc())
                .headers("Connection", "close")
                .params("type", type == 0 ? "phone" : "email")
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        EventBusUtils.postSuccessEvent(EvKey.acPhoneCaptcha, BaseRequestCode.OK, "");
                    }

                    @Override
                    public void onError(ApiException e) {
                        EventBusUtils.postErrorEvent(EvKey.acPhoneCaptcha, e.getCode(), e.getMessage());
                    }
                });
    }
}
