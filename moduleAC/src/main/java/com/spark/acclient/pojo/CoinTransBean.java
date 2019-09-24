package com.spark.acclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/10
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CoinTransBean implements Parcelable{

    /**
     * amount : 0.212
     * coinName : string
     * from : SPOT
     * to : SPOT
     * tradePassword : string
     */

    private double amount;
    private String coinName;
    private String from;
    private String to;
    private String tradePassword;


    public CoinTransBean() {
    }

    protected CoinTransBean(Parcel in) {
        amount = in.readDouble();
        coinName = in.readString();
        from = in.readString();
        to = in.readString();
        tradePassword = in.readString();
    }

    public static final Creator<CoinTransBean> CREATOR = new Creator<CoinTransBean>() {
        @Override
        public CoinTransBean createFromParcel(Parcel in) {
            return new CoinTransBean(in);
        }

        @Override
        public CoinTransBean[] newArray(int size) {
            return new CoinTransBean[size];
        }
    };

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTradePassword() {
        return tradePassword;
    }

    public void setTradePassword(String tradePassword) {
        this.tradePassword = tradePassword;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(amount);
        dest.writeString(coinName);
        dest.writeString(from);
        dest.writeString(to);
        dest.writeString(tradePassword);
    }
}
