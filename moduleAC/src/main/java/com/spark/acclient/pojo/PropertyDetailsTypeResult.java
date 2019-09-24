package com.spark.acclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-08-02
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class PropertyDetailsTypeResult implements Parcelable {

    /**
     * code : 200
     * message : SUCCESS
     * data : [{"key":"DEPOSIT","value":"1","businessType":"sub_type_zh","descript":"充币","status":1,"createTime":12343123142312}]
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

    protected PropertyDetailsTypeResult(Parcel in) {
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
    }

    public static final Creator<PropertyDetailsTypeResult> CREATOR = new Creator<PropertyDetailsTypeResult>() {
        @Override
        public PropertyDetailsTypeResult createFromParcel(Parcel in) {
            return new PropertyDetailsTypeResult(in);
        }

        @Override
        public PropertyDetailsTypeResult[] newArray(int size) {
            return new PropertyDetailsTypeResult[size];
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(code);
        parcel.writeString(message);
        parcel.writeString(responseString);
    }

    public static class DataBean {
        /**
         * key : DEPOSIT
         * value : 1
         * businessType : sub_type_zh
         * descript : 充币
         * status : 1
         * createTime : 12343123142312
         */

        private String key;
        private String value;
        private String businessType;
        private String descript;
        private int status;
        private long createTime;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getBusinessType() {
            return businessType;
        }

        public void setBusinessType(String businessType) {
            this.businessType = businessType;
        }

        public String getDescript() {
            return descript;
        }

        public void setDescript(String descript) {
            this.descript = descript;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }
    }
}
