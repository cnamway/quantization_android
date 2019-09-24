package me.spark.mvvm.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/6
 * 描    述：WebSocket返回Bean
 * 修订历史：
 * ================================================
 */
public class WebSocketResponse implements Parcelable {
    private short cmd;
    private String response;
    private int type;                           // 对应不同地址的ws
    private int code;                           // 对应不同地址的ws

    public WebSocketResponse(int code, int type, short cmd, String response) {
        this.code = code;
        this.type = type;
        this.cmd = cmd;
        this.response = response;
    }

    public short getCmd() {
        return cmd;
    }

    public void setCmd(short cmd) {
        this.cmd = cmd;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
