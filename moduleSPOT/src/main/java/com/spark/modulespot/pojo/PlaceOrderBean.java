package com.spark.modulespot.pojo;


import android.os.Parcel;
import android.os.Parcelable;

public class PlaceOrderBean implements Parcelable {
//    {
//            "orderQty": 0,
//            "price": 0,
//            "priceType": 0,
//            "side": 0,
//            "symbol": "string",
//    }

    private String orderQty;
    private String price;
    private int priceType;
    private int side;
    private String symbol;

    public PlaceOrderBean() {
    }


    protected PlaceOrderBean(Parcel in) {
        orderQty = in.readString();
        price = in.readString();
        priceType = in.readInt();
        side = in.readInt();
        symbol = in.readString();
    }

    public static final Creator<PlaceOrderBean> CREATOR = new Creator<PlaceOrderBean>() {
        @Override
        public PlaceOrderBean createFromParcel(Parcel in) {
            return new PlaceOrderBean(in);
        }

        @Override
        public PlaceOrderBean[] newArray(int size) {
            return new PlaceOrderBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderQty);
        dest.writeString(price);
        dest.writeInt(priceType);
        dest.writeInt(side);
        dest.writeString(symbol);
    }

    public String getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(String orderQty) {
        this.orderQty = orderQty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getPriceType() {
        return priceType;
    }

    public void setPriceType(int priceType) {
        this.priceType = priceType;
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
