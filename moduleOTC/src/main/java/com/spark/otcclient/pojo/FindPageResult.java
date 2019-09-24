package com.spark.otcclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.spark.otcclient.R;

import java.util.List;

import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.utils.MathUtils;
import me.spark.mvvm.utils.StringUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/25
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class FindPageResult implements Parcelable {

    /**
     * code : 200
     * message : SUCCESS
     * data : {"total":1,"size":20,"pages":1,"current":0,"records":[{"id":72,"memberId":64,"advertiseType":0,"tradeType":0,"country":"China","localCurrency":"CNY","coinName":"BTC","payMode":"alipay","number":1,"remainAmount":1,"planFrozenFee":0.01,"remainFrozenFee":0.01,"maxLimit":100,"minLimit":10,"premiseRate":0,"price":12,"priceType":1,"timeLimit":0,"remark":"","createTime":1558598790000,"username":"u1558433403446","rangeTimeOrder":0,"rangeTimeSuccessOrder":4,"realName":"徐佳莹"}]}
     * count :
     * responseString : 200~SUCCESS
     * url :
     * cid :
     */

    private int code;
    private String message;
    private DataBean data;
    private String count;
    private String responseString;
    private String url;
    private String cid;

    protected FindPageResult(Parcel in) {
        code = in.readInt();
        message = in.readString();
        count = in.readString();
        responseString = in.readString();
        url = in.readString();
        cid = in.readString();
    }

    public static final Creator<FindPageResult> CREATOR = new Creator<FindPageResult>() {
        @Override
        public FindPageResult createFromParcel(Parcel in) {
            return new FindPageResult(in);
        }

        @Override
        public FindPageResult[] newArray(int size) {
            return new FindPageResult[size];
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
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
        dest.writeString(count);
        dest.writeString(responseString);
        dest.writeString(url);
        dest.writeString(cid);
    }

    public static class DataBean {
        /**
         * total : 1
         * size : 20
         * pages : 1
         * current : 0
         * records : [{"id":72,"memberId":64,"advertiseType":0,"tradeType":0,"country":"China","localCurrency":"CNY","coinName":"BTC","payMode":"alipay","number":1,"remainAmount":1,"planFrozenFee":0.01,"remainFrozenFee":0.01,"maxLimit":100,"minLimit":10,"premiseRate":0,"price":12,"priceType":1,"timeLimit":0,"remark":"","createTime":1558598790000,"username":"u1558433403446","rangeTimeOrder":0,"rangeTimeSuccessOrder":4,"realName":"徐佳莹"}]
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

        public static class RecordsBean {
            /**
             * id : 72
             * memberId : 64
             * advertiseType : 0
             * tradeType : 0
             * country : China
             * localCurrency : CNY
             * coinName : BTC
             * payMode : alipay
             * number : 1.0
             * remainAmount : 1.0
             * planFrozenFee : 0.01
             * remainFrozenFee : 0.01
             * maxLimit : 100.0
             * minLimit : 10.0
             * premiseRate : 0.0
             * price : 12.0
             * priceType : 1
             * timeLimit : 0
             * remark :
             * createTime : 1558598790000
             * username : u1558433403446
             * rangeTimeOrder : 0
             * rangeTimeSuccessOrder : 4
             * realName : 徐佳莹
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
            private int rangeTimeOrder;
            private int rangeTimeSuccessOrder;
            private String realName;

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

            public int getRangeTimeOrder() {
                return rangeTimeOrder;
            }

            public void setRangeTimeOrder(int rangeTimeOrder) {
                this.rangeTimeOrder = rangeTimeOrder;
            }

            public int getRangeTimeSuccessOrder() {
                return rangeTimeSuccessOrder;
            }

            public void setRangeTimeSuccessOrder(int rangeTimeSuccessOrder) {
                this.rangeTimeSuccessOrder = rangeTimeSuccessOrder;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            /**
             * 姓名缩写
             *
             * @return
             */
            public String getNameFirstChar() {
                return StringUtils.isEmpty(realName) ? "" : realName.substring(0, 1);
            }

            /**
             * 数量
             *
             * @return
             */
            public String formatNum() {
                return BaseApplication.getInstance().getString(R.string.str_number) + MathUtils.getRundNumber(remainAmount, 2, null) + " " + coinName;
            }

            /**
             * 限额
             *
             * @return
             */
            public String formatLimit() {
                return BaseApplication.getInstance().getString(R.string.str_limit_money) +
                        MathUtils.getRundNumber(minLimit, 2, null)
                        + " - " +
                        MathUtils.getRundNumber(maxLimit, 2, null)
                        + " " + localCurrency;
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
             * 是否支持Paypal支付
             *
             * @return
             */
            public boolean isSupportPaypalPay() {
                return payMode.contains(Constant.PAYPAL);
            }

            /**
             * 是否支持其他支付
             *
             * @return
             */
            public boolean isSupportOtherPay() {
                return payMode.contains(Constant.other);
            }

            /**
             * 价格
             *
             * @return
             */
            public String formatPrice() {
                return price + " " + localCurrency;
            }

            /**
             * 购买or出售
             *
             * @return
             */
            public String buyOrSell() {
                return advertiseType == 0 ? BaseApplication.getInstance().getString(R.string.sell2) : BaseApplication.getInstance().getString(R.string.buy2);
            }


            public String formatRangeTimeOrder() {
                if (StringUtils.isEmpty(String.valueOf(rangeTimeOrder))
                        || StringUtils.isEmpty(String.valueOf(rangeTimeSuccessOrder))
                        || rangeTimeSuccessOrder == 0) {
                    rangeTimeOrder = 1;
                    rangeTimeSuccessOrder = 0;
                }
                return rangeTimeSuccessOrder + "    " + MathUtils.getRundNumber((Double.parseDouble(rangeTimeSuccessOrder+"") / rangeTimeOrder) * 100, 2, null) + "%";
            }
        }
    }
}
