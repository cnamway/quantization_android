package com.spark.qtz.app.quotes.kline;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.spark.qtz.App;
import com.spark.qtz.api.pojo.CoinPairPushBean;
import com.spark.qtz.bean.KlineSubscribeBean;
import com.spark.qtz.util.DateUtils;
import com.spark.klinelib.KLineEntity;
import com.spark.wsclient.base.WsCMD;
import com.spark.wsclient.utils.WsConstant;
import com.spark.wsclient.utils.WsUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.bus.event.SingleLiveEvent;
import me.spark.mvvm.utils.DfUtils;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.MathUtils;
import me.spark.mvvm.utils.SpanUtils;
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
public class KlineViewModel extends BaseViewModel {
    private CoinPairPushBean mCoinPairPushBean;
    private String resolution, period;
    private List<KLineEntity> mKLineEntities = new ArrayList<>();

    private String lastResolution;//上次传入时间段
    private int type;

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

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<List<KLineEntity>> klineHistoryList = new SingleLiveEvent<>();
        public SingleLiveEvent<KLineEntity> klineSubscribe = new SingleLiveEvent<>();
    }

    public KlineViewModel(@NonNull Application application) {
        super(application);
    }

    public void initDate(CoinPairPushBean coinPairPushBean) {
        this.mCoinPairPushBean = coinPairPushBean;
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
                resolution = "1";
                period = "m1";
                break;
            //1分
            case 1:
                from = to - 12L * 60 * 60 * 1000;       // 前一天的数据
                resolution = "1";
                period = "m1";
                break;
            //5分
            case 2:
                from = to - 2 * 24L * 60 * 60 * 1000;   // 前2天的数据
                resolution = "5";
                period = "m5";
                break;
            //1小时
            case 3:
                from = to - 24 * 24L * 60 * 60 * 1000;  // 前24天数据
                resolution = "1H";
                period = "h1";
                break;
            //1天
            case 4:
                from = to - 60 * 24L * 60 * 60 * 1000;  // 前60天数据
                resolution = "1D";
                period = "d1";
                break;
            case 5: // 15分钟
                from = to - 6 * 24L * 60 * 60 * 1000;   // 前6天的数据
                resolution = "15";
                period = "m15";
                break;
            case 6: // 30分钟
                from = to - 10 * 24L * 60 * 60 * 1000;  // 前12 天数据
                resolution = "30";
                period = "m30";
                break;
            case 7: // 1周
                from = to - 730 * 24L * 60 * 60 * 1000;  // 前2年数据
                resolution = "1W";
                period = "w1";
                break;
            case 8: // 1月
                from = to - 1095 * 24L * 60 * 60 * 1000; // 前3年数据
                resolution = "1M";
                period = "M1";
                break;
            default: // 默认1分钟
                from = to - 24L * 60 * 60 * 1000;        // 前一天的数据
                resolution = "1";
                period = "m1";
                break;
        }
        //获取历史数据
        WebSocketRequest mWebSocketRequest = new WebSocketRequest();
        mWebSocketRequest.setCode(WsConstant.CODE_KLINE);
        mWebSocketRequest.setCmd(WsCMD.GET_KLINE_HISTORY);
        mWebSocketRequest.setBody(App.gson.toJson(WsUtils.setKlineHistoryJsonMap(mCoinPairPushBean.getSymbol(), from, to, resolution)).getBytes());
        EventBusUtils.postEvent(mWebSocketRequest);
    }

    //title
    public CharSequence initTitle() {
        CharSequence text = new SpanUtils()
                .append(mCoinPairPushBean.getSymbol().split("/")[0])
                .append(" / " + mCoinPairPushBean.getSymbol().split("/")[1])
                .setFontSize(13, true)
                .create();
        return text;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(WebSocketResponse webSocketResponse) {
        switch (webSocketResponse.getCmd()) {
            //首页缩略图接受
            case WsCMD.PUSH_THUMB:
                CoinPairPushBean coinPairPushBean = App.gson.fromJson(webSocketResponse.getResponse(), CoinPairPushBean.class);
                if ((!coinPairPushBean.getSymbol().endsWith(mCoinPairPushBean.getSymbol()))) return;
                this.mCoinPairPushBean = coinPairPushBean;
                updateCoinPairPushBean();
                break;
            //K线历史数据
            case WsCMD.GET_KLINE_HISTORY:
                senKlineHistoryDate(webSocketResponse.getResponse());
                break;
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
        return mCoinPairPushBean.getChg() > 0 ? true : false;
    }

    //现价
    private CharSequence initClose() {
        String close = DfUtils.formatNum(MathUtils.getRundNumber(mCoinPairPushBean.getClose(), mCoinPairPushBean.getBaseCoinScreenScale() == 0 ?
                2 : mCoinPairPushBean.getBaseCoinScreenScale(), null));
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
        return "≈" + MathUtils.getRundNumber(mCoinPairPushBean.getLegalAsset(), 2, null)
                + Constant.CNY
                + "  (" + (isCoinPairPushChgUp() ? "+" : "")
                + MathUtils.getRundNumber(mCoinPairPushBean.getChg() * 100, 2, "########0.")
                + "%)";
    }

    //24H量
    private String init24HourCount() {
        return String.valueOf((int) mCoinPairPushBean.getVolume());
    }

    //24H最高
    private String init24HourHigh() {
        return MathUtils.getRundNumber(mCoinPairPushBean.getHigh(), mCoinPairPushBean.getBaseCoinScreenScale(), null);
    }

    //24H最低
    private String init24HourLow() {
        return MathUtils.getRundNumber(mCoinPairPushBean.getLow(), mCoinPairPushBean.getBaseCoinScreenScale(), null);
    }


    //订阅本次实时k线
    public void subscribeKline() {
        WebSocketRequest mWebSocketRequest = new WebSocketRequest();
        mWebSocketRequest.setCode(WsConstant.CODE_KLINE);
        mWebSocketRequest.setCmd(WsCMD.SUBSCRIBE_KLINE);
        mWebSocketRequest.setBody(App.gson.toJson(WsUtils.setKlineSubscribeJsonMap(mCoinPairPushBean.getSymbol(),WsConstant.SPOT, resolution)).getBytes());
        EventBusUtils.postEvent(mWebSocketRequest);
    }

    //取消订阅上次实时k线
    public void unSubscribeKline() {
        WebSocketRequest mWebSocketRequest = new WebSocketRequest();
        mWebSocketRequest.setCode(WsConstant.CODE_KLINE);
        mWebSocketRequest.setCmd(WsCMD.UN_SUBSCRIBE_KLINE);
        mWebSocketRequest.setBody(App.gson.toJson(WsUtils.setKlineSubscribeJsonMap(mCoinPairPushBean.getSymbol(),WsConstant.SPOT, lastResolution)).getBytes());
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
        unSubscribeKline();
        EventBusUtils.unRegister(this);
    }
}
