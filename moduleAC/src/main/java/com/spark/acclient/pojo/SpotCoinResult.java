package com.spark.acclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/27
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class SpotCoinResult implements Parcelable{

    /**
     * code : 200
     * message : 操作成功!
     * data : [{"symbol":"BTC/USDT","baseIconUrl":"","coinIconUrl":"","baseSymbol":"USDT","coinSymbol":"BTC","coinScale":8,"coinScreenScale":4,"baseCoinScale":8,"baseCoinScreenScale":4},{"symbol":"ETH/USDT","baseIconUrl":"","coinIconUrl":"","baseSymbol":"USDT","coinSymbol":"ETH","coinScale":8,"coinScreenScale":4,"baseCoinScale":8,"baseCoinScreenScale":4},{"symbol":"USDT/XYZ","baseIconUrl":"","coinIconUrl":"","baseSymbol":"USDT","coinSymbol":"HUSD","coinScale":0,"coinScreenScale":4,"baseCoinScale":4,"baseCoinScreenScale":4}]
     * count : null
     * responseString : 200~操作成功!
     */

    private int code;
    private String message;
    private Object count;
    private String responseString;
    private List<DataBean> data;

    protected SpotCoinResult(Parcel in) {
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
    }

    public static final Creator<SpotCoinResult> CREATOR = new Creator<SpotCoinResult>() {
        @Override
        public SpotCoinResult createFromParcel(Parcel in) {
            return new SpotCoinResult(in);
        }

        @Override
        public SpotCoinResult[] newArray(int size) {
            return new SpotCoinResult[size];
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
         * symbol : BTC/USDT
         * baseIconUrl :
         * coinIconUrl :
         * baseSymbol : USDT
         * coinSymbol : BTC
         * coinScale : 8
         * coinScreenScale : 4
         * baseCoinScale : 8
         * baseCoinScreenScale : 4
         */

        private String symbol;
        private String baseIconUrl;
        private String coinIconUrl;
        private String baseSymbol;
        private String coinSymbol;
        private int coinScale;
        private int coinScreenScale;
        private int baseCoinScale;
        private int baseCoinScreenScale;

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getBaseIconUrl() {
            return baseIconUrl;
        }

        public void setBaseIconUrl(String baseIconUrl) {
            this.baseIconUrl = baseIconUrl;
        }

        public String getCoinIconUrl() {
            return coinIconUrl;
        }

        public void setCoinIconUrl(String coinIconUrl) {
            this.coinIconUrl = coinIconUrl;
        }

        public String getBaseSymbol() {
            return baseSymbol;
        }

        public void setBaseSymbol(String baseSymbol) {
            this.baseSymbol = baseSymbol;
        }

        public String getCoinSymbol() {
            return coinSymbol;
        }

        public void setCoinSymbol(String coinSymbol) {
            this.coinSymbol = coinSymbol;
        }

        public int getCoinScale() {
            return coinScale;
        }

        public void setCoinScale(int coinScale) {
            this.coinScale = coinScale;
        }

        public int getCoinScreenScale() {
            return coinScreenScale;
        }

        public void setCoinScreenScale(int coinScreenScale) {
            this.coinScreenScale = coinScreenScale;
        }

        public int getBaseCoinScale() {
            return baseCoinScale;
        }

        public void setBaseCoinScale(int baseCoinScale) {
            this.baseCoinScale = baseCoinScale;
        }

        public int getBaseCoinScreenScale() {
            return baseCoinScreenScale;
        }

        public void setBaseCoinScreenScale(int baseCoinScreenScale) {
            this.baseCoinScreenScale = baseCoinScreenScale;
        }
    }
}
