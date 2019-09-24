package com.spark.otcclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.utils.DfUtils;
import me.spark.mvvm.utils.MathUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/31
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class AdSelfUpFindResult implements Parcelable {

    /**
     * code : 200
     * message : SUCCESS
     * data : [{"id":65,"memberId":58,"advertiseType":0,"tradeType":0,"country":"China","localCurrency":"CNY","coinName":"USDT","payMode":"alipay,wechat,paypal,bankCard","number":20,"remainAmount":20,"planFrozenFee":0.2,"remainFrozenFee":0.2,"maxLimit":2,"minLimit":1,"premiseRate":0,"price":25,"priceType":1,"timeLimit":30,"remark":"","createTime":1558509994000,"username":"8618656003579"},{"id":66,"memberId":58,"advertiseType":0,"tradeType":0,"country":"China","localCurrency":"CNY","coinName":"ETH","payMode":"alipay,wechat,paypal,bankCard","number":12,"remainAmount":0.12,"planFrozenFee":0.12,"remainFrozenFee":0.12,"maxLimit":58,"minLimit":12,"premiseRate":0,"price":26000,"priceType":1,"timeLimit":25,"remark":"","createTime":1558431717000,"username":"8618656003579"},{"id":77,"memberId":58,"advertiseType":1,"tradeType":0,"country":"China","localCurrency":"CNY","coinName":"BTC","payMode":"other,wechat","number":2,"remainAmount":2,"planFrozenFee":0.02,"remainFrozenFee":0.02,"maxLimit":360,"minLimit":255,"premiseRate":0,"price":120000,"priceType":1,"timeLimit":20,"remark":"应该会","createTime":1558925541000,"username":"8618656003579"},{"id":68,"memberId":58,"advertiseType":0,"tradeType":0,"country":"China","localCurrency":"CNY","coinName":"ETH","payMode":"alipay,wechat,paypal,bankCard","number":10,"remainAmount":10,"planFrozenFee":0.1,"remainFrozenFee":0.1,"maxLimit":30,"minLimit":10,"premiseRate":0,"price":20,"priceType":1,"timeLimit":20,"remark":"","createTime":1558594758000,"username":"8618656003579"},{"id":69,"memberId":58,"advertiseType":0,"tradeType":0,"country":"China","localCurrency":"CNY","coinName":"ETH","payMode":"alipay,wechat,paypal,bankCard","number":10,"remainAmount":10,"planFrozenFee":0.1,"remainFrozenFee":0.1,"maxLimit":20,"minLimit":10,"premiseRate":0,"price":20,"priceType":1,"timeLimit":20,"remark":"","createTime":1558595052000,"username":"8618656003579"}]
     * count : null
     * responseString : 200~SUCCESS
     * url : null
     * cid : null
     */

    private int code;
    private String message;
    private Object count;
    private String responseString;
    private Object url;
    private Object cid;
    private List<DataBean> data;

    protected AdSelfUpFindResult(Parcel in) {
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
    }

    public static final Creator<AdSelfUpFindResult> CREATOR = new Creator<AdSelfUpFindResult>() {
        @Override
        public AdSelfUpFindResult createFromParcel(Parcel in) {
            return new AdSelfUpFindResult(in);
        }

        @Override
        public AdSelfUpFindResult[] newArray(int size) {
            return new AdSelfUpFindResult[size];
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(message);
        dest.writeString(responseString);
    }

    public static class DataBean {
        /**
         * id : 65
         * memberId : 58
         * advertiseType : 0
         * tradeType : 0
         * country : China
         * localCurrency : CNY
         * coinName : USDT
         * payMode : alipay,wechat,paypal,bankCard
         * number : 20.0
         * remainAmount : 20.0
         * planFrozenFee : 0.2
         * remainFrozenFee : 0.2
         * maxLimit : 2.0
         * minLimit : 1.0
         * premiseRate : 0.0
         * price : 25.0
         * priceType : 1
         * timeLimit : 30
         * remark :
         * createTime : 1558509994000
         * username : 8618656003579
         */

        private int id;
        private int memberId;
        private int advertiseType;
        private int tradeType;
        private String country;
        private String localCurrency;
        private String coinName;
        private String payMode;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
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

        public boolean isBuyType(){
            return advertiseType == 0 ;
        }
        public String formatType() {
            return advertiseType == 0 ? BaseApplication.getInstance().getString(me.spark.mvvm.R.string.buy2) : BaseApplication.getInstance().getString(me.spark.mvvm.R.string.sell2);
        }

        public String formatLimit() {
            return BaseApplication.getInstance().getString(me.spark.mvvm.R.string.limit) + MathUtils.getRundNumber(minLimit, 2, null) + " - " + MathUtils.getRundNumber(maxLimit, 2, null) + " CNY";
        }

        /**
         * 是否支持支付宝支付
         *
         * @return
         */
        public boolean isSupportAliPay() {
            return payMode.contains(Constant.alipay);
        }

        /**
         * 是否支持微信支付
         *
         * @return
         */
        public boolean isSupportWeChatPay() {
            return payMode.contains(Constant.wechat);
        }

        /**
         * 是否支持银行卡支付
         *
         * @return
         */
        public boolean isSupportBankPay() {
            return payMode.contains(Constant.card);
        }

        /**
         * 是否支持PayPal支付
         *
         * @return
         */
        public boolean isSupportPayPalPay() {
            return payMode.contains(Constant.PAYPAL);
        }

        public String formatPrice() {
            return DfUtils.formatNum(MathUtils.getRundNumber(price, 2, null)) + localCurrency;
        }
    }
}
