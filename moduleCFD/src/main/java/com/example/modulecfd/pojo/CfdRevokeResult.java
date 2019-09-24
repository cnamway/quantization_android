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
public class CfdRevokeResult implements Parcelable {

    /**
     * code : 200
     * count : null
     * data : {"current":1,"pages":2,"records":[{"baseCoinScale":2,"cancelTime":1561799425973,"closeFee":0.002,"completeTime":1561799425973,"cost":0.2,"createTime":1561794439241,"id":"G617944392417022","ip":"192.168.2.111","isSimulate":0,"marginFee":115.21,"marginLever":100,"memberId":55,"multiplier":1,"openFee":11.5,"orderType":0,"price":11499.99,"priceType":1,"side":0,"status":6,"stopLossPrice":0.21,"stopProfitPrice":0.21,"symbol":"BTC/USDT","type":1,"unit":"USDT","volume":1,"walletKey":"USDT55"}],"size":10,"total":15}
     * message : SUCCESS
     * responseString : 200~SUCCESS
     */

    private int code;
    private Object count;
    private DataBean data;
    private String message;
    private String responseString;

    protected CfdRevokeResult(Parcel in) {
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
    }

    public static final Creator<CfdRevokeResult> CREATOR = new Creator<CfdRevokeResult>() {
        @Override
        public CfdRevokeResult createFromParcel(Parcel in) {
            return new CfdRevokeResult(in);
        }

        @Override
        public CfdRevokeResult[] newArray(int size) {
            return new CfdRevokeResult[size];
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
         * pages : 2
         * records : [{"baseCoinScale":2,"cancelTime":1561799425973,"closeFee":0.002,"completeTime":1561799425973,"cost":0.2,"createTime":1561794439241,"id":"G617944392417022","ip":"192.168.2.111","isSimulate":0,"marginFee":115.21,"marginLever":100,"memberId":55,"multiplier":1,"openFee":11.5,"orderType":0,"price":11499.99,"priceType":1,"side":0,"status":6,"stopLossPrice":0.21,"stopProfitPrice":0.21,"symbol":"BTC/USDT","type":1,"unit":"USDT","volume":1,"walletKey":"USDT55"}]
         * size : 10
         * total : 15
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
             * cancelTime : 1561799425973
             * closeFee : 0.002
             * completeTime : 1561799425973
             * cost : 0.2
             * createTime : 1561794439241
             * id : G617944392417022
             * ip : 192.168.2.111
             * isSimulate : 0
             * marginFee : 115.21
             * marginLever : 100
             * memberId : 55
             * multiplier : 1
             * openFee : 11.5
             * orderType : 0
             * price : 11499.99
             * priceType : 1
             * side : 0
             * status : 6
             * stopLossPrice : 0.21
             * stopProfitPrice : 0.21
             * symbol : BTC/USDT
             * type : 1
             * unit : USDT
             * volume : 1
             * walletKey : USDT55
             */

            private int baseCoinScale;
            private long cancelTime;
            private double closeFee;
            private long completeTime;
            private double cost;
            private long createTime;
            private String id;
            private String ip;
            private int isSimulate;
            private double marginFee;
            private int marginLever;
            private int memberId;
            private int multiplier;
            private double openFee;
            private int orderType;
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
            private String walletKey;

            public RecordsBean() {
            }

            protected RecordsBean(Parcel in) {
                baseCoinScale = in.readInt();
                cancelTime = in.readLong();
                closeFee = in.readDouble();
                completeTime = in.readLong();
                cost = in.readDouble();
                createTime = in.readLong();
                id = in.readString();
                ip = in.readString();
                isSimulate = in.readInt();
                marginFee = in.readDouble();
                marginLever = in.readInt();
                memberId = in.readInt();
                multiplier = in.readInt();
                openFee = in.readDouble();
                orderType = in.readInt();
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
                walletKey = in.readString();
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

            public String getWalletKey() {
                return walletKey;
            }

            public void setWalletKey(String walletKey) {
                this.walletKey = walletKey;
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

            public String formatPrice() {
                return DfUtils.numberFormat(price, 4);
            }

            public String formatStopProfitPrice() {
                return DfUtils.numberFormat(stopProfitPrice, 4);
            }

            public String formatStopLossPrice() {
                return DfUtils.numberFormat(stopLossPrice, 4);
            }

            public String formatOpenDate() {
                return DateUtils.formatDate("MM/dd HH:mm", createTime);
            }

            public String formatCloseDate() {
                return DateUtils.formatDate("MM/dd HH:mm", cancelTime);
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
                parcel.writeLong(completeTime);
                parcel.writeDouble(cost);
                parcel.writeLong(createTime);
                parcel.writeString(id);
                parcel.writeString(ip);
                parcel.writeInt(isSimulate);
                parcel.writeDouble(marginFee);
                parcel.writeInt(marginLever);
                parcel.writeInt(memberId);
                parcel.writeInt(multiplier);
                parcel.writeDouble(openFee);
                parcel.writeInt(orderType);
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
                parcel.writeString(walletKey);
            }

            @Override
            public boolean equals(Object obj) {
                if (obj instanceof CfdDealResult.DataBean.RecordsBean) {
                    CfdDealResult.DataBean.RecordsBean dataBean = (CfdDealResult.DataBean.RecordsBean) obj;
                    return this.id.equals(dataBean.getId());
                }
                return super.equals(obj);
            }
        }
    }
}
