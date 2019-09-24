package com.spark.modulespot.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.utils.DateUtils;
import me.spark.mvvm.utils.DfUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-06-24
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class OpenOrderDetailsResult implements Parcelable {


    /**
     * code : 200
     * message : SUCCESS
     * data : [{"id":"F6684500307463731","memberId":"75","orderId":"E668449795200176393","side":1,"symbol":"BTC/USDT","price":"9314.6725","amount":"0.001","turnover":"9.3146725","fee":"0.01862934500","time":"1561215726741"},{"id":"F6684500518051753","memberId":"75","orderId":"E668449795200176393","side":1,"symbol":"BTC/USDT","price":"9314.6725","amount":"0.00010","turnover":"0.93146725","fee":"0.001862934500","time":"1561215726743"}]
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
         * id : F6684500307463731
         * memberId : 75
         * orderId : E668449795200176393
         * side : 1
         * symbol : BTC/USDT
         * price : 9314.6725
         * amount : 0.001
         * turnover : 9.3146725
         * fee : 0.01862934500
         * time : 1561215726741
         */

        private String id;
        private String memberId;
        private String orderId;
        private int side;
        private String symbol;
        private String price;
        private String amount;
        private String turnover;
        private String fee;
        private String time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public int getSide() {
            return side;
        }

        public void setSide(int side) {
            this.side = side;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getTurnover() {
            return turnover;
        }

        public void setTurnover(String turnover) {
            this.turnover = turnover;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }


        public String formatTime() {
            return DateUtils.formatDate("yyyy.MM.dd HH:mm:ss", time);
        }

        public String formatPrice() {
            return DfUtils.numberFormat(Double.valueOf(price), 4) + " " + symbol.split("/")[1];
        }

        public String formatNum() {
            return DfUtils.numberFormat(Double.valueOf(amount), 4) + " " + symbol.split("/")[0];
        }

        public String formatFee() {
            return DfUtils.numberFormat(Double.valueOf(fee), 8) + " " + (side == 0 ? symbol.split("/")[0] : symbol.split("/")[1]);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }


    public OpenOrderDetailsResult() {
    }

    protected OpenOrderDetailsResult(Parcel in) {
        this.code = in.readInt();
        this.message = in.readString();
        this.count = in.readParcelable(Object.class.getClassLoader());
        this.responseString = in.readString();
        this.url = in.readParcelable(Object.class.getClassLoader());
        this.cid = in.readParcelable(Object.class.getClassLoader());
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<OpenOrderDetailsResult> CREATOR = new Parcelable.Creator<OpenOrderDetailsResult>() {
        @Override
        public OpenOrderDetailsResult createFromParcel(Parcel source) {
            return new OpenOrderDetailsResult(source);
        }

        @Override
        public OpenOrderDetailsResult[] newArray(int size) {
            return new OpenOrderDetailsResult[size];
        }
    };
}
