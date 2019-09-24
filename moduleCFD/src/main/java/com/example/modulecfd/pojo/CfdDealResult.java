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
public class CfdDealResult implements Parcelable {

    /**
     * code : 200
     * count : null
     * data : {"current":1,"pages":4,"records":[{"baseCoinScale":2,"closePrice":11614.99,"closeProfit":7.01,"closeTime":1561710482674,"closeType":2,"entrustTime":1561710386477,"fee":23.23,"holdFee":0.21,"id":"GC617104826740484","ip":"192.168.2.24","isSimulate":0,"marginFee":116.08,"marginLever":100,"memberId":55,"multiplier":1,"openFee":11.61,"openPrice":11607.98,"openTime":1561710386507,"orderId":"G617103864762922","orderType":0,"positionId":"GP617103865073632","price":11607.98,"priceType":0,"side":0,"stopLossPrice":0.21,"stopProfitPrice":0.21,"symbol":"BTC/USDT","volume":1}],"size":10,"total":39}
     * message : SUCCESS
     * responseString : 200~SUCCESS
     */

    private int code;
    private Object count;
    private DataBean data;
    private String message;
    private String responseString;

    protected CfdDealResult(Parcel in) {
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
    }

    public static final Creator<CfdDealResult> CREATOR = new Creator<CfdDealResult>() {
        @Override
        public CfdDealResult createFromParcel(Parcel in) {
            return new CfdDealResult(in);
        }

        @Override
        public CfdDealResult[] newArray(int size) {
            return new CfdDealResult[size];
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
         * pages : 4
         * records : [{"baseCoinScale":2,"closePrice":11614.99,"closeProfit":7.01,"closeTime":1561710482674,"closeType":2,"entrustTime":1561710386477,"fee":23.23,"holdFee":0.21,"id":"GC617104826740484","ip":"192.168.2.24","isSimulate":0,"marginFee":116.08,"marginLever":100,"memberId":55,"multiplier":1,"openFee":11.61,"openPrice":11607.98,"openTime":1561710386507,"orderId":"G617103864762922","orderType":0,"positionId":"GP617103865073632","price":11607.98,"priceType":0,"side":0,"stopLossPrice":0.21,"stopProfitPrice":0.21,"symbol":"BTC/USDT","volume":1}]
         * size : 10
         * total : 39
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
             * closePrice : 11614.99
             * closeProfit : 7.01
             * closeTime : 1561710482674
             * closeType : 2
             * entrustTime : 1561710386477
             * fee : 23.23
             * holdFee : 0.21
             * id : GC617104826740484
             * ip : 192.168.2.24
             * isSimulate : 0
             * marginFee : 116.08
             * marginLever : 100
             * memberId : 55
             * multiplier : 1
             * openFee : 11.61
             * openPrice : 11607.98
             * openTime : 1561710386507
             * orderId : G617103864762922
             * orderType : 0
             * positionId : GP617103865073632
             * price : 11607.98
             * priceType : 0
             * side : 0
             * stopLossPrice : 0.21
             * stopProfitPrice : 0.21
             * symbol : BTC/USDT
             * volume : 1
             */

            private int baseCoinScale;
            private double closePrice;
            private double closeProfit;
            private long closeTime;
            private int closeType;
            private long entrustTime;
            private double fee;
            private double holdFee;
            private String id;
            private String ip;
            private int isSimulate;
            private double marginFee;
            private int marginLever;
            private int memberId;
            private int multiplier;
            private double openFee;
            private double openPrice;
            private long openTime;
            private String orderId;
            private int orderType;
            private String positionId;
            private double price;
            private int priceType;
            private int side;
            private double stopLossPrice;
            private double stopProfitPrice;
            private String symbol;
            private int volume;

            public RecordsBean() {
            }

            protected RecordsBean(Parcel in) {
                baseCoinScale = in.readInt();
                closePrice = in.readDouble();
                closeProfit = in.readDouble();
                closeTime = in.readLong();
                closeType = in.readInt();
                entrustTime = in.readLong();
                fee = in.readDouble();
                holdFee = in.readDouble();
                id = in.readString();
                ip = in.readString();
                isSimulate = in.readInt();
                marginFee = in.readDouble();
                marginLever = in.readInt();
                memberId = in.readInt();
                multiplier = in.readInt();
                openFee = in.readDouble();
                openPrice = in.readDouble();
                openTime = in.readLong();
                orderId = in.readString();
                orderType = in.readInt();
                positionId = in.readString();
                price = in.readDouble();
                priceType = in.readInt();
                side = in.readInt();
                stopLossPrice = in.readDouble();
                stopProfitPrice = in.readDouble();
                symbol = in.readString();
                volume = in.readInt();
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

            public int getOrderType() {
                return orderType;
            }

            public void setOrderType(int orderType) {
                this.orderType = orderType;
            }

            public String getPositionId() {
                return positionId;
            }

            public void setPositionId(String positionId) {
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

            public String formatProfitAndloss() {
                String profitAndlossStr;

                double profitAndloss = (openPrice - closePrice) * volume * multiplier;
                if (side == 0) {
                    if (profitAndloss < 0) {
                        profitAndlossStr = "+" + DfUtils.numberFormat(Math.abs(profitAndloss * -1), 4);
                    } else {
                        profitAndlossStr = DfUtils.numberFormat(profitAndloss * -1, 4);
                    }
                } else {
                    if (profitAndloss > 0) {
                        profitAndlossStr = "+" + DfUtils.numberFormat(Math.abs(profitAndloss), 4);
                    } else {
                        profitAndlossStr = DfUtils.numberFormat(profitAndloss, 4);
                    }
                }
                return profitAndlossStr;
            }

            public boolean isProfitAndloss() {
                return Double.valueOf(formatProfitAndloss()) >= 0;
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


            public String formatOpenPrice() {
                return DfUtils.numberFormat(openPrice, 4);
            }

            public String formatClosePrice() {
                return DfUtils.numberFormat(closePrice, 4);
            }

            public String formatStopProfitPrice() {
                return DfUtils.numberFormat(stopProfitPrice, 4);
            }

            public String formatStopLossPrice() {
                return DfUtils.numberFormat(stopLossPrice, 4);
            }

            public String formatOpenDate() {
                return DateUtils.formatDate("MM/dd HH:mm", openTime);
            }

            public String formatCloseDate() {
                return DateUtils.formatDate("MM/dd HH:mm", closeTime);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeInt(baseCoinScale);
                parcel.writeDouble(closePrice);
                parcel.writeDouble(closeProfit);
                parcel.writeLong(closeTime);
                parcel.writeInt(closeType);
                parcel.writeLong(entrustTime);
                parcel.writeDouble(fee);
                parcel.writeDouble(holdFee);
                parcel.writeString(id);
                parcel.writeString(ip);
                parcel.writeInt(isSimulate);
                parcel.writeDouble(marginFee);
                parcel.writeInt(marginLever);
                parcel.writeInt(memberId);
                parcel.writeInt(multiplier);
                parcel.writeDouble(openFee);
                parcel.writeDouble(openPrice);
                parcel.writeLong(openTime);
                parcel.writeString(orderId);
                parcel.writeInt(orderType);
                parcel.writeString(positionId);
                parcel.writeDouble(price);
                parcel.writeInt(priceType);
                parcel.writeInt(side);
                parcel.writeDouble(stopLossPrice);
                parcel.writeDouble(stopProfitPrice);
                parcel.writeString(symbol);
                parcel.writeInt(volume);
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
