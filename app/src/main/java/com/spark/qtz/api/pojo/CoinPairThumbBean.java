package com.spark.qtz.api.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/7
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CoinPairThumbBean implements Parcelable{

    /**
     * code : 200
     * data : [{"baseCoinScale":8,"baseFee":0.001,"baseSymbol":"USDT","coinFee":0.001,"coinScale":8,"coinSymbol":"ETH","enable":1,"enableMarketBuy":1,"enableMarketSell":1,"feeType":0,"flag":1,"maxTradingOrder":100000,"maxTradingTime":604800,"maxVolume":0,"minSellPrice":0,"minTurnover":0,"minVolume":0,"sort":0,"symbol":"ETH/USDT","zone":0},{"baseCoinScale":8,"baseFee":0.001,"baseSymbol":"USDT","coinFee":0.001,"coinScale":8,"coinSymbol":"BCH","enable":1,"enableMarketBuy":1,"enableMarketSell":1,"feeType":0,"flag":1,"maxTradingOrder":1000000,"maxTradingTime":604800,"maxVolume":0,"minSellPrice":0,"minTurnover":0.001,"minVolume":0.001,"sort":1,"symbol":"BCH/USDT","zone":0},{"baseCoinScale":8,"baseFee":0.001,"baseSymbol":"USDT","coinFee":0.001,"coinScale":8,"coinSymbol":"BTC","enable":1,"enableMarketBuy":1,"enableMarketSell":1,"feeType":0,"flag":1,"maxTradingOrder":100000,"maxTradingTime":604800,"maxVolume":0,"minSellPrice":0,"minTurnover":0.001,"minVolume":0.001,"sort":1,"symbol":"BTC/USDT","zone":0},{"baseCoinScale":8,"baseFee":0.001,"baseSymbol":"USDT","coinFee":0.001,"coinScale":8,"coinSymbol":"EOS","enable":1,"enableMarketBuy":1,"enableMarketSell":1,"feeType":0,"flag":1,"maxTradingOrder":100000,"maxTradingTime":604800,"maxVolume":0,"minSellPrice":0,"minTurnover":0.001,"minVolume":0.001,"sort":1,"symbol":"EOS/USDT","zone":0},{"baseCoinScale":8,"baseFee":0.001,"baseSymbol":"USDT","coinFee":0.001,"coinScale":8,"coinSymbol":"ETC","enable":1,"enableMarketBuy":1,"enableMarketSell":1,"feeType":0,"flag":0,"maxTradingOrder":100000,"maxTradingTime":604800,"maxVolume":0,"minSellPrice":0,"minTurnover":0,"minVolume":0,"sort":1,"symbol":"ETC/USDT","zone":0},{"baseCoinScale":2,"baseFee":0.001,"baseSymbol":"USDT","coinFee":0.001,"coinScale":4,"coinSymbol":"LTC","enable":1,"enableMarketBuy":1,"enableMarketSell":1,"feeType":0,"flag":0,"maxTradingOrder":100000,"maxTradingTime":604800,"maxVolume":0,"minSellPrice":0,"minTurnover":0,"minVolume":0,"sort":6,"symbol":"LTC/USDT","zone":0},{"baseCoinScale":8,"baseFee":0.001,"baseSymbol":"BTC","coinFee":0.001,"coinScale":8,"coinSymbol":"ETC","enable":1,"enableMarketBuy":1,"enableMarketSell":1,"feeType":0,"flag":1,"maxTradingOrder":10000,"maxTradingTime":604800,"maxVolume":0,"minSellPrice":0,"minTurnover":0.001,"minVolume":0.001,"sort":1,"symbol":"ETC/BTC","zone":0},{"baseCoinScale":8,"baseFee":0.001,"baseSymbol":"BTC","coinFee":0.001,"coinScale":8,"coinSymbol":"BCH","enable":1,"enableMarketBuy":1,"enableMarketSell":1,"feeType":0,"flag":1,"maxTradingOrder":100000,"maxTradingTime":604800,"maxVolume":0,"minSellPrice":1.0E-6,"minTurnover":0.01,"minVolume":0.001,"sort":2,"symbol":"BCH/BTC","zone":0},{"baseCoinScale":8,"baseFee":0.001,"baseSymbol":"BTC","coinFee":0.001,"coinScale":8,"coinSymbol":"EOS","enable":1,"enableMarketBuy":1,"enableMarketSell":1,"feeType":0,"flag":1,"maxTradingOrder":10000,"maxTradingTime":604800,"maxVolume":0,"minSellPrice":0,"minTurnover":0.001,"minVolume":0.001,"sort":2,"symbol":"EOS/BTC","zone":0},{"baseCoinScale":8,"baseFee":0.001,"baseSymbol":"BTC","coinFee":0.001,"coinScale":8,"coinSymbol":"ETH","enable":1,"enableMarketBuy":1,"enableMarketSell":1,"feeType":0,"flag":0,"maxTradingOrder":1000000,"maxTradingTime":604800,"maxVolume":0,"minSellPrice":0,"minTurnover":0,"minVolume":0,"sort":4,"symbol":"ETH/BTC","zone":0},{"baseCoinScale":8,"baseFee":0.001,"baseSymbol":"BTC","coinFee":0.001,"coinScale":8,"coinSymbol":"LTC","enable":1,"enableMarketBuy":1,"enableMarketSell":1,"feeType":0,"flag":0,"maxTradingOrder":1000000,"maxTradingTime":604800,"maxVolume":0,"minSellPrice":0,"minTurnover":0,"minVolume":0,"sort":4,"symbol":"LTC/BTC","zone":0},{"baseCoinScale":8,"baseFee":0.001,"baseSymbol":"ETH","coinFee":0.001,"coinScale":8,"coinSymbol":"EOS","enable":1,"enableMarketBuy":1,"enableMarketSell":1,"feeType":0,"flag":0,"maxTradingOrder":1000000,"maxTradingTime":604800,"maxVolume":0,"minSellPrice":0,"minTurnover":0.01,"minVolume":1,"sort":3,"symbol":"EOS/ETH","zone":0},{"baseCoinScale":8,"baseFee":0.001,"baseSymbol":"ETH","coinFee":0.001,"coinScale":8,"coinSymbol":"ETC","enable":1,"enableMarketBuy":1,"enableMarketSell":1,"feeType":0,"flag":1,"maxTradingOrder":10000,"maxTradingTime":604800,"maxVolume":0,"minSellPrice":0.01,"minTurnover":0.01,"minVolume":0.01,"sort":3,"symbol":"ETC/ETH","zone":0},{"baseCoinScale":8,"baseFee":0.001,"baseSymbol":"ETH","coinFee":0.001,"coinScale":8,"coinSymbol":"LTC","enable":1,"enableMarketBuy":1,"enableMarketSell":1,"feeType":0,"flag":1,"maxTradingOrder":100000,"maxTradingTime":604800,"maxVolume":0,"minSellPrice":0,"minTurnover":0.01,"minVolume":1,"sort":3,"symbol":"LTC/ETH","zone":0},{"baseCoinScale":8,"baseFee":0.001,"baseSymbol":"CSS","coinFee":0.001,"coinScale":8,"coinSymbol":"BCH","enable":1,"enableMarketBuy":1,"enableMarketSell":1,"feeType":0,"flag":1,"maxTradingOrder":10000,"maxTradingTime":604800,"maxVolume":0,"minSellPrice":0,"minTurnover":0.001,"minVolume":0.001,"sort":4,"symbol":"BCH/CSS","zone":0},{"baseCoinScale":8,"baseFee":0.001,"baseSymbol":"CSS","coinFee":0.001,"coinScale":8,"coinSymbol":"BTC","enable":1,"enableMarketBuy":1,"enableMarketSell":1,"feeType":0,"flag":1,"maxTradingOrder":10000,"maxTradingTime":604800,"maxVolume":0,"minSellPrice":0,"minTurnover":0.001,"minVolume":0.01,"sort":4,"symbol":"BTC/CSS","zone":0},{"baseCoinScale":8,"baseFee":0.001,"baseSymbol":"CSS","coinFee":0.001,"coinScale":8,"coinSymbol":"EOS","enable":1,"enableMarketBuy":1,"enableMarketSell":1,"feeType":0,"flag":1,"maxTradingOrder":10000,"maxTradingTime":604800,"maxVolume":0,"minSellPrice":0.1,"minTurnover":0.001,"minVolume":0.001,"sort":4,"symbol":"EOS/CSS","zone":0},{"baseCoinScale":8,"baseFee":0.001,"baseSymbol":"CSS","coinFee":0.001,"coinScale":8,"coinSymbol":"ETC","enable":1,"enableMarketBuy":1,"enableMarketSell":1,"feeType":0,"flag":1,"maxTradingOrder":10000,"maxTradingTime":604800,"maxVolume":0,"minSellPrice":0.1,"minTurnover":0.001,"minVolume":0.001,"sort":4,"symbol":"ETC/CSS","zone":0},{"baseCoinScale":8,"baseFee":0,"baseSymbol":"CSS","coinFee":0,"coinScale":8,"coinSymbol":"ETH","enable":1,"enableMarketBuy":1,"enableMarketSell":1,"feeType":0,"flag":1,"maxTradingOrder":10000,"maxTradingTime":604800,"maxVolume":0,"minSellPrice":0.1,"minTurnover":0.001,"minVolume":0.001,"sort":4,"symbol":"ETH/CSS","zone":0},{"baseCoinScale":8,"baseFee":0.001,"baseSymbol":"CSS","coinFee":0.001,"coinScale":8,"coinSymbol":"LTC","enable":1,"enableMarketBuy":1,"enableMarketSell":1,"feeType":0,"flag":1,"maxTradingOrder":10000,"maxTradingTime":604800,"maxVolume":0,"minSellPrice":0.1,"minTurnover":0.001,"minVolume":0.001,"sort":4,"symbol":"LTC/CSS","zone":0},{"baseCoinScale":8,"baseFee":0.001,"baseSymbol":"CSS","coinFee":0.001,"coinScale":8,"coinSymbol":"USDT","enable":1,"enableMarketBuy":1,"enableMarketSell":1,"feeType":0,"flag":1,"maxTradingOrder":10000,"maxTradingTime":604800,"maxVolume":0,"minSellPrice":0.01,"minTurnover":0.1,"minVolume":1,"sort":4,"symbol":"USDT/CSS","zone":0}]
     * message : SUCCESS
     * responseString : 200~SUCCESS
     */

    private int code;
    private String message;
    private String responseString;
    private List<CoinPairThumbBean.DataBean> data;

    protected CoinPairThumbBean(Parcel in) {
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
    }

    public static final Creator<CoinPairThumbBean> CREATOR = new Creator<CoinPairThumbBean>() {
        @Override
        public CoinPairThumbBean createFromParcel(Parcel in) {
            return new CoinPairThumbBean(in);
        }

        @Override
        public CoinPairThumbBean[] newArray(int size) {
            return new CoinPairThumbBean[size];
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

    public String getResponseString() {
        return responseString;
    }

    public void setResponseString(String responseString) {
        this.responseString = responseString;
    }

    public List<CoinPairThumbBean.DataBean> getData() {
        return data;
    }

    public void setData(List<CoinPairThumbBean.DataBean> data) {
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
         * baseCoinScale : 8
         * baseFee : 0.001
         * baseSymbol : USDT
         * coinFee : 0.001
         * coinScale : 8
         * coinSymbol : ETH
         * enable : 1
         * enableMarketBuy : 1
         * enableMarketSell : 1
         * feeType : 0
         * flag : 1
         * maxTradingOrder : 100000
         * maxTradingTime : 604800
         * maxVolume : 0
         * minSellPrice : 0
         * minTurnover : 0
         * minVolume : 0
         * sort : 0
         * symbol : ETH/USDT
         * zone : 0
         */

        private int baseCoinScale;
        private double baseFee;
        private String baseSymbol;
        private double coinFee;
        private double coinScale;
        private String coinSymbol;
        private int enable;
        private int enableMarketBuy;
        private int enableMarketSell;
        private int feeType;
        private int flag;
        private double maxTradingOrder;
        private double maxTradingTime;
        private double maxVolume;
        private double minSellPrice;
        private double minTurnover;
        private double minVolume;
        private int sort;
        private String symbol;
        private int zone;

        public int getBaseCoinScale() {
            return baseCoinScale;
        }

        public void setBaseCoinScale(int baseCoinScale) {
            this.baseCoinScale = baseCoinScale;
        }

        public double getBaseFee() {
            return baseFee;
        }

        public void setBaseFee(double baseFee) {
            this.baseFee = baseFee;
        }

        public String getBaseSymbol() {
            return baseSymbol;
        }

        public void setBaseSymbol(String baseSymbol) {
            this.baseSymbol = baseSymbol;
        }

        public double getCoinFee() {
            return coinFee;
        }

        public void setCoinFee(double coinFee) {
            this.coinFee = coinFee;
        }

        public double getCoinScale() {
            return coinScale;
        }

        public void setCoinScale(int coinScale) {
            this.coinScale = coinScale;
        }

        public String getCoinSymbol() {
            return coinSymbol;
        }

        public void setCoinSymbol(String coinSymbol) {
            this.coinSymbol = coinSymbol;
        }

        public int getEnable() {
            return enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
        }

        public int getEnableMarketBuy() {
            return enableMarketBuy;
        }

        public void setEnableMarketBuy(int enableMarketBuy) {
            this.enableMarketBuy = enableMarketBuy;
        }

        public int getEnableMarketSell() {
            return enableMarketSell;
        }

        public void setEnableMarketSell(int enableMarketSell) {
            this.enableMarketSell = enableMarketSell;
        }

        public int getFeeType() {
            return feeType;
        }

        public void setFeeType(int feeType) {
            this.feeType = feeType;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public double getMaxTradingOrder() {
            return maxTradingOrder;
        }

        public void setMaxTradingOrder(double maxTradingOrder) {
            this.maxTradingOrder = maxTradingOrder;
        }

        public double getMaxTradingTime() {
            return maxTradingTime;
        }

        public void setMaxTradingTime(double maxTradingTime) {
            this.maxTradingTime = maxTradingTime;
        }

        public double getMaxVolume() {
            return maxVolume;
        }

        public void setMaxVolume(double maxVolume) {
            this.maxVolume = maxVolume;
        }

        public double getMinSellPrice() {
            return minSellPrice;
        }

        public void setMinSellPrice(double minSellPrice) {
            this.minSellPrice = minSellPrice;
        }

        public double getMinTurnover() {
            return minTurnover;
        }

        public void setMinTurnover(double minTurnover) {
            this.minTurnover = minTurnover;
        }

        public double getMinVolume() {
            return minVolume;
        }

        public void setMinVolume(double minVolume) {
            this.minVolume = minVolume;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public int getZone() {
            return zone;
        }

        public void setZone(int zone) {
            this.zone = zone;
        }
    }
}