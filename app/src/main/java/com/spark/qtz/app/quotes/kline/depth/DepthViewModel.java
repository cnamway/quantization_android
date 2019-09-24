package com.spark.qtz.app.quotes.kline.depth;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.modulespot.QuoteClient;
import com.spark.modulespot.pojo.AllThumbResult;
import com.spark.modulespot.pojo.MarketSymbolResult;
import com.spark.modulespot.pojo.MarketSymbolResult.DataBean.AskBean;
import com.spark.modulespot.pojo.MarketSymbolResult.DataBean.BidBean;
import com.spark.wsclient.base.WsCMD;
import com.spark.wsclient.pojo.B2BMarketBean;
import com.spark.wsclient.utils.WsConstant;
import com.spark.wsclient.utils.WsUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.bus.event.SingleLiveEvent;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;
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
public class DepthViewModel extends BaseViewModel {
    public DepthViewModel(@NonNull Application application) {
        super(application);
    }

    private AllThumbResult.DataBean allThumbResult;
    public ObservableField<String> mPrice = new ObservableField<>("");
    public ObservableField<String> mNum = new ObservableField<>("");
    private List<AskBean> mMarketSellList = new ArrayList<>();
    private List<BidBean> mMarketBuyList = new ArrayList<>();

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<MarketSymbolResult> mMarketSymbolResultSingleLiveEvent = new SingleLiveEvent<>();
        public SingleLiveEvent<List<AskBean>> mMarketSellSingleLiveEvent = new SingleLiveEvent<>();
        public SingleLiveEvent<List<BidBean>> mMarketBuySingleLiveEvent = new SingleLiveEvent<>();
    }

    public void initThumb(AllThumbResult.DataBean allThumbBean) {
        this.allThumbResult = allThumbBean;
        initNumTv();
        initPriceTv();
        QuoteClient.getInstance().getMarketSymbolPolling(allThumbBean.getSymbol());
    }

    public void initThumbWithoutRequest(AllThumbResult.DataBean allThumbBean) {
        this.allThumbResult = allThumbBean;
        initNumTv();
        initPriceTv();
    }

    //买盘 - 卖盘 数量
    public void initNumTv() {
        mNum.set(App.getInstance().getString(R.string.number) + "(" + allThumbResult.getSymbol().split("/")[0] + ")");
    }

    //价格
    public void initPriceTv() {
        mPrice.set(App.getInstance().getString(R.string.price) + "(" + allThumbResult.getSymbol().split("/")[1] + ")");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            //获取指定币种指定深度盘口
            case EvKey.marketSymbol:
                synchronized (this) {
                    if (eventBean.isStatue()
                            && eventBean.getMessage().equals(allThumbResult.getSymbol())) {
                        uc.mMarketSymbolResultSingleLiveEvent.setValue((MarketSymbolResult) eventBean.getObject());
                    }
                }
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(WebSocketResponse webSocketResponse) {
        switch (webSocketResponse.getCmd()) {
            //盘口推送
            case WsCMD.PUSH_TRADE_PLATE:
                try {
                    Constant.lastB2BMarketPushTime = System.currentTimeMillis();
                    B2BMarketBean b2BMarketBean = App.gson.fromJson(webSocketResponse.getResponse(), B2BMarketBean.class);
                    if (null == allThumbResult && !b2BMarketBean.getSymbol().contains(allThumbResult.getSymbol()))
                        return;
                    if (b2BMarketBean.getDirection() == 0) {//买
                        mMarketBuyList.clear();
                        for (B2BMarketBean.ItemsBean item : b2BMarketBean.getItems()) {
                            MarketSymbolResult.DataBean.BidBean bidBean = new MarketSymbolResult.DataBean.BidBean();
                            bidBean.setAmount(item.getAmount());
                            bidBean.setPrice(item.getPrice());
                            mMarketBuyList.add(bidBean);
                        }
                        uc.mMarketBuySingleLiveEvent.setValue(mMarketBuyList);
                    } else if (b2BMarketBean.getDirection() == 1) {//卖
                        mMarketSellList.clear();
                        for (B2BMarketBean.ItemsBean item : b2BMarketBean.getItems()) {
                            MarketSymbolResult.DataBean.AskBean askBean = new MarketSymbolResult.DataBean.AskBean();
                            askBean.setAmount(item.getAmount());
                            askBean.setPrice(item.getPrice());
                            mMarketSellList.add(askBean);
                        }
                        uc.mMarketSellSingleLiveEvent.setValue(mMarketSellList);
                    }
                } catch (Exception e) {
                    LogUtils.e("PUSH_THUMB", e.toString());
                }
                break;
            default:
                break;
        }
    }

    /**
     * 订阅盘口ws
     */
    public void subscribeB2BTradePlate(AllThumbResult.DataBean allThumbBean) {
        this.allThumbResult = allThumbBean;
        initNumTv();
        initPriceTv();
        WebSocketRequest mWebSocketRequest = new WebSocketRequest();
        mWebSocketRequest.setCode(WsConstant.CODE_MARKET);
        mWebSocketRequest.setCmd(WsCMD.SUBSCRIBE_SPOT_TRADE_PLATE);
        mWebSocketRequest.setBody(App.gson.toJson(WsUtils.setCFDMarketJsonMap(allThumbResult.getSymbol())).getBytes());
        EventBusUtils.postEvent(mWebSocketRequest);
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
