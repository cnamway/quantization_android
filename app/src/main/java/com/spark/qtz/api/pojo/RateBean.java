package com.spark.qtz.api.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/7
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class RateBean implements Parcelable{

    /**
     * code : 200
     * data : 6.7207
     * message : SUCCESS
     * responseString : 200~SUCCESS
     */

    private int code;
    private double data;
    private String message;
    private String responseString;

    protected RateBean(Parcel in) {
        code = in.readInt();
        data = in.readDouble();
        message = in.readString();
        responseString = in.readString();
    }

    public static final Creator<RateBean> CREATOR = new Creator<RateBean>() {
        @Override
        public RateBean createFromParcel(Parcel in) {
            return new RateBean(in);
        }

        @Override
        public RateBean[] newArray(int size) {
            return new RateBean[size];
        }
    };

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public double getData() {
        return data;
    }

    public void setData(double data) {
        this.data = data;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeDouble(data);
        dest.writeString(message);
        dest.writeString(responseString);
    }
}
