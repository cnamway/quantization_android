package com.spark.ucclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-07-24
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class InviteFriendResult implements Parcelable {

    /**
     * code : 200
     * message : SUCCESS
     * data : {"id":null,"promotionAmount":1,"promotionSeries":null,"business":null,"realName":null,"totalReardAmount":"60.00000000"}
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

    protected InviteFriendResult(Parcel in) {
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
    }

    public static final Creator<InviteFriendResult> CREATOR = new Creator<InviteFriendResult>() {
        @Override
        public InviteFriendResult createFromParcel(Parcel in) {
            return new InviteFriendResult(in);
        }

        @Override
        public InviteFriendResult[] newArray(int size) {
            return new InviteFriendResult[size];
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(code);
        parcel.writeString(message);
        parcel.writeString(responseString);
    }

    public static class DataBean {
        /**
         * id : null
         * promotionAmount : 1
         * promotionSeries : null
         * business : null
         * realName : null
         * totalReardAmount : 60.00000000
         */

        private Object id;
        private int promotionAmount;
        private Object promotionSeries;
        private Object business;
        private Object realName;
        private String totalReardAmount;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public int getPromotionAmount() {
            return promotionAmount;
        }

        public void setPromotionAmount(int promotionAmount) {
            this.promotionAmount = promotionAmount;
        }

        public Object getPromotionSeries() {
            return promotionSeries;
        }

        public void setPromotionSeries(Object promotionSeries) {
            this.promotionSeries = promotionSeries;
        }

        public Object getBusiness() {
            return business;
        }

        public void setBusiness(Object business) {
            this.business = business;
        }

        public Object getRealName() {
            return realName;
        }

        public void setRealName(Object realName) {
            this.realName = realName;
        }

        public String getTotalReardAmount() {
            return totalReardAmount;
        }

        public void setTotalReardAmount(String totalReardAmount) {
            this.totalReardAmount = totalReardAmount;
        }
    }
}
