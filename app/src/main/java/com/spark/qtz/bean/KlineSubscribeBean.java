package com.spark.qtz.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/13
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class KlineSubscribeBean implements Parcelable{

    /**
     * closePrice : 5.942085
     * count : 58
     * empty : false
     * highestPrice : 5.942085
     * isPush : 0
     * lowestPrice : 5.941248
     * openPrice : 5.941248
     * period : h1
     * symbol : ETC/USDT
     * time : 1557730800000
     * turnover : 311.8634619406
     * volume : 52.4855
     */

    private float closePrice;
    private int count;
    private boolean empty;
    private float highestPrice;
    private int isPush;
    private float lowestPrice;
    private float openPrice;
    private String period;
    private String symbol;
    private long time;
    private float turnover;
    private float volume;


    protected KlineSubscribeBean(Parcel in) {
        closePrice = in.readFloat();
        count = in.readInt();
        empty = in.readByte() != 0;
        highestPrice = in.readFloat();
        isPush = in.readInt();
        lowestPrice = in.readFloat();
        openPrice = in.readFloat();
        period = in.readString();
        symbol = in.readString();
        time = in.readLong();
        turnover = in.readFloat();
        volume = in.readFloat();
    }

    public static final Creator<KlineSubscribeBean> CREATOR = new Creator<KlineSubscribeBean>() {
        @Override
        public KlineSubscribeBean createFromParcel(Parcel in) {
            return new KlineSubscribeBean(in);
        }

        @Override
        public KlineSubscribeBean[] newArray(int size) {
            return new KlineSubscribeBean[size];
        }
    };

    public float getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(float closePrice) {
        this.closePrice = closePrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public float getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(float highestPrice) {
        this.highestPrice = highestPrice;
    }

    public int getIsPush() {
        return isPush;
    }

    public void setIsPush(int isPush) {
        this.isPush = isPush;
    }

    public float getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(float lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public float getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(float openPrice) {
        this.openPrice = openPrice;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getTurnover() {
        return turnover;
    }

    public void setTurnover(float turnover) {
        this.turnover = turnover;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeFloat(closePrice);
        dest.writeInt(count);
        dest.writeByte((byte) (empty ? 1 : 0));
        dest.writeFloat(highestPrice);
        dest.writeInt(isPush);
        dest.writeFloat(lowestPrice);
        dest.writeFloat(openPrice);
        dest.writeString(period);
        dest.writeString(symbol);
        dest.writeLong(time);
        dest.writeFloat(turnover);
        dest.writeFloat(volume);
    }

    @Override
    public String toString() {
        return "KlineSubscribeBean{" +
                "closePrice=" + closePrice +
                ", count=" + count +
                ", empty=" + empty +
                ", highestPrice=" + highestPrice +
                ", isPush=" + isPush +
                ", lowestPrice=" + lowestPrice +
                ", openPrice=" + openPrice +
                ", period='" + period + '\'' +
                ", symbol='" + symbol + '\'' +
                ", time=" + time +
                ", turnover=" + turnover +
                ", volume=" + volume +
                '}';
    }
}
