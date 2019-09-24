package me.spark.mvvm.http;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/18
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CommonResult implements Parcelable {
    private String cid;
    private int code;
    private String count;
    private String data;
    private String message;
    private String responseString;
    private String url;


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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
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

    protected CommonResult(Parcel in) {
        cid = in.readString();
        code = in.readInt();
        count = in.readString();
        data = in.readString();
        message = in.readString();
        responseString = in.readString();
        url = in.readString();
    }

    public static final Creator<CommonResult> CREATOR = new Creator<CommonResult>() {
        @Override
        public CommonResult createFromParcel(Parcel in) {
            return new CommonResult(in);
        }

        @Override
        public CommonResult[] newArray(int size) {
            return new CommonResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cid);
        dest.writeInt(code);
        dest.writeString(count);
        dest.writeString(data);
        dest.writeString(message);
        dest.writeString(responseString);
        dest.writeString(url);
    }
}
