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
 * 创建日期：2019/6/1
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class AdSelfDownFindResult implements Parcelable {

    /**
     * code : 200
     * message : SUCCESS
     * data : {"total":4,"size":20,"pages":1,"current":1,"records":[{"id":53,"memberId":58,"advertiseType":0,"tradeType":0,"country":"China","localCurrency":"CNY","coinName":"ETH","payMode":"alipay,wechat,paypal,bankCard","number":12,"remainAmount":0.12,"planFrozenFee":0.12,"remainFrozenFee":0.12,"maxLimit":36,"minLimit":12,"premiseRate":0,"price":2016,"priceType":1,"timeLimit":25,"remark":"有","createTime":1558428757000,"username":"8618656003579"},{"id":79,"memberId":58,"advertiseType":0,"tradeType":0,"country":"China","localCurrency":"CNY","coinName":"BTC","payMode":"wechat","number":1,"remainAmount":1,"planFrozenFee":0.01,"remainFrozenFee":0.01,"maxLimit":12,"minLimit":12,"premiseRate":0,"price":120000,"priceType":1,"timeLimit":20,"remark":"","createTime":1558924888000,"username":"8618656003579"},{"id":80,"memberId":58,"advertiseType":0,"tradeType":0,"country":"China","localCurrency":"CNY","coinName":"ETH","payMode":"card,other","number":12,"remainAmount":12,"planFrozenFee":0.12,"remainFrozenFee":0.12,"maxLimit":23,"minLimit":13,"premiseRate":2,"price":0,"priceType":0,"timeLimit":25,"remark":"就很好","createTime":1559044584000,"username":"8618656003579"},{"id":81,"memberId":58,"advertiseType":0,"tradeType":0,"country":"China","localCurrency":"CNY","coinName":"BTC","payMode":"alipay,wechat,paypal,bankCard","number":2,"remainAmount":2,"planFrozenFee":0.02,"remainFrozenFee":0.02,"maxLimit":23,"minLimit":12,"premiseRate":68.760002,"price":255,"priceType":0,"timeLimit":25,"remark":"句","createTime":1559045267000,"username":"8618656003579"}]}
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

    protected AdSelfDownFindResult(Parcel in) {
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
    }

    public static final Creator<AdSelfDownFindResult> CREATOR = new Creator<AdSelfDownFindResult>() {
        @Override
        public AdSelfDownFindResult createFromParcel(Parcel in) {
            return new AdSelfDownFindResult(in);
        }

        @Override
        public AdSelfDownFindResult[] newArray(int size) {
            return new AdSelfDownFindResult[size];
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
         * total : 4
         * size : 20
         * pages : 1
         * current : 1
         * records : [{"id":53,"memberId":58,"advertiseType":0,"tradeType":0,"country":"China","localCurrency":"CNY","coinName":"ETH","payMode":"alipay,wechat,paypal,bankCard","number":12,"remainAmount":0.12,"planFrozenFee":0.12,"remainFrozenFee":0.12,"maxLimit":36,"minLimit":12,"premiseRate":0,"price":2016,"priceType":1,"timeLimit":25,"remark":"有","createTime":1558428757000,"username":"8618656003579"},{"id":79,"memberId":58,"advertiseType":0,"tradeType":0,"country":"China","localCurrency":"CNY","coinName":"BTC","payMode":"wechat","number":1,"remainAmount":1,"planFrozenFee":0.01,"remainFrozenFee":0.01,"maxLimit":12,"minLimit":12,"premiseRate":0,"price":120000,"priceType":1,"timeLimit":20,"remark":"","createTime":1558924888000,"username":"8618656003579"},{"id":80,"memberId":58,"advertiseType":0,"tradeType":0,"country":"China","localCurrency":"CNY","coinName":"ETH","payMode":"card,other","number":12,"remainAmount":12,"planFrozenFee":0.12,"remainFrozenFee":0.12,"maxLimit":23,"minLimit":13,"premiseRate":2,"price":0,"priceType":0,"timeLimit":25,"remark":"就很好","createTime":1559044584000,"username":"8618656003579"},{"id":81,"memberId":58,"advertiseType":0,"tradeType":0,"country":"China","localCurrency":"CNY","coinName":"BTC","payMode":"alipay,wechat,paypal,bankCard","number":2,"remainAmount":2,"planFrozenFee":0.02,"remainFrozenFee":0.02,"maxLimit":23,"minLimit":12,"premiseRate":68.760002,"price":255,"priceType":0,"timeLimit":25,"remark":"句","createTime":1559045267000,"username":"8618656003579"}]
         */

        private int total;
        private int size;
        private int pages;
        private int current;
        private List<RecordsBean> records;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

        public List<RecordsBean> getRecords() {
            return records;
        }

        public void setRecords(List<RecordsBean> records) {
            this.records = records;
        }

        public static class RecordsBean implements Parcelable {
            /**
             * id : 53
             * memberId : 58
             * advertiseType : 0
             * tradeType : 0
             * country : China
             * localCurrency : CNY
             * coinName : ETH
             * payMode : alipay,wechat,paypal,bankCard
             * number : 12.0
             * remainAmount : 0.12
             * planFrozenFee : 0.12
             * remainFrozenFee : 0.12
             * maxLimit : 36.0
             * minLimit : 12.0
             * premiseRate : 0.0
             * price : 2016.0
             * priceType : 1
             * timeLimit : 25
             * remark : 有
             * createTime : 1558428757000
             * username : 8618656003579
             */



            private long id;
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
            private String payIds;
            private int status;
            private int autoReply;
            private String autoword;

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

            public String getPayIds() {
                return payIds;
            }

            public void setPayIds(String payIds) {
                this.payIds = payIds;
            }

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

            public boolean isBuyType() {
                return advertiseType == 0;
            }

            public String formatType() {
                return advertiseType == 0 ? BaseApplication.getInstance().getString(me.spark.mvvm.R.string.buy2) : BaseApplication.getInstance().getString(me.spark.mvvm.R.string.sell2);
            }

            public String formatLimit() {
                return BaseApplication.getInstance().getString(me.spark.mvvm.R.string.limit) + MathUtils.getRundNumber(minLimit, 2, null) + " - " + MathUtils.getRundNumber(maxLimit, 2, null);
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


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeLong(this.id);
                dest.writeInt(this.memberId);
                dest.writeInt(this.advertiseType);
                dest.writeInt(this.tradeType);
                dest.writeString(this.country);
                dest.writeString(this.localCurrency);
                dest.writeString(this.coinName);
                dest.writeString(this.payMode);
                dest.writeDouble(this.number);
                dest.writeDouble(this.remainAmount);
                dest.writeDouble(this.planFrozenFee);
                dest.writeDouble(this.remainFrozenFee);
                dest.writeDouble(this.maxLimit);
                dest.writeDouble(this.minLimit);
                dest.writeDouble(this.premiseRate);
                dest.writeDouble(this.price);
                dest.writeInt(this.priceType);
                dest.writeInt(this.timeLimit);
                dest.writeString(this.remark);
                dest.writeLong(this.createTime);
                dest.writeString(this.username);
                dest.writeString(this.payIds);
                dest.writeInt(this.status);
                dest.writeInt(this.autoReply);
                dest.writeString(this.autoword);
            }

            public RecordsBean() {
            }

            protected RecordsBean(Parcel in) {
                this.id = in.readLong();
                this.memberId = in.readInt();
                this.advertiseType = in.readInt();
                this.tradeType = in.readInt();
                this.country = in.readString();
                this.localCurrency = in.readString();
                this.coinName = in.readString();
                this.payMode = in.readString();
                this.number = in.readDouble();
                this.remainAmount = in.readDouble();
                this.planFrozenFee = in.readDouble();
                this.remainFrozenFee = in.readDouble();
                this.maxLimit = in.readDouble();
                this.minLimit = in.readDouble();
                this.premiseRate = in.readDouble();
                this.price = in.readDouble();
                this.priceType = in.readInt();
                this.timeLimit = in.readInt();
                this.remark = in.readString();
                this.createTime = in.readLong();
                this.username = in.readString();
                this.payIds = in.readString();
                this.status = in.readInt();
                this.autoReply = in.readInt();
                this.autoword = in.readString();
            }

            public static final Creator<RecordsBean> CREATOR = new Creator<RecordsBean>() {
                @Override
                public RecordsBean createFromParcel(Parcel source) {
                    return new RecordsBean(source);
                }

                @Override
                public RecordsBean[] newArray(int size) {
                    return new RecordsBean[size];
                }
            };
        }
    }
}
