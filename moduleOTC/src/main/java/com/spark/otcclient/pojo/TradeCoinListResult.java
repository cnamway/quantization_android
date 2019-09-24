package com.spark.otcclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/25
 * 描    述：查询所有的币种
 * 修订历史：
 * ================================================
 */
public class TradeCoinListResult implements Parcelable {

    /**
     * code : 200
     * message : SUCCESS
     * data : [{"id":13,"status":1,"sort":0,"coinName":"BTC","isMarginCoin":0,"coinScale":8,"cnyRate":45000},{"id":14,"status":1,"sort":1,"coinName":"ETH","isMarginCoin":0,"coinScale":8,"cnyRate":1500},{"id":15,"status":1,"sort":2,"coinName":"USDT","isMarginCoin":0,"coinScale":8,"cnyRate":6.8},{"id":16,"status":1,"sort":3,"coinName":"XNE","isMarginCoin":1,"coinScale":8,"cnyRate":0.1},{"id":17,"status":1,"sort":4,"coinName":"CNT","isMarginCoin":0,"coinScale":8,"cnyRate":1}]
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

    protected TradeCoinListResult(Parcel in) {
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
    }

    public static final Creator<TradeCoinListResult> CREATOR = new Creator<TradeCoinListResult>() {
        @Override
        public TradeCoinListResult createFromParcel(Parcel in) {
            return new TradeCoinListResult(in);
        }

        @Override
        public TradeCoinListResult[] newArray(int size) {
            return new TradeCoinListResult[size];
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(message);
        dest.writeString(responseString);
    }

    public static class DataBean {
        /**
         * id : 13
         * status : 1
         * sort : 0
         * coinName : BTC
         * isMarginCoin : 0
         * coinScale : 8
         * cnyRate : 45000.0
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