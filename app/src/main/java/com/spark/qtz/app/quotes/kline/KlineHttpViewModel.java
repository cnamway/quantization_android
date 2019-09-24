package com.spark.qtz.app.quotes.kline;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.KlineSubscribeBean;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.DateUtils;
import com.spark.klinelib.KLineEntity;
import com.spark.modulespot.KlineClient;
import com.spark.modulespot.QuoteClient;
import com.spark.modulespot.SpotCoinClient;
import com.spark.modulespot.pojo.AllThumbResult;
import com.spark.wsclient.base.WsCMD;
import com.spark.wsclient.pojo.B2BThumbBean;
import com.spark.wsclient.utils.WsConstant;
import com.spark.wsclient.utils.WsUtils;
import com.zhouyou.http.EasyHttp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.binding.command.BindingAction;
import me.spark.mvvm.binding.command.BindingCommand;
import me.spark.mvvm.bus.event.SingleLiveEvent;
import me.spark.mvvm.utils.DfUtils;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.MathUtils;
import me.spark.mvvm.utils.SPUtils;
import me.spark.mvvm.utils.SpanUtils;
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
public class KlineHttpViewModel extends BaseViewModel {
    private AllThumbResult.DataBean allThumbResult;
    private String resolution, period;
    private List<KLineEntity> mKLineEntities = new ArrayList<>();

    public String lastResolution;//上次传入时间段
    private int type;

    //是否收藏
    public ObservableField<Boolean> isFavor = new ObservableField<>(false);
    //现价
    public ObservableField<CharSequence> close = new ObservableField<>();
    //换算
    public ObservableField<String> convert = new ObservableField<>();
    public ObservableField<Boolean> convertColor = new ObservableField<>();
    //24H量
    public ObservableField<String> _24hourcount = new ObservableField<>();
    //24H最高
    public ObservableField<String> _24hourhigh = new ObservableField<>();
    //24H最低
    public ObservableField<String> _24hourlow = new ObservableField<>();

