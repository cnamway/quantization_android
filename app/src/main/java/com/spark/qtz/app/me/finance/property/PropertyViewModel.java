package com.spark.qtz.app.me.finance.property;

import android.app.Application;
import android.support.annotation.NonNull;

import com.spark.acclient.FinanceClient;
import com.spark.qtz.App;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.binding.command.BindingAction;
import me.spark.mvvm.binding.command.BindingCommand;
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
public class PropertyViewModel extends BaseViewModel {
    public PropertyViewModel(@NonNull Application application) {
        super(application);
    }

    public BindingCommand finishOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });
    private OnRequestListener mOnRequestListener;

    public void getCoinSupport(OnRequestListener onRequestListener) {
        this.mOnRequestListener = onRequestListener;
        FinanceClient.getInstance().getCoinSupport();
    }

    private boolean getCoinSupportSuccess = false;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            case EvKey.fromPropertyDetails:
                finish();
                break;
            //查询平台支持到币种信息
            case EvKey.coinSupport:
                if (getCoinSupportSuccess) return;
                if (eventBean.isStatue()) {
                    getCoinSupportSuccess = true;
                    Constant.accountJson = App.gson.toJson(eventBean.getObject());
                    mOnRequestListener.onSuccess(eventBean.getObject());
                } else {
                    mOnRequestListener.onFail(eventBean.getMessage());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Constant.isMeFinanceVisiable = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        Constant.isMeFinanceVisiable = false;
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
