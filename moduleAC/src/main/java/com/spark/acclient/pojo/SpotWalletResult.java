package com.spark.acclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.utils.DfUtils;
import me.spark.mvvm.utils.StringUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/31
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class SpotWalletResult implements Parcelable {
    /**
     * cid : string
     * code : 0
     * count : {}
     * data : [{"address":"string","balance":0.21,"coinId":"string","frozenBalance":0.21,"frozenMargin":0.21,"id":0,"interest":0.21,"isLock":0,"legalRate":0.2,"loanBalance":0.2,"localCurrency":"string","memberId":0,"platformAssetRate":0.21,"positionMargin":0.21,"realName":"string","symbol":"string","totalLegalAssetBalance":0.21,"totalPlatformAssetBalance":0.21}]
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

    protected SpotWalletResult(Parcel in) {
        cid = in.readString();
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
        url = in.readString();
    }

    public static final Creator<SpotWalletResult> CREATOR = new Creator<SpotWalletResult>() {
        @Override
        public SpotWalletResult createFromParcel(Parcel in) {
            return new SpotWalletResult(in);
        }

        @Override
        public SpotWalletResult[] newArray(int size) {
            return new SpotWalletResult[size];
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cid);
        parcel.writeInt(code);
        parcel.writeString(message);
        parcel.writeString(responseString);
        parcel.writeString(url);
    }

    public static class CountBean {
    }

    public static class DataBean implements Parcelable {
        /**
         * address : string
         * balance : 0.21
         * coinId : string
         * frozenBalance : 0.21
         * frozenMargin : 0.21
         * id : 0
         * interest : 0.21
         * isLock : 0
         * legalRate : 0.2
         * loanBalance : 0.2
         * localCurrency : string
         * memberId : 0
         * platformAssetRate : 0.21
         * positionMargin : 0.21
         * realName : string
         * symbol : string
         * totalLegalAssetBalance : 0.21
         * totalPlatformAssetBalance : 0.21
         */

        private String address;
        private double balance;
        private String coinId;
        private double frozenBalance;
        private double frozenMargin;
        private int id;
        private double interest;
        private int isLock;
        private double legalRate;
        private double loanBalance;
        private String localCurrency;
        private int memberId;
        private double platformAssetRate;
        private double positionMargin;
        private String realName;
        private String symbol;
        private double totalLegalAssetBalance;
        private double totalPlatformAssetBalance;

        protected DataBean(Parcel in) {
            address = in.readString();
            balance = in.readDouble();
            coinId = in.readString();
            frozenBalance = in.readDouble();
            frozenMargin = in.readDouble();
            id = in.readInt();
            interest = in.readDouble();
            isLock = in.readInt();
            legalRate = in.readDouble();
            loanBalance = in.readDouble();
            localCurrency = in.readString();
            memberId = in.readInt();
            platformAssetRate = in.readDouble();
            positionMargin = in.readDouble();
            realName = in.readString();
            symbol = in.readString();
            totalLegalAssetBalance = in.readDouble();
            totalPlatformAssetBalance = in.readDouble();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

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

        public double getFrozenMargin() {
            return frozenMargin;
        }

        public void setFrozenMargin(double frozenMargin) {
            this.frozenMargin = frozenMargin;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getInterest() {
            return interest;
        }

        public void setInterest(double interest) {
            this.interest = interest;
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

        public double getLoanBalance() {
            return loanBalance;
        }

        public void setLoanBalance(double loanBalance) {
            this.loanBalance = loanBalance;
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

        public double getPositionMargin() {
            return positionMargin;
        }

        public void setPositionMargin(double positionMargin) {
            this.positionMargin = positionMargin;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
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

        public String initImgUrl() {
            String url = "";
            if (!StringUtils.isEmpty(Constant.accountJson)) {
                CoinSupportBean coinSupportBean = BaseApplication.gson.fromJson(Constant.accountJson, CoinSupportBean.class);
                for (CoinSupportBean.DataBean dataBean : coinSupportBean.getData()) {
                    if (dataBean.getCoinName().equals(coinId)) {
                        url = dataBean.getIconUrl();
                    }
                }
            }
            return url;
        }

        public String formatBlance() {
            return DfUtils.numberFormat(balance + frozenBalance, 4);
        }

        public String formatBlanceTrans() {
            return "≈" + DfUtils.numberFormat(totalLegalAssetBalance, 2) + " CNY";
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(address);
            parcel.writeDouble(balance);
            parcel.writeString(coinId);
            parcel.writeDouble(frozenBalance);
            parcel.writeDouble(frozenMargin);
            parcel.writeInt(id);
            parcel.writeDouble(interest);
            parcel.writeInt(isLock);
            parcel.writeDouble(legalRate);
            parcel.writeDouble(loanBalance);
            parcel.writeString(localCurrency);
            parcel.writeInt(memberId);
            parcel.writeDouble(platformAssetRate);
            parcel.writeDouble(positionMargin);
            parcel.writeString(realName);
            parcel.writeString(symbol);
            parcel.writeDouble(totalLegalAssetBalance);
            parcel.writeDouble(totalPlatformAssetBalance);
        }
    }
}