    //收藏 - 取消收藏
    public BindingCommand favorCommond = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!App.getInstance().isAppLogin()) {
                ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_LOGIN)
                        .navigation();
            } else {
                showDialog(App.getInstance().getString(R.string.loading));
                if (isFavor.get()) {
                    SpotCoinClient.getInstance().favorDelete(allThumbResult.getSymbol());
                } else {
                    SpotCoinClient.getInstance().favorAdd(allThumbResult.getSymbol());
                }
            }
        }
    });

    //查看完整行情
    public BindingCommand goTradeBuy = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (Constant.isCcFirstLoad) Constant.ccFirstLoadSymbol = allThumbResult.getSymbol();
            EventBusUtils.postSuccessEvent(EvKey.fromKline, 0, "", allThumbResult);
            EventBusUtils.postJumpEvent(EvKey.goTrade);
            finish();
        }
    });

    //查看完整行情
    public BindingCommand goTradeSell = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (Constant.isCcFirstLoad) Constant.ccFirstLoadSymbol = allThumbResult.getSymbol();
            EventBusUtils.postSuccessEvent(EvKey.fromKline, 1, "", allThumbResult);
            EventBusUtils.postJumpEvent(EvKey.goTrade);
            finish();
        }
    });

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<List<KLineEntity>> klineHistoryList = new SingleLiveEvent<>();
        public SingleLiveEvent<KLineEntity> klineSubscribe = new SingleLiveEvent<>();
    }

    public KlineHttpViewModel(@NonNull Application application) {
        super(application);
    }

    public void initDate(AllThumbResult.DataBean allThumbResult) {
        this.allThumbResult = allThumbResult;
        if (App.getInstance().isAppLogin()) {
            isFavor.set(SPUtils.getInstance().getFavorFindList().contains(allThumbResult.getSymbol()));
        }
        updateCoinPairPushBean();
    }

    //获取K线历史数据
    public void loadKlineHistory(int type) {
        this.type = type;
        Long to = System.currentTimeMillis(), from;
        switch (type) {
            //分时
            case 0:
                from = to - 12L * 60 * 60 * 1000;       // 前半天的数据
                resolution = "m1";
                period = "m1";
                break;
            //1分
            case 1:
                from = to - 12L * 60 * 60 * 1000;       // 前一天的数据
                resolution = "m1";
                period = "m1";
                break;
            //5分
            case 2:
                from = to - 2 * 24L * 60 * 60 * 1000;   // 前2天的数据
                resolution = "m5";
                period = "m5";
                break;
            //1小时
            case 3:
                from = to - 24 * 24L * 60 * 60 * 1000;  // 前24天数据
                resolution = "h1";
                period = "h1";
                break;
            //1天
            case 4:
                from = to - 60 * 24L * 60 * 60 * 1000;  // 前60天数据
                resolution = "d1";
                period = "d1";
                break;
            case 5: // 15分钟
                from = to - 6 * 24L * 60 * 60 * 1000;   // 前6天的数据
                resolution = "m15";
                period = "m15";
                break;
            case 6: // 30分钟
                from = to - 10 * 24L * 60 * 60 * 1000;  // 前12 天数据
                resolution = "m30";
                period = "m30";
                break;
            case 7: // 1周
                from = to - 730 * 24L * 60 * 60 * 1000;  // 前2年数据
                resolution = "w1";
                period = "w1";
                break;
            case 8: // 1月
                from = to - 1095 * 24L * 60 * 60 * 1000; // 前3年数据
                resolution = "M1";
                period = "M1";
                break;
            default: // 默认1分钟
                from = to - 24L * 60 * 60 * 1000;        // 前一天的数据
                resolution = "m1";
                period = "m1";
                break;
        }
        //获取历史数据
        KlineClient.getInstance().getKlineHistory(allThumbResult.getSymbol(), WsConstant.SPOT, from, to, resolution);
    }

    //title
    public CharSequence initTitle() {
        CharSequence text = new SpanUtils()
                .append(allThumbResult.getSymbol().split("/")[0])
                .append(" / " + allThumbResult.getSymbol().split("/")[1])
                .setFontSize(13, true)
                .create();
        return text;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(WebSocketResponse webSocketResponse) {
        switch (webSocketResponse.getCmd()) {
            //缩略图推送
            case WsCMD.PUSH_THUMB:
                Constant.lastB2BKlinePushTime = System.currentTimeMillis();
                try {
                    String json = "{\"date\":" + webSocketResponse.getResponse() + "}";
                    B2BThumbBean b2BThumbBean = App.gson.fromJson(json, B2BThumbBean.class);
                    for (B2BThumbBean.DateBean dateBean : b2BThumbBean.getDate()) {
                        if (allThumbResult.getSymbol().equals(dateBean.getSymbol())) {
                            allThumbResult.setVolume(dateBean.getVolume());
                            allThumbResult.setHigh(dateBean.getHigh());
                            allThumbResult.setChg(dateBean.getChg());
                            allThumbResult.setCnyLegalAsset(dateBean.getCnyLegalAsset());
                            allThumbResult.setLow(dateBean.getLow());
                            allThumbResult.setClose(dateBean.getClose());
                            allThumbResult.setTurnover(dateBean.getTurnover());
                            updateCoinPairPushBean();
                            break;
                        }
                    }
                } catch (Exception e) {
                    LogUtils.e("PUSH_THUMB", e.toString());
                }
                break;
            //K线推送
            case WsCMD.PUSH_KLINE:
                KlineSubscribeBean klineSubscribeBean = App.gson.fromJson(webSocketResponse.getResponse(), KlineSubscribeBean.class);
                if (!klineSubscribeBean.getPeriod().equals(period)) return;
                KLineEntity mKLineEntity = new KLineEntity();
                mKLineEntity.Date = DateUtils.formatKlineTime(type, klineSubscribeBean.getTime());
                mKLineEntity.Open = klineSubscribeBean.getOpenPrice();
                mKLineEntity.Close = klineSubscribeBean.getClosePrice();
                mKLineEntity.High = klineSubscribeBean.getHighestPrice();
                mKLineEntity.Low = klineSubscribeBean.getLowestPrice();
                mKLineEntity.Volume = klineSubscribeBean.getVolume();
                uc.klineSubscribe.setValue(mKLineEntity);
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            //K线历史数据
            case EvKey.klineHistory:
                if (eventBean.isStatue()) {
                    senKlineHistoryDate((String) eventBean.getObject());
                }
                break;
            //成交全币种缩略图
            case EvKey.klineThumbAll:
                synchronized (this) {
                    if (eventBean.isStatue()) {
                        for (AllThumbResult.DataBean dataBean : ((AllThumbResult) eventBean.getObject()).getData()) {
                            if (allThumbResult.getSymbol().equals(dataBean.getSymbol())) {
                                this.allThumbResult = dataBean;
                                updateCoinPairPushBean();
                                break;
                            }
                        }
                    }
                }
                break;
            //添加收藏
            case EvKey.favorAdd:
                dismissDialog();
                if (eventBean.isStatue()) {
                    List<String> tempList = SPUtils.getInstance().getFavorFindList();
                    if (!tempList.contains(allThumbResult.getSymbol())) {
                        tempList.add(allThumbResult.getSymbol());
                    }
                    SPUtils.getInstance().setFavorFindList(tempList);
                    LogUtils.e("setFavorFindList", SPUtils.getInstance().getFavorFindList().size());
                    isFavor.set(true);
                    Toasty.showSuccess(App.getInstance().getString(R.string.add_collection_success));
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //取消收藏
            case EvKey.favorDelete:
                dismissDialog();
                if (eventBean.isStatue()) {
                    List<String> tempList = SPUtils.getInstance().getFavorFindList();
                    if (tempList.contains(allThumbResult.getSymbol())) {
                        tempList.remove(allThumbResult.getSymbol());
                    }
                    SPUtils.getInstance().setFavorFindList(tempList);
                    LogUtils.e("setFavorFindList", SPUtils.getInstance().getFavorFindList().size());
                    isFavor.set(false);
                    Toasty.showSuccess(App.getInstance().getString(R.string.cancel_collection_success));
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            default:
                break;
        }
    }

    private void senKlineHistoryDate(String response) {
        try {
            mKLineEntities.clear();
            JSONObject object = new JSONObject(response);
            JSONArray jsonElements = object.getJSONArray("data");
            for (int i = 0, len = jsonElements.length(); i < len; i++) {
                JSONArray data = jsonElements.optJSONArray(i);
                KLineEntity mKLineEntity = new KLineEntity();
                mKLineEntity.Date = DateUtils.formatKlineTime(type, data.optLong(0));
                mKLineEntity.Open = (float) data.optDouble(1);
                mKLineEntity.Close = (float) data.optDouble(4);
                mKLineEntity.High = (float) data.optDouble(2);
                mKLineEntity.Low = (float) data.optDouble(3);
                mKLineEntity.Volume = (float) data.optDouble(5);
                mKLineEntities.add(mKLineEntity);
            }
            uc.klineHistoryList.setValue(mKLineEntities);

            //取消上一次Tab的订阅，订阅当前选中Tab
            if (!TextUtils.isEmpty(lastResolution)) {
                unSubscribeKline();
            }
            lastResolution = resolution;
            subscribeKline();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //实时更新
    private void updateCoinPairPushBean() {
        close.set(initClose());
        convert.set(initConvert());
        convertColor.set(isCoinPairPushChgUp());
        _24hourcount.set(init24HourCount());
        _24hourhigh.set(init24HourHigh());
        _24hourlow.set(init24HourLow());
    }

    // 涨幅是否为 +/-
    private boolean isCoinPairPushChgUp() {
        return allThumbResult.getChg() >= 0 ? true : false;
    }

    //现价
    private CharSequence initClose() {
        String close = DfUtils.formatNum(MathUtils.getRundNumber(allThumbResult.getClose(), allThumbResult.getBaseCoinScreenScale() == 0 ?
                2 : allThumbResult.getBaseCoinScreenScale(), null));
        if (!close.contains(".")) return close;
        CharSequence text = new SpanUtils()
                .append(close.split("\\.")[0])
                .append("." + close.split("\\.")[1])
                .setFontSize(18, true)
                .create();
        return text;
    }

    //换算
    private String initConvert() {
        return "≈" + MathUtils.getRundNumber(allThumbResult.getCnyLegalAsset(), 2, null)
                + Constant.CNY
                + "  (" + (isCoinPairPushChgUp() ? "+" : "")
                + MathUtils.getRundNumber(allThumbResult.getChg() * 100, 2, "########0.")
                + "%)";
    }

    //24H量
    private String init24HourCount() {
        return String.valueOf((int) allThumbResult.getVolume());
    }

    //24H最高
    private String init24HourHigh() {
        return DfUtils.numberFormatWithZero(allThumbResult.getHigh(), allThumbResult.getBaseCoinScreenScale());
    }

    //24H最低
    private String init24HourLow() {
        return DfUtils.numberFormatWithZero(allThumbResult.getLow(), allThumbResult.getBaseCoinScreenScale());
    }


    //订阅本次实时k线
    public void subscribeKline() {
        WebSocketRequest mWebSocketRequest = new WebSocketRequest();
        mWebSocketRequest.setCode(WsConstant.CODE_KLINE);
        mWebSocketRequest.setCmd(WsCMD.SUBSCRIBE_KLINE);
        mWebSocketRequest.setBody(App.gson.toJson(WsUtils.setKlineSubscribeJsonMap(allThumbResult.getSymbol(), WsConstant.SPOT, resolution)).getBytes());
        EventBusUtils.postEvent(mWebSocketRequest);
    }

    //取消订阅上次实时k线
    public void unSubscribeKline() {
        WebSocketRequest mWebSocketRequest = new WebSocketRequest();
        mWebSocketRequest.setCode(WsConstant.CODE_KLINE);
        mWebSocketRequest.setCmd(WsCMD.UN_SUBSCRIBE_KLINE);
        mWebSocketRequest.setBody(App.gson.toJson(WsUtils.setKlineSubscribeJsonMap(allThumbResult.getSymbol(), WsConstant.SPOT, lastResolution)).getBytes());
        EventBusUtils.postEvent(mWebSocketRequest);
    }

    //取消订阅深度
    public void unSubscribeDeal() {
        WebSocketRequest mWebSocketRequest = new WebSocketRequest();
        mWebSocketRequest.setCode(WsConstant.CODE_KLINE);
        mWebSocketRequest.setCmd(WsCMD.UN_SUBSCRIBE_DEAL);
        mWebSocketRequest.setBody(App.gson.toJson(WsUtils.setDealSubscribeJsonMap(allThumbResult.getSymbol())).getBytes());
        EventBusUtils.postEvent(mWebSocketRequest);
    }

    //取消订阅盘口
    private void unsubscribeB2BTradePlate() {
        WebSocketRequest mWebSocketRequest = new WebSocketRequest();
        mWebSocketRequest.setCode(WsConstant.CODE_MARKET);
        mWebSocketRequest.setCmd(WsCMD.UNSUBSCRIBE_SPOT_TRADE_PLATE);
        mWebSocketRequest.setBody(App.gson.toJson(WsUtils.setCFDMarketJsonMap(allThumbResult.getSymbol())).getBytes());
        EventBusUtils.postEvent(mWebSocketRequest);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Constant.isKineVisiable = true;
        EventBusUtils.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Constant.isKineVisiable = false;
        if (!StringUtils.isEmpty(Constant.currentSymbol)) {
            QuoteClient.getInstance().getMarketSymbolPolling(Constant.currentSymbol);
        }
        unSubscribeKline();
        unSubscribeDeal();
        if (Constant.isHttpAndWs && !Constant.lastB2BMarketPushSymbol.equals(allThumbResult.getSymbol())) {
            unsubscribeB2BTradePlate();
        }
        EventBusUtils.unRegister(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EasyHttp.clearCache();
    }
}
