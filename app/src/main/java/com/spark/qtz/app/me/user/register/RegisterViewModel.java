package com.spark.qtz.app.me.user.register;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.geetest.sdk.Bind.GT3GeetestBindListener;
import com.geetest.sdk.Bind.GT3GeetestUtilsBind;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.CheckErrorUtil;
import com.spark.qtz.util.RegexUtils;
import com.spark.ucclient.CaptchaGetClient;
import com.spark.ucclient.RegisterClient;
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
public class RegisterViewModel extends BaseViewModel {
    private GT3GeetestUtilsBind gt3GeetestUtils;
    private String cid;
    private String[] mCountryArray;
    private Context mContext;
    private List<CountryEntity> mCountryEntityList;
    private String countryEnName;                      //值传递 国籍 enName
    public int type = 0;                                //0 - 手机注册 1 - 邮箱注册

    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    public void initContext(Context context) {
        this.mContext = context;
    }

    public ObservableField<String> title = new ObservableField<>(App.getInstance().getString(R.string.register_phone));
    public ObservableField<String> subTitle = new ObservableField<>(App.getInstance().getString(R.string.register_description));
    public ObservableField<String> registerType = new ObservableField<>(App.getInstance().getString(R.string.phone_num));
    public ObservableField<String> registerHint = new ObservableField<>(App.getInstance().getString(R.string.phone_num_hint));
    //手机号码
    public ObservableField<String> phoneNum = new ObservableField<>("");
    public ObservableField<String> countryCode = new ObservableField<>("");
    public ObservableField<String> countryName = new ObservableField<>("");

    //国籍选择
    public BindingCommand chooseCountryOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            loadCountryInfo();
        }
    });

    //获取短信验证码
    public BindingCommand getCodeOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            switch (type) {
                //手机注册
                case 0:
                    if (!RegexUtils.isMobileExact(phoneNum.get())) {
                        Toasty.showError(mContext.getString(R.string.valid_phone));
                        return;
                    }
                    if (StringUtils.isEmpty(countryCode.get()) || StringUtils.isEmpty(countryName.get())) {
                        Toasty.showError(mContext.getString(R.string.choose_country));
                        return;
                    }
                    showDialog(mContext.getString(R.string.loading));
                    CaptchaGetClient.getInstance().phoneCaptcha2(countryCode.get() + phoneNum.get());
                    break;
                //邮箱注册
                case 1:
                    if (!RegexUtils.isEmail(phoneNum.get())) {
                        Toasty.showError(mContext.getString(R.string.valid_email));
                        return;
                    }

                    showDialog(mContext.getString(R.string.loading));
                    CaptchaGetClient.getInstance().emailCaptcha(phoneNum.get());
                    break;
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
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_VERIFYCODE)
                                    .withString("phoneNum", countryCode.get() + phoneNum.get())
                                    .withString("strCountry", countryEnName)
                                    .withInt("type", type)
                                    .navigation();
                        }
                    }, 1000);

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
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_VERIFYCODE)
                                    .withString("phoneNum", phoneNum.get())
                                    .withString("strCountry", countryEnName)
                                    .withInt("type", type)
                                    .navigation();
                        }
                    }, 1000);

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
            case EvKey.registerSuccess:
                finish();
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

    @Override
    public void onResume() {
        super.onResume();
        EventBusUtils.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBusUtils.unRegister(this);
    }
}
