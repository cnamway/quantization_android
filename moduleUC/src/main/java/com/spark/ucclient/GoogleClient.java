package com.spark.ucclient;


import com.spark.ucclient.base.UcHost;
import com.spark.ucclient.pojo.GoogleAuthSendResult;
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

/**
 * 谷歌认证模块
 */
public class GoogleClient extends BaseHttpClient {

    private static GoogleClient ucClient;

    private GoogleClient() {
    }

    public static GoogleClient getInstance() {
        if (ucClient == null) {
            synchronized (GoogleClient.class) {
                ucClient = new GoogleClient();
            }
        }
        return ucClient;
    }

    /**
     * 生成Google验证码，用于绑定
     */
    public void getGoogleAuthSend() {
        EasyHttp.get(UcHost.googleAuthSendUrl)
                .baseUrl(BaseHost.UC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                GoogleAuthSendResult result = BaseApplication.gson.fromJson(s, GoogleAuthSendResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.googleAuthSend, generalResult.getCode(), generalResult.getMessage(), result);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.googleAuthSend, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.googleAuthSend, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.googleAuthSend, e);
                    }
                });
    }

    /**
     * 绑定Google验证
     *
     * @param nickName
     */
    public void googleAuthBind(int type, String googleKey, String googleCode, String phone, String code, String countryCode) {
        EasyHttp.post(UcHost.googleAuthBindUrl)
                .baseUrl(BaseHost.UC_HOST)
                .headers("check", (type == 0 ? "phone:" + countryCode : "email:") + phone + ":" + code)
                .headers("Content-Type", "application/json")
                .params("secret", googleKey)
                .params("code", googleCode)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.googleAuthBind, generalResult.getCode(), generalResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.googleAuthBind, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.googleAuthBind, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.googleAuthBind, e);
                    }
                });
    }

    /**
     * 解除Google验证
     *
     * @param nickName
     */
    public void googleAuthRemove(String googleCode, String loginPass) {
        EasyHttp.get(UcHost.googleAuthFreeUrl + googleCode)
                .baseUrl(BaseHost.UC_HOST)
                .headers("Content-Type", "application/json")
                //.params("secret", googleKey)
                .params("code", googleCode)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.googleAuthRemove, generalResult.getCode(), generalResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.googleAuthRemove, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.googleAuthRemove, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.googleAuthRemove, e);
                    }
                });
    }

}
