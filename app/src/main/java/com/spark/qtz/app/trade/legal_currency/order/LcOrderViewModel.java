package com.spark.qtz.app.trade.legal_currency.order;

import android.app.Application;
import android.support.annotation.NonNull;

import com.spark.otcclient.AdvertiseScanClient;
import com.spark.otcclient.LcTradeClient;
import com.spark.otcclient.pojo.TradeCoinListResult;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.bus.event.SingleLiveEvent;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/11
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class LcOrderViewModel extends BaseViewModel {
    private OnRequestListener mOnRequestListener;

    public LcOrderViewModel(@NonNull Application application) {
        super(application);
    }

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<Boolean> mIsRefresh = new SingleLiveEvent<>();
    }

    public void getLcOrder(int pageIndex, String staue, String adType, String coinName, OnRequestListener onRequestListener) {
        this.mOnRequestListener = onRequestListener;
        LcTradeClient.getInstance().getLcOrder(pageIndex, staue, adType, coinName);
    }

    public void getLcOrderAll(int pageIndex, String adType, String coinName, OnRequestListener onRequestListener) {
        this.mOnRequestListener = onRequestListener;
        LcTradeClient.getInstance().getLcOrderAll(pageIndex,adType,coinName);
    }

    public void getVCoinSupport(OnRequestListener onRequestListener) {
        this.mOnRequestListener = onRequestListener;
        AdvertiseScanClient.getInstance().getTradeCoinList();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            case EvKey.lcOrderDetails:
                if (eventBean.isStatue()) {
                    mOnRequestListener.onSuccess(eventBean.getObject());
                } else {
                    mOnRequestListener.onFail(eventBean.getMessage());
                }
                break;
            //查询所有的币种
            case EvKey.tradeCoinList:
                if (eventBean.isStatue()) {
                    TradeCoinListResult tradeCoinListBean = (TradeCoinListResult) eventBean.getObject();
                    String[] mTitles = new String[tradeCoinListBean.getData().size()];
                    for (int i = 0; i < tradeCoinListBean.getData().size(); i++) {
                        mTitles[i] = tradeCoinListBean.getData().get(i).getCoinName();
                    }
                    Constant.lcCoinNameArray = mTitles;
                    mOnRequestListener.onSuccess(eventBean.getObject());
                } else {
                    mOnRequestListener.onFail(eventBean.getMessage());
                }
                break;
            case EvKey.logout_success_401:
                if (eventBean.isStatue()) {
                    uc.mIsRefresh.setValue(true);
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
