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
 * 创建日期：2019-06-29
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CfdCommissionResult implements Parcelable {

    /**
     * code : 200
     * count : null
     * data : {"current":1,"pages":1,"records":[{"baseCoinScale":2,"cancelTime":2134323412312,"closeFee":0.002,"closePrice":0.2,"closeProfit":0.1,"closeTime":2134323412312,"closeType":1,"completeTime":2134323412312,"cost":0.2,"createTime":1561794439241,"fee":0.2,"hisType":1,"holdFee":0.2,"id":"G617944392417022","intType":1,"marginFee":115.21,"memberId":55,"multiplier":1,"openFee":11.5,"openPrice":0.2,"openTime":2134323412312,"orderId":"G617944392417022","positionId":1,"price":11499.99,"priceType":1,"side":0,"status":1,"stopLossPrice":0.21,"stopProfitPrice":0.21,"symbol":"BTC/USDT","type":1,"unit":"USDT","volume":1}],"size":10,"total":1}
     * message : SUCCESS
     * responseString : 200~SUCCESS
     */

    private int code;
    private Object count;
    private DataBean data;
    private String message;
    private String responseString;

    protected CfdCommissionResult(Parcel in) {
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
    }

    public static final Creator<CfdCommissionResult> CREATOR = new Creator<CfdCommissionResult>() {
        @Override
        public CfdCommissionResult createFromParcel(Parcel in) {
            return new CfdCommissionResult(in);
        }

        @Override
        public CfdCommissionResult[] newArray(int size) {
            return new CfdCommissionResult[size];
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
         * pages : 1
         * records : [{"baseCoinScale":2,"cancelTime":2134323412312,"closeFee":0.002,"closePrice":0.2,"closeProfit":0.1,"closeTime":2134323412312,"closeType":1,"completeTime":2134323412312,"cost":0.2,"createTime":1561794439241,"fee":0.2,"hisType":1,"holdFee":0.2,"id":"G617944392417022","intType":1,"marginFee":115.21,"memberId":55,"multiplier":1,"openFee":11.5,"openPrice":0.2,"openTime":2134323412312,"orderId":"G617944392417022","positionId":1,"price":11499.99,"priceType":1,"side":0,"status":1,"stopLossPrice":0.21,"stopProfitPrice":0.21,"symbol":"BTC/USDT","type":1,"unit":"USDT","volume":1}]
         * size : 10
         * total : 1
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

        public static class RecordsBean implements Parcelable{
            /**
             * baseCoinScale : 2
             * cancelTime : 2134323412312
             * closeFee : 0.002
             * closePrice : 0.2
             * closeProfit : 0.1
             * closeTime : 2134323412312
             * closeType : 1
             * completeTime : 2134323412312
             * cost : 0.2
             * createTime : 1561794439241
             * fee : 0.2
             * hisType : 1
             * holdFee : 0.2
             * id : G617944392417022
             * intType : 1
             * marginFee : 115.21
             * memberId : 55
             * multiplier : 1
             * openFee : 11.5
             * openPrice : 0.2
             * openTime : 2134323412312
             * orderId : G617944392417022
             * positionId : 1
             * price : 11499.99
             * priceType : 1
             * side : 0
             * status : 1
             * stopLossPrice : 0.21
             * stopProfitPrice : 0.21
             * symbol : BTC/USDT
             * type : 1
             * unit : USDT
             * volume : 1
             */

            private int baseCoinScale;
            private long cancelTime;
            private double closeFee;
            private double closePrice;
            private double closeProfit;
            private long closeTime;
            private int closeType;
            private long completeTime;
            private double cost;
            private long createTime;
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
            private int positionId;
            private double price;
            private int priceType;
            private int side;
            private int status;
            private double stopLossPrice;
            private double stopProfitPrice;
            private String symbol;
            private int type;
            private String unit;
            private int volume;
            private double currentPrice;

            public RecordsBean() {
            }

            protected RecordsBean(Parcel in) {
                baseCoinScale = in.readInt();
                cancelTime = in.readLong();
                closeFee = in.readDouble();
                closePrice = in.readDouble();
                closeProfit = in.readDouble();
                closeTime = in.readLong();
                closeType = in.readInt();
                completeTime = in.readLong();
                cost = in.readDouble();
                createTime = in.readLong();
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
                positionId = in.readInt();
                price = in.readDouble();
                priceType = in.readInt();
                side = in.readInt();
                status = in.readInt();
                stopLossPrice = in.readDouble();
                stopProfitPrice = in.readDouble();
                symbol = in.readString();
                type = in.readInt();
                unit = in.readString();
                volume = in.readInt();
                currentPrice = in.readDouble();
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

            public long getCancelTime() {
                return cancelTime;
            }

            public void setCancelTime(long cancelTime) {
                this.cancelTime = cancelTime;
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

            public long getCompleteTime() {
                return completeTime;
            }

            public void setCompleteTime(long completeTime) {
                this.completeTime = completeTime;
            }

            public double getCost() {
                return cost;
            }

            public void setCost(double cost) {
                this.cost = cost;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
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

            public int getPositionId() {
                return positionId;
            }

            public void setPositionId(int positionId) {
                this.positionId = positionId;
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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public int getVolume() {
                return volume;
            }

            public void setVolume(int volume) {
                this.volume = volume;
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
                            ("+" + DfUtils.numberFormat(currentPrice - openPrice, 4))
                            : DfUtils.numberFormat(currentPrice - openPrice, 4);
                } else {
                    profitAndlossStr = openPrice - currentPrice >= 0 ?
                            ("+" + DfUtils.numberFormat(openPrice - currentPrice, 4))
                            : DfUtils.numberFormat(openPrice - currentPrice, 4);
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
                return DfUtils.numberFormat(price, 4);
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
                return DateUtils.formatDate("MM/dd HH:mm", createTime);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeInt(baseCoinScale);
                parcel.writeLong(cancelTime);
                parcel.writeDouble(closeFee);
                parcel.writeDouble(closePrice);
                parcel.writeDouble(closeProfit);
                parcel.writeLong(closeTime);
                parcel.writeInt(closeType);
                parcel.writeLong(completeTime);
                parcel.writeDouble(cost);
                parcel.writeLong(createTime);
                parcel.writeDouble(fee);
                parcel.writeInt(hisType);
                parcel.writeDouble(holdFee);
                parcel.writeString(id);
                parcel.writeInt(intType);
                parcel.writeDouble(marginFee);
                parcel.writeInt(memberId);
                parcel.writeInt(multiplier);
                parcel.writeDouble(openFee);
                parcel.writeDouble(openPrice);
                parcel.writeLong(openTime);
                parcel.writeString(orderId);
                parcel.writeInt(positionId);
                parcel.writeDouble(price);
                parcel.writeInt(priceType);
                parcel.writeInt(side);
                parcel.writeInt(status);
                parcel.writeDouble(stopLossPrice);
                parcel.writeDouble(stopProfitPrice);
                parcel.writeString(symbol);
                parcel.writeInt(type);
                parcel.writeString(unit);
                parcel.writeInt(volume);
                parcel.writeDouble(currentPrice);
            }

            @Override
            public boolean equals(Object obj) {
                if (obj instanceof RecordsBean) {
                    RecordsBean dataBean = (RecordsBean) obj;
                    return this.id.equals(dataBean.getId());
                }
                return super.equals(obj);
            }
        }
    }
}
