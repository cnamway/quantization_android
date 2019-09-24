package com.spark.ucclient;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spark.ucclient.base.UcHost;
import com.spark.ucclient.pojo.CountryEntity;
import com.spark.ucclient.pojo.RegisterByEmailDto;
import com.spark.ucclient.pojo.RegisterByPhoneDto;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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

/**
 * 注册
 */
public class RegisterClient extends BaseHttpClient {

    private static RegisterClient ucClient;

    private RegisterClient() {
    }

    public static RegisterClient getInstance() {
        if (ucClient == null) {
            synchronized (RegisterClient.class) {
                if (ucClient == null) {
                    ucClient = new RegisterClient();
                }
            }
        }
        return ucClient;
    }

    /**
     * 获取国家列表
     */
    public void findSupportCountry() {
        EasyHttp.post(UcHost.findSupportCountryUrl)
                .baseUrl(BaseHost.UC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("findSupportCountry", s);

                        try {
                            JSONObject object = new JSONObject(s);
                            int code = object.optInt("code");
                            if (code == BaseRequestCode.OK) {
                                Gson gson = BaseApplication.gson;
                                List<CountryEntity> objList = gson.fromJson(object.getString("data"), new TypeToken<List<CountryEntity>>() {
                                }.getType());
                                EventBusUtils.postSuccessEvent(EvKey.findSupportCountry, BaseRequestCode.OK, "", objList);
                            } else {
                                if (code == BaseRequestCode.ERROR_401) {
                                    GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                                    uodateLogin(generalResult);
                                } else {
                                    BaseResponseError responseError = new Gson().fromJson(s, BaseResponseError.class);
                                    EventBusUtils.postErrorEvent(EvKey.findSupportCountry, responseError.getCode(), responseError.getMessage(), responseError);
                                }
                            }
                        } catch (JSONException e) {
                            LogUtils.e(e.toString());
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        LogUtils.e("findSupportCountry", e.getCode() + "--" + e.getMessage());
                        EventBusUtils.postErrorEvent(EvKey.findSupportCountry, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 手机注册
     */
    public void registerByPhone(String username, String code, String password, String country, String promotion, String phone) {
        RegisterByPhoneDto registerByPhoneDto = new RegisterByPhoneDto();
        registerByPhoneDto.setCountry(country);
        registerByPhoneDto.setMobilePhone(phone);
        registerByPhoneDto.setPassword(password);
        registerByPhoneDto.setPromotion(promotion);
        registerByPhoneDto.setTid(DeviceUtils.getTid());
        registerByPhoneDto.setUsername(username);

        EasyHttp.post(UcHost.registerByPhoneUrl)
                .baseUrl(BaseHost.UC_HOST)
                .headers("check", "phone:" + phone + ":" + code)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(registerByPhoneDto))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("phoneCaptcha", s);
                        try {
                            JSONObject object = new JSONObject(s);
                            int code = object.optInt("code");
                            if (code == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.registerByPhone, BaseRequestCode.OK, "");
                            } else {
                                if (code == BaseRequestCode.ERROR_401) {
                                    GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                                    uodateLogin(generalResult);
                                } else {
                                    BaseResponseError responseError = new Gson().fromJson(s, BaseResponseError.class);
                                    EventBusUtils.postErrorEvent(EvKey.registerByPhone, responseError.getCode(), responseError.getMessage(), responseError);
                                }
                            }
                        } catch (JSONException e) {
                            LogUtils.e(e.toString());
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        LogUtils.e("phoneCaptcha", e.getCode() + "--" + e.getMessage());
                        EventBusUtils.postErrorEvent(EvKey.registerByPhone, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 手机注册带请求头
     */
    public void registerByPhone(String cid, String check, String username, String password, String country, String promotion, String phone) {
        RegisterByPhoneDto registerByPhoneDto = new RegisterByPhoneDto();
        registerByPhoneDto.setCountry(country);
        registerByPhoneDto.setMobilePhone(phone);
        registerByPhoneDto.setPassword(password);
        registerByPhoneDto.setPromotion(promotion);
        registerByPhoneDto.setTid(DeviceUtils.getTid());
        registerByPhoneDto.setUsername(username);

        EasyHttp.post(UcHost.registerByPhoneUrl)
                .baseUrl(BaseHost.UC_HOST)
                .headers("cid", cid)
                .headers("check", check)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(registerByPhoneDto))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("phoneCaptcha", s);
                        try {
                            JSONObject object = new JSONObject(s);
                            int code = object.optInt("code");
                            if (code == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.registerByPhone, BaseRequestCode.OK, "");
                            } else {
                                if (code == BaseRequestCode.ERROR_401) {
                                    GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                                    uodateLogin(generalResult);
                                } else {
                                    BaseResponseError responseError = new Gson().fromJson(s, BaseResponseError.class);
                                    EventBusUtils.postErrorEvent(EvKey.registerByPhone, responseError.getCode(), responseError.getMessage(), responseError);
                                }
                            }
                        } catch (JSONException e) {
                            LogUtils.e(e.toString());
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        LogUtils.e("phoneCaptcha", e.getCode() + "--" + e.getMessage());
                        EventBusUtils.postErrorEvent(EvKey.registerByPhone, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 邮箱注册
     */
    public void registerByEmail(String username, String code, String password, String country, String promotion, String email) {
        RegisterByEmailDto registerByEmailDto = new RegisterByEmailDto();
        registerByEmailDto.setCountry(country);
        registerByEmailDto.setEmail(email);
        registerByEmailDto.setPassword(password);
        registerByEmailDto.setPromotion(promotion);
        registerByEmailDto.setTid(DeviceUtils.getTid());
        registerByEmailDto.setUsername(username);

        EasyHttp.post(UcHost.registerByEmailUrl)
                .baseUrl(BaseHost.UC_HOST)
                .headers("check", "email:" + email + ":" + code)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(registerByEmailDto))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            JSONObject object = new JSONObject(s);
                            int code = object.optInt("code");
                            if (code == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.registerByEmail, BaseRequestCode.OK, "");
                            } else {
                                if (code == BaseRequestCode.ERROR_401) {
                                    GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                                    uodateLogin(generalResult);
                                } else {
                                    BaseResponseError responseError = new Gson().fromJson(s, BaseResponseError.class);
                                    EventBusUtils.postErrorEvent(EvKey.registerByEmail, responseError.getCode(), responseError.getMessage(), responseError);
                                }
                            }
                        } catch (JSONException e) {
                            LogUtils.e(e.toString());
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        EventBusUtils.postErrorEvent(EvKey.registerByEmail, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 邮箱注册带请求头
     */
    public void registerByEmail(String cid, String check, String username, String password, String country, String promotion, String email) {
        RegisterByEmailDto registerByEmailDto = new RegisterByEmailDto();
        registerByEmailDto.setCountry(country);
        registerByEmailDto.setEmail(email);
        registerByEmailDto.setPassword(password);
        registerByEmailDto.setPromotion(promotion);
        registerByEmailDto.setTid(DeviceUtils.getTid());
        registerByEmailDto.setUsername(username);

        EasyHttp.post(UcHost.registerByEmailUrl)
                .baseUrl(BaseHost.UC_HOST)
                .headers("cid", cid)
                .headers("check", check)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(registerByEmailDto))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            JSONObject object = new JSONObject(s);
                            int code = object.optInt("code");
                            if (code == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.registerByEmail, BaseRequestCode.OK, "");
                            } else {
                                if (code == BaseRequestCode.ERROR_401) {
                                    GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                                    uodateLogin(generalResult);
                                } else {
                                    BaseResponseError responseError = new Gson().fromJson(s, BaseResponseError.class);
                                    EventBusUtils.postErrorEvent(EvKey.registerByEmail, responseError.getCode(), responseError.getMessage(), responseError);
                                }
                            }
                        } catch (JSONException e) {
                            LogUtils.e(e.toString());
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        EventBusUtils.postErrorEvent(EvKey.registerByEmail, e.getCode(), e.getMessage());
                    }
                });
    }

}
