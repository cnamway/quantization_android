package com.spark.qtz.app.trade.legal_currency.order.appeal;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.ui.toast.Toasty;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/18
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class LcOrderAppealViewModel extends BaseViewModel {
    public LcOrderAppealViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<String> appealContent = new ObservableField<>("");
    public ObservableField<String> imgOne = new ObservableField<>("");
    public ObservableField<String> imgTwo = new ObservableField<>("");
    public ObservableField<String> imgThree = new ObservableField<>("");

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            //上传图片
            case EvKey.lcOrderPicupload:
                if (eventBean.isStatue()) {
                    LogUtils.e(eventBean.getCode() + "--" + eventBean.getMessage());
                    switch (eventBean.getCode()) {
                        case 0:
                            imgOne.set(eventBean.getMessage());
                            break;
                        case 1:
                            imgTwo.set(eventBean.getMessage());
                            break;
                        case 2:
                            imgThree.set(eventBean.getMessage());
                            break;
                    }
                    dismissDialog();
                } else {
                    dismissDialog();
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            case EvKey.lcOrderAppeal:
                dismissDialog();
                if (eventBean.isStatue()) {
                    Toasty.showSuccess(App.getInstance().getApplicationContext().getString(R.string.str_appeal_success));
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
