package com.spark.casclient;

import com.google.gson.Gson;
import com.spark.casclient.base.CasHost;
import com.spark.casclient.pojo.CasConfig;
import com.spark.casclient.pojo.LoginBean;
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
import me.spark.mvvm.utils.DeviceUtils;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.SPUtils;
import me.spark.mvvm.utils.StringUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/9
 * 描    述：Cas登录工具类
 * 修订历史：
 * ================================================
 */
public class CasClient extends BaseHttpClient {
    private static CasClient sCasLoginUtils;

    private CasClient() {
    }

    public static CasClient getInstance() {
        if (sCasLoginUtils == null) {
            synchronized (CasClient.class) {
                if (sCasLoginUtils == null) {
                    sCasLoginUtils = new CasClient();
                }
            }
        }
        return sCasLoginUtils;
    }

    /**
     * 登录接口
     *
     * @param username   用户名
     * @param password   密码
     * @param rememberMe true/false 记住我，可选，为 true时tgt失效时间为一星期
     */
    public void login(String username,
                      String password,
                      String rememberMe) {
        EasyHttp.post(CasHost.casUrl)
                .baseUrl(BaseHost.LOGIN_HOST)
                .headers("tid", DeviceUtils.getTid())
                .params("username", username)
                .params("password", password)
                .params("rememberMe", rememberMe)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            LoginBean generalResult = BaseApplication.gson.fromJson(s, LoginBean.class);
                            if (generalResult != null) {
                                String tgc = generalResult.getTgc();
                                String isNewDevice = generalResult.getIs_new_device() + "";
                                LoginBean.NoticeDataBean noticeData = generalResult.getNotice_data();
                                if (StringUtils.isNotEmpty(tgc)) {
                                    SPUtils.getInstance().setTgc(tgc);
                                    if (noticeData == null) {
                                        noticeData = new LoginBean.NoticeDataBean();
                                    }
                                    EventBusUtils.postSuccessEvent(EvKey.login, BaseRequestCode.OK, isNewDevice, noticeData);
                                } else {
                                    BaseResponseError responseError = new Gson().fromJson(s, BaseResponseError.class);
                                    EventBusUtils.postErrorEvent(EvKey.login, responseError.getCode(), responseError.getMessage(), responseError);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        EventBusUtils.postErrorEvent(EvKey.login, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 获取业务系统登录凭据ticket
     */
    public void getServiceTicket(String tgc, final String type) {
        EasyHttp.post(CasHost.serviceTicketUrl + tgc)
                .baseUrl(BaseHost.LOGIN_HOST)
                .headers("tid", DeviceUtils.getTid())
                .headers("tgt", SPUtils.getInstance().getTgc())
                .params("service", CasHost.getService(type))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            JSONObject object = new JSONObject(s);
                            int code = object.optInt("code");
                            if (code != BaseRequestCode.OK) {
                                if (code == BaseRequestCode.ERROR_401) {
                                    GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                                    uodateLogin(generalResult);
                                } else {
                                    BaseResponseError responseError = new Gson().fromJson(s, BaseResponseError.class);
                                    EventBusUtils.postErrorEvent(EvKey.getServiceTicket, responseError.getCode(), responseError.getMessage(), responseError);
                                }
                            }
                        } catch (JSONException e) {
                            LogUtils.e(e.toString());
                            loginApp(s, type);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        EventBusUtils.postErrorEvent(EvKey.getServiceTicket, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 业务系统登录接口
     */
    public void loginApp(String ticket, final String type) {
        EasyHttp.get(BaseHost.getBusinessUrl(type) + CasHost.appCasUrl)
                .baseUrl(BaseHost.LOGIN_HOST)
                .headers("tid", DeviceUtils.getTid())
                .params("ticket", ticket)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            JSONObject object = new JSONObject(s);
                            int code = object.optInt("code");
                            if (code != BaseRequestCode.OK) {
                                BaseResponseError responseError = new Gson().fromJson(s, BaseResponseError.class);
                                EventBusUtils.postErrorEvent(EvKey.loginApp, responseError.getCode(), responseError.getMessage(), responseError);
                            } else {
                                if (BaseHost.TYPE_UC.equals(type)) {
                                    LogUtils.e("------UC登录成功------");
                                    getServiceTicket(SPUtils.getInstance().getTgc(), BaseHost.TYPE_AC);
                                } else if (BaseHost.TYPE_AC.equals(type)) {
                                    LogUtils.e("------AC登录成功------");
                                    getServiceTicket(SPUtils.getInstance().getTgc(), BaseHost.TYPE_OTC);
                                } else if (BaseHost.TYPE_OTC.equals(type)) {
                                    LogUtils.e("------OTC登录成功------");
                                    getServiceTicket(SPUtils.getInstance().getTgc(), BaseHost.TYPE_SPOT);
                                } else if (BaseHost.TYPE_SPOT.equals(type)) {
                                    LogUtils.e("------SPOT登录成功------");
                                    getServiceTicket(SPUtils.getInstance().getTgc(), BaseHost.TYPE_CFD);
                                } else if (BaseHost.TYPE_CFD.equals(type)) {
                                    LogUtils.e("------CFD登录成功------");
                                    EventBusUtils.postSuccessEvent(EvKey.loginApp, BaseRequestCode.OK, "");
                                } else if (BaseHost.TYPE_QUANTIZATION.equals(type)) {
                                    LogUtils.e("------QUANTIZATION登录成功------");
                                    EventBusUtils.postSuccessEvent(EvKey.loginApp, BaseRequestCode.OK, "");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        EventBusUtils.postErrorEvent(EvKey.loginApp, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 发送验证码
     */
    public void sendVertifyCode(int type) {
        EasyHttp.get(CasHost.sendVertifyCodeUrl)
                .baseUrl(BaseHost.LOGIN_HOST)
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

    /**
     * 验证短信验证码
     *
     * @param type
     * @param code
     */
    public void phoneCodeCheck(int type, String code) {
        String prev = "";
        //type 0 - 手机登录 1 - 邮箱登录
        switch (type) {
            case 0:
                prev = "phone::";
                break;
            case 1:
                prev = "email::";
                break;
            case 2://谷歌验证
                prev = "google::";
                break;
        }
        EasyHttp.get(CasHost.vertifyCodeUrl)
                .baseUrl(BaseHost.LOGIN_HOST)
                .headers("tgt", SPUtils.getInstance().getTgc())
                .headers("check", prev + code)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        EventBusUtils.postSuccessEvent(EvKey.phoneCodeCheck, BaseRequestCode.OK, "");
                    }

                    @Override
                    public void onError(ApiException e) {
                        EventBusUtils.postErrorEvent(EvKey.phoneCodeCheck, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 退出系统,isCheck为true表示tgt失效退出  false表示手动推出
     *
     * @param isCheck
     */
    public void logout(final boolean isCheck) {
        EasyHttp.delete(CasHost.casUrl + "/" + SPUtils.getInstance().getTgc())
                .baseUrl(BaseHost.LOGIN_HOST)
                .headers("tgt", SPUtils.getInstance().getTgc())
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e(s);
                        if (isCheck) {
                            EventBusUtils.postSuccessEvent(EvKey.logout_check, BaseRequestCode.OK, s);
                        } else {
                            EventBusUtils.postSuccessEvent(EvKey.logout, BaseRequestCode.OK, s);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        EventBusUtils.postErrorEvent(EvKey.logout, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 获取 cas配置Json
     */
    public void loadCasConfig() {
        EasyHttp.get(CasHost.casInfo)
                .baseUrl(BaseHost.LOGIN_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            CasConfig casConfig = BaseApplication.gson.fromJson(s, CasConfig.class);
                            if (casConfig.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.casConfig, casConfig.getCode(), s, casConfig);
                            } else {
                                EventBusUtils.postErrorEvent(EvKey.casConfig, casConfig.getCode(), BaseApplication.getInstance().getString(R.string.str_connect_error));
                            }
                        } catch (Exception e) {
                            postException(EvKey.casConfig, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.casConfig, e);
                    }
                });
    }


    /**
     * 根据type类型check登录模块
     */
    public void checkBusinessLogin(final String type) {
        EasyHttp.post(BaseHost.getBusinessUrl(type) + CasHost.casCheckUrl)
                .baseUrl(BaseHost.LOGIN_HOST)
                .headers("tid", DeviceUtils.getTid())
                .headers("tgt", SPUtils.getInstance().getTgc())
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String obj) {
                        if (StringUtils.isNotEmpty(obj)) {
                            if (obj.equals("true")) {
                                EventBusUtils.postSuccessEvent(EvKey.loginApp, BaseRequestCode.OK, "");
                            } else {
                                getServiceTicket(SPUtils.getInstance().getTgc(), type);
                            }
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        EventBusUtils.postErrorEvent(EvKey.loginApp, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 获取极验
     */
    public void geeCaptcha() {
        EasyHttp.get(CasHost.geeCaptchaUrl)
                .baseUrl(BaseHost.LOGIN_HOST)
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
     * 验证极验
     *
     * @param check
     * @param cid
     */
    public void checkGeeCaptcha(String check, String cid) {
        EasyHttp.get(CasHost.vertifyCodeUrl)
                .baseUrl(BaseHost.LOGIN_HOST)
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
                                BaseResponseError responseError = new Gson().fromJson(s, BaseResponseError.class);
                                EventBusUtils.postErrorEvent(EvKey.checkCaptcha, responseError.getCode(), responseError.getMessage(), responseError);
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
}
