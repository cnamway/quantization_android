package com.spark.qtz.app.me.setting.receipt;

import android.app.Application;
import android.support.annotation.NonNull;

import com.spark.qtz.ui.toast.Toasty;
import com.spark.otcclient.PayControlClient;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.bus.event.SingleLiveEvent;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/28
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ReceiptViewModel extends BaseViewModel {
    public ReceiptViewModel(@NonNull Application application) {
        super(application);
    }

    private OnRequestListener mOnRequestListener;
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<Boolean> isRefresh = new SingleLiveEvent<>();
    }
    /**
     * 获取收款方式
     */
    public void getList(OnRequestListener onRequestListener) {
        this.mOnRequestListener = onRequestListener;
        PayControlClient.getInstance().queryList();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            //查询用户的某个币种的提币地址信息
            case EvKey.payList:
                if (mOnRequestListener == null) return;
                if (eventBean.isStatue()) {
                    mOnRequestListener.onSuccess(eventBean.getObject());
                } else {
                    mOnRequestListener.onFail(eventBean.getMessage());
                }
                break;
            //修改支付方式状态/删除支付方式
            case EvKey.payTypeUpdate:
                dismissDialog();
                if (eventBean.isStatue()) {
                    getList(mOnRequestListener);
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
