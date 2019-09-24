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
public class GoogleAuthSendResult implements Parcelable {


    /**
     * code : 200
     * message : SUCCESS
     * data : {"link":"271@www.xinhuokj.com","secret":"JH42GRXUQJUB475J","url":"otpauth://totp/271@www.xinhuokj.com?secret=JH42GRXUQJUB475J"}
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

    public static class DataBean {
        /**
         * link : 271@www.xinhuokj.com
         * secret : JH42GRXUQJUB475J
         * url : otpauth://totp/271@www.xinhuokj.com?secret=JH42GRXUQJUB475J
         */

        private String link;
        private String secret;
        private String url;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
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
    }

    public GoogleAuthSendResult() {
    }

    protected GoogleAuthSendResult(Parcel in) {
        this.code = in.readInt();
        this.message = in.readString();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.count = in.readParcelable(Object.class.getClassLoader());
        this.responseString = in.readString();
        this.url = in.readParcelable(Object.class.getClassLoader());
        this.cid = in.readParcelable(Object.class.getClassLoader());
    }

    public static final Creator<GoogleAuthSendResult> CREATOR = new Creator<GoogleAuthSendResult>() {
        @Override
        public GoogleAuthSendResult createFromParcel(Parcel source) {
            return new GoogleAuthSendResult(source);
        }

        @Override
        public GoogleAuthSendResult[] newArray(int size) {
            return new GoogleAuthSendResult[size];
        }
    };
}
