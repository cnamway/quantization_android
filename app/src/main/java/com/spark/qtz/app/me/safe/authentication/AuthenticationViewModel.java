package com.spark.qtz.app.me.safe.authentication;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.RegexUtils;
import com.spark.ucclient.MemberClient;
import com.spark.ucclient.SecurityClient;
import com.spark.ucclient.pojo.AuthErrorResult;
import com.spark.ucclient.pojo.AuthInfoEntity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.bus.event.SingleLiveEvent;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.pojo.User;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;
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
public class AuthenticationViewModel extends BaseViewModel {
    public ObservableField<String> userName = new ObservableField<>("");
    public ObservableField<String> authType = new ObservableField<>("");
    public ObservableField<String> authNum = new ObservableField<>("");
    public ObservableField<String> auditStatus = new ObservableField<>("");

    //正面 - 反面 - 手持图片 url
    public ObservableField<String> imgAuthUp = new ObservableField<>();
    public ObservableField<String> imgAuthDown = new ObservableField<>();
    public ObservableField<String> imgAuthHandheld = new ObservableField<>();
    public ObservableField<String> submitErrorText = new ObservableField<>("");
    public ObservableField<Boolean> isSubmitError = new ObservableField<>(false);

    public AuthenticationViewModel(@NonNull Application application) {
        super(application);
    }

    private OnRequestListener mOnRequestListener;


    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<Boolean> isSubmitErrorEvent = new SingleLiveEvent<>();
        public SingleLiveEvent<Boolean> isRefresh = new SingleLiveEvent<>();
    }

    public void initAuthInfo(OnRequestListener onRequestListener) {
        this.mOnRequestListener = onRequestListener;
        SecurityClient.getInstance().getAuthInfo();
    }

    //上传
    public void upload() {
        if (StringUtils.isEmpty(userName.get())) {
            Toasty.showError(App.getInstance().getApplicationContext().getString(R.string.name_hint));
            return;
        }
        if (authType.get().equals(App.getInstance().getString(R.string.id_card))) {
            if (StringUtils.isEmpty(authNum.get())) {
                Toasty.showError(App.getInstance().getApplicationContext().getString(R.string.id_card_hint));
                return;
            }
            if (!RegexUtils.isIDCard18(authNum.get())) {
                Toasty.showError(App.getInstance().getApplicationContext().getString(R.string.id_card_verify));
                return;
            }
        } else {
            if (StringUtils.isEmpty(authNum.get())) {
                Toasty.showError(App.getInstance().getApplicationContext().getString(R.string.passport_num_hint));
                return;
            }
        }

        showDialog(App.getInstance().getString(R.string.loading));
        if (authType.get().equals(App.getInstance().getString(R.string.id_card))) {
            SecurityClient.getInstance().uploadAuthInfo(
                    0, authNum.get(), imgAuthUp.get(), imgAuthHandheld.get(), imgAuthDown.get(), userName.get());
        } else {
            SecurityClient.getInstance().uploadAuthInfo(
                    1, authNum.get(), imgAuthUp.get(), imgAuthHandheld.get(), "", userName.get());
        }
    }


    public void updateView(AuthInfoEntity authInfoEntity) {
        userName.set(authInfoEntity.getData().getRealName());
        authType.set(authInfoEntity.getData().getCertifiedType() == 0 ? App.getInstance().getString(R.string.id_card) : App.getInstance().getString(R.string.passport_num));
        authNum.set(authInfoEntity.getData().getIdCardNumber());
        imgAuthUp.set(authInfoEntity.getData().getIdentityCardImgFront());
        imgAuthDown.set(authInfoEntity.getData().getIdentityCardImgReverse());
        imgAuthHandheld.set(authInfoEntity.getData().getIdentityCardImgInHand());
        switch (authInfoEntity.getData().getAuditStatus()) {
            case 0:
                auditStatus.set(App.getInstance().getString(R.string.unauthorized));
                break;
            case 1:
                auditStatus.set(App.getInstance().getString(R.string.approving));
                break;
            case 2:
                auditStatus.set(App.getInstance().getString(R.string.resubmit));
                isSubmitError.set(true);
                SecurityClient.getInstance().getAuthErrorInfo();
                break;
            case 3:
                auditStatus.set(App.getInstance().getString(R.string.authorized));
                break;
        }
        uc.isSubmitErrorEvent.setValue(isSubmitError.get());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        if (App.getInstance().getCurrentUser().getCertifiedType() == null
                && (!eventBean.getOrigin().equals(EvKey.userInfo))) return;
        switch (eventBean.getOrigin()) {
            //实名认证详情
            case EvKey.authInfo:
                if (eventBean.isStatue()) {
                    AuthInfoEntity authInfoEntity = (AuthInfoEntity) eventBean.getObject();
                    updateView(authInfoEntity);
                    mOnRequestListener.onSuccess(authInfoEntity);
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //实名认证拒绝原因
            case EvKey.authErrorInfo:
                if (eventBean.isStatue()) {
                    AuthErrorResult authErrorResult = (AuthErrorResult) eventBean.getObject();
                    submitErrorText.set(App.getInstance().getString(R.string.audit_failure) + authErrorResult.getData().getRealNameRejectReason());
                }
                break;
            //上传图片
            case EvKey.uploadIdPic:
                if (eventBean.isStatue()) {
                    LogUtils.e(eventBean.getCode() + "--" + eventBean.getMessage());
                    switch (eventBean.getCode()) {
                        //正面
                        case 0:
                            imgAuthUp.set(eventBean.getMessage());
                            break;
                        //反面
                        case 1:
                            imgAuthDown.set(eventBean.getMessage());
                            break;
                        //手持
                        case 2:
                            imgAuthHandheld.set(eventBean.getMessage());
                            break;
                    }
                    dismissDialog();
                } else {
                    dismissDialog();
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            case EvKey.uploadPortPic:
                Toasty.showSuccess(App.getInstance().getString(R.string.image_uploaded_successfully));
                if (eventBean.isStatue()) {
                    LogUtils.e(eventBean.getCode() + "--" + eventBean.getMessage());
                    switch (eventBean.getCode()) {
                        //正面
                        case 0:
                            imgAuthUp.set(eventBean.getMessage());
                            break;
                        //反面
                        case 1:
                            imgAuthDown.set(eventBean.getMessage());
                            break;
                        //手持
                        case 2:
                            imgAuthHandheld.set(eventBean.getMessage());
                            break;
                    }
                    dismissDialog();
                } else {
                    dismissDialog();
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //上传身份证
            case EvKey.uploadAuthIDCard:
                if (eventBean.isStatue()) {
                    //刷新用户信息
                    MemberClient.getInstance().userInfo();
                } else {
                    dismissDialog();
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //上传护照
            case EvKey.uploadAuthPort:
                if (eventBean.isStatue()) {
                    //刷新用户信息
                    MemberClient.getInstance().userInfo();
                } else {
                    dismissDialog();
                    Toasty.showError(eventBean.getMessage());
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
                    Toasty.showSuccess(App.getInstance().getApplicationContext().getString(R.string.upload_success));
                    finish();
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            case EvKey.logout_success_401:
                if (eventBean.isStatue()) {
                    uc.isRefresh.setValue(true);
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
