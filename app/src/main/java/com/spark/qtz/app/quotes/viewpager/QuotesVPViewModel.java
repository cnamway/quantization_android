package com.spark.qtz.app.quotes.viewpager;

import android.app.Application;
import android.support.annotation.NonNull;

import com.spark.qtz.App;
import com.spark.qtz.api.pojo.CoinPairPushBean;
import com.spark.wsclient.base.WsCMD;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.bus.event.SingleLiveEvent;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.WebSocketResponse;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/19
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class QuotesVPViewModel extends BaseViewModel {
    private String type;

    public QuotesVPViewModel(@NonNull Application application) {
        super(application);
    }

    public UIChangeObservable mUIChangeObservable = new UIChangeObservable();

    public class UIChangeObservable {
        //币对的推送的观察
        public SingleLiveEvent<CoinPairPushBean> mCoinPairPushBean = new SingleLiveEvent<>();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(WebSocketResponse webSocketResponse) {
        switch (webSocketResponse.getCmd()) {
            //首页缩略图订阅
            case WsCMD.PUSH_THUMB:
                CoinPairPushBean coinPairPushBean = App.gson.fromJson(webSocketResponse.getResponse(), CoinPairPushBean.class);
                if ((!coinPairPushBean.getSymbol().endsWith(type))) return;
                mUIChangeObservable.mCoinPairPushBean.setValue(coinPairPushBean);
                break;
            default:
                break;
        }
    }

    public void initType(String type) {
        this.type = type;
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
