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
public class CoinExtractBean implements Parcelable{

    /**
     * address : string
     * amount : 0
     * businessId : string
     * coinName : string
     * tradePassword : string
     */

    private String address;
    private double amount;
    private String businessId;
    private String coinName;
    private String tradePassword;

    public CoinExtractBean() {
    }

    protected CoinExtractBean(Parcel in) {
        address = in.readString();
        amount = in.readDouble();
        businessId = in.readString();
        coinName = in.readString();
        tradePassword = in.readString();
    }

    public static final Creator<CoinExtractBean> CREATOR = new Creator<CoinExtractBean>() {
        @Override
        public CoinExtractBean createFromParcel(Parcel in) {
            return new CoinExtractBean(in);
        }

        @Override
        public CoinExtractBean[] newArray(int size) {
            return new CoinExtractBean[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
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
        dest.writeString(address);
        dest.writeDouble(amount);
        dest.writeString(businessId);
        dest.writeString(coinName);
        dest.writeString(tradePassword);
    }
}
