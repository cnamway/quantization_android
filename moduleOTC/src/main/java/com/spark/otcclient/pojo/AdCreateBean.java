package com.spark.otcclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/31
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class AdCreateBean implements Parcelable {

    /**
     * advertiseType : 0
     * autoReply : 0
     * autoword : string
     * coinName : string
     * country : string
     * maxLimit : 0.1
     * minLimit : 0.1
     * number : 0.1
     * payMode : string
     * premiseRate : 0.1
     * price : 0.1
     * priceType : 0
     * remark : string
     * timeLimit : 0
     * tradeType : 0
     */

    private int advertiseType;
    private int autoReply;
    private String autoword;
    private String coinName;
    private String country;
    private double maxLimit;
    private double minLimit;
    private double number;
    private String payMode;
    private double premiseRate;
    private double price;
    private int priceType;
    private String remark;
    private int timeLimit;
    private int tradeType;
    private String payIds;

    public AdCreateBean() {
    }

    protected AdCreateBean(Parcel in) {
        advertiseType = in.readInt();
        autoReply = in.readInt();
        autoword = in.readString();
        coinName = in.readString();
        country = in.readString();
        maxLimit = in.readDouble();
        minLimit = in.readDouble();
        number = in.readDouble();
        payMode = in.readString();
        premiseRate = in.readDouble();
        price = in.readDouble();
        priceType = in.readInt();
        remark = in.readString();
        timeLimit = in.readInt();
        tradeType = in.readInt();
        payIds = in.readString();
    }

    public static final Creator<AdCreateBean> CREATOR = new Creator<AdCreateBean>() {
        @Override
        public AdCreateBean createFromParcel(Parcel in) {
            return new AdCreateBean(in);
        }

        @Override
        public AdCreateBean[] newArray(int size) {
            return new AdCreateBean[size];
        }
    };

    public int getAdvertiseType() {
        return advertiseType;
    }

    public void setAdvertiseType(int advertiseType) {
        this.advertiseType = advertiseType;
    }

    public int getAutoReply() {
        return autoReply;
    }

    public void setAutoReply(int autoReply) {
        this.autoReply = autoReply;
    }

    public String getAutoword() {
        return autoword;
    }

    public void setAutoword(String autoword) {
        this.autoword = autoword;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(double maxLimit) {
        this.maxLimit = maxLimit;
    }

    public double getMinLimit() {
        return minLimit;
    }

    public void setMinLimit(double minLimit) {
        this.minLimit = minLimit;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public double getPremiseRate() {
        return premiseRate;
    }

    public void setPremiseRate(double premiseRate) {
        this.premiseRate = premiseRate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPriceType() {
        return priceType;
    }

    public void setPriceType(int priceType) {
        this.priceType = priceType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getTradeType() {
        return tradeType;
    }

    public void setTradeType(int tradeType) {
        this.tradeType = tradeType;
    }

    public String getPayIds() {
        return payIds;
    }

    public void setPayIds(String payIds) {
        this.payIds = payIds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(advertiseType);
        dest.writeInt(autoReply);
        dest.writeString(autoword);
        dest.writeString(coinName);
        dest.writeString(country);
        dest.writeDouble(maxLimit);
        dest.writeDouble(minLimit);
        dest.writeDouble(number);
        dest.writeString(payMode);
        dest.writeDouble(premiseRate);
        dest.writeDouble(price);
        dest.writeInt(priceType);
        dest.writeString(remark);
        dest.writeInt(timeLimit);
        dest.writeInt(tradeType);
        dest.writeString(payIds);
    }
}
