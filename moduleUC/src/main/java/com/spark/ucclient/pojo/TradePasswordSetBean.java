package com.spark.ucclient.pojo;

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
public class TradePasswordSetBean implements Parcelable{

    /**
     * tradePassword : string
     */

    private String tradePassword;

    public TradePasswordSetBean() {
    }

    protected TradePasswordSetBean(Parcel in) {
        tradePassword = in.readString();
    }

    public static final Creator<TradePasswordSetBean> CREATOR = new Creator<TradePasswordSetBean>() {
        @Override
        public TradePasswordSetBean createFromParcel(Parcel in) {
            return new TradePasswordSetBean(in);
        }

        @Override
        public TradePasswordSetBean[] newArray(int size) {
            return new TradePasswordSetBean[size];
        }
    };

    public String getTradePassword() {
        return tradePassword;
    }

    public void setTradePassword(String tradePassword) {
        this.tradePassword = tradePassword;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tradePassword);
    }
}
