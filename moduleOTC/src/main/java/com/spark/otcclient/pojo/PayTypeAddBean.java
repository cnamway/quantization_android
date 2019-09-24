package com.spark.otcclient.pojo;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/24
 * 描    述：添加支付方式
 * 修订历史：
 * ================================================
 */
@SuppressLint("ParcelCreator")
public class PayTypeAddBean implements Parcelable {

    /**
     * bank : string
     * branch : string
     * dayLimit : 0
     * payAddress : string
     * payNotes : string
     * payType : string
     * qrCodeUrl : string
     * tradePwd : string
     */

    private String bank;
    private String branch;
    private int dayLimit;
    private String payAddress;
    private String payNotes;
    private String payType;
    private String qrCodeUrl;
    private String tradePwd;
    private String realName;

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(int dayLimit) {
        this.dayLimit = dayLimit;
    }

    public String getPayAddress() {
        return payAddress;
    }

    public void setPayAddress(String payAddress) {
        this.payAddress = payAddress;
    }

    public String getPayNotes() {
        return payNotes;
    }

    public void setPayNotes(String payNotes) {
        this.payNotes = payNotes;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getTradePwd() {
        return tradePwd;
    }

    public void setTradePwd(String tradePwd) {
        this.tradePwd = tradePwd;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bank);
        dest.writeString(this.branch);
        dest.writeInt(this.dayLimit);
        dest.writeString(this.payAddress);
        dest.writeString(this.payNotes);
        dest.writeString(this.payType);
        dest.writeString(this.qrCodeUrl);
        dest.writeString(this.tradePwd);
        dest.writeString(this.realName);
    }

    public PayTypeAddBean() {
    }

    protected PayTypeAddBean(Parcel in) {
        this.bank = in.readString();
        this.branch = in.readString();
        this.dayLimit = in.readInt();
        this.payAddress = in.readString();
        this.payNotes = in.readString();
        this.payType = in.readString();
        this.qrCodeUrl = in.readString();
        this.tradePwd = in.readString();
        this.realName = in.readString();
    }

    public static final Creator<PayTypeAddBean> CREATOR = new Creator<PayTypeAddBean>() {
        @Override
        public PayTypeAddBean createFromParcel(Parcel source) {
            return new PayTypeAddBean(source);
        }

        @Override
        public PayTypeAddBean[] newArray(int size) {
            return new PayTypeAddBean[size];
        }
    };
}
