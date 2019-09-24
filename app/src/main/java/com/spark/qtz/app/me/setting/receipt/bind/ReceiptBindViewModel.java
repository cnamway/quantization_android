package com.spark.qtz.app.me.setting.receipt.bind;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lxj.xpopup.util.XPopupUtils;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.otcclient.PayControlClient;
import com.spark.otcclient.pojo.PayListBean;
import com.spark.otcclient.pojo.PayTypeAddBean;
import com.spark.otcclient.pojo.UploadPicResult;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.Constant;
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
public class ReceiptBindViewModel extends BaseViewModel {
    private Context mContext;
    private String type;
    private String[] bankList = App.getInstance().getApplicationContext().getResources().getStringArray(R.array.bank_list);
    private PayTypeAddBean payTypeAddBean = new PayTypeAddBean();
    private int updateTypeId;

    public ReceiptBindViewModel(@NonNull Application application) {
        super(application);
    }

    //标题
    public ObservableField<String> titleText = new ObservableField<>("");
    //姓名
    public ObservableField<String> nameText = new ObservableField<>("");
    //支付宝/微信 号
    public ObservableField<String> typeText = new ObservableField<>("");
    public ObservableField<String> typeHintText = new ObservableField<>("");
    //支付宝/微信 地址
    public ObservableField<String> typeAddressText = new ObservableField<>("");
    //银行名称
    public ObservableField<String> bankText = new ObservableField<>("");
    //支行名称
    public ObservableField<String> branchText = new ObservableField<>("");
    //银行卡号
    public ObservableField<String> bankAddressText = new ObservableField<>("");
    //资金密码
    public ObservableField<String> pwdText = new ObservableField<>("");
    public ObservableField<Boolean> typeVisiable = new ObservableField<>(true);
    public ObservableField<String> bottomText = new ObservableField<>("");
    //二维码URL
    public ObservableField<String> qrCodeUrl = new ObservableField<>("");
    public ObservableField<Boolean> qrCodeVisiable = new ObservableField<>(true);

