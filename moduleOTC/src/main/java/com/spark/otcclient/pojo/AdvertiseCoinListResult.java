package com.spark.otcclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/27
 * 描    述：广告交易币种信息
 * 修订历史：
 * ================================================
 */
public class AdvertiseCoinListResult implements Parcelable {


    /**
     * code : 200
     * message : SUCCESS
     * data : [{"id":1,"merchantType":"1","coinName":"BTC","amount":2,"buyFeeRate":0.01,"sellFeeRate":0.01,"advMinLimit":1,"advMaxLimit":20,"createTime":1546840537000,"updateTime":null,"status":1},{"id":2,"merchantType":"1","coinName":"ETH","amount":100,"buyFeeRate":0.01,"sellFeeRate":0.01,"advMinLimit":10,"advMaxLimit":100,"createTime":1546840569000,"updateTime":null,"status":1},{"id":3,"merchantType":"1","coinName":"USDT","amount":100,"buyFeeRate":0.01,"sellFeeRate":0.01,"advMinLimit":10,"advMaxLimit":10000,"createTime":1546840612000,"updateTime":null,"status":1},{"id":4,"merchantType":"1","coinName":"CNT","amount":100,"buyFeeRate":0.01,"sellFeeRate":0.01,"advMinLimit":10,"advMaxLimit":10000,"createTime":1546840685000,"updateTime":null,"status":1}]
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

    public static class DataBean {
        /**
         * id : 1
         * merchantType : 1
         * coinName : BTC
         * amount : 2.0
         * buyFeeRate : 0.01
         * sellFeeRate : 0.01
         * advMinLimit : 1.0
         * advMaxLimit : 20.0
         * createTime : 1546840537000
         * updateTime : null
         * status : 1
         */

        private int id;
        private String merchantType;
        private String coinName;
        private double amount;
        private double buyFeeRate;
        private double sellFeeRate;
        private double advMinLimit;
        private double advMaxLimit;
        private long createTime;
        private Object updateTime;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMerchantType() {
            return merchantType;
        }

        public void setMerchantType(String merchantType) {
            this.merchantType = merchantType;
        }

        public String getCoinName() {
            return coinName;
        }

        public void setCoinName(String coinName) {
            this.coinName = coinName;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public double getBuyFeeRate() {
            return buyFeeRate;
        }

        public void setBuyFeeRate(double buyFeeRate) {
            this.buyFeeRate = buyFeeRate;
        }

        public double getSellFeeRate() {
            return sellFeeRate;
        }

        public void setSellFeeRate(double sellFeeRate) {
            this.sellFeeRate = sellFeeRate;
        }

        public double getAdvMinLimit() {
            return advMinLimit;
        }

        public void setAdvMinLimit(double advMinLimit) {
            this.advMinLimit = advMinLimit;
        }

        public double getAdvMaxLimit() {
            return advMaxLimit;
        }

        public void setAdvMaxLimit(double advMaxLimit) {
            this.advMaxLimit = advMaxLimit;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.message);
        dest.writeString(this.responseString);
        dest.writeList(this.data);
    }

    public AdvertiseCoinListResult() {
    }

    protected AdvertiseCoinListResult(Parcel in) {
        this.code = in.readInt();
        this.message = in.readString();
        this.count = in.readParcelable(Object.class.getClassLoader());
        this.responseString = in.readString();
        this.url = in.readParcelable(Object.class.getClassLoader());
        this.cid = in.readParcelable(Object.class.getClassLoader());
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<AdvertiseCoinListResult> CREATOR = new Parcelable.Creator<AdvertiseCoinListResult>() {
        @Override
        public AdvertiseCoinListResult createFromParcel(Parcel source) {
            return new AdvertiseCoinListResult(source);
        }

        @Override
        public AdvertiseCoinListResult[] newArray(int size) {
            return new AdvertiseCoinListResult[size];
        }
    };
}