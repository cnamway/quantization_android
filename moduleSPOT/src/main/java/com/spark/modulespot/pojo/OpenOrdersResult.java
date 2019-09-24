package com.spark.modulespot.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.spark.modulespot.R;

import java.util.List;

import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.utils.MathUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/13
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class OpenOrdersResult implements Parcelable {

    /**
     * code : 200
     * message : SUCCESS
     * data : {"pageNo":1,"pageSize":10,"totalPage":1,"totalNum":4,"list":[{"orderId":"E28358384947751859126","memberId":54,"symbol":"BTC/USDT","side":1,"priceType":0,"orderQty":23.21,"price":7959.81,"transactTime":1560215840454,"status":1,"remark":"","orderType":0,"tradedAmount":0.01558,"actualTradedAmount":0.21,"fee":0.24511207,"turnover":122.556035,"completedTime":1560215840454,"canceledTime":1560215840454,"coinSymbol":"BTC","baseSymbol":"USDT","server":"0","details":[]}],"records":null}
     * count : null
     * responseString : 200~SUCCESS
     * url : null
     * cid : null
     */

    private int code;
    private String message;
    private DataBean data;
    private Object count;
    private String responseString;
    private Object url;
    private Object cid;
    private static boolean isHistoryOrder;

    protected OpenOrdersResult(Parcel in) {
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
    }

    public static final Creator<OpenOrdersResult> CREATOR = new Creator<OpenOrdersResult>() {
        @Override
        public OpenOrdersResult createFromParcel(Parcel in) {
            return new OpenOrdersResult(in);
        }

        @Override
        public OpenOrdersResult[] newArray(int size) {
            return new OpenOrdersResult[size];
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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


    public boolean isHistoryOrder() {
        return isHistoryOrder;
    }

    public void setHistoryOrder(boolean historyOrder) {
        isHistoryOrder = historyOrder;
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
         * pageNo : 1
         * pageSize : 10
         * totalPage : 1
         * totalNum : 4
         * list : [{"orderId":"E28358384947751859126","memberId":54,"symbol":"BTC/USDT","side":1,"priceType":0,"orderQty":23.21,"price":7959.81,"transactTime":1560215840454,"status":1,"remark":"","orderType":0,"tradedAmount":0.01558,"actualTradedAmount":0.21,"fee":0.24511207,"turnover":122.556035,"completedTime":1560215840454,"canceledTime":1560215840454,"coinSymbol":"BTC","baseSymbol":"USDT","server":"0","details":[]}]
         * records : null
         */

        private int pageNo;
        private int pageSize;
        private int totalPage;
        private int totalNum;
        private Object records;
        private List<ListBean> list;

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public Object getRecords() {
            return records;
        }

        public void setRecords(Object records) {
            this.records = records;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Parcelable {
            /**
             * orderId : E28358384947751859126
             * memberId : 54
             * symbol : BTC/USDT
             * side : 1
             * priceType : 0
             * orderQty : 23.21
             * price : 7959.81
             * transactTime : 1560215840454
             * status : 1
             * remark :
             * orderType : 0
             * tradedAmount : 0.01558
             * actualTradedAmount : 0.21
             * fee : 0.24511207
             * turnover : 122.556035
             * completedTime : 1560215840454
             * canceledTime : 1560215840454
             * coinSymbol : BTC
             * baseSymbol : USDT
             * server : 0
             * details : []
             */

            private String orderId;
            private int memberId;
            private String symbol;
            private int side;
            private int priceType;
            private double orderQty;
            private double price;
            private long transactTime;
            private int status;
            private String remark;
            private int orderType;
            private double tradedAmount;
            private double actualTradedAmount;
            private double fee;
            private double turnover;
            private long completedTime;
            private long canceledTime;
            private String coinSymbol;
            private String baseSymbol;
            private String server;
            private List<?> details;

            protected ListBean(Parcel in) {
                orderId = in.readString();
                memberId = in.readInt();
                symbol = in.readString();
                side = in.readInt();
                priceType = in.readInt();
                orderQty = in.readDouble();
                price = in.readDouble();
                transactTime = in.readLong();
                status = in.readInt();
                remark = in.readString();
                orderType = in.readInt();
                tradedAmount = in.readDouble();
                actualTradedAmount = in.readDouble();
                fee = in.readDouble();
                turnover = in.readDouble();
                completedTime = in.readLong();
                canceledTime = in.readLong();
                coinSymbol = in.readString();
                baseSymbol = in.readString();
                server = in.readString();
            }

            public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
                @Override
                public ListBean createFromParcel(Parcel in) {
                    return new ListBean(in);
                }

                @Override
                public ListBean[] newArray(int size) {
                    return new ListBean[size];
                }
            };

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public String getSymbol() {
                return symbol;
            }

            public void setSymbol(String symbol) {
                this.symbol = symbol;
            }

            public int getSide() {
                return side;
            }

            public void setSide(int side) {
                this.side = side;
            }

            public int getPriceType() {
                return priceType;
            }

            public void setPriceType(int priceType) {
                this.priceType = priceType;
            }

            public double getOrderQty() {
                return orderQty;
            }

            public void setOrderQty(double orderQty) {
                this.orderQty = orderQty;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public long getTransactTime() {
                return transactTime;
            }

            public void setTransactTime(long transactTime) {
                this.transactTime = transactTime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getOrderType() {
                return orderType;
            }

            public void setOrderType(int orderType) {
                this.orderType = orderType;
            }

            public double getTradedAmount() {
                return tradedAmount;
            }

            public void setTradedAmount(double tradedAmount) {
                this.tradedAmount = tradedAmount;
            }

            public double getActualTradedAmount() {
                return actualTradedAmount;
            }

            public void setActualTradedAmount(double actualTradedAmount) {
                this.actualTradedAmount = actualTradedAmount;
            }

            public double getFee() {
                return fee;
            }

            public void setFee(double fee) {
                this.fee = fee;
            }

            public double getTurnover() {
                return turnover;
            }

            public void setTurnover(double turnover) {
                this.turnover = turnover;
            }

            public long getCompletedTime() {
                return completedTime;
            }

            public void setCompletedTime(long completedTime) {
                this.completedTime = completedTime;
            }

            public long getCanceledTime() {
                return canceledTime;
            }

            public void setCanceledTime(long canceledTime) {
                this.canceledTime = canceledTime;
            }

            public String getCoinSymbol() {
                return coinSymbol;
            }

            public void setCoinSymbol(String coinSymbol) {
                this.coinSymbol = coinSymbol;
            }

            public String getBaseSymbol() {
                return baseSymbol;
            }

            public void setBaseSymbol(String baseSymbol) {
                this.baseSymbol = baseSymbol;
            }

            public String getServer() {
                return server;
            }

            public void setServer(String server) {
                this.server = server;
            }

            public List<?> getDetails() {
                return details;
            }

            public void setDetails(List<?> details) {
                this.details = details;
            }


            public String initType() {
                String typeText = "";
                switch (side) {
                    case 0:
                        typeText = BaseApplication.getInstance().getString(R.string.buy);
                        break;
                    case 1:
                        typeText = BaseApplication.getInstance().getString(R.string.sell);
                        break;
                }
                return typeText;
            }

            public boolean initTypeColor() {
                boolean typeColor = false;
                switch (side) {
                    case 0:
                        typeColor = true;
                        break;
                    case 1:
                        typeColor = false;
                        break;
                }

                return typeColor;
            }


            public String formatDate() {
                return me.spark.mvvm.utils.DateUtils.formatDate("yyyy.MM.dd HH:mm:ss", transactTime);
            }

            public String initStatueType() {
                String typeText;
                if (status == 5) {
                    typeText = BaseApplication.getInstance().getString(R.string.completed);
                } else {
                    typeText = isHistoryOrder ? BaseApplication.getInstance().getString(R.string.cancelled) : BaseApplication.getInstance().getString(R.string.undo);
                }
                return typeText;
            }

            public String initPrice() {
                return BaseApplication.getInstance().getString(R.string.price) + "(" + baseSymbol + ")";
            }

            public String initPriceText() {
                String text = "";
                switch (priceType) {
                    //市价
                    case 0:
                        text = BaseApplication.getInstance().getString(R.string.market_price);
                        break;
                    //限价
                    case 1:
                        text = MathUtils.getRundNumber(price, 4, null);
                        break;
                }
                return text;
            }

            public String initNumber() {
                return BaseApplication.getInstance().getString(R.string.number) + "(" + coinSymbol + ")";
            }

            public String initNumberText() {
                String text;
                if (priceType == 0 && side == 0) {
                    text = "--";
                } else {
                    text = MathUtils.getRundNumber(orderQty, 4, null);
                }
                return text;
            }

            public String initDeal() {
                return BaseApplication.getInstance().getString(R.string.traded) + "(" + coinSymbol + ")";
            }

            public String initDealText() {
                return MathUtils.getRundNumber(tradedAmount, 4, null);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(orderId);
                dest.writeInt(memberId);
                dest.writeString(symbol);
                dest.writeInt(side);
                dest.writeInt(priceType);
                dest.writeDouble(orderQty);
                dest.writeDouble(price);
                dest.writeLong(transactTime);
                dest.writeInt(status);
                dest.writeString(remark);
                dest.writeInt(orderType);
                dest.writeDouble(tradedAmount);
                dest.writeDouble(actualTradedAmount);
                dest.writeDouble(fee);
                dest.writeDouble(turnover);
                dest.writeLong(completedTime);
                dest.writeLong(canceledTime);
                dest.writeString(coinSymbol);
                dest.writeString(baseSymbol);
                dest.writeString(server);
            }
        }
    }

}


