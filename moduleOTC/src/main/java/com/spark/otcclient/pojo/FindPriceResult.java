package com.spark.otcclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/19
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class FindPriceResult implements Parcelable{

    /**
     * code : 200
     * message : SUCCESS
     * data : 6.9736
     * count : null
     * responseString : 200~SUCCESS
     * url : null
     * cid : null
     */

    private int code;
    private String message;
    private double data;
    private Object count;
    private String responseString;
    private Object url;
    private Object cid;

    protected FindPriceResult(Parcel in) {
        code = in.readInt();
        message = in.readString();
        data = in.readDouble();
        responseString = in.readString();
    }

    public static final Creator<FindPriceResult> CREATOR = new Creator<FindPriceResult>() {
        @Override
        public FindPriceResult createFromParcel(Parcel in) {
            return new FindPriceResult(in);
        }

        @Override
        public FindPriceResult[] newArray(int size) {
            return new FindPriceResult[size];
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

    public double getData() {
        return data;
    }

    public void setData(double data) {
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
        dest.writeDouble(data);
        dest.writeString(responseString);
    }
}
