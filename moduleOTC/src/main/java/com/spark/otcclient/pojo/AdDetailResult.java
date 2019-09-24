package com.spark.otcclient.pojo;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/7/1
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class AdDetailResult {


    /**
     * code : 200
     * message : SUCCESS
     * data : {"id":183,"memberId":92,"advertiseType":0,"tradeType":0,"country":"China","localCurrency":"CNY","coinName":"USDT","payMode":"alipay","payData":null,"number":20,"remainAmount":20,"planFrozenFee":0.2,"remainFrozenFee":0.2,"maxLimit":9999,"minLimit":5,"premiseRate":1.2,"price":1.2,"priceType":1,"timeLimit":40,"remark":"","createTime":1561624976000,"username":"u1561475391012","status":1,"autoReply":0,"autoword":""}
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

    public static class DataBean {
        /**
         * id : 183
         * memberId : 92
         * advertiseType : 0
         * tradeType : 0
         * country : China
         * localCurrency : CNY
         * coinName : USDT
         * payMode : alipay
         * payData : null
         * number : 20.0
         * remainAmount : 20.0
         * planFrozenFee : 0.2
         * remainFrozenFee : 0.2
         * maxLimit : 9999.0
         * minLimit : 5.0
         * premiseRate : 1.2
         * price : 1.2
         * priceType : 1
         * timeLimit : 40
         * remark :
         * createTime : 1561624976000
         * username : u1561475391012
         * status : 1
         * autoReply : 0
         * autoword :
         */

        private long id;
        private int memberId;
        private int advertiseType;
        private int tradeType;
        private String country;
        private String localCurrency;
        private String coinName;
        private String payMode;
        private Object payData;
        private double number;
        private double remainAmount;
        private double planFrozenFee;
        private double remainFrozenFee;
        private double maxLimit;
        private double minLimit;
        private double premiseRate;
        private double price;
        private int priceType;
        private int timeLimit;
        private String remark;
        private long createTime;
        private String username;
        private int status;
        private int autoReply;
        private String autoword;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public int getAdvertiseType() {
            return advertiseType;
        }

        public void setAdvertiseType(int advertiseType) {
            this.advertiseType = advertiseType;
        }

        public int getTradeType() {
            return tradeType;
        }

        public void setTradeType(int tradeType) {
            this.tradeType = tradeType;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getLocalCurrency() {
            return localCurrency;
        }

        public void setLocalCurrency(String localCurrency) {
            this.localCurrency = localCurrency;
        }

        public String getCoinName() {
            return coinName;
        }

        public void setCoinName(String coinName) {
            this.coinName = coinName;
        }

        public String getPayMode() {
            return payMode;
        }

        public void setPayMode(String payMode) {
            this.payMode = payMode;
        }

        public Object getPayData() {
            return payData;
        }

        public void setPayData(Object payData) {
            this.payData = payData;
        }

        public double getNumber() {
            return number;
        }

        public void setNumber(double number) {
            this.number = number;
        }

        public double getRemainAmount() {
            return remainAmount;
        }

        public void setRemainAmount(double remainAmount) {
            this.remainAmount = remainAmount;
        }

        public double getPlanFrozenFee() {
            return planFrozenFee;
        }

        public void setPlanFrozenFee(double planFrozenFee) {
            this.planFrozenFee = planFrozenFee;
        }

        public double getRemainFrozenFee() {
            return remainFrozenFee;
        }

        public void setRemainFrozenFee(double remainFrozenFee) {
            this.remainFrozenFee = remainFrozenFee;
        }

        public double getMaxLimit() {
            return maxLimit;
        }

        public void setMaxLimit(double maxLimit) {
            this.maxLimit = maxLimit;
        }

        public double getMinLimit() {
            return minLimit;
        }

        public void setMinLimit(double minLimit) {
            this.minLimit = minLimit;
        }

        public double getPremiseRate() {
            return premiseRate;
        }

        public void setPremiseRate(double premiseRate) {
            this.premiseRate = premiseRate;
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

        public int getTimeLimit() {
            return timeLimit;
        }

        public void setTimeLimit(int timeLimit) {
            this.timeLimit = timeLimit;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getAutoReply() {
            return autoReply;
        }

        public void setAutoReply(int autoReply) {
            this.autoReply = autoReply;
        }

        public String getAutoword() {
            return autoword;
        }

        public void setAutoword(String autoword) {
            this.autoword = autoword;
        }
    }


}
