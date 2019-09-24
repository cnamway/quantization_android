package com.spark.qtz.app.me.safe.safecentre.google.step2;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.geetest.sdk.Bind.GT3GeetestBindListener;
import com.geetest.sdk.Bind.GT3GeetestUtilsBind;
import com.google.gson.Gson;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.AppUtils;
import com.spark.qtz.util.CheckErrorUtil;
import com.spark.ucclient.CaptchaGetClient;
import com.spark.ucclient.GoogleClient;
import com.spark.ucclient.pojo.Captcha;
import com.spark.ucclient.pojo.GoogleAuthSendResult;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import me.spark.mvvm.base.BaseHost;
import me.spark.mvvm.base.BaseRequestCode;
import me.spark.mvvm.base.BaseResponseError;
import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.binding.command.BindingAction;
import me.spark.mvvm.binding.command.BindingCommand;
import me.spark.mvvm.bus.event.SingleLiveEvent;
import me.spark.mvvm.pojo.User;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.StringUtils;
import me.spark.mvvm.utils.Utils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/29
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class GoogleBindViewModel extends BaseViewModel {
    public GoogleBindViewModel(@NonNull Application application) {
        super(application);
    }

    //google秘钥
    public ObservableField<String> googleKey = new ObservableField<>("");
    //谷歌验证码
    public ObservableField<String> googleCode = new ObservableField<>("");
    //短信验证码
    public ObservableField<String> smsCode = new ObservableField<>("");
    //手机号码/游戏
    public ObservableField<String> phoneNum = new ObservableField<>("");

    private GT3GeetestUtilsBind gt3GeetestUtils;
    private String cid;
    private User mUser;
    public int type = 0; //0 - 手机注册 1 - 邮箱注册
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<Boolean> mGetCodeSuccessLiveEvent = new SingleLiveEvent<>();
    }

    public void loadDate() {
        if (!App.getInstance().isAppLogin()) {
            googleKey.set("");
            return;
        }
        GoogleClient.getInstance().getGoogleAuthSend();
        mUser = App.getInstance().getCurrentUser();
        if (mUser != null) {
            if (StringUtils.isNotEmpty(mUser.getMobilePhone())) {
                type = 0;
                phoneNum.set(App.getInstance().getString(R.string.input_your_phone) + mUser.getMobilePhone() + App.getInstance().getString(R.string.code_get));
            } else {
                type = 1;
                phoneNum.set(App.getInstance().getString(R.string.input_your_email) + mUser.getEmail() + App.getInstance().getString(R.string.code_get));
            }
        }
    }

    //复制
    public BindingCommand copyOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            AppUtils.copy2Clipboard(Utils.getContext(), googleKey.get());
        }
    });

    //获取短信验证码
    public BindingCommand getCodeOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            showDialog(App.getInstance().getString(R.string.loading));
            CaptchaGetClient.getInstance().sendVertifyCode(type);
        }
    });

    //确认开启
    public BindingCommand submitOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (StringUtils.isEmpty(googleKey.get())) {
                Toasty.showError(App.getInstance().getString(R.string.str_google_key));
                return;
            }
            if (StringUtils.isEmpty(googleCode.get())) {
                Toasty.showError(App.getInstance().getString(R.string.google_code_hint));
                return;
            }
            if (StringUtils.isEmpty(smsCode.get())) {
                Toasty.showError(App.getInstance().getString(R.string.str_enter_sms_code));
                return;
            }
            switch (type) {
                case 0:
                    GoogleClient.getInstance().googleAuthBind(type, googleKey.get(), googleCode.get(), mUser.getMobilePhone(), smsCode.get(), mUser.getAreaCode());
                    break;
                case 1:
                    GoogleClient.getInstance().googleAuthBind(type, googleKey.get(), googleCode.get(), mUser.getEmail(), smsCode.get(), "");
                    break;
            }

        }
    });

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            case EvKey.googleAuthSend:
                if (eventBean.isStatue()) {
                    GoogleAuthSendResult inviteFriendResult = (GoogleAuthSendResult) eventBean.getObject();
                    googleKey.set(inviteFriendResult.getData().getSecret() + "");
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //手机验证
            case EvKey.phoneCaptcha:
                dismissDialog();
                if (eventBean.isStatue()) {
                    if (gt3GeetestUtils != null) {
                        gt3GeetestUtils.gt3TestFinish();
                        gt3GeetestUtils = null;
                    }
                    uc.mGetCodeSuccessLiveEvent.setValue(true);
                } else {
                    if (gt3GeetestUtils != null) {
                        gt3GeetestUtils.gt3TestClose();
                        gt3GeetestUtils = null;
                    }

                    BaseResponseError responseError = (BaseResponseError) eventBean.getObject();
                    if (responseError != null) {
                        String msg = responseError.getMessage();
                        if (responseError.getCode() == BaseRequestCode.ERROR_411 && StringUtils.isNotEmpty(msg) && msg.contains("captcha")) {
                            cid = responseError.getCid();
                            gt3GeetestUtils = new GT3GeetestUtilsBind(App.getInstance());
                            CaptchaGetClient.getInstance().geeCaptcha();
                        } else if (responseError.getCode() == BaseRequestCode.ERROR_412 && StringUtils.isNotEmpty(msg) && msg.contains("Captcha")) {
                            //解决验证码失效问题
                            cid = responseError.getCid();
                            gt3GeetestUtils = new GT3GeetestUtilsBind(App.getInstance());
                            CaptchaGetClient.getInstance().geeCaptcha();
                        } else {
                            CheckErrorUtil.checkError(eventBean);
                        }
                    }
                }
                break;
            //邮箱验证
            case EvKey.emailCaptcha:
                dismissDialog();
                if (eventBean.isStatue()) {
                    if (gt3GeetestUtils != null) {
                        gt3GeetestUtils.gt3TestFinish();
                        gt3GeetestUtils = null;
                    }
                    uc.mGetCodeSuccessLiveEvent.setValue(true);
                } else {
                    if (gt3GeetestUtils != null) {
                        gt3GeetestUtils.gt3TestClose();
                        gt3GeetestUtils = null;
                    }

                    BaseResponseError responseError = (BaseResponseError) eventBean.getObject();
                    if (responseError != null) {
                        String msg = responseError.getMessage();
                        if (responseError.getCode() == BaseRequestCode.ERROR_411 && StringUtils.isNotEmpty(msg) && msg.contains("captcha")) {
                            cid = responseError.getCid();
                            gt3GeetestUtils = new GT3GeetestUtilsBind(App.getInstance());
                            CaptchaGetClient.getInstance().geeCaptcha();
                        } else if (responseError.getCode() == BaseRequestCode.ERROR_412 && StringUtils.isNotEmpty(msg) && msg.contains("Captcha")) {
                            //解决验证码失效问题
                            cid = responseError.getCid();
                            gt3GeetestUtils = new GT3GeetestUtilsBind(App.getInstance());
                            CaptchaGetClient.getInstance().geeCaptcha();
                        } else {
                            CheckErrorUtil.checkError(eventBean);
                        }
                    }
                }
                break;
            //极验验证
            case EvKey.geeCaptcha:
                if (eventBean.isStatue()) {
                    gt3GeetestUtils.gtSetApi1Json((JSONObject) eventBean.getObject());
                    gt3GeetestUtils.getGeetest(App.getInstance(), BaseHost.UC_HOST + "captcha/mm/gee", null, null, new GT3GeetestBindListener() {
                        @Override
                        public boolean gt3SetIsCustom() {
                            return true;
                        }

                        @Override
                        public void gt3GetDialogResult(boolean status, String result) {
                            if (status) {
                                Captcha captcha = new Gson().fromJson(result, Captcha.class);
                                String checkData = "gee::" + captcha.getGeetest_challenge() + "$" + captcha.getGeetest_validate() + "$" + captcha.getGeetest_seccode();
                                switch (type) {
                                    case 0:
                                        CaptchaGetClient.getInstance().phoneCaptchaWithHeader(mUser.getAreaCode() + mUser.getMobilePhone(), checkData, cid);
                                        break;
                                    case 1:
                                        CaptchaGetClient.getInstance().emailCaptchaWithHeader(mUser.getEmail(), checkData, cid);
                                        break;
                                }
                            }
                        }
                    });
                    gt3GeetestUtils.setDialogTouch(true);
                } else {
                    CheckErrorUtil.checkError(eventBean);
                }
                break;
            case EvKey.loginStatue:
                loadDate();
                break;
            //绑定Google认证
            case EvKey.googleAuthBind:
                if (eventBean.isStatue()) {
                    mUser.setGoogleAuthStatus(1);
                    App.getInstance().setCurrentUser(mUser);
                    Toasty.showSuccess(App.getInstance().getString(R.string.success));
                    finish();
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            case EvKey.sendVertifyCode:
                dismissDialog();
                if (eventBean.isStatue()) {
                    if (gt3GeetestUtils != null) {
                        gt3GeetestUtils.gt3TestFinish();
                        gt3GeetestUtils = null;
                    }
                    uc.mGetCodeSuccessLiveEvent.setValue(true);
                } else {
                    BaseResponseError responseError = (BaseResponseError) eventBean.getObject();
                    if (responseError != null) {
                        String msg = responseError.getMessage();
                        if (responseError.getCode() == BaseRequestCode.ERROR_411 && StringUtils.isNotEmpty(msg) && msg.contains("captcha")) {
                            cid = responseError.getCid();
                            gt3GeetestUtils = new GT3GeetestUtilsBind(App.getInstance());
                            CaptchaGetClient.getInstance().geeCaptcha();
                        } else if (responseError.getCode() == BaseRequestCode.ERROR_412 && StringUtils.isNotEmpty(msg) && msg.contains("Captcha")) {
                            //解决验证码失效问题
                            cid = responseError.getCid();
                            gt3GeetestUtils = new GT3GeetestUtilsBind(App.getInstance());
                            CaptchaGetClient.getInstance().geeCaptcha();
                        } else {
                            CheckErrorUtil.checkError(eventBean);
                        }
                    }
                }
                break;
            case EvKey.logout_success_401:
                if (eventBean.isStatue()) {
                    loadDate();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBusUtils.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtils.unRegister(this);
    }
}
