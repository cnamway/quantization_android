package com.spark.qtz.app.me;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.spark.casclient.CasClient;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.api.pojo.UpdateBean;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.AppUtils;
import com.spark.qtz.util.CheckErrorUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.base.BaseRequestCode;
import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.binding.command.BindingAction;
import me.spark.mvvm.binding.command.BindingCommand;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.StringUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/18
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class MeViewModel extends BaseViewModel {
    public MeViewModel(@NonNull Application application) {
        super(application);
    }

    private Context mContext;
    public boolean isVisible2User = false;
    public boolean isOnPause = true;

    public ObservableField<String> versionName = new ObservableField<>(AppUtils.getContextVersionName());

    //版本更新
    public BindingCommand versionUpdateClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            try {
                UpdateBean mUpdateBean = BaseApplication.gson.fromJson(Constant.updateInfoJson, UpdateBean.class);
                if (StringUtils.formatVersionCode(mUpdateBean.getData().getVersion()) >
                        StringUtils.formatVersionCode(AppUtils.getContextVersionName())) {
                    AppUtils.updateApp(mUpdateBean);
                } else {
                    Toasty.showSuccess(App.getInstance().getString(R.string.already_the_latest_version));
                }
            } catch (Exception e) {
                LogUtils.e("checkVersion", e.toString());
            }
        }
    });

    //退出登录
    public BindingCommand logoutOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (App.getInstance().isAppLogin()) {
                new XPopup.Builder(mContext)
                        .asConfirm(mContext.getString(R.string.tips), mContext.getString(R.string.confirm_2_logout),
                                mContext.getString(R.string.cancel), mContext.getString(R.string.ensure),
                                new OnConfirmListener() {
                                    @Override
                                    public void onConfirm() {
                                        showDialog(mContext.getString(R.string.loading));
                                        CasClient.getInstance().logout(false);
                                    }
                                }, null, false)
                        .show();
            }
        }
    });

    public void initContext(Context context) {
        mContext = context;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        if (!isVisible2User && isOnPause && (!eventBean.getOrigin().equals(EvKey.loginStatue)))
            return;
        switch (eventBean.getOrigin()) {
            //退出登录
            case EvKey.logout:
                dismissDialog();
                if (eventBean.isStatue()) {
                    App.getInstance().deleteCurrentUser();
                    EventBusUtils.postSuccessEvent(EvKey.loginStatue, BaseRequestCode.OK, "");
                    Toasty.showSuccess(App.getInstance().getString(R.string.logout_success));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_LOGIN)
                                    .navigation();
                            finish();
                        }
                    }, 500);
                } else {
                    CheckErrorUtil.checkError(eventBean);
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
        isVisible2User = true;
        EventBusUtils.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtils.unRegister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        isOnPause = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        isOnPause = true;
    }
}
