package com.spark.qtz.app.me.user.register.password;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.geetest.sdk.Bind.GT3GeetestBindListener;
import com.geetest.sdk.Bind.GT3GeetestUtilsBind;
import com.google.gson.Gson;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.api.AppClient;
import com.spark.qtz.api.pojo.PromotionCodeLimitResult;
import com.spark.qtz.app.me.user.register.RegisterActivity;
import com.spark.qtz.app.me.user.register.verificationcode.VerifyCodeActivity;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.CheckErrorUtil;
import com.spark.qtz.util.RegexUtils;
import com.spark.ucclient.CaptchaGetClient;
import com.spark.ucclient.RegisterClient;
import com.spark.ucclient.pojo.Captcha;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import me.spark.mvvm.base.AppManager;
import me.spark.mvvm.base.BaseHost;
import me.spark.mvvm.base.BaseRequestCode;
import me.spark.mvvm.base.BaseResponseError;
import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.binding.command.BindingAction;
import me.spark.mvvm.binding.command.BindingCommand;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.StringUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/28
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class RegisterPwdViewModel extends BaseViewModel {
    private String phoneNum;
    private String strCountry;
    private String mCode;
    private int mType;
    private GT3GeetestUtilsBind gt3GeetestUtils;
    private String cid = "";
    private String checkData = "";
    private Context mContext;

    public ObservableField<String> passWord = new ObservableField<>("");
    public ObservableField<String> passWordAgain = new ObservableField<>("");
    public ObservableField<String> inviteCode = new ObservableField<>("");

    public RegisterPwdViewModel(@NonNull Application application) {
        super(application);
    }

    public void initData(Context context, String phoneNum, String strCountry, String code, int type) {
        this.mContext = context;
        this.phoneNum = phoneNum;
        this.strCountry = strCountry;
        this.mCode = code;
        this.mType = type;
    }

    //注册
    public BindingCommand registerOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            register();
        }
    });

    //用户条款
    public BindingCommand registerClauseOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // TODO: 2019/5/14
        }
    });

    //隐私政策
    public BindingCommand registerRivacyOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // TODO: 2019/5/14
        }
    });

    public void register() {
        if (TextUtils.isEmpty(passWord.get())) {
            Toasty.showError(App.getInstance().getString(R.string.pwd_login_not_null));
            return;
        }

        if (!RegexUtils.isPassword(passWord.get())) {
            Toasty.showError(App.getInstance().getString(R.string.pwd_error));
            return;
        }

        if (TextUtils.isEmpty(passWordAgain.get())) {
            Toasty.showError(App.getInstance().getString(R.string.pwd_login_again_not_null));
            return;
        }

        if (!RegexUtils.isPassword(passWordAgain.get())) {
            Toasty.showError(App.getInstance().getString(R.string.pwd_error));
            return;
        }

        if (!passWord.get().equals(passWordAgain.get())) {
            Toasty.showError(App.getInstance().getString(R.string.pwd_twic_not_same));
            return;
        }

        showDialog(App.getInstance().getString(R.string.loading));
        AppClient.getInstance().checkPromotionLimit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        dismissDialog();
        switch (eventBean.getOrigin()) {
            //是否强制输入邀请码
            case EvKey.promotionCodeLimit:
                if (eventBean.isStatue()) {
                    PromotionCodeLimitResult promotionCodeLimitResult = (PromotionCodeLimitResult) eventBean.getObject();
                    if (promotionCodeLimitResult.getData().getValue().equals("true")) {
                        if (TextUtils.isEmpty(inviteCode.get())) {
                            dismissDialog();
                            Toasty.showError(App.getInstance().getString(R.string.invite_code_hint));
                            return;
                        }
                        AppClient.getInstance().checkPromotionCode(inviteCode.get().trim());
                    } else {
                        if (TextUtils.isEmpty(inviteCode.get())) {
                            registerRequest();
                        } else {
                            AppClient.getInstance().checkPromotionCode(inviteCode.get().trim());
                        }
                    }
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //验证邀请码
            case EvKey.promotionCodeCheck:
                if (eventBean.isStatue()) {
                    registerRequest();
                } else {
                    dismissDialog();
                    Toasty.showError(eventBean.getMessage());
                }
                break;

            case EvKey.registerByPhone:
                dismissDialog();
                if (eventBean.isStatue()) {
                    registerSuccess();
                } else {
                    dealError(eventBean);
                }
                break;
            case EvKey.registerByEmail:
                dismissDialog();
                if (eventBean.isStatue()) {
                    registerSuccess();
                } else {
                    dealError(eventBean);
                }
                break;
            case EvKey.geeCaptcha:
                if (eventBean.isStatue()) {
                    gt3GeetestUtils.gtSetApi1Json((JSONObject) eventBean.getObject());
                    gt3GeetestUtils.getGeetest(mContext, BaseHost.UC_HOST + "captcha/mm/gee", null, null, new GT3GeetestBindListener() {
                        @Override
                        public boolean gt3SetIsCustom() {
                            return true;
                        }

                        @Override
                        public void gt3GetDialogResult(boolean status, String result) {
                            if (status) {
                                Captcha captcha = new Gson().fromJson(result, Captcha.class);
                                checkData = "gee::" + captcha.getGeetest_challenge() + "$" + captcha.getGeetest_validate() + "$" + captcha.getGeetest_seccode();
                                register();
                            }
                        }
                    });
                    gt3GeetestUtils.setDialogTouch(true);
                } else {
                    CheckErrorUtil.checkError(eventBean);
                }
                break;
            default:
                break;
        }
    }

    private void registerSuccess() {
        Toasty.showSuccess(App.getInstance().getString(R.string.register_success));
        AppManager.getAppManager().finishActivity(RegisterActivity.class);
        AppManager.getAppManager().finishActivity(VerifyCodeActivity.class);
        ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_LOGIN)
                .navigation();
        finish();
    }

    private void dealError(EventBean eventBean) {
        if (gt3GeetestUtils != null) {
            gt3GeetestUtils.gt3TestClose();
            gt3GeetestUtils = null;
        }

        BaseResponseError responseError = (BaseResponseError) eventBean.getObject();
        if (responseError != null) {
            String msg = responseError.getMessage();
            if (responseError.getCode() == BaseRequestCode.ERROR_411 && StringUtils.isNotEmpty(msg) && msg.contains("captcha")) {
                //极验验证 411
                cid = responseError.getCid();
                gt3GeetestUtils = new GT3GeetestUtilsBind(mContext);
                CaptchaGetClient.getInstance().geeCaptcha();
            } else if (responseError.getCode() == BaseRequestCode.ERROR_412 && StringUtils.isNotEmpty(msg) && msg.contains("Captcha")) {
                //验证码错误
                Toasty.showError(App.getInstance().getString(R.string.sms_code_error));
                finish();
            } else {
                CheckErrorUtil.checkError(eventBean);
            }
        }
    }


    private void registerRequest() {
        switch (mType) {
            case 0:
                if (StringUtils.isNotEmpty(cid, checkData)) {
                    RegisterClient.getInstance().registerByPhone(cid, checkData, "", passWord.get(), strCountry, inviteCode.get(), phoneNum);
                } else {
                    RegisterClient.getInstance().registerByPhone("", mCode, passWord.get(), strCountry, inviteCode.get(), phoneNum);
                }
                break;
            case 1:
                if (StringUtils.isNotEmpty(cid, checkData)) {
                    RegisterClient.getInstance().registerByEmail(cid, checkData, "", passWord.get(), strCountry, inviteCode.get(), phoneNum);
                } else {
                    RegisterClient.getInstance().registerByEmail("", mCode, passWord.get(), strCountry, inviteCode.get(), phoneNum);
                }
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
