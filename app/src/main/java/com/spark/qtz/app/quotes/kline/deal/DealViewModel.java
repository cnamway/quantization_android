package com.spark.qtz.app.quotes.kline.deal;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.api.pojo.DealBean;
import com.spark.modulespot.KlineClient;
import com.spark.modulespot.pojo.AllThumbResult;
import com.spark.wsclient.base.WsCMD;
import com.spark.wsclient.utils.WsConstant;
import com.spark.wsclient.utils.WsUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.bus.event.SingleLiveEvent;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.StringUtils;
import me.spark.mvvm.utils.WebSocketRequest;
import me.spark.mvvm.utils.WebSocketResponse;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/30
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class DealViewModel extends BaseViewModel {
    public DealViewModel(@NonNull Application application) {
        super(application);
    }

    private AllThumbResult.DataBean allThumbResult;

    private OnRequestListener mOnRequestListener;
    public ObservableField<String> price = new ObservableField<>(App.getInstance().getString(R.string.price) + "(--)");
    public ObservableField<String> amount = new ObservableField<>(App.getInstance().getString(R.string.number) + "(--)");

    public UIChangeObservable uc = new UIChangeObservable();
    private boolean getHistorySuccess = false;

    public class UIChangeObservable {
        public SingleLiveEvent<DealBean> mDealResultSingleLiveEvent = new SingleLiveEvent<>();
    }

    public void initThumb(AllThumbResult.DataBean allThumbBean, OnRequestListener onRequestListener) {
        this.allThumbResult = allThumbBean;
        this.mOnRequestListener = onRequestListener;
        price.set(App.getInstance().getString(R.string.price) + "(" + allThumbResult.getSymbol().split("/")[1] + ")");
        amount.set(App.getInstance().getString(R.string.number) + "(" + allThumbResult.getSymbol().split("/")[0] + ")");
        KlineClient.getInstance().getLatestTradeUrl(allThumbResult.getSymbol());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            case EvKey.dealHistory:
                if (eventBean.isStatue() && !getHistorySuccess) {
                    getHistorySuccess = true;
                    mOnRequestListener.onSuccess(eventBean.getObject());
                    subscribeDeal();
                } else {
                    mOnRequestListener.onFail(eventBean.getMessage());
                }
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(WebSocketResponse webSocketResponse) {
        switch (webSocketResponse.getCmd()) {
            //成交推送
            case WsCMD.DEAL_PUSH:
                if (!StringUtils.isEmpty(webSocketResponse.getResponse()) && !webSocketResponse.getResponse().equals("null")) {
                    String json = "{\"date\":" + webSocketResponse.getResponse() + "}";
                    try {
                        DealBean dealBean = App.gson.fromJson(json, DealBean.class);
                        uc.mDealResultSingleLiveEvent.setValue(dealBean);
                    } catch (Exception e) {
                        LogUtils.e("Exception", e.toString());
                    }
                }
                break;
            default:
                break;
        }
    }

    //订阅深度
    public void subscribeDeal() {
        WebSocketRequest mWebSocketRequest = new WebSocketRequest();
        mWebSocketRequest.setCode(WsConstant.CODE_KLINE);
        mWebSocketRequest.setCmd(WsCMD.SUBSCRIBE_DEAL);
        mWebSocketRequest.setBody(App.gson.toJson(WsUtils.setDealSubscribeJsonMap(allThumbResult.getSymbol())).getBytes());
        EventBusUtils.postEvent(mWebSocketRequest);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBusUtils.register(this);
    }
}
