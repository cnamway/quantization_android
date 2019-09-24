package com.spark.qtz.api.pojo;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import com.spark.qtz.App;
import com.spark.qtz.R;

import me.spark.mvvm.base.Constant;
import me.spark.mvvm.utils.MathUtils;
import me.spark.mvvm.utils.SpanUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/7
 * 描    述：币对推送Bean
 * 修订历史：
 * ================================================
 */
public class CoinPairPushBean implements Parcelable {
    /**
     * symbol : BTC/USDT
     * open : 9311.9657
     * high : 9314.6985
     * low : 9314.6725
     * close : 9314.6725
     * chg : 2.0E-4
     * change : 2.7068
     * volume : 0.004
     * turnover : 37.25850516
     * lastDayClose : 0.1
     * startTime : 1561046400000
     * baseCoinScale : 8
     * baseCoinScreenScale : 4
     * coinScale : 8
     * coinScreenScale : 4
     * indexPrice : 0.1
     * tagPrice : 0.1
     * fundRates : 1
     * legalAsset : 9702.36
     * baseCoin : USDT
     * tradeCoin : BTC
     */

    private String symbol;
    private double open;
    private double high;
    private double low;
    private double close;
    private double chg;
    private double change;
    private double volume;
    private double turnover;
    private double lastDayClose;
    private long startTime;
    private int baseCoinScale;
    private int baseCoinScreenScale;
    private int coinScale;
    private int coinScreenScale;
    private double indexPrice;
    private double tagPrice;
    private int fundRates;
    private double legalAsset;
    private String baseCoin;
    private String tradeCoin;

    protected CoinPairPushBean(Parcel in) {
        symbol = in.readString();
        open = in.readDouble();
        high = in.readDouble();
        low = in.readDouble();
        close = in.readDouble();
        chg = in.readDouble();
        change = in.readDouble();
        volume = in.readDouble();
        turnover = in.readDouble();
        lastDayClose = in.readDouble();
        startTime = in.readLong();
        baseCoinScale = in.readInt();
        baseCoinScreenScale = in.readInt();
        coinScale = in.readInt();
        coinScreenScale = in.readInt();
        indexPrice = in.readDouble();
        tagPrice = in.readDouble();
        fundRates = in.readInt();
        legalAsset = in.readDouble();
        baseCoin = in.readString();
        tradeCoin = in.readString();
    }

    public static final Creator<CoinPairPushBean> CREATOR = new Creator<CoinPairPushBean>() {
        @Override
        public CoinPairPushBean createFromParcel(Parcel in) {
            return new CoinPairPushBean(in);
        }

        @Override
        public CoinPairPushBean[] newArray(int size) {
            return new CoinPairPushBean[size];
        }
    };

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getChg() {
        return chg;
    }

