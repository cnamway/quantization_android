package com.spark.otcclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/24
 * 描    述：上传图片
 * 修订历史：
 * ================================================
 */
public class UploadPicResult implements Parcelable{

    /**
     * code : 200
     * message : SUCCESS
     * data : http://nbtconline.oss-ap-northeast-1.aliyuncs.com/2019/05/24/3da2be90-b6a5-44d2-aedb-b4683581d23f.jpg
     * count : null
     * responseString : 200~SUCCESS
     * url : null
     * cid : null
     */

    private int code;
    private String message;
    private String data;
    private Object count;
    private String responseString;
    private Object url;
    private Object cid;

    protected UploadPicResult(Parcel in) {
        code = in.readInt();
        message = in.readString();
        data = in.readString();
        responseString = in.readString();
    }

    public static final Creator<UploadPicResult> CREATOR = new Creator<UploadPicResult>() {
        @Override
        public UploadPicResult createFromParcel(Parcel in) {
            return new UploadPicResult(in);
        }

        @Override
        public UploadPicResult[] newArray(int size) {
            return new UploadPicResult[size];
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
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
        dest.writeString(data);
        dest.writeString(responseString);
    }
}
