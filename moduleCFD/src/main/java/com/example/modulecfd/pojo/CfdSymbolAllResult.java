package com.example.modulecfd.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-06-25
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CfdSymbolAllResult implements Parcelable {


    /**
     * code : 200
     * message : 操作成功!
     * data : [{"symbol":"BTC/USDT","baseSymbol":"USDT","coinSymbol":"BTC","openFee":0.001,"closeFee":0.002,"holdFee":0.001,"multiplier":1,"coinScale":4,"baseCoinScale":2,"warnRiskRate":1.2,"positionRiskRate":1,"marginFee":"90,120,240,480","volumes":"1,3,5,10","maxTradingTime":0,"maxTradingOrder":0,"flag":1,"zone":0,"sort":1,"enable":1,"maxExchangeSize":10000,"minExchangeSize":1,"fixedRatio":1,"marginLever":"1,5,10,20,30,50,100","maxTotalExchangeSize":10000,"isPosition":1}]
     * count : null
     * responseString : 200~操作成功!
     */

    private int code;
    private String message;
    private Object count;
    private String responseString;
    private List<DataBean> data;

    protected CfdSymbolAllResult(Parcel in) {
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
    }

    public static final Creator<CfdSymbolAllResult> CREATOR = new Creator<CfdSymbolAllResult>() {
        @Override
        public CfdSymbolAllResult createFromParcel(Parcel in) {
            return new CfdSymbolAllResult(in);
        }

        @Override
        public CfdSymbolAllResult[] newArray(int size) {
            return new CfdSymbolAllResult[size];
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(code);
        parcel.writeString(message);
        parcel.writeString(responseString);
    }

    public static class DataBean {
        /**
         * symbol : BTC/USDT
         * baseSymbol : USDT
         * coinSymbol : BTC
         * openFee : 0.001
         * closeFee : 0.002
         * holdFee : 0.001
         * multiplier : 1
         * coinScale : 4
         * baseCoinScale : 2
         * warnRiskRate : 1.2
         * positionRiskRate : 1.0
         * marginFee : 90,120,240,480
         * volumes : 1,3,5,10
         * maxTradingTime : 0
         * maxTradingOrder : 0
         * flag : 1
         * zone : 0
         * sort : 1
         * enable : 1
         * maxExchangeSize : 10000
         * minExchangeSize : 1
         * fixedRatio : 1
         * marginLever : 1,5,10,20,30,50,100
         * maxTotalExchangeSize : 10000
         * isPosition : 1
         */

        private String symbol;                  //合约交易对
        private String baseSymbol;              //交易对右边的币种
        private String coinSymbol;              //交易对左边的币种
        private double openFee;                 //开仓手续费
        private double closeFee;                //平仓手续费
        private double holdFee;                 //持仓手续费
        private int multiplier;                 //合约乘数
        private int coinScale;                  //coin精确度
        private int baseCoinScale;              //基币的精确度
        private double warnRiskRate;
        private double positionRiskRate;        //持仓风险率
        private String marginFee;               //保证金
        private String volumes;                 //手数
        private int maxTradingTime;             //最大交易时间
        private int maxTradingOrder;            //最大订单数
        private int flag;
        private int zone;
        private int sort;
        private int enable;
        private int maxExchangeSize;            //最大下单量
        private int minExchangeSize;            //最小下单量
        private int fixedRatio;
        private String marginLever;             //杠杆倍数
        private String screenName;             //杠杆倍数
        private int maxTotalExchangeSize;       //币对最大交易数
        private int isPosition;

        public String getSymbol() {
            return screenName;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
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

        public double getOpenFee() {
            return openFee;
        }

        public void setOpenFee(double openFee) {
            this.openFee = openFee;
        }

        public double getCloseFee() {
            return closeFee;
        }

        public void setCloseFee(double closeFee) {
            this.closeFee = closeFee;
        }

        public double getHoldFee() {
            return holdFee;
        }

        public void setHoldFee(double holdFee) {
            this.holdFee = holdFee;
        }

        public int getMultiplier() {
            return multiplier;
        }

        public void setMultiplier(int multiplier) {
            this.multiplier = multiplier;
        }

        public int getCoinScale() {
            return coinScale;
        }

        public void setCoinScale(int coinScale) {
            this.coinScale = coinScale;
        }

        public int getBaseCoinScale() {
            return baseCoinScale;
        }

        public void setBaseCoinScale(int baseCoinScale) {
            this.baseCoinScale = baseCoinScale;
        }

        public double getWarnRiskRate() {
            return warnRiskRate;
        }

        public void setWarnRiskRate(double warnRiskRate) {
            this.warnRiskRate = warnRiskRate;
        }

        public double getPositionRiskRate() {
            return positionRiskRate;
        }

        public void setPositionRiskRate(double positionRiskRate) {
            this.positionRiskRate = positionRiskRate;
        }

        public String getMarginFee() {
            return marginFee;
        }

        public void setMarginFee(String marginFee) {
            this.marginFee = marginFee;
        }

        public String getVolumes() {
            return volumes;
        }

        public void setVolumes(String volumes) {
            this.volumes = volumes;
        }

        public int getMaxTradingTime() {
            return maxTradingTime;
        }

        public void setMaxTradingTime(int maxTradingTime) {
            this.maxTradingTime = maxTradingTime;
        }

        public int getMaxTradingOrder() {
            return maxTradingOrder;
        }

        public void setMaxTradingOrder(int maxTradingOrder) {
            this.maxTradingOrder = maxTradingOrder;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public int getZone() {
            return zone;
        }

        public void setZone(int zone) {
            this.zone = zone;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getEnable() {
            return enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
        }

        public int getMaxExchangeSize() {
            return maxExchangeSize;
        }

        public void setMaxExchangeSize(int maxExchangeSize) {
            this.maxExchangeSize = maxExchangeSize;
        }

        public int getMinExchangeSize() {
            return minExchangeSize;
        }

        public void setMinExchangeSize(int minExchangeSize) {
            this.minExchangeSize = minExchangeSize;
        }

        public int getFixedRatio() {
            return fixedRatio;
        }

        public void setFixedRatio(int fixedRatio) {
            this.fixedRatio = fixedRatio;
        }

        public String getMarginLever() {
            return marginLever;
        }

        public void setMarginLever(String marginLever) {
            this.marginLever = marginLever;
        }

        public int getMaxTotalExchangeSize() {
            return maxTotalExchangeSize;
        }

        public void setMaxTotalExchangeSize(int maxTotalExchangeSize) {
            this.maxTotalExchangeSize = maxTotalExchangeSize;
        }

        public String getScreenName() {
            return symbol;
        }

        public void setScreenName(String screenName) {
            this.screenName = screenName;
        }

        public int getIsPosition() {
            return isPosition;
        }

        public void setIsPosition(int isPosition) {
            this.isPosition = isPosition;
        }
    }
}
