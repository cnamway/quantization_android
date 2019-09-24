package com.spark.otcclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-07-19
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class LcCoinListResult implements Parcelable {

    /**
     * code : 200
     * message : SUCCESS
     * data : [{"id":13,"status":1,"sort":0,"coinName":"BTC","isMarginCoin":0,"coinScale":8,"cnyRate":45000.21}]
     * count : null
     * responseString : 200~SUCCESS
     * url : null
     * cid : null
     */

    private int code;
    private String message;
    private Object count;
    private String responseString;
    private Object url;
    private Object cid;
    private List<DataBean> data;

    protected LcCoinListResult(Parcel in) {
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
    }

    public static final Creator<LcCoinListResult> CREATOR = new Creator<LcCoinListResult>() {
        @Override
        public LcCoinListResult createFromParcel(Parcel in) {
            return new LcCoinListResult(in);
        }

        @Override
        public LcCoinListResult[] newArray(int size) {
            return new LcCoinListResult[size];
        }
    };

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getCount() {
        return count;
    }

    public void setCount(Object count) {
        this.count = count;
    }

    public String getResponseString() {
        return responseString;
    }

    public void setResponseString(String responseString) {
        this.responseString = responseString;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public Object getCid() {
        return cid;
    }

    public void setCid(Object cid) {
        this.cid = cid;
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
        parcel.writeInt(code);
        parcel.writeString(message);
        parcel.writeString(responseString);
    }

    public static class DataBean {
        /**
         * id : 13
         * status : 1
         * sort : 0
         * coinName : BTC
         * isMarginCoin : 0
         * coinScale : 8
         * cnyRate : 45000.21
         */

        private int id;
        private int status;
        private int sort;
        private String coinName;
        private int isMarginCoin;
        private int coinScale;
        private double cnyRate;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getCoinName() {
            return coinName;
        }

        public void setCoinName(String coinName) {
            this.coinName = coinName;
        }

        public int getIsMarginCoin() {
            return isMarginCoin;
        }

        public void setIsMarginCoin(int isMarginCoin) {
            this.isMarginCoin = isMarginCoin;
        }

        public int getCoinScale() {
            return coinScale;
        }

        public void setCoinScale(int coinScale) {
            this.coinScale = coinScale;
        }

        public double getCnyRate() {
            return cnyRate;
        }

        public void setCnyRate(double cnyRate) {
            this.cnyRate = cnyRate;
        }
    }
}
