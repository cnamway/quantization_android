package com.spark.qtz.app.me.safe.safecentre.google.remove;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.ucclient.GoogleClient;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.binding.command.BindingAction;
import me.spark.mvvm.binding.command.BindingCommand;
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
public class GoogleRemoveViewModel extends BaseViewModel {
    public GoogleRemoveViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<String> googleCode = new ObservableField<>("");
    public ObservableField<String> loginPass = new ObservableField<>("");
    private User mUser;

    public void loadDate() {
        if (!App.getInstance().isAppLogin()) {
            return;
        }
        mUser = App.getInstance().getCurrentUser();
    }

    //解绑
    public BindingCommand submitOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (StringUtils.isEmpty(googleCode.get())) {
                Toasty.showError(App.getInstance().getString(R.string.google_code_hint));
                return;
            }
            if (StringUtils.isEmpty(loginPass.get())) {
                Toasty.showError(App.getInstance().getString(R.string.str_enter_login_pass));
                return;
            }
            GoogleClient.getInstance().googleAuthRemove(googleCode.get(), loginPass.get());
        }
    });

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            case EvKey.loginStatue:
                loadDate();
                break;
            //解绑Google认证
            case EvKey.googleAuthRemove:
                if (eventBean.isStatue()) {
                    mUser.setGoogleAuthStatus(0);
                    App.getInstance().setCurrentUser(mUser);
                    Toasty.showSuccess(App.getInstance().getString(R.string.success));
                    finish();
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            case EvKey.logout_success_401:
                if (eventBean.isStatue()) {
                    loadDate();
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
