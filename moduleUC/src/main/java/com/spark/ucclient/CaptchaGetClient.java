package com.spark.ucclient;


import com.google.gson.Gson;
import com.spark.ucclient.base.UcHost;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.json.JSONException;
import org.json.JSONObject;

import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.base.BaseHost;
import me.spark.mvvm.base.BaseRequestCode;
import me.spark.mvvm.base.BaseResponseError;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.http.BaseHttpClient;
import me.spark.mvvm.http.GeneralResult;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.SPUtils;

/**
 * UC校验模块
 */
public class CaptchaGetClient extends BaseHttpClient {

    private static CaptchaGetClient ucClient;

    private CaptchaGetClient() {
    }

    public static CaptchaGetClient getInstance() {
        if (ucClient == null) {
            synchronized (CaptchaGetClient.class) {
                if (ucClient == null) {
                    ucClient = new CaptchaGetClient();
                }
            }
        }
        return ucClient;
    }

    /**
     * 极验
     */
    public void geeCaptcha() {
        EasyHttp.get(UcHost.geeCaptchaUrl)
                .baseUrl(BaseHost.UC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("geeCaptcha", s);
                        try {
                            JSONObject object = new JSONObject(s.replace("\\", ""));
                            EventBusUtils.postSuccessEvent(EvKey.geeCaptcha, BaseRequestCode.OK, "", object);
                        } catch (JSONException e) {
                            LogUtils.e(e.toString());
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        LogUtils.e("geeCaptcha", e.getCode() + "--" + e.getMessage());
                        EventBusUtils.postErrorEvent(EvKey.geeCaptcha, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 校验极验证
     */
    public void checkCaptcha(String check, String cid) {
        EasyHttp.get(UcHost.checkCaptchaUrl)
                .baseUrl(BaseHost.UC_HOST)
                .headers("check", check)
                .headers("cid", cid)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("checkCaptcha", s);
                        try {
                            JSONObject object = new JSONObject(s);
                            int code = object.optInt("code");
                            if (code != BaseRequestCode.OK) {
                                if (code == BaseRequestCode.ERROR_401) {
                                    GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                                    uodateLogin(generalResult);
                                } else {
                                    BaseResponseError responseError = new Gson().fromJson(s, BaseResponseError.class);
                                    EventBusUtils.postErrorEvent(EvKey.checkCaptcha, responseError.getCode(), responseError.getMessage(), responseError);
                                }
                            } else {
                                EventBusUtils.postSuccessEvent(EvKey.checkCaptcha, BaseRequestCode.OK, "");
                            }
                        } catch (JSONException e) {
                            LogUtils.e(e.toString());
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        LogUtils.e("checkCaptcha", e.getCode() + "--" + e.getMessage());
                        EventBusUtils.postErrorEvent(EvKey.checkCaptcha, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 获取手机验证码
     */
    public void phoneCaptcha(String phone) {
        EasyHttp.get(UcHost.phoneCaptchaUrl)
                .baseUrl(BaseHost.UC_HOST)
                .headers("Cookie", SPUtils.getInstance().getUcCookie())
                .params("type", "phone")
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("phoneCaptcha", s);
                        try {
                            JSONObject object = new JSONObject(s);
                            int code = object.optInt("code");
                            if (code != BaseRequestCode.OK) {
                                if (code == BaseRequestCode.ERROR_401) {
                                    GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                                    uodateLogin(generalResult);
                                } else {
                                    BaseResponseError responseError = new Gson().fromJson(s, BaseResponseError.class);
                                    EventBusUtils.postErrorEvent(EvKey.phoneCaptcha, responseError.getCode(), responseError.getMessage(), responseError);
                                }
                            } else {
                                EventBusUtils.postSuccessEvent(EvKey.phoneCaptcha, BaseRequestCode.OK, "");
                            }
                        } catch (JSONException e) {
                            LogUtils.e(e.toString());
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        LogUtils.e("phoneCaptcha", e.getCode() + "--" + e.getMessage());
                        EventBusUtils.postErrorEvent(EvKey.phoneCaptcha, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 获取手机验证码,带有header
     */
    public void phoneCaptchaWithHeader(String phone, String check, String cid) {
        EasyHttp.get(UcHost.phoneCaptchaUrl)
                .baseUrl(BaseHost.UC_HOST)
                .headers("check", check)
                .headers("cid", cid)
                .headers("Cookie", SPUtils.getInstance().getUcCookie())
                .params("type", "phone")
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("phoneCaptcha", s);
                        try {
                            JSONObject object = new JSONObject(s);
                            int code = object.optInt("code");
                            if (code != BaseRequestCode.OK) {
                                if (code == BaseRequestCode.ERROR_401) {
                                    GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                                    uodateLogin(generalResult);
                                } else {
                                    BaseResponseError responseError = new Gson().fromJson(s, BaseResponseError.class);
                                    EventBusUtils.postErrorEvent(EvKey.phoneCaptcha, responseError.getCode(), responseError.getMessage(), responseError);
                                }
                            } else {
                                EventBusUtils.postSuccessEvent(EvKey.phoneCaptcha, BaseRequestCode.OK, "");
                            }
                        } catch (JSONException e) {
                            LogUtils.e(e.toString());
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        LogUtils.e("phoneCaptcha", e.getCode() + "--" + e.getMessage());
                        EventBusUtils.postErrorEvent(EvKey.phoneCaptcha, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 获取手机验证码
     */
    public void phoneCaptcha2(String phone) {
        EasyHttp.get(UcHost.phoneCaptchaUrl2)
                .baseUrl(BaseHost.UC_HOST)
                .params("phone", phone)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("phoneCaptcha", s);
                        try {
                            JSONObject object = new JSONObject(s);
                            int code = object.optInt("code");
                            if (code != BaseRequestCode.OK) {
                                if (code == BaseRequestCode.ERROR_401) {
                                    GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                                    uodateLogin(generalResult);
                                } else {
                                    BaseResponseError responseError = new Gson().fromJson(s, BaseResponseError.class);
                                    EventBusUtils.postErrorEvent(EvKey.phoneCaptcha, responseError.getCode(), responseError.getMessage(), responseError);
                                }
                            } else {
                                EventBusUtils.postSuccessEvent(EvKey.phoneCaptcha, BaseRequestCode.OK, "");
                            }
                        } catch (JSONException e) {
                            LogUtils.e(e.toString());
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        LogUtils.e("phoneCaptcha", e.getCode() + "--" + e.getMessage());
                        EventBusUtils.postErrorEvent(EvKey.phoneCaptcha, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 获取手机验证码,带有header
     */
    public void phoneCaptchaWithHeader2(String phone, String check, String cid) {
        EasyHttp.get(UcHost.phoneCaptchaUrl2)
                .baseUrl(BaseHost.UC_HOST)
                .headers("check", check)
                .headers("cid", cid)
                .params("phone", phone)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("phoneCaptcha", s);
                        try {
                            JSONObject object = new JSONObject(s);
                            int code = object.optInt("code");
                            if (code != BaseRequestCode.OK) {
                                if (code == BaseRequestCode.ERROR_401) {
                                    GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                                    uodateLogin(generalResult);
                                } else {
                                    BaseResponseError responseError = new Gson().fromJson(s, BaseResponseError.class);
                                    EventBusUtils.postErrorEvent(EvKey.phoneCaptcha, responseError.getCode(), responseError.getMessage(), responseError);
                                }
                            } else {
                                EventBusUtils.postSuccessEvent(EvKey.phoneCaptcha, BaseRequestCode.OK, "");
                            }
                        } catch (JSONException e) {
                            LogUtils.e(e.toString());
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        LogUtils.e("phoneCaptcha", e.getCode() + "--" + e.getMessage());
                        EventBusUtils.postErrorEvent(EvKey.phoneCaptcha, e.getCode(), e.getMessage());
                    }
                });
    }


    /**
     * 获取邮箱验证码
     */
    public void emailCaptcha(String email) {
        EasyHttp.get(UcHost.emailCaptchaUrl)
                .baseUrl(BaseHost.UC_HOST)
                .params("email", email)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("emailCaptcha", s);
                        try {
                            JSONObject object = new JSONObject(s);
                            int code = object.optInt("code");
                            if (code != BaseRequestCode.OK) {
                                if (code == BaseRequestCode.ERROR_401) {
                                    GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                                    uodateLogin(generalResult);
                                } else {
                                    BaseResponseError responseError = new Gson().fromJson(s, BaseResponseError.class);
                                    EventBusUtils.postErrorEvent(EvKey.emailCaptcha, responseError.getCode(), responseError.getMessage(), responseError);
                                }
                            } else {
                                EventBusUtils.postSuccessEvent(EvKey.emailCaptcha, BaseRequestCode.OK, "");
                            }
                        } catch (JSONException e) {
                            LogUtils.e(e.toString());
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        LogUtils.e("phoneCaptcha", e.getCode() + "--" + e.getMessage());
                        EventBusUtils.postErrorEvent(EvKey.emailCaptcha, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 获取邮箱验证码,带有header
     */
    public void emailCaptchaWithHeader(String email, String check, String cid) {
        EasyHttp.get(UcHost.emailCaptchaUrl)
                .baseUrl(BaseHost.UC_HOST)
                .headers("check", check)
                .headers("cid", cid)
                .params("email", email)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("emailCaptchaWithHeader", s);
                        try {
                            JSONObject object = new JSONObject(s);
                            int code = object.optInt("code");
                            if (code != BaseRequestCode.OK) {
                                if (code == BaseRequestCode.ERROR_401) {
                                    GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                                    uodateLogin(generalResult);
                                } else {
                                    BaseResponseError responseError = new Gson().fromJson(s, BaseResponseError.class);
                                    EventBusUtils.postErrorEvent(EvKey.emailCaptcha, responseError.getCode(), responseError.getMessage(), responseError);
                                }
                            } else {
                                EventBusUtils.postSuccessEvent(EvKey.emailCaptcha, BaseRequestCode.OK, "");
                            }
                        } catch (JSONException e) {
                            LogUtils.e(e.toString());
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        LogUtils.e("phoneCaptcha", e.getCode() + "--" + e.getMessage());
                        EventBusUtils.postErrorEvent(EvKey.emailCaptcha, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 发送验证码
     */
    public void sendVertifyCode(int type) {
        EasyHttp.get(UcHost.sendVertifyCodeUrl)
                .baseUrl(BaseHost.UC_HOST)
                .headers("tgt", SPUtils.getInstance().getTgc())
                .headers("Connection", "close")
                .params("type", type == 0 ? "phone" : "email")
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        EventBusUtils.postSuccessEvent(EvKey.sendVertifyCode, BaseRequestCode.OK, "");
                    }

                    @Override
                    public void onError(ApiException e) {
                        EventBusUtils.postErrorEvent(EvKey.sendVertifyCode, e.getCode(), e.getMessage());
                    }
                });
    }

}
