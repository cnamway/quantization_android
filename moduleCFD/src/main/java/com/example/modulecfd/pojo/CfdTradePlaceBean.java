package com.example.modulecfd.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-06-27
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CfdTradePlaceBean implements Parcelable {

    /**
     * baseCoinScale : 0
     * cancelTime : 0
     * closeFee : 0.21
     * completeTime : 0
     * cost : 0.21
     * createTime : 0
     * id : string
     * ip : string
     * isSimulate : 0
     * marginFee : 0.21
     * marginLever : 0
     * memberId : 0
     * multiplier : 0
     * openFee : 0.2
     * orderType : 0
     * price : 0.21
     * priceType : 0
     * side : 0
     * status : 0
     * stopLossPrice : 0.2
     * stopProfitPrice : 0.21
     * symbol : string
     * type : 0
     * unit : string
     * volume : 0
     * walletKey : string
     */

    private int baseCoinScale;
    private int cancelTime;
    private double closeFee;
    private int completeTime;
    private double cost;
    private int createTime;
    private String id;
    private String ip;
    private int isSimulate;
    private double marginFee;
    private int marginLever;
    private int memberId;
    private int multiplier;
    private double openFee;
    private int orderType;
    private double price;
    private int priceType;
    private int side;
    private int status;
    private double stopLossPrice;
    private double stopProfitPrice;
    private String symbol;
    private int type;
    private String unit;
    private int volume;
    private String walletKey;

    public CfdTradePlaceBean() {
    }

    protected CfdTradePlaceBean(Parcel in) {
        baseCoinScale = in.readInt();
        cancelTime = in.readInt();
        closeFee = in.readDouble();
        completeTime = in.readInt();
        cost = in.readDouble();
        createTime = in.readInt();
        id = in.readString();
        ip = in.readString();
        isSimulate = in.readInt();
        marginFee = in.readDouble();
        marginLever = in.readInt();
        memberId = in.readInt();
        multiplier = in.readInt();
        openFee = in.readDouble();
        orderType = in.readInt();
        price = in.readDouble();
        priceType = in.readInt();
        side = in.readInt();
        status = in.readInt();
        stopLossPrice = in.readDouble();
        stopProfitPrice = in.readDouble();
        symbol = in.readString();
        type = in.readInt();
        unit = in.readString();
        volume = in.readInt();
        walletKey = in.readString();
    }

    public static final Creator<CfdTradePlaceBean> CREATOR = new Creator<CfdTradePlaceBean>() {
        @Override
        public CfdTradePlaceBean createFromParcel(Parcel in) {
            return new CfdTradePlaceBean(in);
        }

        @Override
        public CfdTradePlaceBean[] newArray(int size) {
            return new CfdTradePlaceBean[size];
        }
    };

    public int getBaseCoinScale() {
        return baseCoinScale;
    }

    public void setBaseCoinScale(int baseCoinScale) {
        this.baseCoinScale = baseCoinScale;
    }

    public int getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(int cancelTime) {
        this.cancelTime = cancelTime;
    }

    public double getCloseFee() {
        return closeFee;
    }

    public void setCloseFee(double closeFee) {
        this.closeFee = closeFee;
    }

    public int getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(int completeTime) {
        this.completeTime = completeTime;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getIsSimulate() {
        return isSimulate;
    }

    public void setIsSimulate(int isSimulate) {
        this.isSimulate = isSimulate;
    }

    public double getMarginFee() {
        return marginFee;
    }

    public void setMarginFee(double marginFee) {
        this.marginFee = marginFee;
    }

    public int getMarginLever() {
        return marginLever;
    }

    public void setMarginLever(int marginLever) {
        this.marginLever = marginLever;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public double getOpenFee() {
        return openFee;
    }

    public void setOpenFee(double openFee) {
        this.openFee = openFee;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getWalletKey() {
        return walletKey;
    }

    public void setWalletKey(String walletKey) {
        this.walletKey = walletKey;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(baseCoinScale);
        parcel.writeInt(cancelTime);
        parcel.writeDouble(closeFee);
        parcel.writeInt(completeTime);
        parcel.writeDouble(cost);
        parcel.writeInt(createTime);
        parcel.writeString(id);
        parcel.writeString(ip);
        parcel.writeInt(isSimulate);
        parcel.writeDouble(marginFee);
        parcel.writeInt(marginLever);
        parcel.writeInt(memberId);
        parcel.writeInt(multiplier);
        parcel.writeDouble(openFee);
        parcel.writeInt(orderType);
        parcel.writeDouble(price);
        parcel.writeInt(priceType);
        parcel.writeInt(side);
        parcel.writeInt(status);
        parcel.writeDouble(stopLossPrice);
        parcel.writeDouble(stopProfitPrice);
        parcel.writeString(symbol);
        parcel.writeInt(type);
        parcel.writeString(unit);
        parcel.writeInt(volume);
        parcel.writeString(walletKey);
    }
}
