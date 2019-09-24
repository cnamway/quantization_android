package com.spark.otcclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import me.spark.mvvm.base.BaseApplication;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/17
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class OrderDetailsResult implements Parcelable {

    /**
     * cid : string
     * code : 0
     * count : {}
     * data : {"actualPayment":"string","advertiseId":0,"coinName":"string","commission":0.2,"createTime":"2019-06-17T12:34:53.618Z","currentTime":"2019-06-17T12:34:53.618Z","customerId":0,"customerName":"string","localCurrency":"string","memberId":0,"money":0.2,"number":0.2,"orderSn":"string","orderType":"string","payData":"string","payMode":"string","payRefer":"string","payTime":"2019-06-17T12:34:53.618Z","price":0.2,"releaseTime":"2019-06-17T12:34:53.618Z","remark":"string","status":0,"timeLimit":0,"tradeToUsername":"string","tradeType":0,"trateToRealname":"string","userName":"string"}
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

    protected OrderDetailsResult(Parcel in) {
        cid = in.readString();
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
        url = in.readString();
    }

    public static final Creator<OrderDetailsResult> CREATOR = new Creator<OrderDetailsResult>() {
        @Override
        public OrderDetailsResult createFromParcel(Parcel in) {
            return new OrderDetailsResult(in);
        }

        @Override
        public OrderDetailsResult[] newArray(int size) {
            return new OrderDetailsResult[size];
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
         * actualPayment : string
         * advertiseId : 0
         * coinName : string
         * commission : 0.2
         * createTime : 2019-06-17T12:34:53.618Z
         * currentTime : 2019-06-17T12:34:53.618Z
         * customerId : 0
         * customerName : string
         * localCurrency : string
         * memberId : 0
         * money : 0.2
         * number : 0.2
         * orderSn : string
         * orderType : string
         * payData : string
         * payMode : string
         * payRefer : string
         * payTime : 2019-06-17T12:34:53.618Z
         * price : 0.2
         * releaseTime : 2019-06-17T12:34:53.618Z
         * remark : string
         * status : 0
         * timeLimit : 0
         * tradeToUsername : string
         * tradeType : 0
         * trateToRealname : string
         * userName : string
         */

        private String actualPayment;
        private int advertiseId;
        private String coinName;
        private double commission;
        private long createTime;
        private long currentTime;
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
        private String remark;
        private int status;
        private int timeLimit;
        private String tradeToUsername;
        private int tradeType;
        private String trateToRealname;
        private String userName;

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

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getCurrentTime() {
            return currentTime;
        }

        public void setCurrentTime(long currentTime) {
            this.currentTime = currentTime;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTimeLimit() {
            return timeLimit;
        }

        public void setTimeLimit(int timeLimit) {
            this.timeLimit = timeLimit;
        }

        public String getTradeToUsername() {
            return tradeToUsername;
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
    }
}
