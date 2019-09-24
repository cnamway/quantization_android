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
 * 创建日期：2019-07-03
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CfdTradeOrderResult implements Parcelable {
    /**
     * code : 200
     * count : null
     * data : {"current":1,"pages":7,"records":[{"baseCoinScale":2,"closeFee":1.2,"closePrice":10391.48,"closeProfit":11.48,"closeTime":1562078811432,"closeType":2,"fee":20.78,"hisType":1,"holdFee":0.21,"id":"GC620788114328821","intType":1,"marginFee":103.8,"memberId":55,"multiplier":1,"openFee":10.38,"openPrice":10380.21,"openTime":1562078786376,"orderId":"G620787863428150","positionId":"GP620787863762824","priceType":0,"side":0,"stopLossPrice":0.21,"stopProfitPrice":0.21,"symbol":"BTC/USDT","volume":1,"walletKey":""}],"size":10,"total":63}
     * message : SUCCESS
     * responseString : 200~SUCCESS
     */

    private int code;
    private Object count;
    private DataBean data;
    private String message;
    private String responseString;

    protected CfdTradeOrderResult(Parcel in) {
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
    }

    public static final Creator<CfdTradeOrderResult> CREATOR = new Creator<CfdTradeOrderResult>() {
        @Override
        public CfdTradeOrderResult createFromParcel(Parcel in) {
            return new CfdTradeOrderResult(in);
        }

        @Override
        public CfdTradeOrderResult[] newArray(int size) {
            return new CfdTradeOrderResult[size];
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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
         * current : 1
         * pages : 7
         * records : [{"baseCoinScale":2,"closeFee":1.2,"closePrice":10391.48,"closeProfit":11.48,"closeTime":1562078811432,"closeType":2,"fee":20.78,"hisType":1,"holdFee":0.21,"id":"GC620788114328821","intType":1,"marginFee":103.8,"memberId":55,"multiplier":1,"openFee":10.38,"openPrice":10380.21,"openTime":1562078786376,"orderId":"G620787863428150","positionId":"GP620787863762824","priceType":0,"side":0,"stopLossPrice":0.21,"stopProfitPrice":0.21,"symbol":"BTC/USDT","volume":1,"walletKey":""}]
         * size : 10
         * total : 63
         */

        private int current;
        private int pages;
        private int size;
        private int total;
        private List<RecordsBean> records;

        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<RecordsBean> getRecords() {
            return records;
        }

        public void setRecords(List<RecordsBean> records) {
            this.records = records;
        }

        public static class RecordsBean implements Parcelable {
            /**
             * baseCoinScale : 2
             * closeFee : 1.2
             * closePrice : 10391.48
             * closeProfit : 11.48
             * closeTime : 1562078811432
             * closeType : 2
             * fee : 20.78
             * hisType : 1
             * holdFee : 0.21
             * id : GC620788114328821
             * intType : 1
             * marginFee : 103.8
             * memberId : 55
             * multiplier : 1
             * openFee : 10.38
             * openPrice : 10380.21
             * openTime : 1562078786376
             * orderId : G620787863428150
             * positionId : GP620787863762824
             * priceType : 0
             * side : 0
             * stopLossPrice : 0.21
             * stopProfitPrice : 0.21
             * symbol : BTC/USDT
             * volume : 1
             * walletKey :
             */

            private int baseCoinScale;
            private double closeFee;
            private double closePrice;
            private double closeProfit;
            private long closeTime;
            private int closeType;
            private double fee;
            private int hisType;
            private double holdFee;
            private String id;
            private int intType;
            private double marginFee;
            private int memberId;
            private int multiplier;
            private double openFee;
            private double openPrice;
            private long openTime;
            private String orderId;
            private String positionId;
            private int priceType;
            private int side;
            private double stopLossPrice;
            private double stopProfitPrice;
            private String symbol;
            private int volume;
            private String walletKey;
            private double currentPrice;

            protected RecordsBean(Parcel in) {
                baseCoinScale = in.readInt();
                closeFee = in.readDouble();
                closePrice = in.readDouble();
                closeProfit = in.readDouble();
                closeTime = in.readLong();
                closeType = in.readInt();
                fee = in.readDouble();
                hisType = in.readInt();
                holdFee = in.readDouble();
                id = in.readString();
                intType = in.readInt();
                marginFee = in.readDouble();
                memberId = in.readInt();
                multiplier = in.readInt();
                openFee = in.readDouble();
                openPrice = in.readDouble();
                openTime = in.readLong();
                orderId = in.readString();
                positionId = in.readString();
                priceType = in.readInt();
                side = in.readInt();
                stopLossPrice = in.readDouble();
                stopProfitPrice = in.readDouble();
                symbol = in.readString();
                volume = in.readInt();
                walletKey = in.readString();
                currentPrice = in.readDouble();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(baseCoinScale);
                dest.writeDouble(closeFee);
                dest.writeDouble(closePrice);
                dest.writeDouble(closeProfit);
                dest.writeLong(closeTime);
                dest.writeInt(closeType);
                dest.writeDouble(fee);
                dest.writeInt(hisType);
                dest.writeDouble(holdFee);
                dest.writeString(id);
                dest.writeInt(intType);
                dest.writeDouble(marginFee);
                dest.writeInt(memberId);
                dest.writeInt(multiplier);
                dest.writeDouble(openFee);
                dest.writeDouble(openPrice);
                dest.writeLong(openTime);
                dest.writeString(orderId);
                dest.writeString(positionId);
                dest.writeInt(priceType);
                dest.writeInt(side);
                dest.writeDouble(stopLossPrice);
                dest.writeDouble(stopProfitPrice);
                dest.writeString(symbol);
                dest.writeInt(volume);
                dest.writeString(walletKey);
                dest.writeDouble(currentPrice);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<RecordsBean> CREATOR = new Creator<RecordsBean>() {
                @Override
                public RecordsBean createFromParcel(Parcel in) {
                    return new RecordsBean(in);
                }

                @Override
                public RecordsBean[] newArray(int size) {
                    return new RecordsBean[size];
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

            public double getClosePrice() {
                return closePrice;
            }

            public void setClosePrice(double closePrice) {
                this.closePrice = closePrice;
            }

            public double getCloseProfit() {
                return closeProfit;
            }

            public void setCloseProfit(double closeProfit) {
                this.closeProfit = closeProfit;
            }

            public long getCloseTime() {
                return closeTime;
            }

            public void setCloseTime(long closeTime) {
                this.closeTime = closeTime;
            }

            public int getCloseType() {
                return closeType;
            }

            public void setCloseType(int closeType) {
                this.closeType = closeType;
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

            public int getIntType() {
                return intType;
            }

            public void setIntType(int intType) {
                this.intType = intType;
            }

            public double getMarginFee() {
                return marginFee;
            }

            public void setMarginFee(double marginFee) {
                this.marginFee = marginFee;
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

            public double getOpenFee() {
                return openFee;
            }

            public void setOpenFee(double openFee) {
                this.openFee = openFee;
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

            public String getPositionId() {
                return positionId;
            }

            public void setPositionId(String positionId) {
                this.positionId = positionId;
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

            public String formatSymbol() {
                return symbol.split("_")[0]  + " X" + volume + BaseApplication.getInstance().getString(R.string.hands);
            }

            public String formatSide() {
                return side == 0 ? BaseApplication.getInstance().getString(R.string.bullish) : BaseApplication.getInstance().getString(R.string.bearish);
            }

            public boolean isSideUp() {
                return side == 0;
            }

            public String formatDate() {
                return intType == 0 ? DateUtils.formatDate("yyyy-MM-dd HH:mm:ss", openTime) : DateUtils.formatDate("yyyy-MM-dd HH:mm:ss", closeTime);
            }

            public String formatOpenType() {
                return (priceType == 0 ? BaseApplication.getInstance().getString(R.string.market_price)
                        : BaseApplication.getInstance().getString(R.string.limit_price)) +
                        (side == 0 ? BaseApplication.getInstance().getString(R.string.buy)
                                : BaseApplication.getInstance().getString(R.string.sell));
            }

            public String formatMarginPrice() {
                return DfUtils.numberFormat(marginFee, 4);
            }

            public String formatHoldFee() {
                return DfUtils.numberFormat(holdFee, 4);
            }

            public String formatTradeSymbol() {
                return symbol.split("/")[0];
            }

            public String formatOpenFee() {
                return intType == 0 ? DfUtils.numberFormat(fee, 4) : DfUtils.numberFormat(openFee, 4);
            }

            public String formatStatue() {
                return intType == 0 ? BaseApplication.getInstance().getString(R.string.in_position) :
                        BaseApplication.getInstance().getString(R.string.completed);
            }

            public String formatVolume() {
                return String.valueOf(volume);
            }

            public String formatCloseFee() {
                return intType == 0 ? "--" : DfUtils.numberFormat(fee, 4);
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
                return intType != 0 ? (closeProfit >= 0 ? "+" + DfUtils.numberFormat(closeProfit, 4) : DfUtils.numberFormat(closeProfit, 4))
                        : profitAndlossStr;
            }
        }
    }
}
