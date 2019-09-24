package com.spark.modulespot.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/28
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class KlineControllerBean implements Parcelable {

    /**
     * currency : string
     * from : 0
     * key : string
     * memberId : string
     * period : string
     * resolution : string
     * service : string
     * size : 0
     * symbol : string
     * thumbTopic : string
     * to : 0
     * tradeKey : string
     */

    private String currency;  //法币币种
    private long from;        //开始时间
    private String key;
    private String memberId;  //用户标识
    private String period;
    private String resolution;//粒度
    private String service;   //服务类型
    private int size;         //数量
    private String symbol;    //交易对
    private String thumbTopic;//结束时间
    private long to;
    private String tradeKey;

    public KlineControllerBean() {
    }

    protected KlineControllerBean(Parcel in) {
        currency = in.readString();
        from = in.readLong();
        key = in.readString();
        memberId = in.readString();
        period = in.readString();
        resolution = in.readString();
        service = in.readString();
        size = in.readInt();
        symbol = in.readString();
        thumbTopic = in.readString();
        to = in.readLong();
        tradeKey = in.readString();
    }

    public static final Creator<KlineControllerBean> CREATOR = new Creator<KlineControllerBean>() {
        @Override
        public KlineControllerBean createFromParcel(Parcel in) {
            return new KlineControllerBean(in);
        }

        @Override
        public KlineControllerBean[] newArray(int size) {
            return new KlineControllerBean[size];
        }
    };

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getThumbTopic() {
        return thumbTopic;
    }

    public void setThumbTopic(String thumbTopic) {
        this.thumbTopic = thumbTopic;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }

    public String getTradeKey() {
        return tradeKey;
    }

    public void setTradeKey(String tradeKey) {
        this.tradeKey = tradeKey;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(currency);
        dest.writeLong(from);
        dest.writeString(key);
        dest.writeString(memberId);
        dest.writeString(period);
        dest.writeString(resolution);
        dest.writeString(service);
        dest.writeInt(size);
        dest.writeString(symbol);
        dest.writeString(thumbTopic);
        dest.writeLong(to);
        dest.writeString(tradeKey);
    }
}
