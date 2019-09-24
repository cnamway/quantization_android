package com.spark.qtz.app.me.user.login;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.geetest.sdk.Bind.GT3GeetestBindListener;
import com.geetest.sdk.Bind.GT3GeetestUtilsBind;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.spark.casclient.CasClient;
import com.spark.casclient.base.CasHost;
import com.spark.casclient.pojo.LoginBean;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.ui.popup.GoogleVerifyPopup;
import com.spark.qtz.ui.popup.SmsVerifyPopup;
import com.spark.qtz.ui.popup.impl.OnEtContentListener;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.CheckErrorUtil;
import com.spark.modulespot.SpotCoinClient;
import com.spark.modulespot.pojo.FavorFindResult;
import com.spark.quantizationclient.TradeUserClient;
import com.spark.ucclient.MemberClient;
import com.spark.ucclient.pojo.Captcha;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.base.BaseHost;
import me.spark.mvvm.base.BaseRequestCode;
import me.spark.mvvm.base.BaseResponseError;
import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.binding.command.BindingAction;
import me.spark.mvvm.binding.command.BindingCommand;
import me.spark.mvvm.bus.event.SingleLiveEvent;
import me.spark.mvvm.pojo.User;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.SPUtils;
import me.spark.mvvm.utils.ServiceOpenSuccessEvent;
import me.spark.mvvm.utils.StringUtils;
import okhttp3.Cookie;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/27
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class LoginViewModel extends BaseViewModel {
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    //账号
    public ObservableField<String> userName = new ObservableField<>("");
    //密码
    public ObservableField<String> userPassWord = new ObservableField<>("");
    //验证码
    public ObservableField<String> verifyCode = new ObservableField<>("");
    public int type = 0;                                //0 - 手机注册 1 - 邮箱注册

    private boolean isCasLogin = false;
    private String cid;
    private GT3GeetestUtilsBind gt3GeetestUtils;
    private Context mContext;
    private SmsVerifyPopup mSmsVerifyPopup;
    private GoogleVerifyPopup mGoogleVerifyPopup;
    private int loginType;                  //0 - 手机登录 1 - 邮箱登录

    public void initContext(Context context) {
        this.mContext = context;
        userName.set("ccs");
        userPassWord.set("123456");
        initSmsVerifyPopup();
        initGoogleVerifyPopup();
    }

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<Boolean> pwdSwitchEvent = new SingleLiveEvent<>();
        public SingleLiveEvent<Boolean> smsCodePopopShow = new SingleLiveEvent<>();
        public SingleLiveEvent<Boolean> mGetCodeSuccessLiveEvent = new SingleLiveEvent<>();
    }

    //密码显示开关
    public BindingCommand pwdSwitchOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.pwdSwitchEvent.setValue(uc.pwdSwitchEvent.getValue() == null || !uc.pwdSwitchEvent.getValue());
        }
    });

    //登录
    public BindingCommand loginOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            login();
        }
    });
    //忘记密码
    public BindingCommand pwdForGetOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_FORGET_PASS)
                    .navigation();
        }
    });

    //0 - 手机注册 1 - 邮箱注册
    public void setType(int type) {
        this.type = type;
    }

    //获取短信验证码
    public void getPhoneCode() {
        switch (type) {
            //手机注册
            case 0:
//                if (StringUtils.isEmpty(countryCode.get() + phoneNum.get())) {
//                    Toasty.showError(App.getInstance().getString(R.string.valid_phone));
//                    return;
//                }
//                showDialog(App.getInstance().getString(R.string.loading));
//                CaptchaGetClient.getInstance().phoneCaptcha2(countryCode.get() + phoneNum.get());
                break;
            //邮箱注册
            case 1:
//                if (!RegexUtils.isEmail(phoneNum.get())) {
//                    Toasty.showError(App.getInstance().getString(R.string.valid_email));
//                    return;
//                }
//                showDialog(App.getInstance().getString(R.string.loading));
//                CaptchaGetClient.getInstance().emailCaptcha(phoneNum.get());
                break;
        }
    }

    /**
     * 登录
     */
    public void login() {
        if (TextUtils.isEmpty(userName.get())) {
            Toasty.showError(mContext.getString(R.string.count_not_null));
            return;
        }
        if (TextUtils.isEmpty(userPassWord.get())) {
            Toasty.showError(mContext.getString(R.string.pwd_not_null));
            return;
        }

        App.getInstance().deleteCurrentUser();
        showDialog(mContext.getString(R.string.loading));
//        if (RegexUtils.isEmail(userName.get().trim())) {
//            loginType = 1;
//
//        } else {
//            loginType = 0;
//            CasClient.getInstance().login(userName.get(), userPassWord.get(), "true");
//        }
        CasClient.getInstance().login(userName.get(), userPassWord.get(), "true");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            case EvKey.login://cas登录成功
                dismissDialog();
                if (eventBean.isStatue()) {
                    isCasLogin = true;
                    if (eventBean.getMessage().equals("1")) {
                        LoginBean.NoticeDataBean noticeData = (LoginBean.NoticeDataBean) eventBean.getObject();
                        if (StringUtils.isNotEmpty(noticeData.getGoogle())) {
                            showGoogleVertifyDialog();
                        } else {
                            CasClient.getInstance().sendVertifyCode(loginType);
                        }
                    } else {
                        /*isCasLogin = false;
                        User user = new User();
                        App.getInstance().setCurrentUser(user);
                        Toasty.showSuccess(mContext.getString(R.string.login_success));
                        finish();
                        ARouter.getInstance().build(ARouterPath.ACTIVITY_URL_INDEX)
                                .navigation();*/
                        //登录QUANTIZATION模块
                        CasClient.getInstance().getServiceTicket(SPUtils.getInstance().getTgc(), BaseHost.TYPE_QUANTIZATION);
                    }
                } else {
                    dealError(eventBean);
                }
                break;
            case EvKey.getServiceTicket:
                dismissDialog();
                if (!eventBean.isStatue()) {
                    dealError(eventBean);
                }
                break;
            case EvKey.loginApp:
                dismissDialog();
                if (eventBean.isStatue()) {
//                    saveOtcSid();
                    isCasLogin = false;
                    //获取用户信息
                    TradeUserClient.getInstance().userInfo();
                } else {
                    dealError(eventBean);
                }
                break;
            case EvKey.geeCaptcha:
                dismissDialog();
                if (eventBean.isStatue()) {
                    gt3GeetestUtils.gtSetApi1Json((JSONObject) eventBean.getObject());
                    gt3GeetestUtils.getGeetest(mContext, BaseHost.LOGIN_HOST + CasHost.vertifyCodeUrl, null, null, new GT3GeetestBindListener() {
                        @Override
                        public boolean gt3SetIsCustom() {
                            return true;
                        }

                        @Override
                        public void gt3GetDialogResult(boolean status, String result) {
                            if (status) {
                                Captcha captcha = new Gson().fromJson(result, Captcha.class);
                                String checkData = "gee::" + captcha.getGeetest_challenge() + "$" + captcha.getGeetest_validate() + "$" + captcha.getGeetest_seccode();
                                CasClient.getInstance().checkGeeCaptcha(checkData, cid);
                            }
                        }
                    });
                    gt3GeetestUtils.setDialogTouch(true);
                } else {
                    CheckErrorUtil.checkError(eventBean);
                }
                break;
            case EvKey.checkCaptcha:
                if (eventBean.isStatue()) {
                    if (gt3GeetestUtils != null) {
                        gt3GeetestUtils.gt3TestFinish();
                        gt3GeetestUtils = null;
                    }

                    showDialog(mContext.getString(R.string.loading));
                    if (isCasLogin) {
                        CasClient.getInstance().getServiceTicket(SPUtils.getInstance().getTgc(), BaseHost.TYPE_UC);
                    } else {
                        login();
                    }
                } else {
                    CheckErrorUtil.checkError(eventBean);
                }
                break;
            case EvKey.sendVertifyCode:
                dismissDialog();
                if (eventBean.isStatue()) {
                    if (gt3GeetestUtils != null) {
                        gt3GeetestUtils.gt3TestFinish();
                        gt3GeetestUtils = null;
                    }
                    showPhoneVertifyDialog();
                } else {
                    if (eventBean.getCode() == BaseRequestCode.ERROR_500) {
                        Toasty.showError(mContext.getString(R.string.sms_get_error));
                    } else {
                        CheckErrorUtil.checkError(eventBean);
                    }
                }
                break;
            case EvKey.phoneCodeCheck:
                if (eventBean.isStatue()) {
                    if (isCasLogin) {
                        CasClient.getInstance().getServiceTicket(SPUtils.getInstance().getTgc(), BaseHost.TYPE_UC);
                    } else {
                        login();
                    }
                } else {
                    dismissDialog();
                    CheckErrorUtil.checkError(eventBean);
                }
                break;
            case EvKey.userInfo:
                if (eventBean.isStatue()) {
                    User user = (User) eventBean.getObject();
                    user.setLogintype(loginType);
                    if (user != null) {
                        App.getInstance().setCurrentUser(user);
                    }
                    //SpotCoinClient.getInstance().getFavorFind();
                    for (Cookie cookie : App.getInstance().getCookieManger().getCookieStore().getCookies()) {
                        LogUtils.e("cookie", cookie.name() + "-- " + cookie.value());
                        switch (cookie.name()) {
                            case "acsid":
                                SPUtils.getInstance().setAcCookie(cookie.value());
                                break;
                            case "ucsid":
                                SPUtils.getInstance().setUcCookie(cookie.value());
                                break;
                            case "cfdsid":
                                SPUtils.getInstance().setCfdCookie(cookie.value());
                                break;
                            case "otcsid":
                                SPUtils.getInstance().setOtcCookie(cookie.value());
                                break;
                            case "spotsid":
                                SPUtils.getInstance().setSpotCookie(cookie.value());
                                break;
                            case "quantizationsid":
                                SPUtils.getInstance().setQuantizationCookie(cookie.value());
                                break;
                        }
                    }
                    //EventBusUtils.postSuccessEvent(EvKey.loginStatue, BaseRequestCode.OK, "");
                    //登录成功后，聊天WebSocket连接成功后发送订阅聊天通知
                    //EventBusUtils.postEvent(new ServiceOpenSuccessEvent());
                    Toasty.showSuccess(mContext.getString(R.string.login_success));
                    finish();
                    ARouter.getInstance().build(ARouterPath.ACTIVITY_URL_INDEX)
                            .navigation();
                } else {
                    dealError(eventBean);
                }
                break;
            //获取全部收藏币种
            case EvKey.favorFind:
                dismissDialog();
                if (eventBean.isStatue()) {
                    FavorFindResult favorFindResult = (FavorFindResult) eventBean.getObject();
                    List<String> favorFindList = new ArrayList<>();
                    for (FavorFindResult.DataBean dataBean : favorFindResult.getData()) {
                        favorFindList.add(dataBean.getSymbol());
                    }
                    SPUtils.getInstance().setFavorFindList(favorFindList);
                }

                for (Cookie cookie : App.getInstance().getCookieManger().getCookieStore().getCookies()) {
                    LogUtils.e("cookie", cookie.name() + "-- " + cookie.value());
                    switch (cookie.name()) {
                        case "acsid":
                            SPUtils.getInstance().setAcCookie(cookie.value());
                            break;
                        case "ucsid":
                            SPUtils.getInstance().setUcCookie(cookie.value());
                            break;
                        case "cfdsid":
                            SPUtils.getInstance().setCfdCookie(cookie.value());
                            break;
                        case "otcsid":
                            SPUtils.getInstance().setOtcCookie(cookie.value());
                            break;
                        case "spotsid":
                            SPUtils.getInstance().setSpotCookie(cookie.value());
                            break;
                    }
                }
                EventBusUtils.postSuccessEvent(EvKey.loginStatue, BaseRequestCode.OK, "");
                //登录成功后，聊天WebSocket连接成功后发送订阅聊天通知
                EventBusUtils.postEvent(new ServiceOpenSuccessEvent());
                Toasty.showSuccess(mContext.getString(R.string.login_success));
                finish();
                ARouter.getInstance().build(ARouterPath.ACTIVITY_URL_INDEX)
                        .navigation();
                break;
            default:
                break;
        }
    }

    private void dealError(EventBean eventBean) {
        int code = eventBean.getCode();
        if (code == BaseRequestCode.ERROR_401 && !isCasLogin) {
            dismissDialog();
            Toasty.showError(mContext.getString(R.string.usernameorpwderror));
        } else if (code == BaseRequestCode.ERROR_411) {
            BaseResponseError responseError = (BaseResponseError) eventBean.getObject();
            if (responseError != null) {
                String data = responseError.getData();
                cid = responseError.getCid();
                if (StringUtils.isNotEmpty(data) && "phone".equals(data)) {
                    dismissDialog();
                    CasClient.getInstance().sendVertifyCode(loginType);
                } else if (StringUtils.isNotEmpty(data) && "gee".equals(data)) {
                    LogUtils.e("sss", "geeeeee");
                    gt3GeetestUtils = new GT3GeetestUtilsBind(mContext);
                    CasClient.getInstance().geeCaptcha();
                } else if (StringUtils.isNotEmpty(responseError.getMessage())) {
                    dismissDialog();
                    Toasty.showError(responseError.getMessage());
                }
            }
        } else {
            CheckErrorUtil.checkError(eventBean);
        }
    }

    private void initSmsVerifyPopup() {
        if (mSmsVerifyPopup == null) {
            mSmsVerifyPopup = new SmsVerifyPopup(mContext, loginType, true, userName.get(), new OnEtContentListener() {
                @Override
                public void onCEtContentInput(String content) {
                    showDialog(mContext.getString(R.string.loading));
                    CasClient.getInstance().phoneCodeCheck(loginType, content);
                }
            });
        }
    }

    private void initGoogleVerifyPopup() {
        if (mGoogleVerifyPopup == null) {
            mGoogleVerifyPopup = new GoogleVerifyPopup(mContext, new OnEtContentListener() {
                @Override
                public void onCEtContentInput(String content) {
                    showDialog(mContext.getString(R.string.loading));
                    CasClient.getInstance().phoneCodeCheck(2, content);
                }
            });
        }
    }

    /**
     * 展示发送短信验证码弹框
     */
    private void showPhoneVertifyDialog() {
        if (mSmsVerifyPopup.isShow()) {
            return;
        }
        mSmsVerifyPopup.updatePhone(loginType == 0 ? userName.get() : userName.get());
        mSmsVerifyPopup.updateLoginType(loginType);
        new XPopup.Builder(mContext)
                .setPopupCallback(new XPopupCallback() {
                    @Override
                    public void onShow() {
                        uc.smsCodePopopShow.setValue(true);
                    }

                    @Override
                    public void onDismiss() {
                        uc.smsCodePopopShow.setValue(false);
                    }

                })
                .asCustom(mSmsVerifyPopup).show();
    }

    /**
     * 展示谷歌验证码弹框
     */
    private void showGoogleVertifyDialog() {
        if (mGoogleVerifyPopup.isShow()) {
            return;
        }
        new XPopup.Builder(mContext)
                .setPopupCallback(new XPopupCallback() {
                    @Override
                    public void onShow() {
                        uc.smsCodePopopShow.setValue(true);
                    }

                    @Override
                    public void onDismiss() {
                        uc.smsCodePopopShow.setValue(false);
                    }

                })
                .asCustom(mGoogleVerifyPopup).show();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Constant.isLoginVisiable = true;
        EventBusUtils.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Constant.isLoginVisiable = false;
        EventBusUtils.unRegister(this);
    }

}
