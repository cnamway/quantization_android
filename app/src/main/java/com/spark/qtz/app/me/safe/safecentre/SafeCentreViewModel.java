package com.spark.qtz.app.me.safe.safecentre;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;

import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.binding.command.BindingAction;
import me.spark.mvvm.binding.command.BindingCommand;
import me.spark.mvvm.bus.event.SingleLiveEvent;
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
public class SafeCentreViewModel extends BaseViewModel {
    public SafeCentreViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<String> phoneNum = new ObservableField<>();
    public ObservableField<Boolean> phoneNumHasSet = new ObservableField<>(false);
    public ObservableField<String> emailPwd = new ObservableField<>();
    public ObservableField<Boolean> emailPwdHasSet = new ObservableField<>();
    public ObservableField<String> googleCertification = new ObservableField<>(App.getInstance().getApplicationContext().getString(R.string.no_setting));
    public ObservableField<Boolean> googleCertificationHasSet = new ObservableField<>(false);
    public ObservableField<String> loginPwd = new ObservableField<>(App.getInstance().getApplicationContext().getString(R.string.has_setting));
    public ObservableField<Boolean> loginPwdHasSet = new ObservableField<>(true);
    public ObservableField<String> legalCurrencyPwd = new ObservableField<>(App.getInstance().getApplicationContext().getString(R.string.no_setting));
    public ObservableField<Boolean> legalCurrencyPwdHasSet = new ObservableField<>(false);

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<Integer> safeLevel = new SingleLiveEvent<>();
    }

    //修改手机号
    public BindingCommand phoneOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (App.getInstance().getCurrentUser().getMobilePhone() != null) return;
            ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_SAFECENTRE_PHONE)
                    .navigation();
        }
    });

    //绑定邮箱
    public BindingCommand emailOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (App.getInstance().getCurrentUser().getEmail() != null) return;
            ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_SAFECENTRE_EMAIL)
                    .navigation();
        }
    });

    //开启谷歌认证器
    public BindingCommand googleOpenOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (App.getInstance().getCurrentUser().getGoogleAuthStatus() != 0) {
                ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_SAFECENTRE_GOOGLE_REMOVE)
                        .navigation();
            } else {
                ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_SAFECENTRE_GOOGLE_OPEN)
                        .navigation();
            }
        }
    });

    //登录密码
    public BindingCommand loginPwdUpdateOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_SAFECENTRE_LOGIN_PASS)
                    .navigation();
        }
    });

    //法币资金密码
    public BindingCommand tradePwdOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_TRADE_PASS)
                    .withInt("isFirst", App.getInstance().getCurrentUser().getFundsVerified())
                    .navigation();
        }
    });

    public void initItemStatue() {
        phoneNumHasSet.set(App.getInstance().getCurrentUser().getMobilePhone() != null);
        phoneNum.set(App.getInstance().getCurrentUser().getMobilePhone() == null ?
                App.getInstance().getApplicationContext().getString(R.string.no_setting)
                : StringUtils.formatPhone(App.getInstance().getCurrentUser().getMobilePhone()));
        emailPwdHasSet.set(App.getInstance().getCurrentUser().getEmail() != null);
        emailPwd.set(App.getInstance().getCurrentUser().getEmail() == null ?
                App.getInstance().getApplicationContext().getString(R.string.no_setting)
                : App.getInstance().getCurrentUser().getEmail());
        legalCurrencyPwdHasSet.set(App.getInstance().getCurrentUser().getFundsVerified() == 1);
        legalCurrencyPwd.set(legalCurrencyPwdHasSet.get() ?
                App.getInstance().getApplicationContext().getString(R.string.has_setting)
                : App.getInstance().getApplicationContext().getString(R.string.no_setting));
        googleCertificationHasSet.set(App.getInstance().getCurrentUser().getGoogleAuthStatus() != 0);
        googleCertification.set(googleCertificationHasSet.get() ?
                App.getInstance().getApplicationContext().getString(R.string.has_setting)
                : App.getInstance().getApplicationContext().getString(R.string.no_setting));

        int level = 2;
        if (App.getInstance().getCurrentUser().getEmail() != null) {
            level += 1;
        }
        if (App.getInstance().getCurrentUser().getGoogleAuthStatus() != 0) {
            level += 1;
        }
        if (App.getInstance().getCurrentUser().getFundsVerified() == 1) {
            level += 1;
        }
        uc.safeLevel.setValue(level);
    }

}
