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
public class CfdOrderPostBean implements Parcelable {

    /**
     * account : string
     * closeType : 0
     * endTime : string
     * hisType : 0
     * id : string
     * isSimulate : 0
     * memberId : 0
     * memberInfo : string
     * orderId : string
     * orderType : 0
     * pageNo : 0
     * pageSize : 0
     * priceType : 0
     * side : 0
     * sortFields : string
     * startTime : string
     * status : 0
     * symbol : string
     */

    private String account;
    private int closeType;
    private String endTime;
    private int hisType;
    private String id;
    private int isSimulate;
    private int memberId;
    private String memberInfo;
    private String orderId;
    private int orderType;
    private int pageNo;
    private int pageSize;
    private int priceType;
    private int side;
    private String sortFields;
    private String startTime;
    private int status;
    private String symbol;

    public CfdOrderPostBean() {
    }

    protected CfdOrderPostBean(Parcel in) {
        account = in.readString();
        closeType = in.readInt();
        endTime = in.readString();
        hisType = in.readInt();
        id = in.readString();
        isSimulate = in.readInt();
        memberId = in.readInt();
        memberInfo = in.readString();
        orderId = in.readString();
        orderType = in.readInt();
        pageNo = in.readInt();
        pageSize = in.readInt();
        priceType = in.readInt();
        side = in.readInt();
        sortFields = in.readString();
        startTime = in.readString();
        status = in.readInt();
        symbol = in.readString();
    }

    public static final Creator<CfdOrderPostBean> CREATOR = new Creator<CfdOrderPostBean>() {
        @Override
        public CfdOrderPostBean createFromParcel(Parcel in) {
            return new CfdOrderPostBean(in);
        }

        @Override
        public CfdOrderPostBean[] newArray(int size) {
            return new CfdOrderPostBean[size];
        }
    };

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getCloseType() {
        return closeType;
    }

    public void setCloseType(int closeType) {
        this.closeType = closeType;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getHisType() {
        return hisType;
    }

    public void setHisType(int hisType) {
        this.hisType = hisType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsSimulate() {
        return isSimulate;
    }

    public void setIsSimulate(int isSimulate) {
        this.isSimulate = isSimulate;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(String memberInfo) {
        this.memberInfo = memberInfo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
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

    public String getSortFields() {
        return sortFields;
    }

    public void setSortFields(String sortFields) {
        this.sortFields = sortFields;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(account);
        parcel.writeInt(closeType);
        parcel.writeString(endTime);
        parcel.writeInt(hisType);
        parcel.writeString(id);
        parcel.writeInt(isSimulate);
        parcel.writeInt(memberId);
        parcel.writeString(memberInfo);
        parcel.writeString(orderId);
        parcel.writeInt(orderType);
        parcel.writeInt(pageNo);
        parcel.writeInt(pageSize);
        parcel.writeInt(priceType);
        parcel.writeInt(side);
        parcel.writeString(sortFields);
        parcel.writeString(startTime);
        parcel.writeInt(status);
        parcel.writeString(symbol);
    }
}