    public void setChg(double chg) {
        this.chg = chg;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getTurnover() {
        return turnover;
    }

    public void setTurnover(double turnover) {
        this.turnover = turnover;
    }

    public double getLastDayClose() {
        return lastDayClose;
    }

    public void setLastDayClose(double lastDayClose) {
        this.lastDayClose = lastDayClose;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getBaseCoinScale() {
        return baseCoinScale;
    }

    public void setBaseCoinScale(int baseCoinScale) {
        this.baseCoinScale = baseCoinScale;
    }

    public int getBaseCoinScreenScale() {
        return baseCoinScreenScale;
    }

    public void setBaseCoinScreenScale(int baseCoinScreenScale) {
        this.baseCoinScreenScale = baseCoinScreenScale;
    }

    public int getCoinScale() {
        return coinScale;
    }

    public void setCoinScale(int coinScale) {
        this.coinScale = coinScale;
    }

    public int getCoinScreenScale() {
        return coinScreenScale;
    }

    public void setCoinScreenScale(int coinScreenScale) {
        this.coinScreenScale = coinScreenScale;
    }

    public double getIndexPrice() {
        return indexPrice;
    }

    public void setIndexPrice(double indexPrice) {
        this.indexPrice = indexPrice;
    }

    public double getTagPrice() {
        return tagPrice;
    }

    public void setTagPrice(double tagPrice) {
        this.tagPrice = tagPrice;
    }

    public int getFundRates() {
        return fundRates;
    }

    public void setFundRates(int fundRates) {
        this.fundRates = fundRates;
    }

    public double getLegalAsset() {
        return legalAsset;
    }

    public void setLegalAsset(double legalAsset) {
        this.legalAsset = legalAsset;
    }

    public String getBaseCoin() {
        return baseCoin;
    }

    public void setBaseCoin(String baseCoin) {
        this.baseCoin = baseCoin;
    }

    public String getTradeCoin() {
        return tradeCoin;
    }

    public void setTradeCoin(String tradeCoin) {
        this.tradeCoin = tradeCoin;
    }


    //    /**
//     * baseCoinScale : 8
//     * baseUsdRate : 1
//     * change : -0.00182239
//     * chg : -0.0018
//     * close : 0.98917761
//     * coinScale : 8
//     * high : 0.9926984
//     * lastDayClose : 0.991
//     * low : 0.989
//     * open : 0.991
//     * startTime : 1557158400000
//     * symbol : USDT/CSS
//     * turnover : 1151420.7176991173
//     * usdRate : 0.98917761
//     * volume : 1162643.24834853
//     */
//
//    private int baseCoinScale;
//    private double baseUsdRate;
//    private double change;              // 改变量24h
//    private double chg;                 // 涨幅
//    private double close;               // 现价
//    private int coinScale;
//    private double high;                // 最高价
//    private double lastDayClose;        // 昨日收盘价
//    private double low;                 // 最低价
//    private double open;                // 开盘价
//    private long startTime;
//    private String symbol;              // 币种
//    private double turnover;            // 交易额
//    private double usdRate;
//    private double volume;              // 交易量
//    private int coinScreenScale;
//    private int baseCoinScreenScale;
//
//    protected CoinPairPushBean(Parcel in) {
//        baseCoinScale = in.readInt();
//        baseUsdRate = in.readDouble();
//        change = in.readDouble();
//        chg = in.readDouble();
//        close = in.readDouble();
//        coinScale = in.readInt();
//        high = in.readDouble();
//        lastDayClose = in.readDouble();
//        low = in.readDouble();
//        open = in.readDouble();
//        startTime = in.readLong();
//        symbol = in.readString();
//        turnover = in.readDouble();
//        usdRate = in.readDouble();
//        volume = in.readDouble();
//        coinScreenScale = in.readInt();
//        baseCoinScreenScale = in.readInt();
//    }
//
//    public static final Creator<CoinPairPushBean> CREATOR = new Creator<CoinPairPushBean>() {
//        @Override
//        public CoinPairPushBean createFromParcel(Parcel in) {
//            return new CoinPairPushBean(in);
//        }
//
//        @Override
//        public CoinPairPushBean[] newArray(int size) {
//            return new CoinPairPushBean[size];
//        }
//    };
//
//    @Override
//    public boolean equals(Object o) {
//        if (o instanceof CoinPairPushBean) {
//            CoinPairPushBean coinPairPushBean = (CoinPairPushBean) o;
//            return this.symbol.equals(coinPairPushBean.symbol);
//        }
//        return super.equals(o);
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(baseCoinScale);
//        dest.writeDouble(baseUsdRate);
//        dest.writeDouble(change);
//        dest.writeDouble(chg);
//        dest.writeDouble(close);
//        dest.writeInt(coinScale);
//        dest.writeDouble(high);
//        dest.writeDouble(lastDayClose);
//        dest.writeDouble(low);
//        dest.writeDouble(open);
//        dest.writeLong(startTime);
//        dest.writeString(symbol);
//        dest.writeDouble(turnover);
//        dest.writeDouble(usdRate);
//        dest.writeDouble(volume);
//        dest.writeInt(coinScreenScale);
//        dest.writeInt(baseCoinScreenScale);
//    }
//
//    public int getBaseCoinScale() {
//        return baseCoinScale;
//    }
//
//    public void setBaseCoinScale(int baseCoinScale) {
//        this.baseCoinScale = baseCoinScale;
//    }
//
//    public double getBaseUsdRate() {
//        return baseUsdRate;
//    }
//
//    public void setBaseUsdRate(double baseUsdRate) {
//        this.baseUsdRate = baseUsdRate;
//    }
//
//    public double getChange() {
//        return change;
//    }
//
//    public void setChange(double change) {
//        this.change = change;
//    }
//
//    public double getChg() {
//        return chg;
//    }
//
//    public void setChg(double chg) {
//        this.chg = chg;
//    }
//
//    public double getClose() {
//        return close;
//    }
//
//    public void setClose(double close) {
//        this.close = close;
//    }
//
//    public int getCoinScale() {
//        return coinScale;
//    }
//
//    public void setCoinScale(int coinScale) {
//        this.coinScale = coinScale;
//    }
//
//    public double getHigh() {
//        return high;
//    }
//
//    public void setHigh(double high) {
//        this.high = high;
//    }
//
//    public double getLastDayClose() {
//        return lastDayClose;
//    }
//
//    public void setLastDayClose(double lastDayClose) {
//        this.lastDayClose = lastDayClose;
//    }
//
//    public double getLow() {
//        return low;
//    }
//
//    public void setLow(double low) {
//        this.low = low;
//    }
//
//    public double getOpen() {
//        return open;
//    }
//
//    public void setOpen(double open) {
//        this.open = open;
//    }
//
//    public long getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(long startTime) {
//        this.startTime = startTime;
//    }
//
//    public String getSymbol() {
//        return symbol;
//    }
//
//    public void setSymbol(String symbol) {
//        this.symbol = symbol;
//    }
//
//    public double getTurnover() {
//        return turnover;
//    }
//
//    public void setTurnover(double turnover) {
//        this.turnover = turnover;
//    }
//
//    public double getUsdRate() {
//        return usdRate;
//    }
//
//    public void setUsdRate(double usdRate) {
//        this.usdRate = usdRate;
//    }
//
//    public double getVolume() {
//        return volume;
//    }
//
//    public void setVolume(double volume) {
//        this.volume = volume;
//    }
//
//    public int getCoinScreenScale() {
//        return coinScreenScale;
//    }
//
//    public void setCoinScreenScale(int coinScreenScale) {
//        this.coinScreenScale = coinScreenScale;
//    }
//
//    public int getBaseCoinScreenScale() {
//        return baseCoinScreenScale;
//    }
//
//    public void setBaseCoinScreenScale(int baseCoinScreenScale) {
//        this.baseCoinScreenScale = baseCoinScreenScale;
//    }


    //--databinding
    //--QuotesVPViewModel
    // 涨幅是否为 +/-
    public boolean isCoinPairPushChgUp() {
        return chg > 0 ? true : false;
    }

    //
//    public String initConvert() {
//        return MathUtils.getRundNumber((baseUsdRate == 0 ? 1 : baseUsdRate) * close * Constant.rate, 2, null)
//                + WsConstant.CNY;
//    }
    public String initConvert() {
        return MathUtils.getRundNumber(legalAsset, 2, null)
                + Constant.CNY;
    }

    //现价
    public String initClose() {
        return MathUtils.getRundNumber(close, baseCoinScreenScale == 0 ? 2 : baseCoinScreenScale, null);
    }

    public String init24HourCount() {
        return App.getInstance().getString(R.string._24h_size) + (int) volume;
    }

    //币种
    public CharSequence initSymbol() {
        CharSequence text = new SpanUtils()
                .append(symbol.split("/")[0]).setBold()
                .append(" / " + symbol.split("/")[1])
                .setFontSize(12, true)
                .setForegroundColor(Color.parseColor("#718495"))
                .create();
        return text;
    }

    public String initChg() {
        return (isCoinPairPushChgUp() ? "+" : "") + MathUtils.getRundNumber(chg * 100, 2, "########0.") + "%";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(symbol);
        dest.writeDouble(open);
        dest.writeDouble(high);
        dest.writeDouble(low);
        dest.writeDouble(close);
        dest.writeDouble(chg);
        dest.writeDouble(change);
        dest.writeDouble(volume);
        dest.writeDouble(turnover);
        dest.writeDouble(lastDayClose);
        dest.writeLong(startTime);
        dest.writeInt(baseCoinScale);
        dest.writeInt(baseCoinScreenScale);
        dest.writeInt(coinScale);
        dest.writeInt(coinScreenScale);
        dest.writeDouble(indexPrice);
        dest.writeDouble(tagPrice);
        dest.writeInt(fundRates);
        dest.writeDouble(legalAsset);
        dest.writeString(baseCoin);
        dest.writeString(tradeCoin);
    }
    //--databinding

}
