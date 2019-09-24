package com.spark.qtz.app.me.safe.identity;

import android.app.Application;
import android.support.annotation.NonNull;

import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.ucclient.SecurityClient;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.pojo.User;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-07-18
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class IdentityViewModel extends BaseViewModel {
    public IdentityViewModel(@NonNull Application application) {
        super(application);
    }

    private OnRequestListener mOnRequestListener;


    public void initAuthInfo(OnRequestListener onRequestListener) {
        this.mOnRequestListener = onRequestListener;
        SecurityClient.getInstance().getAuthInfo();
    }

    public void initAuthErrorInfo(OnRequestListener onRequestListener) {
        this.mOnRequestListener = onRequestListener;
        SecurityClient.getInstance().getAuthErrorInfo();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        if (App.getInstance().getCurrentUser().getCertifiedType() == null
                && (!eventBean.getOrigin().equals(EvKey.userInfo))) return;
        switch (eventBean.getOrigin()) {
            //实名认证详情
            case EvKey.authInfo:
                if (eventBean.isStatue()) {
                    mOnRequestListener.onSuccess(eventBean.getObject());
                } else {
                    mOnRequestListener.onFail(eventBean.getMessage());
                }
                break;
            //实名认证拒绝原因
            case EvKey.authErrorInfo:
                if (eventBean.isStatue()) {
                    mOnRequestListener.onSuccess(eventBean.getObject());
                } else {
                    mOnRequestListener.onFail(eventBean.getMessage());
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
