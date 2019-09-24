package com.spark.ucclient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;
import com.spark.ucclient.base.UcHost;
import com.spark.ucclient.pojo.AuthErrorResult;
import com.spark.ucclient.pojo.AuthInfoEntity;
import com.spark.ucclient.pojo.EmailBindBean;
import com.spark.ucclient.pojo.PhoneBindBean;
import com.spark.ucclient.pojo.RegisterLoginPasswordDto;
import com.spark.ucclient.pojo.TradePasswordSetBean;
import com.spark.ucclient.pojo.UpdateLoginPwdEntity;
import com.spark.ucclient.pojo.UploadAuthInfoEntity;
import com.spark.ucclient.pojo.UploadBase64PicEntity;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.base.BaseHost;
import me.spark.mvvm.base.BaseRequestCode;
import me.spark.mvvm.base.BaseResponseError;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.http.BaseHttpClient;
import me.spark.mvvm.http.CommonResult;
import me.spark.mvvm.http.GeneralResult;
import me.spark.mvvm.utils.BitmapUtils;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/16
 * 描    述：安全模块
 * 修订历史：
 * ================================================
 */
public class SecurityClient extends BaseHttpClient {
    private static SecurityClient sFinanceClient;

    private SecurityClient() {
    }

    public static SecurityClient getInstance() {
        if (sFinanceClient == null) {
            synchronized (SecurityClient.class) {
                if (sFinanceClient == null) {
                    sFinanceClient = new SecurityClient();
                }
            }
        }
        return sFinanceClient;
    }


