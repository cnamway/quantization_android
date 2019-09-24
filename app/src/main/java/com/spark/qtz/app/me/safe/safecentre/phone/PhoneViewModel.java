package com.spark.qtz.app.me.safe.safecentre.phone;

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
import com.spark.ucclient.MemberClient;
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
import me.spark.mvvm.pojo.User;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.StringUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/29
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class PhoneViewModel extends BaseViewModel {
    public PhoneViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<String> phoneNum = new ObservableField<>("");
    public ObservableField<String> countryCode = new ObservableField<>("");
    public ObservableField<String> countryName = new ObservableField<>("");
    public ObservableField<String> code = new ObservableField<>("");
    public ObservableField<String> pwd = new ObservableField<>("");
    private GT3GeetestUtilsBind gt3GeetestUtils;
    private String cid = "";
    private String checkData = "";
    private String[] mCountryArray;
    private List<CountryEntity> mCountryEntityList;
    private Context mContext;

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

    public BindingCommand ensureClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (StringUtils.isEmpty(phoneNum.get())) {
                Toasty.showError(App.getInstance().getString(R.string.phone_num_hint));
                return;
            }
            if (!RegexUtils.isMobileExact(phoneNum.get())) {
                Toasty.showError(App.getInstance().getString(R.string.valid_phone));
                return;
            }

            if (StringUtils.isEmpty(countryCode.get()) || StringUtils.isEmpty(countryName.get())) {
                Toasty.showError(mContext.getString(R.string.choose_country));
                return;
            }

            if (StringUtils.isEmpty(code.get())) {
                Toasty.showError(App.getInstance().getString(R.string.verify_code_hint));
                return;
            }

            if (StringUtils.isEmpty(pwd.get())) {
                Toasty.showError(App.getInstance().getString(R.string.str_enter_login_pass));
                return;
            }

            if (StringUtils.isNotEmpty(cid, checkData)) {
                SecurityClient.getInstance().bindPhone(cid, checkData, countryCode.get() + phoneNum.get().trim(), pwd.get().trim());
            } else {
                SecurityClient.getInstance().bindPhone(countryCode.get() + phoneNum.get().trim(), pwd.get().trim(), code.get().trim());
            }

        }
    });

    //获取国籍列表
    private void loadCountryInfo() {
        //避免重复请求
        if (mCountryArray != null) {
            new XPopup.Builder(mContext)
                    .asBottomList(mContext.getString(R.string.choose_country), mCountryArray,
                            new OnSelectListener() {
                                @Override
                                public void onSelect(int position, String text) {
                                    updateCountryInfo(mCountryEntityList.get(position).getZhName() + "(" + mCountryEntityList.get(position).getEnName() + ")", mCountryEntityList.get(position).getAreaCode());
                                }
                            })
                    .show();
        } else {
            showDialog(mContext.getString(R.string.loading));
            RegisterClient.getInstance().findSupportCountry();
        }
    }

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<Boolean> mGetCodeSuccessLiveEvent = new SingleLiveEvent<>();
    }

    public void getPhoneCode() {
        showDialog(App.getInstance().getString(R.string.loading));
        CaptchaGetClient.getInstance().phoneCaptcha2(countryCode.get() + phoneNum.get());
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
                                if (gt3GeetestUtils != null) {
                                    gt3GeetestUtils.gt3TestFinish();
                                    gt3GeetestUtils = null;
                                }
                                showDialog(App.getInstance().getString(R.string.loading));
                                Captcha captcha = new Gson().fromJson(result, Captcha.class);
                                String checkData = "gee::" + captcha.getGeetest_challenge() + "$" + captcha.getGeetest_validate() + "$" + captcha.getGeetest_seccode();
                                CaptchaGetClient.getInstance().emailCaptchaWithHeader(countryCode.get() + phoneNum.get(), checkData, cid);
                            }
                        }
                    });
                    gt3GeetestUtils.setDialogTouch(true);
                } else {
                    CheckErrorUtil.checkError(eventBean);
                }
                break;
            //绑定邮箱
            case EvKey.bindPhone:
                if (eventBean.isStatue()) {
                    MemberClient.getInstance().userInfo();
                } else {
                    dismissDialog();
                    dealError(eventBean);
                }
                break;
            //刷新用户信息
            case EvKey.userInfo:
                if (eventBean.isStatue()) {
                    dismissDialog();
                    User user = (User) eventBean.getObject();
                    if (user != null) {
                        App.getInstance().setCurrentUser(user);
                    }
                    Toasty.showSuccess(App.getInstance().getApplicationContext().getString(R.string.str_email_bind_success));
                    finish();
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            case EvKey.logout_success_401:
                if (eventBean.isStatue()) {
                    dismissDialog();
                }
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
        countryCode.set(strAreaCode);
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
