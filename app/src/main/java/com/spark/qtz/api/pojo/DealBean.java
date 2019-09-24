package com.spark.qtz.api.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-08-01
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class DealBean implements Parcelable {

    private List<DateBean> date;

    protected DealBean(Parcel in) {
    }

    public static final Creator<DealBean> CREATOR = new Creator<DealBean>() {
        @Override
        public DealBean createFromParcel(Parcel in) {
            return new DealBean(in);
        }

        @Override
        public DealBean[] newArray(int size) {
            return new DealBean[size];
        }
    };

    public List<DateBean> getDate() {
        return date;
    }

    public void setDate(List<DateBean> date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    public static class DateBean {
        /**
         * amount : 0.006372
         * buyOrderId : E18177703267205356672
         * buyTurnover : 63.86744808
         * completedTime : 1564668240444
         * price : 10023.14
         * sellOrderId : E18119893227998326056
         * sellTurnover : 63.86744808
         * side : 0
         * symbol : BTC/USDT
         */

        private double amount;
        private String buyOrderId;
        private double buyTurnover;
        private long completedTime;
        private double price;
        private String sellOrderId;
        private double sellTurnover;
        private int side;
        private String symbol;

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getBuyOrderId() {
            return buyOrderId;
        }

        public void setBuyOrderId(String buyOrderId) {
            this.buyOrderId = buyOrderId;
        }

        public double getBuyTurnover() {
            return buyTurnover;
        }

        public void setBuyTurnover(double buyTurnover) {
            this.buyTurnover = buyTurnover;
        }

        public long getCompletedTime() {
            return completedTime;
        }

        public void setCompletedTime(long completedTime) {
            this.completedTime = completedTime;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getSellOrderId() {
            return sellOrderId;
        }

        public void setSellOrderId(String sellOrderId) {
            this.sellOrderId = sellOrderId;
        }

        public double getSellTurnover() {
            return sellTurnover;
        }

        public void setSellTurnover(double sellTurnover) {
            this.sellTurnover = sellTurnover;
        }

        public int getSide() {
            return side;
        }

        public void setSide(int side) {
            this.side = side;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }
    }
}
