package com.spark.otcclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/11
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class OrderCreateBean implements Parcelable{

    /**
     * advertiseId : 0
     * money : 0.1
     * number : 0.1
     * orderType : string
     * price : 0.1
     * remark : string
     * tradePwd : string
     * tradeType : 0
     */

    private int advertiseId;
    private double money;
    private double number;
    private String orderType;
    private double price;
    private String remark;
    private String tradePwd;
    private int tradeType;

    public OrderCreateBean() {
    }

    protected OrderCreateBean(Parcel in) {
        advertiseId = in.readInt();
        money = in.readDouble();
        number = in.readDouble();
        orderType = in.readString();
        price = in.readDouble();
        remark = in.readString();
        tradePwd = in.readString();
        tradeType = in.readInt();
    }

    public static final Creator<OrderCreateBean> CREATOR = new Creator<OrderCreateBean>() {
        @Override
        public OrderCreateBean createFromParcel(Parcel in) {
            return new OrderCreateBean(in);
        }

        @Override
        public OrderCreateBean[] newArray(int size) {
            return new OrderCreateBean[size];
        }
    };

    public int getAdvertiseId() {
        return advertiseId;
    }

    public void setAdvertiseId(int advertiseId) {
        this.advertiseId = advertiseId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTradePwd() {
        return tradePwd;
    }

    public void setTradePwd(String tradePwd) {
        this.tradePwd = tradePwd;
    }

    public int getTradeType() {
        return tradeType;
    }

    public void setTradeType(int tradeType) {
        this.tradeType = tradeType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(advertiseId);
        dest.writeDouble(money);
        dest.writeDouble(number);
        dest.writeString(orderType);
        dest.writeDouble(price);
        dest.writeString(remark);
        dest.writeString(tradePwd);
        dest.writeInt(tradeType);
    }


    @Override
    public String toString() {
        return "OrderCreateBean{" +
                "advertiseId=" + advertiseId +
                ", money=" + money +
                ", number=" + number +
                ", orderType='" + orderType + '\'' +
                ", price=" + price +
                ", remark='" + remark + '\'' +
                ", tradePwd='" + tradePwd + '\'' +
                ", tradeType=" + tradeType +
                '}';
    }
}
