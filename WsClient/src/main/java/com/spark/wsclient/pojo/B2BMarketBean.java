package com.spark.wsclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-08-14
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class B2BMarketBean implements Parcelable {

    /**
     * minAmount : 0.001071
     * highestPrice : 1140.8
     * symbol : SPOT-BTC/USDT
     * lowestPrice : 11406.87
     * maxAmount : 14.865886
     * items : [{"amount":0.0054,"price":11813.1}]
     * direction : 1
     */

    private double minAmount;
    private double highestPrice;
    private String symbol;
    private double lowestPrice;
    private double maxAmount;
    private int direction;
    private List<ItemsBean> items;

    protected B2BMarketBean(Parcel in) {
        minAmount = in.readDouble();
        highestPrice = in.readDouble();
        symbol = in.readString();
        lowestPrice = in.readDouble();
        maxAmount = in.readDouble();
        direction = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(minAmount);
        dest.writeDouble(highestPrice);
        dest.writeString(symbol);
        dest.writeDouble(lowestPrice);
        dest.writeDouble(maxAmount);
        dest.writeInt(direction);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<B2BMarketBean> CREATOR = new Creator<B2BMarketBean>() {
        @Override
        public B2BMarketBean createFromParcel(Parcel in) {
            return new B2BMarketBean(in);
        }

        @Override
        public B2BMarketBean[] newArray(int size) {
            return new B2BMarketBean[size];
        }
    };

    public double getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(double minAmount) {
        this.minAmount = minAmount;
    }

    public double getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(double highestPrice) {
        this.highestPrice = highestPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(double lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * amount : 0.0054
         * price : 11813.1
         */

        private double amount;
        private double price;

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }
}
