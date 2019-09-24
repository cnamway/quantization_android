package com.spark.modulespot.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import me.spark.mvvm.base.Constant;
import me.spark.mvvm.utils.MathUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/28
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class MarketSymbolResult implements Parcelable {


    /**
     * code : 200
     * message : SUCCESS
     * data : {"ask":[{"price":7919.89,"amount":0.0296872},{"price":7920.09,"amount":0.009986},{"price":7920.1,"amount":0.021098},{"price":7920.11,"amount":0.021372},{"price":7920.12,"amount":0.021646}],"bid":[{"price":7919.89,"amount":0.0296872},{"price":7920.09,"amount":0.009986},{"price":7920.1,"amount":0.021098},{"price":7920.11,"amount":0.021372},{"price":7920.12,"amount":0.021646}]}
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

    public MarketSymbolResult() {
    }

    protected MarketSymbolResult(Parcel in) {
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
    }

    public static final Creator<MarketSymbolResult> CREATOR = new Creator<MarketSymbolResult>() {
        @Override
        public MarketSymbolResult createFromParcel(Parcel in) {
            return new MarketSymbolResult(in);
        }

        @Override
        public MarketSymbolResult[] newArray(int size) {
            return new MarketSymbolResult[size];
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
        private List<AskBean> ask;
        private List<BidBean> bid;

        public List<AskBean> getAsk() {
            return ask;
        }

        public void setAsk(List<AskBean> ask) {
            this.ask = ask;
        }

        public List<BidBean> getBid() {
            return bid;
        }

        public void setBid(List<BidBean> bid) {
            this.bid = bid;
        }

        public DataBean(List<AskBean> ask, List<BidBean> bid) {
            this.ask = ask;
            this.bid = bid;
        }

        public static class AskBean {
            /**
             * price : 7919.89
             * amount : 0.0296872
             */

            private double price;
            private double amount;
            private int baseCoinScreenScale;
            private String index;
            private int type;

            public int getBaseCoinScreenScale() {
                return baseCoinScreenScale;
            }

            public void setBaseCoinScreenScale(int baseCoinScreenScale) {
                this.baseCoinScreenScale = baseCoinScreenScale;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public String getIndex() {
                return index;
            }

            public void setIndex(String index) {
                this.index = index;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String forMatAmount() {
                return amount == 0 ? "--" : MathUtils.getRundNumber(amount, type == 0 ? Constant.currencyNumberRate : Constant.contractNumberRate, null);
            }

            public String forMatPrice() {
                return price == 0 ? "--" : MathUtils.getRundNumber(price, type == 0 ? Constant.currencyPriceRate : Constant.contractPriceRate, null);
            }
        }

        public static class BidBean {
            /**
             * price : 7919.89
             * amount : 0.0296872
             */

            private double price;
            private double amount;
            private int baseCoinScreenScale;
            private String index;
            private int type;

            public int getBaseCoinScreenScale() {
                return baseCoinScreenScale;
            }

            public void setBaseCoinScreenScale(int baseCoinScreenScale) {
                this.baseCoinScreenScale = baseCoinScreenScale;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public String getIndex() {
                return index;
            }

            public void setIndex(String index) {
                this.index = index;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String forMatAmount() {
                return amount == 0 ? "--" : MathUtils.getRundNumber(amount, type == 0 ? Constant.currencyNumberRate : Constant.contractNumberRate, null);
            }

            public String forMatPrice() {
                return price == 0 ? "--" : MathUtils.getRundNumber(price, type == 0 ? Constant.currencyPriceRate : Constant.contractPriceRate, null);
            }
        }
    }
}
