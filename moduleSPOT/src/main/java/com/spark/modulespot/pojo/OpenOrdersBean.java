package com.spark.modulespot.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/4
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class OpenOrdersBean implements Parcelable {

//    /**
//     * apiKey : string
//     * memberId : 0
//     * orderId : string
//     * pageNo : 0
//     * pageSize : 0
//     * side : 0
//     * sign : string
//     * status : string
//     * symbol : string
//     * ts : 0
//     */
//
//    private String apiKey;
//    private int memberId;
//    private String orderId;
//    private int pageNo;
//    private int pageSize;
//    private int side;
//    private String sign;
//    private String status;
//    private String symbol;
//    private int ts;
//
//    public OpenOrdersBean() {
//    }
//
//    protected OpenOrdersBean(Parcel in) {
//        apiKey = in.readString();
//        memberId = in.readInt();
//        orderId = in.readString();
//        pageNo = in.readInt();
//        pageSize = in.readInt();
//        side = in.readInt();
//        sign = in.readString();
//        status = in.readString();
//        symbol = in.readString();
//        ts = in.readInt();
//    }
//
//    public static final Creator<OpenOrdersBean> CREATOR = new Creator<OpenOrdersBean>() {
//        @Override
//        public OpenOrdersBean createFromParcel(Parcel in) {
//            return new OpenOrdersBean(in);
//        }
//
//        @Override
//        public OpenOrdersBean[] newArray(int size) {
//            return new OpenOrdersBean[size];
//        }
//    };
//
//    public String getApiKey() {
//        return apiKey;
//    }
//
//    public void setApiKey(String apiKey) {
//        this.apiKey = apiKey;
//    }
//
//    public int getMemberId() {
//        return memberId;
//    }
//
//    public void setMemberId(int memberId) {
//        this.memberId = memberId;
//    }
//
//    public String getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(String orderId) {
//        this.orderId = orderId;
//    }
//
//    public int getPageNo() {
//        return pageNo;
//    }
//
//    public void setPageNo(int pageNo) {
//        this.pageNo = pageNo;
//    }
//
//    public int getPageSize() {
//        return pageSize;
//    }
//
//    public void setPageSize(int pageSize) {
//        this.pageSize = pageSize;
//    }
//
//    public int getSide() {
//        return side;
//    }
//
//    public void setSide(int side) {
//        this.side = side;
//    }
//
//    public String getSign() {
//        return sign;
//    }
//
//    public void setSign(String sign) {
//        this.sign = sign;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getSymbol() {
//        return symbol;
//    }
//
//    public void setSymbol(String symbol) {
//        this.symbol = symbol;
//    }
//
//    public int getTs() {
//        return ts;
//    }
//
//    public void setTs(int ts) {
//        this.ts = ts;
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(apiKey);
//        dest.writeInt(memberId);
//        dest.writeString(orderId);
//        dest.writeInt(pageNo);
//        dest.writeInt(pageSize);
//        dest.writeInt(side);
//        dest.writeString(sign);
//        dest.writeString(status);
//        dest.writeString(symbol);
//        dest.writeInt(ts);
//    }

    private String apiKey;
    private String memberId;
    private String orderId;
    private int pageNo;
    private int pageSize;
    private String side;
    private String sign;
    private String status;
    private String symbol;
    private String ts;

    public OpenOrdersBean() {
    }

    protected OpenOrdersBean(Parcel in) {
        apiKey = in.readString();
        memberId = in.readString();
        orderId = in.readString();
        pageNo = in.readInt();
        pageSize = in.readInt();
        side = in.readString();
        sign = in.readString();
        status = in.readString();
        symbol = in.readString();
        ts = in.readString();
    }

    public static final Creator<OpenOrdersBean> CREATOR = new Creator<OpenOrdersBean>() {
        @Override
        public OpenOrdersBean createFromParcel(Parcel in) {
            return new OpenOrdersBean(in);
        }

        @Override
        public OpenOrdersBean[] newArray(int size) {
            return new OpenOrdersBean[size];
        }
    };

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(apiKey);
        dest.writeString(memberId);
        dest.writeString(orderId);
        dest.writeInt(pageNo);
        dest.writeInt(pageSize);
        dest.writeString(side);
        dest.writeString(sign);
        dest.writeString(status);
        dest.writeString(symbol);
        dest.writeString(ts);
    }
}
