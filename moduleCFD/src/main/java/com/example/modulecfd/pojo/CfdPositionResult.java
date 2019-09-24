package com.example.modulecfd.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.modulecfd.R;

import java.util.List;

import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.utils.DateUtils;
import me.spark.mvvm.utils.DfUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-06-28
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CfdPositionResult implements Parcelable {

    /**
     * code : 200
     * count : null
     * data : [{"baseCoinScale":2,"closeFee":0.002,"entrustTime":1561685423431,"fee":11.7,"hisType":0,"holdFee":0.21,"id":"GP616854234906985","ip":"192.168.2.96","isSimulate":0,"marginFee":117.04,"marginLever":100,"memberId":55,"multiplier":1,"openPrice":11704.21,"openTime":1561685423491,"orderId":"G616854234317622","orderType":0,"price":11704.21,"priceType":0,"side":1,"stopLossPrice":0.21,"stopProfitPrice":0.2,"symbol":"BTC/USDT","volume":1,"walletKey":"USDT-55"}]
     * message : SUCCESS
     * responseString : 200~SUCCESS
     */

    private int code;
    private Object count;
    private String message;
    private String responseString;
    private List<DataBean> data;

    protected CfdPositionResult(Parcel in) {
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
    }

    public static final Creator<CfdPositionResult> CREATOR = new Creator<CfdPositionResult>() {
        @Override
        public CfdPositionResult createFromParcel(Parcel in) {
            return new CfdPositionResult(in);
        }

        @Override
        public CfdPositionResult[] newArray(int size) {
            return new CfdPositionResult[size];
        }
    };

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getCount() {
        return count;
    }

    public void setCount(Object count) {
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

    public static class DataBean implements Parcelable {
        /**
         * baseCoinScale : 2
         * closeFee : 0.002
         * entrustTime : 1561685423431
         * fee : 11.7
         * hisType : 0
         * holdFee : 0.21
         * id : GP616854234906985
         * ip : 192.168.2.96
         * isSimulate : 0
         * marginFee : 117.04
         * marginLever : 100
         * memberId : 55
         * multiplier : 1
         * openPrice : 11704.21
         * openTime : 1561685423491
         * orderId : G616854234317622
         * orderType : 0
         * price : 11704.21
         * priceType : 0
         * side : 1
         * stopLossPrice : 0.21
         * stopProfitPrice : 0.2
         * symbol : BTC/USDT
         * volume : 1
         * walletKey : USDT-55
         */

        private int baseCoinScale;
        private double closeFee;
        private long entrustTime;
        private double fee;
        private int hisType;
        private double holdFee;
        private String id;
        private String ip;
        private int isSimulate;
        private double marginFee;
        private int marginLever;
        private int memberId;
        private int multiplier;
        private double openPrice;
        private long openTime;
        private String orderId;
        private int orderType;
        private double price;
        private int priceType;
        private int side;
        private double stopLossPrice;
        private double stopProfitPrice;
        private String symbol;
        private int volume;
        private String walletKey;
        private double currentPrice;

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            baseCoinScale = in.readInt();
            closeFee = in.readDouble();
            entrustTime = in.readLong();
            fee = in.readDouble();
            hisType = in.readInt();
            holdFee = in.readDouble();
            id = in.readString();
            ip = in.readString();
            isSimulate = in.readInt();
            marginFee = in.readDouble();
            marginLever = in.readInt();
            memberId = in.readInt();
            multiplier = in.readInt();
            openPrice = in.readDouble();
            openTime = in.readLong();
            orderId = in.readString();
            orderType = in.readInt();
            price = in.readDouble();
            priceType = in.readInt();
            side = in.readInt();
            stopLossPrice = in.readDouble();
            stopProfitPrice = in.readDouble();
            symbol = in.readString();
            volume = in.readInt();
            walletKey = in.readString();
            currentPrice = in.readDouble();
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

        public int getBaseCoinScale() {
            return baseCoinScale;
        }

        public void setBaseCoinScale(int baseCoinScale) {
            this.baseCoinScale = baseCoinScale;
        }

        public double getCloseFee() {
            return closeFee;
        }

        public void setCloseFee(double closeFee) {
            this.closeFee = closeFee;
        }

        public long getEntrustTime() {
            return entrustTime;
        }

        public void setEntrustTime(long entrustTime) {
            this.entrustTime = entrustTime;
        }

        public double getFee() {
            return fee;
        }

        public void setFee(double fee) {
            this.fee = fee;
        }

        public int getHisType() {
            return hisType;
        }

        public void setHisType(int hisType) {
            this.hisType = hisType;
        }

        public double getHoldFee() {
            return holdFee;
        }

        public void setHoldFee(double holdFee) {
            this.holdFee = holdFee;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public int getIsSimulate() {
            return isSimulate;
        }

        public void setIsSimulate(int isSimulate) {
            this.isSimulate = isSimulate;
        }

        public double getMarginFee() {
            return marginFee;
        }

        public void setMarginFee(double marginFee) {
            this.marginFee = marginFee;
        }

        public int getMarginLever() {
            return marginLever;
        }

        public void setMarginLever(int marginLever) {
            this.marginLever = marginLever;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public int getMultiplier() {
            return multiplier;
        }

        public void setMultiplier(int multiplier) {
            this.multiplier = multiplier;
        }

        public double getOpenPrice() {
            return openPrice;
        }

        public void setOpenPrice(double openPrice) {
            this.openPrice = openPrice;
        }

        public long getOpenTime() {
            return openTime;
        }

        public void setOpenTime(long openTime) {
            this.openTime = openTime;
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

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
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

        public double getStopLossPrice() {
            return stopLossPrice;
        }

        public void setStopLossPrice(double stopLossPrice) {
            this.stopLossPrice = stopLossPrice;
        }

        public double getStopProfitPrice() {
            return stopProfitPrice;
        }

        public void setStopProfitPrice(double stopProfitPrice) {
            this.stopProfitPrice = stopProfitPrice;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public int getVolume() {
            return volume;
        }

        public void setVolume(int volume) {
            this.volume = volume;
        }

        public String getWalletKey() {
            return walletKey;
        }

        public void setWalletKey(String walletKey) {
            this.walletKey = walletKey;
        }

        public double getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(double currentPrice) {
            this.currentPrice = currentPrice;
        }

        public String formatSide() {
            return side == 0 ? BaseApplication.getInstance().getString(R.string.bullish) : BaseApplication.getInstance().getString(R.string.bearish);
        }

        public String formatOpenType() {
            return (priceType == 0 ? BaseApplication.getInstance().getString(R.string.market_price)
                    : BaseApplication.getInstance().getString(R.string.limit_price)) +
                    (side == 0 ? BaseApplication.getInstance().getString(R.string.buy)
                            : BaseApplication.getInstance().getString(R.string.sell));
        }

        public boolean isSideUp() {
            return side == 0;
        }

        public String formatSymbol() {
            return symbol + " X" + volume + BaseApplication.getInstance().getString(R.string.hands);
        }

        public String formatProfitAndloss() {
            String profitAndlossStr;
            if (side == 0) {
                profitAndlossStr = currentPrice - openPrice >= 0 ?
                        ("+" + DfUtils.numberFormat((currentPrice - openPrice) * volume * multiplier, 4))
                        : DfUtils.numberFormat((currentPrice - openPrice) * volume * multiplier, 4);
            } else {
                profitAndlossStr = openPrice - currentPrice >= 0 ?
                        ("+" + DfUtils.numberFormat((openPrice - currentPrice) * volume * multiplier, 4))
                        : DfUtils.numberFormat((openPrice - currentPrice) * volume * multiplier, 4);
            }
            return profitAndlossStr;
        }

        public double getProfitAndloss() {
            return side == 0 ? currentPrice - openPrice : openPrice - currentPrice;
        }

        public boolean isProfitAndloss() {
            return side == 0 ? currentPrice - openPrice >= 0 : openPrice - currentPrice >= 0;
        }


        public String formatOpenPrice() {
            return DfUtils.numberFormat(openPrice, 4);
        }

        public String formatCurrentPrice() {
            return DfUtils.numberFormat(currentPrice, 4);
        }

        public String formatMarginPrice() {
            return DfUtils.numberFormat(marginFee, 4);
        }

        public String formatStopProfitPrice() {
            return stopProfitPrice == 0 ? "--" : DfUtils.numberFormat(stopProfitPrice, 4);
        }

        public String formatStopLossPrice() {
            return stopLossPrice == 0 ? "--" : DfUtils.numberFormat(stopLossPrice, 4);
        }

        public String formatDate() {
            return DateUtils.formatDate("MM/dd HH:mm", openTime);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(baseCoinScale);
            parcel.writeDouble(closeFee);
            parcel.writeLong(entrustTime);
            parcel.writeDouble(fee);
            parcel.writeInt(hisType);
            parcel.writeDouble(holdFee);
            parcel.writeString(id);
            parcel.writeString(ip);
            parcel.writeInt(isSimulate);
            parcel.writeDouble(marginFee);
            parcel.writeInt(marginLever);
            parcel.writeInt(memberId);
            parcel.writeInt(multiplier);
            parcel.writeDouble(openPrice);
            parcel.writeLong(openTime);
            parcel.writeString(orderId);
            parcel.writeInt(orderType);
            parcel.writeDouble(price);
            parcel.writeInt(priceType);
            parcel.writeInt(side);
            parcel.writeDouble(stopLossPrice);
            parcel.writeDouble(stopProfitPrice);
            parcel.writeString(symbol);
            parcel.writeInt(volume);
            parcel.writeString(walletKey);
            parcel.writeDouble(currentPrice);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof DataBean) {
                DataBean dataBean = (DataBean) obj;
                return this.id.equals(dataBean.getId());
            }
            return super.equals(obj);
        }
    }
}