    /**
     * 图片上传
     *
     * @param original   0 - 身份证 1 - 护照
     * @param outputFile
     * @param type       0 - 正面   1 - 反面  2 - 手持
     * @param height
     * @param width
     */
    public void uploadIdCardPic(final int original, File outputFile, final int type, int width, int height) {
        Bitmap bitmap = BitmapUtils.zoomBitmap(BitmapFactory.decodeFile(outputFile.getAbsolutePath()), width, height);
        String base64Data = BitmapUtils.imgToBase64(bitmap);
        bitmap.recycle();

        UploadBase64PicEntity mUploadBase64PicEntity = new UploadBase64PicEntity();
        mUploadBase64PicEntity.setBase64Data("data:image/jpeg;base64," + base64Data);
        mUploadBase64PicEntity.setOss(true);

        EasyHttp.post(UcHost.uploadBase64PicUrl)
                .baseUrl(BaseHost.UC_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(mUploadBase64PicEntity))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            CommonResult uploadBase64Result = null;
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                uploadBase64Result = BaseApplication.gson.fromJson(s, CommonResult.class);
                                EventBusUtils.postSuccessEvent(original == 0 ? EvKey.uploadIdPic : EvKey.uploadPortPic, type, uploadBase64Result.getData(), original);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(original == 0 ? EvKey.uploadIdPic : EvKey.uploadPortPic, generalResult.getCode(), uploadBase64Result.getMessage(), original);
                                }
                            }
                        } catch (Exception e) {
                            postException(original == 0 ? EvKey.uploadIdPic : EvKey.uploadPortPic, e, original);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(original == 0 ? EvKey.uploadIdPic : EvKey.uploadPortPic, e, original);
                    }
                });
    }

    /**
     * 身份认证上传
     *
     * @param certifiedType          0 - 身份证   1 - 护照
     * @param idCardNumber           身份证号码 - 护照号码
     * @param identityCardImgFront   正面图片url
     * @param identityCardImgInHand  手持图片url
     * @param identityCardImgReverse 反面图片url
     * @param realName
     */
    public void uploadAuthInfo(final long certifiedType, String idCardNumber, String identityCardImgFront, String identityCardImgInHand, String identityCardImgReverse, String realName) {
        final int type = certifiedType == 0 ? 0 : 1;
        UploadAuthInfoEntity uploadAuthInfoEntity = new UploadAuthInfoEntity();
        uploadAuthInfoEntity.setCertifiedType(certifiedType);
        uploadAuthInfoEntity.setIdCardNumber(idCardNumber);
        uploadAuthInfoEntity.setIdentityCardImgFront(identityCardImgFront);
        uploadAuthInfoEntity.setIdentityCardImgInHand(identityCardImgInHand);
        uploadAuthInfoEntity.setIdentityCardImgReverse(identityCardImgReverse);
        uploadAuthInfoEntity.setRealName(realName);

        EasyHttp.post(UcHost.uploadAuthcUrl)
                .baseUrl(BaseHost.UC_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(uploadAuthInfoEntity))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(certifiedType == 0 ? EvKey.uploadAuthIDCard : EvKey.uploadAuthPort, generalResult.getCode(), BaseApplication.getInstance().getString(R.string.str_auth_success), type);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(certifiedType == 0 ? EvKey.uploadAuthIDCard : EvKey.uploadAuthPort, generalResult.getCode(), generalResult.getMessage(), type);
                                }
                            }
                        } catch (Exception e) {
                            postException(certifiedType == 0 ? EvKey.uploadAuthIDCard : EvKey.uploadAuthPort, e, type);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(certifiedType == 0 ? EvKey.uploadAuthIDCard : EvKey.uploadAuthPort, e, type);
                    }
                });
    }

    /**
     * 实名认证详情
     */
    public void getAuthInfo() {
        EasyHttp.post(UcHost.gauthInfoUrl)
                .baseUrl(BaseHost.UC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                AuthInfoEntity authInfoEntity = BaseApplication.gson.fromJson(s, AuthInfoEntity.class);
                                EventBusUtils.postSuccessEvent(EvKey.authInfo, generalResult.getCode(), authInfoEntity.getMessage(), authInfoEntity);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.authInfo, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.authInfo, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.authInfo, e);
                    }
                });
    }

    /**
     * 实名认证拒绝原因
     */
    public void getAuthErrorInfo() {
        EasyHttp.get(UcHost.authErrorInfoUrl)
                .baseUrl(BaseHost.UC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("getAuthErrorInfo", s);
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                AuthErrorResult authErrorResult = BaseApplication.gson.fromJson(s, AuthErrorResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.authErrorInfo, authErrorResult.getCode(), authErrorResult.getMessage(), authErrorResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.authErrorInfo, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.authErrorInfo, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.authErrorInfo, e);
                    }
                });
    }

    /**
     * 修改登录密码
     *
     * @param newPassword 新密码
     * @param oldPassword 旧密码
     */
    public void updateLoginPwd(String newPassword, String oldPassword) {
        UpdateLoginPwdEntity updateLoginPwdEntity = new UpdateLoginPwdEntity();
        updateLoginPwdEntity.setNewPassword(newPassword);
        updateLoginPwdEntity.setOldPassword(oldPassword);

        EasyHttp.post(UcHost.updateLoginPwdUrl)
                .baseUrl(BaseHost.UC_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(updateLoginPwdEntity))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                CommonResult updateLoginPwdResult = BaseApplication.gson.fromJson(s, CommonResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.updateLoginPwd, updateLoginPwdResult.getCode(), BaseApplication.getInstance().getString(R.string.successfully_modified));
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.updateLoginPwd, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.updateLoginPwd, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.updateLoginPwd, e);
                    }
                });
    }

    /**
     * 设置资金密码
     *
     * @param tradePassword
     */
    public void setTradePassword(String tradePassword) {
        TradePasswordSetBean tradePasswordSetBean = new TradePasswordSetBean();
        tradePasswordSetBean.setTradePassword(tradePassword);

        EasyHttp.post(UcHost.setTradePasswordUrl)
                .baseUrl(BaseHost.UC_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(tradePasswordSetBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.setTradePwd, generalResult.getCode(), generalResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.setTradePwd, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.setTradePwd, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.setTradePwd, e);
                    }
                });
    }

    /**
     * 绑定手机
     */
    public void bindPhone(String phone, String pwd, String code) {
        PhoneBindBean phoneBindBean = new PhoneBindBean();
        phoneBindBean.setPhoneNo(phone);
        phoneBindBean.setPassword(pwd);

        EasyHttp.post(UcHost.phoneBindUrl)
                .baseUrl(BaseHost.UC_HOST)
                .headers("check", "phone::" + code)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(phoneBindBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            JSONObject object = new JSONObject(s);
                            int code = object.optInt("code");
                            if (code == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.bindPhone, BaseRequestCode.OK, "");
                            } else {
                                if (code == BaseRequestCode.ERROR_401) {
                                    GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                                    uodateLogin(generalResult);
                                } else {
                                    BaseResponseError responseError = new Gson().fromJson(s, BaseResponseError.class);
                                    EventBusUtils.postErrorEvent(EvKey.bindPhone, responseError.getCode(), responseError.getMessage(), responseError);
                                }
                            }
                        } catch (JSONException e) {
                            LogUtils.e(e.toString());
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        EventBusUtils.postErrorEvent(EvKey.bindPhone, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 绑定手机带请求头
     */
    public void bindPhone(String cid, String check, String phone, String pwd) {
        PhoneBindBean phoneBindBean = new PhoneBindBean();
        phoneBindBean.setPhoneNo(phone);
        phoneBindBean.setPassword(pwd);

        EasyHttp.post(UcHost.phoneBindUrl)
                .baseUrl(BaseHost.UC_HOST)
                .headers("cid", cid)
                .headers("check", check)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(phoneBindBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("phoneCaptcha", s);
                        try {
                            JSONObject object = new JSONObject(s);
                            int code = object.optInt("code");
                            if (code == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.bindPhone, BaseRequestCode.OK, "");
                            } else {
                                if (code == BaseRequestCode.ERROR_401) {
                                    GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                                    uodateLogin(generalResult);
                                } else {
                                    BaseResponseError responseError = new Gson().fromJson(s, BaseResponseError.class);
                                    EventBusUtils.postErrorEvent(EvKey.bindPhone, responseError.getCode(), responseError.getMessage(), responseError);
                                }
                            }
                        } catch (JSONException e) {
                            LogUtils.e(e.toString());
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        LogUtils.e("phoneCaptcha", e.getCode() + "--" + e.getMessage());
                        EventBusUtils.postErrorEvent(EvKey.bindPhone, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 绑定邮箱
     */
    public void bindEmail(String email, String pwd, String code) {
        EmailBindBean emailBindBean = new EmailBindBean();
        emailBindBean.setEmail(email);
        emailBindBean.setPassword(pwd);

        EasyHttp.post(UcHost.emailBindUrl)
                .baseUrl(BaseHost.UC_HOST)
                .headers("check", "email:" + email + ":" + code)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(emailBindBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            JSONObject object = new JSONObject(s);
                            int code = object.optInt("code");
                            if (code == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.bindEmail, BaseRequestCode.OK, "");
                            } else {
                                if (code == BaseRequestCode.ERROR_401) {
                                    GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                                    uodateLogin(generalResult);
                                } else {
                                    BaseResponseError responseError = new Gson().fromJson(s, BaseResponseError.class);
                                    EventBusUtils.postErrorEvent(EvKey.bindEmail, responseError.getCode(), responseError.getMessage(), responseError);
                                }
                            }
                        } catch (JSONException e) {
                            LogUtils.e(e.toString());
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        EventBusUtils.postErrorEvent(EvKey.bindEmail, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 绑定邮箱带请求头
     */
    public void bindEmail(String cid, String check, String email, String pwd) {
        EmailBindBean emailBindBean = new EmailBindBean();
        emailBindBean.setEmail(email);
        emailBindBean.setPassword(pwd);

        EasyHttp.post(UcHost.emailBindUrl)
                .baseUrl(BaseHost.UC_HOST)
                .headers("cid", cid)
                .headers("check", check)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(emailBindBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("phoneCaptcha", s);
                        try {
                            JSONObject object = new JSONObject(s);
                            int code = object.optInt("code");
                            if (code == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.bindEmail, BaseRequestCode.OK, "");
                            } else {
                                if (code == BaseRequestCode.ERROR_401) {
                                    GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                                    uodateLogin(generalResult);
                                } else {
                                    BaseResponseError responseError = new Gson().fromJson(s, BaseResponseError.class);
                                    EventBusUtils.postErrorEvent(EvKey.bindEmail, responseError.getCode(), responseError.getMessage(), responseError);
                                }
                            }
                        } catch (JSONException e) {
                            LogUtils.e(e.toString());
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        LogUtils.e("phoneCaptcha", e.getCode() + "--" + e.getMessage());
                        EventBusUtils.postErrorEvent(EvKey.bindEmail, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 忘记密码-登录密码
     */
    public void forgetLoginPass(int type, String phone, String pwd, String code, String countryCode) {
        RegisterLoginPasswordDto registerLoginPasswordDto = new RegisterLoginPasswordDto();
        registerLoginPasswordDto.setMobilePhone(phone);
        registerLoginPasswordDto.setNewPassword(pwd);

        EasyHttp.post(UcHost.forgetLoginPassUrl)
                .baseUrl(BaseHost.UC_HOST)
                .headers("check", (type == 0 ? "phone:" + countryCode : "email:") + phone + ":" + code)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(registerLoginPasswordDto))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            JSONObject object = new JSONObject(s);
                            int code = object.optInt("code");
                            if (code == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.forgetPass, BaseRequestCode.OK, "");
                            } else {
                                if (code == BaseRequestCode.ERROR_401) {
                                    GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                                    uodateLogin(generalResult);
                                } else {
                                    BaseResponseError responseError = new Gson().fromJson(s, BaseResponseError.class);
                                    EventBusUtils.postErrorEvent(EvKey.forgetPass, responseError.getCode(), responseError.getMessage(), responseError);
                                }
                            }
                        } catch (JSONException e) {
                            LogUtils.e(e.toString());
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        EventBusUtils.postErrorEvent(EvKey.forgetPass, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 忘记密码-资金密码
     */
    public void forgetTradePass(int type, String phone, String pwd, String code) {
        RegisterLoginPasswordDto registerLoginPasswordDto = new RegisterLoginPasswordDto();
        registerLoginPasswordDto.setMobilePhone(phone);
        registerLoginPasswordDto.setNewPassword(pwd);

        EasyHttp.post(UcHost.forgetTradePassUrl)
                .baseUrl(BaseHost.UC_HOST)
                .headers("check", (type == 0 ? "phone::" : "email::") + code)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(registerLoginPasswordDto))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            JSONObject object = new JSONObject(s);
                            int code = object.optInt("code");
                            if (code == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.forgetPass, BaseRequestCode.OK, "");
                            } else {
                                if (code == BaseRequestCode.ERROR_401) {
                                    GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                                    uodateLogin(generalResult);
                                } else {
                                    BaseResponseError responseError = new Gson().fromJson(s, BaseResponseError.class);
                                    EventBusUtils.postErrorEvent(EvKey.forgetPass, responseError.getCode(), responseError.getMessage(), responseError);
                                }
                            }
                        } catch (JSONException e) {
                            LogUtils.e(e.toString());
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        EventBusUtils.postErrorEvent(EvKey.forgetPass, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 修改资金密码
     *
     * @param newPassword 新密码
     * @param oldPassword 旧密码
     */
    public void updateTradePassword(String newPassword, String oldPassword) {
        UpdateLoginPwdEntity updateLoginPwdEntity = new UpdateLoginPwdEntity();
        updateLoginPwdEntity.setNewPassword(newPassword);
        updateLoginPwdEntity.setOldPassword(oldPassword);

        EasyHttp.post(UcHost.updateTradePassUrl)
                .baseUrl(BaseHost.UC_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(updateLoginPwdEntity))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.setTradePwd, generalResult.getCode(), generalResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.setTradePwd, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.setTradePwd, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.setTradePwd, e);
                    }
                });
    }
}
