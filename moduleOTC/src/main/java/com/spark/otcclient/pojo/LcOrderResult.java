package com.spark.otcclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.spark.otcclient.R;

import java.util.List;

import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.utils.DateUtils;
import me.spark.mvvm.utils.DfUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/11
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class LcOrderResult implements Parcelable {

    /**
     * cid : string
     * code : 0
     * count : {}
     * data : {"current":0,"pages":0,"records":[{"actualPayment":"string","advertiseId":0,"coinName":"string","commission":0.1,"createTime":"2019-06-11T13:10:41.583Z","customerId":0,"customerName":"string","localCurrency":"string","memberId":0,"money":0.1,"number":0.1,"orderSn":"string","orderType":"string","payData":"string","payMode":"string","payRefer":"string","payTime":"2019-06-11T13:10:41.583Z","price":0.1,"releaseTime":"2019-06-11T13:10:41.583Z","status":0,"tradeToUsername":"string","tradeType":0,"trateToRealname":"string","userName":"string"}],"size":0,"total":0}
     * message : string
     * responseString : string
     * url : string
     */

    private String cid;
    private int code;
    private CountBean count;
    private DataBean data;
    private String message;
    private String responseString;
    private String url;

    protected LcOrderResult(Parcel in) {
        cid = in.readString();
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
        url = in.readString();
    }

    public static final Creator<LcOrderResult> CREATOR = new Creator<LcOrderResult>() {
        @Override
        public LcOrderResult createFromParcel(Parcel in) {
            return new LcOrderResult(in);
        }

        @Override
        public LcOrderResult[] newArray(int size) {
            return new LcOrderResult[size];
        }
    };

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public CountBean getCount() {
        return count;
    }

    public void setCount(CountBean count) {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cid);
        dest.writeInt(code);
        dest.writeString(message);
        dest.writeString(responseString);
        dest.writeString(url);
    }

    public static class CountBean {
    }

    public static class DataBean {
        /**
         * current : 0
         * pages : 0
         * records : [{"actualPayment":"string","advertiseId":0,"coinName":"string","commission":0.1,"createTime":"2019-06-11T13:10:41.583Z","customerId":0,"customerName":"string","localCurrency":"string","memberId":0,"money":0.1,"number":0.1,"orderSn":"string","orderType":"string","payData":"string","payMode":"string","payRefer":"string","payTime":"2019-06-11T13:10:41.583Z","price":0.1,"releaseTime":"2019-06-11T13:10:41.583Z","status":0,"tradeToUsername":"string","tradeType":0,"trateToRealname":"string","userName":"string"}]
         * size : 0
         * total : 0
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
             * actualPayment : string
             * advertiseId : 0
             * coinName : string
             * commission : 0.1
             * createTime : 2019-06-11T13:10:41.583Z
             * customerId : 0
             * customerName : string
             * localCurrency : string
             * memberId : 0
             * money : 0.1
             * number : 0.1
             * orderSn : string
             * orderType : string
             * payData : string
             * payMode : string
             * payRefer : string
             * payTime : 2019-06-11T13:10:41.583Z
             * price : 0.1
             * releaseTime : 2019-06-11T13:10:41.583Z
             * status : 0
             * tradeToUsername : string
             * tradeType : 0
             * trateToRealname : string
             * userName : string
             */

            private String actualPayment;
            private int advertiseId;
            private String coinName;
            private double commission;
            private String createTime;
            private int customerId;
            private String customerName;
            private String localCurrency;
            private int memberId;
            private double money;
            private double number;
            private String orderSn;
            private String orderType;
            private String payData;
            private String payMode;
            private String payRefer;
            private String payTime;
            private double price;
            private String releaseTime;
            private int status;
            private String tradeToUsername;
            private int tradeType;
            private String trateToRealname;
            private String userName;

            protected RecordsBean(Parcel in) {
                actualPayment = in.readString();
                advertiseId = in.readInt();
                coinName = in.readString();
                commission = in.readDouble();
                createTime = in.readString();
                customerId = in.readInt();
                customerName = in.readString();
                localCurrency = in.readString();
                memberId = in.readInt();
                money = in.readDouble();
                number = in.readDouble();
                orderSn = in.readString();
                orderType = in.readString();
                payData = in.readString();
                payMode = in.readString();
                payRefer = in.readString();
                payTime = in.readString();
                price = in.readDouble();
                releaseTime = in.readString();
                status = in.readInt();
                tradeToUsername = in.readString();
                tradeType = in.readInt();
                trateToRealname = in.readString();
                userName = in.readString();
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

            public String getActualPayment() {
                return actualPayment;
            }

            public void setActualPayment(String actualPayment) {
                this.actualPayment = actualPayment;
            }

            public int getAdvertiseId() {
                return advertiseId;
            }

            public void setAdvertiseId(int advertiseId) {
                this.advertiseId = advertiseId;
            }

            public String getCoinName() {
                return coinName;
            }

            public void setCoinName(String coinName) {
                this.coinName = coinName;
            }

            public double getCommission() {
                return commission;
            }

            public void setCommission(double commission) {
                this.commission = commission;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getCustomerId() {
                return customerId;
            }

            public void setCustomerId(int customerId) {
                this.customerId = customerId;
            }

            public String getCustomerName() {
                return customerName;
            }

            public void setCustomerName(String customerName) {
                this.customerName = customerName;
            }

            public String getLocalCurrency() {
                return localCurrency;
            }

            public void setLocalCurrency(String localCurrency) {
                this.localCurrency = localCurrency;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public double getMoney() {
                return money;
            }

            public void setMoney(double money) {
                this.money = money;
            }

            public double getNumber() {
                return number;
            }

            public void setNumber(double number) {
                this.number = number;
            }

            public String getOrderSn() {
                return orderSn;
            }

            public void setOrderSn(String orderSn) {
                this.orderSn = orderSn;
            }

            public String getOrderType() {
                String typeText = "";
                if (orderType.equals("0")) {
                    if (String.valueOf(memberId).equals(BaseApplication.getInstance().getCurrentUser().getId())) {
                        typeText = "1";
                    } else {
                        typeText = "0";
                    }
                }
                if (orderType.equals("1")) {
                    if (String.valueOf(memberId).equals(BaseApplication.getInstance().getCurrentUser().getId())) {
                        typeText = "0";
                    } else {
                        typeText = "1";
                    }
                }

                return typeText;
            }

            public void setOrderType(String orderType) {
                this.orderType = orderType;
            }

            public String getPayData() {
                return payData;
            }

            public void setPayData(String payData) {
                this.payData = payData;
            }

            public String getPayMode() {
                return payMode;
            }

            public void setPayMode(String payMode) {
                this.payMode = payMode;
            }

            public String getPayRefer() {
                return payRefer;
            }

            public void setPayRefer(String payRefer) {
                this.payRefer = payRefer;
            }

            public String getPayTime() {
                return payTime;
            }

            public void setPayTime(String payTime) {
                this.payTime = payTime;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getReleaseTime() {
                return releaseTime;
            }

            public void setReleaseTime(String releaseTime) {
                this.releaseTime = releaseTime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getTradeToUsername() {
                if (orderType.equals("0")) {
                    return (initTypeColor() ? tradeToUsername : trateToRealname);
                }
                if (orderType.equals("1")) {
                    return (initTypeColor() ? trateToRealname : tradeToUsername);
                }
                return "";
            }

            public void setTradeToUsername(String tradeToUsername) {
                this.tradeToUsername = tradeToUsername;
            }

            public int getTradeType() {
                return tradeType;
            }

            public void setTradeType(int tradeType) {
                this.tradeType = tradeType;
            }

            public String getTrateToRealname() {
                return trateToRealname;
            }

            public void setTrateToRealname(String trateToRealname) {
                this.trateToRealname = trateToRealname;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }


            public String initType() {
                String typeText = "";
                if (orderType.equals("0")) {
                    if (String.valueOf(memberId).equals(BaseApplication.getInstance().getCurrentUser().getId())) {
                        typeText = BaseApplication.getInstance().getString(R.string.sell2);
                    } else {
                        typeText = BaseApplication.getInstance().getString(R.string.buy2);
                    }
                }
                if (orderType.equals("1")) {
                    if (String.valueOf(memberId).equals(BaseApplication.getInstance().getCurrentUser().getId())) {
                        typeText = BaseApplication.getInstance().getString(R.string.buy2);
                    } else {
                        typeText = BaseApplication.getInstance().getString(R.string.sell2);
                    }
                }

                return typeText;
            }

            public boolean initTypeColor() {
                return initType().equals(BaseApplication.getInstance().getString(R.string.buy2));
            }

            public String initStatueType() {
                String typeText = "";
                switch (status) {
                    case 0:
                        typeText = BaseApplication.getInstance().getString(R.string.cancelled);
                        break;
                    case 1:
                        typeText = BaseApplication.getInstance().getString(R.string.str_un_pay);
                        break;
                    case 2:
                        typeText = BaseApplication.getInstance().getString(R.string.str_paid);
                        break;
                    case 3:
                        typeText = BaseApplication.getInstance().getString(R.string.completed);
                        break;
                    case 4:
                        typeText = BaseApplication.getInstance().getString(R.string.str_order_appealing);
                        break;
                }

                return typeText;
            }

            public String initNumber() {
                return BaseApplication.getInstance().getString(R.string.number) + "(" + coinName + ")";
            }

            public String initNumberText() {
                return DfUtils.numberFormat(number, number == 0 ? 0 : 8) + " " + coinName;
            }

            public String initMoneyText() {
                return money + " CNY";
            }

            public String formatDate() {
                return DateUtils.formatDate("yyyy.MM.dd HH:mm", createTime);
            }

            public String initTradeToUsername() {
                if (orderType.equals("0")) {
                    return (initTypeColor() ? BaseApplication.getInstance().getString(R.string.str_seller) + tradeToUsername : BaseApplication.getInstance().getString(R.string.str_buyer) + trateToRealname);
                }
                if (orderType.equals("1")) {
                    return (initTypeColor() ? BaseApplication.getInstance().getString(R.string.str_seller) + trateToRealname : BaseApplication.getInstance().getString(R.string.str_buyer) + tradeToUsername);
                }
                return "";
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(actualPayment);
                dest.writeInt(advertiseId);
                dest.writeString(coinName);
                dest.writeDouble(commission);
                dest.writeString(createTime);
                dest.writeInt(customerId);
                dest.writeString(customerName);
                dest.writeString(localCurrency);
                dest.writeInt(memberId);
                dest.writeDouble(money);
                dest.writeDouble(number);
                dest.writeString(orderSn);
                dest.writeString(orderType);
                dest.writeString(payData);
                dest.writeString(payMode);
                dest.writeString(payRefer);
                dest.writeString(payTime);
                dest.writeDouble(price);
                dest.writeString(releaseTime);
                dest.writeInt(status);
                dest.writeString(tradeToUsername);
                dest.writeInt(tradeType);
                dest.writeString(trateToRealname);
                dest.writeString(userName);
            }

            @Override
            public String toString() {
                return "RecordsBean{" +
                        "actualPayment='" + actualPayment + '\'' +
                        ", advertiseId=" + advertiseId +
                        ", coinName='" + coinName + '\'' +
                        ", commission=" + commission +
                        ", createTime='" + createTime + '\'' +
                        ", customerId=" + customerId +
                        ", customerName='" + customerName + '\'' +
                        ", localCurrency='" + localCurrency + '\'' +
                        ", memberId=" + memberId +
                        ", money=" + money +
                        ", number=" + number +
                        ", orderSn='" + orderSn + '\'' +
                        ", orderType='" + orderType + '\'' +
                        ", payData='" + payData + '\'' +
                        ", payMode='" + payMode + '\'' +
                        ", payRefer='" + payRefer + '\'' +
                        ", payTime='" + payTime + '\'' +
                        ", price=" + price +
                        ", releaseTime='" + releaseTime + '\'' +
                        ", status=" + status +
                        ", tradeToUsername='" + tradeToUsername + '\'' +
                        ", tradeType=" + tradeType +
                        ", trateToRealname='" + trateToRealname + '\'' +
                        ", userName='" + userName + '\'' +
                        '}';
            }
        }
    }
}
