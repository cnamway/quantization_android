package com.spark.qtz.app.me.safe.safecentre.loginpwd;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.ucclient.SecurityClient;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
 * 创建日期：2019/5/17
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class LoginPwdUpdateViewModel extends BaseViewModel {
    public LoginPwdUpdateViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<String> oldPwd = new ObservableField<>("");
    public ObservableField<String> newPwd = new ObservableField<>("");
    public ObservableField<String> newAgainPwd = new ObservableField<>("");

    //密码显示开关
    public BindingCommand oldPwdSwitchOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.oldPwdSwitchEvent.setValue(uc.oldPwdSwitchEvent.getValue() == null || !uc.oldPwdSwitchEvent.getValue());
        }
    });
    public BindingCommand newPwdSwitchOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.newPwdSwitchEvent.setValue(uc.newPwdSwitchEvent.getValue() == null || !uc.newPwdSwitchEvent.getValue());
        }
    });

    public BindingCommand newAgainPwdSwitchOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.newAgainPwdSwitchEvent.setValue(uc.newAgainPwdSwitchEvent.getValue() == null || !uc.newAgainPwdSwitchEvent.getValue());
        }
    });

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //密码开关观察者
        public SingleLiveEvent<Boolean> oldPwdSwitchEvent = new SingleLiveEvent<>();
        public SingleLiveEvent<Boolean> newPwdSwitchEvent = new SingleLiveEvent<>();
        public SingleLiveEvent<Boolean> newAgainPwdSwitchEvent = new SingleLiveEvent<>();
    }

    public BindingCommand pwdUpdateOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            loginPwdUpdate();
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

    //修改登录密码提交
    private void loginPwdUpdate() {
        if (StringUtils.isEmpty(oldPwd.get())) {
            Toasty.showError(App.getInstance().getApplicationContext().getString(R.string.old_pwd_hint));
            return;
        }
        if (StringUtils.isEmpty(newPwd.get())) {
            Toasty.showError(App.getInstance().getApplicationContext().getString(R.string.new_pwd_hint));
            return;
        }
        if (StringUtils.isEmpty(newAgainPwd.get())) {
            Toasty.showError(App.getInstance().getApplicationContext().getString(R.string.new_pwd_ensure_hint));
            return;
        }

        if (!newPwd.get().equals(newAgainPwd.get())) {
            Toasty.showError(App.getInstance().getApplicationContext().getString(R.string.pwd_inconsistent));
            return;
        }

        showDialog(App.getInstance().getString(R.string.loading));
        SecurityClient.getInstance().updateLoginPwd(newPwd.get(), oldPwd.get());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            //上传图片
            case EvKey.updateLoginPwd:
                dismissDialog();
                if (eventBean.isStatue()) {
                    Toasty.showSuccess(eventBean.getMessage());
                    //修改成功，重新登录
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
