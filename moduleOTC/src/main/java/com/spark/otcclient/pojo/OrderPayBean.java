package com.spark.otcclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/17
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class OrderPayBean implements Parcelable{

    /**
     * actualPayment : string
     * orderSn : string
     */

    private String actualPayment;
    private String orderSn;

    public OrderPayBean() {
    }

    protected OrderPayBean(Parcel in) {
        actualPayment = in.readString();
        orderSn = in.readString();
    }

    public static final Creator<OrderPayBean> CREATOR = new Creator<OrderPayBean>() {
        @Override
        public OrderPayBean createFromParcel(Parcel in) {
            return new OrderPayBean(in);
        }

        @Override
        public OrderPayBean[] newArray(int size) {
            return new OrderPayBean[size];
        }
    };

    public String getActualPayment() {
        return actualPayment;
    }

    public void setActualPayment(String actualPayment) {
        this.actualPayment = actualPayment;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(actualPayment);
        dest.writeString(orderSn);
    }
}
