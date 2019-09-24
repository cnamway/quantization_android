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
public class CoinExtractSubmitBean implements Parcelable{
    private String address;
    private String tag;
    private String amount;
    private String coinName;
    private String serviceCharge;

    public CoinExtractSubmitBean() {
    }

    protected CoinExtractSubmitBean(Parcel in) {
        address = in.readString();
        tag = in.readString();
        amount = in.readString();
        coinName = in.readString();
        serviceCharge = in.readString();
    }

    public static final Creator<CoinExtractSubmitBean> CREATOR = new Creator<CoinExtractSubmitBean>() {
        @Override
        public CoinExtractSubmitBean createFromParcel(Parcel in) {
            return new CoinExtractSubmitBean(in);
        }

        @Override
        public CoinExtractSubmitBean[] newArray(int size) {
            return new CoinExtractSubmitBean[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(tag);
        dest.writeString(amount);
        dest.writeString(coinName);
        dest.writeString(serviceCharge);
    }
}
