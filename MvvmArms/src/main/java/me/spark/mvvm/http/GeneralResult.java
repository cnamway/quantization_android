package me.spark.mvvm.http;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/24
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class GeneralResult implements Parcelable{

    /**
     * cid : string
     * code : 0
     * count : {}
     * data : {}
     * message : string
     * responseString : string
     * url : string
     */

    private String cid;
    private int code;
    private Object count;
    private Object data;
    private String message;
    private String responseString;
    private String url;

    protected GeneralResult(Parcel in) {
        cid = in.readString();
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
        url = in.readString();
    }

    public static final Creator<GeneralResult> CREATOR = new Creator<GeneralResult>() {
        @Override
        public GeneralResult createFromParcel(Parcel in) {
            return new GeneralResult(in);
        }

        @Override
        public GeneralResult[] newArray(int size) {
            return new GeneralResult[size];
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

    public Object getCount() {
        return count;
    }

    public void setCount(Object count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
