package com.spark.qtz.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/19
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class LcAdFilterBean implements Parcelable{
    private String payMode;
    private String country;
    private String minLimit;
    private String maxLimit;

    public LcAdFilterBean(String payMode, String country, String minLimit, String maxLimit) {
        this.payMode = payMode;
        this.country = country;
        this.minLimit = minLimit;
        this.maxLimit = maxLimit;
    }

    protected LcAdFilterBean(Parcel in) {
        payMode = in.readString();
        country = in.readString();
        minLimit = in.readString();
        maxLimit = in.readString();
    }

    public static final Creator<LcAdFilterBean> CREATOR = new Creator<LcAdFilterBean>() {
        @Override
        public LcAdFilterBean createFromParcel(Parcel in) {
            return new LcAdFilterBean(in);
        }

        @Override
        public LcAdFilterBean[] newArray(int size) {
            return new LcAdFilterBean[size];
        }
    };

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMinLimit() {
        return minLimit;
    }

    public void setMinLimit(String minLimit) {
        this.minLimit = minLimit;
    }

    public String getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(String maxLimit) {
        this.maxLimit = maxLimit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(payMode);
        dest.writeString(country);
        dest.writeString(minLimit);
        dest.writeString(maxLimit);
    }
}
