package com.spark.acclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/10
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CoinWithdrawAddressResult implements Parcelable{

    /**
     * cid : string
     * code : 0
     * count : {}
     * data : [{"address":"string","coinId":"string","createTime":"2019-06-10T07:36:49.612Z","deleteTime":"2019-06-10T07:36:49.612Z","id":0,"memberId":0,"remark":"string","status":0}]
     * message : string
     * responseString : string
     * url : string
     */

    private String cid;
    private int code;
    private CountBean count;
    private String message;
    private String responseString;
    private String url;
    private List<DataBean> data;

    protected CoinWithdrawAddressResult(Parcel in) {
        cid = in.readString();
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
        url = in.readString();
    }

    public static final Creator<CoinWithdrawAddressResult> CREATOR = new Creator<CoinWithdrawAddressResult>() {
        @Override
        public CoinWithdrawAddressResult createFromParcel(Parcel in) {
            return new CoinWithdrawAddressResult(in);
        }

        @Override
        public CoinWithdrawAddressResult[] newArray(int size) {
            return new CoinWithdrawAddressResult[size];
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
         * address : string
         * coinId : string
         * createTime : 2019-06-10T07:36:49.612Z
         * deleteTime : 2019-06-10T07:36:49.612Z
         * id : 0
         * memberId : 0
         * remark : string
         * status : 0
         */

        private String address;
        private String coinId;
        private String createTime;
        private String deleteTime;
        private int id;
        private int memberId;
        private String remark;
        private int status;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCoinId() {
            return coinId;
        }

        public void setCoinId(String coinId) {
            this.coinId = coinId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDeleteTime() {
            return deleteTime;
        }

        public void setDeleteTime(String deleteTime) {
            this.deleteTime = deleteTime;
        }

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
    }
}