    public BindingCommand chooseBankList = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            new XPopup.Builder(mContext)
                    .maxHeight((int) (XPopupUtils.getWindowHeight(mContext) * .85f))
                    .asBottomList(App.getInstance().getString(R.string.str_select_bank), bankList,
                            new OnSelectListener() {
                                @Override
                                public void onSelect(int position, String text) {
                                    bankText.set(text);
                                }
                            })
                    .show();
        }
    });


    public BindingCommand submitCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (bottomText.get().equals(App.getInstance().getString(R.string.str_bind))) {
                bind();
            } else if (bottomText.get().equals(App.getInstance().getString(R.string.reset))) {
                update();
            }
        }
    });


    public void initType(Context context, String type, PayListBean.DataBean typeBean) {
        this.mContext = context;
        this.type = type;

        if (typeBean == null) {
            switch (type) {
                case Constant.alipay:
                    titleText.set(App.getInstance().getString(R.string.str_receive_bind_alipay_bind));
                    typeText.set(App.getInstance().getString(R.string.str_receive_bind_alipay_account));
                    typeHintText.set(App.getInstance().getString(R.string.str_enter_alipay));
                    break;
                case Constant.wechat:
                    titleText.set(App.getInstance().getString(R.string.str_receive_bind_wechat_bind));
                    typeText.set(App.getInstance().getString(R.string.str_receive_bind_wechat_account));
                    typeHintText.set(App.getInstance().getString(R.string.str_enter_wechat));
                    break;
                case Constant.card:
                    titleText.set(App.getInstance().getString(R.string.str_receive_bind_bank_bind));
                    typeVisiable.set(false);
                    qrCodeVisiable.set(false);
                    break;
                case Constant.PAYPAL:
                    titleText.set(App.getInstance().getString(R.string.str_receive_bind_paypal_bind));
                    typeText.set(App.getInstance().getString(R.string.str_receive_bind_paypal_account));
                    typeHintText.set(App.getInstance().getString(R.string.str_enter_paypal));
                    qrCodeVisiable.set(false);
                    break;
                case Constant.other:
                    titleText.set(App.getInstance().getString(R.string.str_receive_bind_other_bind));
                    typeText.set(App.getInstance().getString(R.string.str_receive_bind_other_account));
                    typeHintText.set(App.getInstance().getString(R.string.str_enter_other));
                    qrCodeVisiable.set(false);
                    break;
            }
            bottomText.set(App.getInstance().getString(R.string.str_bind));
        } else {
            if (StringUtils.isEmpty(typeBean.getRealName())) {
                nameText.set(App.getInstance().getCurrentUser().getRealName());
            } else {
                nameText.set(typeBean.getRealName());
            }
            switch (typeBean.getPayType()) {
                case Constant.alipay:
                    titleText.set(App.getInstance().getString(R.string.str_receive_bind_alipay_edit));
                    typeText.set(App.getInstance().getString(R.string.str_receive_bind_alipay_account));
                    typeHintText.set(App.getInstance().getString(R.string.str_enter_alipay));
                    typeAddressText.set(typeBean.getPayAddress());
                    qrCodeUrl.set(typeBean.getQrCodeUrl());
                    break;
                case Constant.wechat:
                    titleText.set(App.getInstance().getString(R.string.str_receive_bind_wechat_edit));
                    typeText.set(App.getInstance().getString(R.string.str_receive_bind_wechat_account));
                    typeHintText.set(App.getInstance().getString(R.string.str_enter_wechat));
                    typeAddressText.set(typeBean.getPayAddress());
                    qrCodeUrl.set(typeBean.getQrCodeUrl());
                    break;
                case Constant.card:
                    titleText.set(App.getInstance().getString(R.string.str_receive_bind_bank_edit));
                    typeVisiable.set(false);
                    qrCodeVisiable.set(false);
                    bankText.set(typeBean.getBank());
                    branchText.set(typeBean.getBranch());
                    bankAddressText.set(typeBean.getPayAddress());
                    break;
                case Constant.PAYPAL:
                    titleText.set(App.getInstance().getString(R.string.str_receive_bind_paypal_edit));
                    typeText.set(App.getInstance().getString(R.string.str_receive_bind_paypal_account));
                    typeHintText.set(App.getInstance().getString(R.string.str_enter_paypal));
                    typeAddressText.set(typeBean.getPayAddress());
                    qrCodeVisiable.set(false);
                    break;
                case Constant.other:
                    titleText.set(App.getInstance().getString(R.string.str_receive_bind_other_edit));
                    typeText.set(App.getInstance().getString(R.string.str_receive_bind_other_account));
                    typeHintText.set(App.getInstance().getString(R.string.str_enter_other));
                    typeAddressText.set(typeBean.getPayAddress());
                    qrCodeVisiable.set(false);
                    break;
            }
            updateTypeId = typeBean.getId();
            bottomText.set(App.getInstance().getString(R.string.reset));
        }
    }

    /**
     * 绑定
     */
    private void bind() {
        if (verify()) {
            showDialog(App.getInstance().getString(R.string.str_binding));
            PayControlClient.getInstance().payTypeAdd(payTypeAddBean);
        }
    }

    /**
     * 重置
     */
    private void update() {
        if (verify()) {
            showDialog(App.getInstance().getString(R.string.str_reseting));
            PayControlClient.getInstance().payTypeUpdate(updateTypeId, payTypeAddBean);
        }
    }

    /**
     * 表单校验
     */
    private boolean verify() {
        boolean isVerifySuccess = false;
        if (StringUtils.isEmpty(nameText.get())) {
            Toasty.showError(App.getInstance().getString(R.string.name_hint));
            return isVerifySuccess;
        }
        payTypeAddBean.setRealName(nameText.get().trim());
        switch (type) {
            case Constant.alipay:
                if (StringUtils.isEmpty(typeAddressText.get())) {
                    Toasty.showError(App.getInstance().getString(R.string.str_enter_alipay));
                } else {
                    if (StringUtils.isEmpty(qrCodeUrl.get())) {
                        Toasty.showError(App.getInstance().getString(R.string.str_upload));
                    } else if (StringUtils.isEmpty(pwdText.get())) {
                        Toasty.showError(App.getInstance().getString(R.string.trade_pwd_hint));
                    } else {
                        payTypeAddBean.setPayAddress(typeAddressText.get().trim());
                        payTypeAddBean.setQrCodeUrl(qrCodeUrl.get());
                        payTypeAddBean.setTradePwd(pwdText.get().trim());
                        payTypeAddBean.setPayType(type);
                        isVerifySuccess = true;
                    }
                }
                break;
            case Constant.wechat:
                if (StringUtils.isEmpty(typeAddressText.get())) {
                    Toasty.showError(App.getInstance().getString(R.string.str_enter_wechat));
                } else if (StringUtils.isEmpty(qrCodeUrl.get())) {
                    Toasty.showError(App.getInstance().getString(R.string.str_upload));
                } else if (StringUtils.isEmpty(pwdText.get())) {
                    Toasty.showError(App.getInstance().getString(R.string.trade_pwd_hint));
                } else {
                    payTypeAddBean.setPayAddress(typeAddressText.get().trim());
                    payTypeAddBean.setQrCodeUrl(qrCodeUrl.get());
                    payTypeAddBean.setTradePwd(pwdText.get().trim());
                    payTypeAddBean.setPayType(type);
                    isVerifySuccess = true;
                }
                break;
            case Constant.card:
                if (StringUtils.isEmpty(bankText.get())) {
                    Toasty.showError(App.getInstance().getString(R.string.str_enter_bank_name));
                } else if (StringUtils.isEmpty(branchText.get())) {
                    Toasty.showError(App.getInstance().getString(R.string.str_enter_branch));
                } else if (StringUtils.isEmpty(bankAddressText.get())) {
                    Toasty.showError(App.getInstance().getString(R.string.str_enter_bank_number));
                } else if (StringUtils.isEmpty(pwdText.get())) {
                    Toasty.showError(App.getInstance().getString(R.string.trade_pwd_hint));
                } else {
                    payTypeAddBean.setBank(bankText.get());
                    payTypeAddBean.setBranch(branchText.get());
                    payTypeAddBean.setPayAddress(bankAddressText.get().trim());
                    payTypeAddBean.setTradePwd(pwdText.get().trim());
                    payTypeAddBean.setPayType(type);
                    isVerifySuccess = true;
                }
                break;
            case Constant.PAYPAL:
                if (StringUtils.isEmpty(typeAddressText.get())) {
                    Toasty.showError(App.getInstance().getString(R.string.str_enter_paypal));
                } else {
                    if (StringUtils.isEmpty(pwdText.get())) {
                        Toasty.showError(App.getInstance().getString(R.string.trade_pwd_hint));
                    } else {
                        payTypeAddBean.setPayAddress(typeAddressText.get().trim());
                        payTypeAddBean.setTradePwd(pwdText.get().trim());
                        payTypeAddBean.setPayType(type);
                        isVerifySuccess = true;
                    }
                }
                break;
            case Constant.other:
                if (StringUtils.isEmpty(typeAddressText.get())) {
                    Toasty.showError(App.getInstance().getString(R.string.str_enter_other));
                } else {
                    if (StringUtils.isEmpty(pwdText.get())) {
                        Toasty.showError(App.getInstance().getString(R.string.trade_pwd_hint));
                    } else {
                        payTypeAddBean.setPayAddress(typeAddressText.get().trim());
                        payTypeAddBean.setTradePwd(pwdText.get().trim());
                        payTypeAddBean.setPayType(type);
                        isVerifySuccess = true;
                    }
                }
                break;
        }
        return isVerifySuccess;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            //上传图片
            case EvKey.uploadQRCode:
                dismissDialog();
                if (eventBean.isStatue()) {
                    qrCodeUrl.set(((UploadPicResult) eventBean.getObject()).getData());
                    Toasty.showSuccess(App.getInstance().getApplicationContext().getString(R.string.upload_success));
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //添加支付方式
            case EvKey.payTypeAdd:
                dismissDialog();
                if (eventBean.isStatue()) {
                    Toasty.showSuccess(bottomText.get() + App.getInstance().getApplicationContext().getString(R.string.success));
                    finish();
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //修改支付方式
            case EvKey.payTypeUpdate:
                dismissDialog();
                if (eventBean.isStatue()) {
                    Toasty.showSuccess(bottomText.get() + App.getInstance().getApplicationContext().getString(R.string.success));
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
