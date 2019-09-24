package com.spark.otcclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/30
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class TradeAreaListResult implements Parcelable {

    /**
     * cid : string
     * code : 0
     * count : {}
     * data : [{"areaCode":"string","enName":"string","language":"string","localCurrency":"string","sort":0,"sysLanguage":"string","zhName":"string"}]
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

    protected TradeAreaListResult(Parcel in) {
        cid = in.readString();
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
        url = in.readString();
    }

    public static final Creator<TradeAreaListResult> CREATOR = new Creator<TradeAreaListResult>() {
        @Override
        public TradeAreaListResult createFromParcel(Parcel in) {
            return new TradeAreaListResult(in);
        }

        @Override
        public TradeAreaListResult[] newArray(int size) {
            return new TradeAreaListResult[size];
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
         * areaCode : string
         * enName : string
         * language : string
         * localCurrency : string
         * sort : 0
         * sysLanguage : string
         * zhName : string
         */

        private String areaCode;
        private String enName;
        private String language;
        private String localCurrency;
        private int sort;
        private String sysLanguage;
        private String zhName;

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getEnName() {
            return enName;
        }

        public void setEnName(String enName) {
            this.enName = enName;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getLocalCurrency() {
            return localCurrency;
        }

        public void setLocalCurrency(String localCurrency) {
            this.localCurrency = localCurrency;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getSysLanguage() {
            return sysLanguage;
        }

        public void setSysLanguage(String sysLanguage) {
            this.sysLanguage = sysLanguage;
        }

        public String getZhName() {
            return zhName;
        }

        public void setZhName(String zhName) {
            this.zhName = zhName;
        }
    }
}
