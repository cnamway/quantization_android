package com.spark.qtz.app.trade.currency.openorders.fragment;

import android.app.Application;
import android.support.annotation.NonNull;

import com.spark.modulespot.SpotCoinClient;

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
 * 创建日期：2019/6/13
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class OpenOrdersVpViewModel extends BaseViewModel {
    public OpenOrdersVpViewModel(@NonNull Application application) {
        super(application);
    }

    private OnRequestListener onRequestListener;
    private OnRequestListener onHistoryRequestListener;

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<Boolean> orderStatue = new SingleLiveEvent<>();
        public SingleLiveEvent<Boolean> isRefresh = new SingleLiveEvent<>();
    }

    public void findOpenOrdersType(int type, int pageIndex, String symbol, String slide, OnRequestListener onRequestListener) {
        switch (type) {
            case 0:
                this.onRequestListener = onRequestListener;
                SpotCoinClient.getInstance().findOpenOrders(pageIndex, slide, symbol);
                break;
            case 1:
                this.onHistoryRequestListener = onRequestListener;
                SpotCoinClient.getInstance().findOpenOrdersHistory(pageIndex, slide, symbol);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            case EvKey.openOrders:
                if (onRequestListener == null) return;
                if (eventBean.isStatue()) {
                    onRequestListener.onSuccess(eventBean.getObject());
                } else {
                    onRequestListener.onFail(eventBean.getMessage());
                }
                break;
            case EvKey.openOrdersHistory:
                if (onHistoryRequestListener == null) return;
                if (eventBean.isStatue()) {
                    onHistoryRequestListener.onSuccess(eventBean.getObject());
                } else {
                    onHistoryRequestListener.onFail(eventBean.getMessage());
                }
                break;
            //用户撤单
            case EvKey.cancelOrder:
                if (eventBean.isStatue()) {
                    uc.orderStatue.setValue(true);
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
