package me.spark.mvvm.base;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/17
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class BaseResponseError implements Parcelable{

    /**
     * code : 411
     * message : need captcha
     * data : phone
     * url : /cas/v1/tickets/TGT-8-kKVV-zSttmSHFR7ApS8QZ-vNMHKpixgdHs6zG2fZt6ECZQTUiT0cx-i-Vtc-WizoJjQbitaccept
     * cid : 9d82b535-54ba-43b5-82c9-2aee811ffe5a
     */

    private int code;
    private String message;
    private String data;
    private String url;
    private String cid;

    protected BaseResponseError(Parcel in) {
        code = in.readInt();
        message = in.readString();
        data = in.readString();
        url = in.readString();
        cid = in.readString();
    }

    public static final Creator<BaseResponseError> CREATOR = new Creator<BaseResponseError>() {
        @Override
        public BaseResponseError createFromParcel(Parcel in) {
            return new BaseResponseError(in);
        }

        @Override
        public BaseResponseError[] newArray(int size) {
            return new BaseResponseError[size];
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
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
        dest.writeString(url);
        dest.writeString(cid);
    }

    @Override
    public String toString() {
        return "ResponseError{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                ", url='" + url + '\'' +
                ", cid='" + cid + '\'' +
                '}';
    }
}
