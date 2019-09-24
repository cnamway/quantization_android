package com.spark.qtz.app.me.finance.property.contract.tradeOrder;

import android.app.Application;
import android.support.annotation.NonNull;

import com.example.modulecfd.CfdClient;
import com.example.modulecfd.pojo.CfdAllThumbResult;
import com.spark.qtz.App;
import com.spark.wsclient.base.WsCMD;
import com.spark.wsclient.pojo.CfdThumbBean;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.bus.event.SingleLiveEvent;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.WebSocketResponse;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-07-03
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ContractAccountTradeViewModel extends BaseViewModel {
    private OnRequestListener mOnRequestListener;
    public CfdAllThumbResult currentCfdAllThumbResult;
    public boolean isLoadDate = true;

    public ContractAccountTradeViewModel(@NonNull Application application) {
        super(application);
    }

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<CfdAllThumbResult> currentCfdAllThumbResultObserve = new SingleLiveEvent<>();
        public SingleLiveEvent<Boolean> isRefresh = new SingleLiveEvent<>();
        public SingleLiveEvent<CfdThumbBean> mCfdThumbBeanSingleLiveEvent = new SingleLiveEvent<>();
    }

    public void loadTradeDate(int pageNo, OnRequestListener onRequestListener) {
        this.mOnRequestListener = onRequestListener;
        isLoadDate = true;
        CfdClient.getInstance().getCfdTradeOrder(pageNo);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            //币种缩略图推送
            case EvKey.cfdAllSymbolPush:
                synchronized (this) {
                    if (eventBean.isStatue()) {
                        if (isLoadDate) return;
                        currentCfdAllThumbResult = (CfdAllThumbResult) eventBean.getObject();
                        uc.currentCfdAllThumbResultObserve.setValue(currentCfdAllThumbResult);
                    }
                }
                break;
            case EvKey.cfdTradeOrder:
                if (eventBean.isStatue()) {
                    mOnRequestListener.onSuccess(eventBean.getObject());
                } else {
                    mOnRequestListener.onFail(eventBean.getMessage());
                }
                break;
            case EvKey.logout_success_401:
                if (eventBean.isStatue()) {
                    uc.isRefresh.setValue(true);
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(WebSocketResponse webSocketResponse) {
        switch (webSocketResponse.getCmd()) {
            //缩略图推送
            case WsCMD.PUSH_CFD_THUMB:
                Constant.lastCfdKlinePushTime = System.currentTimeMillis();
                if (isLoadDate) return;
                try {
                    String json = "{\"date\":" + webSocketResponse.getResponse() + "}";
                    CfdThumbBean cfdThumbBean = App.gson.fromJson(json, CfdThumbBean.class);
                    uc.mCfdThumbBeanSingleLiveEvent.setValue(cfdThumbBean);
                } catch (Exception e) {
                    LogUtils.e("Exception", e.toString());
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
