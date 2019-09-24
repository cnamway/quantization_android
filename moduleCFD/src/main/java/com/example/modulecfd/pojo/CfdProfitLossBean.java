package com.example.modulecfd.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-07-01
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CfdProfitLossBean implements Parcelable {

    /**
     * orderId : string
     * positionId : string
     * stopLossPrice : 0.21
     * stopProfitPrice : 0.21
     */

    private String orderId;
    private String positionId;
    private double stopLossPrice;
    private double stopProfitPrice;

    public CfdProfitLossBean() {
    }

    protected CfdProfitLossBean(Parcel in) {
        orderId = in.readString();
        positionId = in.readString();
        stopLossPrice = in.readDouble();
        stopProfitPrice = in.readDouble();
    }

    public static final Creator<CfdProfitLossBean> CREATOR = new Creator<CfdProfitLossBean>() {
        @Override
        public CfdProfitLossBean createFromParcel(Parcel in) {
            return new CfdProfitLossBean(in);
        }

        @Override
        public CfdProfitLossBean[] newArray(int size) {
            return new CfdProfitLossBean[size];
        }
    };

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public double getStopLossPrice() {
        return stopLossPrice;
    }

    public void setStopLossPrice(double stopLossPrice) {
        this.stopLossPrice = stopLossPrice;
    }

    public double getStopProfitPrice() {
        return stopProfitPrice;
    }

    public void setStopProfitPrice(double stopProfitPrice) {
        this.stopProfitPrice = stopProfitPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(orderId);
        parcel.writeString(positionId);
        parcel.writeDouble(stopLossPrice);
        parcel.writeDouble(stopProfitPrice);
    }
}
