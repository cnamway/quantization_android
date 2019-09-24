package com.spark.modulespot.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/12
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class QueryWithSymbolResult implements Parcelable{

    /**
     * cid : string
     * code : 0
     * count : {}
     * data : [{"address":"string","balance":0.1,"coinId":"string","frozenBalance":0.1,"id":0,"isLock":0,"legalRate":0.1,"localCurrency":"string","memberId":0,"platformAssetRate":0.1,"realName":"string","totalLegalAssetBalance":0.1,"totalPlatformAssetBalance":0.1}]
     * message : string
     * responseString : string
     * url : string
     */

    private String cid;
    private int code;
    private CountBean count;
    private String message;
    private String responseString;
    private String url;
    private List<DataBean> data;

    protected QueryWithSymbolResult(Parcel in) {
        cid = in.readString();
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
        url = in.readString();
    }

    public static final Creator<QueryWithSymbolResult> CREATOR = new Creator<QueryWithSymbolResult>() {
        @Override
        public QueryWithSymbolResult createFromParcel(Parcel in) {
            return new QueryWithSymbolResult(in);
        }

        @Override
        public QueryWithSymbolResult[] newArray(int size) {
            return new QueryWithSymbolResult[size];
        }
    };

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public CountBean getCount() {
        return count;
    }

    public void setCount(CountBean count) {
        this.count = count;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponseString() {
        return responseString;
    }

    public void setResponseString(String responseString) {
        this.responseString = responseString;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cid);
        dest.writeInt(code);
        dest.writeString(message);
        dest.writeString(responseString);
        dest.writeString(url);
    }

    public static class CountBean {
    }

    public static class DataBean {
        /**
         * address : string
         * balance : 0.1
         * coinId : string
         * frozenBalance : 0.1
         * id : 0
         * isLock : 0
         * legalRate : 0.1
         * localCurrency : string
         * memberId : 0
         * platformAssetRate : 0.1
         * realName : string
         * totalLegalAssetBalance : 0.1
         * totalPlatformAssetBalance : 0.1
         */

        private String address;
        private double balance;
        private String coinId;
        private double frozenBalance;
        private int id;
        private int isLock;
        private double legalRate;
        private String localCurrency;
        private int memberId;
        private double platformAssetRate;
        private String realName;
        private double totalLegalAssetBalance;
        private double totalPlatformAssetBalance;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public String getCoinId() {
            return coinId;
        }

        public void setCoinId(String coinId) {
            this.coinId = coinId;
        }

        public double getFrozenBalance() {
            return frozenBalance;
        }

        public void setFrozenBalance(double frozenBalance) {
            this.frozenBalance = frozenBalance;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsLock() {
            return isLock;
        }

        public void setIsLock(int isLock) {
            this.isLock = isLock;
        }

        public double getLegalRate() {
            return legalRate;
        }

        public void setLegalRate(double legalRate) {
            this.legalRate = legalRate;
        }

        public String getLocalCurrency() {
            return localCurrency;
        }

        public void setLocalCurrency(String localCurrency) {
            this.localCurrency = localCurrency;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public double getPlatformAssetRate() {
            return platformAssetRate;
        }

        public void setPlatformAssetRate(double platformAssetRate) {
            this.platformAssetRate = platformAssetRate;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public double getTotalLegalAssetBalance() {
            return totalLegalAssetBalance;
        }

        public void setTotalLegalAssetBalance(double totalLegalAssetBalance) {
            this.totalLegalAssetBalance = totalLegalAssetBalance;
        }

        public double getTotalPlatformAssetBalance() {
            return totalPlatformAssetBalance;
        }

        public void setTotalPlatformAssetBalance(double totalPlatformAssetBalance) {
            this.totalPlatformAssetBalance = totalPlatformAssetBalance;
        }
    }
}
