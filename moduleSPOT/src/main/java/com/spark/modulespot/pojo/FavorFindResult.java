package com.spark.modulespot.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/3
 * 描    述：全部收藏币种
 * 修订历史：
 * ================================================
 */
public class FavorFindResult implements Parcelable {


    /**
     * code : 200
     * message :
     * data : [{"id":1135440484758675457,"symbol":"BTC/USDT","memberId":58,"addTime":"2019-06-03 14:58:01"},{"id":1135440404957847554,"symbol":"ETH/USDT","memberId":58,"addTime":"2019-06-03 14:57:42"}]
     * count : null
     * responseString : 200~
     */

    private int code;
    private String message;
    private Object count;
    private String responseString;
    private List<DataBean> data;

    protected FavorFindResult(Parcel in) {
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
    }

    public static final Creator<FavorFindResult> CREATOR = new Creator<FavorFindResult>() {
        @Override
        public FavorFindResult createFromParcel(Parcel in) {
            return new FavorFindResult(in);
        }

        @Override
        public FavorFindResult[] newArray(int size) {
            return new FavorFindResult[size];
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
         * id : 1135440484758675457
         * symbol : BTC/USDT
         * memberId : 58
         * addTime : 2019-06-03 14:58:01
         */

        private long id;
        private String symbol;
        private int memberId;
        private String addTime;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }
    }
}
