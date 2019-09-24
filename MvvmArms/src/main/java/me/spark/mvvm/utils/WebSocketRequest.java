package me.spark.mvvm.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/6
 * 描    述：WebSocket请求Bean
 * 修订历史：
 * ================================================
 */
public class WebSocketRequest implements Parcelable {
    private int code;       // 对应不同地址的ws
    private short cmd;      // 传的指令
    private byte[] body;    // 参数

    protected WebSocketRequest(Parcel in) {
        code = in.readInt();
        cmd = (short) in.readInt();
        body = in.createByteArray();
    }


    public WebSocketRequest() {
    }

    public static final Creator<WebSocketRequest> CREATOR = new Creator<WebSocketRequest>() {
        @Override
        public WebSocketRequest createFromParcel(Parcel in) {
            return new WebSocketRequest(in);
        }

        @Override
        public WebSocketRequest[] newArray(int size) {
            return new WebSocketRequest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeInt((int) cmd);
        dest.writeByteArray(body);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public short getCmd() {
        return cmd;
    }

    public void setCmd(short cmd) {
        this.cmd = cmd;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
