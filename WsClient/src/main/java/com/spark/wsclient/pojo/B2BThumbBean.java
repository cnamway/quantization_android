package com.spark.wsclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import me.spark.mvvm.utils.DfUtils;
import me.spark.mvvm.utils.MathUtils;
import me.spark.mvvm.utils.SPUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-08-07
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class B2BThumbBean implements Parcelable {

    private List<DateBean> date;

    protected B2BThumbBean(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<B2BThumbBean> CREATOR = new Creator<B2BThumbBean>() {
        @Override
        public B2BThumbBean createFromParcel(Parcel in) {
            return new B2BThumbBean(in);
        }

        @Override
        public B2BThumbBean[] newArray(int size) {
            return new B2BThumbBean[size];
        }
    };

    public List<DateBean> getDate() {
        return date;
    }

    public void setDate(List<DateBean> date) {
        this.date = date;
    }

    public static class DateBean {
        /**
         * volume : 5628.452563
         * symbol : BTC/USDT
         * high : 11782.21
         * chg : -0.0074
         * cnyLegalAsset : 80956.2556
         * low : 11535.12
         * close : 11638.21
         * turnover : 6.565252889202841E7
         */

        private double volume;
        private String symbol;
        private double high;
        private double chg;
        private double cnyLegalAsset;
        private double low;
        private double close;
        private double turnover;

        public double getVolume() {
            return volume;
        }

        public void setVolume(double volume) {
            this.volume = volume;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public double getHigh() {
            return high;
        }

        public void setHigh(double high) {
            this.high = high;
        }

        public double getChg() {
            return chg;
        }

        public void setChg(double chg) {
            this.chg = chg;
        }

        public double getCnyLegalAsset() {
            return cnyLegalAsset;
        }

        public void setCnyLegalAsset(double cnyLegalAsset) {
            this.cnyLegalAsset = cnyLegalAsset;
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

        public double getTurnover() {
            return turnover;
        }

        public void setTurnover(double turnover) {
            this.turnover = turnover;
        }

        public CharSequence initSymbol() {
            return symbol.split("/")[0];
        }

        //现价
        public CharSequence initClose() {
            return DfUtils.formatNum(MathUtils.getRundNumber(close, 2, null));
        }

        // 涨幅是否为 +/-
        public boolean isCoinPairPushChgUp() {
            return SPUtils.getInstance().getGainMode() ? chg >= 0 : chg < 0;
        }
    }
}
