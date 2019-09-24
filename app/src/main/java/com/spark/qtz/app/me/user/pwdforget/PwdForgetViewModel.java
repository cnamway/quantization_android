package com.spark.qtz.app.me.user.pwdforget;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.geetest.sdk.Bind.GT3GeetestBindListener;
import com.geetest.sdk.Bind.GT3GeetestUtilsBind;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.CheckErrorUtil;
import com.spark.qtz.util.RegexUtils;
import com.spark.ucclient.CaptchaGetClient;
import com.spark.ucclient.RegisterClient;
import com.spark.ucclient.SecurityClient;
import com.spark.ucclient.pojo.Captcha;
import com.spark.ucclient.pojo.CountryEntity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.List;

import me.spark.mvvm.base.BaseHost;
import me.spark.mvvm.base.BaseRequestCode;
import me.spark.mvvm.base.BaseResponseError;
import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.binding.command.BindingAction;
import me.spark.mvvm.binding.command.BindingCommand;
import me.spark.mvvm.bus.event.SingleLiveEvent;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.StringUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/20
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class PwdForgetViewModel extends BaseViewModel {
    public PwdForgetViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<String> phoneNum = new ObservableField<>("");
    public ObservableField<String> verifyCode = new ObservableField<>("");
    public ObservableField<String> newPwd = new ObservableField<>("");
    public ObservableField<String> newPwdAgain = new ObservableField<>("");
    public ObservableField<String> countryCode = new ObservableField<>("");
    public ObservableField<String> countryName = new ObservableField<>("");
    private GT3GeetestUtilsBind gt3GeetestUtils;
    private String cid;
    private String[] mCountryArray;
    private Context mContext;
    private List<CountryEntity> mCountryEntityList;
    private String countryEnName;                      //值传递 国籍 enName
    public int type = 0;                                //0 - 手机注册 1 - 邮箱注册
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<Boolean> mGetCodeSuccessLiveEvent = new SingleLiveEvent<>();
    }

    public void initContext(Context context) {
        this.mContext = context;
    }

    //国籍选择
    public BindingCommand chooseCountryOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            loadCountryInfo();
        }
    });

    public BindingCommand pwdResetOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            pwdReset();
        }
    });

    private void pwdReset() {
        if (StringUtils.isEmpty(phoneNum.get())) {
            return;
        }

        if (StringUtils.isEmpty(verifyCode.get())) {
            Toasty.showError(App.getInstance().getString(R.string.verify_code_hint));
            return;
        }

        if (StringUtils.isEmpty(newPwd.get())) {
            Toasty.showError(App.getInstance().getString(R.string.new_pwd_hint));
            return;
        }

        if (!RegexUtils.isPassword(newPwd.get())) {
            Toasty.showError(App.getInstance().getString(R.string.pwd_error));
            return;
        }

        if (StringUtils.isEmpty(newPwdAgain.get())) {
            Toasty.showError(App.getInstance().getString(R.string.new_pwd_ensure_hint));
            return;
        }

        if (!newPwd.get().equals(newPwdAgain.get())) {
            Toasty.showError(App.getInstance().getString(R.string.pwd_inconsistent));
            return;
        }

        switch (type) {
            //手机注册
            case 0:
                SecurityClient.getInstance().forgetLoginPass(type, phoneNum.get(), newPwd.get(), verifyCode.get(), countryCode.get());
                break;
            //邮箱注册
            case 1:
                SecurityClient.getInstance().forgetLoginPass(type, phoneNum.get(), newPwd.get(), verifyCode.get(), "");
                break;
        }

    }

    //0 - 手机注册 1 - 邮箱注册
    public void setType(int type) {
        this.type = type;
    }

    //获取短信验证码
    public void getPhoneCode() {
        switch (type) {
            //手机注册
            case 0:
                if (StringUtils.isEmpty(countryCode.get() + phoneNum.get())) {
                    Toasty.showError(App.getInstance().getString(R.string.valid_phone));
                    return;
                }
                showDialog(App.getInstance().getString(R.string.loading));
                CaptchaGetClient.getInstance().phoneCaptcha2(countryCode.get() + phoneNum.get());
                break;
            //邮箱注册
            case 1:
                if (!RegexUtils.isEmail(phoneNum.get())) {
                    Toasty.showError(App.getInstance().getString(R.string.valid_email));
                    return;
                }
                showDialog(App.getInstance().getString(R.string.loading));
                CaptchaGetClient.getInstance().emailCaptcha(phoneNum.get());
                break;
        }
    }

    //获取国籍列表
    private void loadCountryInfo() {
        //避免重复请求
        if (mCountryArray != null) {
            new XPopup.Builder(mContext)
                    .asBottomList(mContext.getString(R.string.choose_country), mCountryArray,
                            new OnSelectListener() {
                                @Override
                                public void onSelect(int position, String text) {
                                    countryEnName = mCountryEntityList.get(position).getEnName();
                                    updateCountryInfo(mCountryEntityList.get(position).getZhName() + "(" + mCountryEntityList.get(position).getEnName() + ")", mCountryEntityList.get(position).getAreaCode());
                                }
                            })
                    .show();
        } else {
            showDialog(mContext.getString(R.string.loading));
            RegisterClient.getInstance().findSupportCountry();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            //获取国籍列表
            case EvKey.findSupportCountry:
                dismissDialog();
                if (eventBean.isStatue()) {
                    final List<CountryEntity> objList = (List<CountryEntity>) eventBean.getObject();
                    if (!objList.isEmpty()) {
                        mCountryEntityList = objList;
                        mCountryArray = new String[objList.size()];
                        for (int i = 0; i < objList.size(); i++) {
                            mCountryArray[i] = objList.get(i).getZhName();
                        }
                        if (mCountryArray.length > 0) {
                            new XPopup.Builder(mContext)
                                    .asBottomList(mContext.getString(R.string.choose_country), mCountryArray,
                                            new OnSelectListener() {
                                                @Override
                                                public void onSelect(int position, String text) {
                                                    countryEnName = objList.get(position).getEnName();
                                                    updateCountryInfo(objList.get(position).getZhName() + "(" + objList.get(position).getEnName() + ")", objList.get(position).getAreaCode());
                                                }
                                            })
                                    .show();
                        }
                    } else {
                        Toasty.showInfo(mContext.getString(R.string.country_list_null));
                    }
                } else {
                    CheckErrorUtil.checkError(eventBean);
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
                            gt3GeetestUtils = new GT3GeetestUtilsBind(mContext);
                            CaptchaGetClient.getInstance().geeCaptcha();
                        } else if (responseError.getCode() == BaseRequestCode.ERROR_412 && StringUtils.isNotEmpty(msg) && msg.contains("Captcha")) {
                            //解决验证码失效问题
                            cid = responseError.getCid();
                            gt3GeetestUtils = new GT3GeetestUtilsBind(mContext);
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
                    gt3GeetestUtils.getGeetest(mContext, BaseHost.UC_HOST + "captcha/mm/gee", null, null, new GT3GeetestBindListener() {
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
                                        CaptchaGetClient.getInstance().phoneCaptchaWithHeader2(countryCode.get() + phoneNum.get(), checkData, cid);
                                        break;
                                    case 1:
                                        CaptchaGetClient.getInstance().emailCaptchaWithHeader(phoneNum.get(), checkData, cid);
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
            case EvKey.forgetPass:
                if (eventBean.isStatue()) {
                    Toasty.showSuccess(App.getInstance().getString(R.string.set_success));
                    finish();
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            default:
                break;
        }
    }


    /**
     * 更新国籍展示
     *
     * @param strCountry
     * @param strAreaCode
     */
    public void updateCountryInfo(String strCountry, String strAreaCode) {
        countryName.set(strCountry);
        if (type == 0) countryCode.set(strAreaCode);
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
