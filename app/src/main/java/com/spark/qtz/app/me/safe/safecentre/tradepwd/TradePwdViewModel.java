package com.spark.qtz.app.me.safe.safecentre.tradepwd;

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
public class TradePwdViewModel extends BaseViewModel {
    public TradePwdViewModel(@NonNull Application application) {
        super(application);
    }

    public boolean isFirst;

    public ObservableField<String> oldPwd = new ObservableField<>("");
    public ObservableField<String> newPwd = new ObservableField<>("");
    public ObservableField<String> newAgainPwd = new ObservableField<>("");

    //标题
    public ObservableField<String> titleText = new ObservableField<>("");
    public ObservableField<Boolean> oldPwdVisiable = new ObservableField<>(false);

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
            tradePwdUpdate();
        }
    });

    //忘记密码
    public BindingCommand pwdForGetOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_TRADE_PASS_FORGET)
                    .navigation();
        }
    });

    public void initIsFirst(boolean isFirst) {
        this.isFirst = isFirst;
        titleText.set(isFirst ? App.getInstance().getString(R.string.lc_pwd_set) : App.getInstance().getString(R.string.lc_pwd_update));
        oldPwdVisiable.set(isFirst);
    }

    //修改登录密码提交
    private void tradePwdUpdate() {
        if (!isFirst && StringUtils.isEmpty(oldPwd.get())) {
            Toasty.showError(App.getInstance().getApplicationContext().getString(R.string.old_pwd_hint));
            return;
        }
        if (StringUtils.isEmpty(newPwd.get())) {
            Toasty.showError(isFirst ?
                    App.getInstance().getApplicationContext().getString(R.string.pwd_hint) :
                    App.getInstance().getApplicationContext().getString(R.string.new_pwd_hint));
            return;
        }
        if (StringUtils.isEmpty(newAgainPwd.get())) {
            Toasty.showError(isFirst ?
                    App.getInstance().getApplicationContext().getString(R.string.pwd_again) :
                    App.getInstance().getApplicationContext().getString(R.string.new_pwd_ensure_hint));
            return;
        }

        if (!newPwd.get().equals(newAgainPwd.get())) {
            Toasty.showError(App.getInstance().getApplicationContext().getString(R.string.pwd_inconsistent));
            return;
        }

        showDialog(App.getInstance().getString(R.string.loading));
        if (isFirst) {
            SecurityClient.getInstance().setTradePassword(newPwd.get());
        } else {
            SecurityClient.getInstance().updateTradePassword(newPwd.get(), oldPwd.get());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            //设置交易密码
            case EvKey.setTradePwd:
                dismissDialog();
                if (eventBean.isStatue()) {
                    //修改-用户资金密码已设置
                    App.getInstance().getCurrentUser().setFundsVerified(1);
                    Toasty.showSuccess(App.getInstance().getString(R.string.set_success));
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
